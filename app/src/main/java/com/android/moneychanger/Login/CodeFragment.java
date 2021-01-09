package com.android.moneychanger.Login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.cardview.widget.CardView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.android.moneychanger.R;

import java.util.Locale;

public class CodeFragment extends Fragment {
    View view;
    PinView mCode;

    TextView mPhone,mPhoneLong,mTextViewCountDown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.fragment_registration_details, container, false);
        else
            container.removeView(view);

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AuthenticationActivity) getActivity()).dismissProgressDialog();

        initializeObjects();

        codeInputHandling();
        setPhoneText();


        resendSMScardView = view.findViewById(R.id.cardview_resend_sms);
        resendSMScardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
                ((AuthenticationActivity) getActivity()).startPhoneNumberVerification(((AuthenticationActivity) getActivity()).getPhoneNumber());
            }
        });

        resendTV = view.findViewById(R.id.textview_resend);
        mTextViewCountDown = view.findViewById(R.id.textview_timer);
        updateCountDownText();
        startTimer();
    }

    private void codeInputHandling() {
        mCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 6){
                    ((AuthenticationActivity)getActivity()).verifyPhoneNumberWithCode(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setPhoneText(){
        mPhone.setText("Verify " + ((AuthenticationActivity)getActivity()).getPhoneNumber());
        mPhoneLong.setText("Waiting to automatically detect a SMS sent to " + ((AuthenticationActivity)getActivity()).getPhoneNumber());
    }

    void initializeObjects(){
        mCode = view.findViewById(R.id.code);
        mPhone = view.findViewById(R.id.phone);
        mPhoneLong = view.findViewById(R.id.phoneLong);
    }


    //countDownTimer code
    private static final long START_TIME_IN_MILLIS = 60000;//one minute
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis;
    CardView resendSMScardView;
    TextView resendTV;

    public void startTimer() {

        mTimeLeftInMillis = START_TIME_IN_MILLIS;

        //UI elements behavior change
        resendTV.setTextColor(getResources().getColor(android.R.color.darker_gray));
        resendSMScardView.setEnabled(false);
        mTextViewCountDown.setVisibility(View.VISIBLE);

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                if(getContext()!=null) {
                    resendTV.setTextColor(getResources().getColor(android.R.color.black));
                    mTextViewCountDown.setVisibility(View.INVISIBLE);
                    resendSMScardView.setEnabled(true);
                }
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
}