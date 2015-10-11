package uk.co.jatra.scrollrecyclerchild;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by tim on 10/10/2015.
 */
public abstract class HeadedAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE = 1;
    public static final int HEADER_TYPE = 2;
    private List<T> data;
    protected View headerView;

    public HeadedAdapter() {
    }

    public HeadedAdapter(List<T> data, View headerView) {
        this.data = data;
        this.headerView = headerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE:
                return createItemViewHolder(parent);
            case HEADER_TYPE:
                return createHeaderViewHolder(parent);
            default:
                return null;
        }
    }

    public abstract RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent);

    public abstract RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent);

    @Override
    public int getItemViewType(int position) {
        return headerView != null && position == 0 ? HEADER_TYPE : ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return (data != null ? data.size() :-0) + (headerView != null ? 1 :-0);
    }

    public T getItem(int position) {
        return data.get(position);
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderWrapper container;

        private HeaderHolder(HeaderWrapper view) {
            super(view);
            container = view;
        }

        static HeaderHolder create(Context context) {
            HeaderWrapper container = new HeaderWrapper(context);
            return new HeaderHolder(container);
        }

        public void setHeader(View headerView) {
            container.removeAllViews();
            if (headerView != null) {
                container.addView(headerView);
            }
        }
    }

    static class HeaderWrapper extends FrameLayout {
        public HeaderWrapper(Context context) {
            super(context);
        }
    }
}
