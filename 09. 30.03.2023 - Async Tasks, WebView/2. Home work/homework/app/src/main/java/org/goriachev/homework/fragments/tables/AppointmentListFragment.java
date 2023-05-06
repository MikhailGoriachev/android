
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
import org.goriachev.homework.adapters.AppointmentAdapter;
import org.goriachev.homework.entities.Appointment;
import org.goriachev.homework.infrastructure.Task;
import org.goriachev.homework.repositories.AppointmentsRepository;
import org.goriachev.homework.repositories.DoctorsRepository;
import org.goriachev.homework.repositories.PatientsRepository;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
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

        var calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -Utils.getInt(5, 10));

        var patient = Utils.getItem(new PatientsRepository(getContext()).getAll());
        var doctor = Utils.getItem(new DoctorsRepository(getContext()).getAll());

        var item = new Appointment(0, calendar.getTime(), patient, doctor);

        repository.store(item);

        Toast.makeText(getContext(), "Запись добавлена", Toast.LENGTH_LONG).show();

        updateList();
    }

    // изменение записи
    private void editItem(Appointment item) {

        var calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, -Utils.getInt(5, 10));

        item.setAppointmentDate(calendar.getTime());

        repository.update(item);

        Toast.makeText(getContext(), "Запись изменена", Toast.LENGTH_LONG).show();

        updateList();
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
                (v) -> repository.getAll(),
                (v) -> {
                    items = v;
                    adapter = new AppointmentAdapter(context, items, this::editItem, this::deleteItem);
                    rcvAppointments.setAdapter(adapter);
                }
        );

        task.execute();
    }
}