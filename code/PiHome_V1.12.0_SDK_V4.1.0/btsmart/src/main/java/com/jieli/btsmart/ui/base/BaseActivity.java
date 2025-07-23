package com.jieli.btsmart.ui.base;

import android.app.Activity;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jieli.bluetooth.utils.JL_Log;
import com.jieli.btsmart.util.AppUtil;
import com.jieli.component.utils.ToastUtil;

/**
 * @author : chensenhua
 * @e-mail : chensenhua@zh-jieli.com
 * @date : 2020/8/11 7:44 PM
 * @desc :
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG = getClass().getSimpleName();
    private CustomBackPress mCustomBackPress;


    @Override
    public void onBackPressed() {
        if (mCustomBackPress != null && mCustomBackPress.onBack()) {
            return;
        }
        setResult(Activity.RESULT_OK);
        finish();
    }

    public boolean isValid() {
        return !isFinishing() && !isDestroyed();
    }

    public CustomBackPress getCustomBackPress() {
        return mCustomBackPress;
    }

    public void setCustomBackPress(CustomBackPress customBackPress) {
        mCustomBackPress = customBackPress;
    }

    /**
     * 切换fragment(不带tag)
     *
     * @param containerId layout id
     * @param fragment    target fragment
     */
    public void changeFragment(int containerId, Fragment fragment) {
        changeFragment(containerId, fragment, null);
    }

    /**
     * 切换fragment
     *
     * @param containerId layout id
     * @param fragment    fragment
     * @param fragmentTag fragment tag
     */

    public void changeFragment(int containerId, Fragment fragment, String fragmentTag) {
        if (fragment != null && !isFinishing()) {
            Fragment origin = getSupportFragmentManager().findFragmentById(containerId);
            changeFragment(containerId, origin, fragment, fragmentTag);
        }
    }

    /**
     * 切换fragment
     *
     * @param containerId layout id
     * @param origin      fragment
     * @param target      fragment
     * @param fragmentTag fragment tag
     */
    public void changeFragment(int containerId, Fragment origin, Fragment target, String fragmentTag) {
        if (target != null && !isFinishing()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!target.isAdded()) {
                if (!TextUtils.isEmpty(fragmentTag)) {
                    fragmentTransaction.add(containerId, target, fragmentTag);
                } else {
                    fragmentTransaction.add(containerId, target);
                }
            }
            if (origin != null) {
                fragmentTransaction.hide(origin);
            }
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.show(target);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    protected void showTips(String tips) {
        ToastUtil.showToastLong(tips);
        JL_Log.d(TAG, tips);
    }

    protected void showTips(String format, Object... args) {
        showTips(AppUtil.formatString(format, args));
    }

    public interface CustomBackPress {

        boolean onBack();
    }
}
