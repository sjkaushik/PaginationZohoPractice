package examplepagination.com.pagination_zoho_practice.task;

import android.content.Context;
import android.util.Log;

import java.util.List;

import examplepagination.com.pagination_zoho_practice.interfaces.StudentInfoInterface;
import examplepagination.com.pagination_zoho_practice.models.StudentDetails;
import examplepagination.com.pagination_zoho_practice.webservice.ApiClient;
import examplepagination.com.pagination_zoho_practice.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kaushik on 25/06/2018.
 */

public class StudentDetailTask {

    private static final String TAG = StudentDetailTask.class.getSimpleName();
    private Context context;
    private StudentInfoInterface mListener;
    private ApiInterface apiInterface;

    public StudentDetailTask(Context context, StudentInfoInterface mlistener) {
        this.context = context;
        this.mListener = mlistener;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public void loadFirstPage(int pageNo) {
        Log.d(TAG, "loadFirstPage: ");

        callDetailApi(pageNo).enqueue(new Callback<StudentDetails>() {
            @Override
            public void onResponse(Call<StudentDetails> call, Response<StudentDetails> response) {

                List<StudentDetails.DataBean> results = response.body().getData();

                mListener.onSuccessFirst(results);
            }

            @Override
            public void onFailure(Call<StudentDetails> call, Throwable t) {
                t.printStackTrace();
                mListener.firstPageFail(t);
            }
        });
    }

    public void loadNextPage(int pageNo) {
        Log.d(TAG, "loadNextPage: ");


        callDetailApi(pageNo).enqueue(new Callback<StudentDetails>() {
            @Override
            public void onResponse(Call<StudentDetails> call, Response<StudentDetails> response) {

                List<StudentDetails.DataBean> results = response.body().getData();

                mListener.onSuccessSecond(results);
            }

            @Override
            public void onFailure(Call<StudentDetails> call, Throwable t) {
                t.printStackTrace();
                mListener.nextPageFail(t);
            }
        });


    }

    private Call<StudentDetails> callDetailApi(int page) {

        return apiInterface.getStudentDetails(page);
    }


}
