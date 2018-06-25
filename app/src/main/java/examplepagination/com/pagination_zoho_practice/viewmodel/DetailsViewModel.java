package examplepagination.com.pagination_zoho_practice.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import examplepagination.com.pagination_zoho_practice.models.StudentDetails;
import examplepagination.com.pagination_zoho_practice.repository.ProjectRepository;

/**
 * Created by kaushik on 22-Jun-18.
 */


public class DetailsViewModel extends ViewModel {

    private LiveData<StudentDetails> studentDetailsFirstpageObservable = null;
    private LiveData<StudentDetails> studentDetailsNextpageObservable = null;
    private ProjectRepository repository;
    private int page;

    private DetailsViewModel(int pageNo) {
        this.page = pageNo;

        repository = new ProjectRepository();

        studentDetailsFirstpageObservable = repository.getDetailsFirstPage(page);

        studentDetailsNextpageObservable = repository.getDetailsNextPage(page);
    }

    public LiveData<StudentDetails> getProjectListFirstObservable() {
        return studentDetailsFirstpageObservable;
    }

    public LiveData<StudentDetails> getProjectListNextObservable() {
        return studentDetailsNextpageObservable;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final int page;

        public Factory(int pageNo) {
               this.page = pageNo;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DetailsViewModel(page);
        }
    }

    public static class FactoryNext extends ViewModelProvider.NewInstanceFactory {

        private final int page;

        public FactoryNext(int pageNo) {
            this.page = pageNo;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DetailsViewModel(page);
        }
    }
}
