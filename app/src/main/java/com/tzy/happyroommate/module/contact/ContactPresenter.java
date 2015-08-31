package com.tzy.happyroommate.module.contact;

import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.model.AttentionModel;
import com.tzy.happyroommate.model.bean.MyUser;
import com.tzy.happyroommate.model.bean.Trend;
import com.tzy.happyroommate.model.callback.DataCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tzy on 2015/8/13.
 */
public class ContactPresenter extends Presenter<ContactFragment> {
    private ArrayList<MyUser> mArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        refresh();
    }

    @Override
    protected void onTakeView(ContactFragment view) {
        super.onTakeView(view);
        refresh();
        if (mArrayList.size() != 0) {
            getView().stopRefresh();
            getView().addDate(mArrayList);//一次性更新拿到的所有数据
        }
    }

    public void refresh() {
        AttentionModel.getInstance().getAllAttention(new DataCallback<List<MyUser>>() {
            @Override
            public void success(String info, List<MyUser> data) {
                if (getView() != null) {
                    if (data == null || data.size() == 0)
                        getView().stopRefresh();
                    else {
                        getView().stopRefresh();
                        getView().addDate(data);
                        mArrayList.clear();
                        mArrayList.addAll(data);
                    }
                }
            }

            @Override
            public void failure(String info) {
                super.failure(info);
                JUtils.Toast("得到联系人列表失败");
            }
        });
    }


    public void loadMore() {
        AttentionModel.getInstance().getAllAttention(new DataCallback<List<MyUser>>() {
            @Override
            public void success(String info, List<MyUser> data) {
                if (getView() != null) {
                    if (data == null || data.size() == 0)
                        getView().stopLoadMore();
                    else if (data.size() == mArrayList.size()) {
                        getView().stopLoadMore();
                    } else {
                        getView().addDate(data);
                        mArrayList.addAll(data);
                    }
                }
            }
        });
    }
}
