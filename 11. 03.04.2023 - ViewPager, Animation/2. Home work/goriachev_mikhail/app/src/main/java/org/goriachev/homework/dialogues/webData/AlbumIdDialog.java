package org.goriachev.homework.dialogues.webData;

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


// Класс Диалог для ввода id альбома
public class AlbumIdDialog extends DialogFragment {

    // callback для обработки результата ввода
    private Consumer<Integer> onSubmitCallback;


    // конструктор
    public AlbumIdDialog(@NonNull Consumer<Integer> onSubmitCallback) {
        this.onSubmitCallback = onSubmitCallback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.album_by_id_title)
                .setView(R.layout.dialog_album_id)
                .setPositiveButton(R.string.search_label, submitClickListener)
                .setNegativeButton(R.string.cancel_label, null);


        return builder.create();
    }

    private final DialogInterface.OnClickListener submitClickListener = (dialogInterface, which) -> {
        EditText etxAlbumId = getDialog().findViewById(R.id.etx_album_id);
        onSubmitCallback.accept(Utils.tryParseInt(etxAlbumId.getText().toString()));
    };
}
