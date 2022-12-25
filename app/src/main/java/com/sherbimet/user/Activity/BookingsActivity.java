package com.sherbimet.user.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import com.sherbimet.user.Adapter.BookingsAdapter;
import com.sherbimet.user.Model.Bookings;
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

public class BookingsActivity extends BaseActivity implements View.OnClickListener {
    UserSessionManager userSessionManager;
    AtClass atClass;

    ProgressBarHandler progressBarHandler;

    LinearLayout llBookings, llError, llNoInternetConnection;
    RecyclerView rVBookings;

    TextView tvMessage, tvTitle;

    Button btnRetry;

    ImageView iVBack;

    String AreaID;

    Boolean isScrolling = false;
    int currentItems = 0;
    int totalItems = 0;
    int scrollOutItems = 0;
    int currentPageValue = 1;
    LinearLayoutManager linearLayoutManager;

    ArrayList<Bookings> listBookings = new ArrayList<Bookings>();
    BookingsAdapter bookingsAdapter;

    String Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        bookingsAdapter = new BookingsAdapter(listBookings);

        userSessionManager = new UserSessionManager(this);
        atClass = new AtClass();
        progressBarHandler = new ProgressBarHandler(this);

        tvTitle = findViewById(R.id.tvTitle);
        iVBack = findViewById(R.id.iVBack);
        iVBack.setOnClickListener(this);

        rVBookings = findViewById(R.id.rVBookings);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_large);
        rVBookings.addItemDecoration(itemDecoration);
        linearLayoutManager = new LinearLayoutManager(this);
        rVBookings.setLayoutManager(linearLayoutManager);
        rVBookings.setAdapter(bookingsAdapter);


        llBookings = findViewById(R.id.llBookings);
        llError = findViewById(R.id.llError);
        llNoInternetConnection = findViewById(R.id.llNoInternetConnection);

        tvMessage = findViewById(R.id.tvMessage);
        btnRetry = findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnRetry:
                if (atClass.isNetworkAvailable(this)) {
                    Intent i = new Intent(this, BookingsActivity.class);
                    i.putExtra("Title", Title);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.booking_request_list_no_internet_toast_text), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.iVBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("AreaID", AreaID);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        rVBookings.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,int newState){
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (isScrolling && currentItems + scrollOutItems == totalItems){
                    isScrolling = false;
                    if (atClass.isNetworkAvailable(BookingsActivity.this)){
                        currentPageValue++;
                        getBookings();
                    } else{
                        llBookings.setVisibility(View.GONE);
                        llError.setVisibility(View.GONE);
                        llNoInternetConnection.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        if (atClass.isNetworkAvailable(BookingsActivity.this)){
            getBookings();
        } else{
            llBookings.setVisibility(View.GONE);
            llError.setVisibility(View.GONE);
            llNoInternetConnection.setVisibility(View.VISIBLE);

        }
    }

    private void getBookings() {
        progressBarHandler.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.BOOKINGS_REQUESTS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHandler.show();
                llNoInternetConnection.setVisibility(View.VISIBLE);
                llError.setVisibility(View.GONE);
                llBookings.setVisibility(View.GONE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return WebAuthorization.checkAuthentication();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(JsonFields.COMMON_REQUEST_PARAMS_USER_ID, userSessionManager.getUserID());
                params.put(JsonFields.COMMON_REQUEST_PARAMS_CURRENT_PAGE, String.valueOf(currentPageValue));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_ID, atClass.getDeviceID(BookingsActivity.this));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TYPE, atClass.getDeviceType());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TOKEN, atClass.getRefreshedToken());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS, atClass.getOsInfo());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS, atClass.getRefreshedIpAddress(BookingsActivity.this));
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

            String UserName = jsonObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_USER_NAME);
            String UserImage = jsonObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_USER_IMAGE);

            if (currentPageValue == 1) {
                listBookings.clear();
            }

            if (flag == 1) {
                AreaID = jsonObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_USER_AREA_ID);
                progressBarHandler.hide();
                JSONArray arrBookings = jsonObject.optJSONArray(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_ARRAY);
                if (arrBookings.length() > 0) {
                    for (int i = 0; i<arrBookings.length(); i++){
                        JSONObject bookingsObject = arrBookings.optJSONObject(i);
                        String BookingID = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_ID);
                        String BookingDateTime = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_DATE_TIME);
                        String BookingAddress = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_ADDRESS);
                        String BookingMessage = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_MESSAGE);
                        String BookingTotalAmount = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_TOTAL_AMOUNT);
                        String WorkerID = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_ID);
                        String WorkerImage = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_IMAGE);
                        String WorkerName = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_NAME);
                        String WorkerGender = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_GENDER);
                        String WorkerMobile = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_MOBILE);
                        String WorkerEmail = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_EMAIL);
                        String BookingStatusID = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_STATUS_ID);
                        String BookingStatus = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_STATUS);
                        String PackageName = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_PACKAGE_NAME);
                        String CanCancel = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_CAN_CANCEL);
                        String CancelReason = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_CANCEL_REASON);
                        String CanFeedback = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_CAN_FEEDBACK);
                        String FeedbackMessage = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_FEEDBACK_MESSAGE);
                        String FeedbackRatings = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_FEEDBACK_RATING);
                        String PaymentMethod = bookingsObject.optString(JsonFields.BOOKINGS_REQUESTS_RESPONSE_BOOKING_PAYMENT_METHOD);

                        listBookings.add(new Bookings(BookingID, BookingDateTime, BookingAddress, BookingMessage, BookingTotalAmount, WorkerID, WorkerImage, WorkerName, WorkerGender, WorkerMobile, WorkerEmail, BookingStatusID, BookingStatus, PackageName, CanCancel, CancelReason, CanFeedback, FeedbackMessage, FeedbackRatings, UserName, UserImage,PaymentMethod));
                    }
                    bookingsAdapter.notifyDataSetChanged();
                    llBookings.setVisibility(View.VISIBLE);
                    llNoInternetConnection.setVisibility(View.GONE);
                    llError.setVisibility(View.GONE);
                }
            } else if (flag == 2) {


            } else if (flag == 3) {
                progressBarHandler.hide();
                llBookings.setVisibility(View.VISIBLE);
                llNoInternetConnection.setVisibility(View.GONE);
                llError.setVisibility(View.GONE);
            } else {
                progressBarHandler.hide();
                llBookings.setVisibility(View.GONE);
                llNoInternetConnection.setVisibility(View.GONE);
                llError.setVisibility(View.VISIBLE);
                tvMessage.setText(Message);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
