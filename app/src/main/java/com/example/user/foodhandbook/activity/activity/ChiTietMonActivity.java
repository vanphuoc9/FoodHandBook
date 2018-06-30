package com.example.user.foodhandbook.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.foodhandbook.R;
import com.example.user.foodhandbook.activity.adapter.MenuAdapter;
import com.example.user.foodhandbook.activity.model.Menu;
import com.example.user.foodhandbook.activity.model.MonAn;
import com.example.user.foodhandbook.activity.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChiTietMonActivity extends AppCompatActivity {
    private ImageView imageMon;
    private TextView txtTenMon, txtMoTa, txtNguyenLieu, txtCachLam;
    Toolbar toolbarchitiet;
    DrawerLayout drawlayoutchitiet;
    ArrayList<Menu> arrayMenu;
    MenuAdapter adapterMenu;
    ListView listviewChitiet;
    //private int idmon;
    MonAn monan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon);
        // kiểm tra kết nối
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            anhxa();
            GetData();
            Actionbar();
            CatchChonItemListView();

        }else{
            CheckConnection.ShowToast(getApplicationContext(),"Vui lòng kiểm tra kết nối");
        }

    }

    private void CatchChonItemListView() {
        listviewChitiet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(ChiTietMonActivity.this, MainActivity.class);
                        startActivity(intent);
                        // Đóng drawer
                        drawlayoutchitiet.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intentTT = new Intent(ChiTietMonActivity.this, DanhMucActivity.class);
                        startActivity(intentTT);
                        drawlayoutchitiet.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        Intent intentVideo = new Intent(ChiTietMonActivity.this, VideoActivity.class);
                        intentVideo.putExtra("idmon",monan.getId());
                        startActivity(intentVideo);
                        drawlayoutchitiet.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        Intent intentDD = new Intent(ChiTietMonActivity.this, MapsActivity.class);
                        intentDD.putExtra("idmon",monan.getId());
                        startActivity(intentDD);
                        drawlayoutchitiet.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void Actionbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        // click mở thanh menu
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mở ra nhảnh ra giữa
                drawlayoutchitiet.openDrawer(GravityCompat.START);
            }
        });
    }

    private void GetData() {
        // nhận dữ liệu oject gửi qua
        monan = (MonAn) getIntent().getSerializableExtra("thongtinmon");
        // gán dữ liệu lên layout
        Picasso.with(getApplicationContext()).load(monan.getHinhmon())
                .placeholder(R.drawable.no_image_available)
                .error(R.drawable.err)
                .into(imageMon);
        txtTenMon.setText(monan.getTenmon());
        txtMoTa.setText(monan.getMotamon());
        txtNguyenLieu.setText(monan.getNguyenlieu());
        txtCachLam.setText(monan.getCachlam());

    }

    private void anhxa() {
        imageMon = (ImageView) findViewById(R.id.imgCTHinhmon);
        txtTenMon = (TextView) findViewById(R.id.txtCTtenmon);
        txtMoTa = (TextView) findViewById(R.id.txtCTmota);
        txtNguyenLieu = (TextView) findViewById(R.id.txtCTnguyenlieu);
        txtCachLam = (TextView) findViewById(R.id.txtCTcachlam);
        toolbarchitiet = (Toolbar) findViewById(R.id.toolbarchitiet);
        drawlayoutchitiet = (DrawerLayout) findViewById(R.id.drawerlayoutchitiet);
        listviewChitiet = (ListView) findViewById(R.id.listviewchitietmon);

        arrayMenu = new ArrayList<>();
        arrayMenu.add(0,new Menu("Trang chủ",R.drawable.home));
        arrayMenu.add(1,new Menu("Danh mục",R.drawable.search));
        arrayMenu.add(2,new Menu("Xem video hướng dẫn",R.drawable.video));
        arrayMenu.add(3,new Menu("Địa điểm nổi tiếng",R.drawable.location_food));


        // đưa dữ liệu vào adapter
        adapterMenu = new MenuAdapter(arrayMenu,getApplicationContext());
        // đổi các adapter vào listview
        listviewChitiet.setAdapter(adapterMenu);


    }
}
