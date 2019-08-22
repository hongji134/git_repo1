package com.example.uitest1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.uitest1.adapter.MemberAdapter;
import com.example.uitest1.member.Member;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    ListView listView;
    List<Member> list;
    MemberAdapter adapter;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

       button = findViewById(R.id.button);

        list = new ArrayList<>();
        adapter = new MemberAdapter(this, R.layout.list_item, list);

        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        ArrayList<Member> item_list = (ArrayList<Member>) getIntent().getSerializableExtra("list");

        for(int i=0; i<item_list.size(); i++) {
            String name = item_list.get(i).getName();
            String email = item_list.get(i).getEmail();
            String tel = item_list.get(i).getTel();
            String addr = item_list.get(i).getAddr();
            String filePath = item_list.get(i).getFilePath();
            adapter.add(new Member(name, email, tel, addr, filePath));
        }

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
