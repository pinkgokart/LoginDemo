package com.example.logindemo;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class ViewHolder extends RecyclerView.ViewHolder {
    private TextView txtName;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtPostDec;
View mView;
    public ViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        //  txtTime = itemView.findViewById(R.id.post_profile_image);
    }

    void setTimePost(posts post) {
        txtName = itemView.findViewById((R.id.post_user_name));
        txtDate = itemView.findViewById(R.id.post_date);
        txtTime = itemView.findViewById(R.id.post_time);
        txtPostDec = itemView.findViewById(R.id.post_description);

        String date = post.getDate();
        txtDate.setText((date));
        String postdescrip = post.getPostdescrip();
        txtPostDec.setText((postdescrip));
        String time = post.getTime();
        txtTime.setText((time));
        String fullname = post.getFullname();
    }

}