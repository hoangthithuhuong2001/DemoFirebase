package com.example.demofirebase.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.demofirebase.R;
import com.example.demofirebase.adapter.GiohangAdapter;

import java.text.DecimalFormat;

public class GiohangActivity extends AppCompatActivity {

    Toolbar toolbargh;
    ListView lvgiohang;
    TextView txtthongbao, txttongtien;
    Button btnthanhtoan, btntieptucmua;
    GiohangAdapter giohangAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        Anhxa();
        CheckData();
        EvenUltil();
    }

    private void EvenUltil() {
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

    private void ActionToolbar() {
        setSupportActionBar(toolbargh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

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