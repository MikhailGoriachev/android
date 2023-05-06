package org.goriachev.homework.adapters.polyclinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Doctor;
import org.goriachev.homework.utils.Utils;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Doctor> items;


    // конструктор инициализирующий
    public DoctorAdapter(@NonNull Context context, @NonNull List<Doctor> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_doctor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvDoctorName.setText(String.format("%s %s. %s.",
                item.getPerson().getSurname(),
                item.getPerson().getName().charAt(0),
                item.getPerson().getPatronymic().charAt(0))
        );
        holder.txvSpecialityName.setText(item.getSpeciality().getSpecialityName());
        holder.txvDoctorPercent.setText(Utils.doubleFormat(item.getPercent(), 2));
        holder.txvDoctorPrice.setText(Utils.intFormat(item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // поля для вывода информации
        TextView txvDoctorName, txvSpecialityName, txvDoctorPercent, txvDoctorPrice;

        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvDoctorName = view.findViewById(R.id.txv_doctor_name);
            txvSpecialityName = view.findViewById(R.id.txv_speciality_name);
            txvDoctorPercent = view.findViewById(R.id.txv_doctor_percent);
            txvDoctorPrice = view.findViewById(R.id.txv_doctor_price);
        }
    }
}
