package com.example.user.foodhandbook.activity.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.foodhandbook.R;
import com.example.user.foodhandbook.activity.model.TienIch;
import com.example.user.foodhandbook.activity.ultil.CheckConnection;
import com.example.user.foodhandbook.activity.ultil.Server;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.user.foodhandbook.R.id.map;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient apiClient;
    private LocationRequest locationRequest;
    private List<LatLng> latLngs = new ArrayList<>();
    private Marker currentMarket;// dùng để lấy ra vị trí hiện tại

    private static final int GPS_PERMISSION_REQUEST_CODE = 1;

    private ProgressDialog progressDialog;



    private int idmonan = 0;
    private TienIch tienIch;
    //ArrayList<TienIch> arrayTienIch;
    private TextView textViewDiaChi;
    private TextView textViewTenQuan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            textViewDiaChi = (TextView) findViewById(R.id.textviewDiaDiem);
            textViewTenQuan = (TextView) findViewById(R.id.textviewTenQuan);
            //arrayTienIch = new ArrayList<>();
            GetIdMonAn();
            GetData();
            initProgressDialog();
        }else{
            CheckConnection.ShowToast(getApplicationContext(),"Bạn hãy kiểm tra kết nối");
        }



    }

    private void GetIdMonAn() {
        idmonan = getIntent().getIntExtra("idmon", -1);
        //Toast.makeText(getApplicationContext(),idmonan+"", Toast.LENGTH_SHORT).show();
        // Log.d("IDloaisp", idloai + "");
    }

    private void GetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongan = Server.Duongdantienich;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongan, new Response.Listener<String>() {
            // dùng phương thức này để lấy dữ liệu về
            @Override
            public void onResponse(String response) {
                // tạo biến hứng giá trị
                int idtienich;
                double vido;
                double kinhdo;
                String chude;
                String chuthich;
                String video;
                int id;
                if (response != null) {
                    // có dữ liệu trả về thì thanh progressBar tắt đi
                    try {
                        JSONArray jsonArr = new JSONArray(response);
                        // lấy từng object của Array
                        for (int i = 0; i < jsonArr.length(); i++) {
                            // lấy từng values của từng object
                            JSONObject jsonObject = jsonArr.getJSONObject(i);
                            idtienich = jsonObject.getInt("idtienich");
                            vido = jsonObject.getDouble("vido");
                            kinhdo = jsonObject.getDouble("kinhdo");
                            chude = jsonObject.getString("chude");
                            chuthich = jsonObject.getString("chuthich");
                            video = jsonObject.getString("video");
                            id = jsonObject.getInt("id");
                            // thêm dữ liệu vào mảng
                            //arrayTienIch.add(new TienIch(idtienich, vido, kinhdo, chude, chuthich, video, id));
                            tienIch = new TienIch(idtienich, vido, kinhdo, chude, chuthich, video, id);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            // phương thức dùng để đẩy dữ liệu lên server
            // dưới dạng 1 HashMap
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // HashMap<Key,value>
                HashMap<String, String> param = new HashMap<String, String>();
                // đưa giá trị của idloai lên server
                param.put("id", String.valueOf(idmonan));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    // ProgessDialof
    private void initProgressDialog() {
        progressDialog = new ProgressDialog(MapsActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Đang lấy địa điểm");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }


    // tạo android client để truy cập vào GPS
    private void buildApiClient() {
        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        apiClient.connect();
    }

    // xin cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GPS_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (apiClient == null) {
                            buildApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // lấy vị trí hiện tại
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
//            buildApiClient();
//            this.mMap.setMyLocationEnabled(true);
//        }else{
//            // chưa cấp quyền
//            // kiểm tra phiên bản
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION_REQUEST_CODE);
//            }
//        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LatLng diadiem = new LatLng(tienIch.getVido(), tienIch.getKinhdo());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        diadiem, 14));
                // You can customize the marker image using images bundled with
                // your app, or dynamFically generated bitmaps.
                mMap.addMarker(new MarkerOptions().title(tienIch.getChude())
                        .snippet(tienIch.getChuthich())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.house_flag))
                        .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                        .position(diadiem));
                textViewDiaChi.setText(tienIch.getChuthich());
                textViewTenQuan.setText("Tên Quán: "+tienIch.getChude());
                progressDialog.dismiss();

            }


        }, 3000);


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // khi app kết nối yêu cầu lấy loacation hiện tại
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000); // khoảng thời gian request
        locationRequest.setFastestInterval(1000);
        // độ ưu tiên cân bằng với dung lượng pin
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
