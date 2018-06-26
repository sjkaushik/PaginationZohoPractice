package examplepagination.com.pagination_zoho_practice.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import examplepagination.com.pagination_zoho_practice.R;
import examplepagination.com.pagination_zoho_practice.adaptor.PaginationAdapter;
import examplepagination.com.pagination_zoho_practice.interfaces.StudentInfoInterface;
import examplepagination.com.pagination_zoho_practice.models.Result;
import examplepagination.com.pagination_zoho_practice.models.StudentDetails;
import examplepagination.com.pagination_zoho_practice.support.PaginationAdapterCallback;
import examplepagination.com.pagination_zoho_practice.support.PaginationScrollListener;
import examplepagination.com.pagination_zoho_practice.task.StudentDetailTask;
import examplepagination.com.pagination_zoho_practice.viewmodel.StudentViewModel;

public class MainActivity extends AppCompatActivity implements PaginationAdapterCallback, StudentInfoInterface {

    private static final String TAG = MainActivity.class.getSimpleName();

    PaginationAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout errorLayout;
    Button btnRetry;
    TextView txtError;

    private static final int PAGE_START = 1;

    private boolean isLoading = false;
    private boolean isLastPage = false;

    private int TOTAL_PAGES = 4;
    private int currentPage = PAGE_START;
    private StudentViewModel studentViewModel;
    private StudentDetailTask studentDetailTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        btnRetry = (Button) findViewById(R.id.error_btn_retry);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        txtError = (TextView) findViewById(R.id.error_txt_cause);

        adapter = new PaginationAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                retryPageLoad();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        /* First API CALL*/
        Log.d(TAG, "onCreate: ");
        loadFirstPage();

    }

    private void loadFirstPage() {
        studentDetailTask = new StudentDetailTask(this, this);
        studentDetailTask.loadFirstPage(currentPage);

    }

    @Override
    public void retryPageLoad() {
        studentDetailTask = new StudentDetailTask(this, this);
        studentDetailTask.loadNextPage(currentPage);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private String fetchErrorMessage(Throwable throwable) {
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()) {
            errorMsg = getResources().getString(R.string.error_msg_no_internet);

        } else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }


    @Override
    public void onSuccessFirst(List<StudentDetails.DataBean> results) {

        //hideErrorView();
        progressBar.setVisibility(View.GONE);

        final List<Result> resultList = new ArrayList<>();

        studentViewModel.deleteTable();

        for (int i = 0; i < results.size(); i++) {

            Result result = new Result(results.get(i).getId(), results.get(i).getFirst_name(), results.get(i).getLast_name(), results.get(i).getAvatar());
            resultList.add(result);

            studentViewModel.insert(result);
        }

        adapter.addAll(resultList);

        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void onSuccessSecond(List<StudentDetails.DataBean> results) {

        adapter.removeLoadingFooter();
        isLoading = false;

        progressBar.setVisibility(View.GONE);

        final List<Result> resultList = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            Result result = new Result(results.get(i).getId(), results.get(i).getFirst_name(), results.get(i).getLast_name(), results.get(i).getAvatar());
            resultList.add(result);
            studentViewModel.insert(result);
        }

        adapter.addAll(resultList);

        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void firstPageFail(final Throwable failure) {

        progressBar.setVisibility(View.GONE);
        studentViewModel.getAllDetails().observe(MainActivity.this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable final List<Result> results) {
                adapter.addAll(results);
            }
        });

    }

    @Override
    public void nextPageFail(Throwable failure) {
        adapter.showRetry(true, fetchErrorMessage(failure));
    }
}
