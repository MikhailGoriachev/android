package org.goriachev.homework.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import org.goriachev.homework.entities.SensorEntity;
import org.goriachev.homework.utils.Utils;

// Класс Репозиторий для Датчиков
public class SensorsRepository extends SensorsDataBaseRepository<SensorEntity> {

    // конструктор
    public SensorsRepository(Context context) {
        super(context);
    }

    // поиск по идентификатору (типу) датчика
    @Nullable
    public SensorEntity findByIdentifierSensor(int identifier) {
        return where("identifier_sensor = ?", new String[]{Utils.intFormat(identifier)})
                .stream()
                .findFirst()
                .orElse(null);
    }

    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                SensorEntity.ID,
                SensorEntity.IDENTIFIER_SENSOR,
                SensorEntity.NAME,
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return SensorEntity.TABLE_NAME;
    }

    // получить данные из объекта в виде контента
    @Override
    protected ContentValues getContentValues(SensorEntity item) {
        var content = new ContentValues();

        content.put(SensorEntity.ID, item.getId());
        content.put(SensorEntity.IDENTIFIER_SENSOR, item.getIdentifierSensor());
        content.put(SensorEntity.NAME, item.getName());

        return content;
    }

    // удаление записи по id
    @Override
    public long delete(long id) {
        return super.delete(id);
    }

    // удаление записи
    @Override
    public long delete(SensorEntity item) {
        return super.delete(item);
    }

    // очистка данных таблицы
    @Override
    public long clear() {
        return super.clear();
    }

    // использование метода загрузки модели
    @Override
    protected SensorEntity loadEntity(Context context, Cursor cursor) {
        return SensorEntity.loadEntity(context, cursor);
    }
}
