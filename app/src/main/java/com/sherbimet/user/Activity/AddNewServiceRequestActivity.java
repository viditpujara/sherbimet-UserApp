package com.sherbimet.user.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.textfield.TextInputEditText;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.sherbimet.user.APIHelper.JsonFields;
import com.sherbimet.user.APIHelper.WebAuthorization;
import com.sherbimet.user.APIHelper.WebURL;
import com.sherbimet.user.R;
import com.sherbimet.user.Utils.AtClass;
import com.sherbimet.user.Utils.BaseActivity;
import com.sherbimet.user.Utils.DashboardAreaManager;
import com.sherbimet.user.Utils.ProgressDialogHandler;
import com.sherbimet.user.Utils.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddNewServiceRequestActivity extends BaseActivity implements View.OnClickListener, PaymentResultListener {

    ImageView iVBack;
    TextView tvPackageName, tvPackageAmount;
    CircleImageView ciVPackageImage;


    TextInputEditText etBookingDate, etBookingTime, etBookingAddress, etBookingMessage;

    Button btnBookNow;

    AtClass atClass;
    ProgressDialogHandler progressDialogHandler;
    UserSessionManager userSessionManager;
    DashboardAreaManager dashboardAreaManager;

    LinearLayout llPaymentMethodNotSelected, llPaymentMethodSelected;
    ImageView ivPaymentMethodIcon;
    TextView tvPaymentMethod;

    String PackageID, PackageName, PackageAmount, PackageImage;
    String PaymentMethodID, PaymentMethodName;

    private static final int CHOOSE_PAYMENT_METHOD_REQUEST = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_service_request);

        Checkout.preload(this);

        Intent i = getIntent();

        if (i.hasExtra("PackageID")) {
            PackageID = i.getStringExtra("PackageID");
        } else {
            PackageID = "";
        }

        if (i.hasExtra("PackageName")) {
            PackageName = i.getStringExtra("PackageName");
        } else {
            PackageName = "";
        }

        if (i.hasExtra("PackageAmount")) {
            PackageAmount = i.getStringExtra("PackageAmount");
        } else {
            PackageAmount = "";
        }

        if (i.hasExtra("PackageImage")) {
            PackageImage = i.getStringExtra("PackageImage");
        } else {
            PackageImage = "";
        }

        atClass = new AtClass();
        progressDialogHandler = new ProgressDialogHandler(this);
        userSessionManager = new UserSessionManager(this);
        dashboardAreaManager = new DashboardAreaManager(this);

        iVBack = findViewById(R.id.iVBack);
        iVBack.setOnClickListener(this);

        tvPackageName = findViewById(R.id.tvPackageName);
        tvPackageAmount = findViewById(R.id.tvPackageAmount);

        ciVPackageImage = findViewById(R.id.ciVPackageImage);


        etBookingDate = findViewById(R.id.etBookingDate);
        etBookingDate.setFocusable(false);
        etBookingDate.setFocusableInTouchMode(false);
        etBookingDate.setInputType(InputType.TYPE_NULL);
        etBookingDate.setOnClickListener(this);

        etBookingTime = findViewById(R.id.etBookingTime);
        etBookingTime.setFocusable(false);
        etBookingTime.setFocusableInTouchMode(false);
        etBookingTime.setInputType(InputType.TYPE_NULL);
        etBookingTime.setOnClickListener(this);

        etBookingAddress = findViewById(R.id.etBookingAddress);
        etBookingMessage = findViewById(R.id.etBookingMessage);


        btnBookNow = findViewById(R.id.btnBookNow);
        btnBookNow.setOnClickListener(this);

        llPaymentMethodNotSelected = findViewById(R.id.llPaymentMethodNotSelected);
        llPaymentMethodNotSelected.setOnClickListener(this);
        llPaymentMethodSelected = findViewById(R.id.llPaymentMethodSelected);
        llPaymentMethodSelected.setOnClickListener(this);

        ivPaymentMethodIcon = findViewById(R.id.ivPaymentMethodIcon);
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod);

        setPackageDetailsDateTime();

    }

    private void setPackageDetailsDateTime() {
        Glide.with(this).load(PackageImage).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.app_icon_transparent).into(ciVPackageImage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvPackageName.setText(Html.fromHtml(PackageName, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvPackageName.setText(Html.fromHtml(PackageName));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvPackageAmount.setText(Html.fromHtml(PackageAmount, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvPackageAmount.setText(Html.fromHtml(PackageAmount));
        }

        etBookingDate.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        etBookingTime.setText(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));

        llPaymentMethodNotSelected.setVisibility(View.VISIBLE);
        llPaymentMethodSelected.setVisibility(View.GONE);

        PaymentMethodID = "";
        PaymentMethodName = "";

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iVBack:
                onBackPressed();
                break;

            case R.id.btnBookNow:
                if (atClass.isNetworkAvailable(this)) {
                    Log.d("Clicked", "Yes");
                    /*Log.d("PID", String.valueOf(checkPackageID()));
                    Log.d("BD", String.valueOf(checkBookingDate()));
                    Log.d("BT", String.valueOf(checkBookingTime()));
                    Log.d("BA", String.valueOf(checkAddress()));
                    Log.d("BM", String.valueOf(checkMessage()));*/

                    if (checkPackageID() && checkBookingDate() && checkBookingTime() && checkAddress() && checkMessage() && checkPaymentMethod()) {
                        Log.d("Valid", "Yes");
                        if (PaymentMethodID.equals("1")) {
                            startRazorPayPayment();
                        } else if (PaymentMethodID.equals("8")) {
                            addNewServiceRequest();
                        } else {
                            Toast.makeText(this, getString(R.string.payments_something_went_wrong_text), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.etBookingDate:
                selectBookingDate();
                break;

            case R.id.etBookingTime:
                selectBookingTime();
                break;

            case R.id.llPaymentMethodNotSelected:
            case R.id.llPaymentMethodSelected:
                Intent i2 = new Intent(this, PaymentsActivity.class);
                startActivityForResult(i2, CHOOSE_PAYMENT_METHOD_REQUEST);
                break;

        }
    }

    private void startRazorPayPayment() {
        int amount = Math.round(Float.parseFloat(PackageAmount.substring(1)) * 100);

        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.app_icon_transparent);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Sherbimet");
            options.put("theme.color", "#0d98ba");
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("Razor Pay Error", "Error in starting Razorpay Checkout", e);
        }
    }

    private void selectBookingTime() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(AddNewServiceRequestActivity.this, R.style.datepicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String hh, mm;

                if (selectedHour <= 9) {
                    hh = "0" + selectedHour;
                } else {
                    hh = String.valueOf(selectedHour);
                }


                if (selectedMinute <= 9) {
                    mm = "0" + selectedMinute;
                } else {
                    mm = String.valueOf(selectedMinute);
                }
                String time = hh + ":" + mm + ":" + "00";

                        /*SimpleDateFormat fmt = new SimpleDateFormat("hh:mm");
                        Date date = null;
                        try {
                            date = fmt.parse(time);
                        } catch (ParseException e) {

                            e.printStackTrace();
                        }

                        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm:ss");*/

                //String formattedTime = fmtOut.format(date);
                //String formattedTime = fmtOut.format(time);

                Log.d("Time", time);
                etBookingTime.setText(time);
            }
        }, hour, minute, true);//No 24 hour time
        //mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void selectBookingDate() {
        final Calendar c2 = Calendar.getInstance();
        int mYear = c2.get(Calendar.YEAR);
        int mMonth = c2.get(Calendar.MONTH);
        int mDay = c2.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                String mt, dy;
                if (monthOfYear < 10)
                    mt = "0" + monthOfYear;
                else mt = String.valueOf(monthOfYear);

                if (dayOfMonth < 10)
                    dy = "0" + dayOfMonth;
                else dy = String.valueOf(dayOfMonth);

                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                String BookingDate = dy + "-" + mt + "-" + year;
                etBookingDate.setText(BookingDate);
                Log.d("Booking Date", BookingDate);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    private void addNewServiceRequest() {
        progressDialogHandler.showPopupProgressSpinner(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.ADD_NEW_SERVICE_REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogHandler.showPopupProgressSpinner(false);
                Toast.makeText(AddNewServiceRequestActivity.this, getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
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
                params.put(JsonFields.ADD_NEW_BOOKING_REQUEST_REQUEST_PARAMS_AREA_ID, dashboardAreaManager.getAreaID());
                params.put(JsonFields.ADD_NEW_BOOKING_REQUEST_PACKAGE_ID, PackageID);
                params.put(JsonFields.ADD_NEW_BOOKING_REQUEST_PARAMS_BOOKING_DATE, etBookingDate.getText().toString().trim());
                params.put(JsonFields.ADD_NEW_BOOKING_REQUEST_PARAMS_BOOKING_TIME, etBookingTime.getText().toString().trim());
                params.put(JsonFields.ADD_NEW_BOOKING_REQUEST_PARAMS_BOOKING_MESSAGE, etBookingMessage.getText().toString().trim());
                params.put(JsonFields.ADD_NEW_BOOKING_REQUEST_PARAMS_BOOKING_ADDRESS, etBookingAddress.getText().toString().trim());
                if (PaymentMethodID.equals("1")) {
                    params.put(JsonFields.ADD_NEW_BOOKING_REQUEST_PARAMS_PAYMENT_METHOD_NAME, "Razorpay");
                } else if (PaymentMethodID.equals("8")) {
                    params.put(JsonFields.ADD_NEW_BOOKING_REQUEST_PARAMS_PAYMENT_METHOD_NAME, "Cash");
                } else {
                    params.put(JsonFields.ADD_NEW_BOOKING_REQUEST_PARAMS_PAYMENT_METHOD_NAME, "");
                }
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_ID, atClass.getDeviceID(AddNewServiceRequestActivity.this));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TYPE, atClass.getDeviceType());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TOKEN, atClass.getRefreshedToken());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS, atClass.getOsInfo());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS, atClass.getRefreshedIpAddress(AddNewServiceRequestActivity.this));
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
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonFields.FLAG);
            String Message = jsonObject.optString(JsonFields.MESSAGE);

            if (flag == 1) {
                progressDialogHandler.showPopupProgressSpinner(false);
                Intent i = new Intent(this, SuccessMessageActivity.class);
                i.putExtra("Message", Message);
                i.putExtra("FromScreenName", "AddNewServiceRequestActivity");
                i.putExtra("ToScreenName", "HomeActivity");
                startActivity(i);
                finish();
            } else if (flag == 3) {
                //User Is Deactivated By Admin
                progressDialogHandler.showPopupProgressSpinner(false);
                String LogoutTitle = jsonObject.optString(JsonFields.COMMON_LOGOUT_RESPONSE_TITLE);
                String LogoutMessage = jsonObject.optString(JsonFields.COMMON_LOGOUT_RESPONSE_MESSAGE);
                String LogoutIcon = jsonObject.optString(JsonFields.COMMON_LOGOUT_RESPONSE_ICON);

                /*Intent i = new Intent(this, LogoutActivity.class);
                i.putExtra("Title", LogoutTitle);
                i.putExtra("Message", LogoutMessage);
                i.putExtra("ImageURL", LogoutIcon);
                i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();*/
                //ShowUserDisabledDialog(LogoutTitle, LogoutMessage, LogoutIcon);
            } else {
                progressDialogHandler.showPopupProgressSpinner(false);
                Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPackageID() {
        boolean isPackageIDValid = false;

        if (PackageID != null && !PackageID.isEmpty() && !PackageID.equals("")) {
            isPackageIDValid = true;
        } else {
            isPackageIDValid = false;
            Toast.makeText(this, getString(R.string.add_booking_validation_package_error_text), Toast.LENGTH_SHORT).show();
        }
        return isPackageIDValid;
    }


    private boolean checkPaymentMethod() {
        boolean isPaymentMethodValid = false;

        if (PaymentMethodID != null && !PaymentMethodID.equals("") && !PaymentMethodID.isEmpty()
                && PaymentMethodName != null && !PaymentMethodName.equals("") && !PaymentMethodName.isEmpty()) {
            isPaymentMethodValid = true;
        } else {
            Toast.makeText(this, getString(R.string.add_booking_validation_payment_method_error_text), Toast.LENGTH_SHORT).show();
            isPaymentMethodValid = false;
        }
        return isPaymentMethodValid;
    }

    private boolean checkMessage() {
        boolean isBookingMessageValid = false;

        if (etBookingMessage.getText().toString().trim().equals("")) {
            isBookingMessageValid = false;
            etBookingMessage.setError(getString(R.string.add_booking_validation_message_error_text));
        } else {
            isBookingMessageValid = true;
            etBookingMessage.setError(null);
        }
        return isBookingMessageValid;
    }

    private boolean checkAddress() {
        boolean isBookingAddressValid = false;

        if (etBookingAddress.getText().toString().trim().equals("")) {
            isBookingAddressValid = false;
            etBookingAddress.setError(getString(R.string.add_booking_validation_address_error_text));
        } else {
            isBookingAddressValid = true;
            etBookingAddress.setError(null);
        }
        return isBookingAddressValid;
    }

    private boolean checkBookingTime() {
        boolean isBookingTimeValid = false;

        if (etBookingTime.getText().toString().trim().equals("")) {
            isBookingTimeValid = false;
            etBookingTime.setError(getString(R.string.add_booking_validation_time_error_text));
        } else {
            isBookingTimeValid = true;
            etBookingTime.setError(null);
        }
        return isBookingTimeValid;
    }

    private boolean checkBookingDate() {
        boolean isBookingDateValid = false;

        if (etBookingDate.getText().toString().trim().equals("")) {
            isBookingDateValid = false;
            etBookingDate.setError(getString(R.string.add_booking_validation_date_error_text));
        } else {
            isBookingDateValid = true;
            etBookingDate.setError(null);
        }
        return isBookingDateValid;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == CHOOSE_PAYMENT_METHOD_REQUEST && resultCode == RESULT_OK) {
                PaymentMethodID = data.getStringExtra("PaymentMethodID");
                PaymentMethodName = data.getStringExtra("PaymentMethodName");
                if (PaymentMethodID != null && !PaymentMethodID.isEmpty() && !PaymentMethodID.equals("") &&
                        PaymentMethodName != null && !PaymentMethodName.isEmpty() && !PaymentMethodName.equals("")) {
                    SetPaymentMethod();
                } else {
                    PaymentMethodID = "";
                    PaymentMethodName = "";
                    Toast.makeText(this, getString(R.string.payments_something_went_wrong_text), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void SetPaymentMethod() {
        llPaymentMethodNotSelected.setVisibility(View.GONE);
        llPaymentMethodSelected.setVisibility(View.VISIBLE);

        tvPaymentMethod.setText(PaymentMethodName);

        if (PaymentMethodID.equals("1")) {
            ivPaymentMethodIcon.setImageResource(R.drawable.ic_razor_pay);
        } else if (PaymentMethodID.equals("8")) {
            ivPaymentMethodIcon.setImageResource(R.drawable.ic_cod);
        } else {
            ivPaymentMethodIcon.setImageResource(R.drawable.ic_payment_method);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        // this method is called on payment success.
        addNewServiceRequest();
    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        Toast.makeText(this, getString(R.string.add_booking_razor_pay_error_text), Toast.LENGTH_SHORT).show();
    }
}