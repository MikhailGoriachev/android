package org.goriachev.homework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.goriachev.homework.activities.AnimalEditActivity;
import org.goriachev.homework.activities.ShipEditActivity;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.utils.Utils;

public class MainActivity extends AppCompatActivity {

    // животное
    private Animal animal;

    // элемент для вывода изображения животного
    ImageView ivwAnimal;

    // поля для вывода информации о животном
    TextView txvAnimalName, txvAnimalBreed, txvAnimalAge, txvAnimalWeight, txvAnimalOwner;

    // судно
    private Ship ship;

    // элемент для вывода изображения судна
    ImageView ivwShip;

    // поля для вывода информации о судне
    TextView txvShipType, txvShipLoadCapacity, txvShipDestination, txvShipCargoType, txvShipCargoWeight, txvShipPrice;

    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // создание объекта животного
            animal = Animal.factory();

            // поиск элементов о животном
            txvAnimalName = findViewById(R.id.txv_animal_name);
            txvAnimalBreed = findViewById(R.id.txv_animal_breed);
            txvAnimalAge = findViewById(R.id.txv_animal_age);
            txvAnimalWeight = findViewById(R.id.txv_animal_weight);
            txvAnimalOwner = findViewById(R.id.txv_animal_owner);
            ivwAnimal = findViewById(R.id.ivw_animal_image);

            onUpdateAnimal();

            // создание объекта сунда
            ship = Ship.factory();

            // поиск элементов о судне
            txvShipType = findViewById(R.id.txv_ship_type);
            txvShipLoadCapacity = findViewById(R.id.txv_ship_load_capacity);
            txvShipDestination = findViewById(R.id.txv_ship_destination);
            txvShipCargoType = findViewById(R.id.txv_ship_weight_type);
            txvShipCargoWeight = findViewById(R.id.txv_ship_weight);
            txvShipPrice = findViewById(R.id.txv_ship_price);
            ivwShip = findViewById(R.id.ivw_ship_image);

            onUpdateShip();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // обработка события изменения животного
    private void onUpdateAnimal() {
        if (animal == null)
            return;

        Utils.setViewImage(ivwAnimal, "animals/" + animal.getImageFile(), getApplicationContext());
        txvAnimalName.setText(animal.getName());
        txvAnimalBreed.setText(animal.getBreed());
        txvAnimalAge.setText(Utils.intFormat(animal.getAge()));
        txvAnimalWeight.setText(Utils.doubleFormat(animal.getWeight(), 2));
        txvAnimalOwner.setText(animal.getOwner());
    }

    // обработка события изменения судна
    private void onUpdateShip() {
        if (ship == null)
            return;

        Utils.setViewImage(ivwShip, "ships/" + ship.getShipType().getFileName(), getApplicationContext());
        txvShipType.setText(ship.getShipType().getName());
        txvShipLoadCapacity.setText(Utils.intFormat(ship.getLoadCapacity()));
        txvShipDestination.setText(ship.getDestination());
        txvShipCargoType.setText(ship.getCargoType());
        txvShipCargoWeight.setText(Utils.intFormat(ship.getCargoWeight()));
        txvShipPrice.setText(Utils.intFormat(ship.getPrice()));
    }

    // запуск активности редактирования животного
    public void showAnimalEditActivity(View view) {
        Intent intent = new Intent(this, AnimalEditActivity.class);
        intent.putExtra(Animal.class.getCanonicalName(), animal);

        startActivityForResult(intent, AnimalEditActivity.ANIMAL_EDIT_ACTIVITY_ID);
    }

    // запуск активности редактирования судна
    public void showShipEditActivity(View view) {
        Intent intent = new Intent(this, ShipEditActivity.class);
        intent.putExtra(Ship.class.getCanonicalName(), ship);

        startActivityForResult(intent, ShipEditActivity.SHIP_EDIT_ACTIVITY_ID);
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
            case AnimalEditActivity.ANIMAL_EDIT_ACTIVITY_ID:
                if (data != null) {
                    animal = data.getParcelableExtra(Animal.class.getCanonicalName());
                    onUpdateAnimal();
                }
                break;

            case ShipEditActivity.SHIP_EDIT_ACTIVITY_ID:
                if (data != null) {
                    ship = data.getParcelableExtra(Ship.class.getCanonicalName());
                    onUpdateShip();
                }
                break;
        }
    }
}