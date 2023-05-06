package org.goriachev.homework.task02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.goriachev.homework.task02.models.Goods;
import org.goriachev.homework.task02.utils.Utils;

public class MainActivity extends AppCompatActivity {



    // товар
    private Goods goods;

    // элементы для вывода данных
    private TextView txvName, txvAmount, txvPrice, txvCost;

    // поля для ввода данных
    private EditText etxName, etxAmount, etxPrice;

    // элемент для вывода сообщения об ошибке
    private TextView txvErrorMessage;

    // обработка события создания активности
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // поиск элементов представления
        txvName = findViewById(R.id.txv_name);
        txvAmount = findViewById(R.id.txv_amount);
        txvPrice = findViewById(R.id.txv_price);
        txvCost = findViewById(R.id.txv_cost);

        etxName = findViewById(R.id.etx_name);
        etxAmount = findViewById(R.id.etx_amount);
        etxPrice = findViewById(R.id.etx_price);

        txvErrorMessage = findViewById(R.id.txv_error_message);
    }

    // обработка сохранения введённых данных
    public void saveData(View view) {
        try {

            if (etxName.getText().length() == 0 || etxAmount.getText().length() == 0
                    || etxPrice.getText().length() == 0)
                throw new Exception("Все поля должны быть заполнены");

            goods = new Goods(
                    etxName.getText().toString(),
                    Integer.parseInt(etxAmount.getText().toString()),
                    Integer.parseInt(etxPrice.getText().toString())
            );

            onUpdateGoodsInfo();
            resetErrorMessage();
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    // обновление данных товара
    private void onUpdateGoodsInfo() {
        if (goods == null)
            return;

        txvName.setText(goods.getName());
        txvAmount.setText(Utils.intFormat(goods.getAmount()));
        txvPrice.setText(Utils.intFormat(goods.getPrice()));
        txvCost.setText(Utils.intFormat(goods.getCost()));
    }

    // установка ошибки
    private void setErrorMessage(String message) {
        txvErrorMessage.setText(message);
    }

    // сброс ошибки
    private void resetErrorMessage() {
        txvErrorMessage.setText("");
    }

    // выход из приложения
    public void exit(View view) {
        finish();
    }
}