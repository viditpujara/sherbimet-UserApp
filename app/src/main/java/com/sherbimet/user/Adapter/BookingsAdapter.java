package com.sherbimet.user.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.textfield.TextInputEditText;
import com.sherbimet.user.APIHelper.JsonFields;
import com.sherbimet.user.APIHelper.WebAuthorization;
import com.sherbimet.user.APIHelper.WebURL;
import com.sherbimet.user.Activity.BookingsActivity;
import com.sherbimet.user.Activity.SubServiceActivity;
import com.sherbimet.user.Activity.SuccessMessageActivity;
import com.sherbimet.user.Fragment.RatingReviewBottomSheetDialog;
import com.sherbimet.user.Model.Bookings;
import com.sherbimet.user.Model.Services;
import com.sherbimet.user.R;
import com.sherbimet.user.Utils.AtClass;
import com.sherbimet.user.Utils.ProgressDialogHandler;
import com.sherbimet.user.Utils.UserConfirmationAlertDialog;
import com.sherbimet.user.Utils.UserSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    private Context mContext;
    ArrayList<Bookings> listBookings;
    AtClass atClass;
    UserSessionManager userSessionManager;
    ProgressDialogHandler progressDialogHandler;

    public BookingsAdapter(ArrayList<Bookings> listBookings) {
        this.listBookings = listBookings;
    }

    @Override
    public BookingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.bookings_list_row_item_layout, parent, false);
        atClass = new AtClass();
        userSessionManager = new UserSessionManager(mContext);
        progressDialogHandler = new ProgressDialogHandler(mContext);
        return new BookingsAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final BookingsAdapter.ViewHolder holder, final int position) {
        final Bookings bookings = listBookings.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvBookingID.setText(Html.fromHtml(bookings.getBookingID(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvBookingID.setText(Html.fromHtml(bookings.getBookingID()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvBookingDateTime.setText(Html.fromHtml(bookings.getBookingDateTime(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvBookingDateTime.setText(Html.fromHtml(bookings.getBookingDateTime()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvBookingAddress.setText(Html.fromHtml(bookings.getBookingAddress(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvBookingAddress.setText(Html.fromHtml(bookings.getBookingAddress()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvBookingMessage.setText(Html.fromHtml(bookings.getBookingMessage(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvBookingMessage.setText(Html.fromHtml(bookings.getBookingMessage()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvBookingAmount.setText(Html.fromHtml(bookings.getBookingTotalAmount(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvBookingAmount.setText(Html.fromHtml(bookings.getBookingTotalAmount()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvPaymentMethod.setText(Html.fromHtml(bookings.getPaymentMethod(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvPaymentMethod.setText(Html.fromHtml(bookings.getPaymentMethod()));
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvWorkerName.setText(Html.fromHtml(bookings.getWorkerName(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvWorkerName.setText(Html.fromHtml(bookings.getWorkerName()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvWorkerGender.setText(Html.fromHtml(bookings.getWorkerGender(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvWorkerGender.setText(Html.fromHtml(bookings.getWorkerGender()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvWorkerMobile.setText(Html.fromHtml(bookings.getWorkerMobile(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvWorkerMobile.setText(Html.fromHtml(bookings.getWorkerMobile()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvWorkerEmail.setText(Html.fromHtml(bookings.getWorkerEmail(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvWorkerEmail.setText(Html.fromHtml(bookings.getWorkerEmail()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvPackageName.setText(Html.fromHtml(bookings.getPackageName(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvPackageName.setText(Html.fromHtml(bookings.getPackageName()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvBookingStatus.setText(Html.fromHtml(bookings.getBookingStatus(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvBookingStatus.setText(Html.fromHtml(bookings.getBookingStatus()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvRatings.setText(Html.fromHtml(bookings.getFeedbackRatings(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvRatings.setText(Html.fromHtml(bookings.getFeedbackRatings()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvReviewMessage.setText(Html.fromHtml(bookings.getFeedbackMessage(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvReviewMessage.setText(Html.fromHtml(bookings.getFeedbackMessage()));
        }

        Glide.with(mContext).load(bookings.getWorkerImage()).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).error(R.drawable.app_icon_transparent)
                .into(holder.ciVWorkerImage);

        if (bookings.getBookingStatusID().equals("1") || bookings.getBookingStatusID().equals("5")) {
            holder.llWorkerDetails.setVisibility(View.GONE);
        } else {
            holder.llWorkerDetails.setVisibility(View.VISIBLE);
        }


        ;

        if (bookings.getBookingStatusID().equals("4")) {
            if (bookings.getCanFeedback().equals("0")) {
                holder.llReview.setVisibility(View.GONE);
                holder.btnRateService.setVisibility(View.VISIBLE);
            } else {
                holder.llReview.setVisibility(View.VISIBLE);
                holder.btnRateService.setVisibility(View.GONE);
            }
        } else {
            holder.llReview.setVisibility(View.GONE);
            holder.btnRateService.setVisibility(View.GONE);
        }

        if (bookings.getBookingStatusID().equals("5")) {
            holder.llCancelReason.setVisibility(View.VISIBLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.tvCancelReason.setText(Html.fromHtml(bookings.getCancelReason(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.tvCancelReason.setText(Html.fromHtml(bookings.getCancelReason()));
            }
        } else {
            holder.llCancelReason.setVisibility(View.GONE);
        }

        if (bookings.getCanCancel() != null && !bookings.getCanCancel().isEmpty() && !bookings.getCanCancel().equals("")) {
            if (bookings.getCanCancel().equals("1")) {
                holder.btnCancelBookingRequest.setVisibility(View.VISIBLE);
            } else {
                holder.btnCancelBookingRequest.setVisibility(View.GONE);
            }
        } else {
            holder.btnCancelBookingRequest.setVisibility(View.GONE);
        }

        holder.btnCancelBookingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atClass.isNetworkAvailable(mContext)) {
                    ShowCancelConfirmationDialog(bookings.getBookingID());
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.booking_request_list_no_internet_toast_text), Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.btnRateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (atClass.isNetworkAvailable(mContext)) {
                    ShowRatingBottomSheetDialog(bookings.getBookingID(), bookings.getWorkerID(), bookings.getUserName(), bookings.getUserImage());
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.booking_request_list_no_internet_toast_text), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*val i = Intent(mContext, WorkerActivity::class.java)
            i.putExtra("SubCategoryID", subCategory.SubCategoryID)
            i.putExtra("SubCategoryName", subCategory.SubCategoryName)
            mContext!!.startActivity(i)*/
            }
        });
    }


    private void ShowRatingBottomSheetDialog(String bookingID,String workerID,String userName,String userImage) {
        AppCompatActivity activity  = (BookingsActivity)mContext;
        Bundle bundle = new Bundle();
        bundle.putString("bookingID", bookingID);
        bundle.putString("userName", userName);
        bundle.putString("userImage", userImage);
        bundle.putString("workerID", workerID);

        Log.d("Worker ID",workerID);

        RatingReviewBottomSheetDialog bottomSheetFragment = new RatingReviewBottomSheetDialog();
        bottomSheetFragment.setArguments(bundle);
        FragmentTransaction fragmentTransactionrequestlead = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransactionrequestlead.commitAllowingStateLoss();
        bottomSheetFragment.show(activity.getSupportFragmentManager(), bottomSheetFragment.getTag());
    }


    private void ShowCancelConfirmationDialog(String bookingID) {
        UserConfirmationAlertDialog userConfirmationAlertDialog = new UserConfirmationAlertDialog(mContext);
        userConfirmationAlertDialog.setTitle(mContext.getString(R.string.booking_request_cancel_service_title_text));
        //userConfirmationAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        userConfirmationAlertDialog.setMessage(mContext.getString(R.string.booking_request_cancel_service_message_text));

        userConfirmationAlertDialog.setPositiveButton(mContext.getString(R.string.booking_request_cancel_service_yes_button_text), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConfirmationAlertDialog.dismiss();
                //Do want you want
                ShowCancelationReasonDialog(bookingID);
            }
        });

        userConfirmationAlertDialog.setNegativeButton(mContext.getString(R.string.booking_request_cancel_service_no_button_text), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConfirmationAlertDialog.dismiss();
                //Do want you want
            }
        });

        userConfirmationAlertDialog.setCancelable(false);
        userConfirmationAlertDialog.setCanceledOnTouchOutside(false);
        userConfirmationAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        userConfirmationAlertDialog.show();
        userConfirmationAlertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void ShowCancelationReasonDialog(String bookingID) {
        TextInputEditText etCancellationReason;
        Button btnSubmit;

        Dialog cancelOrderReasonDialog = new Dialog(mContext);
        cancelOrderReasonDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cancelOrderReasonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelOrderReasonDialog.setCancelable(false);
        cancelOrderReasonDialog.setCanceledOnTouchOutside(false);
        cancelOrderReasonDialog.setContentView(R.layout.cancealltion_reason_pop_up_layout);
        cancelOrderReasonDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        ImageView ivClose = cancelOrderReasonDialog.findViewById(R.id.ivClose);
        etCancellationReason = cancelOrderReasonDialog.findViewById(R.id.etCancellationReason);
        btnSubmit = cancelOrderReasonDialog.findViewById(R.id.btnSubmit);

        ivClose.setEnabled(true);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrderReasonDialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrderReasonDialog.dismiss();
                if (etCancellationReason.getText().toString().trim() != null && !etCancellationReason.getText().toString().trim().isEmpty() && !etCancellationReason.getText().toString().trim().equals("")) {
                    cancelBookingRequest(bookingID, etCancellationReason.getText().toString().trim());
                } else {
                    etCancellationReason.setError(mContext.getString(R.string.booking_request_cancel_dialog_cancel_reason_validation_error_text));
                }
                etCancellationReason.setText("");
                cancelOrderReasonDialog.dismiss();
            }
        });
        cancelOrderReasonDialog.show();
    }

    private void cancelBookingRequest(String bookingID,String cancelReason) {
        progressDialogHandler.showPopupProgressSpinner(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.CANCEL_BOOKING_REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogHandler.showPopupProgressSpinner(false);
                if (error instanceof NetworkError){
                    Toast.makeText(mContext, mContext.getString(R.string.cancel_service_network_error_text), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError){
                    Toast.makeText(mContext, mContext.getString(R.string.cancel_service_no_connection_error_text), Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError){
                    Toast.makeText(mContext, mContext.getString(R.string.cancel_service_server_error_text), Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError){
                    Toast.makeText(mContext, mContext.getString(R.string.cancel_service_connection_timeout_error_text), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(mContext, mContext.getString(R.string.cancel_service_error_text), Toast.LENGTH_SHORT).show();
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
                params.put(JsonFields.CANCEL_BOOKING_REQUESTS_REQUEST_CANCEL_REASON, cancelReason);
                params.put(JsonFields.CANCEL_BOOKING_REQUESTS_REQUEST_BOOKING_ID, bookingID);
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_ID, atClass.getDeviceID(mContext));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TYPE, atClass.getDeviceType());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TOKEN, atClass.getRefreshedToken());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS, atClass.getOsInfo());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS, atClass.getRefreshedIpAddress(mContext));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_MODEL_DETAILS, atClass.getDeviceManufacturerModel());
                params.put(JsonFields.COMMON_REQUEST_PARAM_APP_VERSION_DETAILS, atClass.getAppVersionNumberAndVersionCode());
                Log.d("params", String.valueOf(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    private void parseJSON(String response) {
        Log.d("RESPONSE", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonFields.FLAG);
            String Message = jsonObject.optString(JsonFields.MESSAGE);

            if (flag == 1) {
                progressDialogHandler.showPopupProgressSpinner(false);
                Intent i = new Intent(mContext, SuccessMessageActivity.class);
                i.putExtra("Message", Message);
                i.putExtra("FromScreenName", "BookingsAdapter");
                i.putExtra("ToScreenName", "BookingsActivity");
                mContext.startActivity(i);
                ((BookingsActivity)mContext).finish();
            } else if (flag == 3) {
                /*progressDialogHandler.showPopupProgressSpinner(false)
                dealerSessionManager.logout()
                val LogoutTitle = jsonObject.optString(JsonFields.COMMON_LOGOUT_RESPONSE_TITLE)
                val LogoutMessage = jsonObject.optString(JsonFields.COMMON_LOGOUT_RESPONSE_MESSAGE)
                val LogoutIcon = jsonObject.optString(JsonFields.COMMON_LOGOUT_RESPONSE_ICON)

                val i = Intent(mContext, LogoutMessageActivity::class.java)
                i.putExtra("Title", LogoutTitle)
                i.putExtra("Message", LogoutMessage)
                i.putExtra("ImageURL", LogoutIcon)
                i.flags =
                    i.FLAG_ACTIVITY_CLEAR_TOP or i.FLAG_ACTIVITY_CLEAR_TASK or i.FLAG_ACTIVITY_NEW_TASK
                mContext.startActivity(i)
                (mContext as OrderStatusWiseOrdersActivity).finish()*/

            } else {
                progressDialogHandler.showPopupProgressSpinner(false);
                Toast.makeText(mContext, Message, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return listBookings.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName,tvPaymentMethod;
        ImageView iVServiceIcon;

        CardView cVBookings;
        CircleImageView ciVWorkerImage;
        TextView tvBookingID, tvBookingDateTime, tvBookingAddress, tvBookingMessage, tvBookingAmount, tvWorkerName, tvWorkerGender, tvWorkerMobile, tvWorkerEmail, tvBookingStatus, tvPackageName;

        LinearLayout llWorkerDetails;

        Button btnCancelBookingRequest;

        LinearLayout llCancelReason;
        TextView tvCancelReason, tvRatings, tvReviewMessage;

        LinearLayout llReview;
        Button btnRateService;


        public ViewHolder(View itemView) {
            super(itemView);

            cVBookings = itemView.findViewById(R.id.cVBookings);
            ciVWorkerImage = itemView.findViewById(R.id.ciVWorkerImage);
            tvBookingID = itemView.findViewById(R.id.tvBookingID);
            tvBookingDateTime = itemView.findViewById(R.id.tvBookingDateTime);
            tvBookingAddress = itemView.findViewById(R.id.tvBookingAddress);
            tvBookingMessage = itemView.findViewById(R.id.tvBookingMessage);
            tvBookingAmount = itemView.findViewById(R.id.tvBookingAmount);
            tvWorkerName = itemView.findViewById(R.id.tvWorkerName);
            tvWorkerGender = itemView.findViewById(R.id.tvWorkerGender);
            tvWorkerMobile = itemView.findViewById(R.id.tvWorkerMobile);
            tvWorkerEmail = itemView.findViewById(R.id.tvWorkerEmail);

            tvPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);

            tvBookingStatus = itemView.findViewById(R.id.tvBookingStatus);
            tvPackageName = itemView.findViewById(R.id.tvPackageName);

            llWorkerDetails = itemView.findViewById(R.id.llWorkerDetails);

            btnCancelBookingRequest = itemView.findViewById(R.id.btnCancelBookingRequest);

            llCancelReason = itemView.findViewById(R.id.llCancelReason);
            tvCancelReason = itemView.findViewById(R.id.tvCancelReason);

            tvRatings = itemView.findViewById(R.id.tvRatings);
            tvReviewMessage = itemView.findViewById(R.id.tvReviewMessage);
            llReview = itemView.findViewById(R.id.llReview);
            btnRateService = itemView.findViewById(R.id.btnRateService);
        }
    }
}

