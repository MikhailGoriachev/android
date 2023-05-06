package org.goriachev.homework.repositories;

import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Person;

import java.util.ArrayList;
import java.util.List;


// Класс Репозиторий для персон
public class PeopleRepository extends PolyclinicBaseRepository<Person> {

    // название таблицы
    public static final String TABLE_NAME = "persons";

    // поля
    public static final String SURNAME = "surname";
    public static final String NAME = "name";
    public static final String PATRONYMIC = "patronymic";


    // конструктор
    public PeopleRepository(Context context) {
        super(context);
    }


    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                ID,
                SURNAME,
                NAME,
                PATRONYMIC
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return TABLE_NAME;
    }

    // преобразовать в список объектов
    @Override
    List<Person> toList(Cursor cursor) {

        try (cursor) {
            var list = new ArrayList<Person>();

            if (cursor.moveToFirst()) {

                var id = cursor.getColumnIndex(ID);
                var surname = cursor.getColumnIndex(SURNAME);
                var name = cursor.getColumnIndex(NAME);
                var patronymic = cursor.getColumnIndex(PATRONYMIC);

                do {
                    list.add(new Person(
                            cursor.getInt(id),
                            cursor.getString(surname),
                            cursor.getString(name),
                            cursor.getString(patronymic)
                    ));

                } while (cursor.moveToNext());
            }

            return list;
        }
    }
}
