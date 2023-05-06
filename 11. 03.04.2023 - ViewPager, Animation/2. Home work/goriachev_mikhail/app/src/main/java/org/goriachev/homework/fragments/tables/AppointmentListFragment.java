
package org.goriachev.homework.fragments.tables;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.polyclinic.AppointmentAdapter;
import org.goriachev.homework.dialogues.forms.AppointmentFormDialog;
import org.goriachev.homework.entities.Appointment;
import org.goriachev.homework.infrastructure.Task;
import org.goriachev.homework.repositories.AppointmentsRepository;

import java.io.Serializable;
import java.util.List;


public class AppointmentListFragment extends Fragment implements Serializable {

    private RecyclerView rcvAppointments;

    // репозиторий
    AppointmentsRepository repository;

    // адаптер
    AppointmentAdapter adapter;

    // записи
    List<Appointment> items;

    // кнопка добавления
    FloatingActionButton btnAdd;

    public AppointmentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        var view = inflater.inflate(R.layout.fragment_appointment_list, container, false);

        var context = getContext();

        repository = new AppointmentsRepository(context);

        rcvAppointments = view.findViewById(R.id.rcv_appointments);

        // запуск обработки запроса в отдельном потоке исполнения
        run(context);

        btnAdd = view.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(v -> addItem());

        return view;
    }

    // добавление записи
    private void addItem() {
        var dialog = new AppointmentFormDialog((appointment) -> {
            repository.store(appointment);

            Toast.makeText(getContext(), "Запись добавлена", Toast.LENGTH_LONG).show();

            updateList();
        });

        dialog.show(getChildFragmentManager(), "appointment_form");
    }

    // изменение записи
    private void editItem(Appointment item) {
        var dialog = new AppointmentFormDialog((appointment) -> {
            repository.update(appointment);

            Toast.makeText(getContext(), "Запись изменена", Toast.LENGTH_LONG).show();

            updateList();
        }, item);

        dialog.show(getChildFragmentManager(), "appointment_form");
    }

    // удаление записи
    private void deleteItem(Appointment item) {
        repository.delete(item);

        Toast.makeText(getContext(), "Запись удалена", Toast.LENGTH_LONG).show();

        updateList();
    }

    // обновить полностью коллекцию
    private void updateList() {
        items.clear();
        items.addAll(repository.getAll());
        adapter.notifyDataSetChanged();
    }

    // обработка запроса
    private void run(Context context) {

        var task = new Task<Void, Void, List<Appointment>>(
                null,
                (v) -> {
                    var data = repository.getAll();
                    repository.close();
                    return data;
                },
                (v) -> {
                    items = v;
                    adapter = new AppointmentAdapter(context, items, this::editItem, this::deleteItem);
                    rcvAppointments.setAdapter(adapter);
                }
        );

        task.execute();
    }
}