package org.goriachev.homework.dialogues.forms;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Appointment;
import org.goriachev.homework.entities.Doctor;
import org.goriachev.homework.entities.Patient;
import org.goriachev.homework.repositories.DoctorsRepository;
import org.goriachev.homework.repositories.PatientsRepository;
import org.goriachev.homework.utils.Utils;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;


// Класс Форма для Приёма
public class AppointmentFormDialog extends DialogFragment {

    // callback для обработки результата ввода
    private Consumer<Appointment> onSubmitCallback;

    private Appointment appointment;

    private Spinner spnPatient, spnDoctor;

    private Button btnAppointmentDate;

    // пациенты
    private List<Patient> patients;

    // доктора
    private List<Doctor> doctors;

    // статус формы
    private boolean isCreate;


    // конструктор для добавления приёма
    public AppointmentFormDialog(@NonNull Consumer<Appointment> onSubmitCallback) {
        this.onSubmitCallback = onSubmitCallback;
        this.appointment = new Appointment();
        this.isCreate = true;
    }

    // конструктор для редактирования приёма
    public AppointmentFormDialog(@NonNull Consumer<Appointment> onSubmitCallback,
                                 Appointment appointment) {
        this.onSubmitCallback = onSubmitCallback;
        this.appointment = appointment;
        this.isCreate = false;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // разметка
        var inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_form_appointment, null);

        // контроллы
        btnAppointmentDate = view.findViewById(R.id.btn_appointment_date);
        spnDoctor = view.findViewById(R.id.spn_doctor);
        spnPatient = view.findViewById(R.id.spn_patient);

        // получение данных для списков
        patients = new PatientsRepository(getContext()).getAll();
        doctors = new DoctorsRepository(getContext()).getAll();

        // установка адаптеров
        var patientsAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                patients.stream().map(Patient::toString).collect(Collectors.toList())
        );

        spnPatient.setAdapter(patientsAdapter);

        var doctorsAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                doctors.stream().map(Doctor::toString).collect(Collectors.toList())
        );

        spnDoctor.setAdapter(doctorsAdapter);

        // установка начальных данных в модель при создании
        if (isCreate) {
            appointment.setAppointmentDate(new Date());
            appointment.setDoctor(doctors.get(0));
            appointment.setPatient(patients.get(0));
        }

        // установка данных из модели в контроллы
        setData();

        // установка обработчиков
        btnAppointmentDate.setOnClickListener(this::btnAppointmentDateClickHandler);
        spnPatient.setOnItemSelectedListener(patientSpinnerOnItemSelectedListener);
        spnDoctor.setOnItemSelectedListener(doctorSpinnerOnItemSelectedListener);

        builder.setTitle(isCreate ? "Добавление приёма" : "Изменение приёма")
                .setView(view)
                .setPositiveButton(isCreate ? R.string.add_label : R.string.edit_label, submitClickListener)
                .setNegativeButton(R.string.cancel_label, null);

        return builder.create();
    }

    // установка данных в контроллы
    private void setData() {
        btnAppointmentDate.setText(Utils.dateHtmlToFormat(appointment.getAppointmentDate()));

        var patientIndex = patients.indexOf(
                patients.stream()
                        .filter(p -> p.getId() == appointment.getPatient().getId())
                        .findFirst()
                        .get());

        spnPatient.setSelection(patientIndex);

        var doctorIndex = doctors.indexOf(
                doctors.stream()
                        .filter(p -> p.getId() == appointment.getDoctor().getId())
                        .findFirst()
                        .get());

        spnDoctor.setSelection(doctorIndex);
    }

    private final DialogInterface.OnClickListener submitClickListener = (dialogInterface, which) -> {
        onSubmitCallback.accept(appointment);
    };

    // обработка нажатия на кнопку выбора даты приёма
    private void btnAppointmentDateClickHandler(View view) {
        var dialog = new DatePickerDialog(
                getContext(),
                (datePicker, year, month, dayOfMonth) -> {
                    appointment.getAppointmentDate().setYear(year - 1900);
                    appointment.getAppointmentDate().setMonth(month);
                    appointment.getAppointmentDate().setDate(dayOfMonth);

                    btnAppointmentDate.setText(Utils.dateHtmlToFormat(appointment.getAppointmentDate()));
                },
                appointment.getAppointmentDate().getYear() + 1900,
                appointment.getAppointmentDate().getMonth(),
                appointment.getAppointmentDate().getDate()
        );

        dialog.show();
    }

    // обработчик спиннера пациента
    private final AdapterView.OnItemSelectedListener patientSpinnerOnItemSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    appointment.setPatient(patients.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            };

    // обработчик спиннера доктора
    private final AdapterView.OnItemSelectedListener doctorSpinnerOnItemSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    appointment.setDoctor(doctors.get(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            };
}
