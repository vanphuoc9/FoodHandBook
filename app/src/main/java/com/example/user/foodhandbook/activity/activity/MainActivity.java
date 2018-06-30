package com.example.user.foodhandbook.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.foodhandbook.R;
import com.example.user.foodhandbook.activity.adapter.LoaiMonAnAdapter;
import com.example.user.foodhandbook.activity.adapter.MenuAdapter;
import com.example.user.foodhandbook.activity.model.LoaiMonAn;
import com.example.user.foodhandbook.activity.model.Menu;
import com.example.user.foodhandbook.activity.ultil.CheckConnection;
import com.example.user.foodhandbook.activity.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
   // RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Menu> arrayMenu;
    MenuAdapter adapterMenu;
    ArrayList<LoaiMonAn> arrayLoaiMon;
    LoaiMonAnAdapter adapterLoaiMon;

    GridView gridViewMonAn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        // kiểm tra kết nối Internet
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            // Tạo action bar
            Actionbar();
            // tạo quảng cáo viewFliper
            ActionViewFlipper();
            CatchChonItemListView();
            GetDataLoai();
            CatchItemClickLoai();
        }else{
            CheckConnection.ShowToast(getApplicationContext(),"Bạn hãy kiểm tra kết nối");
        }

    }

    // bắt sự kiện GridView Loại món ăn
    private void CatchItemClickLoai() {
        // bắt sự kiện
        gridViewMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DanhSachMonAnActivity.class);
                intent.putExtra("idloai", arrayLoaiMon.get(position).getIdloai());
                startActivity(intent);

            }
        });

    }
    // get dữ liệu từ server
    private void GetDataLoai() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrRequest = new JsonArrayRequest(Server.Duongdanloaimon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // nếu mảng không rỗng
                 if(response != null){
                     int id = 0;
                     String tenloai;
                     String hinhloai;
                     // lấy từ phần tử object của mảng gán vào adapter
                     for(int i = 0; i < response.length(); i++){
                         try{
                             JSONObject jsonObject = response.getJSONObject(i);
                             id = jsonObject.getInt("idloai");
                             tenloai = jsonObject.getString("tenloai");
                             hinhloai = jsonObject.getString("hinhloai");
                             arrayLoaiMon.add(new LoaiMonAn(id,tenloai,hinhloai));
                             adapterLoaiMon.notifyDataSetChanged();
                         }catch(JSONException e){
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
    // bắt sư kiện của thanh navigationbar
    private void CatchChonItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        // Đóng drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intentDM = new Intent(MainActivity.this, DanhMucActivity.class);
                        startActivity(intentDM);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        Intent intentLH = new Intent(MainActivity.this, LienHeActivity.class);
                        startActivity(intentLH);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }
            }
        });

    }
    // Tạo quảng cáo
    private void ActionViewFlipper() {
        // Tạo mảng chứa đường dẫn của hình
        ArrayList<String> arr = new ArrayList<>();
        arr.add("http://softwatergroup.com/wp-content/uploads/2016/12/ban-sac-am-thuc.jpg");
        arr.add("http://webdata.vcmedia.vn/k:webdata/100/7612f68584620140418113451/nhung-khung-hinh-tuyet-dep-ve-am-thuc-chau-a.jpg");
        arr.add("http://tcndulichhanoi.edu.vn/wp-content/uploads/2016/06/hn.jpg");
        arr.add("https://cloud.lovindublin.com/images/uploads/2016/01/_blogWide/2-Korean-food-1.jpg?mtime=20160121152458");
        for(int i = 0; i<arr.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            // load hình ảnh bằng Picasso
            Picasso.with(getApplicationContext())
                    .load(arr.get(i)) // load bằng url
                    .into(imageView); // đổ vào imageView
            // Set cho hình vừa với ViewFlipper
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            // thêm imageView vào viewFlipper
            viewFlipper.addView(imageView);
        }
        // chạy trong bao lâu
        viewFlipper.setFlipInterval(5000);
        // cho nó chạy
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        // Thêm các animation vào viewFlipper
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }
    // thanh action bar
    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        // click mở thanh menu
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mở ra nhảnh ra giữa
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    // khởi tạo và ánh xạ
    private void anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        gridViewMonAn = (GridView) findViewById(R.id.gvMonAn);
        //recyclerViewmanhinhchinh = (RecyclerView) findViewById(R.id.recyclerview);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        listViewmanhinhchinh = (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        arrayMenu = new ArrayList<>();
        arrayMenu.add(0,new Menu("Trang chủ",R.drawable.home));
        arrayMenu.add(1,new Menu("Danh mục",R.drawable.search));
        arrayMenu.add(2,new Menu("Liên hệ",R.drawable.contact));
        // đưa dữ liệu vào adapter
        adapterMenu = new MenuAdapter(arrayMenu,getApplicationContext());
        // đổi các adapter vào listview
        listViewmanhinhchinh.setAdapter(adapterMenu);

        // khai báo cho mảng loại
        arrayLoaiMon = new ArrayList<>();
        adapterLoaiMon = new LoaiMonAnAdapter(getApplicationContext(),arrayLoaiMon);
        // GView
        gridViewMonAn.setAdapter(adapterLoaiMon);

        // RecylerView
        // Fix kích thước
//        recyclerViewmanhinhchinh.setHasFixedSize(true);
//        // tạo gridlaout và 2 cột
//        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
//        // Đưa adater Loai mon vào RecyclerView
//        recyclerViewmanhinhchinh.setAdapter(adapterLoaiMon);
//        ViewCompat.setNestedScrollingEnabled(recyclerViewmanhinhchinh,false);
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
                Intent intentDM = new Intent(MainActivity.this, DanhMucActivity.class);
                startActivity(intentDM);
        }
        return super.onOptionsItemSelected(item);
    }
}
