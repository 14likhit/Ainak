package com.example.ainak.customlisteners;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ainak.data.models.ImagesRequestBody;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Custom Scroll listener for image List.
 */
public abstract class RecyclerViewPaginator extends RecyclerView.OnScrollListener {

    /*
     * Variable to keep track of the current page
     * */
    private Long currentPage = 0L;

    /*
     * This variable is used to set
     * the threshold. For instance, if I have
     * set the page limit to 20, this will notify
     * the app to fetch more transactions when the
     * user scrolls to the 18th item of the list.
     * */
    private Integer threshold = 2;

    /*
     * This is a hack to ensure that the app is notified
     * only once to fetch more data. Since we use
     * scrollListener, there is a possibility that the
     * app will be notified more than once when user is
     * scrolling. This means there is a chance that the
     * same data will be fetched from the backend again.
     * This variable is to ensure that this does NOT
     * happen.
     * */
    private boolean endWithAuto = false;

    /*
     * We pass the RecyclerView to the constructor
     * of this class to get the LayoutManager
     * */
    private RecyclerView.LayoutManager layoutManager;

    public RecyclerViewPaginator(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(this);
        this.layoutManager = recyclerView.getLayoutManager();
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == SCROLL_STATE_IDLE) {
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();

            int firstVisibleItemPosition = 0;
            if (layoutManager instanceof LinearLayoutManager) {
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

            } else if (layoutManager instanceof GridLayoutManager) {
                firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

            //if(isLoading()) return
            if (isLastPage()) return;

            if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount) {
                if (!endWithAuto) {
                    endWithAuto = true;
                    loadMore(getStartSize(), getMaxSize());
                }
            } else {
                endWithAuto = false;
            }
        }
    }

    public Long getStartSize() {
        return ++currentPage;
    }

    public Long getMaxSize() {
        return currentPage + ImagesRequestBody.FLICKR_IMAGES_PER_PAGE;
    }


    /*
     * I have added a reset method here
     * that can be called from the UI because
     * if we have a filter option in the app,
     * we might need to refresh the whole data set
     * */
    public void reset() {
        currentPage = 0l;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }


    /*
     * I have created two abstract methods:
     * isLastPage() where the UI can specify if
     * this is the last page - this data usually comes from the backend.
     *
     * loadMore() where the UI can specify to load
     * more data when this method is called.
     *
     * We can also specify another method called
     * isLoading() - to let the UI display a loading View.
     * Since I did not need to display this, I have
     * commented it out.
     * */
    //public abstract boolean isLoading();

    public abstract boolean isLastPage();

    public abstract void loadMore(Long start, Long count);
}