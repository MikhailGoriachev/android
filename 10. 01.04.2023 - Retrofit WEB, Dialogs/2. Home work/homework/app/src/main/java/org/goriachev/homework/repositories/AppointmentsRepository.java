package org.goriachev.homework.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Appointment;
import org.goriachev.homework.models.Query06;
import org.goriachev.homework.models.Query07;
import org.goriachev.homework.utils.Utils;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// Класс Репозиторий для приёмов
public class AppointmentsRepository extends PolyclinicBaseRepository<Appointment> {

    // конструктор
    public AppointmentsRepository(Context context) {
        super(context);
    }


    // приемы за некоторый период, заданный параметрами
    public List<Appointment> getAllByAppointmentDateRange(Date begin, Date end) {
        return where(Appointment.APPOINTMENT_DATE + " between ? and ?", new String[]{
                Utils.dateHtmlToFormat(begin),
                Utils.dateHtmlToFormat(end)
        });
    }

    // сортировка по полю Специальность врача
    public List<Appointment> getAllOrderByDoctorSpeciality() {
        return getAll()
                .stream()
                .sorted(Comparator.comparing(a -> a.getDoctor().getSpeciality().getSpecialityName()))
                .collect(Collectors.toList());
    }

    // группировка по полю Дата приема. Вычисляется статистика по стоимости приёма
    public List<Query06> getAllGroupByAppointmentDate() {
        return rawQuery(
                "select " +
                        "appointment_date, " +
                        "min(price) as min_price, " +
                        "avg(price) as avg_price, " +
                        "max(price) as max_price, " +
                        "count(price) as count " +
                        "from view_appointments " +
                        "group by appointment_date",
                (c) ->
                {
                    try {
                        return new Query06(
                                Utils.getDate(c.getString(0)),
                                c.getInt(1),
                                c.getDouble(2),
                                c.getInt(3),
                                c.getInt(4));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    // группировка по полю Специальность. Вычисляется средний Процент отчисления на зарплату
    // от стоимости приема
    public List<Query07> getAllGroupBySpeciality() {
        return rawQuery(
                "select " +
                        "doctor_speciality_name, " +
                        "min(percent) as min_percent, " +
                        "avg(percent) as avg_percent, " +
                        "max(percent) as max_percent, " +
                        "count(price) as count " +
                        "from view_appointments " +
                        "group by doctor_speciality_name",
                (c) -> new Query07(
                        c.getString(0),
                        c.getInt(1),
                        c.getDouble(2),
                        c.getInt(3),
                        c.getInt(4))
        );
    }

    // получить названия столбцов
    @Override
    String[] getColumnsName() {
        return new String[]{
                Appointment.ID,
                Appointment.APPOINTMENT_DATE,
                Appointment.PATIENT_ID,
                Appointment.DOCTOR_ID,
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return Appointment.TABLE_NAME;
    }

    // получить данные из объекта в виде контента
    @Override
    protected ContentValues getContentValues(Appointment item) {
        var content = new ContentValues();

        content.put(Appointment.ID, item.getId());
        content.put(Appointment.APPOINTMENT_DATE, Utils.dateHtmlToFormat(item.getAppointmentDate()));
        content.put(Appointment.PATIENT_ID, item.getPatient().getId());
        content.put(Appointment.DOCTOR_ID, item.getDoctor().getId());

        return content;
    }

    // удаление записи по id
    @Override
    public long delete(long id) {
        return super.delete(id);
    }

    // удаление записи
    @Override
    public long delete(Appointment item) {
        return super.delete(item);
    }

    // очистка данных таблицы
    @Override
    public long clear() {
        return super.clear();
    }

    // использование метода загрузки модели
    @Override
    protected Appointment loadEntity(Context context, Cursor cursor) {
        return Appointment.loadEntity(context, cursor);
    }
}
