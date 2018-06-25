package examplepagination.com.pagination_zoho_practice.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import examplepagination.com.pagination_zoho_practice.models.Result;

@Dao
public interface StudentDao {

    @Query("SELECT * from student_table ORDER BY d_id ASC")
    LiveData<List<Result>> getIdWise();

    @Insert
    void insert(Result result);

    @Query("DELETE FROM student_table")
    void deleteAll();
}
