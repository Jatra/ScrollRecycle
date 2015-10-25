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
        if (isEventInChildBounds(header, motionEvent)) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    private boolean isEventInChildBounds(View header, MotionEvent motionEvent) {
        return header != null && isPointInChildBounds(header, (int)motionEvent.getX(), (int)motionEvent.getY());
    }
}
