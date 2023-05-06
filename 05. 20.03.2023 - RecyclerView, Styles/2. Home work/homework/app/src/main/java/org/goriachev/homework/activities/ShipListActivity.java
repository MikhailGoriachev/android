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
import org.goriachev.homework.adapters.ShipAdapter;
import org.goriachev.homework.infrastructure.ResultCode;
import org.goriachev.homework.models.Ship;
import org.goriachev.homework.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ShipListActivity extends AppCompatActivity {

    public static final int SHIP_LIST_ACTIVITY_ID = 4;

    // адаптер судна
    private ShipAdapter shipAdapter;

    // судна
    private List<Ship> ships;

    // listView для суден
    private RecyclerView rclShips;


    // обработчик события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_list);

        ships = new ArrayList<>(getIntent().getParcelableArrayListExtra("ships"));

        rclShips = findViewById(R.id.rcl_ships);
        shipAdapter = new ShipAdapter(this, ships, this::onClickShipHandler, this::delete);
        rclShips.setAdapter(shipAdapter);
    }

    // обработчик события выбора судна в списке
    private void onClickShipHandler(int position) {
        Intent intent = new Intent(this, ShipEditActivity.class);
        intent.putExtra(Ship.class.getCanonicalName(), ships.get(position));
        intent.putExtra("position", position);

        startActivityForResult(intent, ShipEditActivity.SHIP_EDIT_ACTIVITY_ID);
    }

    // добавление элемента
    private void add(Ship ship) {
        ships.add(ship);
        shipAdapter.notifyDataSetChanged();
    }

    // удаление элемента
    private void delete(int position) {
        ships.remove(position);
        shipAdapter.notifyDataSetChanged();
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
        getMenuInflater().inflate(R.menu.basic_collection_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // обработка пунктов меню
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mni_add_item:
                add(Ship.factory());
                break;
            case R.id.mni_exit:
                finish(null);
                break;
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