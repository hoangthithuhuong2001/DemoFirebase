package com.example.demofirebase.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demofirebase.R;
import com.example.demofirebase.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChamsoccotheAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sanpham> arraychamsoccothe;

    public ChamsoccotheAdapter(Context context, ArrayList<Sanpham> arraychamsoccothe) {
        this.context = context;
        this.arraychamsoccothe = arraychamsoccothe;
    }

    @Override
    public int getCount() {

        return arraychamsoccothe.size();
    }

    @Override
    public Object getItem(int i) {

        return arraychamsoccothe.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }
    public class ViewHolder{
        public TextView txttenchamsoccothe, txtgiachamsoccothe, txtmotachamsoccothe;
        public ImageView imgchamsoccothe;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_chamsoccothe, null);
            viewHolder.txttenchamsoccothe = (TextView) view.findViewById(R.id.textviewtenchamsoccothe);
            viewHolder.txtgiachamsoccothe = (TextView) view.findViewById(R.id.textviewgiachamsoccothe);
            viewHolder.txtmotachamsoccothe = (TextView) view.findViewById(R.id.textviewmotachamsoccothe);
            viewHolder.imgchamsoccothe = (ImageView) view.findViewById(R.id.imageviewchamsoccothe);
            view.setTag(viewHolder);


        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenchamsoccothe.setText(sanpham.getTensanpham());
        //định dạng chuỗi của giá thành 000.000.000
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiachamsoccothe.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham()) + "Đ");
        //cho phần mô tả san phẩm hiere thị 2 dòng đầu
        viewHolder.txtmotachamsoccothe.setMaxLines(2);
        viewHolder.txtmotachamsoccothe.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotachamsoccothe.setText(sanpham.getMotasanpham());
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgchamsoccothe);
        return view;
    }
}
