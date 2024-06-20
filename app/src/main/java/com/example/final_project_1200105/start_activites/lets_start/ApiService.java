package com.example.final_project_1200105.start_activites.lets_start;

import com.example.final_project_1200105.ui_normal_user.Menu.Pizza;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/pizzas")
    Call<List<Pizza>> getPizzas();
}
