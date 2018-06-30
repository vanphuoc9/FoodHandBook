package com.example.user.foodhandbook.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class DanhMucActivity extends AppCompatActivity {
    EditText editTextSearch;
    ListView listViewDS;
    ArrayList<MonAn> monAnArrayList;
    ArrayList<String> listTenMon;
    ArrayList<MonAn> newMonAn;
    DanhSachMonAnAdapter danhMucAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            anhxa();
            GetMonAn();
           CatchChonItemListView();
            CatchChonItemSearch();
        }else{
            CheckConnection.ShowToast(getApplicationContext(),"vui lòng kiểm tra kết nối");
        }



    }
    // bắt sự kiện tìm kiếm
    private void CatchChonItemSearch() {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // đang thay đổi ký tự thì bắt sự kiện
                // nếu chuỗi khác rỗng
                if(s != null && !s.toString().isEmpty()){
                    // khởi tạo
                    newMonAn = new ArrayList<>();
                    listTenMon = new ArrayList<>();
                    // lấy tên món gán vào listten
                    for(int i = 0; i < monAnArrayList.size(); i++){
                        listTenMon.add(monAnArrayList.get(i).getTenmon());
                    }
                    // đổi kiêu chữ hoa
                    s = s.toString().toUpperCase();
                    for(String item: listTenMon){
                        // contains() tìm kiếm chuỗi ký tự trong chuỗi này.
                        // Nó trả về true nếu chuỗi các giá trị char được tìm thấy trong chuỗi này, nếu không trả về false.
                        if(item.toUpperCase().contains(s)){
                            // dò trong mảng nếu có tên trùng với item thì cho vào mảng newMonan
                            for(int i = 0; i < monAnArrayList.size(); i++){
                                if(monAnArrayList.get(i).getTenmon() == item)
                                    newMonAn.add(monAnArrayList.get(i));
                            }
                        }
                    }
                    // set mảng món ăn vào listview
                    danhMucAdapter = new DanhSachMonAnAdapter(newMonAn,getApplicationContext());
                    listViewDS.setAdapter(danhMucAdapter);
                    // bắt sự kiện chon ListView
                    listViewDS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(),ChiTietMonActivity.class);
                            intent.putExtra("thongtinmon",newMonAn.get(position));
                            startActivity(intent);
                        }
                    });
                }else{
                    // nếu chuỗi rỗng thì lấy mảng danh sách món ăn
                    danhMucAdapter = new DanhSachMonAnAdapter(monAnArrayList,getApplicationContext());
                    listViewDS.setAdapter(danhMucAdapter);
                    // bắt sự kiện ListView
                    CatchChonItemListView();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // bắt sự kiện của listview
    private void CatchChonItemListView() {
        listViewDS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietMonActivity.class);
                intent.putExtra("thongtinmon",monAnArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    // lấy dữ liệu từ server
    private void GetMonAn() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrRequest = new JsonArrayRequest(Server.Duongdanmon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 // nếu mảng không rỗng
                if(response != null){
                    int id;
                    String tenmon;
                    String hinhmon;
                    String motamon;
                    String nguyenlieu;
                    String cachlam;
                    int idloai;
                    // lấy từng phần tử của mảng
                    for(int i =0; i< response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenmon = jsonObject.getString("tenmon");
                            hinhmon = jsonObject.getString("hinhmon");
                            motamon = jsonObject.getString("motamon");
                            nguyenlieu = jsonObject.getString("nguyenlieu");
                            cachlam = jsonObject.getString("cachlam");
                            idloai = jsonObject.getInt("idloai");
                            monAnArrayList.add(new MonAn(id,tenmon,hinhmon,motamon,nguyenlieu,cachlam,idloai));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast(getApplicationContext(),"Vui lòng kiểm tra kết nối");
            }
        });
        requestQueue.add(arrRequest);
    }

    // khởi tạo và ánh xạ
    private void anhxa() {
        editTextSearch = (EditText) findViewById(R.id.editSearch);
        listViewDS = (ListView) findViewById(R.id.listviewSearch);
        // khởi tạo mảng
        monAnArrayList = new ArrayList<>();
        // khởi tạo adapter
        danhMucAdapter = new DanhSachMonAnAdapter(monAnArrayList,getApplicationContext());
        // tạo adapter cho listview
        listViewDS.setAdapter(danhMucAdapter);
}

}
