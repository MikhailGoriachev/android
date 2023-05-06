package org.goriachev.homework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.goriachev.homework.R;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class AnimalEditActivity extends AppCompatActivity {

    public static final int ANIMAL_EDIT_ACTIVITY_ID = 1;

    // статус работы (создание/редактирование)
    private boolean isCreate;

    // название папки с картинками
    private static final String IMAGES_DIR = "animals/";

    // исходные данные животного
    private Animal sourceAnimal;

    // животное
    private Animal animal;

    // элемент для вывода изображения животного
    ImageView ivwAnimal;

    // поля для ввода данных
    EditText etxAnimalName, etxAnimalAge, etxAnimalWeight, etxAnimalOwner;

    SwitchMaterial swtSpecialDiet, swtFreeKeeping;

    Spinner spnBreed;

    TextView txvTitle;

    Button btnSubmit;

    // список пород
    private List<String> breeds;

    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_edit);

        try {
            animal = getIntent().getParcelableExtra(Animal.class.getCanonicalName());

            breeds = Utils.breeds.stream()
                    .map(b -> b[0])
                    .map(String::valueOf)
                    .collect(Collectors.toList());

            isCreate = getIntent().getBooleanExtra("isCreate", false);

            if (isCreate) {
                var breed = Utils.breeds.get(0);
                animal.setBreed(breed[0]);
                animal.setImageFile(breed[1]);
            }

            sourceAnimal = animal.clone();


            txvTitle = findViewById(R.id.txv_title);
            txvTitle.setText(isCreate
                    ? "Добавление домашнего животного"
                    : "Изменение домашнего животного");

            btnSubmit = findViewById(R.id.btn_submit);
            btnSubmit.setText(isCreate
                    ? "Добавить"
                    : "Сохранить");

            // инициализация спиннера пород
            initializationBreedsSpinner();

            // инициализация контроллов для ввода
            initializationInputControls();

            // установка валидаторов (обработчиков ввода)
            setValidators();

            // установка значений из модели в контроллы
            onUpdateAnimal();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // инициализация спиннера пород
    private void initializationBreedsSpinner() {
        spnBreed = findViewById(R.id.spn_breed);

        var breedAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                breeds
        );
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBreed.setAdapter(breedAdapter);
        spnBreed.setOnItemSelectedListener(breedSpinnerOnItemSelectedListener);

    }

    // инициализация контроллов для ввода
    private void initializationInputControls() {
        etxAnimalName = findViewById(R.id.etx_animal_name);
        etxAnimalAge = findViewById(R.id.etx_animal_age);
        etxAnimalWeight = findViewById(R.id.etx_animal_weight);
        etxAnimalOwner = findViewById(R.id.etx_animal_owner);

        ivwAnimal = findViewById(R.id.ivw_animal_image);

        swtSpecialDiet = findViewById(R.id.swt_animal_special_diet);
        swtFreeKeeping = findViewById(R.id.swt_animal_free_keeping);

        swtFreeKeeping.setOnCheckedChangeListener((b, v) -> animal.setFreeKeeping(v));
        swtSpecialDiet.setOnCheckedChangeListener((b, v) -> animal.setSpecialDiet(v));
    }

    // обработка события изменения животного
    private void onUpdateAnimal() {
        if (animal == null)
            return;

        Utils.setViewImage(ivwAnimal, IMAGES_DIR + animal.getImageFile(), getApplicationContext());

        etxAnimalName.setText(animal.getName());
        etxAnimalAge.setText(Utils.intFormat(animal.getAge()));
        etxAnimalWeight.setText(Utils.doubleFormat(animal.getWeight(), 2));
        etxAnimalOwner.setText(animal.getOwner());
        swtSpecialDiet.setChecked(animal.isSpecialDiet());
        swtFreeKeeping.setChecked(animal.isFreeKeeping());

        spnBreed.setSelection(breeds.indexOf(animal.getBreed()));
    }

    // увеличение возраста на 1 год
    public void increaseAge(View view) throws Exception {
        animal.setAge(animal.getAge() + 1);
        etxAnimalAge.setText(Utils.intFormat(animal.getAge()));
        etxAnimalAge.setError(null);
    }

    // уменьшение возраста на 1 год
    public void decreaseAge(View view) throws Exception {
        if (animal.getAge() > 1) {
            animal.setAge(animal.getAge() - 1);
            etxAnimalAge.setText(Utils.intFormat(animal.getAge()));
            etxAnimalAge.setError(null);
        }
    }

    // увеличение веса на 1 кг
    public void increaseWeight(View view) throws Exception {
        animal.setWeight(animal.getWeight() + 1d);
        etxAnimalWeight.setText(Utils.doubleFormat(animal.getWeight(), 2));
        etxAnimalWeight.setError(null);
    }

    // уменьшение веса на 1 кг
    public void decreaseWeight(View view) throws Exception {
        if (animal.getWeight() > 1.1) {
            animal.setWeight(animal.getWeight() - 1d);
            etxAnimalWeight.setText(Utils.doubleFormat(animal.getWeight(), 2));
            etxAnimalWeight.setError(null);
        }
    }

    // сохранение
    public void save(View view) {
        Intent intent = new Intent();

        try {
            animal.setName(etxAnimalName.getText().toString());
            animal.setAge(Integer.parseInt(etxAnimalAge.getText().toString()));
            animal.setWeight(Double.parseDouble(etxAnimalWeight.getText().toString()));
            animal.setOwner(etxAnimalOwner.getText().toString());
            animal.setSpecialDiet(swtSpecialDiet.isChecked());
            animal.setFreeKeeping(swtFreeKeeping.isChecked());

            intent.putExtra(Animal.class.getCanonicalName(), animal);
            intent.putExtra("isCreate", isCreate);

            intent.putExtra("position", getIntent().getIntExtra("position", -1));

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
        animal = sourceAnimal.clone();

        onUpdateAnimal();
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
//                throw new RuntimeException(e);
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
//                throw new RuntimeException(e);
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
//                throw new RuntimeException(e);
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
//                throw new RuntimeException(e);
            }
        });
    }

    // обработчик спиннера породы
    private final AdapterView.OnItemSelectedListener breedSpinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            try {

                var value = Utils.breeds.get(i);

                animal.setBreed(value[0]);
                animal.setImageFile(value[1]);

                Utils.setViewImage(ivwAnimal, IMAGES_DIR + animal.getImageFile(), getApplicationContext());
            } catch (Exception e) {
//                throw new RuntimeException(e);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
}