package com.sherbimet.user.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.google.android.material.navigation.NavigationView;
import com.sherbimet.user.APIHelper.JsonFields;
import com.sherbimet.user.APIHelper.WebAuthorization;
import com.sherbimet.user.APIHelper.WebURL;
import com.sherbimet.user.Adapter.AreaAdapter;
import com.sherbimet.user.Adapter.ServicesAdapter;
import com.sherbimet.user.Model.Area;
import com.sherbimet.user.Model.Services;
import com.sherbimet.user.R;
import com.sherbimet.user.Utils.AtClass;
import com.sherbimet.user.Utils.BaseActivity;
import com.sherbimet.user.Utils.DashboardAreaManager;
import com.sherbimet.user.Utils.ItemOffsetDecoration;
import com.sherbimet.user.Utils.ProgressBarHandler;
import com.sherbimet.user.Utils.ProgressDialogHandler;
import com.sherbimet.user.Utils.UserConfirmationAlertDialog;
import com.sherbimet.user.Utils.UserSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    UserSessionManager userSessionManager;
    AtClass atClass;

    LinearLayout llHomeMaster, llHome, llCategory, llError, llNoInternetConnection;

    CircleImageView civUserImage;
    TextView tvUserName, tvUserRoleGroupName;
    ProgressBarHandler progressBarHandler;

    public static ProgressBarHandler staticProgressBarHandler;
    public static AtClass staticAtClass;

    RecyclerView rVCategory;

    TextView tvGreeting, tvSelectedArea;
    CardView cVSelectedArea;
    ProgressDialogHandler progressDialogHandler1, progressDialogHandler2;

    TextView tvMessage;
    Button btnRetry;

    String AreaID, AreaName;

    ArrayList<Services> listServices = new ArrayList<Services>();
    ServicesAdapter servicesAdapter = new ServicesAdapter(listServices);

    ImageView iVLogout;
    DashboardAreaManager dashboardAreaManager;

    NavigationView navigationView;

    CircleImageView ciVHeaderUserImage;
    TextView tvHeaderUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dashboardAreaManager = new DashboardAreaManager(this);
        staticAtClass = new AtClass();

        iVLogout = findViewById(R.id.iVLogout);
        iVLogout.setOnClickListener(this);

        tvGreeting = findViewById(R.id.tvGreeting);
        tvMessage = findViewById(R.id.tvMessage);
        btnRetry = findViewById(R.id.btnRetry);
        btnRetry.setOnClickListener(this);

        progressDialogHandler1 = new ProgressDialogHandler(this);
        progressDialogHandler2 = new ProgressDialogHandler(this);

        llHomeMaster = findViewById(R.id.llHomeMaster);
        llHome = findViewById(R.id.llHome);
        llCategory = findViewById(R.id.llCategory);
        llError = findViewById(R.id.llError);
        llNoInternetConnection = findViewById(R.id.llNoInternetConnection);

        atClass = new AtClass();
        userSessionManager = new UserSessionManager(this);
        progressBarHandler = new ProgressBarHandler(this);
        staticProgressBarHandler = new ProgressBarHandler(this);

        civUserImage = findViewById(R.id.civUserImage);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserRoleGroupName = findViewById(R.id.tvUserRoleGroupName);
        tvSelectedArea = findViewById(R.id.tvSelectedArea);
        cVSelectedArea = findViewById(R.id.cVSelectedArea);

        cVSelectedArea.setOnClickListener(this);

        rVCategory = findViewById(R.id.rVCategory);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset_medium);
        rVCategory.addItemDecoration(itemDecoration);
        rVCategory.setLayoutManager(new GridLayoutManager(this, 3));
        rVCategory.setAdapter(servicesAdapter);

        Log.d("User ID", "Value " + userSessionManager.getUserID());


        Toolbar toolbar = findViewById(R.id.tl_toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        final Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, null);

        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View mView = navigationView.getHeaderView(0);
        ciVHeaderUserImage = mView.findViewById(R.id.ciVHeaderUserImage);
        tvHeaderUserName = mView.findViewById(R.id.tvHeaderUserName);

        iVLogout = findViewById(R.id.iVLogout);
        iVLogout.setOnClickListener(this::onClick);


        if (atClass.isNetworkAvailable(this)) {
            getDashboardData();
        } else {
            llHomeMaster.setVisibility(View.GONE);
            llNoInternetConnection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_service_bookings:
                 /*Intent i1 = new Intent(this, BookingsActivity.class);
                 i1.putExtra("isBooking", "1");
                 i1.putExtra("Title", getString(R.string.home_intent_title_for_booking));
                 i1.setFlags(i1.FLAG_ACTIVITY_CLEAR_TASK|i1.FLAG_ACTIVITY_CLEAR_TOP|i1.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(i1);
                 finish();*/
                break;

            case R.id.nav_service_requests:
                 Intent i2 = new Intent(this, BookingsActivity.class);
                 i2.setFlags(i2.FLAG_ACTIVITY_CLEAR_TASK|i2.FLAG_ACTIVITY_CLEAR_TOP|i2.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(i2);
                 finish();
                break;

            case R.id.nav_profile:
                 Intent i3 = new Intent(this, ProfileActivity.class);
                 startActivity(i3);
                 finish();
                break;

            case R.id.nav_about:
                 Intent i4 = new Intent(this, AboutUsActivity.class);
                 startActivity(i4);
                break;

            case R.id.nav_logout:
                if (atClass.isNetworkAvailable(this)) {
                    ShowLogoutDealerConfirmationAlert();
                } else {
                    Toast.makeText(this, getString(R.string.home_no_internet_connection_toast), Toast.LENGTH_SHORT).show();
                }
                break;


        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getDashboardData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.DASHBOARD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseDashboardJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarHandler.hide();
                llNoInternetConnection.setVisibility(View.VISIBLE);
                llError.setVisibility(View.GONE);
                llHomeMaster.setVisibility(View.GONE);
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
                params.put(JsonFields.DASHBOARD_REQUEST_PARAMS_AREA_ID, dashboardAreaManager.getAreaID());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_ID, atClass.getDeviceID(HomeActivity.this));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TYPE, atClass.getDeviceType());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TOKEN, atClass.getRefreshedToken());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS, atClass.getOsInfo());
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS, atClass.getRefreshedIpAddress(HomeActivity.this));
                params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_MODEL_DETAILS, atClass.getDeviceManufacturerModel());
                params.put(JsonFields.COMMON_REQUEST_PARAM_APP_VERSION_DETAILS, atClass.getAppVersionNumberAndVersionCode());
                Log.d("params", String.valueOf(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void parseDashboardJSON(String response) {
        Log.d("RESPONSE", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            int flag = jsonObject.optInt(JsonFields.FLAG);
            String Message = jsonObject.optString(JsonFields.MESSAGE);
            if (flag == 1) {
                progressDialogHandler1.showPopupProgressSpinner(false);

                String UserName = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_NAME);
                String UserImageURL = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_IMAGE_URL);
                String Greeting = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_GREETING);
                AreaID = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_SELECTED_AREA_ID);
                AreaName = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_SELECTED_AREA_NAME);

                dashboardAreaManager.setAreaID(AreaID);
                dashboardAreaManager.setAreaName(AreaName);

                JSONArray arrService = jsonObject.optJSONArray(JsonFields.DASHBOARD_RESPONSE_USER_SELECTED_SERVICE_ARRAY);
                if (arrService.length() > 0) {
                    for (int i = 0; i < arrService.length(); i++) {
                        JSONObject serviceObject = arrService.optJSONObject(i);
                        String ServiceID = serviceObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_SELECTED_SERVICE_ID);
                        String ServiceName = serviceObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_SELECTED_SERVICE_NAME);
                        String ServiceImage = serviceObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_SELECTED_SERVICE_IMAGE);

                        listServices.add(new Services(ServiceID, ServiceName, ServiceImage));
                    }
                    servicesAdapter.notifyDataSetChanged();

                    llHomeMaster.setVisibility(View.VISIBLE);
                    llNoInternetConnection.setVisibility(View.GONE);

                    llHome.setVisibility(View.VISIBLE);
                    llCategory.setVisibility(View.VISIBLE);
                    llError.setVisibility(View.GONE);

                    setDashboardData(UserName, UserImageURL, Greeting);
                }
            } else if (flag == 3) {

            } else if (flag == 4) {
                progressDialogHandler1.showPopupProgressSpinner(false);

                llHomeMaster.setVisibility(View.VISIBLE);
                llNoInternetConnection.setVisibility(View.GONE);

                llHome.setVisibility(View.VISIBLE);
                llError.setVisibility(View.VISIBLE);
                llCategory.setVisibility(View.GONE);

                tvMessage.setText(Message);

                String UserName = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_NAME);
                String UserImageURL = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_IMAGE_URL);
                String Greeting = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_GREETING);
                AreaID = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_SELECTED_AREA_ID);
                AreaName = jsonObject.optString(JsonFields.DASHBOARD_RESPONSE_USER_SELECTED_AREA_NAME);

                dashboardAreaManager.setAreaID(AreaID);
                dashboardAreaManager.setAreaName(AreaName);
                setDashboardData(UserName, UserImageURL, Greeting);

            } else {
                progressDialogHandler1.showPopupProgressSpinner(false);

                llHomeMaster.setVisibility(View.VISIBLE);
                llNoInternetConnection.setVisibility(View.GONE);

                llHome.setVisibility(View.GONE);
                llCategory.setVisibility(View.GONE);
                llError.setVisibility(View.VISIBLE);

                tvMessage.setText(Message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDashboardData(String UserName, String UserImageURL, String Greeting) {
        Glide.with(this).load(UserImageURL).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.ic_avatar).into(civUserImage);
        Glide.with(this).load(UserImageURL).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.drawable.ic_avatar).into(ciVHeaderUserImage);


        if (UserName != null && !UserName.isEmpty() && UserName != "") {
            setDataToTextView(tvUserName.getId(), UserName);
        } else {
        }

        if (UserName != null && !UserName.isEmpty() && UserName != "") {
            setDataToTextView(tvHeaderUserName.getId(), UserName);
        } else {
        }

        if (Greeting != null && !Greeting.isEmpty() && Greeting != "") {
            setDataToTextView(tvGreeting.getId(), Greeting);
        } else {
        }

        if (AreaName != null && !AreaName.isEmpty() && AreaName != "") {
            setDataToTextView(tvSelectedArea.getId(), AreaName);
        } else {
        }
    }

    private void setDataToTextView(int TextViewID, String Text) {
        TextView textView = findViewById(TextViewID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(Text, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml(Text));
        }
    }

    private void ShowLogoutDealerConfirmationAlert() {
        UserConfirmationAlertDialog userConfirmationAlertDialog = new UserConfirmationAlertDialog(this);
        userConfirmationAlertDialog.setTitle(getString(R.string.home_logout_title));
        //userConfirmationAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        userConfirmationAlertDialog.setMessage(getString(R.string.home_logout_description));
        userConfirmationAlertDialog.setPositiveButton(getString(R.string.home_logout_yes_button), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userConfirmationAlertDialog.dismiss();
                //Do want you want
                //LocaleManager.setNewLocale(this, "en")
                userSessionManager.logout();
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });


        userConfirmationAlertDialog.setNegativeButton(getString(R.string.home_logout_no_button), new View.OnClickListener() {
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

    private void ShowAreaSelectionBottomDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DialogFragment newFragment = new AllSelectedCity();
        newFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme);
        newFragment.show(ft, "dialog");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRetry:
                if (atClass.isNetworkAvailable(this)) {
                    Intent i = new Intent(this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.home_no_internet_connection_toast), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.cVSelectedArea:
                if (atClass.isNetworkAvailable(this)) {
                    ShowAreaSelectionBottomDialog();
                } else {
                    Toast.makeText(this, getString(R.string.home_no_internet_connection_toast), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.iVLogout:
                if (atClass.isNetworkAvailable(this)) {
                    ShowLogoutDealerConfirmationAlert();
                } else {
                    Toast.makeText(this, getString(R.string.home_no_internet_connection_toast), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public static class AllSelectedCity extends DialogFragment implements AreaAdapter.ClickListener, View.OnClickListener {
        RecyclerView rVArea;
        TextView tvNoAreaFound;
        RelativeLayout iVClose;
        AreaAdapter areaadapter;
        DashboardAreaManager dashboardAreaManager;
        private SearchView searchViewcity;
        ArrayList<Area> arealist;
        ArrayList<Area> temparealist;
        ProgressBarHandler progressBarHandler;
        AtClass atClass;

        String Message;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.area_fragment, container, false);
            rVArea = v.findViewById(R.id.rVArea);
            dashboardAreaManager = new DashboardAreaManager(getActivity());
            atClass = new AtClass();
            progressBarHandler = new ProgressBarHandler(getActivity());
            tvNoAreaFound = v.findViewById(R.id.tvNoAreaFound);
            iVClose = v.findViewById(R.id.iVClose);
            // searchViewcity=(SearchView) v.findViewById(R.id.searchViewcity);

            iVClose.setOnClickListener(this);
            loadfilter();
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            DisplayMetrics metrics = new DisplayMetrics();
            //getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            getDialog().getWindow().setGravity(Gravity.BOTTOM);
            //   getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (metrics.heightPixels * 0));// here i have fragment height 30% of window's height you can set it as per your requirement
            //     getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimationUpDown;
        }

        @Override
        public void onClick(View v) {
            if (v == iVClose) {
                getDialog().dismiss();
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            String id = arealist.get(position).getAreaID();
            String name = arealist.get(position).getAreaName();
            //Log.e(TAG, "itemClicked: One " + id);
            getDialog().dismiss();

            dashboardAreaManager.setAreaID(id);
            dashboardAreaManager.setAreaName(name);

            Intent i = new Intent(getActivity(), HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            getActivity().finish();
        }

        public void loadfilter() {
            progressBarHandler.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, WebURL.AREA_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBarHandler.hide();
                    parseAreaJSON(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBarHandler.hide();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return WebAuthorization.checkAuthentication();
                }


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TYPE, atClass.getDeviceType());
                    params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_ID, atClass.getDeviceID(getActivity()));
                    params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_TOKEN, atClass.getRefreshedToken());
                    params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS, atClass.getOsInfo());
                    params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS, atClass.getRefreshedIpAddress(getActivity()));
                    params.put(JsonFields.COMMON_REQUEST_PARAM_DEVICE_MODEL_DETAILS, atClass.getDeviceManufacturerModel());
                    params.put(JsonFields.COMMON_REQUEST_PARAM_APP_VERSION_DETAILS, atClass.getAppVersionNumberAndVersionCode());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }

        private void parseAreaJSON(String response) {
            temparealist = new ArrayList<Area>();
            Log.d("RESPONSE", response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has(JsonFields.FLAG)) {
                    int flag = jsonObject.optInt(JsonFields.FLAG);
                    Message = jsonObject.optString(JsonFields.MESSAGE);

                    if (flag == 1) {
                        JSONArray arrArea = jsonObject.optJSONArray(JsonFields.AREA_RESPONSE_USER_SELECTED_AREA_ARRAY);
                        if (arrArea.length() > 0) {
                            for (int i = 0; i < arrArea.length(); i++) {
                                JSONObject areaObject = arrArea.optJSONObject(i);
                                String AreaName = areaObject.optString(JsonFields.AREA_RESPONSE_USER_SELECTED_ARAE_NAME);
                                String AreaID = areaObject.optString(JsonFields.AREA_RESPONSE_USER_SELECTED_AREA_ID);
                                String AreaNameInitials = areaObject.optString(JsonFields.AREA_RESPONSE_USER_SELECTED_AREA_NAME_INITIALS);

                                temparealist.add(new Area(AreaID, AreaName, AreaNameInitials));
                            }
                            updatefilter();
                        }
                    } else {
                        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                        hidefilter();
                    }
                } else {
                    Message = getString(R.string.home_something_went_wrong_toast);
                    Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
                    hidefilter();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void updatefilter() {
            arealist = new ArrayList<Area>();
            arealist = temparealist;
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset_medium);
            rVArea.addItemDecoration(itemDecoration);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            rVArea.setLayoutManager(layoutManager);
            areaadapter = new AreaAdapter(getActivity(), arealist);
            rVArea.setAdapter(areaadapter);
            areaadapter.setClickListener(this);
        }

        public void showfilter() {
            rVArea.setVisibility(View.VISIBLE);
            tvNoAreaFound.setVisibility(View.GONE);
        }

        public void hidefilter() {
            rVArea.setVisibility(View.GONE);
            tvNoAreaFound.setVisibility(View.VISIBLE);
        }

    }


}