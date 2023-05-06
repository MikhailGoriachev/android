package org.goriachev.homework.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.AnimalAdapter;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class AnimalListActivity extends AppCompatActivity {

    public static final int ANIMAL_LIST_ACTIVITY_ID = 3;

    // животные
    private List<Animal> animals;

    // recyclerView для животных
    private RecyclerView rclAnimals;

    // адаптер животного
    private AnimalAdapter animalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_list);

        animals = new ArrayList<>(getIntent().getParcelableArrayListExtra("animals"));

        rclAnimals = findViewById(R.id.rcl_animals);
        animalAdapter = new AnimalAdapter(this, animals, this::edit, this::delete);
        rclAnimals.setAdapter(animalAdapter);
    }

    // обработчик события выбора животного в списке
    private void edit(int position) {
        Intent intent = new Intent(this, AnimalEditActivity.class);
        intent.putExtra("isCreate", false);
        intent.putExtra(Animal.class.getCanonicalName(), animals.get(position));
        intent.putExtra("position", position);

        startActivityForResult(intent, AnimalEditActivity.ANIMAL_EDIT_ACTIVITY_ID);
    }

    // обработчик события добавления записи
    private void add() {
        Intent intent = new Intent(this, AnimalEditActivity.class);
        intent.putExtra("isCreate", true);
        intent.putExtra(Animal.class.getCanonicalName(), new Animal());
        intent.putExtra("position", -1);

        startActivityForResult(intent, AnimalEditActivity.ANIMAL_EDIT_ACTIVITY_ID);
    }

    // удаление элемента
    private void delete(int position) {
        animals.remove(position);
        animalAdapter.notifyDataSetChanged();
    }

    // завершение активности
    public void finish(View view) {
        var intent = new Intent();

        intent.putParcelableArrayListExtra("animals", (ArrayList<Animal>) animals);
        setResult(ResultCode.RESULT_OK, intent);

        finish();
    }

    // обработчик события получения результата из активности
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != ResultCode.RESULT_OK)
            Utils.showSnakeBar(null, "Ошибка с кодом: " + resultCode);

        switch (requestCode) {
            case AnimalEditActivity.ANIMAL_EDIT_ACTIVITY_ID:
                if (data != null) {

                    Animal item = data.getParcelableExtra(Animal.class.getCanonicalName());

                    if (data.getBooleanExtra("isCreate", false))
                        animals.add(item);
                    else
                        animals.set(
                                data.getIntExtra("position", -1),
                                item
                        );

                    animalAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    // работа с главным меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_collection_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // обработка пунктов меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mni_exit:
                finish(null);
                break;
            case R.id.mni_add_item:
                add();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("animals", (ArrayList<Animal>) animals);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        animals.clear();
        animals.addAll(savedInstanceState.getParcelableArrayList("animals"));
    }
}