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

public class TrangdiemAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraytrangdiem;

    public TrangdiemAdapter(Context context, ArrayList<Sanpham> arraytrangdiem) {
        this.context = context;
        this.arraytrangdiem = arraytrangdiem;
    }

    @Override
    public int getCount() {

        return arraytrangdiem.size();
    }

    @Override
    public Object getItem(int i) {

        return arraytrangdiem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txttentrangdiem, txtgiatrangdiem, txtmotatrangdiem;
        public ImageView imgtrangdiem;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_trangdiem, null);
            viewHolder.txttentrangdiem = (TextView) view.findViewById(R.id.textviewtrangdiem);
            viewHolder.txtgiatrangdiem = (TextView) view.findViewById(R.id.textviewgiatrangdiem);
            viewHolder.txtmotatrangdiem = (TextView) view.findViewById(R.id.textviewmotatrangdiem);
            viewHolder.imgtrangdiem = (ImageView) view.findViewById(R.id.imageviewtrangdiem);
            view.setTag(viewHolder);


        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttentrangdiem.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiatrangdiem.setText("Giá : " + decimalFormat.format(sanpham.getGiasanpham()) + "Đ");
        viewHolder.txtmotatrangdiem.setMaxLines(2);
        viewHolder.txtmotatrangdiem.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotatrangdiem.setText(sanpham.getMotasanpham());
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgtrangdiem);
        return view;
    }
}
