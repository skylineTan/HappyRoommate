package com.tzy.happyroommate.module.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.beam.nucleus.view.NucleusFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.rollviewpager.RollPagerView;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.bean.Article;
import com.tzy.happyroommate.widget.FloatingActionButton;

import java.util.List;

/**
 * Created by tzy on 2015/8/13.
 */
@RequiresPresenter(ArticlePresenter.class)
public class ArticleFragment extends NucleusFragment<ArticlePresenter> {
    private RollPagerView mRollViewPager;
    private EasyRecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        JUtils.Log("在fragment里面");
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.recyclerview);
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.fab_send);
        button.setIcon(R.mipmap.ic_fab_star);
        button.setStrokeVisible(false);
        mRollViewPager.setAdapter(new TestAdapter());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapterWithProgress(mAdapter = new ArticleAdapter(getActivity()));
        mRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().refresh();
            }
        });
        mAdapter.setMore(R.layout.view_list_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getPresenter().loadMore();
            }
        });
        mAdapter.setNoMore(R.layout.view_list_nomore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ArticleWriteActivity.class));
            }
        });
        return view;
    }

    public void stopRefresh() {
        mAdapter.clear();
    }

    public void addDate(List<Article> articles) {
        mAdapter.addAll(articles);
    }

    public void stopLoadMore() {
        mAdapter.stopMore();
    }

    private class TestAdapter extends DynamicPagerAdapter {
        private int[] imgs = {
                R.mipmap.img1,
                R.mipmap.img2,
                R.mipmap.img3,
                R.mipmap.img4,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
