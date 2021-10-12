package com.example.headsupprep

import retrofit2.Call
import retrofit2.http.*


interface APIInterface {

    @GET("celebrities/")
    fun getPerson(): Call<List<Celeb.Details>>

    @POST("celebrities/")
    fun addPerson(@Body person : Celeb.Details): Call<List<Celeb.Details>>

    @PUT("celebrities/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person : Celeb.Details): Call<Celeb.Details>

    @DELETE("celebrities/{id}")
    fun deletePerson(@Path("id") id: Int): Call<Void>
}