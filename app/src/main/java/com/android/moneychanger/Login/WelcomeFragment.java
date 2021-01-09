package com.android.moneychanger.Login;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.moneychanger.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {


    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        TextView tncBtn = getView().findViewById(R.id.text_agree);
//
//        tncBtn.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                final CharSequence[] items = { "Terms & Conditions" , "Privacy Policy" };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int item) {
//                        if(item==0)
//                        {
//                            //((AuthenticationActivity) getActivity()).openTermsAndConditionsFragment();
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://gloxpage.web.app/terms_and_conditions.html")));
//                        }
//                        else if(item==1)
//                        {
//                            //((AuthenticationActivity) getActivity()).openPrivacyPolicyFragment();
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://gloxpage.web.app/privacy_policy.html")));
//                        }
//
//                    }
//                });
//
//                AlertDialog alert = builder.create();
//                alert.show();
//                return false;
//            }
//        });

        //display the page with agree button
        Button agreeBtn = getView().findViewById(R.id.button_agree);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthenticationActivity) getActivity()).openPhoneFragment();
            }
        });

    }


}
