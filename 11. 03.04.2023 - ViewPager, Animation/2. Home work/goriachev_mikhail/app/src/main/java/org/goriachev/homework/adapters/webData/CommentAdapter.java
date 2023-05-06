package org.goriachev.homework.adapters.webData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.webData.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var item = items.get(position);

        holder.txvCommentName.setText(item.getName());
        holder.txvCommentBody.setText(item.getBody());
        holder.txvCommentEmail.setText(item.getEmail());
//        holder.txvCommentId.setText(Utils.intFormat(item.getId()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txvCommentName, txvCommentBody, txvCommentEmail, txvCommentId;


        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            txvCommentName = view.findViewById(R.id.txv_comment_name);
            txvCommentBody = view.findViewById(R.id.txv_comment_body);
            txvCommentEmail = view.findViewById(R.id.txv_comment_email);
//            txvCommentId = view.findViewById(R.id.txv_comment_id);
        }
    }
}
