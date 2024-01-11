    package com.android.ellocker.adapter;

    import android.app.Activity;
    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.android.ellocker.Locker;
    import com.android.ellocker.R;
    import com.android.ellocker.holder.ListLockerHolder;
    import com.android.ellocker.transaction.Transaction;

    import java.util.List;

    public class ListLockerAdapter extends RecyclerView.Adapter<ListLockerHolder> {


        List<Transaction> lockerlist;

        public ListLockerAdapter(List<Transaction> lockerlist) {
            this.lockerlist = lockerlist;
        }

        @NonNull
        @Override
        public ListLockerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_locker_card, parent, false);
            return new ListLockerHolder(view);
        }

            @Override
            public void onBindViewHolder(@NonNull ListLockerHolder holder, int position) {

                Transaction locker = lockerlist.get(position);



                holder.getTvlockernumber().setText(String.valueOf(locker.getLocker_number()));
                holder.getTvlockerlocation().setText(String.valueOf(locker.getLocation_name()));
                holder.getTvlockersize().setText(String.valueOf(locker.getLocker_size()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent pindahLocker = new Intent(v.getContext(), Locker.class);
                        pindahLocker.putExtra("TRANSACTION", locker);
                        v.getContext().startActivity(pindahLocker);
                    }
                });


            }

        @Override
        public int getItemCount() {
            return lockerlist.size();
        }
    }
