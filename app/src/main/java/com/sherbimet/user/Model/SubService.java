package com.sherbimet.user.Model;

public class SubService {
    String SubServiceID,SubServiceName,SubServiceImage;

    public SubService(String subServiceID, String subServiceName, String subServiceImage) {
        SubServiceID = subServiceID;
        SubServiceName = subServiceName;
        SubServiceImage = subServiceImage;
    }

    public SubService() {
    }

    public String getSubServiceID() {
        return SubServiceID;
    }

    public void setSubServiceID(String subServiceID) {
        SubServiceID = subServiceID;
    }

    public String getSubServiceName() {
        return SubServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        SubServiceName = subServiceName;
    }

    public String getSubServiceImage() {
        return SubServiceImage;
    }

    public void setSubServiceImage(String subServiceImage) {
        SubServiceImage = subServiceImage;
    }
}
