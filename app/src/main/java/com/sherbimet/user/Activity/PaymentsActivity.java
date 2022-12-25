package com.sherbimet.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sherbimet.user.R;
import com.sherbimet.user.Utils.BaseActivity;

public class PaymentsActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout llRazorPay,llPayPal,llVisaCard,llMasterCard,llBitcoin,llApplePay,llGooglePay,llAmazonPay,llCash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        llRazorPay = findViewById(R.id.llRazorPay);
        llPayPal = findViewById(R.id.llPayPal);
        llVisaCard = findViewById(R.id.llVisaCard);
        llMasterCard = findViewById(R.id.llMasterCard);
        llBitcoin = findViewById(R.id.llBitcoin);
        llApplePay = findViewById(R.id.llApplePay);
        llGooglePay = findViewById(R.id.llGooglePay);
        llAmazonPay = findViewById(R.id.llAmazonPay);
        llCash = findViewById(R.id.llCash);

        llRazorPay.setOnClickListener(this);
        llPayPal.setOnClickListener(this);
        llVisaCard.setOnClickListener(this);
        llMasterCard.setOnClickListener(this);
        llBitcoin.setOnClickListener(this);
        llApplePay.setOnClickListener(this);
        llGooglePay.setOnClickListener(this);
        llAmazonPay.setOnClickListener(this);
        llCash.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.llRazorPay:
                Intent intent1 = new Intent(this, AddNewServiceRequestActivity.class);
                intent1.putExtra("PaymentMethodID", "1");
                intent1.putExtra("PaymentMethodName", getString(R.string.payments_razor_pay_title));
                setResult(RESULT_OK, intent1);
                finish();
                break;

            case R.id.llCash:
                Intent intent2 = new Intent(this, AddNewServiceRequestActivity.class);
                intent2.putExtra("PaymentMethodID", "8");
                intent2.putExtra("PaymentMethodName", getString(R.string.payments_cash_on_delivery_title));
                setResult(RESULT_OK, intent2);
                finish();
                break;

            case R.id.llPayPal:
            case R.id.llVisaCard:
            case R.id.llMasterCard:
            case R.id.llBitcoin:
            case R.id.llApplePay:
            case R.id.llGooglePay:
            case R.id.llAmazonPay:
                Toast.makeText(this, getString(R.string.payments_not_available_toast_title), Toast.LENGTH_SHORT).show();
                break;


        }
    }
}