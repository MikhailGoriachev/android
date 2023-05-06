package org.goriachev.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Edition;
import org.goriachev.homework.viewHolders.ItemFourLineViewHolder;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class EditionAdapter extends RecyclerView.Adapter<ItemFourLineViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Edition> items;

    // обработчик клика по элементу
    private final Consumer<Edition> onClickHandler;


    // конструктор инициализирующий
    public EditionAdapter(@NonNull Context context,
                          @NonNull List<Edition> objects,
                          @Nullable Consumer<Edition> onClickHandler) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
        this.onClickHandler = onClickHandler;
    }


    @NonNull
    @Override
    public ItemFourLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemFourLineViewHolder(inflater.inflate(R.layout.view_item_four_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemFourLineViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvFirstLine.setText(item.getName());
        holder.txvSecondLine.setText(String.format("Индекс: %s", item.getIndex()));
        holder.txvThirdLine.setText(String.format("Тип издания: %s", item.getPublicationType()));
        holder.txvFourthLine.setText(String.format(Locale.UK, "Цена: %d", item.getPrice()));

        if (onClickHandler != null)
            holder.layCard.setOnClickListener(v -> onClickHandler.accept(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
