package com.tzy.happyroommate.module.main;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.tagview.TAGView;
import com.jude.utils.JUtils;
import com.tzy.happyroommate.R;
import com.tzy.happyroommate.model.RoomModel;
import com.tzy.happyroommate.model.bean.Room;
import com.tzy.happyroommate.model.callback.StatusCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dell on 2015/8/23.
 */
public class AddRoomViewHolder extends BaseViewHolder<Room> {
    @InjectView(R.id.tv_room_name)
    TextView tvRoomName;
    @InjectView(R.id.tv_room_id)
    TextView tvRoomId;
    @InjectView(R.id.view_add)
    TAGView viewAdd;
    @InjectView(R.id.cv_room_add)
    CardView cvRoomAdd;

    private Room data;
    public AddRoomViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_room_add);
        ButterKnife.inject(this, itemView);
        viewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomModel.getInstance().addRoom(AddRoomActivity.getRoomIdInfo(), new StatusCallback() {
                    @Override
                    public void success(String info) {
                        JUtils.Toast("加入寝室成功");
                    }

                    @Override
                    public void failure(String info) {
                        super.failure(info);
                        JUtils.Toast("加入寝室失败");
                    }
                });
            }
        });
    }

    @Override
    public void setData(Room data) {
        super.setData(data);
        tvRoomName.setText(data.getRoomName());
        tvRoomId.setText(data.getObjectId());
    }
}
