package com.sherbimet.user.Model;

public class Bookings {

    String BookingID,BookingDateTime,BookingAddress,BookingMessage,BookingTotalAmount,WorkerID,WorkerImage,WorkerName,WorkerGender,WorkerMobile,WorkerEmail,BookingStatusID, BookingStatus,PackageName,CanCancel,CancelReason,CanFeedback,FeedbackMessage,FeedbackRatings,UserName,UserImage,PaymentMethod;

    public Bookings(String bookingID, String bookingDateTime, String bookingAddress, String bookingMessage, String bookingTotalAmount, String workerID, String workerImage, String workerName, String workerGender, String workerMobile, String workerEmail, String bookingStatusID, String bookingStatus, String packageName, String canCancel, String cancelReason, String canFeedback, String feedbackMessage, String feedbackRatings, String userName, String userImage, String paymentMethod) {
        BookingID = bookingID;
        BookingDateTime = bookingDateTime;
        BookingAddress = bookingAddress;
        BookingMessage = bookingMessage;
        BookingTotalAmount = bookingTotalAmount;
        WorkerID = workerID;
        WorkerImage = workerImage;
        WorkerName = workerName;
        WorkerGender = workerGender;
        WorkerMobile = workerMobile;
        WorkerEmail = workerEmail;
        BookingStatusID = bookingStatusID;
        BookingStatus = bookingStatus;
        PackageName = packageName;
        CanCancel = canCancel;
        CancelReason = cancelReason;
        CanFeedback = canFeedback;
        FeedbackMessage = feedbackMessage;
        FeedbackRatings = feedbackRatings;
        UserName = userName;
        UserImage = userImage;
        PaymentMethod = paymentMethod;
    }

    public Bookings() {
    }

    public String getBookingID() {
        return BookingID;
    }

    public void setBookingID(String bookingID) {
        BookingID = bookingID;
    }

    public String getBookingDateTime() {
        return BookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        BookingDateTime = bookingDateTime;
    }

    public String getBookingAddress() {
        return BookingAddress;
    }

    public void setBookingAddress(String bookingAddress) {
        BookingAddress = bookingAddress;
    }

    public String getBookingMessage() {
        return BookingMessage;
    }

    public void setBookingMessage(String bookingMessage) {
        BookingMessage = bookingMessage;
    }

    public String getBookingTotalAmount() {
        return BookingTotalAmount;
    }

    public void setBookingTotalAmount(String bookingTotalAmount) {
        BookingTotalAmount = bookingTotalAmount;
    }

    public String getWorkerID() {
        return WorkerID;
    }

    public void setWorkerID(String workerID) {
        WorkerID = workerID;
    }

    public String getWorkerImage() {
        return WorkerImage;
    }

    public void setWorkerImage(String workerImage) {
        WorkerImage = workerImage;
    }

    public String getWorkerName() {
        return WorkerName;
    }

    public void setWorkerName(String workerName) {
        WorkerName = workerName;
    }

    public String getWorkerGender() {
        return WorkerGender;
    }

    public void setWorkerGender(String workerGender) {
        WorkerGender = workerGender;
    }

    public String getWorkerMobile() {
        return WorkerMobile;
    }

    public void setWorkerMobile(String workerMobile) {
        WorkerMobile = workerMobile;
    }

    public String getWorkerEmail() {
        return WorkerEmail;
    }

    public void setWorkerEmail(String workerEmail) {
        WorkerEmail = workerEmail;
    }

    public String getBookingStatusID() {
        return BookingStatusID;
    }

    public void setBookingStatusID(String bookingStatusID) {
        BookingStatusID = bookingStatusID;
    }

    public String getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        BookingStatus = bookingStatus;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getCanCancel() {
        return CanCancel;
    }

    public void setCanCancel(String canCancel) {
        CanCancel = canCancel;
    }

    public String getCancelReason() {
        return CancelReason;
    }

    public void setCancelReason(String cancelReason) {
        CancelReason = cancelReason;
    }

    public String getCanFeedback() {
        return CanFeedback;
    }

    public void setCanFeedback(String canFeedback) {
        CanFeedback = canFeedback;
    }

    public String getFeedbackMessage() {
        return FeedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        FeedbackMessage = feedbackMessage;
    }

    public String getFeedbackRatings() {
        return FeedbackRatings;
    }

    public void setFeedbackRatings(String feedbackRatings) {
        FeedbackRatings = feedbackRatings;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }
}
