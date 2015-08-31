package com.tzy.happyroommate.module.trend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.beam.nucleus.view.NucleusFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.widget.FloatingActionsMenu;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell on 2015/8/12.
 */
@RequiresPresenter(TrendPresenter.class)
public class TrendFragment extends NucleusFragment<TrendPresenter> {
    private static EasyRecyclerView mRecyclerView;
    private TrendAdapter adapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);//fragment里面有菜单

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trend, container, false);
        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.recyclerview);
        com.tzy.happyroommate.widget.FloatingActionButton button = (com.tzy.happyroommate.widget.FloatingActionButton) view.findViewById(R.id.fab_send);
        button.setIcon(R.mipmap.ic_fab_star);
        button.setStrokeVisible(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteTrendActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapterWithProgress(adapter = new TrendAdapter(getActivity()));
        mRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_trend, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent intent = new Intent(getActivity(), WriteTrendActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_FROM_BACKGROUND);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void stopRefresh() {
        adapter.clear();
    }

    public void addDate(List<Trend> trends) {
        adapter.addAll(trends);
    }

    public void stopLoadMore() {
        adapter.stopMore();
    }

}
