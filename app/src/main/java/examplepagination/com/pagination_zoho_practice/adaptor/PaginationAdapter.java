package examplepagination.com.pagination_zoho_practice.adaptor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import examplepagination.com.pagination_zoho_practice.R;
import examplepagination.com.pagination_zoho_practice.models.Result;
import examplepagination.com.pagination_zoho_practice.support.PaginationAdapterCallback;

/**
 * Created by kaushik on 23/06/2018.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // View Types
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Result> studentResult;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    public PaginationAdapter(Context context) {
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        studentResult = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_list, parent, false);
                viewHolder = new StudentVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Result result = studentResult.get(position);

        switch (getItemViewType(position)) {

            case ITEM:
                final StudentVH studentVH = (StudentVH) holder;

                studentVH.firstName.setText(result.getFirst_name());
                studentVH.lastName.setText(result.getLast_name());

                Glide.with(context)
                        .load(result.getAvatar())
                        .into(studentVH.mPosterImg);
                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return studentResult == null ? 0 : studentResult.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == studentResult.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    public void add(Result r) {
        studentResult.add(r);
        notifyItemInserted(studentResult.size() - 1);
    }

    public void addAll(List<Result> studentResults) {
        for (Result result : studentResults) {
            add(result);
        }
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Result());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = studentResult.size() - 1;
        Result result = getItem(position);

        if (result != null) {
            studentResult.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Result getItem(int position) {
        return studentResult.get(position);
    }

    /*To show the error message*/

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(studentResult.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    /**
     * Main list's  ViewHolder
     */
    protected class StudentVH extends RecyclerView.ViewHolder {
        private TextView firstName;
        private TextView lastName;
        private ImageView mPosterImg;


        public StudentVH(View itemView) {
            super(itemView);

            firstName = (TextView) itemView.findViewById(R.id.fName);
            lastName = (TextView) itemView.findViewById(R.id.lname);
            mPosterImg = (ImageView) itemView.findViewById(R.id.avatar_image);
        }
    }


    /*
    * Loading view holder*/
    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:
                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

}
