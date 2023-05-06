package org.goriachev.homework.adapters.webData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.webData.User;
import org.goriachev.homework.viewHolders.ItemFourLineViewHolder;

import java.util.List;
import java.util.function.Consumer;

public class UserAdapter extends RecyclerView.Adapter<ItemFourLineViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<User> items;

    // обработчик события редактирования
    private final Consumer<User> onClickHandler;

    // конструктор инициализирующий
    public UserAdapter(@NonNull Context context, @NonNull List<User> objects, @Nullable Consumer<User> onClickHandler) {
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

        holder.txvFirstLine.setText(item.getUsername());
        holder.txvSecondLine.setText(String.format("Имя: %s", item.getName()));
        holder.txvThirdLine.setText(String.format("Почта: %s", item.getEmail()));
        holder.txvFourthLine.setText(String.format("Веб-сайт: %s", item.getWebsite()));

        if (onClickHandler != null)
            holder.layCard.setOnClickListener(v -> onClickHandler.accept(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
