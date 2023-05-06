package org.goriachev.homework.interfaces;


import org.goriachev.homework.models.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


// Интерфейс API для альбомов
public interface Api {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("albums")
    Call<List<Album>> getAll();

    @GET("albums/{id}")
    Call<Album> getById(@Path(value = "id") int id);
}
