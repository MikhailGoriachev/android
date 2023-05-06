package org.goriachev.homework.interfaces;


import org.goriachev.homework.models.webData.Album;
import org.goriachev.homework.models.webData.Comment;
import org.goriachev.homework.models.webData.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


// Интерфейс API для альбомов
public interface Api {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    // альбомы
    @GET("albums")
    Call<List<Album>> albumsGetAll();

    // альбом по id
    @GET("albums/{id}")
    Call<Album> albumsGetById(@Path(value = "id") int id);

    // комментарии
    @GET("comments")
    Call<List<Comment>> commentsGetAll();

    // посты
    @GET("posts")
    Call<List<Post>> postsGetAll();
}
