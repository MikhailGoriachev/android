package org.goriachev.homework.fragments;

import static org.goriachev.homework.R.raw.song;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.IntermediateSensorData;
import org.goriachev.homework.entities.SensorEntity;
import org.goriachev.homework.infrastructure.Settings;
import org.goriachev.homework.repositories.IntermediateSensorDataRepository;
import org.goriachev.homework.repositories.ResultsRepository;
import org.goriachev.homework.repositories.SensorsRepository;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


// Фрагмент для для запуска сбора данных и обработки
public class ExecuteFragment extends Fragment implements Serializable {

    MediaPlayer mediaPlayer;

    SensorManager sensorManager;

    // датчик ускорения
    Sensor accelerometerSensor;
    SensorEntity accelerometerSensorEntity;

    // датчик приближения
    Sensor proximitySensor;
    SensorEntity proximitySensorEntity;

    // датчик освещённости
    Sensor lightSensor;
    SensorEntity lightSensorEntity;

    // репозиторий датчиков
    SensorsRepository sensorsRepository;

    // репозиторий промежуточных данных датчиков
    IntermediateSensorDataRepository intermediateSensorDataRepository;

    // репозиторий результатов
    ResultsRepository resultsRepository;

    // таймер для сбора данных
    Timer timer;

    // длительность сбора информации (в секундах)
    final static int TIMER_DURATION = 120;

    // кнопка запуска сбора данных
    Button btnExecute;

    // кнопка обработки данных
    Button btnProcessData;


    public ExecuteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        mediaPlayer = MediaPlayer.create(getContext(), song);

        timer = new Timer();

        // создание репозиториев
        sensorsRepository = new SensorsRepository(getContext());
        intermediateSensorDataRepository = new IntermediateSensorDataRepository(getContext());
        resultsRepository = new ResultsRepository(getContext());

        // получение датчиков
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // получение записей датчиков из базы данных

        // датчик ускорения
        accelerometerSensorEntity = sensorsRepository.findByIdentifierSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometerSensorEntity == null) {
            sensorsRepository.store(new SensorEntity(0, accelerometerSensor.getType(), accelerometerSensor.getName()));
            accelerometerSensorEntity = sensorsRepository.findByIdentifierSensor(Sensor.TYPE_ACCELEROMETER);
        }

        accelerometerSensorEntity = sensorsRepository.findByIdentifierSensor(Sensor.TYPE_ACCELEROMETER);

        // датчик приближения
        proximitySensorEntity = sensorsRepository.findByIdentifierSensor(Sensor.TYPE_PROXIMITY);

        if (proximitySensorEntity == null) {
            sensorsRepository.store(new SensorEntity(0, proximitySensor.getType(), proximitySensor.getName()));
            proximitySensorEntity = sensorsRepository.findByIdentifierSensor(Sensor.TYPE_PROXIMITY);
        }

        // датчик освещённости
        lightSensorEntity = sensorsRepository.findByIdentifierSensor(Sensor.TYPE_LIGHT);

        if (lightSensorEntity == null) {
            sensorsRepository.store(new SensorEntity(0, lightSensor.getType(), lightSensor.getName()));
            lightSensorEntity = sensorsRepository.findByIdentifierSensor(Sensor.TYPE_LIGHT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        var view = inflater.inflate(R.layout.fragment_execute, container, false);

        // поиск элементов интерфейса
        btnExecute = view.findViewById(R.id.btn_execute);
        btnProcessData = view.findViewById(R.id.btn_process_data);

        btnProcessData.setEnabled(false);

        // установка обработчиков на элементы интерфейса
        btnExecute.setOnClickListener(v -> onStartExecute());
        btnProcessData.setOnClickListener(v -> onProcessData());

        return view;
    }

    // начало сбора данных
    private void onStartExecute() {

        btnExecute.setEnabled(false);

        // отписка слушателей от датчиков
        unregisteredListeners();

        // период опроса датчиков
        var period = (int) (Settings.getPeriod() * 1e+6);

        // вывод сообщения о начале сбора данных
        Toast.makeText(getActivity(), "Начат сбора данных", Toast.LENGTH_LONG).show();

        // очистка таблиц
        intermediateSensorDataRepository.deleteAll();

        // датчик ускорения
        if (Settings.isAccelerometerSensor()) {
            sensorManager.registerListener(
                    accelerometerListener,
                    accelerometerSensor,
                    period
            );
        }

        // датчик приближения
        if (Settings.isProximitySensor()) {
            sensorManager.registerListener(
                    proximityListener,
                    proximitySensor,
                    period
            );
        }

        // датчик освещённости
        if (Settings.isLightSensor()) {
            sensorManager.registerListener(
                    lightListener,
                    lightSensor,
                    period
            );
        }

        // запуск аудио
        mediaPlayer.start();

        var context = getActivity();

        // запуск таймера
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        unregisteredListeners();
                        btnProcessData.post(() -> btnProcessData.setEnabled(true));
                        mediaPlayer.stop();

                        try {
                            mediaPlayer.prepare();
                            mediaPlayer.seekTo(0);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        // вывод сообщения об окончании сбора данных
                        context.runOnUiThread(() -> Toast.makeText(context, "Окончен сбора данных", Toast.LENGTH_LONG).show());
                    }
                },
                TIMER_DURATION * 1000
        );
    }

    // обработка данных
    private void onProcessData() {
        var idf = intermediateSensorDataRepository.getAll();
        resultsRepository.store(intermediateSensorDataRepository.getResults());
        btnExecute.setEnabled(true);
        btnProcessData.setEnabled(false);

        Toast.makeText(getActivity(), "Данные обработаны", Toast.LENGTH_LONG).show();
    }

    // отписка слушателей от датчиков
    private void unregisteredListeners() {
        sensorManager.unregisterListener(accelerometerListener, accelerometerSensor);
        sensorManager.unregisterListener(proximityListener, proximitySensor);
        sensorManager.unregisterListener(lightListener, lightSensor);
    }

    // слушатель датчика ускорения
    SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            intermediateSensorDataRepository.store(new IntermediateSensorData(
                    0, accelerometerSensorEntity, sensorEvent.values[0], Calendar.getInstance().getTime()
            ));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    // слушатель датчика приближения
    SensorEventListener proximityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            intermediateSensorDataRepository.store(new IntermediateSensorData(
                    0, proximitySensorEntity, sensorEvent.values[0], Calendar.getInstance().getTime()
            ));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };


    // слушатель датчика освещённости
    SensorEventListener lightListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            intermediateSensorDataRepository.store(new IntermediateSensorData(
                    0, lightSensorEntity, sensorEvent.values[0], Calendar.getInstance().getTime()
            ));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

}