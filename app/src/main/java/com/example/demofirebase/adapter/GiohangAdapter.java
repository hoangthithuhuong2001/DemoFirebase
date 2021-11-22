package com.example.demofirebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demofirebase.R;
import com.example.demofirebase.activity.GiohangActivity;
import com.example.demofirebase.activity.MainActivity;
import com.example.demofirebase.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {

    Context context;
    ArrayList<Giohang> arraygiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int i) {
        return arraygiohang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttengiohang, txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus, btnvalues, btnplus;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if(view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang, null);
            viewHolder.txttengiohang = (TextView) view.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang = (TextView) view.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang = (ImageView) view.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = (Button) view.findViewById(R.id.buttonminus);
            viewHolder.btnvalues = (Button) view.findViewById(R.id.buttonvalues);
            viewHolder.btnplus = (Button) view.findViewById(R.id.buttonplus);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Giohang giohang = (Giohang) getItem(i);
        viewHolder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp()) + "Đ");
        Picasso.get().load(giohang.getHinhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imggiohang);
        //đưa về  dạng chuỗi
        viewHolder.btnvalues.setText(giohang.getSoluongsp() + "");

        //bắt đầu đoạn bắt sự kiện cho nút - +
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if(sl >=5){
            viewHolder.btnplus.setVisibility(view.INVISIBLE);//làm nút + mờ đi
            viewHolder.btnminus.setVisibility(view.VISIBLE);//nút - hiệ bình thường
        }
        else if(sl<= 1)
        {
            viewHolder.btnplus.setVisibility(view.VISIBLE);//làm nút + hiệ bình thường
            viewHolder.btnminus.setVisibility(view.INVISIBLE);//nút - mờ đi
        }
        else if(sl>=1)
        {
            viewHolder.btnminus.setVisibility(view.VISIBLE);
            viewHolder.btnplus.setVisibility(view.VISIBLE);

        }
        //update lại giá và số lượng ở trong khung
        ViewHolder finalViewHolder = viewHolder;

        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) +1;
                int slht = MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                //tính giá mới
                long giamoinhat = (giaht * slmoinhat)/slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + "Đ");

                GiohangActivity.EvenUltil();
                if(slmoinhat > 4){
                    finalViewHolder.btnplus.setVisibility(view.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(view.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewHolder.btnminus.setVisibility(view.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(view.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));

                }


            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) -1;
                int slht = MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht = MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                //tính giá mới
                long giamoinhat = (giaht * slmoinhat)/slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat) + "Đ");

                GiohangActivity.EvenUltil();
                if(slmoinhat < 2){
                    finalViewHolder.btnplus.setVisibility(view.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(view.INVISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }else{
                    finalViewHolder.btnminus.setVisibility(view.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(view.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoinhat));

                }

            }
        });
        //kết thúc đoạn btn- và btn+

        return view;
    }
}
