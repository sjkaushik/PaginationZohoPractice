package examplepagination.com.pagination_zoho_practice.webservice;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kaushik on 22-Jun-18.
 */


public class ApiClient {

    private static final String BASE_URL = "https://reqres.in/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttpClientBuilder.build())
                    .build();

        }
        return retrofit;
    }

}
