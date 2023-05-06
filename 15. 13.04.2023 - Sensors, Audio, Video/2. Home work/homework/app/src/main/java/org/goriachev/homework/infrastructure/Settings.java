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

    // датчик ускорения
    public static final String IS_ACCELEROMETER_SENSOR_KEY = "is_accelerometer_sensor";

    public static boolean isAccelerometerSensor() {
        return preferences.getBoolean(IS_ACCELEROMETER_SENSOR_KEY, true);
    }

    // датчик приближения
    public static final String IS_PROXIMITY_SENSOR_KEY = "is_proximity_sensor";

    public static boolean isProximitySensor() {
        return preferences.getBoolean(IS_PROXIMITY_SENSOR_KEY, true);
    }

    // датчик освещённости
    public static final String IS_LIGHT_SENSOR_KEY = "is_light_sensor";

    public static boolean isLightSensor() {
        return preferences.getBoolean(IS_LIGHT_SENSOR_KEY, true);
    }
}
