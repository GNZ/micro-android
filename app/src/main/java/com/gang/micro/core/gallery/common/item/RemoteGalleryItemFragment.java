package com.gang.micro.core.gallery.common.item;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gang.micro.R;
import com.gang.micro.core.MicroApplication;
import com.gang.micro.core.utils.io.ImageIO;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemoteGalleryItemFragment extends GalleryItemFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_remote_view_analysis, container, false);
        ButterKnife.bind(this, rootView);

        return initUI(rootView);
    }

    @OnClick(R.id.view_analysis_save_fab)
    void saveInLocalGallery() {

        (new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                Bitmap bitmap = caller.getBitmap();
                return ImageIO.saveImage(image) && ImageIO.savePicture(bitmap, image);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    String msg = getResources().getString(R.string.image_save) + " " + image.getName();

                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();

                    ((MicroApplication)getActivity().getApplication())
                            .getLocalGalleryAdapter()
                            .add(image);
                }
            }
        }).execute();
    }
}
