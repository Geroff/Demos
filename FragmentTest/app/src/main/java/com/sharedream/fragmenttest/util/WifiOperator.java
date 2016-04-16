package com.sharedream.fragmenttest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by as on 2016/4/16.
 */
public class WifiOperator {
    private static final String TAG = "TAG";
    private static WifiOperator instance = null;
    WifiManager mWifiManager;
    ConnectivityManager mConnectivityManager;
    private WifiOperator(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static WifiOperator getInstance(Context context) {
        if (instance == null) {
            synchronized (WifiOperator.class) {
                if (instance == null) {
                    instance = new WifiOperator(context);
                }
            }
        }
        return instance;
    }


    public WifiInfo getConnectionInfo() {
        return mWifiManager != null ? mWifiManager.getConnectionInfo() : null;
    }

    // remove " in ssid name for some android systems
    public String trimQuotation(String ssid) {
        if (ssid == null) {
            return null;
        }
        if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        return ssid;
    }
    public String getCurrentSSID() {
        String ssid = "";
        WifiInfo wifiInfo = getConnectionInfo();
        if (mWifiManager != null && wifiInfo != null) {
            ssid = trimQuotation(wifiInfo.getSSID());
        }
        return ssid;
    }

    public String getCurrentBSSID() {
        String bssid = "";
        WifiInfo wifiInfo = getConnectionInfo();
        if (mWifiManager != null && wifiInfo != null) {
            bssid = wifiInfo.getBSSID();
        }
        return bssid;
    }

    public WifiConfiguration getCurrentWifiConfiguration() {
        return getWifiConfiguration(getCurrentSSID(), getCurrentBSSID());
    }


    public WifiConfiguration getWifiConfiguration(String ssid, String bssid) {
        WifiInfo wifiInfo = getConnectionInfo();
        if (mWifiManager != null && wifiInfo != null) {
            List<WifiConfiguration> list = mWifiManager.getConfiguredNetworks();
            if (list != null) {
                for (WifiConfiguration conf : list) {
                    if ((Utils.validateString(bssid) && Utils.validateString(conf.BSSID) && conf.BSSID.equalsIgnoreCase(bssid)) || (Utils.validateString(ssid) && Utils.validateString(conf.SSID) && trimQuotation(conf.SSID).equalsIgnoreCase(ssid))) {
                        return conf;
                    }
                }
            }
        }

        return null;
    }


    /**
     * Create wifi configuration from supplied SSID and password
     *
     * @param ssid Wifi SSID to connect
     * @param password Wifi password
     * @param type Wifi security type
     * @return WifiConfiguration The created WifiConfiguration.
     */
    public WifiConfiguration createWifiConfiguration(String ssid, String password, int type) {
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + ssid + "\"";
        config.priority = 90000;

        if (type == Utils.AP_SECURITY_OPEN) {        // WIFICIPHER_NOPASS
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else if (type == Utils.AP_SECURITY_WEP) {    // WIFICIPHER_WEP
            config.hiddenSSID = true;
            config.wepTxKeyIndex = 0;
            config.wepKeys[0] = "\"" + password + "\"";
//			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
//			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == Utils.AP_SECURITY_WPA) { // WIFICIPHER_WPA
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            config.status = WifiConfiguration.Status.ENABLED;
        }/* else if (type == Constant.AP_KEY_MGMT_EAP) {
        	WifiEnterpriseConfig enterpriseConfig = new WifiEnterpriseConfig();
        	enterpriseConfig.setIdentity(username);
        	enterpriseConfig.setPassword(password);
        	enterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.PEAP);
            config.hiddenSSID = true;
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            config.enterpriseConfig = enterpriseConfig;
            config.status = WifiConfiguration.Status.ENABLED;*/

/*        } else if (type == Constant.AP_SECURITY_WPA2) { // WIFICIPHER_WPA2
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            config.status = WifiConfiguration.Status.ENABLED;
        }*/

        return config;
    }
    public boolean isUsingWifi() {
        try {

            if (mConnectivityManager != null) {
                android.net.NetworkInfo.State state = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                if (state == android.net.NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return false;
    }
    public int connectSSID(String ssid, String bssid, String password, int type) {
        try {
            WifiConfiguration config = getWifiConfiguration(ssid, bssid);
            if (config == null) {
                config = createWifiConfiguration(ssid, password, type);
                if (mWifiManager != null) {
                    if (isUsingWifi()) {
                        mWifiManager.disconnect();
                    }
                }


            }


            int formerNetworkId = mWifiManager.updateNetwork(config);
            int networkId = formerNetworkId == -1 ? mWifiManager.addNetwork(config) : formerNetworkId;
            Log.w(TAG, "networkId " + networkId);
            if (networkId >= 0) {
                Log.w(TAG, "Connecting SSID " + ssid + " with security " + type);
                Method connectMethod = connectWifiByReflectMethod(networkId);
                if (connectMethod == null) {
                    Log.d(TAG, "connect wifi normal road");
                    if (mWifiManager.enableNetwork(networkId, true) && mWifiManager.saveConfiguration() && mWifiManager.reconnect()) {
                        return networkId;    // important, otherwise the network won't switch immediately, even it'll not switch
                    }
                } else {
                    return networkId;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Connect to wifi using reflect method, calls connect after 4.2, calls connectNetwork in 4.0 ~ 4.1, doesn't handle before 4.0.
     *
     * @param netId The configuration network id
     * @return Method The reflect method.
     */
    private Method connectWifiByReflectMethod(int netId) {
        Method connectMethod = null;
        if (netId < 0) {
            return connectMethod;
        }

        try {
            if (true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    Log.d(TAG, "connectWifiByReflectMethod road 1");
                    // 反射方法： connect(int, listener) , 4.2 <= phone's android version
                    for (Method methodSub : mWifiManager.getClass().getDeclaredMethods()) {
                        if ("connect".equalsIgnoreCase(methodSub.getName())) {
                            Class<?>[] types = methodSub.getParameterTypes();
                            if (types != null && types.length > 0) {
                                if ("int".equalsIgnoreCase(types[0].getName())) {
                                    connectMethod = methodSub;
                                }
                            }
                        }
                    }
                    if (connectMethod != null) {
                        try {
                            connectMethod.invoke(mWifiManager, netId, null);
                        } catch (Throwable e) {
                            e.printStackTrace();
                            Log.i(TAG, "connectWifiByReflectMethod Android " + Build.VERSION.SDK_INT + " error!");
                            return null;
                        }
                    }
                } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN) {
                    // 反射方法: connect(Channel c, int networkId, ActionListener listener)
                    // 暂时不处理4.1的情况 , 4.1 == phone's android version
                    Log.d(TAG, "connectWifiByReflectMethod road 2");
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    Log.d(TAG, "connectWifiByReflectMethod road 3");
                    // 反射方法：connectNetwork(int networkId) ,
                    // 4.0 <= phone's android version < 4.1
                    for (Method methodSub : mWifiManager.getClass().getDeclaredMethods()) {
                        if ("connectNetwork".equalsIgnoreCase(methodSub.getName())) {
                            Class<?>[] types = methodSub.getParameterTypes();
                            if (types != null && types.length > 0) {
                                if ("int".equalsIgnoreCase(types[0].getName())) {
                                    connectMethod = methodSub;
                                }
                            }
                        }
                    }
                    if (connectMethod != null) {
                        try {
                            connectMethod.invoke(mWifiManager, netId);
                        } catch (Throwable e) {
                            e.printStackTrace();
                            Log.d(TAG, "connectWifiByReflectMethod Android " + Build.VERSION.SDK_INT + " error!");
                            return null;
                        }
                    }
                } else {
                    // < android 4.0
                    return null;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            connectMethod = null;
        }

        return connectMethod;
    }

}
