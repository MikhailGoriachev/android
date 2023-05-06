package org.goriachev.homework.adapters.webData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.webData.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvPostTitle.setText(item.getTitle());
        holder.txvPostBody.setText(item.getBody());
//        holder.txvPostId.setText(Utils.intFormat(item.getId()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txvPostTitle, txvPostBody, txvPostId;


        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvPostTitle = view.findViewById(R.id.txv_post_title);
            txvPostBody = view.findViewById(R.id.txv_post_body);
//            txvPostId = view.findViewById(R.id.txv_post_id);
        }
    }
}
