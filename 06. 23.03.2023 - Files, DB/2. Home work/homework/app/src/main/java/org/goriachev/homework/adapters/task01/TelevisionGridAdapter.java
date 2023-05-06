package org.goriachev.homework.adapters.task01;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.goriachev.homework.R;
import org.goriachev.homework.models.task01.Television;
import org.goriachev.homework.utils.Utils;

import java.util.List;
import java.util.Locale;

// Класс Адаптер для телевизора при отображении в GridViews
public class TelevisionGridAdapter extends ArrayAdapter<Television> {

    // id разметки
    private int layout;

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Television> items;


    // конструктор инициализирующий
    public TelevisionGridAdapter(@NonNull Context context, int resource, @NonNull List<Television> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(context);
        this.context = context;
        this.items = objects;
        this.layout = resource;
    }


    // получить представление
    public View getView(int position, View convertView, ViewGroup parent) {
        /* Использование ViewHolder */
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView, position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.position = position;
        }

        // получить ссылку на элемент коллекции
        var item = items.get(position);

        // вывести данные в элементы разметки view
        Utils.setViewImage(viewHolder.imvTelevisionImage, "televisions/" + item.getImageFile(), context);
        viewHolder.txvTelevisionModel.setText(item.getModel());
        viewHolder.txvTelevisionDiagonal.setText(Utils.doubleFormat(item.getDiagonal(), 1) + "\"");
        viewHolder.txvTelevisionResolution.setText(String.format(Locale.UK, "%dx%d",
                item.getHorizontalResolution(), item.getVerticalResolution()));
        viewHolder.txvTelevisionPrice.setText(Utils.doubleFormat(item.getPrice(), 2) + "₽");

        // вернуть сформированное представление
        return convertView;
    } // getView


    private static class ViewHolder {
        public final ImageView imvTelevisionImage;
        public final TextView txvTelevisionModel;
        public final TextView txvTelevisionDiagonal;
        public final TextView txvTelevisionResolution;
        public final TextView txvTelevisionPrice;

        public int position;


        public ViewHolder(View view, int position) {
            imvTelevisionImage = view.findViewById(R.id.imv_television_image);
            txvTelevisionModel = view.findViewById(R.id.txv_television_model);
            txvTelevisionDiagonal = view.findViewById(R.id.txv_television_diagonal);
            txvTelevisionResolution = view.findViewById(R.id.txv_television_resolution);
            txvTelevisionPrice = view.findViewById(R.id.txv_television_price);

            this.position = position;
        }
    }
}
