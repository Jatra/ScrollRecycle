package uk.co.jatra.scrollrecyclerchild;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by tim on 27/10/2015.
 */
public class NestedVScrollTabStrip extends TabLayout implements NestedScrollingChild {
    private static final String TAG = NestedVScrollTabStrip.class.getSimpleName();
    private final NestedScrollingChildHelper helper;

    public NestedVScrollTabStrip(Context context) {
        this(context, null);
    }

    public NestedVScrollTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        helper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    int lastMotionY;
    boolean isBeingDragged;

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        final int action = ev.getAction();
//
//        if ((action == MotionEvent.ACTION_MOVE) && (isBeingDragged)) {
//            return true;
//        }
//
//
//        switch (action & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_MOVE: {
//                /*
//                 * mIsBeingDragged == false, otherwise the shortcut would have caught it. Check
//                 * whether the user has moved far enough from his original down touch.
//                 */
//
//                /*
//                * Locally do absolute value. mLastMotionY is set to the x value
//                * of the down event.
//                */
//                final int activePointerId = mActivePointerId;
//                if (activePointerId == INVALID_POINTER) {
//                    // If we don't have a valid id, the touch down wasn't on content.
//                    break;
//                }
//
//                final int pointerIndex = ev.findPointerIndex(activePointerId);
//                if (pointerIndex == -1) {
//                    Log.e(TAG, "Invalid pointerId=" + activePointerId
//                            + " in onInterceptTouchEvent");
//                    break;
//                }
//
//                final int y = (int) ev.getY(pointerIndex);
//                final int yDiff = (int) Math.abs(y - lastMotionY);
//                if (yDiff > mTouchSlop) {
//                    isBeingDragged = true;
//                    lastMotionY = y;
//                    initVelocityTrackerIfNotExists();
//                    mVelocityTracker.addMovement(ev);
//                    if (mParent != null) mParent.requestDisallowInterceptTouchEvent(true);
//                }
//                break;
//            }
//
//            case MotionEvent.ACTION_DOWN: {
//                final int x = (int) ev.getX();
//                if (!inChild((int) x, (int) ev.getY())) {
//                    isBeingDragged = false;
//                    recycleVelocityTracker();
//                    break;
//                }
//
//                /*
//                 * Remember location of down touch.
//                 * ACTION_DOWN always refers to pointer index 0.
//                 */
//                lastMotionY = x;
//                mActivePointerId = ev.getPointerId(0);
//
//                initOrResetVelocityTracker();
//                mVelocityTracker.addMovement(ev);
//
//                /*
//                * If being flinged and user touches the screen, initiate drag;
//                * otherwise don't.  mScroller.isFinished should be false when
//                * being flinged.
//                */
//                isBeingDragged = !mScroller.isFinished();
//                break;
//            }
//
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                /* Release the drag */
//                isBeingDragged = false;
//                mActivePointerId = INVALID_POINTER;
//                if (mScroller.springBack(mScrollX, mScrollY, 0, getScrollRange(), 0, 0)) {
//                    postInvalidateOnAnimation();
//                }
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN: {
//                final int index = ev.getActionIndex();
//                lastMotionY = (int) ev.getX(index);
//                mActivePointerId = ev.getPointerId(index);
//                break;
//            }
//            case MotionEvent.ACTION_POINTER_UP:
//                onSecondaryPointerUp(ev);
//                lastMotionY = (int) ev.getX(ev.findPointerIndex(mActivePointerId));
//                break;
//        }
//
//        /*
//        * The only time we want to intercept motion events is if we are in the
//        * drag mode.
//        */
//
//        boolean isBeingDraggedFromSuper =  super.onInterceptTouchEvent(ev);
//        Log.d(TAG, "isBeingDraggedFromSuper: "+isBeingDraggedFromSuper);
//        return isBeingDraggedFromSuper;
//    }



    /**
     * Enable or disable nested scrolling for this view.
     * <p/>
     * <p>If this property is set to true the view will be permitted to initiate nested
     * scrolling operations with a compatible parent view in the current hierarchy. If this
     * view does not implement nested scrolling this will have no effect. Disabling nested scrolling
     * while a nested scroll is in progress has the effect of {@link #stopNestedScroll() stopping}
     * the nested scroll.</p>
     *
     * @param enabled true to enable nested scrolling, false to disable
     * @see #isNestedScrollingEnabled()
     */
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        helper.setNestedScrollingEnabled(enabled);
    }

    /**
     * Returns true if nested scrolling is enabled for this view.
     * <p/>
     * <p>If nested scrolling is enabled and this View class implementation supports it,
     * this view will act as a nested scrolling child view when applicable, forwarding data
     * about the scroll operation in progress to a compatible and cooperating nested scrolling
     * parent.</p>
     *
     * @return true if nested scrolling is enabled
     * @see #setNestedScrollingEnabled(boolean)
     */
    @Override
    public boolean isNestedScrollingEnabled() {
        return helper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return helper.startNestedScroll(axes);
    }

    /**
     * Stop a nested scroll in progress.
     * <p/>
     * <p>Calling this method when a nested scroll is not currently in progress is harmless.</p>
     *
     * @see #startNestedScroll(int)
     */
    @Override
    public void stopNestedScroll() {
        helper.stopNestedScroll();
    }

    /**
     * Returns true if this view has a nested scrolling parent.
     * <p/>
     * <p>The presence of a nested scrolling parent indicates that this view has initiated
     * a nested scroll and it was accepted by an ancestor view further up the view hierarchy.</p>
     *
     * @return whether this view has a nested scrolling parent
     */
    @Override
    public boolean hasNestedScrollingParent() {
        return helper.hasNestedScrollingParent();
    }

    /**
     * Dispatch one step of a nested scroll in progress.
     * <p/>
     * <p>Implementations of views that support nested scrolling should call this to report
     * info about a scroll in progress to the current nested scrolling parent. If a nested scroll
     * is not currently in progress or nested scrolling is not
     * {@link #isNestedScrollingEnabled() enabled} for this view this method does nothing.</p>
     * <p/>
     * <p>Compatible View implementations should also call
     * {@link #dispatchNestedPreScroll(int, int, int[], int[]) dispatchNestedPreScroll} before
     * consuming a component of the scroll event themselves.</p>
     *
     * @param dxConsumed     Horizontal distance in pixels consumed by this view during this scroll step
     * @param dyConsumed     Vertical distance in pixels consumed by this view during this scroll step
     * @param dxUnconsumed   Horizontal scroll distance in pixels not consumed by this view
     * @param dyUnconsumed   Horizontal scroll distance in pixels not consumed by this view
     * @param offsetInWindow Optional. If not null, on return this will contain the offset
     *                       in local view coordinates of this view from before this operation
     *                       to after it completes. View implementations may use this to adjust
     *                       expected input coordinate tracking.
     * @return true if the event was dispatched, false if it could not be dispatched.
     * @see #dispatchNestedPreScroll(int, int, int[], int[])
     */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    /**
     * Dispatch one step of a nested scroll in progress before this view consumes any portion of it.
     * <p/>
     * <p>Nested pre-scroll events are to nested scroll events what touch intercept is to touch.
     * <code>dispatchNestedPreScroll</code> offers an opportunity for the parent view in a nested
     * scrolling operation to consume some or all of the scroll operation before the child view
     * consumes it.</p>
     *
     * @param dx             Horizontal scroll distance in pixels
     * @param dy             Vertical scroll distance in pixels
     * @param consumed       Output. If not null, consumed[0] will contain the consumed component of dx
     *                       and consumed[1] the consumed dy.
     * @param offsetInWindow Optional. If not null, on return this will contain the offset
     *                       in local view coordinates of this view from before this operation
     *                       to after it completes. View implementations may use this to adjust
     *                       expected input coordinate tracking.
     * @return true if the parent consumed some or all of the scroll delta
     * @see #dispatchNestedScroll(int, int, int, int, int[])
     */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return helper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    /**
     * Dispatch a fling to a nested scrolling parent before it is processed by this view.
     * <p/>
     * <p>Nested pre-fling events are to nested fling events what touch intercept is to touch
     * and what nested pre-scroll is to nested scroll. <code>dispatchNestedPreFling</code>
     * offsets an opportunity for the parent view in a nested fling to fully consume the fling
     * before the child view consumes it. If this method returns <code>true</code>, a nested
     * parent view consumed the fling and this view should not scroll as a result.</p>
     * <p/>
     * <p>For a better user experience, only one view in a nested scrolling chain should consume
     * the fling at a time. If a parent view consumed the fling this method will return false.
     * Custom view implementations should account for this in two ways:</p>
     * <p/>
     * <ul>
     * <li>If a custom view is paged and needs to settle to a fixed page-point, do not
     * call <code>dispatchNestedPreFling</code>; consume the fling and settle to a valid
     * position regardless.</li>
     * <li>If a nested parent does consume the fling, this view should not scroll at all,
     * even to settle back to a valid idle position.</li>
     * </ul>
     * <p/>
     * <p>Views should also not offer fling velocities to nested parent views along an axis
     * where scrolling is not currently supported; a {@link ScrollView ScrollView}
     * should not offer a horizontal fling velocity to its parents since scrolling along that
     * axis is not permitted and carrying velocity along that motion does not make sense.</p>
     *
     * @param velocityX Horizontal fling velocity in pixels per second
     * @param velocityY Vertical fling velocity in pixels per second
     * @return true if a nested scrolling parent consumed the fling
     */
    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return helper.dispatchNestedPreFling(velocityX, velocityY);
    }
}