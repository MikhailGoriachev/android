package org.goriachev.homework.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Speciality;


// Класс Репозиторий для специальностей
public class SpecialitiesRepository extends PolyclinicBaseRepository<Speciality> {

    // конструктор
    public SpecialitiesRepository(Context context) {
        super(context);
    }


    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                Speciality.ID,
                Speciality.SPECIALITY_NAME,
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return Speciality.TABLE_NAME;
    }

    // получить данные из объекта в виде контента
    @Override
    protected ContentValues getContentValues(Speciality item) {
        var content = new ContentValues();

        content.put(Speciality.ID, item.getId());
        content.put(Speciality.SPECIALITY_NAME, item.getSpecialityName());

        return content;
    }

    // использование метода загрузки модели
    @Override
    protected Speciality loadEntity(Context context, Cursor cursor) {
        return Speciality.loadEntity(context, cursor);
    }
}
