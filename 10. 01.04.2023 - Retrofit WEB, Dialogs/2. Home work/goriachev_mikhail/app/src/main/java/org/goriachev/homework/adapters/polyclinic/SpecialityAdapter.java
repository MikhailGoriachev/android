package org.goriachev.homework.adapters.polyclinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Speciality;

import java.util.List;

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.ViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Speciality> items;


    // конструктор инициализирующий
    public SpecialityAdapter(@NonNull Context context, @NonNull List<Speciality> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_speciality, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvSpecialityName.setText(item.getSpecialityName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // поля для вывода информации
        TextView txvSpecialityName;


        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvSpecialityName = view.findViewById(R.id.txv_speciality_name);
        }
    }
}
