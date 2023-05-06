package org.goriachev.homework.repositories;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;

import org.goriachev.homework.entities.Edition;
import org.goriachev.homework.contracts.PeriodicalsContract;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.List;

// Репозиторий изданий
public class EditionsRepository {

    private static final String[] columns = {
            PeriodicalsContract.Columns._ID,
            PeriodicalsContract.Columns.INDEX,
            PeriodicalsContract.Columns.PUBLICATION_TYPE,
            PeriodicalsContract.Columns.NAME,
            PeriodicalsContract.Columns.PRICE,
            PeriodicalsContract.Columns.SUBSCRIBE_DATE,
            PeriodicalsContract.Columns.DURATION
    };

    // все записи
    public static List<Edition> getAll(ContentResolver contentResolver) {
        var cursor = contentResolver.query(
                PeriodicalsContract.CONTENT_URI,
                columns,
                null,
                null,
                null
        );

        var items = getListFromCursor(cursor);

        cursor.close();

        return items;
    }

    // запись по id
    public static Edition getById(long id, ContentResolver contentResolver) {
        var cursor = contentResolver.query(
                PeriodicalsContract.CONTENT_URI,
                columns,
                PeriodicalsContract.Columns._ID + " = ?",
                new String[]{Utils.longFormat(id)},
                null
        );

        cursor.moveToFirst();

        var item = getItemFromCursor(cursor);

        cursor.close();

        return item;
    }

    // добавление
    public static void create(@NonNull Edition edition, ContentResolver contentResolver) {

        var values = getContentValuesFromItem(edition);
        values.remove("_id");

        contentResolver.insert(PeriodicalsContract.CONTENT_URI, values);
    }

    // редактирование
    public static void update(@NonNull Edition edition, ContentResolver contentResolver) {
        contentResolver.update(
                PeriodicalsContract.CONTENT_URI,
                getContentValuesFromItem(edition),
                PeriodicalsContract.Columns._ID + " = ?",
                new String[]{Utils.longFormat(edition.getId())}
        );
    }

    // удаление по id
    public static void deleteById(long id, ContentResolver contentResolver) {
        contentResolver.delete(
                PeriodicalsContract.CONTENT_URI,
                PeriodicalsContract.Columns._ID + " = ?",
                new String[]{Utils.longFormat(id)}
        );
    }

    // получить значения из модели
    public static ContentValues getContentValuesFromItem(Edition edition) {
        var values = new ContentValues();

        values.put(PeriodicalsContract.Columns._ID, edition.getId());
        values.put(PeriodicalsContract.Columns.INDEX, edition.getIndex());
        values.put(PeriodicalsContract.Columns.PUBLICATION_TYPE, edition.getPublicationType());
        values.put(PeriodicalsContract.Columns.NAME, edition.getName());
        values.put(PeriodicalsContract.Columns.PRICE, edition.getPrice());
        values.put(PeriodicalsContract.Columns.SUBSCRIBE_DATE, Utils.dateHtmlToFormat(edition.getSubscribeDate()));
        values.put(PeriodicalsContract.Columns.DURATION, edition.getDuration());

        return values;
    }

    // получить запись из курсора
    @SuppressLint("Range")
    public static Edition getItemFromCursor(@NonNull Cursor cursor) {
        try {
            var i = cursor.getColumnIndex(PeriodicalsContract.Columns._ID);

            return new Edition(
                    cursor.getLong(cursor.getColumnIndex(PeriodicalsContract.Columns._ID)),
                    cursor.getString(cursor.getColumnIndex(PeriodicalsContract.Columns.INDEX)),
                    cursor.getString(cursor.getColumnIndex(PeriodicalsContract.Columns.PUBLICATION_TYPE)),
                    cursor.getString(cursor.getColumnIndex(PeriodicalsContract.Columns.NAME)),
                    cursor.getInt(cursor.getColumnIndex(PeriodicalsContract.Columns.PRICE)),
                    Utils.getDate(cursor.getString(cursor.getColumnIndex(PeriodicalsContract.Columns.SUBSCRIBE_DATE))),
                    cursor.getInt(cursor.getColumnIndex(PeriodicalsContract.Columns.DURATION))
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // получить список записей из курсора
    public static List<Edition> getListFromCursor(@NonNull Cursor cursor) {
        List<Edition> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(getItemFromCursor(cursor));
        }

        return list;
    }
}
