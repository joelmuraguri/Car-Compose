package com.joel.car_compose.model.network

import com.joel.car_compose.model.network.auth.LoginRequest
import com.joel.car_compose.model.network.auth.RegisterRequest
import com.joel.car_compose.model.network.auth.TokenResponse
import com.joel.car_compose.model.Car
import com.joel.car_compose.model.Brand
import com.joel.car_compose.model.fav.FavouriteCarItem
import com.joel.car_compose.model.fav.FavouriteResponseItem
import com.joel.car_compose.utils.ApiConstants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @POST(ApiConstants.REGISTER_ENDPOINT)
    fun register (@Body request: RegisterRequest) : Call<TokenResponse>

    @POST(ApiConstants.LOG_IN_ENDPOINT)
    fun login(@Body request: LoginRequest) : Call<TokenResponse>

    @GET(ApiConstants.CAR_LIST_ENDPOINT)
    suspend fun getCarList() : List<Car>

    @GET(ApiConstants.CAR_BRAND_ENDPOINT)
    suspend fun getBrandList() : List<Brand>

    @GET(ApiConstants.FAVOURITES_ENDPOINT)
    suspend fun fetchFavourites(
        @Header ("Authorization") token : String
    ) : List<FavouriteResponseItem>

    @GET(ApiConstants.FAVOURITE_CAR_ID)
     fun toggleFavoriteCar(
        @Header ("Authorization") token : String,
        @Path ("car_id") carId: Int
    ) : Call<FavouriteCarItem>


     @GET(ApiConstants.CAR_DETAILS)
    fun fetchCarDetails(
         @Path("id") id: Int
    ) : Call<Car>


    companion object{
        private var apiService : ApiService?= null
        fun getInstance () : ApiService {
            if (apiService == null){
                apiService = Retrofit.Builder()
                    .baseUrl(ApiConstants.MAIN_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}