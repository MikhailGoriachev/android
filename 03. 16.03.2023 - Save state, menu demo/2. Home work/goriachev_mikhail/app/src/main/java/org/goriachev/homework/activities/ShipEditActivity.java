package org.goriachev.homework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.goriachev.homework.R;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.models.ShipType;
import org.goriachev.homework.utils.Utils;

import java.util.concurrent.atomic.AtomicBoolean;


public class ShipEditActivity extends AppCompatActivity {

    public static final int SHIP_EDIT_ACTIVITY_ID = 2;

    // название папки с картинками
    private static final String IMAGES_DIR = "ships/";

    // исходная информация о судне
    private Ship sourceShip;

    // судно
    private Ship ship;

    // элемент для вывода изображения судна
    ImageView ivwShip;

    // поля для ввода информации о судне
    EditText etxShipLoadCapacity, etxShipDestination, etxShipCargoType, etxShipCargoWeight, etxShipPrice;

    RadioGroup rbgShipType;

    RadioButton rbtShipType1, rbtShipType2, rbtShipType3;

    SwitchMaterial swtShipAnchorage, swtShipRefueling, swtShipPilot;

    // элемент для вывода стоимости
    TextView txvShipCost;

    // обработчик создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_edit);

        ship = getIntent().getParcelableExtra(Ship.class.getCanonicalName());
        sourceShip = ship.clone();

        // поиск элементов о судне
        rbgShipType = findViewById(R.id.rbg_ship_type);
        rbtShipType1 = findViewById(R.id.rbt_ship_type_1);
        rbtShipType2 = findViewById(R.id.rbt_ship_type_2);
        rbtShipType3 = findViewById(R.id.rbt_ship_type_3);

        etxShipLoadCapacity = findViewById(R.id.etx_ship_load_capacity);
        etxShipDestination = findViewById(R.id.etx_ship_destination);
        etxShipCargoType = findViewById(R.id.etx_ship_weight_type);
        etxShipCargoWeight = findViewById(R.id.etx_ship_weight);
        etxShipPrice = findViewById(R.id.etx_ship_price);

        swtShipAnchorage = findViewById(R.id.swt_ship_anchorage);
        swtShipRefueling = findViewById(R.id.swt_ship_refueling);
        swtShipPilot = findViewById(R.id.swt_ship_pilot);

        ivwShip = findViewById(R.id.ivw_ship_image);

        txvShipCost = findViewById(R.id.txv_ship_cost);

        rbgShipType.setOnCheckedChangeListener(this::onCheckedChangeShipType);
        swtShipAnchorage.setOnCheckedChangeListener((b, v) -> ship.setAnchorage(v));
        swtShipRefueling.setOnCheckedChangeListener((b, v) -> ship.setRefueling(v));
        swtShipPilot.setOnCheckedChangeListener((b, v) -> ship.setPilot(v));

        // установка валидаторов (обработчиков ввода)
        setValidators();

        onUpdateShip();
    }

    // обработка события изменения судна
    private void onUpdateShip() {
        if (ship == null)
            return;

        Utils.setViewImage(ivwShip, IMAGES_DIR + ship.getShipType().getFileName(), getApplicationContext());

        etxShipLoadCapacity.setText(Utils.intFormat(ship.getLoadCapacity()));
        etxShipDestination.setText(ship.getDestination());
        etxShipCargoType.setText(ship.getCargoType());
        etxShipCargoWeight.setText(Utils.intFormat(ship.getCargoWeight()));
        etxShipPrice.setText(Utils.intFormat(ship.getPrice()));

        swtShipAnchorage.setChecked(ship.isAnchorage());
        swtShipRefueling.setChecked(ship.isRefueling());
        swtShipPilot.setChecked(ship.isPilot());

        txvShipCost.setText(Utils.longFormat(ship.getCost()));

        setShipType(ship.getShipType().getName());
    }

    // обновление стоимости
    private void updateCost(boolean isValid) {
        txvShipCost.setText(isValid ? Utils.longFormat(ship.getCost()) : "———");
    }

    // сохранение
    public void save(View view) {
        Intent intent = new Intent();

        try {
            ship.setLoadCapacity(Integer.parseInt(etxShipLoadCapacity.getText().toString()));
            ship.setDestination(etxShipDestination.getText().toString());
            ship.setCargoType(etxShipCargoType.getText().toString());
            ship.setCargoWeight(Integer.parseInt(etxShipCargoWeight.getText().toString()));
            ship.setPrice(Integer.parseInt(etxShipPrice.getText().toString()));

            intent.putExtra(Ship.class.getCanonicalName(), ship);

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
        ship = sourceShip.clone();

        onUpdateShip();
    }

    // увеличение веса груза на 20 тонн
    public void increaseCargoWeight(View view) throws Exception {

        final int VALUE = 20;

        ship.setCargoWeight(Math.min(ship.getCargoWeight() + VALUE, ship.getLoadCapacity()));
        onUpdateShip();
    }

    // уменьшение веса груза на 20 тонн
    public void decreaseCargoWeight(View view) throws Exception {
        final int VALUE = 20;

        ship.setCargoWeight(Math.max(ship.getCargoWeight() - VALUE, 0));
        onUpdateShip();
    }

    // завершение активности
    public void finish(View view) {
        finish();
    }

    // обработчик для switch выбора типа судна
    private void onCheckedChangeShipType(RadioGroup radioGroup, int id) {
        try {
            Object[] value = new Object[4];
            switch (id) {
                case R.id.rbt_ship_type_1:
                    value = Utils.shipTypes.get(0);
                    break;
                case R.id.rbt_ship_type_2:
                    value = Utils.shipTypes.get(1);
                    break;
                default:
                    value = Utils.shipTypes.get(2);
            }

            ship.setShipType(new ShipType(
                    (String) value[0],
                    (String) value[1],
                    (int) value[2],
                    (int) value[3]
            ));

            System.out.println("Новый тип: " + ship.getShipType().getName());

            Utils.setViewImage(ivwShip, IMAGES_DIR + ship.getShipType().getFileName(), getApplicationContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // установка значения породы
    private void setShipType(String breed) {
        final String SHIP_TYPE_1 = "Танкер";
        final String SHIP_TYPE_2 = "Контейнеровоз";
        final String SHIP_TYPE_3 = "Рыболовецкое судно";

        System.out.println("Тип: " + breed);

        switch (breed) {
            case SHIP_TYPE_1:
                rbtShipType1.setChecked(true);
                break;
            case SHIP_TYPE_2:
                rbtShipType2.setChecked(true);
                break;
            default:
                rbtShipType3.setChecked(true);
        }
    }

    // установка валидаторов (обработчиков ввода)
    private void setValidators() {

        // флаг валидности грузоподъмности
        AtomicBoolean isCapacityValid = new AtomicBoolean(true);

        // флаг валидности веса груза
        AtomicBoolean isWeightValid = new AtomicBoolean(true);

        // флаг валидности цены
        AtomicBoolean isPriceValid = new AtomicBoolean(true);

        Utils.addTextChangedListener(etxShipLoadCapacity, (value) -> {
            try {

                isCapacityValid.set(Utils.isValidEditText(etxShipLoadCapacity, (s) -> {
                            Integer val = Utils.tryParseInt(s);
                            return val != null && val >= 0 && val >= ship.getCargoWeight();
                        },
                        "Грузоподъёмность судна не должна быть меньше 0 и меньше веса груза " + ship.getCargoWeight(),
                        getApplicationContext()));

                ship.setLoadCapacity((isCapacityValid.get() && isWeightValid.get())
                        ? Utils.tryParseInt(value)
                        : ship.getLoadCapacity());

                updateCost(isCapacityValid.get() && isWeightValid.get() && isPriceValid.get());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Utils.addTextChangedListener(etxShipDestination, (value) -> {
            try {
                ship.setDestination(
                        Utils.isValidEditText(etxShipDestination, (s) -> !s.isEmpty(),
                                "Поле пункта назначения должно быть заполнено",
                                getApplicationContext())
                                ? value
                                : ship.getDestination()

                );

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Utils.addTextChangedListener(etxShipCargoType, (value) -> {
            try {
                ship.setCargoType(
                        Utils.isValidEditText(etxShipCargoType, (s) -> !s.isEmpty(),
                                "Поле типа груза должно быть заполнено",
                                getApplicationContext())
                                ? value
                                : ship.getCargoType()
                );

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Utils.addTextChangedListener(etxShipCargoWeight, (value) -> {
            try {

                isWeightValid.set(Utils.isValidEditText(etxShipCargoWeight, (s) -> {
                            Integer val = Utils.tryParseInt(s);
                            return val != null && val >= 0 && val <= ship.getLoadCapacity();
                        },
                        "Вес груза не должен быть меньше 0 и больше грузоподъёмности " + ship.getLoadCapacity(),
                        getApplicationContext()));

                ship.setCargoWeight((isCapacityValid.get() && isWeightValid.get())
                        ? Utils.tryParseInt(value)
                        : ship.getCargoWeight());

                updateCost(isCapacityValid.get() && isWeightValid.get() && isPriceValid.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Utils.addTextChangedListener(etxShipPrice, (value) -> {
            try {
                isPriceValid.set(Utils.isValidEditText(etxShipPrice, (s) -> {
                            Integer val = Utils.tryParseInt(s);
                            return val != null && val >= 0;
                        },
                        "Цена единицы груза должна быть больше 0",
                        getApplicationContext()));

                ship.setPrice(isPriceValid.get()
                        ? Utils.tryParseInt(value)
                        : ship.getPrice()
                );

                updateCost(isCapacityValid.get() && isWeightValid.get() && isPriceValid.get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}