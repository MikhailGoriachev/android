package org.goriachev.homework.dialogues.queries;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Speciality;
import org.goriachev.homework.repositories.SpecialitiesRepository;

import java.util.function.Consumer;
import java.util.stream.Collectors;


// Класс Диалог для ввода параметров запроса 4
// Выбирает информацию о докторах, специальность которых задана параметром
public class Query04Dialog extends DialogFragment {

    // callback для обработки результата ввода
    private Consumer<String> onSubmitCallback;

    private Spinner spnSpeciality;



    // конструктор
    public Query04Dialog(@NonNull Consumer<String> onSubmitCallback) {
        this.onSubmitCallback = onSubmitCallback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_query04, null);

        spnSpeciality = view.findViewById(R.id.spn_speciality);

        var specialities = new SpecialitiesRepository(getContext())
                .getAll()
                .stream()
                .map(Speciality::getSpecialityName)
                .collect(Collectors.toList());

        var adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                specialities
        );

        spnSpeciality.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.query04_title)
                .setView(view)
                .setPositiveButton(R.string.execute_label, submitClickListener)
                .setNegativeButton(R.string.cancel_label, null);


        return builder.create();
    }

    private final DialogInterface.OnClickListener submitClickListener = (dialogInterface, which) -> {
        onSubmitCallback.accept(spnSpeciality.getSelectedItem().toString());
    };
}
