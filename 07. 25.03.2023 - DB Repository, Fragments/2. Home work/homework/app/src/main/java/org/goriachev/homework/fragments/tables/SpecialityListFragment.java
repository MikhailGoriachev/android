
package org.goriachev.homework.fragments.tables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.SpecialityAdapter;
import org.goriachev.homework.repositories.SpecialitiesRepository;

import java.io.Serializable;


public class SpecialityListFragment extends Fragment implements Serializable {

    private RecyclerView rcvSpecialities;

    // репозиторий
    SpecialitiesRepository repository;


    public SpecialityListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        var view = inflater.inflate(R.layout.fragment_speciality_list, container, false);

        var context = getContext();

        repository = new SpecialitiesRepository(context);

        rcvSpecialities = view.findViewById(R.id.rcv_specialities);

        rcvSpecialities.setAdapter(new SpecialityAdapter(context, repository.getAll()));

        return view;
    }
}