package com.sharedream.fragmenttest.activity;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sharedream.fragmenttest.R;
import com.sharedream.fragmenttest.adapter.WifiAdapter;
import com.sharedream.fragmenttest.util.Utils;
import com.sharedream.fragmenttest.util.WifiAdmin;
import com.sharedream.fragmenttest.util.WifiOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2016/4/16.
 */
public class MainActivity extends Activity {
    private final static String TAG = "TAG";
//    private EditText ssid;
//    private EditText password;
//    private Button connect;
    private WifiAdmin wifiAdmin;
    private ListView listView;
    private List<ScanResult> allListWifi = new ArrayList<ScanResult>();
    private List<ScanResult> filterListWifi = new ArrayList<ScanResult>();
    private List<ScanResult> dataList = new ArrayList<ScanResult>();
    private WifiAdapter adapter;
    private WifiManager mWifiManager;
    private WifiOperator wifiOperator;
    private Button changeMode;
    private float endX = 0;
    private float startX = 0;
    private Handler mMainHandler;
    private Thread mMainThread;
    private boolean modeFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mMainThread == null) {
            mMainThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    mMainHandler = new Handler();
                    Log.i(TAG, "mMainThread");
                    Looper.loop();
                }
            });
            mMainThread.setDaemon(true);
            mMainThread.start();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openwifi);
//        ssid = (EditText) findViewById(R.id.wifissid);
//        password = (EditText) findViewById(R.id.password);
//        connect = (Button) findViewById(R.id.connectwifi);
        changeMode = (Button) findViewById(R.id.changemode);
        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeFlag = !modeFlag;
                if (modeFlag) {
                    changeMode.setText("SDK METHOD");
                } else {
                    changeMode.setText("MY METHOD");
                }

            }
        });
        wifiOperator = WifiOperator.getInstance(this);
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        Log.i(TAG, "startY-->" + startX);
                        break;
                    case MotionEvent.ACTION_UP:
                        float len = endX - startX;
                        Log.i(TAG, "len-->" + len);
                        if (len  > 200) {
                            /*endX = 0;
                            startX = 0;*/
                            freshWifi();
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        endX = event.getX();
                        Log.i(TAG, "endY-->" + endX);
                        break;

                }

                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (filterListWifi != null && filterListWifi.size() > 0) {
                        ScanResult scanResult = filterListWifi.get(position);
                        String ssidName = new String(scanResult.SSID.getBytes(),"UTF-8");
//                        String ssidName = scanResult.SSID;
                        String bssidName = scanResult.BSSID;
                        int wifiType = Utils.getSecurity(scanResult.capabilities);
                        Log.i(TAG, "current wifi:ssid-->" + ssidName + ",bssid-->" + scanResult.BSSID + ", wifiType-->" + wifiType);
                        Toast.makeText(MainActivity.this, "current wifi:ssid-->" + ssidName + ",bssid-->" + scanResult.BSSID +", wifiType-->" + wifiType, Toast.LENGTH_SHORT).show();

                        if (wifiType == 0) {
                            if (modeFlag) {
                                //for sdk
                                Toast.makeText(MainActivity.this, "SDK MODE", Toast.LENGTH_SHORT).show();
                                wifiOperator.connectSSID(ssidName, bssidName, null, wifiType);
                            } else {
                                Toast.makeText(MainActivity.this, "MY METHOD", Toast.LENGTH_SHORT).show();
                                removeList();
                                String passwordName = "";
                                WifiConfiguration config = wifiAdmin.CreateWifiInfo(ssidName, passwordName, 1);
                                wifiAdmin.addNetwork(config);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        wifiAdmin = new WifiAdmin(this);
        wifiAdmin.openWifi();
        freshWifi();

        /*connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ssidName = ssid.getText().toString();
                String passwordName = password.getText().toString();
                WifiConfiguration config = wifiAdmin.CreateWifiInfo(ssidName, passwordName, 1);
                wifiAdmin.addNetwork(config);
            }
        });*/

    }

    public void removeList() {
        try {

            List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
            if (list != null) {
                for (WifiConfiguration conf : list) {
                    mWifiManager.removeNetwork(conf.networkId);
                }
            }
            Log.w(TAG, "remove Network list successfully .");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void freshWifi() {
        Log.i(TAG,"fresh");
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                allListWifi.clear();
                filterListWifi.clear();
                allListWifi = mWifiManager.getScanResults();
                for (ScanResult scanResult : allListWifi) {
                    if (!scanResult.SSID.equals("")) {
                        filterListWifi.add(scanResult);
                    }

                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dataList.clear();
                        dataList.addAll(filterListWifi);
                        if (dataList != null && dataList.size() > 0) {
                            if (adapter == null) {
                                adapter = new WifiAdapter(MainActivity.this, R.layout.item_wifi, dataList);
                                listView.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                                listView.setSelection(0);
                            }
                        }
                    }


                });


            }
    });

    }
    public void freshList() {
        Log.i(TAG, "freshList");
     /*   adapter = new WifiAdapter(MainActivity.this, R.layout.item_wifi, filterListWifi);
        listView.setAdapter(adapter);*/
        if (adapter == null){
            adapter = new WifiAdapter(MainActivity.this, R.layout.item_wifi, filterListWifi);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

       /* if (adapter == null) {
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ssidList);
            listView.setAdapter(adapter);
        } else {
            Log.i(TAG, "notifydataSetChanged");
            adapter.notifyDataSetChanged();
        }*/
    }
}
