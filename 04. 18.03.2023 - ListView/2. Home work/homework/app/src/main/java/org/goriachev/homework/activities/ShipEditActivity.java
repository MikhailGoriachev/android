package org.goriachev.homework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.goriachev.homework.R;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.models.ShipType;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


public class ShipEditActivity extends AppCompatActivity {

    public static final int SHIP_EDIT_ACTIVITY_ID = 2;

    // название папки с картинками
    private static final String IMAGES_DIR = "ships/";

    // исходная информация о судне
    private Ship sourceShip;

    // судно
    private Ship ship;

    // список типов суден
    private List<String> shipTypes;

    // список типов груза
    private List<String> shipCargoTypes;

    // адаптер для списка типов груза
    private ArrayAdapter<String> shipCargoTypeAdapter;

    // элемент для вывода изображения судна
    ImageView ivwShip;

    // поля для ввода информации о судне
    EditText etxShipLoadCapacity, etxShipDestination, etxShipCargoWeight, etxShipPrice;

    Spinner spnShipType, spnShipCargoType;

    SwitchMaterial swtShipAnchorage, swtShipRefueling, swtShipSpecialPier;

    // элемент для вывода стоимости
    TextView txvShipCost;

    // обработчик создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_edit);

        ship = getIntent().getParcelableExtra(Ship.class.getCanonicalName());
        sourceShip = ship.clone();

        spnShipType = findViewById(R.id.spn_ship_type);

        shipTypes = new ArrayList<>(Utils.shipTypes.keySet());

        var shipTypeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                shipTypes
        );
        shipTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnShipType.setAdapter(shipTypeAdapter);
        spnShipType.setOnItemSelectedListener(shipTypeSpinnerOnItemSelectedListener);

        spnShipCargoType = findViewById(R.id.spn_ship_cargo_type);

        shipCargoTypes = new ArrayList<>(Utils.getCargoTypesByShip(ship.getShipType().getName()));

        shipCargoTypeAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                shipCargoTypes
        );
        shipCargoTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnShipCargoType.setAdapter(shipCargoTypeAdapter);
        spnShipCargoType.setOnItemSelectedListener(shipCargoTypeSpinnerOnItemSelectedListener);


        etxShipLoadCapacity = findViewById(R.id.etx_ship_load_capacity);
        etxShipDestination = findViewById(R.id.etx_ship_destination);
        etxShipCargoWeight = findViewById(R.id.etx_ship_weight);
        etxShipPrice = findViewById(R.id.etx_ship_price);

        swtShipAnchorage = findViewById(R.id.swt_ship_anchorage);
        swtShipRefueling = findViewById(R.id.swt_ship_refueling);
        swtShipSpecialPier = findViewById(R.id.swt_ship_special_pier);

        ivwShip = findViewById(R.id.ivw_ship_image);

        txvShipCost = findViewById(R.id.txv_ship_cost);

        swtShipAnchorage.setOnCheckedChangeListener((b, v) -> ship.setAnchorage(v));
        swtShipRefueling.setOnCheckedChangeListener((b, v) -> ship.setRefueling(v));
        swtShipSpecialPier.setOnCheckedChangeListener((b, v) -> ship.setSpecialPier(v));

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
        etxShipCargoWeight.setText(Utils.intFormat(ship.getCargoWeight()));
        etxShipPrice.setText(Utils.intFormat(ship.getPrice()));

        swtShipAnchorage.setChecked(ship.isAnchorage());
        swtShipRefueling.setChecked(ship.isRefueling());
        swtShipSpecialPier.setChecked(ship.isSpecialPier());

        txvShipCost.setText(Utils.longFormat(ship.getCost()));

        spnShipCargoType.setSelection(shipCargoTypes.indexOf(ship.getCargoType()));
        spnShipType.setSelection(shipTypes.indexOf(ship.getShipType().getName()));
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
            ship.setCargoWeight(Integer.parseInt(etxShipCargoWeight.getText().toString()));
            ship.setPrice(Integer.parseInt(etxShipPrice.getText().toString()));

            intent.putExtra(Ship.class.getCanonicalName(), ship);

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
        ship = sourceShip.clone();

        onUpdateShip();
    }

    // увеличение веса груза на 100 тонн
    public void increaseCargoWeight(View view) throws Exception {

        final int VALUE = 100;

        ship.setCargoWeight(Math.min(ship.getCargoWeight() + VALUE, ship.getLoadCapacity()));
        onUpdateShip();
    }

    // уменьшение веса груза на 100 тонн
    public void decreaseCargoWeight(View view) throws Exception {
        final int VALUE = 100;

        ship.setCargoWeight(Math.max(ship.getCargoWeight() - VALUE, 0));
        onUpdateShip();
    }

    // увеличение цены за 1 тонну груза на 10$
    public void increasePrice(View view) throws Exception {

        final int VALUE = 10;

        ship.setPrice(ship.getPrice() + VALUE);
        onUpdateShip();
    }

    // уменьшение цены за 1 тонну груза на 10$
    public void decreasePrice(View view) throws Exception {
        final int VALUE = 10;

        ship.setPrice(Math.max(ship.getPrice() - VALUE, 0));
        onUpdateShip();
    }

    // завершение активности
    public void finish(View view) {
        finish();
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

    // обработчик спиннера типа судна
    private final AdapterView.OnItemSelectedListener shipTypeSpinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            try {

                var key = adapterView.getItemAtPosition(i);
                var value = Utils.shipTypes.get(key);

                var oldType = ship.getShipType().getName();

                ship.setShipType(new ShipType(
                        (String) key,
                        (String) value[0],
                        (int) value[1],
                        (int) value[2]
                ));

                Utils.setViewImage(ivwShip, IMAGES_DIR + ship.getShipType().getFileName(), getApplicationContext());

                if (!oldType.equals(ship.getShipType().getName())) {

                    var cargoTypes = Utils.getCargoTypesByShip(ship.getShipType().getName());

                    ship.setCargoType(cargoTypes.get(0));
                    shipCargoTypes.clear();
                    shipCargoTypes.addAll(cargoTypes);
                    shipCargoTypeAdapter.notifyDataSetChanged();
                    spnShipCargoType.setSelection(0);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    // обработчик спиннера типа груза
    private final AdapterView.OnItemSelectedListener shipCargoTypeSpinnerOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                ship.setCargoType((String) adapterView.getItemAtPosition(i));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
}