package org.goriachev.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Result;
import org.goriachev.homework.utils.Utils;
import org.goriachev.homework.viewHolders.ItemFourLineViewHolder;

import java.util.List;
import java.util.Locale;


// Класс Адаптер для результата обработки
public class ResultAdapter extends RecyclerView.Adapter<ItemFourLineViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Result> items;


    // конструктор инициализирующий
    public ResultAdapter(@NonNull Context context, @NonNull List<Result> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }


    @NonNull
    @Override
    public ItemFourLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemFourLineViewHolder(inflater.inflate(R.layout.view_item_four_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemFourLineViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvFirstLine.setText(item.getSensor().getName());

        holder.txvSecondLine.setText(
                String.format(Locale.UK, "Min: %.4f, Avg: %.4f, Max: %.4f, Количество: %d",
                        item.getMinValue(),
                        item.getAvgValue(),
                        item.getMaxValue(),
                        item.getAmount()
                ));

        holder.txvThirdLine.setText(String.format("Начало обработки данных: %s", Utils.dateTimeToFormat(item.getStartDateTime())));
        holder.txvFourthLine.setText(String.format("Обработка данных: %s", Utils.dateTimeToFormat(item.getProcessDataDateTime())));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
