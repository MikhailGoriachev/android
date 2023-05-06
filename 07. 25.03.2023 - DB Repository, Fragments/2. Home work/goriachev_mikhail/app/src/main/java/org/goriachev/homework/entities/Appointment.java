package org.goriachev.homework.entities;


import java.util.Date;

// Класс Приём
public class Appointment {

    // id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // дата приёма
    private Date appointmentDate;

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    // id пациента
    private int patientId;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    // пациент
    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // id доктора
    private int doctorId;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
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
    public Appointment(int id, Date appointmentDate, int patientId, Patient patient, int doctorId, Doctor doctor) {
        this.setId(id);
        this.setAppointmentDate(appointmentDate);
        this.setPatientId(patientId);
        this.setPatient(patient);
        this.setDoctorId(doctorId);
        this.setDoctor(doctor);
    }

    // конструктор инициализирующий
    public Appointment(int id, Date appointmentDate, int doctorId, int doctorPersonId,
                       String doctorSurname, String doctorName, String doctorPatronymic,
                       int doctorPrice, int doctorPercent,
                       int specialityId, String specialityName,
                       int patientId, int patientPersonId, String patientSurname,
                       String patientName, String patientPatronymic, Date patientBornDate,
                       String patientAddress, String patientPassport) {
        this.setId(id);
        this.setAppointmentDate(appointmentDate);
        this.setPatientId(patientId);
        this.setPatient(new Patient(patientId, patientPersonId, patientSurname, patientName,
                patientPatronymic, patientBornDate, patientAddress, patientPassport));
        this.setDoctorId(doctorId);
        this.setDoctor(new Doctor(doctorId, doctorPersonId, doctorSurname, doctorName,
                doctorPatronymic, specialityId, specialityName, doctorPrice, doctorPercent));
    }
}
