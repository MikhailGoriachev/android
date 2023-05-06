
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
import org.goriachev.homework.adapters.PatientAdapter;
import org.goriachev.homework.repositories.PatientsRepository;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;


public class Query01Fragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // репозиторий
    PatientsRepository repository;

    // элемент для заголовка
    TextView txvTitle;


    public Query01Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var context = getContext();

        repository = new PatientsRepository(context);

        var view = inflater.inflate(R.layout.fragment_query01, container, false);

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        // запуск обработки запроса в отдельном потоке исполнения
        new Thread(() -> run(context)).start();

        return view;
    }

    // обработка запроса
    private void run(Context context) {
        var surname = Utils.getItem(repository.getAll())
                .getPerson()
                .getSurname()
                .substring(0, 3);

        rcvItems.post(() -> rcvItems.setAdapter(
                new PatientAdapter(context, repository.getAllBySurname(surname))
        ));

        txvTitle.post(
                () -> txvTitle.setText(String.format("Запрос 1. Фамилия начинается с \"%s\"", surname))
        );
    }
}