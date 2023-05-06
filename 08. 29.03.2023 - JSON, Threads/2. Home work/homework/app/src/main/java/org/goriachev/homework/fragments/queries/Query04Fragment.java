
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
import org.goriachev.homework.adapters.DoctorAdapter;
import org.goriachev.homework.repositories.DoctorsRepository;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.util.Locale;


public class Query04Fragment extends Fragment implements Serializable {

    private RecyclerView rcvItems;

    // репозиторий
    DoctorsRepository repository;

    // элемент для заголовка
    TextView txvTitle;


    public Query04Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        var context = getContext();

        repository = new DoctorsRepository(context);

        var view = inflater.inflate(R.layout.fragment_query04, container, false);

        // запуск обработки запроса в отдельном потоке исполнения
        new Thread(() -> run(context)).start();

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        return view;
    }

    // обработка запроса
    private void run(Context context) {

        var specialityName = Utils.getItem(repository.getAll())
                .getSpeciality()
                .getSpecialityName();

        rcvItems.post(() -> rcvItems.setAdapter(
                new DoctorAdapter(context, repository.getAllBySpeciality(specialityName))
        ));

        txvTitle.post(() ->
                txvTitle.setText(String.format(
                        Locale.UK, "Запрос 4. Специальность \"%s\"", specialityName
                ))
        );
    }
}