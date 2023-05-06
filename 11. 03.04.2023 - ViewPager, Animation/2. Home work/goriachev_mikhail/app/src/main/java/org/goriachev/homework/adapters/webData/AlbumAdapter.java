package org.goriachev.homework.adapters.webData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.webData.Album;
import org.goriachev.homework.utils.Utils;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Album> items;


    // конструктор инициализирующий
    public AlbumAdapter(@NonNull Context context, @NonNull List<Album> objects) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_album, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvAlbumTitle.setText(item.getTitle());
        holder.txvAlbumId.setText(Utils.intFormat(item.getId()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txvAlbumTitle, txvAlbumId;


        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvAlbumTitle = view.findViewById(R.id.txv_album_title);
            txvAlbumId = view.findViewById(R.id.txv_album_id);
        }
    }
}
