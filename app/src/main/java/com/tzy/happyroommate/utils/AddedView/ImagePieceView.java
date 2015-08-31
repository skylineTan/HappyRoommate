package com.tzy.happyroommate.utils.AddedView;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tzy.happyroommate.R;

public class ImagePieceView extends PieceView {

    ImageView mImage;

    public ImagePieceView(Context context) {
        super(context);
    }

    public ImagePieceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImagePieceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        super.initView();
        mImage = new ImageView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImage.setLayoutParams(params);
        mImage.setImageResource(R.mipmap.ic_add);
        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(mImage);
    }

    public void setImage(Uri uri){
        mImage.setImageURI(uri);
    }
}
