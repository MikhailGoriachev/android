package org.goriachev.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.utils.Utils;

import java.util.List;
import java.util.function.Consumer;


public class ShipAdapter extends RecyclerView.Adapter<ShipAdapter.ViewHolder> {
//public class ShipAdapter extends ArrayAdapter<Ship> {

    // загрузчик разметки
    private LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Ship> items;

    // обработчик события выбора по элемента
    private final Consumer<Integer> onClickHandler;

    // обработчик события удаления элемента
    private final Consumer<Integer> onDeleteHandler;

    // конструктор инициализирующий
    public ShipAdapter(@NonNull Context context, @NonNull List<Ship> objects,
                       Consumer<Integer> onClickHandler, Consumer<Integer> onDeleteHandler) {

        this.onClickHandler = onClickHandler;
        this.onDeleteHandler = onDeleteHandler;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.ship_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var ship = items.get(position);

        Utils.setViewImage(holder.ivwShip, "ships/" + ship.getShipType().getFileName(), context);
        holder.txvShipType.setText(ship.getShipType().getName());
        holder.txvShipCargoWeight.setText(Utils.intFormat(ship.getCargoWeight()));
        holder.txvShipPrice.setText(Utils.intFormat(ship.getPrice()));
        holder.txvShipCost.setText(Utils.longFormat(ship.getCost()));

        holder.layShipItem.setOnClickListener((view) -> onClickHandler.accept(position));
        holder.btnDelete.setOnClickListener((view) -> onDeleteHandler.accept(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // элемент для вывода изображения судна
        ImageView ivwShip;

        // поля для вывода информации о судне
        TextView txvShipType, txvShipCargoWeight, txvShipPrice, txvShipCost;

        LinearLayout layShipItem;

        Button btnDelete;


        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            layShipItem = view.findViewById(R.id.lay_ship_item);

            // поиск элементов о судне
            txvShipType = view.findViewById(R.id.txv_ship_type);
            txvShipCargoWeight = view.findViewById(R.id.txv_ship_weight);
            txvShipPrice = view.findViewById(R.id.txv_ship_price);
            txvShipCost = view.findViewById(R.id.txv_ship_cost);

            ivwShip = view.findViewById(R.id.ivw_ship_image);

            btnDelete = view.findViewById(R.id.btn_delete);
        }
    }

}
