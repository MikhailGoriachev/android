package org.goriachev.homework.entities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.infrastructure.Entity;
import org.goriachev.homework.repositories.SensorsRepository;
import org.goriachev.homework.utils.Utils;

import java.util.Date;

// Класс Результаты обработки
public class Result extends Entity<Result> {

    // название таблицы
    public static final String TABLE_NAME = "results";

    // поля
    public static final String SENSOR_ID = "sensor_id";
    public static final String AMOUNT = "amount";
    public static final String MIN_VALUE = "min_value";
    public static final String AVG_VALUE = "avg_value";
    public static final String MAX_VALUE = "max_value";
    public static final String START_DATE_TIME = "start_date_time";
    public static final String PROCESS_DATA_DATE_TIME = "process_data_date_time";

    // датчик
    private SensorEntity sensor;

    public SensorEntity getSensor() {
        return sensor;
    }

    public void setSensor(SensorEntity sensor) {
        this.sensor = sensor;
    }

    // количество
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // минимальное значение
    private double minValue;

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    // среднее значение
    private double avgValue;

    public double getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(double avgValue) {
        this.avgValue = avgValue;
    }

    // максимальное значение
    private double maxValue;

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    // дата и время работы начала сбора данных
    private Date startDateTime;

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    // дата и время обработки данных
    private Date processDataDateTime;

    public Date getProcessDataDateTime() {
        return processDataDateTime;
    }

    public void setProcessDataDateTime(Date processDataDateTime) {
        this.processDataDateTime = processDataDateTime;
    }


    // конструктор по умолчанию
    public Result() {
    }

    // конструктор инициализирующий

    public Result(int id, SensorEntity sensor, int amount, double minValue, double avgValue, double maxValue, Date startDateTime, Date processDataDateTime) {
        this.setId(id);
        this.setSensor(sensor);
        this.setAmount(amount);
        this.setMinValue(minValue);
        this.setAvgValue(avgValue);
        this.setMaxValue(maxValue);
        this.setStartDateTime(startDateTime);
        this.setProcessDataDateTime(processDataDateTime);
    }

    // получить название таблицы
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    // загрузка сущности
    @SuppressLint("Range")
    public static Result loadEntity(Context context, Cursor cursor) {

        var sensorsRepository = new SensorsRepository(context);
        return new Result(
                cursor.getInt(cursor.getColumnIndex(ID)),
                sensorsRepository.getById(cursor.getInt(cursor.getColumnIndex(SENSOR_ID))),
                cursor.getInt(cursor.getColumnIndex(AMOUNT)),
                cursor.getDouble(cursor.getColumnIndex(MIN_VALUE)),
                cursor.getDouble(cursor.getColumnIndex(AVG_VALUE)),
                cursor.getDouble(cursor.getColumnIndex(MAX_VALUE)),
                Utils.getDateTime(cursor.getString(cursor.getColumnIndex(START_DATE_TIME))),
                Utils.getDateTime(cursor.getString(cursor.getColumnIndex(PROCESS_DATA_DATE_TIME)))
        );
    }
}
