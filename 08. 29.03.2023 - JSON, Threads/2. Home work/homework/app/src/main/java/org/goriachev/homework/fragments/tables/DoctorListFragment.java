
package org.goriachev.homework.fragments.tables;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.DoctorAdapter;
import org.goriachev.homework.repositories.DoctorsRepository;

import java.io.Serializable;


public class DoctorListFragment extends Fragment implements Serializable {

    private RecyclerView rcvDoctors;

    // репозиторий
    DoctorsRepository repository;


    public DoctorListFragment() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        var view = inflater.inflate(R.layout.fragment_doctor_list, container, false);

        var context = getContext();
        
        repository = new DoctorsRepository(context);

        rcvDoctors = view.findViewById(R.id.rcv_doctors);

        // запуск обработки запроса в отдельном потоке исполнения
        new Thread(() -> run(context)).start();

        return view;
    }

    // обработка запроса
    private void run(Context context) {
        rcvDoctors.post(() -> rcvDoctors.setAdapter(
                new DoctorAdapter(context, repository.getAll())
        ));
    }
}