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
import org.goriachev.homework.repositories.PatientsRepository;
import org.goriachev.homework.utils.Utils;

import java.util.function.Consumer;


// Класс Диалог для ввода параметров запроса 1
// Выбирает информацию о пациентах с фамилиями, начинающимися на заданную параметром
// последовательность букв
public class Query01Dialog extends DialogFragment {

    // callback для обработки результата ввода
    private Consumer<String> onSubmitCallback;

    private EditText etxSurname;



    // конструктор
    public Query01Dialog(@NonNull Consumer<String> onSubmitCallback) {
        this.onSubmitCallback = onSubmitCallback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.query01_title)
                .setView(R.layout.dialog_query01)
                .setPositiveButton(R.string.execute_label, submitClickListener)
                .setNeutralButton(R.string.generate_value_label, generateClickListener)
                .setNegativeButton(R.string.cancel_label, null);


        return builder.create();
    }

    private final DialogInterface.OnClickListener submitClickListener = (dialogInterface, which) -> {
        etxSurname = getDialog().findViewById(R.id.etx_surname);
        onSubmitCallback.accept(etxSurname.getText().toString());
    };

    private final DialogInterface.OnClickListener generateClickListener = (dialogInterface, which) -> {
        etxSurname = getDialog().findViewById(R.id.etx_surname);

        var respository = new PatientsRepository(getContext());

        var surname = Utils.getItem(respository.getAll()).getPerson().getSurname();

        onSubmitCallback.accept(surname);
    };
}
