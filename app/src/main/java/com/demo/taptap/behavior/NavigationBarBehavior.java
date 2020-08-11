package com.demo.taptap.behavior;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.demo.taptap.R;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class NavigationBarBehavior extends CoordinatorLayout.Behavior<View> {

    public NavigationBarBehavior() {
    }

    public NavigationBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static final String TAG = "NavigationBarBehavior";
    private RelativeLayout navigationBar;
    private int translationY = 0;
    private int barColor = Color.rgb(3, 218, 197);

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (navigationBar == null){
            ViewGroup rootView = (ViewGroup) parent.getParent().getParent();
            navigationBar = rootView.findViewById(R.id.rlNavigationBar);
            Log.d(TAG, "layoutDependsOn: " + navigationBar);
        }
        return dependency.getId() == R.id.list;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        //        获取原始距离
        if (translationY == 0){
            translationY = (int) dependency.getY() - child.getHeight();
            Log.d(TAG, "translationY: " + translationY);
        }
//        获取当前距离content列表的距离
        float depY = dependency.getY() - child.getHeight();
//        除以2用于区分滑动到上半部还是下半部
        int ban = translationY / 2;
        float offset = 0;
        int color = barColor;

//        处于原始距离的下半部移动  减去10是为了修复移到屏幕外后还有几像素的露白
        if (depY > (ban-10)){
//            求出移动距离占child的比重  因为除的是原始距离所以需要乘以2是才能形成视觉差
            offset = (depY / translationY * child.getHeight() - child.getHeight()) * 2;
            color = Color.TRANSPARENT;
        }else {
//            处于原始距离的上半部移动
//            当滑动到上半部时直接使用一半header的高度计算出占child的比重
            offset = depY / ban * child.getHeight();
//            由于是从屏幕外到屏幕内 所以需要从负值到0
            offset = offset > 0 ?  -offset:0;
        }
//        Log.d(TAG, "depY: " + depY + " ban: " + ban + " offset: " + offset);
        child.setBackgroundColor(color);
        child.setTranslationY(offset);
        navigationBar.setTranslationY(offset);
        return super.onDependentViewChanged(parent, child, dependency);
    }

    /**
     * float depY = dependency.getY() - child.getHeight();
     *         float bili = -(depY / translationY);
     *         float offset = bili * child.getHeight();
     *         Log.d(TAG, "depY: " + depY + "  bili: " + bili + " offset: " + offset);
     *         offset = offset > 0 ? 0 : offset;
     */

}
