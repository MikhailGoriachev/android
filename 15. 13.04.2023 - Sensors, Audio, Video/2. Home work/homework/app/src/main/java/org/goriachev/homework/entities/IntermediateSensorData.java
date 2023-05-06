package org.goriachev.homework.entities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.infrastructure.Entity;
import org.goriachev.homework.repositories.SensorsRepository;
import org.goriachev.homework.utils.Utils;

import java.util.Date;

// Класс Промежуточные данные датчика
public class IntermediateSensorData extends Entity<IntermediateSensorData> {

    // название таблицы
    public static final String TABLE_NAME = "intermediate_sensor_data";

    // поля
    public static final String SENSOR_ID = "sensor_id";
    public static final String VALUE = "value";
    public static final String EXEC_DATE_TIME = "exec_date_time";

    // датчик
    private SensorEntity sensor;

    public SensorEntity getSensor() {
        return sensor;
    }

    public void setSensor(SensorEntity sensor) {
        this.sensor = sensor;
    }

    // значение
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    // дата и время работы
    private Date execDateTime;

    public Date getExecDateTime() {
        return execDateTime;
    }

    public void setExecDateTime(Date execDateTime) {
        this.execDateTime = execDateTime;
    }


    // конструктор по умолчанию
    public IntermediateSensorData() {
    }

    // конструктор инициализирующий
    public IntermediateSensorData(int id, SensorEntity sensor, double value, Date execDateTime) {
        this.setId(id);
        this.setSensor(sensor);
        this.setValue(value);
        this.setExecDateTime(execDateTime);
    }


    // получить название таблицы
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    // загрузка сущности
    @SuppressLint("Range")
    public static IntermediateSensorData loadEntity(Context context, Cursor cursor) {

        var sensorsRepository = new SensorsRepository(context);

        return new IntermediateSensorData(
                cursor.getInt(cursor.getColumnIndex(ID)),
                sensorsRepository.getById(cursor.getInt(cursor.getColumnIndex(SENSOR_ID))),
                cursor.getDouble(cursor.getColumnIndex(VALUE)),
                Utils.getDateTime(cursor.getString(cursor.getColumnIndex(EXEC_DATE_TIME)))
        );
    }
}
