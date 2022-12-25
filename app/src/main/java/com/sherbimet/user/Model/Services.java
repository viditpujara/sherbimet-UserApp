package com.sherbimet.user.Model;

public class Services {
    String ServiceID, ServiceName, ServiceImage;

    public Services(String serviceID, String serviceName, String serviceImage) {
        ServiceID = serviceID;
        ServiceName = serviceName;
        ServiceImage = serviceImage;
    }

    public Services() {
    }

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getServiceImage() {
        return ServiceImage;
    }

    public void setServiceImage(String serviceImage) {
        ServiceImage = serviceImage;
    }
}
