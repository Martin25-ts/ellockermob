package com.android.ellocker.transaction;

import android.os.Parcel;
import android.os.Parcelable;

public class TransactionDetail extends Locker implements Parcelable {
    private String detail_id;
    private Integer duration;

    public TransactionDetail(String locker_Id, String locker_number, String locker_size, Boolean status_door, String location_name, String location_url, String detail_id, Integer duration) {
        super(locker_Id, locker_number, locker_size, status_door, location_name, location_url);
        this.detail_id = detail_id;
        this.duration = duration;
    }

    protected TransactionDetail(Parcel in) {
        super(in);
        detail_id = in.readString();
        duration = in.readInt();
    }


    public static final Creator<TransactionDetail> CREATOR = new Creator<TransactionDetail>() {
        @Override
        public TransactionDetail createFromParcel(Parcel in) {
            return new TransactionDetail(in);
        }

        @Override
        public TransactionDetail[] newArray(int size) {
            return new TransactionDetail[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(detail_id);
        dest.writeInt(duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
