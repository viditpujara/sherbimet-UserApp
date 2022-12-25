package com.sherbimet.user.APIHelper;

public class JsonFields {
    public static final String AUTHORIZATION_KEY = "Authorization";

    public static final String FLAG = "flag";
    public static final String MESSAGE = "message";

    public static final String COMMON_REQUEST_PARAMS_CURRENT_PAGE = "currentpage";
    public static final String COMMON_REQUEST_PARAMS_USER_ID = "user_id";
    public static final String COMMON_REQUEST_PARAM_DEVICE_TYPE = "device_type";
    public static final String COMMON_REQUEST_PARAM_DEVICE_ID = "device_id";
    public static final String COMMON_REQUEST_PARAM_DEVICE_TOKEN = "device_token";
    public static final String COMMON_REQUEST_PARAM_DEVICE_OS_DETAILS = "device_os_details";
    public static final String COMMON_REQUEST_PARAM_DEVICE_IP_ADDRESS = "ip_address";
    public static final String COMMON_REQUEST_PARAM_DEVICE_MODEL_DETAILS = "device_modal_details";
    public static final String COMMON_REQUEST_PARAM_APP_VERSION_DETAILS = "app_version_details";

    public static final String LOGIN_COMMON_RESPONSE_OTP = "otp";


    public static final String COMMON_LOGOUT_RESPONSE_TITLE = "logout_title";
    public static final String COMMON_LOGOUT_RESPONSE_MESSAGE = "logout_message";
    public static final String COMMON_LOGOUT_RESPONSE_ICON = "logout_icon";

    public static final String LOGIN_REQUEST_PARAMS_MOBILE_NUMBER = "user_mobileno";

    public static final String LOGIN_RESPONSE_MOBILE_NUMBER = "user_mobileno";


    public static final String VERIFY_OTP_REQUEST_PARAM_USER_MOBILE = "user_mobileno";
    public static final String VERIFY_OTP_REQUEST_PARAM_USER_OTP = "mobile_otp";

    public static final String VERIFY_OTP_RESPONSE_USER_ID = "user_id";
    public static final String VERIFY_OTP_RESPONSE_USER_NAME = "user_name";
    public static final String VERIFY_OTP_RESPONSE_USER_MOBILE = "user_mobile";
    public static final String VERIFY_OTP_RESPONSE_USER_IMAGE = "user_image_url";

    public static final String RESEND_OTP_REQUEST_PARAM_USER_MOBILE = "user_mobileno";

    public static final String DASHBOARD_REQUEST_PARAMS_AREA_ID = "area_id";

    public static final String DASHBOARD_RESPONSE_USER_NAME = "user_name";
    public static final String DASHBOARD_RESPONSE_USER_IMAGE_URL = "user_image";
    public static final String DASHBOARD_RESPONSE_USER_GREETING = "greeting";
    public static final String DASHBOARD_RESPONSE_USER_SELECTED_AREA_ID = "area_id";
    public static final String DASHBOARD_RESPONSE_USER_SELECTED_AREA_NAME = "area_name";

    public static final String DASHBOARD_RESPONSE_USER_SELECTED_SERVICE_ARRAY = "service_list";
    public static final String DASHBOARD_RESPONSE_USER_SELECTED_SERVICE_ID = "service_id";
    public static final String DASHBOARD_RESPONSE_USER_SELECTED_SERVICE_NAME = "service_name";
    public static final String DASHBOARD_RESPONSE_USER_SELECTED_SERVICE_IMAGE = "service_image";

    public static final String AREA_RESPONSE_USER_SELECTED_AREA_ARRAY = "area";
    public static final String AREA_RESPONSE_USER_SELECTED_ARAE_NAME = "area_name";
    public static final String AREA_RESPONSE_USER_SELECTED_AREA_ID = "area_id";
    public static final String AREA_RESPONSE_USER_SELECTED_AREA_NAME_INITIALS = "area_name_initials";


    public static final String SUB_SERVICE_REQUEST_PARAMS_SERVICE_ID = "service_id";

    public static final String SUB_SERVICE_RESPONSE_SUB_SERVICE_ARRAY = "subservice_list";
    public static final String SUB_SERVICE_RESPONSE_SUB_SERVICE_ID = "subservice_id";
    public static final String SUB_SERVICE_RESPONSE_SUB_SERVICE_NAME = "subservice_name";
    public static final String SUB_SERVICE_RESPONSE_SUB_SERVICE_IMAGE = "subservice_image";


    public static final String PACKAGE_REQUEST_PARAMS_SUB_SERVICE_ID = "subservice_id";
    public static final String PACKAGE_REQUEST_PARAMS_PACKAGE_ID = "package_id";

    public static final String PACKAGE_RESPONSE_PACKAGE_ARRAY = "package_list";
    public static final String PACKAGE_RESPONSE_PACKAGE_ID = "package_id";
    public static final String PACKAGE_RESPONSE_PACKAGE_NAME = "package_name";
    public static final String PACKAGE_RESPONSE_PACKAGE_DESCRIPTION = "package_details";
    public static final String PACKAGE_RESPONSE_PACKAGE_AMOUNT = "package_price";
    public static final String PACKAGE_RESPONSE_PACKAGE_IMAGE = "package_image";

    public static final String ADD_NEW_BOOKING_REQUEST_REQUEST_PARAMS_AREA_ID = "area_id";
    public static final String ADD_NEW_BOOKING_REQUEST_PACKAGE_ID = "package_id";
    public static final String ADD_NEW_BOOKING_REQUEST_PARAMS_BOOKING_DATE = "booking_date";
    public static final String ADD_NEW_BOOKING_REQUEST_PARAMS_BOOKING_TIME = "booking_time";
    public static final String ADD_NEW_BOOKING_REQUEST_PARAMS_BOOKING_MESSAGE = "booking_message";
    public static final String ADD_NEW_BOOKING_REQUEST_PARAMS_BOOKING_ADDRESS = "booking_address";
    public static final String ADD_NEW_BOOKING_REQUEST_PARAMS_PAYMENT_METHOD_NAME = "payment_method";


    public static final String WORKERS_REQUEST_PARAMS_SUB_CATEGORY_ID = "sub_category_id";
    public static final String WORKERS_REQUEST_PARAMS_WORKER_ID = "worker_id";

    public static final String WORKERS_RESPONSE_WORKERS_ARRAY = "worker";
    public static final String WORKERS_RESPONSE_WORKER_ID = "worker_id";
    public static final String WORKERS_RESPONSE_WORKERS_USER_TYPE_ID = "user_type_id";
    public static final String WORKERS_RESPONSE_WORKER_NAME = "worker_name";
    public static final String WORKERS_RESPONSE_WORKER_GENDER = "worker_gender";
    public static final String WORKERS_RESPONSE_WORKER_EMAIL = "worker_email";
    public static final String WORKERS_RESPONSE_WORKER_MOBILE = "worker_mobile";
    public static final String WORKERS_RESPONSE_WORKER_EXPERIENCE = "worker_experience";
    public static final String WORKERS_RESPONSE_WORKER_PRICE = "worker_price";
    public static final String WORKERS_RESPONSE_WORKER_IMAGE = "worker_image";
    public static final String WORKERS_RESPONSE_WORKER_AVERAGE_RATING = "worker_average_rating";

    public static final String BOOKING_REQUEST_PARAMS_WORKER_ID = "worker_id";
    public static final String BOOKING_REQUEST_PARAMS_AREA_ID = "area_id";
    public static final String BOOKING_REQUEST_PARAMS_SUB_CATEGORY_ID = "subcategory_id";

    public static final String BOOKING_REQUEST_PARAMS_BOOKING_DATE = "booking_date";
    public static final String BOOKING_REQUEST_PARAMS_BOOKING_TIME = "booking_time";
    public static final String BOOKING_REQUEST_PARAMS_TYPE = "is_booking";
    public static final String BOOKING_REQUEST_PARAMS_TOTAL_AMOUNT = "booking_totalamount";
    public static final String BOOKING_REQUEST_PARAMS_BOOKING_MESSAGE = "booking_message";
    public static final String BOOKING_REQUEST_PARAMS_BOOKING_ADDRESS = "booking_address";

    public static final String BOOKINGS_REQUESTS_REQUEST_PARAMS_IS_BOOKING = "is_booking";

    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_USER_AREA_ID = "area_id";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_ARRAY = "booking";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_ID = "booking_id";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_DATE_TIME = "booking_date_time";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_ADDRESS = "booking_address";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_MESSAGE = "booking_message";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_TOTAL_AMOUNT = "booking_totalamount";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_ID = "worker_id";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_IMAGE = "worker_image";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_NAME = "worker_name";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_GENDER = "worker_gender";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_MOBILE = "worker_mobile";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_WORKER_EMAIL = "worker_email";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_STATUS_ID = "status_id";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_STATUS = "status_name";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_PACKAGE_NAME = "package_name";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_CAN_CANCEL = "can_cancel";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_CANCEL_REASON = "cancel_reason";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_CAN_FEEDBACK = "can_feedback";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_FEEDBACK_MESSAGE = "feedback_message";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_FEEDBACK_RATING = "feedback_rating";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_USER_NAME = "user_name";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_USER_IMAGE = "user_image";
    public static final String BOOKINGS_REQUESTS_RESPONSE_BOOKING_PAYMENT_METHOD = "payment_method";

    public static final String USER_PROFILE_RESPONSE_USER_NAME = "user_name";
    public static final String USER_PROFILE_RESPONSE_USER_FIRST_NAME = "user_first_name";
    public static final String USER_PROFILE_RESPONSE_USER_MIDDLE_NAME = "user_middle_name";
    public static final String USER_PROFILE_RESPONSE_USER_LAST_NAME = "user_last_name";

    public static final String USER_PROFILE_RESPONSE_PREFERRED_LANGUAGE_ID = "language_id";
    public static final String USER_PROFILE_RESPONSE_PREFERRED_LANGUAGE_TITLE = "language_name";

    public static final String USER_PROFILE_RESPONSE_PREFERRED_CITY_ID = "city_id";
    public static final String USER_PROFILE_RESPONSE_PREFERRED_CITY_TITLE = "city_name";

    public static final String USER_PROFILE_RESPONSE_PREFERRED_PINCODE_ID = "pincode_id";
    public static final String USER_PROFILE_RESPONSE_PREFERRED_PINCODE_TITLE = "pincode";
    public static final String USER_PROFILE_RESPONSE_USER_GENDER = "user_gender";
    public static final String USER_PROFILE_RESPONSE_USER_EMAIL = "user_email";
    public static final String USER_PROFILE_RESPONSE_USER_MOBILE_NUMBER = "user_mobileno";
    public static final String USER_PROFILE_RESPONSE_USER_IMAGE = "user_image";
    public static final String USER_PROFILE_RESPONSE_USER_ADDRESS = "user_address";
    public static final String USER_PROFILE_RESPONSE_USER_ADDRESS_LINE_1 = "user_address_line_1";
    public static final String USER_PROFILE_RESPONSE_USER_ADDRESS_LINE_2 = "user_address_line_2";

    public static final String EDIT_USER_PROFILE_REQUEST_USER_NAME = "user_name";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_FIRST_NAME = "user_first_name";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_MIDDLE_NAME = "user_middle_name";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_LAST_NAME = "user_last_name";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_PREFERRED_LANGUAGE_ID = "language_id";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_ADDRESS_LINE_1 = "user_address_line_1";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_ADDRESS_LINE_2 = "user_address_line_2";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_CITY_ID = "city_id";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_PINCODE_ID = "pincode_id";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_GENDER = "user_gender";
    public static final String EDIT_USER_PROFILE_REQUEST_USER_EMAIL = "user_email";

    public static final String EDIT_USER_PROFILE_IMAGE_REQUEST_USER_IMAGE = "user_image";

    public static final String ABOUT_US_RESPONSE_DESCRIPTION = "description";
    public static final String ABOUT_US_RESPONSE_IMAGE = "image";
    public static final String ABOUT_US_RESPONSE_FOOTER = "about_footer";

    public static final String CANCEL_BOOKING_REQUESTS_REQUEST_CANCEL_REASON = "cancel_reason";
    public static final String CANCEL_BOOKING_REQUESTS_REQUEST_BOOKING_ID = "booking_id";
    public static final String CANCEL_BOOKING_REQUESTS_REQUEST_IS_BOOKING = "is_booking";


    public static final String CITY_REQUEST_PARAMS_CITY_SEARCH_QUERY = "city_name";


    public static final String CITY_RESPONSE_CITY_ARRAY = "city";
    public static final String CITY_RESPONSE_CITY_ID = "city_id";
    public static final String CITY_RESPONSE_CITY_NAME = "city_name";
    public static final String CITY_RESPONSE_CITY_NAME_INITIALS = "city_name_initials";

    public static final String PINCODE_REQUEST_PARAMS_PINCODE_SEARCH_QUERY = "pincode";


    public static final String PINCODE_RESPONSE_PINCODE_ARRAY = "pincode";
    public static final String PINCODE_RESPONSE_PINCODE_ID = "pincode_id";
    public static final String PINCODE_RESPONSE_PINCODE = "pincode";

    public static final String PREFERRED_LANGUAGE_REQUEST_PARAMS_PREFERRED_LANGUAGE_SEARCH_QUERY = "language_name";

    public static final String PREFERRED_LANGUAGE_REQUEST_PARAMS_PREFERRED_LANGUAGE_ARRAY = "language";
    public static final String PREFERRED_LANGUAGE_REQUEST_PARAMS_PREFERRED_LANGUAGE_ID = "language_id";
    public static final String PREFERRED_LANGUAGE_REQUEST_PARAMS_PREFERRED_LANGUAGE_NAME = "language_name";
    public static final String PREFERRED_LANGUAGE_REQUEST_PARAMS_PREFERRED_LANGUAGE_INITIALS = "language_name_initials";

    public static final String FEEDBACK_REQUEST_IS_BOOKING = "is_booking";
    public static final String FEEDBACK_REQUEST_BOOKING_ID = "booking_id";
    public static final String FEEDBACK_REQUEST_WORKER_ID = "worker_id";
    public static final String FEEDBACK_REQUEST_FEEDBACK_MESSAGE = "feedback_message";
    public static final String FEEDBACK_REQUEST_FEEDBACK_RATING = "feedback_rating";

}
