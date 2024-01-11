package com.android.ellocker.transaction;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Transaction extends TransactionDetail implements Parcelable {
    private String transaction_id;
    private String transaction_date;
    private String user_id;
    private String status_transaction;


    public Transaction(String locker_Id, String locker_number, String locker_size, Boolean status_door, String location_name, String location_url, String detail_id, Integer duration, String transaction_id, String transaction_date, String user_id, String status_transaction) {
        super(locker_Id, locker_number, locker_size, status_door, location_name, location_url, detail_id, duration);
        this.transaction_id = transaction_id;
        this.transaction_date = transaction_date;
        this.user_id = user_id;
        this.status_transaction = status_transaction;
    }

    protected Transaction(Parcel in) {
        super(in);
        transaction_id = in.readString();
        transaction_date = in.readString();
        user_id = in.readString();
        status_transaction = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus_transaction() {
        if(status_transaction.equals("1")){
            return status_transaction = "InProgres";
        } else if (status_transaction.equals("2")) {
            return status_transaction = "MoreTime";
        }else{
            return status_transaction = "Finished";
        }
    }

    public void setStatus_transaction(String status_transaction) {
        this.status_transaction = status_transaction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(transaction_id);
        dest.writeString(transaction_date);
        dest.writeString(user_id);
        dest.writeString(status_transaction);
    }
}
