package com.sujeet.filemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sujeet.filemanager.R;
import com.sujeet.filemanager.model.FileModel;

import java.util.List;

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileRecyclerAdapter.FileViewHolder> {

    private Context mContext;

    private List<FileModel> mFileModelList;

    public List<FileModel> getmFileModelList() {
        return mFileModelList;
    }

    /**
     * Set the file list and notify the data changes
     *
     * @param mFileModelList
     */
    public void setFileModelList(List<FileModel> mFileModelList) {
        this.mFileModelList = mFileModelList;
        notifyDataSetChanged();
    }

    public FileRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_folder_info, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
            holder.setUi(mFileModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFileModelList != null ? mFileModelList.size() : 0;
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIconImageView;
        private TextView mInfoTextView;
        public FileViewHolder(View itemView) {
            super(itemView);
            mIconImageView=(ImageView)itemView.findViewById(R.id.folder_imageview);
            mInfoTextView=(TextView)itemView.findViewById(R.id.info_text_view);
        }

        public void setUi(FileModel fileModel){


        }
    }
}
