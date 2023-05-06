package org.goriachev.homework.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.IntermediateSensorData;
import org.goriachev.homework.entities.Result;
import org.goriachev.homework.utils.Utils;

import java.util.List;

// Класс Репозиторий для Промежуточных данных датчиков
public class IntermediateSensorDataRepository extends SensorsDataBaseRepository<IntermediateSensorData> {

    // конструктор
    public IntermediateSensorDataRepository(Context context) {
        super(context);
    }

    // создание записей результата
    public List<Result> getResults() {
        return rawQuery(
                "select " +
                        "-1 as _id," +
                        "sensor_id," +
                        "count(sensor_id)   as amount," +
                        "min(value) as min_value," +
                        "avg(value) as avg_value," +
                        "max(value) as max_value," +
                        "min(exec_date_time) as start_date_time," +
                        "datetime('now', 'localtime') as process_data_date_time " +
                        "from intermediate_sensor_data " +
                        "group by sensor_id",
                (c) -> Result.loadEntity(context, c)
        );
    }

    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                IntermediateSensorData.ID,
                IntermediateSensorData.SENSOR_ID,
                IntermediateSensorData.VALUE,
                IntermediateSensorData.EXEC_DATE_TIME
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return IntermediateSensorData.TABLE_NAME;
    }

    // получить данные из объекта в виде контента
    @Override
    protected ContentValues getContentValues(IntermediateSensorData item) {
        var content = new ContentValues();

        content.put(IntermediateSensorData.ID, item.getId());
        content.put(IntermediateSensorData.SENSOR_ID, item.getSensor().getId());
        content.put(IntermediateSensorData.VALUE, item.getValue());
        content.put(IntermediateSensorData.EXEC_DATE_TIME, Utils.dateTimeHtmlToFormat(item.getExecDateTime()));

        return content;
    }

    // удаление записи по id
    @Override
    public long delete(long id) {
        return super.delete(id);
    }

    // удаление записи
    @Override
    public long delete(IntermediateSensorData item) {
        return super.delete(item);
    }

    // удаление всех записей
    @Override
    public long deleteAll() {
        return super.deleteAll();
    }

    // очистка данных таблицы
    @Override
    public long clear() {
        return super.clear();
    }

    // использование метода загрузки модели
    @Override
    protected IntermediateSensorData loadEntity(Context context, Cursor cursor) {
        return IntermediateSensorData.loadEntity(context, cursor);
    }
}
