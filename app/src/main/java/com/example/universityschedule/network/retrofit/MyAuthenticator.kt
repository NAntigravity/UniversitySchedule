package com.example.universityschedule.network.retrofit


import com.example.universityschedule.MainApplication
import com.example.universityschedule.network.Network
import com.example.universityschedule.network.api.AuthApi
import com.example.universityschedule.network.models.LoginResponse
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val newTokenResponse = getNewToken(Network.getToken(MainApplication.AccessToken))
            //val newTokenResponse = getNewToken(Network.RefreshToken)

            if (!newTokenResponse.isSuccessful || newTokenResponse.body() == null) {
                // Всё плохо, кидаем на экран входа
            }

            newTokenResponse.body()?.let {
                Network.updateToken(it.access_token, MainApplication.AccessToken)
                //Network.refreshToken = it.refresh_token
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.access_token}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Network.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val authApi = retrofit.create(AuthApi::class.java)
        return authApi.refreshToken("Bearer $refreshToken")
    }
}