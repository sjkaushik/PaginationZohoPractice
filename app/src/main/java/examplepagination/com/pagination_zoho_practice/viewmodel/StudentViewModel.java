package examplepagination.com.pagination_zoho_practice.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import examplepagination.com.pagination_zoho_practice.models.Result;
import examplepagination.com.pagination_zoho_practice.repository.StudentRepository;

/**
 * Created by kaushik on 24/06/2018.
 */

public class StudentViewModel extends AndroidViewModel {

    private StudentRepository studentRepository;

    private LiveData<List<Result>> mAllDetails;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentRepository = new StudentRepository(application);
        mAllDetails = studentRepository.getAllDetails();
    }

    public LiveData<List<Result>> getAllDetails() { return mAllDetails; }

    public void insert(Result result) { studentRepository.insert(result); }

    public void deleteTable() { studentRepository.delete(); }
}


