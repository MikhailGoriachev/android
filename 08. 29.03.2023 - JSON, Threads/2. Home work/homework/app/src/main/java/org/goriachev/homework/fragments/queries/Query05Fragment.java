
package org.goriachev.homework.fragments.queries;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.AppointmentAdapter;
import org.goriachev.homework.repositories.AppointmentsRepository;

import java.io.Serializable;


public class Query05Fragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // репозиторий
    AppointmentsRepository repository;

    // элемент для заголовка
    TextView txvTitle;


    public Query05Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var context = getContext();

        repository = new AppointmentsRepository(context);

        var view = inflater.inflate(R.layout.fragment_query05, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        // запуск обработки запроса в отдельном потоке исполнения
        new Thread(() -> run(context)).start();

        return view;
    }

    // обработка запроса
    private void run(Context context) {
        rcvItems.post(() -> rcvItems.setAdapter(
                new AppointmentAdapter(context, repository.getAllOrderByDoctorSpeciality())
        ));

        txvTitle.post(() -> txvTitle.setText("Запрос 5. Сортировка по специальности"));
    }
}