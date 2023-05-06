package org.goriachev.homework.repositories;

import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Doctor;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.List;

// Класс Репозиторий для докторов
public class DoctorsRepository extends PolyclinicBaseRepository<Doctor> {

    // название таблицы
    public static final String TABLE_NAME = "view_doctors";

    // поля
    public static final String PERSON_ID = "id_person";
    public static final String PERSON_SURNAME = "person_surname";
    public static final String PERSON_NAME = "person_name";
    public static final String PERSON_PATRONYMIC = "person_patronymic";
    public static final String SPECIALITY_ID = "id_speciality";
    public static final String SPECIALITY_NAME = "speciality_name";
    public static final String PRICE = "price";
    public static final String PERCENT = "percent";


    // конструктор
    public DoctorsRepository(Context context) {
        super(context);
    }


    // доктора для которых значение в поле Процент отчисления на зарплату, больше заданного значения
    public List<Doctor> getAllByPercentOver(double percent) {
        return where(PERCENT + " > ?", new String[] {Utils.doubleFormat(percent)});
    }

    // доктора специальность которых задана параметром
    public List<Doctor> getAllBySpeciality(String speciality) {
        return where(SPECIALITY_NAME + " like ?", new String[] {speciality});
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
                SPECIALITY_ID,
                SPECIALITY_NAME,
                PRICE,
                PERCENT
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return TABLE_NAME;
    }

    // преобразовать в список объектов
    @Override
    List<Doctor> toList(Cursor cursor) {

        try (cursor) {
            var list = new ArrayList<Doctor>();

            if (cursor.moveToFirst()) {

                var id = cursor.getColumnIndex(ID);
                var doctorPersonId = cursor.getColumnIndex(PERSON_ID);
                var doctorPersonSurname = cursor.getColumnIndex(PERSON_SURNAME);
                var doctorPersonName = cursor.getColumnIndex(PERSON_NAME);
                var doctorPersonPatronymic = cursor.getColumnIndex(PERSON_PATRONYMIC);
                var doctorSpecialityId = cursor.getColumnIndex(SPECIALITY_ID);
                var doctorSpecialityName = cursor.getColumnIndex(SPECIALITY_NAME);
                var doctorPrice = cursor.getColumnIndex(PRICE);
                var doctorPercent = cursor.getColumnIndex(PERCENT);

                do {
                    list.add(new Doctor(
                            cursor.getInt(id),
                            cursor.getInt(doctorPersonId),
                            cursor.getString(doctorPersonSurname),
                            cursor.getString(doctorPersonName),
                            cursor.getString(doctorPersonPatronymic),
                            cursor.getInt(doctorSpecialityId),
                            cursor.getString(doctorSpecialityName),
                            cursor.getInt(doctorPrice),
                            cursor.getInt(doctorPercent)
                    ));

                } while (cursor.moveToNext());
            }

            return list;
        }
    }
}
