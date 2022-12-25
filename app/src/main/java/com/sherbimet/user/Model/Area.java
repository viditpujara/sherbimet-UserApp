package com.sherbimet.user.Model;

public class Area {
    String AreaID,AreaName,AreaNameInitials;

    public Area(String areaID, String areaName, String areaNameInitials) {
        AreaID = areaID;
        AreaName = areaName;
        AreaNameInitials = areaNameInitials;
    }

    public Area() {
    }

    public String getAreaID() {
        return AreaID;
    }

    public void setAreaID(String areaID) {
        AreaID = areaID;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getAreaNameInitials() {
        return AreaNameInitials;
    }

    public void setAreaNameInitials(String areaNameInitials) {
        AreaNameInitials = areaNameInitials;
    }
}
