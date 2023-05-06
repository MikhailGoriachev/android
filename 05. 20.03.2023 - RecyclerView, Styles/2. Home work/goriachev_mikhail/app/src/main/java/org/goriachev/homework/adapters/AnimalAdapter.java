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
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.utils.Utils;

import java.util.List;
import java.util.function.Consumer;


public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {

    // загрузчик разметки
    private final LayoutInflater inflater;

    // контекст
    final Context context;

    // данные
    private final List<Animal> items;

    // обработчик события выбора по элемента
    private final Consumer<Integer> onClickHandler;

    // обработчик события удаления элемента
    private final Consumer<Integer> onDeleteHandler;

    // конструктор инициализирующий
    public AnimalAdapter(@NonNull Context context, @NonNull List<Animal> objects,
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
        return new ViewHolder(inflater.inflate(R.layout.animal_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        var animal = items.get(position);

        Utils.setViewImage(holder.ivwAnimal, "animals/" + animal.getImageFile(), context);
        holder.txvAnimalName.setText(animal.getName());
        holder.txvAnimalBreed.setText(animal.getBreed());
        holder.txvAnimalOwner.setText(animal.getOwner());

        holder.layAnimalItem.setOnClickListener((view) -> onClickHandler.accept(position));
        holder.btnDelete.setOnClickListener((view) -> onDeleteHandler.accept(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // элемент для вывода изображения животного
        ImageView ivwAnimal;

        // поля для вывода информации о животном
        TextView txvAnimalName, txvAnimalBreed, txvAnimalOwner;

        LinearLayout layAnimalItem;

        Button btnDelete;

        // конструктор инициализирующий
        public ViewHolder(View view) {
            super(view);

            layAnimalItem = view.findViewById(R.id.lay_animal_item);

            txvAnimalName = view.findViewById(R.id.txv_animal_name);
            txvAnimalBreed = view.findViewById(R.id.txv_animal_breed);
            txvAnimalOwner = view.findViewById(R.id.txv_animal_owner);

            ivwAnimal = view.findViewById(R.id.ivw_animal_image);

            btnDelete = view.findViewById(R.id.btn_delete);
        }
    }

}
