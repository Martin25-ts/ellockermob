package com.android.ellocker.user;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String userid = "-1";
    private String frontName;
    private String lastName;
    private String email;
    private String nomorPonsel;

    private Integer role_id;

    private Integer status_user_id;



    protected User(Parcel in) {
        userid = in.readString();
        frontName = in.readString();
        lastName = in.readString();
        email = in.readString();
        nomorPonsel = in.readString();
        if (in.readByte() == 0) {
            role_id = null;
        } else {
            role_id = in.readInt();
        }
        if (in.readByte() == 0) {
            status_user_id = null;
        } else {
            status_user_id = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userid);
        dest.writeString(frontName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(nomorPonsel);
        if (role_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(role_id);
        }
        if (status_user_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status_user_id);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User getUser(User user){
        return user;
    }


    public User(String userid, String frontName, String lastName, String email, String nomorPonsel, Integer role_id, Integer status_user_id) {
        this.userid = userid;
        this.frontName = frontName;
        this.lastName = lastName;
        this.email = email;
        this.nomorPonsel = nomorPonsel;
        this.role_id = role_id;
        this.status_user_id = status_user_id;

    }

    public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNomorPonsel() {
        return nomorPonsel;
    }

    public void setNomorPonsel(String nomorPonsel) {
        this.nomorPonsel = nomorPonsel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getStatus_user_id() {
        return status_user_id;
    }

    public void setStatus_user_id(Integer status_user_id) {
        this.status_user_id = status_user_id;
    }
}