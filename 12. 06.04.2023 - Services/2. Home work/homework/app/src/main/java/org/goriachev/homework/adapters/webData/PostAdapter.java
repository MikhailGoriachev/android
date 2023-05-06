package org.goriachev.homework.adapters.webData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.webData.Post;
import org.goriachev.homework.viewHolders.ItemTwoLineViewHolder;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<ItemTwoLineViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Post> items;


    // конструктор инициализирующий
    public PostAdapter(@NonNull Context context, @NonNull List<Post> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }


    @NonNull
    @Override
    public ItemTwoLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemTwoLineViewHolder(inflater.inflate(R.layout.view_item_two_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTwoLineViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvFirstLine.setText(item.getTitle());
        holder.txvSecondLine.setText(String.format("Текст: %s", item.getBody()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
