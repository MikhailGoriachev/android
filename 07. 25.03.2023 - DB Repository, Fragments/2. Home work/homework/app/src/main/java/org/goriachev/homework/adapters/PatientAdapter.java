package org.goriachev.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Patient;
import org.goriachev.homework.utils.Utils;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Patient> items;


    // конструктор инициализирующий
    public PatientAdapter(@NonNull Context context, @NonNull List<Patient> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_patient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvPatientName.setText(String.format("%s %s. %s.",
                item.getPerson().getSurname(),
                item.getPerson().getName().charAt(0),
                item.getPerson().getPatronymic().charAt(0))
        );
        holder.txvPatientBornDate.setText(Utils.dateHtmlToFormat(item.getBornDate()));
        holder.txvPatientPassport.setText(item.getPassport());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // поля для вывода информации
        TextView txvPatientName, txvPatientBornDate, txvPatientPassport;

        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvPatientName = view.findViewById(R.id.txv_patient_name);
            txvPatientBornDate = view.findViewById(R.id.txv_patient_born_date);
            txvPatientPassport = view.findViewById(R.id.txv_patient_born_passport);
        }
    }
}
