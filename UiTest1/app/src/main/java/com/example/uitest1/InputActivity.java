package com.example.uitest1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uitest1.helper.FileUtils;
import com.example.uitest1.helper.PhotoHelper;
import com.example.uitest1.helper.RegexHelper;
import com.example.uitest1.member.Member;

import java.io.File;
import java.util.ArrayList;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText1, editText2, editText3, editText4;
    Button button1, button2, button3;
    ArrayList<Member> list;
    String filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        list = new ArrayList<>();

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        permissionCheck();
    }
    private void permissionCheck() {
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                showListDialog();
            break;
            case R.id.button2:
                addList();
                getIntent().putExtra("list", list);
                setResult(RESULT_OK, getIntent());
            break;
            case R.id.button3:
                finish();
                break;
        }
    }

    private void showListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"새로 촬영하기", "갤러리에서 가져오기"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        String photoPath = PhotoHelper.getInstance().getNewPhotoPath();
                        File file = new File(photoPath);
                        Uri uri;

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            uri = FileProvider.getUriForFile(getApplicationContext(),
                                    getApplicationContext().getPackageName() + ".fileprovider", file);
                            cameraIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } else {
                            uri = Uri.fromFile(file);
                        }
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(cameraIntent, 100);
                        break;
                    case 1:
                        Intent galleryIntent = null;
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                            galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        } else {
                            galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        }
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        }
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, 101);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addList() {
        String name = editText1.getText().toString().trim();
        String tel = editText2.getText().toString().trim();
        String email = editText3.getText().toString().trim();
        String addr = editText4.getText().toString().trim();

        RegexHelper regexHelper = RegexHelper.getInstance();
        String msg = null;

        if(msg == null && !regexHelper.isValue(name)) {
            msg = "이름을 입력하세요";
        } else if(msg == null && !regexHelper.isValue(tel)) {
            msg = "전화번호를 입력하세요";
        } else if(msg == null && !regexHelper.isCellPhone(tel)) {
            msg = "전화번호가 형식에 맞지 않습니다.";
        } else if(msg == null && !regexHelper.isValue(email)) {
            msg = "이메일을을 입력하세요";
        } else if(msg == null && !regexHelper.isEmail(email)) {
            msg = "이메일이 형식에 맞지 않습니다.";
        } else if(msg==null && !regexHelper.isValue(addr)) {
            msg = "주소를 입력하세요";
        }

        if(msg != null) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        Member member = new Member(name, tel, email, addr, filePath);
        list.add(member);

        editText1.setText("");
        editText2.setText("");
        editText3.setText("");
        editText4.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 100:
                Intent photoIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://"+filePath));
                sendBroadcast(photoIntent);
                break;
            case 101:
                Uri photoUri = data.getData();
                filePath = FileUtils.getPath(this,photoUri);
                break;
        }
    }
}
