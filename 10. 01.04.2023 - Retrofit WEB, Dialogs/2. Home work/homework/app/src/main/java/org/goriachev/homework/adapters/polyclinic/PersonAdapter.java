package org.goriachev.homework.adapters.polyclinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Person> items;


    // конструктор инициализирующий
    public PersonAdapter(@NonNull Context context, @NonNull List<Person> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_person, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvSurname.setText(item.getSurname());
        holder.txvName.setText(item.getName());
        holder.txvPatronymic.setText(item.getPatronymic());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // поля для вывода информации
        TextView txvSurname, txvName, txvPatronymic;

        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvSurname = view.findViewById(R.id.txv_surname);
            txvName = view.findViewById(R.id.txv_name);
            txvPatronymic = view.findViewById(R.id.txv_patronymic);
        }
    }
}
