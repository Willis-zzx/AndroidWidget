package com.example.datachartandroid;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;

import java.util.Collections;
import java.util.Comparator;

/**
 * 分享模块
 */
public class MainActivity3 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        final BottomSheetLayout bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet);
        final EditText shareText = (EditText) findViewById(R.id.editText);

        Button shareButton = (Button) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(shareText.getWindowToken(), 0);

                final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText.getText() + "");
                shareIntent.setType("text/plain");

                IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(getApplication(), shareIntent, "分享到...", new IntentPickerSheetView.OnIntentPickedListener() {
                    @Override
                    public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                        bottomSheetLayout.dismissSheet();
                        startActivity(activityInfo.getConcreteIntent(shareIntent));
                    }
                });


                // 过滤到像系统蓝牙这样的分享模式（可以过滤掉也可以不用过滤）
                /*
                intentPickerSheet.setFilter(new IntentPickerSheetView.Filter() {
                    @Override
                    public boolean include(IntentPickerSheetView.ActivityInfo info) {
                        return !info.componentName.getPackageName().startsWith("com.android");
                    }
                });
                */

                // 排序分享模块
                intentPickerSheet.setSortMethod(new Comparator<IntentPickerSheetView.ActivityInfo>() {
                    @Override
                    public int compare(IntentPickerSheetView.ActivityInfo lhs, IntentPickerSheetView.ActivityInfo rhs) {
                        return rhs.label.compareTo(lhs.label);
                    }
                });

                // 增加一个自定义的分享选择方式（可选）
                Drawable customDrawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, null);
                IntentPickerSheetView.ActivityInfo customInfo = new IntentPickerSheetView.ActivityInfo(customDrawable, "zhang phil custom", getApplicationContext(), MainActivity.class);
                intentPickerSheet.setMixins(Collections.singletonList(customInfo));

                bottomSheetLayout.showWithSheetView(intentPickerSheet);
            }
        });
    }

}