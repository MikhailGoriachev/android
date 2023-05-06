package org.goriachev.homework.adapters.polyclinic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.Query07;
import org.goriachev.homework.utils.Utils;

import java.util.List;

public class Query07Adapter extends RecyclerView.Adapter<Query07Adapter.ViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Query07> items;

    // конструктор инициализирующий
    public Query07Adapter(@NonNull Context context, @NonNull List<Query07> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_query07, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvSpecialityName.setText(item.getSpecialityName());
        holder.txvPercentMin.setText(Utils.doubleFormat(item.getMinPercent(), 2));
        holder.txvPercentAvg.setText(Utils.doubleFormat(item.getAvgPercent(), 2));
        holder.txvPercentMax.setText(Utils.doubleFormat(item.getMaxPercent(), 2));
        holder.txvCount.setText(Utils.intFormat(item.getCount()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // поля для вывода информации
        TextView txvSpecialityName, txvPercentMin, txvPercentAvg, txvPercentMax, txvCount;

        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvSpecialityName = view.findViewById(R.id.txv_speciality_name);
            txvPercentMin = view.findViewById(R.id.txv_percent_min);
            txvPercentAvg = view.findViewById(R.id.txv_percent_avg);
            txvPercentMax = view.findViewById(R.id.txv_percent_max);
            txvCount = view.findViewById(R.id.txv_count);
        }
    }
}
