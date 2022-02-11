package com.fetchrewards.hiring.network;

import com.fetchrewards.hiring.models.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FetchRewardsAPI {

    @GET("/hiring.json")
    Call<List<Item>> fetchItems();
}
