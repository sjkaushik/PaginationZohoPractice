package examplepagination.com.pagination_zoho_practice.webservice;

import examplepagination.com.pagination_zoho_practice.models.StudentDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kaushik on 22-Jun-18.
 */

public interface ApiInterface {

    @GET("users")
    Call<StudentDetails> getStudentDetails(@Query("page") int pageNo);
}
