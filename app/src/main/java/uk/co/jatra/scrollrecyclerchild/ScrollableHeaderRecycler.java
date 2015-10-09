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

    View headerView;

    public ScrollableHeaderRecycler(Context context) {
        super(context);
    }

    public ScrollableHeaderRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableHeaderRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        View view = findChildViewUnder(e.getX(), e.getY());
        if (view == headerView) {
            return false;
        }
        return super.onInterceptTouchEvent(e);
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }
}
