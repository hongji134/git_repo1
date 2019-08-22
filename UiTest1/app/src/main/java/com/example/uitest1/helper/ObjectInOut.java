package com.example.uitest1.helper;

import android.util.Log;

import com.example.uitest1.member.Member;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectInOut {
    private static ObjectInOut instance = null;

    public static ObjectInOut getInstance() {
        if(instance == null) instance = new ObjectInOut();

        return instance;
    }
    public static  void freeInstance() {
        instance = null;
    }
    private ObjectInOut() {

    }
    public boolean write(String fname, List<Member> list) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean result = false;
        try {
            fos = new FileOutputStream(fname);
            oos = new ObjectOutputStream(fos);

            oos.writeInt(list.size());

            for(int i=0; i<list.size(); i++) {
                Member member = list.get(i);
                oos.writeObject(member);
                oos.flush();
            }
            result = true;
            Log.d("[INFO]", "파일 저장 성공 >> "+fname);
        } catch (FileNotFoundException e) {
            Log.d("[ERROR]", "저장 경로를 찾을 수 없음 >> "+fname);
            // e.printStackTrace();
        } catch (IOException e) {
            Log.d("[ERROR]", "파일 저장 실패 >> "+fname);
            // e.printStackTrace();
        } catch (Exception e) {
            Log.d("[ERROR]", "알 수 없는 에러 >> "+fname);
            // e.printStackTrace();
        } finally {
            try {
                if(oos != null) oos.close();
                if(fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<Member> read(String fname) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<Member> list = new ArrayList<>();

        try {
            fis = new FileInputStream(fname);
            ois = new ObjectInputStream(fis);

            int totalSize = ois.readInt();
            for(int i=0; i<totalSize; i++) {
                Member member = (Member) ois.readObject();
                list.add(member);
            }
            Log.d("[INFO]", "파일 읽기 성공 >> "+fname);
        } catch (FileNotFoundException e) {
            Log.d("[ERROR]", "저장 경로를 찾을 수 없음 >> "+fname);
            // e.printStackTrace();
        } catch (IOException e) {
            Log.d("[ERROR]", "파일 읽기 실패 >> "+fname);
            // e.printStackTrace();
        } catch (Exception e) {
            Log.d("[ERROR]", "알 수 없는 에러 >> "+fname);
            // e.printStackTrace();
        } finally {
            try {
                if(ois != null) ois.close();
                if(fis != null) fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
