package org.goriachev.homework.viewModels;


import java.util.Date;

// Класс Модель представления Приёма
public class AppointmentViewModel {

    // дата приёма
    public Date appointmentDate;

    // id доктора
    public int doctorId;

    // id пациента
    public int patientId;


    public AppointmentViewModel() {
    }

    public AppointmentViewModel(Date appointmentDate, int doctorId, int patientId) {
        this.appointmentDate = appointmentDate;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }
}
