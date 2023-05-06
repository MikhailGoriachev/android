package org.goriachev.homework.databaseHelpers;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// Класс Кастомный хэлпер для работы с базой данных
public class PeriodicalsDatabaseHelper extends SQLiteOpenHelper {

    // путь к базе данных
    private final String DB_PATH;

    // название файла базы данных
    private static final String DB_NAME = "periodicals.db";

    // версия базы данных
    private static final int SCHEMA = 1;

    // контекст активности
    private final Context context;


    // конструктор инициализирующий
    public PeriodicalsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.context = context;

        DB_PATH = context.getFilesDir().getPath() + "/" + DB_NAME;
        createDatabase();
    }


    // создание
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    // обновление
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    // кастомное создание базы данных
    public void createDatabase() {

        // если база данных уже существует
        if (new File(DB_PATH).exists()) return;

        // разрешение записи
        this.getReadableDatabase();

        // копирование локальной базы данных
        try (InputStream input = context.getAssets().open(DB_NAME);
             OutputStream output = new FileOutputStream(DB_PATH)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = input.read(buffer)) > 0)
                output.write(buffer, 0, length);

            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // открыть подключение к базе данных
    public SQLiteDatabase open() {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
