package org.goriachev.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.Message;
import org.goriachev.homework.viewHolders.ItemFourLineViewHolder;

import java.util.List;
import java.util.function.Consumer;

public class MessageAdapter extends RecyclerView.Adapter<ItemFourLineViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Message> items;

    // обработчик клика по элементу
    private Consumer<Message> onClickHandler;


    // конструктор инициализирующий
    public MessageAdapter(@NonNull Context context,
                          @NonNull List<Message> objects,
                          @Nullable Consumer<Message> onClickHandler) {
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

        holder.txvFirstLine.setText(item.getText());
        holder.txvSecondLine.setText(String.format("Получатель: %s", item.getReceiver()));
        holder.txvThirdLine.setText(String.format("Отправитель: %s", item.getSender()));
        holder.txvFourthLine.setText(String.format("Вложения: %s", item.isAttach() ? "да" : "нет"));

        if (onClickHandler != null)
            holder.layCard.setOnClickListener(v -> onClickHandler.accept(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
