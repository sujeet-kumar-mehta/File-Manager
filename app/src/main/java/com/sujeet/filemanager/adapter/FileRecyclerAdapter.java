package com.sujeet.filemanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sujeet.filemanager.R;
import com.sujeet.filemanager.interfaces.OnItemCLickListener;
import com.sujeet.filemanager.model.FileModel;

import java.util.List;

public class FileRecyclerAdapter extends RecyclerView.Adapter<FileRecyclerAdapter.FileViewHolder> {

    private Context mContext;

    private List<FileModel> mFileModelList;

    private OnItemCLickListener mOnItemCLickListener;

    /**
     * Set the file list and notify the data changes
     *
     * @param mFileModelList
     */
    public void setFileModelList(List<FileModel> mFileModelList) {
        this.mFileModelList = mFileModelList;
        notifyDataSetChanged();
    }

    public FileRecyclerAdapter(Context context, OnItemCLickListener listener) {
        this.mContext = context;
        this.mOnItemCLickListener=listener;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_folder_info, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, final int position) {
        final FileModel fileModel = mFileModelList.get(position);
        holder.setUi(fileModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemCLickListener.onItemClick(view, fileModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFileModelList != null ? mFileModelList.size() : 0;
    }

    public static class FileViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIconImageView;
        private TextView mInfoTextView,mFolderNameTextView;

        public FileViewHolder(View itemView) {
            super(itemView);
            mIconImageView = (ImageView) itemView.findViewById(R.id.folder_imageview);
            mFolderNameTextView = (TextView) itemView.findViewById(R.id.folder_name_text_view);

            mInfoTextView = (TextView) itemView.findViewById(R.id.info_text_view);
        }

        public void setUi(FileModel fileModel) {

            mFolderNameTextView.setText(fileModel.getName());
            mInfoTextView.setText(fileModel.getData());

        }
    }
}
