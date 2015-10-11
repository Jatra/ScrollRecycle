package uk.co.jatra.scrollrecyclerchild;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tim on 11/10/2015.
 */
public class ScrollingHeaderCoordinatorLayout extends CoordinatorLayout {

    public ScrollingHeaderCoordinatorLayout(Context context) {
        super(context);
    }

    public ScrollingHeaderCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollingHeaderCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        View header = findViewById(R.id.header);
        if (header != null && isPointInChildBounds(motionEvent, header)) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    private boolean isPointInChildBounds(MotionEvent motionEvent, View header) {
        return isPointInChildBounds(header, (int)motionEvent.getX(), (int)motionEvent.getY());
    }
}
