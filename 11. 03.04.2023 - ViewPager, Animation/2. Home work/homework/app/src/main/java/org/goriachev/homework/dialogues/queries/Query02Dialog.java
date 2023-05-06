package org.goriachev.homework.dialogues.queries;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.goriachev.homework.R;
import org.goriachev.homework.utils.Utils;

import java.util.function.Consumer;


// Класс Диалог для ввода параметров запроса 2
// Выбирает информацию о врачах, для которых значение в поле Процент отчисления на зарплату,
// больше 2.3% (задавать параметром)
public class Query02Dialog extends DialogFragment {

    // callback для обработки результата ввода
    private Consumer<Double> onSubmitCallback;

    private EditText etxPercent;



    // конструктор
    public Query02Dialog(@NonNull Consumer<Double> onSubmitCallback) {
        this.onSubmitCallback = onSubmitCallback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.query02_title)
                .setView(R.layout.dialog_query02)
                .setPositiveButton(R.string.execute_label, submitClickListener)
                .setNegativeButton(R.string.cancel_label, null);


        return builder.create();
    }

    private final DialogInterface.OnClickListener submitClickListener = (dialogInterface, which) -> {
        etxPercent = getDialog().findViewById(R.id.etx_doctor_percent);
        onSubmitCallback.accept(Utils.tryParseDouble(etxPercent.getText().toString()));
    };
}
