package com.android.moneychanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class RequestorMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestor_main);

    }

    int kuku = 0, chips = 0, sausage = 0, mishkaki = 0, maxNo = 1000;
    //values
    int pKuku = 100, pChips = 50, pSausage = 200, pMishkaki = 500;
    int totalPrice = 0;

    public void decreaseQuantity(View view) {
        switch (view.getId()) {
            case R.id.chipsdecquantityBtn:
                //check quantity range before passing it to display function
                if (chips == 0) {
                } else {
                    chips--;
                }

                display(chips, R.id.chipsincquantityBtn);  //used incquantity to save time
                break;                                       //since they relate to same txtview for value display

            case R.id.kukudecquantityBtn:
                if (kuku == 0) {
                } else {
                    kuku--;
                }

                display(kuku, R.id.kukuincquantityBtn);
                break;

            case R.id.sausagedecquantityBtn:
                if (sausage == 0) {
                } else {
                    sausage--;
                }

                display(sausage, R.id.sausageincquantityBtn);
                break;

            case R.id.mishkakidecquantityBtn:
                if (mishkaki == 0) {
                } else {
                    mishkaki--;
                }

                display(mishkaki, R.id.mishkakiincquantityBtn);
                break;
        }
    }

    public void increaseQuantity(View view) {
        switch (view.getId()) {

            case R.id.chipsincquantityBtn:
                //check quantity range before passing it to display function
                if (chips == maxNo) {
                } else {
                    chips++;
                }

                display(chips, R.id.chipsincquantityBtn);
                break;

            case R.id.kukuincquantityBtn:
                if (kuku == maxNo) {
                } else {
                    kuku++;
                }
                display(kuku, R.id.kukuincquantityBtn);
                break;

            case R.id.sausageincquantityBtn:
                if (sausage == maxNo) {
                } else {
                    sausage++;
                }
                display(sausage, R.id.sausageincquantityBtn);
                break;

            case R.id.mishkakiincquantityBtn:
                if (mishkaki == maxNo) {
                } else {
                    mishkaki++;
                }
                display(mishkaki, R.id.mishkakiincquantityBtn);
                break;
        }
    }

    public void display(int quantity, int resourceID) {

        switch (resourceID) {

            case R.id.chipsincquantityBtn:
            case R.id.chipsdecquantityBtn:
                TextView chipsTV = (TextView) findViewById(R.id.chipsQuantity_txtview);
                chipsTV.setText("" + quantity);
                break;

            case R.id.kukuincquantityBtn:
            case R.id.kukudecquantityBtn:
                TextView kukuTV = (TextView) findViewById(R.id.kukuQuantity_txtview);
                kukuTV.setText("" + quantity);
                break;

            case R.id.sausageincquantityBtn:
            case R.id.sausagedecquantityBtn:
                TextView sausageTV = (TextView) findViewById(R.id.sausageQuantity_txtview);
                sausageTV.setText("" + quantity);
                break;

            case R.id.mishkakiincquantityBtn:
            case R.id.mishkakidecquantityBtn:
                TextView mishkakiTV = (TextView) findViewById(R.id.mishkakiQuantity_txtview);
                mishkakiTV.setText("" + quantity);
                break;
        }
        displayPrice();
    }

    private void CalculatePrice() {
        totalPrice = kuku * pKuku + chips * pChips + sausage * pSausage + mishkaki * pMishkaki;
//        int maj = Integer.parseInt(((TextView) findViewById(R.id.majiQuantity_txtview)).getText().toString());
//        int sod = Integer.parseInt(((TextView) findViewById(R.id.sodaQuantity_txtview)).getText().toString());
//        int emb = Integer.parseInt(((TextView) findViewById(R.id.azamembeQuantity_txtview)).getText().toString());
    }

    public void displayPrice() {
        CalculatePrice();
        TextView priceTV = (TextView) findViewById(R.id.grandTotalLbl);
        priceTV.setText("Tsh. " + totalPrice);
        displayGrandTotal();
    }
//
//        public void addOrder() {
//            commitSingleOrder();
//            displayOrderQty();
//            grandTotal+=totalPrice;
//            //store drinks data
//            tMaji+=maji;
//            taEmbe+=azamEmbe;
//            tSoda+=soda;
//
//            addSodaString();
//            resetView();
//            //save prices of @ order
//
//        }

//        public void resetView()
//        {
//            //houseCleaning
//            EditText sodaET = (EditText) findViewById(R.id.sodaEditTxt);
//            sodaET.setText("");
//            sodaET.setVisibility(View.INVISIBLE);
//
//            ResetValues();
//            displayPrice();
//        }


    public void displayGrandTotal() {
        TextView grandtotalTV = (TextView) findViewById(R.id.grandTotalLbl);
        grandtotalTV.setText("Tsh. " + totalPrice);
    }

    public void sendMessage(View view) {

        //open choose dealer activity for choosing dealer
        Intent i = new Intent(getApplicationContext(), ChooseDealerActivity.class);
        startActivity(i);
    }
}