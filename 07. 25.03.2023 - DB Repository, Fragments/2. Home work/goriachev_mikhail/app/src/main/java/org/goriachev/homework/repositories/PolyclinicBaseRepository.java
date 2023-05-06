package org.goriachev.homework.repositories;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import org.goriachev.homework.middleware.PolyclinicDatabaseHelper;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


// Класс Базовый репозиторий
public abstract class PolyclinicBaseRepository<T> {

    // поле id
    public static final String ID = "_id";

    // хэлпер для доступа к базе данных
    private PolyclinicDatabaseHelper helper;

    // объект для работы с базой данных
    protected SQLiteDatabase database;


    // конструктор
    public PolyclinicBaseRepository(Context context) {
        helper = new PolyclinicDatabaseHelper(context.getApplicationContext());
        open();
    }


    // получить названия столбцов
    abstract String[] getColumnsName();

    // получить название таблицы
    abstract String getTableName();

    // открытие подключения к базе данных
    public PolyclinicBaseRepository<T> open() {
        database = helper.open();
        return this;
    }

    // закрытие подключения
    public void close() {
        helper.close();
    }

    // количество элементов
    public long getCount() {
        return DatabaseUtils.queryNumEntries(database, getTableName());
    }

    // получить все записи (курсор)
    public List<T> getAll() {
        return toList(database.query(getTableName(), getColumnsName(), null,
                null, null, null, null));
    }

    // получить запись по id
    public T getById(int id) {
        return toList(database.query(getTableName(), getColumnsName(), "id = ?",
                new String[]{Utils.intFormat(id)}, null, null, null))
                .stream()
                .findFirst()
                .orElse(null);
    }

    // выборка по условию
    public List<T> where(String selection, String[] args) {
        return toList(database.query(getTableName(), getColumnsName(), selection,
                args, null, null, null));
    }

    // сортировка по полю
    public List<T> orderBy(String orderBy) {
        return toList(database.query(getTableName(), getColumnsName(), null,
                null, null, null, orderBy));
    }

    // группировка по полям
    public <V> List<V> rawQuery(String sql, Function<Cursor, V> parseObject) {

        var cursor = database.rawQuery(sql, null);

        try (cursor) {
            var list = new ArrayList<V>();

            if (cursor.moveToFirst()) {
                do {
                    list.add(parseObject.apply(cursor));

                } while (cursor.moveToNext());
            }

            return list;
        }
    }

    // преобразовать в список объектов
    abstract List<T> toList(Cursor cursor);
}
