
package org.goriachev.homework.fragments.albums;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.goriachev.homework.R;
import org.goriachev.homework.infrastructure.CustomCallback;
import org.goriachev.homework.interfaces.Api;
import org.goriachev.homework.utils.Utils;

import java.io.Serializable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AlbumFragment extends Fragment implements Serializable {

    TextView txvAlbumTitle, txvAlbumId;

    // api для работы с сервисом альбомов
    private Api api;


    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        var view = inflater.inflate(R.layout.fragment_album, container, false);

        var context = getContext();

        txvAlbumTitle = view.findViewById(R.id.txv_album_title);
        txvAlbumId = view.findViewById(R.id.txv_album_id);

        var retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

        // получить данные с сервера
        getData();

        return view;
    }

    // обработка запроса
    private void getData() {

        var id = getArguments().getInt("album_id");

        api.getById(id).enqueue(new CustomCallback<>(
                response -> {
                    var album = response.body();

                    if (album == null) {
                        Toast.makeText(
                                getContext(),
                                "Запись с id = " + id + " не найдена",
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }

                    txvAlbumId.post(() -> txvAlbumId.setText(Utils.intFormat(album.getId())));
                    txvAlbumTitle.post(() -> txvAlbumTitle.setText(album.getTitle()));
                },
                throwable ->
                        Toast.makeText(
                                getContext(),
                                "Не удалось получить данные",
                                Toast.LENGTH_SHORT
                        ).show()
        ));
    }
}