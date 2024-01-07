package com.android.ellocker.helper;

import android.content.Context;
import android.content.Intent;

import com.android.ellocker.Dashboard;

public interface Clickable {

    default void pindahHome(Context context){
        Intent pindahhome = new Intent(context, Dashboard.class);

    }
}
