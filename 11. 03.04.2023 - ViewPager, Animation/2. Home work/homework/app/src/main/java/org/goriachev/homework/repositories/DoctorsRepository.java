package org.goriachev.homework.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Doctor;
import org.goriachev.homework.utils.Utils;

import java.util.List;

// Класс Репозиторий для докторов
public class DoctorsRepository extends PolyclinicBaseRepository<Doctor> {

    // конструктор
    public DoctorsRepository(Context context) {
        super(context);
    }


    // доктора для которых значение в поле Процент отчисления на зарплату, больше заданного значения
    public List<Doctor> getAllByPercentOver(double percent) {
        return where(Doctor.PERCENT + " > ?", new String[]{Utils.doubleFormat(percent)});
    }

    // доктора специальность которых задана параметром
    public List<Doctor> getAllBySpeciality(String speciality) {
        return rawQuery(
                String.format("select * from view_doctors where speciality_name like '%s'", speciality),
                cursor -> loadEntity(context, cursor)
        );
    }

    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                Doctor.ID,
                Doctor.PERSON_ID,
                Doctor.SPECIALITY_ID,
                Doctor.PRICE,
                Doctor.PERCENT
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return Doctor.TABLE_NAME;
    }

    // получить данные из объекта в виде контента
    @Override
    protected ContentValues getContentValues(Doctor item) {
        var content = new ContentValues();

        content.put(Doctor.ID, item.getId());
        content.put(Doctor.PERSON_ID, item.getPerson().getId());
        content.put(Doctor.SPECIALITY_ID, item.getSpeciality().getId());
        content.put(Doctor.PERCENT, item.getPercent());
        content.put(Doctor.PRICE, item.getPrice());

        return content;
    }

    // использование метода загрузки модели
    @Override
    protected Doctor loadEntity(Context context, Cursor cursor) {
        return Doctor.loadEntity(context, cursor);
    }
}
