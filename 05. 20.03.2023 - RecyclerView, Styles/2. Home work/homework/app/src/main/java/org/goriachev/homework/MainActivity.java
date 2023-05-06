package org.goriachev.homework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import org.goriachev.homework.activities.AnimalListActivity;
import org.goriachev.homework.activities.ShipEditActivity;
import org.goriachev.homework.activities.ShipListActivity;
import org.goriachev.homework.adapters.ShipAdapter;
import org.goriachev.homework.adapters.StringAdapter;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    // животные
    private List<Animal> animals;

    // судно
    private List<Ship> ships;

    // список данных
    RecyclerView rclData;

    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animals = Arrays.stream((new Animal[12]))
                .map(a -> Animal.factory())
                .collect(Collectors.toList());

        ships = Arrays.stream((new Ship[12]))
                .map(a -> Ship.factory())
                .collect(Collectors.toList());

        var list = new ArrayList<String>();
        list.addAll(animals.stream().map(Animal::toString).collect(Collectors.toList()));
        list.addAll(ships.stream().map(Ship::toString).collect(Collectors.toList()));

        rclData = findViewById(R.id.rcl_data);
        rclData.setAdapter(new StringAdapter(this, list));
    }

    // переход на активность со списком животных
    public void showAnimalList(View view) {
        Intent intent = new Intent(this, AnimalListActivity.class);
        intent.putParcelableArrayListExtra("animals", (ArrayList<Animal>) animals);

        startActivityForResult(intent, AnimalListActivity.ANIMAL_LIST_ACTIVITY_ID);
    }

    // переход на активность со списком суден
    public void showShipList(View view) {
        Intent intent = new Intent(this, ShipListActivity.class);
        intent.putParcelableArrayListExtra("ships", (ArrayList<Ship>) ships);

        startActivityForResult(intent, ShipListActivity.SHIP_LIST_ACTIVITY_ID);
    }

    // завершение активности
    public void finish(View view) {
        finish();
    }

    // обработчик события получения результата из активности
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != ResultCode.RESULT_OK)
            Utils.showSnakeBar(null, "Ошибка с кодом: " + resultCode);
        switch (requestCode) {
            case AnimalListActivity.ANIMAL_LIST_ACTIVITY_ID:
                if (data != null) {
                    animals = data.getParcelableArrayListExtra("animals");
                }
                break;

            case ShipListActivity.SHIP_LIST_ACTIVITY_ID:
                if (data != null)
                    ships = data.getParcelableArrayListExtra("ships");
                break;
        }
    }

    // работа с главным меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // обработка пунктов меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mni_animal_list:
                showAnimalList(null);
                break;
            case R.id.mni_edit_ship:
                showShipList(null);
                break;
            case R.id.mni_exit:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("animals", (ArrayList<Animal>) animals);
        outState.putParcelableArrayList("ships", (ArrayList<Ship>) ships);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        animals = savedInstanceState.getParcelableArrayList("animals");
        ships = savedInstanceState.getParcelableArrayList("ships");
    }
}