package examplepagination.com.pagination_zoho_practice.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import examplepagination.com.pagination_zoho_practice.dao.StudentDao;
import examplepagination.com.pagination_zoho_practice.models.Result;
import examplepagination.com.pagination_zoho_practice.room.StudentRoomDatabase;

public class StudentRepository {

    private StudentDao mStudentDao;
    private LiveData<List<Result>> mAllDetails;

    public StudentRepository(Application application) {
        StudentRoomDatabase db = StudentRoomDatabase.getDatabase(application);
        mStudentDao = db.studentDao();
        mAllDetails = mStudentDao.getIdWise();
    }

    public LiveData<List<Result>> getAllDetails() {
        return mAllDetails;
    }

    public void insert(Result result) {
        new insertAsyncTask(mStudentDao).execute(result);
    }

    public void delete() {
        new deleteAsyncTask(mStudentDao).execute();
    }

    /*inserting record*/
    private static class insertAsyncTask extends android.os.AsyncTask<Result, Void, Void> {

        private StudentDao mAsyncTaskDao;

        insertAsyncTask(StudentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Result... params) {

            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /*Deleting the table*/
    private static class deleteAsyncTask extends AsyncTask<Result, Void, Void> {

        private StudentDao mAsyncTaskDao;

        deleteAsyncTask(StudentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Result... params) {
            Result res = new Result(3, "AAA", "BBB", "CCC");
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


}
