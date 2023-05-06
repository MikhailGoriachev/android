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
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.utils.Utils;

import java.util.List;
import java.util.function.Consumer;


public class AnimalAdapter extends ArrayAdapter<Animal> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // id разметки списка
    private final int layoutId;

    // контекст
    final Context context;

    // данные
    private final List<Animal> items;

    // обработчик события выбора по элемента
    private final Consumer<Integer> onClickHandler;

    // конструктор инициализирующий
    public AnimalAdapter(@NonNull Context context, int resource, @NonNull List<Animal> objects, Consumer<Integer> onClickHandler) {
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
    private void setData(Animal animal, ViewHolder holder, int position) {
            Utils.setViewImage(holder.ivwAnimal, "animals/" + animal.getImageFile(), context);
            holder.txvAnimalName.setText(animal.getName());
            holder.txvAnimalBreed.setText(animal.getBreed());
            holder.txvAnimalOwner.setText(animal.getOwner());

            holder.layAnimalItem.setOnClickListener((view) -> onClickHandler.accept(position));
    }

    private static class ViewHolder {

        // элемент для вывода изображения животного
        ImageView ivwAnimal;

        // поля для вывода информации о животном
        TextView txvAnimalName, txvAnimalBreed, txvAnimalOwner;

        LinearLayout layAnimalItem;

        // позиция элемента в коллекции
        int position;


        // конструктор инициализирующий
        private ViewHolder(View view, int position) {

            this.position = position;

            layAnimalItem = view.findViewById(R.id.lay_animal_item);

            txvAnimalName = view.findViewById(R.id.txv_animal_name);
            txvAnimalBreed = view.findViewById(R.id.txv_animal_breed);
            txvAnimalOwner = view.findViewById(R.id.txv_animal_owner);

            ivwAnimal = view.findViewById(R.id.ivw_animal_image);
        }
    }

}
