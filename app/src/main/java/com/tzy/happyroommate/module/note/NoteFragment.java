package com.tzy.happyroommate.module.note;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dexafree.materialList.card.Card;
import com.dexafree.materialList.card.OnButtonClickListener;
import com.dexafree.materialList.card.provider.BasicImageButtonsCardProvider;
import com.dexafree.materialList.view.MaterialListView;
import com.jude.beam.nucleus.factory.RequiresPresenter;
import com.jude.beam.nucleus.view.NucleusFragment;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.NoteModel;
import com.tzy.happyroommate.model.bean.Note;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by tzy on 2015/8/13.
 */
@RequiresPresenter(NotePresenter.class)
public class NoteFragment extends NucleusFragment<NotePresenter> {

    private EasyRecyclerView mRecyclerView;
    private List<String> mDatas;
    private StaggeredHomeAdapter mStaggeredHomeAdapter;
    private String content;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);//fragment里面有菜单
        initData();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_single_recyclerview, container, false);
        ButterKnife.inject(this, view);
        mRecyclerView = (EasyRecyclerView) view.findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //initEvent();
        return view;
    }

    private void initEvent()
    {
        mStaggeredHomeAdapter.setOnItemClickLitener(new StaggeredHomeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),
                        position + " click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),
                        position + " long click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void initData()
    {
        NoteModel.getInstance().getAllNote(new DataCallback<List<Note>>() {
            @Override
            public void success(String info, List<Note> data) {
                JUtils.Log("note" + data.size());
                mDatas = new ArrayList<String>();
                for (int i = 0; i < data.size(); i++) {
                    mDatas.add(data.get(i).getContent());
                }
                mRecyclerView.setAdapter(mStaggeredHomeAdapter = new StaggeredHomeAdapter(getActivity(), mDatas));
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_staggered, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.id_action_add:
                showSendDialog();
                break;
        }
        return true;
    }

    private void showSendDialog(){
        new MaterialDialog.Builder(getActivity())
                .title(R.string.dialog_send_note)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("填写便利贴内容", content, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog materialDialog, CharSequence charSequence) {
                        NoteModel.getInstance().sendNote(charSequence.toString(), new DataCallback<Note>() {
                            @Override
                            public void success(String info, Note data) {
                                mStaggeredHomeAdapter.addData(1,data.getContent());
                            }
                        });
                    }
                }).show();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}




