package org.goriachev.homework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.goriachev.homework.R;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.utils.Utils;

public class AnimalEditActivity extends AppCompatActivity {

    public static final int ANIMAL_EDIT_ACTIVITY_ID = 1;

    // животное
    private Animal animal;

    // элемент для вывода изображения животного
    ImageView ivwAnimal;

    // поля для ввода данных
    EditText etxAnimalName, etxAnimalBreed, etxAnimalAge, etxAnimalWeight, etxAnimalOwner;


    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_edit);

        animal = getIntent().getParcelableExtra(Animal.class.getCanonicalName());

        // поиск элементов о животном
        etxAnimalName = findViewById(R.id.etx_animal_name);
        etxAnimalBreed = findViewById(R.id.etx_animal_breed);
        etxAnimalAge = findViewById(R.id.etx_animal_age);
        etxAnimalWeight = findViewById(R.id.etx_animal_weight);
        etxAnimalOwner = findViewById(R.id.etx_animal_owner);
        ivwAnimal = findViewById(R.id.ivw_animal_image);

        // установка валидаторов (обработчиков ввода)
        setValidators();

        onUpdateAnimal();
    }

    // обработка события изменения животного
    private void onUpdateAnimal() {
        if (animal == null)
            return;

        Utils.setViewImage(ivwAnimal, "animals/" + animal.getImageFile(), getApplicationContext());
        etxAnimalName.setText(animal.getName());
        etxAnimalBreed.setText(animal.getBreed());
        etxAnimalAge.setText(Utils.intFormat(animal.getAge()));
        etxAnimalWeight.setText(Utils.doubleFormat(animal.getWeight(), 2));
        etxAnimalOwner.setText(animal.getOwner());
    }

    // увеличение возраста на 1 год
    public void increaseAge(View view) throws Exception {
        animal.setAge(animal.getAge() + 1);
        onUpdateAnimal();
    }

    // уменьшение возраста на 1 год
    public void decreaseAge(View view) throws Exception {
        if (animal.getAge() > 1) {
            animal.setAge(animal.getAge() - 1);
            onUpdateAnimal();
        }
    }

    // увеличение веса на 1 кг
    public void increaseWeight(View view) throws Exception {
        animal.setWeight(animal.getWeight() + 1d);
        onUpdateAnimal();
    }

    // уменьшение веса на 1 кг
    public void decreaseWeight(View view) throws Exception {
        if (animal.getWeight() > 1.1) {
            animal.setWeight(animal.getWeight() - 1d);
            onUpdateAnimal();
        }
    }

    // сохранение
    public void save(View view) {
        Intent intent = new Intent();

        try {
            animal.setName(etxAnimalName.getText().toString());
            animal.setBreed(etxAnimalBreed.getText().toString());
            animal.setAge(Integer.parseInt(etxAnimalAge.getText().toString()));
            animal.setWeight(Double.parseDouble(etxAnimalWeight.getText().toString()));
            animal.setOwner(etxAnimalOwner.getText().toString());

            intent.putExtra(Animal.class.getCanonicalName(), animal);

            setResult(ResultCode.RESULT_OK, intent);

            finish();
        } catch (NumberFormatException ex) {
            Utils.showSnakeBar(view, "Введите числовое значение");
        } catch (Exception ex) {
            Utils.showSnakeBar(view, ex.getMessage());
        }
    }

    // сбросить поля ввода
    public void reset(View view) {
        etxAnimalName.setText("");
        etxAnimalBreed.setText("");
        etxAnimalAge.setText("0");
        etxAnimalWeight.setText("0.0");
        etxAnimalOwner.setText("");
    }

    // завершение активности
    public void finish(View view) {
        finish();
    }

    // установка валидаторов (обработчиков ввода)
    private void setValidators() {

        Utils.addTextChangedListener(etxAnimalName, (value) -> {
            try {
                animal.setName(
                        Utils.isValidEditText(etxAnimalName, (s) -> !s.isEmpty(),
                                "Поле клички должно быть заполнено",
                                getApplicationContext())
                                ? value
                                : animal.getName()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Utils.addTextChangedListener(etxAnimalBreed, (value) -> {
            try {
                animal.setBreed(
                        Utils.isValidEditText(etxAnimalBreed, (s) -> !s.isEmpty(),
                                "Поле породы должно быть заполнено",
                                getApplicationContext())
                                ? value
                                : animal.getBreed()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Utils.addTextChangedListener(etxAnimalAge, (value) -> {
            try {
                animal.setAge(
                        Utils.isValidEditText(etxAnimalAge, (s) -> {
                                    Integer val = Utils.tryParseInt(s);
                                    return val != null && val >= 0;
                                },
                                "Возраст должен быть больше или равен 0",
                                getApplicationContext())
                                ? Utils.tryParseInt(value)
                                : animal.getAge()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Utils.addTextChangedListener(etxAnimalWeight, (value) -> {
            try {
                animal.setWeight(
                        Utils.isValidEditText(etxAnimalWeight, (s) -> {
                                    Double val = Utils.tryParseDouble(s);
                                    return val != null && val >= 0.1;
                                },
                                "Вес должен быть больше или равен 0.1",
                                getApplicationContext())
                                ? Utils.tryParseDouble(value)
                                : animal.getWeight()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Utils.addTextChangedListener(etxAnimalOwner, (value) -> {
            try {
                animal.setOwner(
                        Utils.isValidEditText(etxAnimalOwner, (s) -> !s.isEmpty(),
                                "Поле владельца должно быть заполнено",
                                getApplicationContext())
                                ? value
                                : animal.getName()
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}