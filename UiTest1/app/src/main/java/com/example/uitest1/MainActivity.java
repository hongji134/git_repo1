package com.example.uitest1;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uitest1.helper.ObjectInOut;
import com.example.uitest1.member.Member;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button1, button2, button3, button4;
    ArrayList<Member> list=null;
    Member item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        list = new ArrayList<>();

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String fname = Environment.getExternalStorageDirectory().getAbsolutePath()+"/members.txt";
        Intent intent = null;

        switch (v.getId()) {
            case R.id.button1:
                intent = new Intent(this, InputActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.button2:
                intent = new Intent(this, ListActivity.class);
                intent.putExtra("list", list);
                startActivity(intent);
                break;
            case R.id.button3:
                boolean result = ObjectInOut.getInstance().write(fname, list);
                String msg = "";
                if(result) msg = "저장 성공";
                else msg = "저장 실패";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                list.clear();
                list = (ArrayList<Member>) ObjectInOut.getInstance().read(fname);
                String msg1="";
                if(list.size()>0) msg1="읽기 성공";
                else msg1="읽기 실패";
                Toast.makeText(this, msg1, Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 100:
                if (data != null) {
                    ArrayList<Member> arrayList = (ArrayList<Member>) data.getSerializableExtra("list");

                    list.addAll(arrayList);
                }
                break;
        }
    }
}
