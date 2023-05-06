package org.goriachev.homework.repositories;

import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Patient;
import org.goriachev.homework.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

// Класс Репозиторий для пациентов
public class PatientsRepository extends PolyclinicBaseRepository<Patient> {

    // название таблицы
    public static final String TABLE_NAME = "view_patients";

    // поля
    public static final String PERSON_ID = "id_person";
    public static final String PERSON_SURNAME = "person_surname";
    public static final String PERSON_NAME = "person_name";
    public static final String PERSON_PATRONYMIC = "person_patronymic";
    public static final String BORN_DATE = "born_date";
    public static final String ADDRESS = "address";
    public static final String PASSPORT = "passport";


    // конструктор
    public PatientsRepository(Context context) {
        super(context);
    }


    // пациенты с фамилиями, начинающимися на заданную параметром последовательность букв
    public List<Patient> getAllBySurname(String surname) {
        return where(PERSON_SURNAME + " like ?", new String[]{surname + "%"});
    }

    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                ID,
                PERSON_ID,
                PERSON_SURNAME,
                PERSON_NAME,
                PERSON_PATRONYMIC,
                BORN_DATE,
                ADDRESS,
                PASSPORT
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return TABLE_NAME;
    }

    // преобразовать в список объектов
    @Override
    List<Patient> toList(Cursor cursor) {

        try (cursor) {
            var list = new ArrayList<Patient>();

            if (cursor.moveToFirst()) {

                var id = cursor.getColumnIndex(ID);
                var patientPersonId = cursor.getColumnIndex(PERSON_ID);
                var patientPersonSurname = cursor.getColumnIndex(PERSON_SURNAME);
                var patientPersonName = cursor.getColumnIndex(PERSON_NAME);
                var patientPersonPatronymic = cursor.getColumnIndex(PERSON_PATRONYMIC);
                var patientBornDate = cursor.getColumnIndex(BORN_DATE);
                var patientAddress = cursor.getColumnIndex(ADDRESS);
                var patientPassport = cursor.getColumnIndex(PASSPORT);

                do {
                    list.add(new Patient(
                            cursor.getInt(id),
                            cursor.getInt(patientPersonId),
                            cursor.getString(patientPersonSurname),
                            cursor.getString(patientPersonName),
                            cursor.getString(patientPersonPatronymic),
                            Utils.getDate(cursor.getString(patientBornDate)),
                            cursor.getString(patientAddress),
                            cursor.getString(patientPassport)
                    ));

                } while (cursor.moveToNext());
            }

            return list;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
