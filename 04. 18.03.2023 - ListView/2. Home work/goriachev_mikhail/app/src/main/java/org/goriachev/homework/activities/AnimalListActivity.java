package org.goriachev.homework.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.AnimalAdapter;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class AnimalListActivity extends AppCompatActivity {

    public static final int ANIMAL_LIST_ACTIVITY_ID = 3;

    // животные
    private List<Animal> animals;

    // listView для животных
    private ListView lvwAnimals;

    // адаптер животного
    private AnimalAdapter animalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_list);

        animals = new ArrayList<>(getIntent().getParcelableArrayListExtra("animals"));

        lvwAnimals = findViewById(R.id.lvw_animals);

        animalAdapter = new AnimalAdapter(this, R.layout.animal_view_item, animals, this::onClickAnimalHandler);

        lvwAnimals.setAdapter(animalAdapter);

        System.out.println("Создал: " + (animals.get(0).getName()));
    }

    // обработчик события выбора животного в списке
    private void onClickAnimalHandler(int position) {
        Intent intent = new Intent(this, AnimalEditActivity.class);
        intent.putExtra(Animal.class.getCanonicalName(), animals.get(position));
        intent.putExtra("position", position);

        startActivityForResult(intent, AnimalEditActivity.ANIMAL_EDIT_ACTIVITY_ID);
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
                    animals.set(
                            data.getIntExtra("position", -1),
                            data.getParcelableExtra(Animal.class.getCanonicalName())
                    );
                    animalAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    // работа с главным меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // обработка пунктов меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mni_exit:
                finish(null);
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