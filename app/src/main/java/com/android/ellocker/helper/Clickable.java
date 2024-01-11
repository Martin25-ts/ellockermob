package com.android.ellocker.helper;

import android.content.Context;
import android.content.Intent;

import com.android.ellocker.Dashboard;
import com.android.ellocker.Order;
import com.android.ellocker.Profile;
import com.android.ellocker.user.User;

public interface Clickable {

    default void pindahHome(Context context){
        Intent pindahhome = new Intent(context, Dashboard.class);
        context.startActivity(pindahhome);
    }

    default void pindahProfile(Context context, User user){
        Intent pindahprofile = new Intent(context, Profile.class);
        pindahprofile.putExtra("USER", user);
        context.startActivity(pindahprofile);
    }

    default void pindahOrder(Context context){
        Intent pindahorder = new Intent(context, Order.class);
        context.startActivity(pindahorder);

    }
}
