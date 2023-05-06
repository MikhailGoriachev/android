package org.goriachev.homework.repositories;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import org.goriachev.homework.databaseHelpers.SensorsDataDatabaseHelper;
import org.goriachev.homework.infrastructure.Entity;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


// Класс Базовый репозиторий
public abstract class SensorsDataBaseRepository<T extends Entity<T>> {

    // хэлпер для доступа к базе данных
    private final SensorsDataDatabaseHelper helper;

    // объект для работы с базой данных
    protected SQLiteDatabase database;

    // контекст активности
    protected Context context;


    // конструктор
    public SensorsDataBaseRepository(Context context) {
        this.context = context.getApplicationContext();
        helper = new SensorsDataDatabaseHelper(this.context);
        open();
    }


    // получить названия столбцов
    abstract String[] getColumnsName();

    // получить название таблицы
    abstract String getTableName();

    // открытие подключения к базе данных
    public SensorsDataBaseRepository<T> open() {
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

    // добавление записи
    public long store(T item) {
        var content = getContentValues(item);
        content.remove(Entity.ID);
        return database.insert(getTableName(), null, content);
    }

    // добавление записей
    public void store(List<T> items) {
        items.forEach(this::store);
    }

    // изменение записи
    public long update(T item) {
        return database.update(
                getTableName(),
                getContentValues(item),
                Entity.ID + " = ?",
                new String[]{Utils.intFormat(item.getId())}
        );
    }

    // удаление записи по id
    protected long delete(long id) {
        return database.delete(
                getTableName(), Entity.ID + " = ?",
                new String[]{Utils.longFormat(id)}
        );
    }

    // удаление всех записей
    protected long deleteAll() {
        return database.delete(getTableName(), null, null);
    }

    // удаление записи
    protected long delete(T item) {
        return database.delete(
                getTableName(), Entity.ID + " = ?",
                new String[]{Utils.intFormat(item.getId())}
        );
    }

    // очистка данных таблицы
    protected long clear() {
        return database.delete(getTableName(), null, null);
    }

    // получить все записи (курсор)
    public List<T> getAll() {
        return toList(database.query(getTableName(), getColumnsName(), null,
                null, null, null, null));
    }

    // получить запись по id
    public T getById(long id) {
        return toList(database.query(getTableName(), getColumnsName(), "_id = ?",
                new String[]{Utils.longFormat(id)}, null, null, null))
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

    // сырой запрос
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

    // получить данные из объекта в виде контента
    abstract ContentValues getContentValues(T item);

    // преобразовать в список объектов
    protected List<T> toList(Cursor cursor) {
        try (cursor) {
            var list = new ArrayList<T>();

            if (cursor.moveToFirst()) {
                do {
                    list.add(loadEntity(context, cursor));

                } while (cursor.moveToNext());
            }

            return list;
        }
    }

    // использование метода загрузки модели
    protected abstract T loadEntity(Context context, Cursor cursor);
}
