package com.tzy.happyroommate.module.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.beam.nucleus.view.NucleusFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.bean.MyUser;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by tzy on 2015/8/13.
 */
@RequiresPresenter(ContactPresenter.class)
public class ContactFragment extends NucleusFragment<ContactPresenter> implements AdapterViewCompat.OnItemLongClickListener{
    private EasyRecyclerView mRecyclerView;
    private ContactAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.rv_friends);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapterWithProgress(mAdapter = new ContactAdapter(getActivity()));
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
        mAdapter.setNoMore(R.layout.view_contact_nomore);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initToolbar() {
    }

    public void stopRefresh(){
        mAdapter.clear();
    }

    public void addDate(List<MyUser> users){
        mAdapter.addAll(users);
    }

    public void stopLoadMore(){
        mAdapter.stopMore();
    }


    @Override
    public boolean onItemLongClick(AdapterViewCompat<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
