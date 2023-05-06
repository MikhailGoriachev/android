package org.goriachev.homework.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.goriachev.homework.R;
import org.goriachev.homework.infrastructure.Settings;
import org.goriachev.homework.models.Message;
import org.goriachev.homework.utils.Utils;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MessengerService extends Service {

    private final ServiceBinder binder = new ServiceBinder();

    // менеджер уведомлений
    private NotificationManager notificationManager;

    // id канала уведомлений
    final String CHANNEL_ID = MessengerService.class.getCanonicalName();

    // объект для работы с JSON
    private final Gson gson = new GsonBuilder().create();

    // таймер для запуска обработки сервиса
    private Timer timer;

    // задача для таймера
    private TimerTask task;

    // интервал запуска (мс)
    private long period;

    // время задержки (мс)
    private long delay;

    // сообщения
    private List<Message> messageList;

    public List<Message> getMessageList() {
        return new ArrayList<>(messageList);
    }

    // файл для сохранения записей
    private String fileName;


    public MessengerService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        period = Settings.getPeriod();
        delay = Settings.getDelay();
        fileName = Settings.getMessagesFileName();

        if (!load()) {
            messageList = new ArrayList<>(Utils.messageList);
            save();
        }

        notificationManager = createNotificationChannel();

        run();

        return super.onStartCommand(intent, flags, startId);
    }


    // В обработке сервис вычисляет в отдельном потоке суммарную длину текстов сообщений, количество
    // сообщений с вложениями, количество сообщений с заполненными темами. По окончании обработки
    // выводить уведомление с результатами обработки
    private void run() {
        if (task != null)
            task.cancel();

        if (period > 0) {
            task = new TimerTask() {
                @Override
                public void run() {

                    var stat = new int[3];

                    messageList.forEach(message -> {
                        stat[0] += message.getText().length();
                        stat[1] += message.isAttach() ? 1 : 0;
                        stat[2] += !message.getSubject().isEmpty() ? 1 : 0;
                    });

                    sendNotify(
                            "Статистка сообщений",
                            String.format(Locale.UK, "Длина: %d\r\nС вложениями: %d\r\nС темами: %d\r\nВсего сообщений: %d",
                                    stat[0], stat[1], stat[2], messageList.size())
                    );
                }
            };
        }

        timer.schedule(task, delay * 1000, period * 1000);
    }

    // добавить сообщение
    public void sendMessage(Message message) {
        var id = messageList.size() > 0
                ? messageList.stream()
                .max(Comparator.comparingInt(Message::getId))
                .get()
                .getId() + 1
                : 1;

        message.setId(id);
        messageList.add(message);

        save();
    }

    // редактировать сообщение
    public void editMessage(int id, Message message) {
        var item = messageList
                .stream()
                .filter(m -> m.getId() == id)
                .findFirst();

        messageList.set(messageList.indexOf(item.get()), message);

        save();
    }

    // удалить сообщение
    public void deleteMessage(int id) {
        var item = messageList.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .get();

        messageList.remove(item);
        save();
    }


    // отправка уведомления
    private void sendNotify(String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text));

        notificationManager.notify(1, builder.build());
    }

    // сохранить сообщения
    private void save() {
        String json = gson.toJson(messageList);

        try (var stream = getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE)) {
            stream.write(json.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // загрузить сообщения
    private boolean load() {
        try (var streamReader = new InputStreamReader(getApplicationContext().openFileInput(fileName))) {
            messageList = new ArrayList<>(Arrays.asList(gson.fromJson(streamReader, Message[].class)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // создание канала уведомлений
    private NotificationManager createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            var channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            var manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

            return manager;
        } else
            return (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    public class ServiceBinder extends Binder {
        public MessengerService getService() {
            return MessengerService.this;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        // остановка обработки
        task.cancel();
    }
}
