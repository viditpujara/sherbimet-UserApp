package com.sherbimet.user.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sherbimet.user.APIHelper.JsonFields;
import com.sherbimet.user.APIHelper.WebAuthorization;
import com.sherbimet.user.APIHelper.WebURL;
import com.sherbimet.user.Adapter.SubServiceAdapter;
import com.sherbimet.user.Model.SubService;
import com.sherbimet.user.R;
import com.sherbimet.user.Utils.AtClass;
import com.sherbimet.user.Utils.BaseActivity;
import com.sherbimet.user.Utils.ItemOffsetDecoration;
import com.sherbimet.user.Utils.ProgressBarHandler;
import com.sherbimet.user.Utils.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubServiceActivity extends BaseActivity implements View.OnClickListener {
    UserSessionManager userSessionManager;
    AtClass atClass;
    ProgressBarHandler progressBarHandler;

    LinearLayout llSubService, llError, llNoInternetConnection;

    RecyclerView rVSubService;

    TextView tvMessage, tvTitle;

    Button btnRetry;

    ImageView iVBack;

    String ServiceID, ServiceName;

    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    int currentPageValue = 1;
    LinearLayoutManager linearLayoutManager;

    ArrayList<SubService> listSubService = new ArrayList<SubService>();
    SubServiceAdapter subCategoryAdapter = new SubServiceAdapter(listSubService);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_service);

        Intent i = getIntent();

        if (i.hasExtra("ServiceID")) {
            ServiceID = i.getStringExtra("ServiceID");
        } else {
            ServiceID = "";
        }

        if (i.hasExtra("ServiceName")) {
            ServiceName = i.getStringExtra("ServiceName");
        } else {
            ServiceName = "Sub Service";
        }

        userSessionManager = new UserSessionManager(this);
        atClass = new AtClass();
        progressBarHandler = new ProgressBarHandler(this);

        tvTitle = findViewById(R.id.tvTitle);
        iVBack = findViewById(R.id.iVBack);
        iVBack.setOnClickListener(this);

        rVSubService = findViewById(R.id.rVSubService);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_large);
        rVSubService.addItemDecoration(itemDecoration);
        linearLayoutManager = new LinearLayoutManager(this);
        rVSubService.setLayoutManager(linearLayoutManager);
        rVSubService.setAdapter(subCategoryAdapter);


        llSubService = findViewById(R.id.llSubService);
        llError = findViewById(R.id.llError);
        llNoInternetConnection = findViewById(R.id.llNoInternetConnection);

        tvMessage = findViewById(R.id.tvMessage);
        btnRetry = findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTitle.setText(Html.fromHtml(ServiceName, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvTitle.setText(Html.fromHtml(ServiceName));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRetry:
                if (atClass.isNetworkAvailable(this)) {
                    Intent i = new Intent(this, SubServiceActivity.class);
                    i.putExtra("ServiceID", ServiceID);
                    i.putExtra("ServiceName", ServiceName);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.sub_category_no_internet), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.iVBack:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        rVSubService.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView,int dx,int dy){
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isScrolling && currentItems + scrollOutItems == totalItems){
                    isScrolling = false;
                    if (atClass.isNetworkAvailable(SubServiceActivity.this)){
                        currentPageValue++;
                        getSubCategory();
                    } else{
                        llSubService.setVisibility(View.GONE);
                        llError.setVisibility(View.GONE);
                        llNoInternetConnection.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        if (atClass.isNetworkAvailable(SubServiceActivity.this)){
            getSubCategory();
        } else{
            llSubService.setVisibility(View.GONE);
            llError.setVisibility(View.GONE);
            llNoInternetConnection.setVisibility(View.VISIBLE);
        }
    }

    private void getSubCategory() {
        progressBarHandler.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.SUB_SERVICE_URL, new Response.Listener<String>() {
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
                llSubService.setVisibility(View.GONE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return WebAuthorization.checkAuthentication();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(JsonFields.SUB_SERVICE_REQUEST_PARAMS_SERVICE_ID, ServiceID);
                params.put(JsonFields.COMMON_REQUEST_PARAMS_CURRENT_PAGE, String.valueOf(currentPageValue));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_ID, atClass.getDeviceID(SubServiceActivity.this));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TYPE, atClass.getDeviceType());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TOKEN, atClass.getRefreshedToken());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS, atClass.getOsInfo());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS, atClass.getRefreshedIpAddress(SubServiceActivity.this));
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
            if (currentPageValue == 1) {
                listSubService.clear();
            }

            if (flag == 1) {
                progressBarHandler.hide();
                JSONArray arrSubService = jsonObject.optJSONArray(JsonFields.SUB_SERVICE_RESPONSE_SUB_SERVICE_ARRAY);
                if (arrSubService.length() > 0) {
                    for (int i = 0;i<arrSubService.length(); i++){
                        JSONObject subServiceObject = arrSubService.optJSONObject(i);
                        String subServiceID = subServiceObject.optString(JsonFields.SUB_SERVICE_RESPONSE_SUB_SERVICE_ID);
                        String subServiceName = subServiceObject.optString(JsonFields.SUB_SERVICE_RESPONSE_SUB_SERVICE_NAME);
                        String subServiceImage = subServiceObject.optString(JsonFields.SUB_SERVICE_RESPONSE_SUB_SERVICE_IMAGE);

                        listSubService.add(new SubService(subServiceID, subServiceName, subServiceImage));
                    }
                    subCategoryAdapter.notifyDataSetChanged();
                    llSubService.setVisibility(View.VISIBLE);
                    llNoInternetConnection.setVisibility(View.GONE);
                    llError.setVisibility(View.GONE);
                }
            } else if (flag == 2) {


            } else if (flag == 3) {
                progressBarHandler.hide();
                llSubService.setVisibility(View.VISIBLE);
                llNoInternetConnection.setVisibility(View.GONE);
                llError.setVisibility(View.GONE);
            } else {
                progressBarHandler.hide();
                llSubService.setVisibility(View.GONE);
                llNoInternetConnection.setVisibility(View.GONE);
                llError.setVisibility(View.VISIBLE);
                tvMessage.setText(Message);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
