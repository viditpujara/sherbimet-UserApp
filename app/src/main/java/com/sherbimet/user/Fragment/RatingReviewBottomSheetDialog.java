package com.sherbimet.user.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ServerError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.sherbimet.user.APIHelper.JsonFields;
import com.sherbimet.user.APIHelper.WebAuthorization;
import com.sherbimet.user.APIHelper.WebURL;
import com.sherbimet.user.Activity.SuccessMessageActivity;
import com.sherbimet.user.R;
import com.sherbimet.user.Utils.AtClass;
import com.sherbimet.user.Utils.ProgressDialogHandler;
import com.sherbimet.user.Utils.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RatingReviewBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    ImageView iVBack;

    Dialog CancelReceiptBottomSheetDialog;

    Context mcontext;

    ProgressDialogHandler progressDialogHandler;
    AtClass atClass;
    UserSessionManager userSessionManager;

    boolean isDialogOpen = false;

    CircleImageView civUserImageDrawable;
    TextView tvUserName;
    TextInputEditText etReviewMessage;
    RatingBar ratingBar;

    Button btnSubmit;

    String UserName, UserImage, BookingID, WorkerID;

    public void CloseDialog() {
        CancelReceiptBottomSheetDialog.dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserName = getArguments().getString("userName");
        UserImage = getArguments().getString("userImage");
        BookingID = getArguments().getString("bookingID");
        WorkerID = getArguments().getString("workerID");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext = context;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CancelReceiptBottomSheetDialog = super.onCreateDialog(savedInstanceState);
        isDialogOpen = true;
        return (Dialog) CancelReceiptBottomSheetDialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rating_review_bottom_sheet_dialog, container, false);
        userSessionManager = new UserSessionManager(getActivity());
        progressDialogHandler = new ProgressDialogHandler(getActivity());
        atClass = new AtClass();
        isDialogOpen = true;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        civUserImageDrawable = v.findViewById(R.id.civUserImageDrawable);
        tvUserName = v.findViewById(R.id.tvUserName);
        etReviewMessage = v.findViewById(R.id.etReviewMessage);
        ratingBar = v.findViewById(R.id.ratingBar);
        btnSubmit = v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        iVBack = v.findViewById(R.id.iVBack);
        iVBack.setOnClickListener(this);

        setUserData();
        return v;
    }

    private void setUserData() {
        tvUserName.setText(UserName);
        Log.d("User Image", "Val " + UserImage);
        Glide.with(getActivity()).load(UserImage).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.ic_avatar).into(civUserImageDrawable);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressDialogHandler != null) {
            progressDialogHandler.showPopupProgressSpinner(false);
        }
        isDialogOpen = false;
        Log.d("View is Closed", "Yes");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iVBack:
                CloseDialog();
                isDialogOpen = false;
                break;

            case R.id.btnSubmit:
                if (atClass.isNetworkAvailable(getActivity())) {
                    if (checkReviewMessage() && checkRating()) {
                        SubmitReview();
                    }
                } else {
                    Toast.makeText(mcontext, getString(R.string.rate_no_internet_toast), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private Boolean checkRating(){
        boolean isRatingValid = false;
        double Rating = ratingBar.getRating();
        if (Rating == 0.0F) {
            isRatingValid = false;
            Toast.makeText(getActivity(), getString(R.string.rate_ratings_validation_error_text), Toast.LENGTH_SHORT).show();
        } else {
            isRatingValid = true;
        }
        return isRatingValid;
    }


    private Boolean checkReviewMessage(){
        boolean isReviewMessageValid = false;
        if (etReviewMessage.getText().toString().trim() ==""){
            etReviewMessage.setError(getString(R.string.rate_ratings_empty_validation_error_text));
            isReviewMessageValid = false;
        } else{
            etReviewMessage.setError(null);
            isReviewMessageValid = true;
        }
        return isReviewMessageValid;
    }


    private void SubmitReview() {
        progressDialogHandler.showPopupProgressSpinner(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.GIVE_FEEDBACK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogHandler.showPopupProgressSpinner(false);
                if (error instanceof NetworkError){
                    Toast.makeText(mcontext, mcontext.getString(R.string.cancel_service_network_error_text), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError){
                    Toast.makeText(mcontext, mcontext.getString(R.string.cancel_service_no_connection_error_text), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError){
                    Toast.makeText(mcontext, mcontext.getString(R.string.cancel_service_server_error_text), Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError){
                    Toast.makeText(mcontext, mcontext.getString(R.string.cancel_service_connection_timeout_error_text), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(mcontext, mcontext.getString(R.string.cancel_service_error_text), Toast.LENGTH_SHORT).show();
                }
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
                params.put(JsonFields.FEEDBACK_REQUEST_BOOKING_ID, BookingID);
                params.put(JsonFields.FEEDBACK_REQUEST_WORKER_ID, WorkerID);
                params.put(JsonFields.FEEDBACK_REQUEST_FEEDBACK_MESSAGE, etReviewMessage.getText().toString().trim());
                params.put(JsonFields.FEEDBACK_REQUEST_FEEDBACK_RATING, String.valueOf(ratingBar.getRating()));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_ID, atClass.getDeviceID(mcontext));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TYPE, atClass.getDeviceType());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TOKEN, atClass.getRefreshedToken());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS, atClass.getOsInfo());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS, atClass.getRefreshedIpAddress(mcontext));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_MODEL_DETAILS, atClass.getDeviceManufacturerModel());
                params.put(JsonFields.COMMON_REQUEST_PARAM_APP_VERSION_DETAILS, atClass.getAppVersionNumberAndVersionCode());
                Log.d("params", String.valueOf(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);
    }


    private void parseJSON(String response) {
        Log.d("RESPONSE CANCEL RECEIPT", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonFields.FLAG);
            String Message = jsonObject.optString(JsonFields.MESSAGE);
            if (flag == 1) {
                CloseDialog();
                progressDialogHandler.showPopupProgressSpinner(false);
                Intent i = new Intent(mcontext, SuccessMessageActivity.class);
                i.putExtra("Message", Message);
                i.putExtra("Title", getString(R.string.rate_intent_title_for_booking));
                i.putExtra("FromScreenName", "RatingReviewBottomSheetDialogFragment");
                i.putExtra("ToScreenName", "BookingsActivity");
                startActivity(i);
                getActivity().finish();
            } else if (flag == 3) {
                //User Is Deactivated By Admin
            } else {
                progressDialogHandler.showPopupProgressSpinner(false);
                Toast.makeText(mcontext, Message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


}