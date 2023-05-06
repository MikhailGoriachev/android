package org.goriachev.homework.adapters.webData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.webData.Album;
import org.goriachev.homework.viewHolders.ItemOneLineViewHolder;

import java.util.List;
import java.util.function.Consumer;

public class AlbumAdapter extends RecyclerView.Adapter<ItemOneLineViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Album> items;

    // обработчик события редактирования
    private final Consumer<Album> onClickHandler;

    // конструктор инициализирующий
    public AlbumAdapter(@NonNull Context context, @NonNull List<Album> objects, @Nullable Consumer<Album> onClickHandler) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
        this.onClickHandler = onClickHandler;
    }


    @NonNull
    @Override
    public ItemOneLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemOneLineViewHolder(inflater.inflate(R.layout.view_item_one_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOneLineViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvFirstLine.setText(item.getTitle());

        if (onClickHandler != null)
            holder.layCard.setOnClickListener(v -> onClickHandler.accept(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
