/*
 * Copyright (C) 2014 VanirAOSP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.vanir.navbar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.android.internal.util.vanir.NavbarConstants.NavbarConstant;
import com.android.settings.R;
//import com.android.settings.util.HardwareKeyNavbarHelper;
import com.vanir.util.DeviceUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class NavbarSettingsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        CompoundButton.OnCheckedChangeListener {
    private static final String TAG = NavbarSettingsFragment.class.getSimpleName();

    private static final int SOFTKEY_LONG_PRES_DEF_VALUE = ViewConfiguration.getLongPressTimeout();
    private static final int SOFTKEY_LONG_PRESS_TIMEOUT_MIN_VAL = 225;
    private static final int SOFTKEY_LONG_PRESS_TIMEOUT_MAX_VAL = 575;

    private SeekBar mSoftkeyLongPress;

    private TextView mSoftkeyLongPressValue;

    private Switch mNavring;

    private CheckBox mSideKeys;
    private CheckBox mArrows;
    private LinearLayout mLayouts;

//    private Switch mEnabledSwitch;

	// value stored in SettingsProvider
    private static int HValue;
    private static int LValue;
    private static int WValue;
    private static int LPValue;

    private Handler mHandler = new Handler();
/*    private SettingsObserver mSettingsObserver;

    class SettingsObserver extends ContentObserver {
        SettingsObserver(Handler handler) {
            super(handler);
        }

        void observe() {
            ContentResolver resolver = getActivity().getContentResolver();
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.ENABLE_NAVIGATION_BAR), false, this);
        }

        @Override
        public void onChange(boolean selfChange) {
            final ContentResolver resolver = getActivity().getContentResolver();

            boolean enabled = Settings.System.getInt(resolver,
                         Settings.System.ENABLE_NAVIGATION_BAR, 0) == 1;
            mEnabledSwitch.setChecked(enabled);
        }
    }
*/
    public NavbarSettingsFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

/*        if (HardwareKeyNavbarHelper.shouldShowNavbarToggle()) {
            final Activity activity = getActivity();
            mEnabledSwitch = new Switch(activity);
            final int padding = activity.getResources().getDimensionPixelSize(R.dimen.action_bar_switch_padding);
            mEnabledSwitch.setPaddingRelative(0, 0, padding, 0);
            mEnabledSwitch.setOnCheckedChangeListener(this);
        }
*/    }

    @Override
    public void onStart() {
        super.onStart();
/*        if (HardwareKeyNavbarHelper.shouldShowNavbarToggle()) {
            final Activity activity = getActivity();
            activity.getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                    ActionBar.DISPLAY_SHOW_CUSTOM);
            activity.getActionBar().setCustomView(mEnabledSwitch, new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER_VERTICAL | Gravity.END));
            mEnabledSwitch.setChecked((Settings.System.getInt(activity.getContentResolver(),
                Settings.System.ENABLE_NAVIGATION_BAR, 0) == 1));
        }
*/    }

    @Override
    public void onStop() {
        super.onStop();
/*        if (HardwareKeyNavbarHelper.shouldShowNavbarToggle()) {
            final Activity activity = getActivity();
            activity.getActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_CUSTOM);
            activity.getActionBar().setCustomView(null);
        }
*/    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Resources res = getActivity().getResources();
        final ContentResolver cr = getActivity().getContentResolver();

        LPValue = Settings.System.getInt(cr, Settings.System.SOFTKEY_LONG_PRESS_CONFIGURATION, SOFTKEY_LONG_PRES_DEF_VALUE);
    }

    @Override
    public void onResume() {
        super.onResume();
/*        if (HardwareKeyNavbarHelper.shouldShowNavbarToggle()) {
            if (mSettingsObserver == null) {
                mSettingsObserver = new SettingsObserver(mHandler);
                mSettingsObserver.observe();
            }
        }
*/
    }

    @Override
    public void onPause() {
        super.onPause();
/*        if (mSettingsObserver != null) {
            ContentResolver resolver = getActivity().getContentResolver();
            resolver.unregisterContentObserver(mSettingsObserver);
            mSettingsObserver = null;
        }
*/    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.navbar_settings, container, false);
        final View view = v;

        final Activity activity = getActivity();
        final ContentResolver cr = activity.getContentResolver();

		// Softkey longpress timeout
        mSoftkeyLongPress = (SeekBar) v.findViewById(R.id.navigation_bar_longpress_timeout);
        mSoftkeyLongPressValue = (TextView) v.findViewById(R.id.navigation_bar_longpress_timeout_value);
        mSoftkeyLongPress.setMax(SOFTKEY_LONG_PRESS_TIMEOUT_MAX_VAL);
        mSoftkeyLongPress.setProgress(LPValue);
        mSoftkeyLongPressValue.setText(String.valueOf(LPValue) + "ms");
        mSoftkeyLongPress.setOnSeekBarChangeListener(this);

        // Legacy side menu keys
        mSideKeys = (CheckBox) v.findViewById(R.id.sidekey_checkbox);
        mSideKeys.setChecked(Settings.System.getInt(cr, Settings.System.NAVIGATION_BAR_SIDEKEYS, 1) == 1);
        mSideKeys.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = ((CheckBox) v).isChecked();
                Settings.System.putInt(cr, Settings.System.NAVIGATION_BAR_SIDEKEYS, isChecked ? 1 : 0);
                updatePreferences(view);
            }
        });

        // Custom IME key layout
        mArrows = (CheckBox) v.findViewById(R.id.arrows_checkbox);
        mArrows.setChecked(Settings.System.getInt(cr, Settings.System.NAVIGATION_BAR_ARROWS, 0) == 1);
        mArrows.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.System.putInt(cr, Settings.System.NAVIGATION_BAR_ARROWS, mArrows.isChecked() ? 1 : 0);
            }
        });

        // Alternate key layouts
        mLayouts = (LinearLayout) v.findViewById(R.id.alternate_layouts);
        mLayouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutnumber = Settings.System.getInt(cr, Settings.System.NAVIGATION_BAR_ALTERNATE_LAYOUTS, 1);
                final CharSequence[] items = {" 1 "," 2 "," 3 "," 4 "," 5 "};
                AlertDialog dialog;

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(R.string.layouts_dialog_title);
                builder.setSingleChoiceItems(items, layoutnumber - 1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Settings.System.putInt(cr, Settings.System.NAVIGATION_BAR_ALTERNATE_LAYOUTS, item+1);
                        dialog.dismiss();
                        updatePreferences(view);
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });

        // Navigation ring
        mNavring = (Switch) v.findViewById(R.id.enable_navigation_ring);
        mNavring.setChecked((Settings.System.getInt(activity.getContentResolver(),
                Settings.System.ENABLE_NAVIGATION_RING, 1) == 1));
        mNavring.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.System.putInt(cr, Settings.System.ENABLE_NAVIGATION_RING, mNavring.isChecked() ? 1 : 0);
            }
        });

        updatePreferences(view);
        return v;
    }

    /*private int getSharedPreferenceValue(String string, int value, int storedValue) {
        // loads previous bar states per type
        SharedPreferences prefs = getActivity().getSharedPreferences("last_slider_values", Context.MODE_PRIVATE);
        return prefs.getInt(string,
                (int)(100.0 * ( storedValue - (mMinHeightPercent/100.0) * value) /
                ( (mMaxHeightPercent/100.0) * value - (mMinHeightPercent/100.0) * value )));
    } */

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/*        if (buttonView == mEnabledSwitch) {
            mEnabledSwitch.setEnabled(false);
            HardwareKeyNavbarHelper.writeEnableNavbarOption(getActivity(), mEnabledSwitch.isChecked());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEnabledSwitch.setEnabled(true);
                }
            }, 1000);
        }
*/    }

    @Override
    public void onProgressChanged(SeekBar seekbar, int rawprogress, boolean fromUser) {
        ContentResolver cr = getActivity().getContentResolver();
        double proportion = 1.0;

        if (fromUser) {
            if (seekbar == mSoftkeyLongPress) {
                LPValue = rawprogress + SOFTKEY_LONG_PRESS_TIMEOUT_MIN_VAL;
                mSoftkeyLongPressValue.setText(String.valueOf(LPValue) + "ms");
                Settings.System.putInt(cr,
                        Settings.System.SOFTKEY_LONG_PRESS_CONFIGURATION, LPValue);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        SharedPreferences prefs = getActivity().getSharedPreferences("last_slider_values", Context.MODE_PRIVATE);
        /*prefs.edit().putInt("heightPercent", mNavigationBarHeight.getProgress())
                    .putInt("heightLandscapePercent", mNavigationBarHeightLandscape.getProgress())
                    .putInt("widthPercent", mNavigationBarWidth.getProgress()).commit();*/
    }

    private void updatePreferences(final View v) {
        final Activity activity = getActivity();
        final ContentResolver cr = activity.getContentResolver();

        TextView menuSummary = (TextView) v.findViewById(R.id.sidekey_text);
        TextView layoutSummary = (TextView) v.findViewById(R.id.alternate_layout_text);
        TextView arrowsSummary = (TextView) v.findViewById(R.id.arrows_text);

        int layoutnumber = Settings.System.getInt(cr, Settings.System.NAVIGATION_BAR_ALTERNATE_LAYOUTS, 1);
        if (layoutnumber > 1 && layoutnumber < 5) {
            menuSummary.setText(getString(R.string.alternate_menu_summary));
            layoutSummary.setText(layoutnumber
                    + " " + getString(R.string.alternate_key_layouts_enabled_partial_summary)
                    + " " + getString(R.string.alternate_key_layouts_enabled_summary));
        } else if (layoutnumber == 5) {
            menuSummary.setText(getString(R.string.alternate_menu_summary));
            layoutSummary.setText(getString(R.string.alternate_key_layouts_all_enabled_summary));
        } else {
            menuSummary.setText(getString(R.string.enable_sidekeys_summary));
            layoutSummary.setText(getString(R.string.alternate_key_layouts_summary));
        }

        // we need space for the extra IME options.  Disable if it's not available.
        boolean allow = layoutnumber > 1 || mSideKeys.isChecked();
        mArrows.setEnabled(allow);
        if (!allow) {
            mArrows.setChecked(false);
            Settings.System.putInt(cr, Settings.System.NAVIGATION_BAR_ARROWS, 0);
            arrowsSummary.setText(getString(R.string.enable_ime_layout_disabled));
        } else {
            arrowsSummary.setText(getString(R.string.enable_ime_layout_summary));
        }

        if (!DeviceUtils.isPackageInstalled(activity, "com.google.android.googlequicksearchbox")) {
            mNavring.setVisibility(View.GONE);
            v.findViewById(R.id.enable_navigation_ring_text).setVisibility(View.GONE);
        }
    }
}
