package com.sharedream.fragmenttest.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharedream.fragmenttest.R;
import com.sharedream.fragmenttest.util.Utils;

import java.util.List;

/**
 * Created by as on 2016/4/16.
 */
public class WifiAdapter extends ArrayAdapter<ScanResult> {
    private int resourceId;



    public WifiAdapter(Context context, int resource, List<ScanResult> wifiList) {
        super(context, resource, wifiList);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            ScanResult scanResult = getItem(position);
            if (scanResult != null) {
                HolderView holderView;
                if (convertView == null) {
                    holderView = new HolderView();
                    convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
                    holderView.imageView = (ImageView) convertView.findViewById(R.id.wifi_password_img);
                    holderView.textView = (TextView) convertView.findViewById(R.id.wifi_ssid);
                    convertView.setTag(holderView);
                } else {
                    holderView = (HolderView) convertView.getTag();
                }

                if (scanResult != null && !scanResult.SSID.equals("")) {
                    if (Utils.getSecurity(scanResult.capabilities) == 0) {
                        holderView.imageView.setImageResource(R.drawable.icon_unlock);
                    } else {
                        holderView.imageView.setImageResource(R.drawable.icon_lock);
                    }
                    holderView.textView.setText(scanResult.SSID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class HolderView {
        ImageView imageView;
        TextView textView;
    }


}
