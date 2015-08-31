package com.tzy.happyroommate.module.main;

import android.os.Bundle;

import com.jude.beam.nucleus.manager.Presenter;
import com.tzy.happyroommate.model.RoomModel;
import com.tzy.happyroommate.model.bean.Room;
import com.tzy.happyroommate.model.callback.DataCallback;

/**
 * Created by tzy on 2015/8/23.
 */
public class AddRoomPresenter extends Presenter<AddRoomActivity>{
    private AddRoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
    }

    public void queryRoom(String id){
        getView().showProgress("查询中");
        RoomModel.getInstance().queryRoomById(id, new DataCallback<Room>() {
            @Override
            public void success(String info, Room data) {
                getView().dismissProgress();
                adapter = new AddRoomAdapter(getView());
                adapter.addAll(data);
                getView().showQueryResult(adapter);

            }

            @Override
            public void failure(String info) {
                super.failure(info);
                getView().dismissProgress();
            }
        });

    }
}
