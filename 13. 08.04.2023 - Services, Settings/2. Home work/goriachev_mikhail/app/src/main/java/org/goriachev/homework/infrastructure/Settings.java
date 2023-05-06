package org.goriachev.homework.infrastructure;

import android.content.SharedPreferences;

public class Settings {

    public static SharedPreferences preferences;

    // получить едитора для изменения настроек
    public static SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    // имя JSON-файла для хранения сообщений
    public static final String PERIOD_KEY = "period";

    public static int getPeriod() {
        return preferences.getInt(PERIOD_KEY, 3);
    }

    // период запуска сервиса N, в секундах
    public static final String DELAY_KEY = "delay";

    public static int getDelay() {
        return preferences.getInt(DELAY_KEY, 2);
    }

    // время задержки запуска сервиса M, в секундах
    public static final String MESSAGES_FILE_NAME_KEY = "messenger_file_name";

    public static String getMessagesFileName() {
        return preferences.getString(MESSAGES_FILE_NAME_KEY, "messages.json");
    }

    // признак восстановления полей диалога ввода/редактирования полей сообщения
    public static final String IS_RESTORE_KEY = "is_restore_fields";

    public static boolean isRestoreKey() {
        return preferences.getBoolean(IS_RESTORE_KEY, true);
    }


    // поле получателя
    public static final String RECEIVER_KEY = "receiver";

    public static String getReceiver() {
        return preferences.getString(RECEIVER_KEY, "");
    }

    // поле отправителя
    public static final String SENDER_KEY = "sender";

    public static String getSender() {
        return preferences.getString(SENDER_KEY, "");
    }

    // поле темы
    public static final String SUBJECT_KEY = "subject";

    public static String getSubject() {
        return preferences.getString(SUBJECT_KEY, "");
    }

    // поле текста
    public static final String TEXT_KEY = "text";

    public static String getText() {
        return preferences.getString(TEXT_KEY, "");
    }

    // поле признака вложения
    public static final String IS_ATTACH_KEY = "is_attach";

    public static boolean isAttach() {
        return preferences.getBoolean(IS_ATTACH_KEY, true);
    }
}
