package com.example.universityschedule.network

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.universityschedule.MainApplication
import com.example.universityschedule.network.api.*
import com.example.universityschedule.network.models.basicmodels.ErrorResponse
import com.example.universityschedule.network.retrofit.MyAuthenticator
import com.example.universityschedule.network.retrofit.MyInterceptor
import com.example.universityschedule.network.testfiles.UserTestApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    const val BASE_URL = "http://46.161.150.167:50011/api/"

    var userAuthorized = false

    private var masterKey = MasterKey.Builder(MainApplication.applicationContext())
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences = EncryptedSharedPreferences.create(
        MainApplication.applicationContext(),
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun updateSharedPrefs(typeOfData: String, newToken: String) {
        sharedPreferences.edit().putString(typeOfData, newToken).apply()
    }

    fun getSharedPrefs(typeOfData: String): String? {
        return sharedPreferences.getString(typeOfData, "")
    }

    private fun getHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(MyInterceptor())
            authenticator(MyAuthenticator())
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
        }
        return client.build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .client(getHttpClient())
            .build()
    }

    private val retrofit: Retrofit = getRetrofit()

    fun getAuthApi(): AuthApi = retrofit.create(AuthApi::class.java)
    fun getListInfoApi(): ListInfoApi = retrofit.create(ListInfoApi::class.java)
    fun getRoomApi(): RoomApi = retrofit.create(RoomApi::class.java)
    fun getScheduleApi(): ScheduleApi = retrofit.create(ScheduleApi::class.java)
    fun getUserApi(): UserApi = retrofit.create(UserApi::class.java)

    fun getUserTestApi(): UserTestApi = retrofit.create(UserTestApi::class.java)

}

fun <T> apiRequestFlow(call: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
    emit(ApiResponse.Loading)

    withTimeoutOrNull(15000L) {
        val response = call()

        try {
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(ApiResponse.Success(data))
                }
            } else {
                response.errorBody()?.let { error ->
                    error.close()
                    val parsedError: ErrorResponse =
                        Gson().fromJson(error.charStream(), ErrorResponse::class.java)
                    emit(ApiResponse.Failure(parsedError.message, parsedError.code))
                }
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e.message ?: e.toString(), 400))
        }
    } ?: emit(ApiResponse.Failure("Timeout! Please try again.", 408))
}.flowOn(Dispatchers.IO)