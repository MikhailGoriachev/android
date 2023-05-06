package org.goriachev.homework.adapters.webData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.webData.Comment;
import org.goriachev.homework.viewHolders.ItemThreeLineViewHolder;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<ItemThreeLineViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Comment> items;


    // конструктор инициализирующий
    public CommentAdapter(@NonNull Context context, @NonNull List<Comment> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }


    @NonNull
    @Override
    public ItemThreeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemThreeLineViewHolder(inflater.inflate(R.layout.view_item_three_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemThreeLineViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvFirstLine.setText(item.getName());
        holder.txvSecondLine.setText(String.format("Почта: %s", item.getEmail()));
        holder.txvThirdLine.setText(String.format("Текст: %s", item.getBody()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
