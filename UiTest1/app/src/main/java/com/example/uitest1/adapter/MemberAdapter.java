package com.example.uitest1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uitest1.R;
import com.example.uitest1.member.Member;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.List;

public class MemberAdapter extends ArrayAdapter<Member> {

    Activity activity;
    int resource;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public MemberAdapter(Context context, int resource, List<Member> objects) {
        super(context, resource, objects);

        activity = (Activity) context;
        this.resource = resource;

        imageLoaderInit();
    }

    private void imageLoaderInit() {
        imageLoader = ImageLoader.getInstance();
        if(!imageLoader.isInited()) {
            ImageLoaderConfiguration configuration =
                    ImageLoaderConfiguration.createDefault(activity);
            imageLoader.init(configuration);
        }
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.showImageOnLoading(R.drawable.ic_stub);
        builder.showImageForEmptyUri(R.drawable.ic_empty);
        builder.showImageOnFail(R.drawable.ic_error);
        builder.cacheInMemory(true);
        builder.cacheOnDisk(true);
        options = builder.build();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }
        Member item = getItem(position);
        if (item != null) {
            TextView textView1 = convertView.findViewById(R.id.textView1);
            TextView textView2 = convertView.findViewById(R.id.textView2);
            TextView textView3 = convertView.findViewById(R.id.textView3);
            TextView textView4 = convertView.findViewById(R.id.textView4);
            ImageView imageView = convertView.findViewById(R.id.imageView);

            imageLoader.displayImage("file://"+item.getFilePath(), imageView, options);
            textView1.setText(item.getName());
            textView2.setText(item.getTel());
            textView3.setText(item.getEmail());
            textView4.setText(item.getAddr());
        }
        return convertView;
    }
}
