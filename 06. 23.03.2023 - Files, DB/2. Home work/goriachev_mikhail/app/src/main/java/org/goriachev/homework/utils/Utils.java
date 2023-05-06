package org.goriachev.homework.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import org.goriachev.homework.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;


// Класс Утилиты
public class Utils {

    public static Random random = new Random();

    // случайное целочисленное число
    public static int getInt(int min, int max) {
        return random.ints(min, max).findFirst().getAsInt();
    }

    // генерация boolean
    public static boolean getBoolean() {
        return (random.nextInt() & 1) == 1;
    }

    // случайное вещественное число
    public static double getDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    // случайное вещественное число с генерацией 0
    public static double getDoubleWithZero(double min, double max) {
        double num = min + (max - min) * random.nextDouble();

        return num < 1d && num > -1d ? 0d : num;
    }

    // получить случайный элемент массива
    public static <T> T getItem(T[] array) {
        return array[Utils.getInt(0, array.length)];
    }

    // получить случайный элемент списка
    public static <T> T getItem(List<T> list) {
        return list.get(Utils.getInt(0, list.size()));
    }

    // сравнить два значения double
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < 1e-7;
    }

    // заполнение массива случайными вещественными числами
    public static double[] fillArray(double[] array, double min, double max) {
        for (int i = 0; i < array.length; i++) {
            array[i] = getDouble(min, max);
        }

        return array;
    }


    // получить случайный элемент массива строк
    public static String getItem(String[] array) {
        return array[getInt(0, array.length)];
    }

    // получить фамилию и имя
    public static List<String[]> people = Arrays.asList(
            new String[]{"Корчагина", "Софья", "Елисеевна"},
            new String[]{"Ефремов", "Эмиль", "Маркович"},
            new String[]{"Денисова", "Марьяна", "Михайловна"},
            new String[]{"Романова", "Алиса", "Егоровна"},
            new String[]{"Коршунова", "Александра", "Александровна"},
            new String[]{"Попова", "Елизавета", "Данииловна"},
            new String[]{"Дубровин", "Сергей", "Леонидович"},
            new String[]{"Кузин", "Фёдор", "Максимович"},
            new String[]{"Антонов", "Владислав", "Дмитриевич"},
            new String[]{"Кондратова", "Мария", "Макаровна"},
            new String[]{"Костина", "Анна", "Андреевна"},
            new String[]{"Родионов", "Дмитрий", "Михайлович"},
            new String[]{"Крючкова", "Амелия", "Владимировна"},
            new String[]{"Баженов", "Никита", "Матвеевич"},
            new String[]{"Потапова", "Ника", "Михайловна"},
            new String[]{"Иванова", "Виктория", "Петровна"},
            new String[]{"Алексеев", "Пётр", "Денисович"},
            new String[]{"Фирсов", "Михаил", "Даниилович"},
            new String[]{"Панкова", "Екатерина", "Ивановна"},
            new String[]{"Попова", "Варвара", "Ярославовна"},
            new String[]{"Зайцев", "Александр", "Никитич"},
            new String[]{"Смирнов", "Александр", "Тихонович"},
            new String[]{"Серова", "Алиса", "Юрьевна"},
            new String[]{"Орлов", "Кирилл", "Алексеевич"},
            new String[]{"Виноградова", "Сафия", "Константиновна"},
            new String[]{"Федотов", "Максим", "Иванович"},
            new String[]{"Титов", "Савва", "Артёмович"},
            new String[]{"Алешин", "Марк", "Матвеевич"},
            new String[]{"Романов", "Матвей", "Павлович"},
            new String[]{"Акимова", "Алия", "Александровна"}
    );

    // получить отформатированную дату для html
    public static String dateHtmlToFormat(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    // получить отформатированную дату
    public static String dateToFormat(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    // получить отформатированную дату и время
    public static String dateTimeToFormat(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy hh:mm:ss").format(date);
    }

    // форматирование числа double
    public static String doubleFormat(double value) {
        return String.format(Locale.UK, "%.4f", value);
    }

    // форматирование числа double
    public static String doubleFormat(double value, int precision) {
        return String.format(Locale.UK, "%." + precision + "f", value);
    }

    // форматирование числа int
    public static String intFormat(int value) {
        return String.format(Locale.UK, "%d", value);
    }

    // форматирование числа long
    public static String longFormat(long value) {
        return String.format(Locale.UK, "%d", value);
    }

    // установка картинки
    public static void setViewImage(ImageView imageView, String fileName, Context context) {
        try (InputStream stream = context.getAssets().open(fileName)) {
            Drawable drawable = Drawable.createFromStream(stream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            Snackbar snackbar = Snackbar.make(imageView, "Ошибка чтения файла изображения",
                    Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Закрыть", v -> {
            });
            snackbar.show();
        }
    }

    // вывод снейкбара
    public static void showSnakeBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message,
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Закрыть", v -> {
        });
        snackbar.show();
    }

    // проверка валидации по предикату и установка ошибки
    public static boolean isValidEditText(EditText editText, Predicate<String> predicate, String errorMessage, Context context) {
        if (!predicate.test(editText.getText().toString())) {
            editText.setError(errorMessage);
            editText.getBackground()
                    .setColorFilter(context.getResources().getColor(R.color.red),
                            PorterDuff.Mode.SRC_ATOP);
            return false;
        }

        editText.getBackground()
                .setColorFilter(context.getResources().getColor(R.color.black),
                        PorterDuff.Mode.SRC_ATOP);
        return true;
    }

    // добавление обработчика события изменения текста
    public static void addTextChangedListener(EditText editText, Consumer<String> onTextChangedHandler) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onTextChangedHandler.accept(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // безопасный парсинг double
    public static Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception ex) {
            return null;
        }
    }

    // безопасный парсинг int
    public static Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return null;
        }
    }

    // получит строковое представление boolean ("да"/"нет")
    public static String getBooleanToString(boolean value) {
        return value ? "да" : "нет";
    }
}
