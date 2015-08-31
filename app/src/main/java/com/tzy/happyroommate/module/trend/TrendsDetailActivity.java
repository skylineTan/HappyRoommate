package com.tzy.happyroommate.module.trend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.tagview.TAGView;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.app.BaseActivity;
import com.tzy.happyroommate.model.bean.Comment;
import com.tzy.happyroommate.model.bean.Trend;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tzy on 2015/8/9.
 */
@RequiresPresenter(TrendsDetailPresenter.class)
public class TrendsDetailActivity extends BaseActivity<TrendsDetailPresenter> {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.face)
    SimpleDraweeView face;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.mainContent)
    TextView content;
    @InjectView(R.id.imageList)
    GridView imageList;
    @InjectView(R.id.praise)
    TextView praise;
    @InjectView(R.id.comment)
    TextView comment;
    @InjectView(R.id.view_praise)
    ImageView viewPraise;
    @InjectView(R.id.view_comment)
    ImageView viewComment;
    @InjectView(R.id.recyclerview)
    EasyRecyclerView recyclerview;
    @InjectView(R.id.view_delete)
    TAGView viewDelete;

    private String commentContent;
    private Trend data;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_detail);//这里开始乱码了
        ButterKnife.inject(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapterWithProgress(adapter = new CommentAdapter(this));
        recyclerview.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().refresh();
            }
        });
        adapter.setMore(R.layout.view_list_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getPresenter().loadMore();
            }
        });
        adapter.setNoMore(R.layout.view_list_nomore);
        viewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditCommentDialog();
            }
        });
        viewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteTrendDialog();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showEditCommentDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.dialog_comment)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("在此处输入你的评论", commentContent, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        getPresenter().sendCommentInfo(charSequence.toString(), data);
                    }
                }).show();
    }

    private void showDeleteTrendDialog(){
        new MaterialDialog.Builder(this)
                .title(R.string.dialog_delete)
                .content(R.string.dialog_delete_content)
                .positiveText("确定")
                .negativeText("取消")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        getPresenter().deleteTrendInfo(data);
                        finish();
                        getPresenter().refresh();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).show();
    }

    public void setTrends(final Trend data) {
        this.data = data;
        face.setImageURI(Uri.parse(data.getTrendPic()));
        name.setText(data.getRoomName());
        content.setText(data.getContent());
        time.setText(data.getCreatedAt());
        praise.setText(String.valueOf(data.getPraiseCount()));
        comment.setText(String.valueOf(data.getCommentCount()));
        if (data.getImages() != null) {
            imageList.setAdapter(new NetImageListAdapter(this, data.getImages()));
            //imageList.setLayoutParams(new LinearLayout.LayoutParams(JUtils.dip2px(84) * ((data.getImages().length - 1) / 2 + 1), JUtils.dip2px(84) * ((data.getImages().length - 1) / 2 + 1)));
            imageList.setVisibility(View.VISIBLE);
        } else {
            imageList.setVisibility(View.INVISIBLE);
        }
    }

    public void stopRefresh() {
        adapter.clear();
    }

    public void addDate(List<Comment> comments) {
        adapter.addAll(comments);
    }

    public void stopLoadMore() {
        adapter.stopMore();
    }
}
