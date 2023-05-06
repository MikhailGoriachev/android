
package org.goriachev.homework.fragments.tables;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.PatientAdapter;
import org.goriachev.homework.repositories.PatientsRepository;

import java.io.Serializable;


public class PatientListFragment extends Fragment implements Serializable {

    private RecyclerView rcvPatients;

    // репозиторий
    PatientsRepository repository;


    public PatientListFragment() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        var view = inflater.inflate(R.layout.fragment_patient_list, container, false);

        var context = getContext();

        repository = new PatientsRepository(context);

        rcvPatients = view.findViewById(R.id.rcv_patients);

        // запуск обработки запроса в отдельном потоке исполнения
        new Thread(() -> run(context)).start();

        return view;
    }

    // обработка запроса
    private void run(Context context) {
        rcvPatients.post(() -> rcvPatients.setAdapter(
                new PatientAdapter(context, repository.getAll())
        ));
    }
}