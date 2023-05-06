package org.goriachev.homework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.goriachev.homework.R;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.utils.Utils;

import java.util.List;
import java.util.function.Consumer;


public class ShipAdapter extends ArrayAdapter<Ship> {

    // загрузчик разметки
    private LayoutInflater inflater;

    // id разметки списка
    private final int layoutId;

    // контекст
    final Context context;

    // данные
    private final List<Ship> items;

    // обработчик события выбора по элемента
    private final Consumer<Integer> onClickHandler;

    // конструктор инициализирующий
    public ShipAdapter(@NonNull Context context, int resource, @NonNull List<Ship> objects, Consumer<Integer> onClickHandler) {
        super(context, resource, objects);

        this.onClickHandler = onClickHandler;
        this.context = context;
        this.layoutId = resource;
        this.inflater = LayoutInflater.from(context);
        this.items = objects;
    }

    // формирование разметки
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(this.layoutId, parent, false);
            holder = new ViewHolder(convertView, position);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.position = position;
        }

        setData(items.get(position), holder, position);

        return convertView;
    }

    // установка данных в поля
    private void setData(Ship ship, ViewHolder holder, int position) {
        Utils.setViewImage(holder.ivwShip, "ships/" + ship.getShipType().getFileName(), context);
        holder.txvShipType.setText(ship.getShipType().getName());
        holder.txvShipCargoWeight.setText(Utils.intFormat(ship.getCargoWeight()));
        holder.txvShipPrice.setText(Utils.intFormat(ship.getPrice()));
        holder.txvShipCost.setText(Utils.longFormat(ship.getCost()));

        holder.layShipItem.setOnClickListener((view) -> onClickHandler.accept(position));
    }


    private static class ViewHolder {

        // элемент для вывода изображения судна
        ImageView ivwShip;

        // поля для вывода информации о судне
        TextView txvShipType, txvShipCargoWeight, txvShipPrice, txvShipCost;

        final LinearLayout layShipItem;

        // позиция элемента в коллекции
        int position;


        // конструктор инициализирующий
        private ViewHolder(View view, int position) {

            this.position = position;

            layShipItem = view.findViewById(R.id.lay_ship_item);

            // поиск элементов о судне
            txvShipType = view.findViewById(R.id.txv_ship_type);
            txvShipCargoWeight = view.findViewById(R.id.txv_ship_weight);
            txvShipPrice = view.findViewById(R.id.txv_ship_price);
            txvShipCost = view.findViewById(R.id.txv_ship_cost);

            ivwShip = view.findViewById(R.id.ivw_ship_image);

        }
    }

}
