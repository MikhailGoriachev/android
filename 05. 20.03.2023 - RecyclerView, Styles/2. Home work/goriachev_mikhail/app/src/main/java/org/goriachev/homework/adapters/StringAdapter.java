package org.goriachev.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.utils.Utils;

import java.util.List;
import java.util.function.Consumer;


public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {
//public class ShipAdapter extends ArrayAdapter<Ship> {

    // загрузчик разметки
    private LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<String> items;

    // конструктор инициализирующий
    public StringAdapter(@NonNull Context context, @NonNull List<String> objects) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.string_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txvValue.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // поля для вывода информации о судне
        TextView txvValue;


        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);
            txvValue = view.findViewById(R.id.txv_value);
        }
    }

}
