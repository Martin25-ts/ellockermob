package com.android.ellocker.transaction;

public class TransactionDetail extends Locker {
    private String detail_id;
    private Integer duration;

    public TransactionDetail(String locker_Id, String locker_number, String locker_size, Boolean status_door, String location_name, String location_url, String detail_id, Integer duration) {
        super(locker_Id, locker_number, locker_size, status_door, location_name, location_url);
        this.detail_id = detail_id;
        this.duration = duration;
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
