
package org.goriachev.homework.fragments.queries;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.DoctorAdapter;
import org.goriachev.homework.entities.Doctor;
import org.goriachev.homework.infrastructure.Task;
import org.goriachev.homework.repositories.DoctorsRepository;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;
import java.util.List;
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
        run(context);

        rcvItems = view.findViewById(R.id.rcv_items);
        txvTitle = view.findViewById(R.id.txv_title);

        return view;
    }

    // обработка запроса
    private void run(Context context) {

        var task = new Task<Void, Void, Pair<List<Doctor>, String>>(
                null,
                (v) -> {
                    var specialityName = Utils.getItem(repository.getAll())
                            .getSpeciality()
                            .getSpecialityName();

                    return new Pair<>(repository.getAllBySpeciality(specialityName), specialityName);
                },
                (v) -> {
                    rcvItems.setAdapter(new DoctorAdapter(context, v.first));
                    txvTitle.setText(
                            String.format(
                                    Locale.UK, "Запрос 4. Специальность \"%s\"", v.second
                            )
                    );
                }
        );

        task.execute();
    }
}