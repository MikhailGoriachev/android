package org.goriachev.homework.entities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.JsonObject;

import org.goriachev.homework.infrastructure.JsonEntity;
import org.goriachev.homework.repositories.DoctorsRepository;
import org.goriachev.homework.repositories.PatientsRepository;
import org.goriachev.homework.utils.Utils;

import java.text.ParseException;
import java.util.Date;

// Класс Приём
public class Appointment extends JsonEntity<Appointment> {

    // название таблицы
    public static final String TABLE_NAME = "appointments";

    // поля
    public static final String APPOINTMENT_DATE = "appointment_date";
    public static final String DOCTOR_ID = "id_doctor";
    public static final String PATIENT_ID = "id_patient";

    // дата приёма
    private Date appointmentDate;

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    // пациент
    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // доктор
    private Doctor doctor;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    // конструктор по умолчанию
    public Appointment() {
    }

    // конструктор инициализирующий
    public Appointment(int id, Date appointmentDate, Patient patient, Doctor doctor) {
        this.setId(id);
        this.setAppointmentDate(appointmentDate);
        this.setPatient(patient);
        this.setDoctor(doctor);
    }


    // получить название таблицы
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    // загрузка сущности
    @SuppressLint("Range")
    public static Appointment loadEntity(Context context, Cursor cursor) {

        var doctorsRepository = new DoctorsRepository(context);
        var patientsRepository = new PatientsRepository(context);

        try {
            return new Appointment(
                    cursor.getInt(cursor.getColumnIndex(ID)),
                    Utils.getDate(cursor.getString(cursor.getColumnIndex(APPOINTMENT_DATE))),
                    patientsRepository.getById(cursor.getInt(cursor.getColumnIndex(PATIENT_ID))),
                    doctorsRepository.getById(cursor.getInt(cursor.getColumnIndex(DOCTOR_ID)))
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // установка данных из JSON
    public static Appointment getEntityFromJson(Context context, JsonObject jsonObject) {

        var doctorsRepository = new DoctorsRepository(context);
        var patientsRepository = new PatientsRepository(context);

        try {
            return new Appointment(
                    jsonObject.get(ID).getAsInt(),
                    Utils.getDate(jsonObject.get(APPOINTMENT_DATE).getAsString()),
                    patientsRepository.getById(jsonObject.get(PATIENT_ID).getAsInt()),
                    doctorsRepository.getById(jsonObject.get(DOCTOR_ID).getAsInt())
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // получение данных в формате JSON
    @Override
    public JsonObject getJsonFromEntity() {
        var object = new JsonObject();

        object.addProperty(Appointment.ID, getId());
        object.addProperty(Appointment.APPOINTMENT_DATE, Utils.dateHtmlToFormat(getAppointmentDate()));
        object.addProperty(Appointment.PATIENT_ID, getPatient().getId());
        object.addProperty(Appointment.DOCTOR_ID, getDoctor().getId());

        return object;
    }
}
