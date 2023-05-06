package org.goriachev.homework.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.goriachev.homework.R;
import org.goriachev.homework.models.webData.User;

import java.util.Locale;

public class UserActivity extends AppCompatActivity {

    private User item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        item = (User) getIntent().getSerializableExtra("item");

        TextView txvFirstLine = findViewById(R.id.txv_first_line);
        TextView txvSecondLine = findViewById(R.id.txv_second_line);
        TextView txvThirdLine = findViewById(R.id.txv_third_line);
        TextView txvFourthLine = findViewById(R.id.txv_fourth_line);
        TextView txvFifthLine = findViewById(R.id.txv_fifth_line);

        txvFirstLine.setText(String.format(Locale.UK, "ID: %d",item.getId()));
        txvSecondLine.setText(item.getUsername());
        txvThirdLine.setText(String.format("Имя: %s", item.getName()));
        txvFourthLine.setText(String.format("Почта: %s", item.getEmail()));
        txvFifthLine.setText(String.format("Веб-сайт: %s", item.getWebsite()));
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