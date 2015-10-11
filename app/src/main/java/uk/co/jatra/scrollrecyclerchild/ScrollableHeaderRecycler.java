package uk.co.jatra.scrollrecyclerchild;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tim on 09/10/2015.
 */
public class ScrollableHeaderRecycler extends RecyclerView {

    private static final String TAG = ScrollableHeaderRecycler.class.getSimpleName();

    private boolean isScrollingHeader;
    private HeadedAdapter adapter;

    public ScrollableHeaderRecycler(Context context) {
        this(context, null, 0);
    }

    public ScrollableHeaderRecycler(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollableHeaderRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(HeadedAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (isScrollingHeader) {
            if (isUpOrCancel(motionEvent)) {
                isScrollingHeader = false;
            }
            return false;
        }

        if (childUnderEvent(motionEvent) instanceof HeadedAdapter.HeaderWrapper) {
            isScrollingHeader = true;
            return false;
        }

        return super.onInterceptTouchEvent(motionEvent);
    }

    private View childUnderEvent(MotionEvent motionEvent) {
        return findChildViewUnder(motionEvent.getX(), motionEvent.getY());
    }

    private boolean isUpOrCancel(MotionEvent e) {
        return e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL;
    }

}
