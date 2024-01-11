package com.android.ellocker.transaction;

import android.os.Parcel;
import android.os.Parcelable;

public class Locker implements Parcelable {
    private String locker_Id;
    private String locker_number;

    private String locker_size;
    private Boolean status_door;

    private String location_name;

    private String location_url;

    public Locker(String locker_Id, String locker_number, String locker_size, Boolean status_door, String location_name, String location_url) {
        this.locker_Id = locker_Id;
        this.locker_number = locker_number;
        this.locker_size = locker_size;
        this.status_door = status_door;
        this.location_name = location_name;
        this.location_url = location_url;
    }

    protected Locker(Parcel in) {
        locker_Id = in.readString();
        locker_number = in.readString();
        locker_size = in.readString();
        status_door = in.readInt() != 0;
        location_name = in.readString();
        location_url = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(locker_Id);
        dest.writeString(locker_number);
        dest.writeString(locker_size);
        dest.writeInt(status_door ? 1 : 0);
        dest.writeString(location_name);
        dest.writeString(location_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Locker> CREATOR = new Creator<Locker>() {
        @Override
        public Locker createFromParcel(Parcel in) {
            return new Locker(in);
        }

        @Override
        public Locker[] newArray(int size) {
            return new Locker[size];
        }
    };

    public String getLocation_name() {
        return location_name;
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }

    public String getLocation_url() {
        return location_url;
    }

    public void setLocation_url(String location_url) {
        this.location_url = location_url;
    }

    public String getLocker_Id() {
        return locker_Id;
    }

    public void setLocker_Id(String locker_Id) {
        this.locker_Id = locker_Id;
    }

    public String getLocker_number() {
        return locker_number;
    }

    public void setLocker_number(String locker_number) {
        this.locker_number = locker_number;
    }

    public String getLocker_size() {
        return locker_size;
    }

    public void setLocker_size(String locker_size) {
        this.locker_size = locker_size;
    }

    public Boolean getStatus_door() {
        return status_door;
    }

    public void setStatus_door(Boolean status_door) {
        this.status_door = status_door;
    }
}
