package org.goriachev.homework.adapters.polyclinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Appointment;
import org.goriachev.homework.utils.Utils;

import java.util.List;
import java.util.function.Consumer;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Appointment> items;

    // режим редактирования
    private boolean isEdit;

    // обработчик события редактирования
    private Consumer<Appointment> onEditHandler;

    // обработчик события удаления
    private Consumer<Appointment> onDeleteHandler;


    // конструктор инициализирующий
    public AppointmentAdapter(@NonNull Context context, @NonNull List<Appointment> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
        isEdit = false;
    }

    // конструктор инициализирующий
    public AppointmentAdapter(@NonNull Context context,
                              @NonNull List<Appointment> objects,
                              Consumer<Appointment> onEditHandler,
                              Consumer<Appointment> onDeleteHandler) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
        this.onEditHandler = onEditHandler;
        this.onDeleteHandler = onDeleteHandler;
        isEdit = true;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_appointment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvAppointmentDate.setText(Utils.dateHtmlToFormat(item.getAppointmentDate()));
        holder.txvPatientName.setText(String.format("%s %s. %s.",
                item.getPatient().getPerson().getSurname(),
                item.getPatient().getPerson().getName().charAt(0),
                item.getPatient().getPerson().getPatronymic().charAt(0))
        );
        holder.txvPatientBornDate.setText(Utils.dateHtmlToFormat(item.getPatient().getBornDate()));
        holder.txvPatientPassport.setText(item.getPatient().getPassport());
        holder.txvDoctorName.setText(String.format("%s %s. %s.",
                item.getDoctor().getPerson().getSurname(),
                item.getDoctor().getPerson().getName().charAt(0),
                item.getDoctor().getPerson().getPatronymic().charAt(0))
        );
        holder.txvSpecialityName.setText(item.getDoctor().getSpeciality().getSpecialityName());
        holder.txvDoctorPercent.setText(Utils.doubleFormat(item.getDoctor().getPercent(), 2));
        holder.txvDoctorPrice.setText(Utils.intFormat(item.getDoctor().getPrice()));
        holder.txvDoctorSalary.setText(Utils.doubleFormat(item.getDoctor().getSalary(), 2));

        if (isEdit) {
            holder.btnEdit.setOnClickListener((v) -> onEditHandler.accept(item));
            holder.btnDelete.setOnClickListener((v) -> onDeleteHandler.accept(item));
        } else {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // поля для вывода информации
        TextView txvAppointmentDate, txvPatientName, txvPatientBornDate, txvPatientPassport,
                txvDoctorName, txvSpecialityName, txvDoctorPercent, txvDoctorPrice, txvDoctorSalary,
                btnEdit, btnDelete;

        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvAppointmentDate = view.findViewById(R.id.txv_appointment_date);
            txvPatientName = view.findViewById(R.id.txv_patient_name);
            txvPatientBornDate = view.findViewById(R.id.txv_patient_born_date);
            txvPatientPassport = view.findViewById(R.id.txv_patient_born_passport);
            txvDoctorName = view.findViewById(R.id.txv_doctor_name);
            txvSpecialityName = view.findViewById(R.id.txv_speciality_name);
            txvDoctorPercent = view.findViewById(R.id.txv_doctor_percent);
            txvDoctorPrice = view.findViewById(R.id.txv_doctor_price);
            txvDoctorSalary = view.findViewById(R.id.txv_doctor_salary);

            btnEdit = view.findViewById(R.id.btn_edit);
            btnDelete = view.findViewById(R.id.btn_delete);
        }
    }
}
