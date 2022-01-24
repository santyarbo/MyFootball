package es.santyarbo.myfootball.data.server.common

import com.google.gson.Gson
import es.santyarbo.domain.ErrorResponse
import es.santyarbo.myfootball.data.server.ApiKeyRetriever
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FootballApi {
    private val apiKey = ApiKeyRetriever.getFooApiKey()//"8695ef4873mshd7b05b62256bb67p142921jsnad04cab8aba8"
    private const val apiHost = "api-football-v1.p.rapidapi.com"

    val okHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder().addInterceptor(
            FootballRequestInterceptor(
                apiKey,
                apiHost
            )
        ).build()
    }

    val service: FootballServices = Retrofit.Builder()
        .baseUrl("https://api-football-v1.p.rapidapi.com/v2/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run {
            create<FootballServices>(
                FootballServices::class.java)
        }
}


class FootballRequestInterceptor(private val apiKey: String, private val apiHost: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url.newBuilder()
            .build()

        val newRequest = request.newBuilder()
            .url(url)
            .addHeader("x-rapidapi-key", apiKey)
            .addHeader("x-rapidapi-host", apiHost)
            .build()

        return chain.proceed(newRequest)
    }
}

fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            return Gson().fromJson(it.toString(), ErrorResponse::class.java)
        }
    } catch (exception: Exception) {
        return null
    }
}