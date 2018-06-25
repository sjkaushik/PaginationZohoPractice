package examplepagination.com.pagination_zoho_practice.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import examplepagination.com.pagination_zoho_practice.models.StudentDetails;
import examplepagination.com.pagination_zoho_practice.webservice.ApiClient;
import examplepagination.com.pagination_zoho_practice.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kaushik on 23/06/2018.
 */

public class ProjectRepository {

    private static String TAG = ProjectRepository.class.getSimpleName();
    private int pageNo;
    private ApiInterface apiInterface;


    public ProjectRepository() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public LiveData<StudentDetails> getDetailsFirstPage(int page) {

        Log.d(TAG, "loadFirstPage: ");
        final MutableLiveData<StudentDetails> data = new MutableLiveData<>();

        callDetailApi(page).enqueue(new Callback<StudentDetails>() {
            @Override
            public void onResponse(Call<StudentDetails> call, Response<StudentDetails> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<StudentDetails> call, Throwable t) {
                t.printStackTrace();

            }
        });

        return data;
    }

    public LiveData<StudentDetails> getDetailsNextPage(int page) {

        Log.d(TAG, "loadNext Page: ");
        final MutableLiveData<StudentDetails> data = new MutableLiveData<>();

        callDetailApi(page).enqueue(new Callback<StudentDetails>() {
            @Override
            public void onResponse(Call<StudentDetails> call, Response<StudentDetails> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<StudentDetails> call, Throwable t) {
                t.printStackTrace();

            }
        });

        return data;
    }

    private Call<StudentDetails> callDetailApi(int pageNo) {

        return apiInterface.getStudentDetails(pageNo
        );
    }


}
