package org.goriachev.homework.repositories;

import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Speciality;

import java.util.ArrayList;
import java.util.List;


// Класс Репозиторий для специальностей
public class SpecialitiesRepository extends PolyclinicBaseRepository<Speciality> {

    // название таблицы
    public static final String TABLE_NAME = "specialities";

    // поля
    public static final String SPECIALITY_NAME = "name";


    // конструктор
    public SpecialitiesRepository(Context context) {
        super(context);
    }


    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                ID,
                SPECIALITY_NAME,
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return TABLE_NAME;
    }

    // преобразовать в список объектов
    @Override
    List<Speciality> toList(Cursor cursor) {

        try (cursor) {
            var list = new ArrayList<Speciality>();

            if (cursor.moveToFirst()) {

                var id = cursor.getColumnIndex(ID);
                var specialityName = cursor.getColumnIndex(SPECIALITY_NAME);

                do {
                    list.add(new Speciality(
                            cursor.getInt(id),
                            cursor.getString(specialityName)
                    ));

                } while (cursor.moveToNext());
            }

            return list;
        }
    }
}
