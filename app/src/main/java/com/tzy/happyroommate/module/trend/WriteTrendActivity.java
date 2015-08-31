package com.tzy.happyroommate.module.trend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.utils.AddedView.AddImagePieceView;
import com.tzy.happyroommate.utils.AddedView.ImagePieceView;
import com.tzy.happyroommate.utils.AddedView.PieceView;
import com.tzy.happyroommate.utils.AddedView.PieceViewGroup;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tzy on 2015/8/12.
 */
@RequiresPresenter(WriteTrendPresenter.class)
public class WriteTrendActivity extends BaseActivity<WriteTrendPresenter> {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.content)
    EditText content;
    @InjectView(R.id.imageviewgroup)
    PieceViewGroup imageviewgroup;

    public static final String RESPONSE = "response";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_write_detail);
        ButterKnife.inject(this);
        imageviewgroup.addEnder(initImageView());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public PieceView initImageView() {
        PieceView img = new AddImagePieceView(this);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSelector();
            }
        });
        return img;
    }

    public void showImageSelector() {
        new MaterialDialog.Builder(this)
                .title("请选择图片的来源")
                .items(new String[]{"拍照","从手机相册选择","网络"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        getPresenter().changeFace(i);
                    }
                }).show();
    }
    //通过presenter改变图片后，在用view设置。
    public ImagePieceView addImageAddedView(Uri uri){
        ImagePieceView view = new ImagePieceView(this);
        view.setImage(uri);
        imageviewgroup.add(view);
        JUtils.Log("Count:" + imageviewgroup.getCount());
        return view;
    }

    public String getTrendContent(){
        return content.getText().toString();
    }

    public PieceViewGroup getImageviewgroup(){
        return imageviewgroup;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_info,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.complete) {
            getPresenter().sendTrendInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData(List<Trend> list){

    }



    @Override
    public void onBackPressed() {
        if (imageviewgroup.isInEditMode()){
            imageviewgroup.finishEdit();
            return;
        }
        super.onBackPressed();
    }
}
