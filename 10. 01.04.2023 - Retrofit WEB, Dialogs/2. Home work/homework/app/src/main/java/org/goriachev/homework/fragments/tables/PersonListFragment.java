
package org.goriachev.homework.fragments.tables;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.polyclinic.PersonAdapter;
import org.goriachev.homework.entities.Person;
import org.goriachev.homework.infrastructure.Task;
import org.goriachev.homework.repositories.PeopleRepository;

import java.io.Serializable;
import java.util.List;


public class PersonListFragment extends Fragment implements Serializable {

    private RecyclerView rcvPeople;

    // репозиторий
    PeopleRepository repository;


    public PersonListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        var view = inflater.inflate(R.layout.fragment_person_list, container, false);

        var context = getContext();

        repository = new PeopleRepository(context);

        rcvPeople = view.findViewById(R.id.rcv_people);

        // запуск обработки запроса в отдельном потоке исполнения
        run(context);

        return view;
    }

    // обработка запроса
    private void run(Context context) {
        
        var task = new Task<Void, Void, List<Person>>(
                null,
                (v) -> repository.getAll(),
                (v) -> rcvPeople.setAdapter(new PersonAdapter(context, v))
        );

        task.execute();
    }
}