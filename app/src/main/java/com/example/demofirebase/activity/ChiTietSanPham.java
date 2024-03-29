package com.example.demofirebase.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.demofirebase.R;
import com.example.demofirebase.model.Giohang;
import com.example.demofirebase.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarctsp;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;

    int id = 0;
    String TenChitiet = "";
    int GiaChitiet = 0;
    String HinhanhChitiet = "";
    String MotaChitiet = "";
    int Idsanpham = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        Anhxa();
        //làm nút quay lại trang trước
        //ActionToolbar();
        //lấy dữ liệu của các màn hình khác truyền qua
        GetInformation();
        //Giới hạn đặt mỗi sản phẩm là 5
        CatchEventSpinner();
        //bắt sự kiện cho nút đặt mua
        EventButton();


    }

    //thêm giỏ hàng vào thanh toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //bắt sự kiện cho icon giỏ hàng
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.manggiohang.size() > 0)
                {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;

                    for(int i = 0; i<MainActivity.manggiohang.size(); i++)
                    {
                        if(MainActivity.manggiohang.get(i).getIdsp() == id)
                        {
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if(MainActivity.manggiohang.get(i).getSoluongsp() >= 5)
                            {
                                MainActivity.manggiohang.get(i).setSoluongsp(10);

                            }
                            MainActivity.manggiohang.get(i).setGiasp(GiaChitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if(exists == false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        //tính tổng tiền
                        long Giamoi = soluong * GiaChitiet;
                        MainActivity.manggiohang.add(new Giohang(id, TenChitiet, Giamoi, HinhanhChitiet, soluong));

                    }

                }
                else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    //tính tổng tiền
                    long Giamoi = soluong * GiaChitiet;
                    MainActivity.manggiohang.add(new Giohang(id, TenChitiet, Giamoi, HinhanhChitiet, soluong));

                }
               Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5};//cho phép thêm 5 sản phẩm
        ArrayAdapter<Integer> arrayadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayadapter);
    }

    private void GetInformation() {


        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        TenChitiet = sanpham.getTensanpham();
        GiaChitiet = sanpham.getGiasanpham();
        HinhanhChitiet = sanpham.getHinhanhsanpham();
        MotaChitiet = sanpham.getMotasanpham();
        Idsanpham = sanpham.getIDSanpham();
        txtten.setText(TenChitiet);
        DecimalFormat decimalFormat = new DecimalFormat("##,###,###");
        txtgia.setText("Giá: " + decimalFormat.format(GiaChitiet) + "Đ");
        txtmota.setText(MotaChitiet);
        Picasso.get().load(HinhanhChitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgChitiet);

    }

//    private void ActionToolbar() {
//        setSupportActionBar(toolbarctsp);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbarctsp.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

    private void Anhxa() {
        toolbarctsp = (Toolbar) findViewById(R.id.toolbarchitietsanpham);
        imgChitiet = (ImageView) findViewById(R.id.imageviewchitietsanpham);
        txtten = (TextView) findViewById(R.id.textviewtenchitietsanpham);
        txtgia = (TextView) findViewById(R.id.textviewgiachitietsanpham);
        txtmota = (TextView) findViewById(R.id.textviewmotachitietsanpham);
        btndatmua = (Button) findViewById(R.id.buttondatmua);
        spinner = (Spinner) findViewById(R.id.spinner);
    }

}