package com.sharedream.fragmenttest.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sharedream.fragmenttest.R;

/**
 * Created by as on 2016/4/16.
 */
public class RightFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        return view;
    }
    public void showMsg() {
        Toast.makeText(getActivity(), "I'm from rightfragment", Toast.LENGTH_LONG).show();
    }
}
