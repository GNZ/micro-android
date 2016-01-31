package com.gang.micro.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.nsd.Microscope;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;


public class ChooseMicroscopeDialogFragment extends DialogFragment {


    private static final String TAG = "ChooseMicroscopeDialog";
    private static int IP = 1;
    @Bind(R.id.container)
    ViewGroup microscopesList;
    @Bind(R.id.no_microscope_textView)
    TextView noMicroscopeTextView;
    HashMap<String, Microscope> microscopes;
    private OnCompleteListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.select_microscope))
                .setPositiveButton(getResources().getString(R.string.settings), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent settingsActivity = new Intent(getActivity(), SettingsActivity.class);
                        startActivity(settingsActivity);
                        dismiss();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                getActivity().finish();
                            }
                        }
                );
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.microscopes_dialog, null);
        ButterKnife.bind(this, rootView);
        ArrayList<Card> cards = new ArrayList<Card>();
        if (!microscopes.isEmpty()) {
            noMicroscopeTextView.setVisibility(View.GONE);

            for (Microscope e : microscopes.values()) {

                ViewGroup newMicroscope = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
                        R.layout.microscope_button, microscopesList, false
                );


                ((TextView) newMicroscope.findViewById(R.id.name_textView)).setText(e.getName());
                ((TextView) newMicroscope.findViewById(R.id.name_textView)).setTag(e.getIp());

                newMicroscope.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ip = (String) ((TextView) v.findViewById(R.id.name_textView)).getTag();
                        mListener.onComplete(ip);
                        dismiss();
                    }
                });

                Log.d(TAG, e.getIp());
                microscopesList.addView(newMicroscope);
            }
        } else {
            Log.d(TAG, "Dictionary empty");
        }
        builder.setView(rootView);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Button positive_button =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
        //positive_button.setBackground(getResources().getDrawable(android.R.drawable.ic_menu_manage));
    }

    public void setMicroscopes(HashMap<String, Microscope> microscopes) {
        this.microscopes = microscopes;
    }

    public static interface OnCompleteListener {
        public abstract void onComplete(String IP);
    }

}
