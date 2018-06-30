package com.example.user.foodhandbook.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.foodhandbook.R;
import com.example.user.foodhandbook.activity.adapter.DanhSachMonAnAdapter;
import com.example.user.foodhandbook.activity.model.MonAn;
import com.example.user.foodhandbook.activity.ultil.CheckConnection;
import com.example.user.foodhandbook.activity.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DanhSachMonAnActivity extends AppCompatActivity {
    int idloai = 0;
    // khởi tạo page bằng 1 load lần 1
    int page = 1;
    // khởi tạo các thuộc tính
    Toolbar toolbar;
    ArrayList<MonAn> arrayMonAn;
    ListView listViewMonAn;
    DanhSachMonAnAdapter danhSachMonAnAdapter;
    // thuộc tính của progressbar
    View footerview;
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_mon_an);
        // nếu có kết nối
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            AnhXa();
            GetIdLoai();
            ActionBar();
            GetData(page);
            LoadMoreData();
        } else {
            CheckConnection.ShowToast(getApplicationContext(), "Bạn vui lòng kiểm tra kết nối");
        }

    }

    private void LoadMoreData() {
        // bắt sự kiện click vào từng Item
        listViewMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietMonActivity.class);
                intent.putExtra("thongtinmon",arrayMonAn.get(position));
                startActivity(intent);

            }
        });
        // bắt sự kiện kéo của listView
        listViewMonAn.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // vuốt listView đến vị trí nào đó trả vào trong function này
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // vuốt listView trả vào trong function này
                // hàm if để bắt giá trị cuối trong
                //lần đầu tiên run lên thì totalItenCount = 0 nên đặt total khác không để không bị nhảy vào function if liền
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false) {
                    // nhảy vào function thì đang load dữ liệu
                    isLoading = true;
                    // Thực hiện cho Theard hoạt động
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }
    // phương thức GetData(int page) dùng để đưa idloai lên server
    // sau đó lấy dữ liệu(là thông tin món ăn) về theo idloai
    // biến page giúp lấy đối tượng theo từng trang
    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongan = Server.Duongdandanhsachmontheoloai + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongan, new Response.Listener<String>() {
            // dùng phương thức này để lấy dữ liệu về
            @Override
            public void onResponse(String response) {
                // tạo biến hứng giá trị
                int id = 0;
                String tenmon = "";
                String hinhmon = "";
                String motamon = "";
                String nguyenlieu = "";
                String cachlam = "";
                int idloai = 0;
                if (response != null&& response.length() != 2) {
                    // có dữ liệu trả về thì thanh progressBar tắt đi
                    listViewMonAn.removeFooterView(footerview);
                    try {
                        JSONArray jsonArr = new JSONArray(response);
                        // lấy từng object của Array
                        for (int i = 0; i < jsonArr.length(); i++) {
                            // lấy từng values của từng object
                            JSONObject jsonObject = jsonArr.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenmon = jsonObject.getString("tenmon");
                            hinhmon = jsonObject.getString("hinhmon");
                            motamon = jsonObject.getString("motamon");
                            nguyenlieu = jsonObject.getString("nguyenlieu");
                            cachlam = jsonObject.getString("cachlam");
                            // thêm dữ liệu vào mảng
                            arrayMonAn.add(new MonAn(id, tenmon, hinhmon, motamon, nguyenlieu, cachlam, idloai));
                            // cập nhật Adapter
                            danhSachMonAnAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    // bắt sự kiện hết dữ liệu
                    limitData = true;
                    listViewMonAn.removeFooterView(footerview);

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
                param.put("idloai", String.valueOf(idloai));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    // ánh xạ và khởi tạo
    private void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDanhSachMonAn);
        listViewMonAn = (ListView) findViewById(R.id.listviewDSMonAn);
        arrayMonAn = new ArrayList<>();
        danhSachMonAnAdapter = new DanhSachMonAnAdapter(arrayMonAn,getApplicationContext());
        listViewMonAn.setAdapter(danhSachMonAnAdapter);
        // gán layout cho progressbar
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar, null);
        // khởi tạo handler
        mHandler = new mHandler();
    }
    // action bar
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    // get id loại
    private void GetIdLoai() {
        idloai = getIntent().getIntExtra("idloai", -1);
        // Toast.makeText(getApplicationContext(),idloai+"", Toast.LENGTH_SHORT).show();
        // Log.d("IDloaisp", idloai + "");
    }
    // Handler là ông chủ của các Theard
    // Handler cấp công việc cho từng Theard để thực hiện
    // Handler dùng thư viện android.os
    public class mHandler extends Handler {
        // function dùng để quản lý những Theard gửi lên
        @Override
        public void handleMessage(Message msg) {
            // những giá trị gửi lên thông qua msg
            switch (msg.what){
                case 0:
                    // Theard gửi lên biến = 0 thì add progessbar vào lisview
                    listViewMonAn.addFooterView(footerview);
                    break;
                case 1:
                    // Theard gửi lên = 1 thì cập nhật đổ dữ liệu lên
                    // page + 1 trước rồi mới thực hiên function
                    GetData(++page);
                    // trả về trạng thái chưa load dữ liệu vì đã load xong rồi
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    // Các nhân viên là các dạng luồng (Threard)
    public  class ThreadData extends Thread{
        // để cho thực hiện các luồng gọi run
        @Override
        public void run() {
            // gửi tin nhắn = 0  trước
            mHandler.sendEmptyMessage(0);
            try {
                // cho nó ngủ 1s
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // sau 3s gửi tiếp tin nhắn
            // obtainMessage liên kết các Theard và Handler
            // muốn liên kết tiếp tục thì gọi obtainMessage
            // gửi tiếp cho Handler giá trị 1
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
    // khởi tạo menu search
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // khởi tạo menu
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    // bắt sự kiện cho search
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSearch:
                Intent intentDM = new Intent(DanhSachMonAnActivity.this, DanhMucActivity.class);
                startActivity(intentDM);
        }
        return super.onOptionsItemSelected(item);
    }
}