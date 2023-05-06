package org.goriachev.homework.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Result;
import org.goriachev.homework.utils.Utils;

// Класс Репозиторий для Результатов обработки
public class ResultsRepository extends SensorsDataBaseRepository<Result> {

    // конструктор
    public ResultsRepository(Context context) {
        super(context);
    }

    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                Result.ID,
                Result.SENSOR_ID,
                Result.AMOUNT,
                Result.MIN_VALUE,
                Result.AVG_VALUE,
                Result.MAX_VALUE,
                Result.START_DATE_TIME,
                Result.PROCESS_DATA_DATE_TIME
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return Result.TABLE_NAME;
    }

    // получить данные из объекта в виде контента
    @Override
    protected ContentValues getContentValues(Result item) {
        var content = new ContentValues();

        content.put(Result.ID, item.getId());
        content.put(Result.SENSOR_ID, item.getSensor().getId());
        content.put(Result.AMOUNT, item.getAmount());
        content.put(Result.MIN_VALUE, item.getMinValue());
        content.put(Result.AVG_VALUE, item.getAvgValue());
        content.put(Result.MAX_VALUE, item.getMaxValue());
        content.put(Result.START_DATE_TIME, Utils.dateTimeHtmlToFormat(item.getStartDateTime()));
        content.put(Result.PROCESS_DATA_DATE_TIME, Utils.dateTimeHtmlToFormat(item.getProcessDataDateTime()));

        return content;
    }

    // удаление записи по id
    @Override
    public long delete(long id) {
        return super.delete(id);
    }

    // удаление записи
    @Override
    public long delete(Result item) {
        return super.delete(item);
    }

    // очистка данных таблицы
    @Override
    public long clear() {
        return super.clear();
    }

    // использование метода загрузки модели
    @Override
    protected Result loadEntity(Context context, Cursor cursor) {
        return Result.loadEntity(context, cursor);
    }
}
