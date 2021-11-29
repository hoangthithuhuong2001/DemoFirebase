package com.example.demofirebase.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demofirebase.R;
import com.example.demofirebase.adapter.ChamsoccotheAdapter;
import com.example.demofirebase.adapter.TrangdiemAdapter;
import com.example.demofirebase.model.Sanpham;
import com.example.demofirebase.ultil.CheckConnection;
import com.example.demofirebase.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DochamsoccotheActivity extends AppCompatActivity {

    Toolbar toolbarcsct;
    ListView lvchamsoccothe;
    ChamsoccotheAdapter chamsoccotheAdapter;
    ArrayList<Sanpham> mangchamsoccothe;

    int idchamsoccothe = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dochamsoccothe);

        Anhxa();

        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaisp();
            //ActionToolbar();
            GetData(page);
            LoadMoreData();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
            finish();
        }

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

    private void LoadMoreData() {
        lvchamsoccothe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", mangchamsoccothe.get(i));
                startActivity(intent);
            }
        });
    }
    private void GetData(int Page) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdanchamsoccothe+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tenchamsoccothe = "";
                int Giachamsoccothe = 0;
                String Hinhanhchamsoccothe = "";
                String Motachamsoccothe = "";
                int Idspchamsoccothe = 0;
                if(response != null && response.length() > 0){

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenchamsoccothe = jsonObject.getString("tensp");
                            Giachamsoccothe = jsonObject.getInt("giasp");
                            Hinhanhchamsoccothe = jsonObject.getString("hinhanhsp");
                            Motachamsoccothe = jsonObject.getString("motasp");
                            Idspchamsoccothe = jsonObject.getInt("idsanpham");
                            mangchamsoccothe.add(new Sanpham(id, Tenchamsoccothe, Giachamsoccothe, Hinhanhchamsoccothe,Motachamsoccothe, Idspchamsoccothe));
                            chamsoccotheAdapter.notifyDataSetChanged();//cập nhật lại dữ liệu

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idchamsoccothe));
                return param;
            }

        };


        requestQueue.add(stringRequest);

    }

    private void GetIdloaisp() {
        idchamsoccothe = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham", idchamsoccothe + "");
    }
//    private void ActionToolbar() {
//        setSupportActionBar(toolbarcsct);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbarcsct.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

    private void Anhxa() {
        toolbarcsct = (Toolbar) findViewById(R.id.toolbarchamsoccothe);
        lvchamsoccothe = (ListView) findViewById(R.id.listviewchamsoccothe);
        mangchamsoccothe = new ArrayList<>();
        chamsoccotheAdapter = new ChamsoccotheAdapter(getApplicationContext(), mangchamsoccothe);
        lvchamsoccothe.setAdapter(chamsoccotheAdapter);
    }
}