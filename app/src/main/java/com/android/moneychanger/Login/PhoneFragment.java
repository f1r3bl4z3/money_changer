package com.android.moneychanger.Login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.android.moneychanger.R;

public class PhoneFragment extends Fragment implements View.OnClickListener {

    EditText mPhoneNumber;
    Button mNext;
    CountryCodePicker ccp;
    RadioGroup radioGroup;
    RadioButton requestor, provider;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_authentication_phone, container, false);
        else
            container.removeView(view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeObjects();

        phoneEditTextButtonColor();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.change_requestor) {
                    Toast.makeText(getApplicationContext(), "choice: Change Requestor",
                            Toast.LENGTH_SHORT).show();
                } else if(checkedId == R.id.change_provider) {
                    Toast.makeText(getApplicationContext(), "choice: Change Provider",
                            Toast.LENGTH_SHORT).show();
                }
            }

        });

        TextView problemText = getView().findViewById(R.id.problem_text);
        problemText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("smsto:+255769064436"));
//                intent.putExtra("sms_body", "Leave us a text here and we will get back to you as soon as possible.");
//                if (intent.resolveActivity(getActivity().getPackageManager()) != null)
//                    startActivity(intent);

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/255769064436")));

                return true;
            }
        });
    }

    /*
     * Change the color of R.id.next Button Background
     * Color if there is text in the R.id.phone EditText
     */

    private void phoneEditTextButtonColor() {
        mNext.setActivated(false);
        mNext.setBackgroundColor(getResources().getColor(R.color.unselected));
        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    mNext.setActivated(true);
                    mNext.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                else {
                    mNext.setActivated(false);
                    mNext.setBackgroundColor(getResources().getColor(R.color.unselected));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private String getSelectedRadioButton(){
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find which radioButton is checked by id
        if(selectedId == requestor.getId()) {
            return "Requestor";
        } else if(selectedId == provider.getId()) {
            return "Provider";
        }
    }

    private String buildPhoneNumber(){
        String phoneBuilt = "";
        phoneBuilt = ccp.getSelectedCountryCodeWithPlus() + mPhoneNumber.getText().toString();

        return  phoneBuilt;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next:
                if(mPhoneNumber.getText().length() <= 0)
                    return;

                ((AuthenticationActivity) getActivity()).showProgressDialog("Connecting...");
                ((AuthenticationActivity) getActivity()).setNextActivity(getSelectedRadioButton());
                ((AuthenticationActivity) getActivity()).startPhoneNumberVerification(buildPhoneNumber());
                break;
        }
    }


    void initializeObjects(){
        mPhoneNumber = view.findViewById(R.id.phone);
        mNext = view.findViewById(R.id.next);
        ccp = view.findViewById(R.id.ccp);
        radioGroup = view.findViewById(R.id.radioGroup);
        requestor = view.findViewById(R.id.change_requestor);
        provider = view.findViewById(R.id.change_provider);

        mNext.setOnClickListener(this);
    }
}