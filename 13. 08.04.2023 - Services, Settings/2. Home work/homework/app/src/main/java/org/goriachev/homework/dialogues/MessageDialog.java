package org.goriachev.homework.dialogues;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.goriachev.homework.R;
import org.goriachev.homework.infrastructure.Settings;
import org.goriachev.homework.models.Message;
import org.goriachev.homework.utils.Utils;

import java.util.function.Consumer;

// Диалог для ввода сообщения
public class MessageDialog extends DialogFragment {

    private Message message;

    // callback для обработки результата ввода
    private Consumer<Message> onSubmitCallback;

    // callback для обработки результата удаления
    private Consumer<Message> onDeleteCallback;

    // статус формы
    private boolean isCreate;

    EditText edtReceiver, edtSender, edtSubject, edtText;

    SwitchMaterial swtIsAttach;

    private SharedPreferences.Editor editor;


    // конструктор для добавления
    public MessageDialog(@NonNull Consumer<Message> onSubmitCallback) {
        this.onSubmitCallback = onSubmitCallback;
        this.message = new Message();
        this.isCreate = true;
    }

    // конструктор для редактирования
    public MessageDialog(@NonNull Consumer<Message> onSubmitCallback,
                         @Nullable Consumer<Message> onDeleteCallback,
                         Message message) {
        this.onSubmitCallback = onSubmitCallback;
        this.message = message.clone();
        this.isCreate = false;
        this.onDeleteCallback = onDeleteCallback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // разметка
        var inflater = getActivity().getLayoutInflater();
        var view = inflater.inflate(R.layout.dialog_message_form, null);

        // контроллы
        edtReceiver = view.findViewById(R.id.edt_receiver);
        edtSender = view.findViewById(R.id.edt_sender);
        edtSubject = view.findViewById(R.id.edt_subject);
        edtText = view.findViewById(R.id.edt_text);
        swtIsAttach = view.findViewById(R.id.swt_is_attach);

        // установка слушателей событий ввода
        setListeners();

        editor = Settings.getEditor();

        builder.setTitle(isCreate ? "Добавление сообщения" : "Изменение сообщения")
                .setView(view)
                .setPositiveButton(isCreate ? R.string.add_label : R.string.save_label, submitClickListener)
                .setNegativeButton(R.string.cancel_label, null);

        // добавление кнопки удаления при редактировании
        if (!isCreate)
            builder.setNeutralButton(R.string.delete_label, deleteClickListener);

        // установка данных в поля
        setDataToFields();

        return builder.create();
    }

    // установка слушателей событий ввода
    private void setListeners() {

        Utils.addTextChangedListener(edtReceiver, (v) -> {
            message.setReceiver(v);
            editor.putString(Settings.RECEIVER_KEY, v);
            editor.apply();
        });
        Utils.addTextChangedListener(edtSender, (v) -> {
            message.setSender(v);
            editor.putString(Settings.SENDER_KEY, v);
            editor.apply();
        });
        Utils.addTextChangedListener(edtSubject, (v) -> {
            message.setSubject(v);
            editor.putString(Settings.SUBJECT_KEY, v);
            editor.apply();
        });
        Utils.addTextChangedListener(edtText, (v) -> {
            message.setText(v);
            editor.putString(Settings.TEXT_KEY, v);
            editor.apply();
        });
        swtIsAttach.setOnCheckedChangeListener((button, v) -> {
            message.setAttach(v);
            editor.putBoolean(Settings.IS_ATTACH_KEY, v);
            editor.apply();
        });
    }

    // установка данных в поля ввода
    private void setDataToFields() {

        if (Settings.isRestoreKey()) {
            if (isCreate) {
                message.setReceiver(Settings.getReceiver());
                message.setSender(Settings.getSender());
                message.setSubject(Settings.getSubject());
                message.setText(Settings.getText());
                message.setAttach(Settings.isAttach());
            } else {
                editor.putString(Settings.RECEIVER_KEY, message.getReceiver());
                editor.putString(Settings.SENDER_KEY, message.getSender());
                editor.putString(Settings.SUBJECT_KEY, message.getSubject());
                editor.putString(Settings.TEXT_KEY, message.getText());
                editor.putBoolean(Settings.IS_ATTACH_KEY, message.isAttach());

                editor.apply();
            }
        }

        edtReceiver.setText(message.getReceiver());
        edtSender.setText(message.getSender());
        edtSubject.setText(message.getSubject());
        edtText.setText(message.getText());
        swtIsAttach.setChecked(message.isAttach());
    }

    // установка данных в поля модели
    private void setDataToModel() {
        message.setReceiver(edtReceiver.getText().toString());
        message.setSender(edtSender.getText().toString());
        message.setSubject(edtSubject.getText().toString());
        message.setText(edtText.getText().toString());
        message.setAttach(swtIsAttach.isChecked());
    }

    // обработчик submit
    private final DialogInterface.OnClickListener submitClickListener = (dialogInterface, which) -> {
//        setDataToModel();
        onSubmitCallback.accept(message);
    };

    // обработчик удаления
    private final DialogInterface.OnClickListener deleteClickListener = (dialogInterface, which) -> {
        onDeleteCallback.accept(message);
    };
}
