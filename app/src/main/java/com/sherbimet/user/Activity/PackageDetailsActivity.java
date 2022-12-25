package com.sherbimet.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sherbimet.user.APIHelper.JsonFields;
import com.sherbimet.user.APIHelper.WebAuthorization;
import com.sherbimet.user.APIHelper.WebURL;
import com.sherbimet.user.R;
import com.sherbimet.user.Utils.AtClass;
import com.sherbimet.user.Utils.BaseActivity;
import com.sherbimet.user.Utils.ProgressBarHandler;
import com.sherbimet.user.Utils.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PackageDetailsActivity extends BaseActivity implements View.OnClickListener {
    ImageView iVBack;
    TextView tvTitle;

    LinearLayout llPackageDetails, llError, llNoInternetConnection;
    TextView tvMessage;
    Button btnRetry;

    ImageView iVPackageImage;

    TextView tvPackageName, tvPackagePrice, tvPackageDetails;
    Button btnConfirmBooking;

    UserSessionManager userSessionManager;
    ProgressBarHandler progressBarHandler;
    AtClass atClass;

    String PackageID, PackageName,PackageDescription,PackageAmount,PackageImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        Intent i = getIntent();

        if (i.hasExtra("PackageID")) {
            PackageID = i.getStringExtra("PackageID");
        } else {
            PackageID = "";
        }

        if (i.hasExtra("PackageName")) {
            PackageName = i.getStringExtra("PackageName");
        } else {
            PackageName = "Package Details";
        }

        userSessionManager = new UserSessionManager(this);
        progressBarHandler = new ProgressBarHandler(this);
        atClass = new AtClass();

        iVBack = findViewById(R.id.iVBack);
        iVBack.setOnClickListener(this);
        tvTitle = findViewById(R.id.tvTitle);

        llPackageDetails = findViewById(R.id.llPackageDetails);
        llError = findViewById(R.id.llError);
        llNoInternetConnection = findViewById(R.id.llNoInternetConnection);

        tvMessage = findViewById(R.id.tvMessage);

        btnRetry = findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);

        iVPackageImage = findViewById(R.id.iVPackageImage);

        tvPackageName = findViewById(R.id.tvPackageName);
        tvPackagePrice = findViewById(R.id.tvPackagePrice);
        tvPackageDetails = findViewById(R.id.tvPackageDetails);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);

        btnConfirmBooking.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTitle.setText(Html.fromHtml(PackageName, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvTitle.setText(Html.fromHtml(PackageName));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (atClass.isNetworkAvailable(this)) {
            getPackage();
        } else {
            llPackageDetails.setVisibility(View.GONE);
            llError.setVisibility(View.GONE);
            llNoInternetConnection.setVisibility(View.VISIBLE);
        }
    }

    private void getPackage() {
        progressBarHandler.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.PACKAGE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHandler.hide();
                llNoInternetConnection.setVisibility(View.VISIBLE);
                llError.setVisibility(View.GONE);
                llPackageDetails.setVisibility(View.GONE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return WebAuthorization.checkAuthentication();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(JsonFields.PACKAGE_REQUEST_PARAMS_PACKAGE_ID, PackageID);
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_ID, atClass.getDeviceID(PackageDetailsActivity.this));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TYPE, atClass.getDeviceType());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TOKEN, atClass.getRefreshedToken());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS, atClass.getOsInfo());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS, atClass.getRefreshedIpAddress(PackageDetailsActivity.this));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_MODEL_DETAILS, atClass.getDeviceManufacturerModel());
                params.put(JsonFields.COMMON_REQUEST_PARAM_APP_VERSION_DETAILS, atClass.getAppVersionNumberAndVersionCode());
                Log.d("params", String.valueOf(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void parseJSON(String response) {
        Log.d("RESPONSE", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonFields.FLAG);
            String Message = jsonObject.optString(JsonFields.MESSAGE);

            if (flag == 1) {
                progressBarHandler.hide();
                JSONArray arrPackage = jsonObject.optJSONArray(JsonFields.PACKAGE_RESPONSE_PACKAGE_ARRAY);
                if (arrPackage.length() > 0) {
                    for (int i = 0; i < arrPackage.length(); i++) {
                        JSONObject packageObject = arrPackage.optJSONObject(i);
                        PackageID = packageObject.optString(JsonFields.PACKAGE_RESPONSE_PACKAGE_ID);
                        PackageName = packageObject.optString(JsonFields.PACKAGE_RESPONSE_PACKAGE_NAME);
                        PackageDescription = packageObject.optString(JsonFields.PACKAGE_RESPONSE_PACKAGE_DESCRIPTION);
                        PackageAmount = packageObject.optString(JsonFields.PACKAGE_RESPONSE_PACKAGE_AMOUNT);
                        PackageImage = packageObject.optString(JsonFields.PACKAGE_RESPONSE_PACKAGE_IMAGE);

                        setPackageDetails();
                    }
                    llPackageDetails.setVisibility(View.VISIBLE);
                    llNoInternetConnection.setVisibility(View.GONE);
                    llError.setVisibility(View.GONE);
                }
            } else if (flag == 2) {


            } else if (flag == 3) {
                progressBarHandler.hide();
                llPackageDetails.setVisibility(View.VISIBLE);
                llNoInternetConnection.setVisibility(View.GONE);
                llError.setVisibility(View.GONE);
            } else {
                progressBarHandler.hide();
                llPackageDetails.setVisibility(View.GONE);
                llNoInternetConnection.setVisibility(View.GONE);
                llError.setVisibility(View.VISIBLE);
                tvMessage.setText(Message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPackageDetails() {

        Glide.with(this).load(PackageImage).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.app_icon_transparent).into(iVPackageImage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvPackageName.setText(Html.fromHtml(PackageName, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvPackageName.setText(Html.fromHtml(PackageName));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvPackageDetails.setText(Html.fromHtml(PackageDescription, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvPackageDetails.setText(Html.fromHtml(PackageDescription));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvPackagePrice.setText(Html.fromHtml(PackageAmount, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvPackagePrice.setText(Html.fromHtml(PackageAmount));
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iVBack:
                onBackPressed();
                break;

            case R.id.btnRetry:
                if (atClass.isNetworkAvailable(this)) {
                    Intent i = new Intent(this, PackageDetailsActivity.class);
                    i.putExtra("PackageID", PackageID);
                    i.putExtra("PackageName", PackageName);
                    startActivity(i);
                } else {
                    llPackageDetails.setVisibility(View.GONE);
                    llError.setVisibility(View.GONE);
                    llNoInternetConnection.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btnConfirmBooking:
                if (atClass.isNetworkAvailable(this)) {
                    Intent i = new Intent(this, AddNewServiceRequestActivity.class);
                    i.putExtra("PackageID", PackageID);
                    i.putExtra("PackageName", PackageName);
                    i.putExtra("PackageImage", PackageImage);
                    i.putExtra("PackageAmount", PackageAmount);
                    startActivity(i);
                } else {
                    llPackageDetails.setVisibility(View.GONE);
                    llError.setVisibility(View.GONE);
                    llNoInternetConnection.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}