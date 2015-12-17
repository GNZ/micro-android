package com.gang.micro.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.NSD.Microscope;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;


public class ChooseMicroscopeDialogFragment extends DialogFragment {


    private static final String TAG = "ChooseMicroscopeDialog";
    @Bind(R.id.microscopes_list) CardListView microscopesList;
    @Bind(R.id.no_microscope_textView) TextView noMicroscopeTextView;
    HashMap<String,Microscope> microscopes;
    private OnCompleteListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.select_microscope))
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
        if (!microscopes.isEmpty()){
            noMicroscopeTextView.setVisibility(View.GONE);
            for(Microscope e: microscopes.values()){

                Card card = new Card(rootView.getContext());

                CardHeader header = new CardHeader(rootView.getContext());

                header.setTitle(e.getName());
                card.setTitle(e.getIp());
                card.addCardHeader(header);

                CardThumbnail thumb = new CardThumbnail(rootView.getContext());
                thumb.setDrawableResource(R.drawable.microscope);

                card.addCardThumbnail(thumb);

                card.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        //TODO save the ip in some place
                        mListener.onComplete(card.getTitle());
                        dismiss();
                    }
                });
                Log.d(TAG, e.getIp());
                cards.add(card);

                CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(rootView.getContext(), cards);

                if (microscopesList != null) {
                    microscopesList.setAdapter(mCardArrayAdapter);
                }
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
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    public void setMicroscopes(HashMap<String,Microscope> microscopes){
        this.microscopes = microscopes;
    }

    public static interface OnCompleteListener {
        public abstract void onComplete(String IP);
    }

}
