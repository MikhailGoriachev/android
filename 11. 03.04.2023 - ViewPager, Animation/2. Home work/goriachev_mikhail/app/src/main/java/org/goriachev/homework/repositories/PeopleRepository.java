package org.goriachev.homework.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Person;


// Класс Репозиторий для персон
public class PeopleRepository extends PolyclinicBaseRepository<Person> {

    // конструктор
    public PeopleRepository(Context context) {
        super(context);
    }


    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                Person.ID,
                Person.SURNAME,
                Person.NAME,
                Person.PATRONYMIC
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return Person.TABLE_NAME;
    }

    // получить данные из объекта в виде контента
    @Override
    protected ContentValues getContentValues(Person item) {
        var content = new ContentValues();

        content.put(Person.ID, item.getId());
        content.put(Person.SURNAME, item.getSurname());
        content.put(Person.NAME, item.getName());
        content.put(Person.PATRONYMIC, item.getPatronymic());

        return content;
    }

    // использование метода загрузки модели
    @Override
    protected Person loadEntity(Context context, Cursor cursor) {
        return Person.loadEntity(context, cursor);
    }
}
