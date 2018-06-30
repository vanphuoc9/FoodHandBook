package com.example.user.foodhandbook.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.foodhandbook.R;
import com.example.user.foodhandbook.activity.model.LoaiMonAn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 22/01/2018.
 */
public class LoaiMonAnAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiMonAn> arrLoaiMon;
    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    public LoaiMonAnAdapter(Context context, ArrayList<LoaiMonAn> arrLoaiMon) {
        this.context = context;
        this.arrLoaiMon = arrLoaiMon;
    }

    @Override
    public int getCount() {
        return arrLoaiMon.size();
    }

    @Override
    public Object getItem(int position) {
        return arrLoaiMon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  class ViewHolder{
        TextView txtloaimon;
        ImageView imgloaimon;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        // Nếu chưa có dữ liệu
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            // get service là layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listview_loaimonan,null);
            // ánh xạ tới từng dòng
            viewHolder.txtloaimon = (TextView) convertView.findViewById(R.id.textviewloaimonan);
            viewHolder.imgloaimon = (ImageView) convertView.findViewById(R.id.imageloaimonan);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        } // nếu đã có dữ liệu thì getTag
        // lấy dữ liệu từ trong mảng
        // gọi lại khuôn là menu
        LoaiMonAn loaiMonAn = (LoaiMonAn) getItem(position);
        viewHolder.txtloaimon.setText(loaiMonAn.getTenloai());
        Picasso.with(context).load(loaiMonAn.getHinhloai())
                .placeholder(R.drawable.no_image_available)
                .into(viewHolder.imgloaimon);
        return convertView;
    }

//    // giúp khởi lại layout đã khởi tạo bên ngoài
//    @Override
//    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // biến view là màn hình này
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_listview_loaimonan,null);
//        ItemHolder itemHolder = new ItemHolder(v);
//        return itemHolder;
//
//    }
//    // set và get layout gán lên
//    @Override
//    public void onBindViewHolder(ItemHolder holder, int position) {
//       // lấy dữ liệu trong mảng gán lên khuôn
//        LoaiMonAn loaiMonAn = arrLoaiMon.get(position);
//        holder.txttenloai.setText(loaiMonAn.getTenloai());
//        // dùng picasso load hình ảnh
//        Picasso.with(context).load(loaiMonAn.getHinhloai())
//                .placeholder(R.drawable.no_image_available)
//                .into(holder.imghinhloai);
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrLoaiMon.size();
//    }
//
//    public class ItemHolder extends RecyclerView.ViewHolder{
//       private ImageView imghinhloai;
//       private TextView txttenloai;
//
//       public ItemHolder(View itemView) {
//           super(itemView);
//           imghinhloai = (ImageView) itemView.findViewById(R.id.imageloaimonan);
//           txttenloai = (TextView) itemView.findViewById(R.id.textviewloaimonan);
//           // bắt sự kiện click vào Item cảu Recycler
//           itemView.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   Intent intent = new Intent(context, DanhSachMonAnActivity.class);
//                   // bắt từng vi trí trong từng Item
//                   intent.putExtra("idloai", arrLoaiMon.get(getPosition()).getIdloai());
//                   context.startActivity(intent);
//               }
//           });
//
//
//       }
//   }

}

//public class LoaiMonAnAdapter extends RecyclerView.Adapter<LoaiMonAnAdapter.ItemHolder> {
//    Context context;
//    ArrayList<LoaiMonAn> arrLoaiMon;
//    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();
//
//    public LoaiMonAnAdapter(Context context, ArrayList<LoaiMonAn> arrLoaiMon) {
//        this.context = context;
//        this.arrLoaiMon = arrLoaiMon;
//    }
//    // giúp khởi lại layout đã khởi tạo bên ngoài
//    @Override
//    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // biến view là màn hình này
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_listview_loaimonan,null);
//        ItemHolder itemHolder = new ItemHolder(v);
//        return itemHolder;
//
//    }
//    // set và get layout gán lên
//    @Override
//    public void onBindViewHolder(ItemHolder holder, int position) {
//       // lấy dữ liệu trong mảng gán lên khuôn
//        LoaiMonAn loaiMonAn = arrLoaiMon.get(position);
//        holder.txttenloai.setText(loaiMonAn.getTenloai());
//        // dùng picasso load hình ảnh
//        Picasso.with(context).load(loaiMonAn.getHinhloai())
//                .placeholder(R.drawable.no_image_available)
//                .into(holder.imghinhloai);
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrLoaiMon.size();
//    }
//
//    public class ItemHolder extends RecyclerView.ViewHolder{
//       private ImageView imghinhloai;
//       private TextView txttenloai;
//
//       public ItemHolder(View itemView) {
//           super(itemView);
//           imghinhloai = (ImageView) itemView.findViewById(R.id.imageloaimonan);
//           txttenloai = (TextView) itemView.findViewById(R.id.textviewloaimonan);
//           // bắt sự kiện click vào Item cảu Recycler
//           itemView.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   Intent intent = new Intent(context, DanhSachMonAnActivity.class);
//                   // bắt từng vi trí trong từng Item
//                   intent.putExtra("idloai", arrLoaiMon.get(getPosition()).getIdloai());
//                   context.startActivity(intent);
//               }
//           });
//
//
//       }
//   }
//
//}
