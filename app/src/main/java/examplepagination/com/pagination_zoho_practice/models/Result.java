package examplepagination.com.pagination_zoho_practice.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by kaushik on 23-Jun-18.
 */

@Entity(tableName = "student_table")
public class Result {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int sd;
    @ColumnInfo(name = "d_id")
    private int id;
    @ColumnInfo(name = "d_first_name")
    private String first_name;
    @ColumnInfo(name = "d_last_name")
    private String last_name;
    @ColumnInfo(name = "d_avatar")
    private String avatar;

    public Result(int id, String first_name, String last_name, String avatar) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

    public Result() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @NonNull
    public int getSd() {
        return sd;
    }

    public void setSd(@NonNull int sd) {
        this.sd = sd;
    }
}
