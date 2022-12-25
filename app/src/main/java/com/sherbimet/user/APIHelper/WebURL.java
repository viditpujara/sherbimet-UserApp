package com.sherbimet.user.APIHelper;

public class WebURL {

    //LIVE URL
    private static final String LIVE_URL = "http://sherbimet.swapsinfotech.com/admin/api/user/";

    //IP ADDRESS
    private static final String IP_ADDRESS = "192.168.0.7";

    //LOCAL URL
    private static final String LOCAL_URL = "http://" + IP_ADDRESS + "/sherbimet/admin/api/user/";

    //BASE URL
    public static final String MAIN_URL = LIVE_URL;

    public static final String LOGIN_URL = MAIN_URL+"login-with-otp.php";

    public static final String LOGOUT_URL = MAIN_URL+"user-logout.php";

    public static final String VERIFY_OTP_URL = MAIN_URL+"verify-otp.php";

    public static final String RESEND_OTP_URL = MAIN_URL+"resend-otp.php";

    public static final String DASHBOARD_URL = MAIN_URL+"dashboard.php";

    public static final String AREA_URL = MAIN_URL+"area-list.php";

    public static final String SUB_SERVICE_URL = MAIN_URL+"subservice-list.php";

    public static final String PACKAGE_URL = MAIN_URL+"package-list.php";

    public static final String ADD_NEW_SERVICE_REQUEST_URL = MAIN_URL+"add-new-booking-request.php";

    public static final String WORKERS_URL = MAIN_URL+"worker-list.php";

    public static final String BOOK_WORKER_URL = MAIN_URL+"add-new-booking-request.php";

    public static final String BOOKINGS_REQUESTS_URL = MAIN_URL+"booking-list.php";

    public static final String USER_PROFILE_URL = MAIN_URL+"user-profile-list.php";

    public static final String EDIT_PORFILE_URL = MAIN_URL+"user-profile-update.php";

    public static final String EDIT_USER_PROFILE_IMAGE_URL = MAIN_URL+"user-profile-image-update.php";

    public static final String ABOUT_US_URL = MAIN_URL+"aboutus.php";

    public static final String CANCEL_BOOKING_REQUEST_URL = MAIN_URL+"booking-cancelled.php";

    public static final String SELECT_CITY_URL = MAIN_URL+"city-list.php";

    public static final String SELECT_PINCODE_URL = MAIN_URL+"pincode-list.php";

    public static final String SELECT_PREFERRED_LANGUAGE_URL = MAIN_URL+"language-list.php";

    public static final String GIVE_FEEDBACK_URL = MAIN_URL+"user-feedback.php";
}
