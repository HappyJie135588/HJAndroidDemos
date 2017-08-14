package com.huangjie.hjandroiddemos.mediarecorderdemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huangjie.hjandroiddemos.BaseActivity;
import com.huangjie.hjandroiddemos.R;
import com.huangjie.hjandroiddemos.utils.CommTools;
import com.huangjie.hjandroiddemos.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocalVideoListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public static int LOCAL_VIDEO_RESULT = 3;

    @BindView(R.id.lv_video)
    public ListView mListView;

    public List<VideoInfo> infos = new ArrayList<>();

    private Intent lastIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_video_list);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        lastIntent = getIntent();
        mListView.setOnItemClickListener(this);
        String[] mediaColumns = new String[]{MediaStore.MediaColumns.DATA, BaseColumns._ID, MediaStore.MediaColumns.TITLE, MediaStore.MediaColumns.MIME_TYPE, MediaStore.Video.VideoColumns.DURATION, MediaStore.MediaColumns.SIZE};
        Cursor   cursor       = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                VideoInfo info = new VideoInfo();
                info.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)));
                info.setMimeType(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)));
                info.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE)));
                info.setTime(CommTools.LongToHms(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION))));
                info.setSize(CommTools.LongToPoint(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))));
                int                   id      = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, options);
                info.setBitmap(bitmap);
                infos.add(info);
            } while (cursor.moveToNext());
            VideoListAdapter adapter = new VideoListAdapter();
            mListView.setAdapter(adapter);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String filePath = infos.get(position).getFilePath();
        lastIntent.setData(Uri.parse(filePath));
        setResult(LOCAL_VIDEO_RESULT, lastIntent);
        finish();
    }

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, LocalVideoListActivity.class);
        activity.startActivityForResult(intent,LOCAL_VIDEO_RESULT);
    }

    class VideoListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView =  LayoutInflater.from(UIUtils.getContext()).inflate(R.layout.item_video_list, null);
                holder.vImage = (ImageView) convertView.findViewById(R.id.video_img);
                holder.vTitle = (TextView) convertView.findViewById(R.id.video_title);
                holder.vSize = (TextView) convertView.findViewById(R.id.video_size);
                holder.vTime = (TextView) convertView.findViewById(R.id.video_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.vImage.setImageBitmap(infos.get(position).getBitmap());
            holder.vTitle.setText(infos.get(position).getTitle()); // + "." + (videoList.get(position).getMimeType()).substring(6))
            holder.vSize.setText(infos.get(position).getSize());
            holder.vTime.setText(infos.get(position).getTime());

            holder.vImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String bpath = "file://" + infos.get(position).getFilePath();
                    intent.setDataAndType(Uri.parse(bpath), "video/*");
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView vImage;
            TextView  vTitle;
            TextView  vSize;
            TextView  vTime;
        }
    }

}