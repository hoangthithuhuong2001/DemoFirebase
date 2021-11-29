package com.example.demofirebase.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.demofirebase.R;
import com.example.demofirebase.adapter.GiohangAdapter;
import com.example.demofirebase.ultil.CheckConnection;

import java.text.DecimalFormat;

public class GiohangActivity extends AppCompatActivity {

    Toolbar toolbargh;
    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan, btntieptucmua;
    GiohangAdapter giohangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        Anhxa();
        CheckData();
        EvenUltil();
        //khi chọn vào sp trong giỏ hàng sẽ hiện ra thống báo bạn muốn xóa hay không
        CatchOnItemListView();
        //bắt sự kiện cho nút btnthanhtoan và btntieptucmua
        EventButton();

    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(MainActivity.manggiohang.size() > 0){
                   Intent intent = new Intent(getApplicationContext(), Thongtinkhachhang.class);
                   startActivity(intent);
               }
               else{
                   CheckConnection.ShowToast_Short(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm");

               }
            }
        });
    }

    private void CatchOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                //hàm gọi hộp thông báo có muốn xóa sản phẩm không
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (MainActivity.manggiohang.size() <= 0)
                        {
                            txtthongbao.setVisibility(view.VISIBLE);
                        }
                        else{
                            MainActivity.manggiohang.remove(position);
                            //cập nhật lại giỏ hàng
                            giohangAdapter.notifyDataSetChanged();
                            //cập nhật lại số tiền
                            EvenUltil();
                            if(MainActivity.manggiohang.size() <=0)
                            {
                                txtthongbao.setVisibility(view.VISIBLE);
                            }else{
                                txtthongbao.setVisibility(view.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EvenUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       giohangAdapter.notifyDataSetChanged();
                       EvenUltil();
                    }
                });
                builder.show();

                return true;
            }
        });
    }

    public static void EvenUltil() {
        long tongtien = 0;
        for(int  i = 0; i<MainActivity.manggiohang.size(); i++)
        {
            tongtien += MainActivity.manggiohang.get(i).getGiasp();

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "Đ");
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size() <=0 ){
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }
        else{
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

//    private void ActionToolbar() {
//        setSupportActionBar(toolbargh);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbargh.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

    private void Anhxa() {
        toolbargh = (Toolbar) findViewById(R.id.toolbargiohang);
        lvgiohang = (ListView) findViewById(R.id.listviewgiohang);
        txtthongbao = (TextView) findViewById(R.id.textviewthongbao);
        txttongtien = (TextView) findViewById(R.id.textviewtongtien);
        btnthanhtoan = (Button) findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua = (Button) findViewById(R.id.buttontieptucmuahang);
        giohangAdapter = new GiohangAdapter(GiohangActivity.this, MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);

    }
}