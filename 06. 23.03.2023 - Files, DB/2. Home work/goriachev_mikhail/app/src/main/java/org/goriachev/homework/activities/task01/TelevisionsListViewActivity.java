package org.goriachev.homework.activities.task01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.task01.TelevisionRecyclerAdapter;
import org.goriachev.homework.models.task01.Television;

import java.util.List;

public class TelevisionsListViewActivity extends AppCompatActivity {

    // список телевизоров
    List<Television> items;

    // элемент для списка телевизоров
    RecyclerView rcvTelevisions;

    // адаптер для вывода элементов
    TelevisionRecyclerAdapter recyclerAdapter;

    // элемент для вывода заголовка
    TextView txvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_televisions_list_view);

        var intent = getIntent();

        items = intent.getParcelableArrayListExtra("items");

        recyclerAdapter = new TelevisionRecyclerAdapter(this, items);

        rcvTelevisions = findViewById(R.id.rcv_televisions);
        rcvTelevisions.setAdapter(recyclerAdapter);

        txvTitle = findViewById(R.id.txv_title);
        txvTitle.setText(intent.getStringExtra("title"));
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
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}