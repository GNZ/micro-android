package com.gang.micro.core.gallery.common.item;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gang.micro.R;
import com.gang.micro.core.image.Image;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GalleryItemEditFragment extends DialogFragment {

    // UI elements
    @Bind(R.id.edit_dialog_toolbar)
    Toolbar toolbar;

    @Bind(R.id.name_edit_item)
    EditText name;

    @Bind(R.id.description_edit_item)
    EditText description;

    private GalleryItem caller;

    private GalleryItemFragment galleryItemFragment;

    Image image;

    public GalleryItemEditFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_edit_dialog, container, false);
        ButterKnife.bind(this, rootView);

        toolbar.setTitle(R.string.edit_item_title);

        toolbar.inflateMenu(R.menu.menu_gallery_item_edit);

        toolbar.setOnMenuItemClickListener(new GalleryItemEditMenuClickListener());

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (caller != null){
            image = caller.getImage();
            name.setText(image.getName());
            description.setText(image.getDescription());
        }

        return rootView;
    }

    public void setCaller(GalleryItem caller) {
        this.caller = caller;
    }

    public void setGalleryItemFragment(GalleryItemFragment galleryItemFragment) {
        this.galleryItemFragment = galleryItemFragment;
    }

    private void editImage() {
        String newName = name.getText().toString();
        String newDescription = description.getText().toString();

        if (!newName.equals(image.getName()) || !newDescription.equals(image.getDescription())) {
            image.setName(newName);
            image.setDescription(newDescription);

            caller.updateItem();

            galleryItemFragment.refresh();

            Toast.makeText(getContext(),R.string.image_changed,Toast.LENGTH_SHORT).show();
        }

        dismiss();
    }

    class GalleryItemEditMenuClickListener implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.gallery_item_edit_menu_button:
                    editImage();
                    return true;
                default:
                    return false;
            }
        }
    }

}
