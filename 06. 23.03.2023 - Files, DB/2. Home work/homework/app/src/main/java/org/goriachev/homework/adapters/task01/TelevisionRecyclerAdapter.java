package org.goriachev.homework.adapters.task01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.task01.Television;
import org.goriachev.homework.utils.Utils;

import java.util.List;
import java.util.Locale;

public class TelevisionRecyclerAdapter extends RecyclerView.Adapter<TelevisionRecyclerAdapter.ViewHolder> {

    // загрузчик разметки
    private LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Television> items;


    // конструктор инициализирующий
    public TelevisionRecyclerAdapter(@NonNull Context context, @NonNull List<Television> objects) {

        System.out.println("Количество: " + objects);

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.view_recycler_item_television, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // получить ссылку на элемент коллекции
        var item = items.get(position);

        // вывести данные в элементы разметки view
        Utils.setViewImage(holder.imvTelevisionImage, "televisions/" + item.getImageFile(), context);
        holder.txvTelevisionModel.setText(item.getModel());
        holder.txvTelevisionDiagonal.setText(Utils.doubleFormat(item.getDiagonal(), 1) + "\"");
        holder.txvTelevisionResolution.setText(String.format(Locale.UK, "%dx%d",
                item.getHorizontalResolution(), item.getVerticalResolution()));
        holder.txvTelevisionPrice.setText(Utils.doubleFormat(item.getPrice(), 2) + "₽");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imvTelevisionImage;
        public final TextView txvTelevisionModel;
        public final TextView txvTelevisionDiagonal;
        public final TextView txvTelevisionResolution;
        public final TextView txvTelevisionPrice;


        public ViewHolder(View view) {
            super(view);
            imvTelevisionImage = view.findViewById(R.id.imv_television_image);
            txvTelevisionModel = view.findViewById(R.id.txv_television_model);
            txvTelevisionDiagonal = view.findViewById(R.id.txv_television_diagonal);
            txvTelevisionResolution = view.findViewById(R.id.txv_television_resolution);
            txvTelevisionPrice = view.findViewById(R.id.txv_television_price);
        }
    }
}
