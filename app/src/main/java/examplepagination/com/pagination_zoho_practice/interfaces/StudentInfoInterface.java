package examplepagination.com.pagination_zoho_practice.interfaces;

import java.util.List;


import examplepagination.com.pagination_zoho_practice.models.StudentDetails;

public interface StudentInfoInterface {

    void onSuccessFirst(List<StudentDetails.DataBean> result);

    void onSuccessSecond(List<StudentDetails.DataBean> result);

    void firstPageFail(Throwable failure);

    void nextPageFail(Throwable failure);

}
