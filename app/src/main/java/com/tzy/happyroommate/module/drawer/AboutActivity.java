package com.tzy.happyroommate.module.drawer;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by skyline on 2015/8/29.
 */
public class AboutActivity extends BaseActivity {

    @InjectView(R.id.version)
    TextView version;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
        version.setText("v" + JUtils.getAppVersionName());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
