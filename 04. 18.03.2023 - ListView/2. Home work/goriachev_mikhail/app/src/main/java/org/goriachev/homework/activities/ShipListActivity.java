package org.goriachev.homework.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.ShipAdapter;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Animal;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.utils.Utils;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShipListActivity extends AppCompatActivity {

    public static final int SHIP_LIST_ACTIVITY_ID = 4;

    // адаптер судна
    private ShipAdapter shipAdapter;

    // судна
    private List<Ship> ships;

    // listView для суден
    private ListView lvwShips;


    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_list);

        ships = new ArrayList<>(getIntent().getParcelableArrayListExtra("ships"));

        lvwShips = findViewById(R.id.lvw_ships);

        shipAdapter = new ShipAdapter(this, R.layout.ship_view_item, ships, this::onClickShipHandler);

        lvwShips.setAdapter(shipAdapter);
    }

    // обработчик события выбора судна в списке
    private void onClickShipHandler(int position) {
        Intent intent = new Intent(this, ShipEditActivity.class);
        intent.putExtra(Ship.class.getCanonicalName(), ships.get(position));
        intent.putExtra("position", position);

        startActivityForResult(intent, ShipEditActivity.SHIP_EDIT_ACTIVITY_ID);
    }

    // завершение активности
    public void finish(View view) {
        var intent = new Intent();

        intent.putParcelableArrayListExtra("ships", (ArrayList<Ship>) ships);
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
            case ShipEditActivity.SHIP_EDIT_ACTIVITY_ID:
                if (data != null) {
                    ships.set(
                            data.getIntExtra("position", -1),
                            data.getParcelableExtra(Ship.class.getCanonicalName())
                    );
                    shipAdapter.notifyDataSetChanged();
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
        outState.putParcelableArrayList("ships", (ArrayList<Ship>) ships);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ships.clear();
        ships.addAll(savedInstanceState.getParcelableArrayList("ships"));
    }
}