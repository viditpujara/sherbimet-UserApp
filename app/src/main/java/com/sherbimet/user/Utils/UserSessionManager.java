package com.sherbimet.user.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
import com.sherbimet.user.APIHelper.JsonFields;
import com.sherbimet.user.APIHelper.WebAuthorization;
import com.sherbimet.user.APIHelper.WebURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserSessionManager {
    private static final String USER_PREF = "user_pref";
    private static final String KEY_IS_LOGIN = "IS_LOGIN";
    private final SharedPreferences.Editor editor;
    Context mContext;
    SharedPreferences preferences;

    public static final String USER_ID ="user_id";
    public static final String USER_NAME ="user_name";
    public static final String USER_MOBILE ="user_mobile";
    public static final String USER_IMAGE_URL ="user_image_url";
    public static final String USER_PROFILE_IMAGE_FILE_PATH ="user_image_path";

    AtClass atClass;

    public UserSessionManager(Context mContext) {
        this.mContext = mContext;
        atClass = new AtClass();
        preferences = mContext.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setLoginStatus() {
        editor.putBoolean(KEY_IS_LOGIN, true).commit();
    }

    public boolean getLoginStatus() {
        return preferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public void logout() {
        LogoutUser();
    }

    public void setUserDetails(String user_id,String user_name,String user_mobile,String user_image_url) {
        editor.putString(USER_ID, user_id);
        editor.putString(USER_NAME, user_name);
        editor.putString(USER_MOBILE, user_mobile);
        editor.putString(USER_IMAGE_URL, user_image_url);
        editor.apply();
    }



    public void setPhotoURI(String userImagePath) {
        editor.putString(USER_PROFILE_IMAGE_FILE_PATH, userImagePath).apply();
    }

    public String getPhotoURI() {
        return preferences.getString(USER_PROFILE_IMAGE_FILE_PATH, "");
    }

    public String getUserID() {
        return preferences.getString(USER_ID, "");
    }
    public String getUserName() {
        return preferences.getString(USER_NAME, "");
    }
    public String getUserMobile() {
        return preferences.getString(USER_MOBILE, "");
    }
    public String getUserImageURL() { return preferences.getString(USER_IMAGE_URL, ""); }

    private void LogoutUser() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.LOGOUT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseLogoutJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    RemoveSession();
                } else if (error instanceof NoConnectionError) {
                    RemoveSession();
                } else if (error instanceof ServerError) {
                    RemoveSession();
                } else if (error instanceof TimeoutError) {
                    RemoveSession();
                } else {
                    RemoveSession();
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
                params.put(JsonFields.COMMON_REQUEST_PARAMS_USER_ID, getUserID());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_ID, atClass.getDeviceID(mContext));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TYPE, atClass.getDeviceType());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TOKEN, atClass.getRefreshedToken());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS, atClass.getOsInfo());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS, atClass.getRefreshedIpAddress(mContext));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_MODEL_DETAILS, atClass.getDeviceManufacturerModel());
                params.put(JsonFields.COMMON_REQUEST_PARAM_APP_VERSION_DETAILS, atClass.getAppVersionNumberAndVersionCode());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    private void parseLogoutJSON(String response) {
        Log.d("RESPONSE", response.toString());
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonFields.FLAG);
            String Message = jsonObject.optString(JsonFields.MESSAGE);

            if (flag == 1) {
                RemoveSession();
            } else {
                RemoveSession();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void RemoveSession() {
        editor.remove(USER_ID);
        editor.remove(USER_NAME);
        editor.remove(USER_MOBILE);
        editor.remove(USER_IMAGE_URL);
        editor.remove(USER_PROFILE_IMAGE_FILE_PATH);
        editor.remove(KEY_IS_LOGIN);
        editor.commit();
    }



}