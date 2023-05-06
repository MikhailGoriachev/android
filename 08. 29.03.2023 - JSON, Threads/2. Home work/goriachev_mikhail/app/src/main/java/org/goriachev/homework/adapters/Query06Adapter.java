package org.goriachev.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.Query06;
import org.goriachev.homework.utils.Utils;

import java.util.List;

public class Query06Adapter extends RecyclerView.Adapter<Query06Adapter.ViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Query06> items;

    // конструктор инициализирующий
    public Query06Adapter(@NonNull Context context, @NonNull List<Query06> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_query06, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvAppointmentDate.setText(Utils.dateHtmlToFormat(item.getAppointmentDate()));
        holder.txvPriceMin.setText(Utils.intFormat(item.getMinPrice()));
        holder.txvPriceAvg.setText(Utils.doubleFormat(item.getAvgPrice(), 2));
        holder.txvPriceMax.setText(Utils.intFormat(item.getMaxPrice()));
        holder.txvCount.setText(Utils.intFormat(item.getCount()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // поля для вывода информации
        TextView txvAppointmentDate, txvPriceMin, txvPriceAvg, txvPriceMax, txvCount;

        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvAppointmentDate = view.findViewById(R.id.txv_appointment_date);
            txvPriceMin = view.findViewById(R.id.txv_price_min);
            txvPriceAvg = view.findViewById(R.id.txv_price_avg);
            txvPriceMax = view.findViewById(R.id.txv_price_max);
            txvCount = view.findViewById(R.id.txv_count);
        }
    }
}
