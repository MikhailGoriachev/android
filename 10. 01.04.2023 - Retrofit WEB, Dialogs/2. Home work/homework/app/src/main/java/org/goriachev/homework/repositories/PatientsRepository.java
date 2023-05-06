package org.goriachev.homework.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Patient;
import org.goriachev.homework.utils.Utils;

import java.util.List;

// Класс Репозиторий для пациентов
public class PatientsRepository extends PolyclinicBaseRepository<Patient> {

    // конструктор
    public PatientsRepository(Context context) {
        super(context);
    }


    // пациенты с фамилиями, начинающимися на заданную параметром последовательность букв
    public List<Patient> getAllBySurname(String surname) {
        surname += "%";
        return rawQuery(
                "select * from view_patients where person_surname like '" + surname + "'",
                cursor -> loadEntity(context, cursor)
        );
    }

    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                Patient.ID,
                Patient.PERSON_ID,
                Patient.BORN_DATE,
                Patient.ADDRESS,
                Patient.PASSPORT
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return Patient.TABLE_NAME;
    }

    // получить данные из объекта в виде контента
    @Override
    protected ContentValues getContentValues(Patient item) {
        var content = new ContentValues();

        content.put(Patient.ID, item.getId());
        content.put(Patient.PERSON_ID, item.getPerson().getId());
        content.put(Patient.ADDRESS, item.getAddress());
        content.put(Patient.BORN_DATE, Utils.dateHtmlToFormat(item.getBornDate()));
        content.put(Patient.PASSPORT, item.getPassport());

        return content;
    }

    // использование метода загрузки модели
    @Override
    protected Patient loadEntity(Context context, Cursor cursor) {
        return Patient.loadEntity(context, cursor);
    }
}
