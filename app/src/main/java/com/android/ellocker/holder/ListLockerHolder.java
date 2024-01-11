package com.android.ellocker.holder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ellocker.R;

public class ListLockerHolder extends RecyclerView.ViewHolder {

    private RelativeLayout rllockercard;
    private TextView tvlockernumber, tvlockerlocation, tvlockersize;



    public ListLockerHolder(@NonNull View itemView) {
        super(itemView);

        rllockercard = itemView.findViewById(R.id.rllockercard);
        tvlockerlocation = itemView.findViewById(R.id.tvlockerlocation);
        tvlockernumber = itemView.findViewById(R.id.tvlockernumber);
        tvlockersize = itemView.findViewById(R.id.tvlockersize);
    }

    public RelativeLayout getRllockercard() {
        return rllockercard;
    }

    public void setRllockercard(RelativeLayout rllockercard) {
        this.rllockercard = rllockercard;
    }

    public TextView getTvlockernumber() {
        return tvlockernumber;
    }

    public void setTvlockernumber(TextView tvlockernumber) {
        this.tvlockernumber = tvlockernumber;
    }

    public TextView getTvlockerlocation() {
        return tvlockerlocation;
    }

    public void setTvlockerlocation(TextView tvlockerlocation) {
        this.tvlockerlocation = tvlockerlocation;
    }

    public TextView getTvlockersize() {
        return tvlockersize;
    }

    public void setTvlockersize(TextView tvlockersize) {
        this.tvlockersize = tvlockersize;
    }
}
