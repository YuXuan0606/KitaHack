package com.example.kitahack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DonationAdapter extends BaseAdapter {

    private Context context;
    private List<DonationHistoryActivity.Donation> donations;

    public DonationAdapter(Context context, List<DonationHistoryActivity.Donation> donations) {
        this.context = context;
        this.donations = donations;
    }

    @Override
    public int getCount() {
        return donations.size();
    }

    @Override
    public Object getItem(int position) {
        return donations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_donation, parent, false);
        }

        DonationHistoryActivity.Donation donation = donations.get(position);

        TextView typeTextView = convertView.findViewById(R.id.donationType);
        TextView detailTextView = convertView.findViewById(R.id.donationDetail);
        TextView dateTextView = convertView.findViewById(R.id.donationDate);

        typeTextView.setText(donation.type + " Donation");
        detailTextView.setText(donation.detail);
        dateTextView.setText(donation.date);

        return convertView;
    }
}
