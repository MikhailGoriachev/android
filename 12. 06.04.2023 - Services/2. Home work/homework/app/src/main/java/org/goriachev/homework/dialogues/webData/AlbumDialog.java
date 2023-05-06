package org.goriachev.homework.dialogues.webData;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.goriachev.homework.R;
import org.goriachev.homework.models.webData.Album;


// Класс Диалог для ввода id альбома
public class AlbumDialog extends DialogFragment {

    private final Album item;

    // конструктор
    public AlbumDialog(@NonNull Album item) {
        this.item = item;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        var inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_item_two_lines, null);

        TextView firstLine = view.findViewById(R.id.txv_first_line);
        TextView secondLine = view.findViewById(R.id.txv_second_line);

        firstLine.setText(String.format("ID: " + item.getId()));
        secondLine.setText(String.format("Заголовок: " + item.getTitle()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.album_title)
                .setView(view)
                .setPositiveButton(R.string.back_label, null);

        return builder.create();
    }
}
