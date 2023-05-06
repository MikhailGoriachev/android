package org.goriachev.homework.repositories;

import android.content.Context;
import android.database.Cursor;

import org.goriachev.homework.entities.Appointment;
import org.goriachev.homework.models.Query06;
import org.goriachev.homework.models.Query07;
import org.goriachev.homework.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Класс Репозиторий для приёмов
public class AppointmentRepository extends PolyclinicBaseRepository<Appointment> {

    // название таблицы
    public static final String TABLE_NAME = "view_appointments";

    // поля
    public static final String APPOINTMENT_DATE = "appointment_date";
    public static final String DOCTOR_ID = "id_doctor";
    public static final String DOCTOR_PERSON_ID = "doctor_person_id";
    public static final String DOCTOR_PERSON_SURNAME = "doctor_person_surname";
    public static final String DOCTOR_PERSON_NAME = "doctor_person_name";
    public static final String DOCTOR_PERSON_PATRONYMIC = "doctor_person_patronymic";
    public static final String DOCTOR_SPECIALITY_ID = "doctor_speciality_id";
    public static final String DOCTOR_SPECIALITY_NAME = "doctor_speciality_name";
    public static final String DOCTOR_PRICE = "price";
    public static final String DOCTOR_PERCENT = "percent";
    public static final String PATIENT_ID = "id_patient";
    public static final String PATIENT_PERSON_ID = "patient_person_id";
    public static final String PATIENT_PERSON_SURNAME = "patient_person_surname";
    public static final String PATIENT_PERSON_NAME = "patient_person_name";
    public static final String PATIENT_PERSON_PATRONYMIC = "patient_person_patronymic";
    public static final String PATIENT_BORN_DATE = "patient_born_date";
    public static final String PATIENT_ADDRESS = "patient_address";
    public static final String PATIENT_PASSPORT = "patient_passport";


    // конструктор
    public AppointmentRepository(Context context) {
        super(context);
    }


    // приемы за некоторый период, заданный параметрами
    public List<Appointment> getAllByAppointmentDateRange(Date begin, Date end) {
        return where(APPOINTMENT_DATE + " between ? and ?", new String[]{
                Utils.dateHtmlToFormat(begin),
                Utils.dateHtmlToFormat(end)
        });
    }

    // сортировка по полю Специальность врача
    public List<Appointment> getAllOrderByDoctorSpeciality() {
        return orderBy(DOCTOR_SPECIALITY_NAME);
    }

    // группировка по полю Дата приема. Вычисляется статистика по стоимости приёма
    public List<Query06> getAllGroupByAppointmentDate() {
//        var columns = new String[]{"appointment_date", "min_price", "avg_price", "max_price", "count"};
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
//        var columns = new String[]{"doctor_speciality_name", "min_percent", "avg_percent", "max_percent", "count"};
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
                ID,
                APPOINTMENT_DATE,
                DOCTOR_ID,
                DOCTOR_PERSON_ID,
                DOCTOR_PERSON_SURNAME,
                DOCTOR_PERSON_NAME,
                DOCTOR_PERSON_PATRONYMIC,
                DOCTOR_SPECIALITY_ID,
                DOCTOR_SPECIALITY_NAME,
                DOCTOR_PRICE,
                DOCTOR_PERCENT,
                PATIENT_ID,
                PATIENT_PERSON_ID,
                PATIENT_PERSON_SURNAME,
                PATIENT_PERSON_NAME,
                PATIENT_PERSON_PATRONYMIC,
                PATIENT_BORN_DATE,
                PATIENT_ADDRESS,
                PATIENT_PASSPORT
        };
    }

    // получить название таблицы
    @Override
    String getTableName() {
        return TABLE_NAME;
    }

    // преобразовать в список объектов
    @Override
    List<Appointment> toList(Cursor cursor) {

        try (cursor) {
            var list = new ArrayList<Appointment>();

            if (cursor.moveToFirst()) {

                var id = cursor.getColumnIndex(ID);
                var appointmentDate = cursor.getColumnIndex(APPOINTMENT_DATE);
                var idDoctor = cursor.getColumnIndex(DOCTOR_ID);
                var doctorPersonId = cursor.getColumnIndex(DOCTOR_PERSON_ID);
                var doctorPersonSurname = cursor.getColumnIndex(DOCTOR_PERSON_SURNAME);
                var doctorPersonName = cursor.getColumnIndex(DOCTOR_PERSON_NAME);
                var doctorPersonPatronymic = cursor.getColumnIndex(DOCTOR_PERSON_PATRONYMIC);
                var doctorSpecialityId = cursor.getColumnIndex(DOCTOR_SPECIALITY_ID);
                var doctorSpecialityName = cursor.getColumnIndex(DOCTOR_SPECIALITY_NAME);
                var doctorPrice = cursor.getColumnIndex(DOCTOR_PRICE);
                var doctorPercent = cursor.getColumnIndex(DOCTOR_PERCENT);
                var idPatient = cursor.getColumnIndex(PATIENT_ID);
                var patientPersonId = cursor.getColumnIndex(PATIENT_PERSON_ID);
                var patientPersonSurname = cursor.getColumnIndex(PATIENT_PERSON_SURNAME);
                var patientPersonName = cursor.getColumnIndex(PATIENT_PERSON_NAME);
                var patientPersonPatronymic = cursor.getColumnIndex(PATIENT_PERSON_PATRONYMIC);
                var patientBornDate = cursor.getColumnIndex(PATIENT_BORN_DATE);
                var patientAddress = cursor.getColumnIndex(PATIENT_ADDRESS);
                var patientPassport = cursor.getColumnIndex(PATIENT_PASSPORT);

                do {
                    list.add(new Appointment(
                            cursor.getInt(id),
                            Utils.getDate(cursor.getString(appointmentDate)),
                            cursor.getInt(idDoctor),
                            cursor.getInt(doctorPersonId),
                            cursor.getString(doctorPersonSurname),
                            cursor.getString(doctorPersonName),
                            cursor.getString(doctorPersonPatronymic),
                            cursor.getInt(doctorSpecialityId),
                            cursor.getInt(doctorPrice),
                            cursor.getInt(doctorPercent),
                            cursor.getString(doctorSpecialityName),
                            cursor.getInt(idPatient),
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
