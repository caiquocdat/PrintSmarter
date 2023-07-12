package com.example.printmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.printmaster.adapter.DeviceAdapter;
import com.example.printmaster.adapter.ImagePrintDocumentAdapter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

public class ConnectDeviceActivity extends AppCompatActivity {
    private boolean isDataLoaded = false;
    private NsdManager nsdManager;
    private NsdManager.DiscoveryListener discoveryListener;
    private NsdManager.ResolveListener resolveListener;
    private static final String TAG = "QuocDat";
    private static final int PERMISSION_REQUEST_CODE = 1;
    List<NsdServiceInfo> devices;
    RecyclerView recyclerView;
    DeviceAdapter adapter;
    NsdServiceInfo service_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_device);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Compatible List");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.img_back);
            getSupportActionBar().setTitle("Compatible List");
            toolbar.setTitleTextColor(Color.parseColor("#F7F7F7"));
        }
        // Kiểm tra và yêu cầu quyền truy cập Wi-Fi
//        checkAndRequestPermissions();
//
//        // Kiểm tra kết nối Wi-Fi và tìm kiếm máy in
//        if (isWifiConnected()) {
//            new PrinterSearchTask().execute();
//        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        devices = new ArrayList<>();
        adapter = new DeviceAdapter(devices,ConnectDeviceActivity.this);
        recyclerView.setAdapter(adapter);

        nsdManager = (NsdManager) getSystemService(Context.NSD_SERVICE);

        initializeResolveListener();
        initializeDiscoveryListener();

        // Start discovering
        nsdManager.discoverServices("_ipp._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void initializeDiscoveryListener() {
        discoveryListener = new NsdManager.DiscoveryListener() {
            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG, "Service discovery started");
            }

            @Override
            public void onServiceFound(NsdServiceInfo service) {
                Log.d(TAG, "Service discovery success " + service);
                String serviceType = service.getServiceType();
                if (serviceType.equals("_ipp._tcp.") || serviceType.equals("_pdl-datastream._tcp.") || serviceType.equals("_http._tcp")) {
//                    nsdManager.resolveService(service, resolveListener);
                    devices.add(service);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isDataLoaded) {
                                adapter.notifyDataSetChanged(); // <-- Thêm dòng này vào
                            }
                        }
                    });
                } else {
                    Log.d(TAG, "Unknown Service Type: " + service.getServiceType());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isDataLoaded = true; // <-- Thêm dòng này vào
                        adapter.notifyDataSetChanged(); // <-- Thêm dòng này vào
                    }});
            }

            @Override
            public void onServiceLost(NsdServiceInfo service) {
                Log.e(TAG, "service lost" + service);
//                devices.remove(service_copy);
//                adapter.notifyDataSetChanged();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (Iterator<NsdServiceInfo> iterator = devices.iterator(); iterator.hasNext(); ) {
                            NsdServiceInfo device = iterator.next();
                            if (device.getServiceName().equals(service.getServiceName())) {
                                iterator.remove();
                                adapter.notifyDataSetChanged(); // Cập nhật ngay lập tức sau khi một dịch vụ bị loại bỏ
                                break; // Thoát khỏi vòng lặp sau khi đã tìm thấy và loại bỏ dịch vụ
                            }
                        }
                    }
                });
            }

            @Override
            public void onDiscoveryStopped(String serviceType) {
                Log.i(TAG, "Discovery stopped: " + serviceType);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                nsdManager.stopServiceDiscovery(this);
            }
        };
    }

    public void initializeResolveListener() {
        resolveListener = new NsdManager.ResolveListener() {
            @Override
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                Log.e(TAG, "Resolve failed" + errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                Log.e(TAG, "Resolve Succeeded. " + serviceInfo);
                if (serviceInfo.getServiceName().equals("NsdChat")) {
                    Log.d(TAG, "Same IP.");
                    return;
                }
                service_copy = serviceInfo;
                devices.add(serviceInfo);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isDataLoaded) {
                            adapter.notifyDataSetChanged(); // <-- Thêm dòng này vào
                        }
                    }
                });
                // Perform any operation with the resolved service info here.
                // E.g. send the printer data for printing.
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_8_lable);
//                PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
//                String jobName = getString(R.string.app_name) + " Document";
//                printManager.print(jobName, new ImagePrintDocumentAdapter(ConnectDeviceActivity.this, bitmap), null);

            }
        }

        ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (nsdManager != null) {
            nsdManager.stopServiceDiscovery(discoveryListener);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isDataLoaded = true; // <-- Thêm dòng này vào
                    adapter.notifyDataSetChanged(); // <-- Thêm dòng này vào
                }});
        }
    }

    private void checkAndRequestPermissions() {
        String[] permissions = {android.Manifest.permission.ACCESS_WIFI_STATE, android.Manifest.permission.CHANGE_WIFI_STATE,
                android.Manifest.permission.ACCESS_NETWORK_STATE};

        ArrayList<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        }
    }

    private boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo.isConnected();
    }

    private class PrinterSearchTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> printerList = new ArrayList<>();

            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ipString = String.format("%d.%d.%d.%d",
                    (ipAddress & 0xff),
                    (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff),
                    (ipAddress >> 24 & 0xff));
            String subnet = ipString.substring(0, ipString.lastIndexOf(".") + 1);

            for (int i = 1; i <= 255; i++) {
                String printerIp = subnet + i;
                try {
                    InetAddress inetAddress = InetAddress.getByName(printerIp);
                    if (inetAddress.isReachable(10000)) {
                        printerList.add(printerIp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d("Printer", "doInBackground: " + printerList);

            return printerList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> printerList) {
            super.onPostExecute(printerList);
            // In danh sách các máy in tìm được
            for (String printerIp : printerList) {
                Log.d("Printer", "Printer IP: " + printerIp);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}