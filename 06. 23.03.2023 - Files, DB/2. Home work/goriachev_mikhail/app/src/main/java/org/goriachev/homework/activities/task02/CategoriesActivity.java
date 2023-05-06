package org.goriachev.homework.activities.task02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.goriachev.homework.R;
import org.goriachev.homework.middleware.BooksDatabaseHelper;

public class CategoriesActivity extends AppCompatActivity {
    
    // объект для подключения к базе данных
    BooksDatabaseHelper helper;

    // объект для работы с базой данных
    SQLiteDatabase database;

    // курсор для получения результата
    Cursor cursor;

    // представление списка данных
    private ListView lvwCategories;

    // адаптер для представления списка данных
    private SimpleCursorAdapter adapter;


    // обработка события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        helper = new BooksDatabaseHelper(this);

        lvwCategories = findViewById(R.id.lvw_categories);
    }

    @Override
    public void onResume() {
        super.onResume();

        database = helper.open();

        cursor = database.rawQuery("select id as _id, * from categories", null);

        adapter = new SimpleCursorAdapter(
                this,
                R.layout.view_list_item_category,
                cursor,
                new String[] {"_id", "category"},
                new int[] {R.id.txv_id, R.id.txv_category_name }
        );

        lvwCategories.setAdapter(adapter);
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

    // обработка уничтожения активности
    @Override
    protected void onDestroy() {
        super.onDestroy();

        database.close();
        cursor.close();
    }
}