
package org.goriachev.homework.fragments.webData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.goriachev.homework.R;
import org.goriachev.homework.adapters.webData.PostAdapter;
import org.goriachev.homework.infrastructure.CustomCallback;
import org.goriachev.homework.interfaces.Api;

import java.io.Serializable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PostListFragment extends Fragment implements Serializable {

    private RecyclerView rcvPosts;

    // api для работы с веб-сервисом
    private Api api;


    public PostListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        var view = inflater.inflate(R.layout.fragment_post_list, container, false);

        var context = getContext();

        rcvPosts = view.findViewById(R.id.rcv_posts);

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
        api.postsGetAll().enqueue(new CustomCallback<>(
                listResponse -> rcvPosts.setAdapter(new PostAdapter(getContext(), listResponse.body())),
                throwable ->
                        Toast.makeText(
                                getContext(),
                                "Не удалось получить данные",
                                Toast.LENGTH_SHORT
                        ).show()
        ));
    }
}