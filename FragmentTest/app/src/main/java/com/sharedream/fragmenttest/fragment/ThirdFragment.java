package com.sharedream.fragmenttest.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sharedream.fragmenttest.R;

/**
 * Created by as on 2016/4/16.
 */
public class ThirdFragment extends Fragment {
    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        button = (Button) view.findViewById(R.id.btn_thirdfragment_showdialog);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity)getActivity()).showDialog(getActivity());
            }
        });
        return view;
    }
}
