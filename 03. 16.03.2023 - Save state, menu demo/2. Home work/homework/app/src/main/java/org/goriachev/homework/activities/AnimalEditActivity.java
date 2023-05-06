package org.goriachev.homework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.goriachev.homework.R;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.utils.Utils;

public class AnimalEditActivity extends AppCompatActivity {

    public static final int ANIMAL_EDIT_ACTIVITY_ID = 1;

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

    RadioGroup rbgBreed;

    RadioButton rbtBreed1, rbtBreed2, rbtBreed3;


    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_edit);

        animal = getIntent().getParcelableExtra(Animal.class.getCanonicalName());
        sourceAnimal = animal.clone();

        // поиск элементов о животном
        rbgBreed = findViewById(R.id.rbg_breed);
        rbtBreed1 = findViewById(R.id.rbt_breed_1);
        rbtBreed2 = findViewById(R.id.rbt_breed_2);
        rbtBreed3 = findViewById(R.id.rbt_breed_3);

        etxAnimalName = findViewById(R.id.etx_animal_name);
        etxAnimalAge = findViewById(R.id.etx_animal_age);
        etxAnimalWeight = findViewById(R.id.etx_animal_weight);
        etxAnimalOwner = findViewById(R.id.etx_animal_owner);
        
        ivwAnimal = findViewById(R.id.ivw_animal_image);

        swtSpecialDiet = findViewById(R.id.swt_animal_special_diet);
        swtFreeKeeping = findViewById(R.id.swt_animal_free_keeping);

        // установка валидаторов (обработчиков ввода)
        setValidators();

        rbgBreed.setOnCheckedChangeListener(this::onCheckedChangeBreed);
        swtFreeKeeping.setOnCheckedChangeListener((b, v) -> animal.setFreeKeeping(v));
        swtSpecialDiet.setOnCheckedChangeListener((b, v) -> animal.setSpecialDiet(v));

        onUpdateAnimal();
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

        setBreed(animal.getBreed());
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

    // обработчик для switch выбора породы
    private void onCheckedChangeBreed(RadioGroup radioGroup, int id) {
        try {
            String[] value = new String[2];
            switch (id) {
                case R.id.rbt_breed_1:
                    value = Utils.breeds.get(0);
                    break;
                case R.id.rbt_breed_2:
                    value = Utils.breeds.get(1);
                    break;
                default:
                    value = Utils.breeds.get(2);
            }

            animal.setBreed(value[0]);
            animal.setImageFile(value[1]);

            Utils.setViewImage(ivwAnimal, IMAGES_DIR + animal.getImageFile(), getApplicationContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // установка значения породы
    private void setBreed(String breed) {
        final String BREED_1 = "Мейн-кун";
        final String BREED_2 = "Сиамская кошка";
        final String BREED_3 = "Рэгдолл";

        switch (breed) {
            case BREED_1:
                rbtBreed1.setChecked(true);
                break;
            case BREED_2:
                rbtBreed2.setChecked(true);
                break;
            default:
                rbtBreed3.setChecked(true);
        }
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