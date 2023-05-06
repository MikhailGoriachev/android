package org.goriachev.homework.dialogues;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.goriachev.homework.R;
import org.goriachev.homework.entities.Edition;
import org.goriachev.homework.utils.Utils;

import java.util.function.Consumer;


// Диалог для формы издания
public class EditionDialog extends DialogFragment {

    private Edition edition;

    // callback для обработки результата ввода
    private Consumer<Edition> onSubmitCallback;

    // callback для обработки результата удаления
    private Consumer<Edition> onDeleteCallback;

    // статус формы
    private final boolean isCreate;

    TextInputEditText edtIndex, edtName, edtPrice, edtDuration;

    TextInputLayout txlIndex, txlName, txlPrice, txlDuration;

    Button btnSubscribeDate;

    AutoCompleteTextView atcPublicationType;

    private View view;


    // конструктор для добавления
    public EditionDialog(@NonNull Consumer<Edition> onSubmitCallback) {
        this.onSubmitCallback = onSubmitCallback;
        this.edition = new Edition();
        this.isCreate = true;
    }

    // конструктор для редактирования
    public EditionDialog(@NonNull Consumer<Edition> onSubmitCallback,
                         @Nullable Consumer<Edition> onDeleteCallback,
                         Edition edition) {
        this.onSubmitCallback = onSubmitCallback;
        this.edition = edition.clone();
        this.isCreate = false;
        this.onDeleteCallback = onDeleteCallback;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // разметка
        var inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_edition_form, null);

        // контроллы
        txlIndex = view.findViewById(R.id.txl_index);
        txlName = view.findViewById(R.id.txl_title);
        txlPrice = view.findViewById(R.id.txl_price);
        txlDuration = view.findViewById(R.id.txl_duration);

        edtIndex = view.findViewById(R.id.edt_index);
        edtName = view.findViewById(R.id.edt_title);
        edtPrice = view.findViewById(R.id.edt_price);
        edtDuration = view.findViewById(R.id.edt_duration);

        btnSubscribeDate = view.findViewById(R.id.btn_subscribe_date);
        atcPublicationType = view.findViewById(R.id.acp_publication_type);

        atcPublicationType.setAdapter(new ArrayAdapter<>(
                getContext(),
                R.layout.view_item_spinner,
                Utils.publicationTypeList
        ));

        // установка слушателей событий ввода
        setListeners();

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

        Utils.addTextChangedListener(edtName, (value) -> {
            try {
                edition.setName(
                        Utils.isValidEditText(edtName, txlName, (s) -> !s.isEmpty(),
                                "Поле наименования должно быть заполнено",
                                getContext())
                                ? value
                                : edition.getName()
                );
            } catch (Exception ignored) {
            }
        });

        Utils.addTextChangedListener(edtIndex, (value) -> {
            try {
                edition.setIndex(
                        Utils.isValidEditText(edtIndex, txlIndex, (s) -> !s.isEmpty(),
                                "Поле индекса должно быть заполнено",
                                getContext())
                                ? value
                                : edition.getIndex()
                );
            } catch (Exception ignored) {
            }
        });

        Utils.addTextChangedListener(edtPrice, (value) -> {
            try {
                edition.setPrice(
                        Utils.isValidEditText(edtPrice, txlPrice, (s) -> {
                                    Integer val = Utils.tryParseInt(s);
                                    return val != null && val >= 0;
                                },
                                "Цена должна быть больше или равна 0",
                                getContext())
                                ? Utils.tryParseInt(value)
                                : edition.getPrice()
                );
            } catch (Exception e) {
            }
        });

        Utils.addTextChangedListener(edtDuration, (value) -> {
            try {
                edition.setDuration(
                        Utils.isValidEditText(edtDuration, txlDuration, (s) -> {
                                    Integer val = Utils.tryParseInt(s);
                                    return val != null && val > 0;
                                },
                                "Длительность подписки должна быть больше 0",
                                getContext())
                                ? Utils.tryParseInt(value)
                                : edition.getDuration()
                );
            } catch (Exception e) {
            }
        });

        btnSubscribeDate.setOnClickListener(v -> {
            var dialog = new DatePickerDialog(
                    getContext(),
                    (datePicker, year, month, dayOfMonth) -> {
                        edition.getSubscribeDate().setYear(year - 1900);
                        edition.getSubscribeDate().setMonth(month);
                        edition.getSubscribeDate().setDate(dayOfMonth);

                        btnSubscribeDate.setText(Utils.dateHtmlToFormat(edition.getSubscribeDate()));
                    },
                    edition.getSubscribeDate().getYear() + 1900,
                    edition.getSubscribeDate().getMonth(),
                    edition.getSubscribeDate().getDate()
            );

            dialog.show();
        });

        Utils.addTextChangedListener(atcPublicationType, s -> {
            try {
                edition.setPublicationType(s);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // установка данных в поля ввода
    private void setDataToFields() {
        if (isCreate) {
            atcPublicationType.setText(Utils.publicationTypeList.get(0));
            btnSubscribeDate.setText(Utils.dateHtmlToFormat(edition.getSubscribeDate()));
        } else {
            edtIndex.setText(edition.getIndex());
            edtName.setText(edition.getName());
            edtPrice.setText(Utils.intFormat(edition.getPrice()));
            edtDuration.setText(Utils.intFormat(edition.getDuration()));
            btnSubscribeDate.setText(Utils.dateHtmlToFormat(edition.getSubscribeDate()));
            atcPublicationType.setText(edition.getPublicationType(), false);
        }
    }

    // обработчик submit
    private final DialogInterface.OnClickListener submitClickListener = (dialogInterface, which) -> {
        try {
            edition.setIndex(edtIndex.getText().toString());
            edition.setName(edtName.getText().toString());
            edition.setPrice(Integer.parseInt(edtPrice.getText().toString()));
            edition.setDuration(Integer.parseInt(edtDuration.getText().toString()));

            onSubmitCallback.accept(edition);
        } catch (NumberFormatException ex) {
            Utils.showSnakeBar(getActivity().findViewById(R.id.rcv_items), "Введите числовое значение");
        } catch (Exception ex) {
            Utils.showSnakeBar(getActivity().findViewById(R.id.rcv_items), ex.getMessage());
        }
    };

    // обработчик удаления
    private final DialogInterface.OnClickListener deleteClickListener = (dialogInterface, which) -> {
        onDeleteCallback.accept(edition);
    };
}
