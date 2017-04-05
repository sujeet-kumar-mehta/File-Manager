package com.sujeet.filemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sujeet.filemanager.adapter.FileRecyclerAdapter;
import com.sujeet.filemanager.interfaces.OnItemCLickListener;
import com.sujeet.filemanager.model.FileModel;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnItemCLickListener {

    private static final String SD_CARD_ROOT_DIRECTORY = "/sdcard/", FILE_SELECTED = "fileSelected";

    private FloatingActionButton mAddFloatingActionButton;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    private File mCurrentDirectory;
    private FileRecyclerAdapter mFileRecyclerAdapter;
    private FileFilter mFileFilter;
    private File mFileSelected;
    private ArrayList<String> mStringExtensionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initialization();
    }

    //initialize UI
    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAddFloatingActionButton = (FloatingActionButton) findViewById(R.id.addfab);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        setSupportActionBar(mToolbar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mFileRecyclerAdapter = new FileRecyclerAdapter(this, this);
        mRecyclerView.setAdapter(mFileRecyclerAdapter);
        mAddFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewFolderDailog();
            }
        });
    }


    private void initialization() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getStringArrayList("filterFileExtension") != null) {
                mStringExtensionList = extras.getStringArrayList("filterFileExtension");
                mFileFilter = new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return ((pathname.isDirectory()) || (pathname.getName().contains(".") ? mStringExtensionList.contains(pathname.getName().substring(pathname.getName().lastIndexOf("."))) : false));
                    }
                };
            }
        }

        mCurrentDirectory = new File(SD_CARD_ROOT_DIRECTORY);
        readDirectory(mCurrentDirectory);
    }

    /**
     * read all the given directory
     *
     * @param currentDirectory
     */
    private void readDirectory(File currentDirectory) {
        File[] directories = null;
        if (mFileFilter != null)
            directories = currentDirectory.listFiles(mFileFilter);
        else
            directories = currentDirectory.listFiles();

        List<FileModel> directoryList = new ArrayList<>();
        List<FileModel> fileModelList = new ArrayList<>();

        mToolbar.setSubtitle(mCurrentDirectory.getAbsolutePath());

        try {
            for (File ff : directories) {
                if (ff.isDirectory() && !ff.isHidden())
                    directoryList.add(new FileModel(ff.getName(), getString(R.string.folder), ff
                            .getAbsolutePath(), true, false));
                else {
                    if (!ff.isHidden())
                        fileModelList.add(new FileModel(ff.getName(), getString(R.string.fileSize) + ": "
                                + ff.length(), ff.getAbsolutePath(), false, false));
                }
            }
        } catch (Exception e) {

        }
        directoryList.addAll(fileModelList);

        if (!currentDirectory.getName().equalsIgnoreCase("sdcard")) {

            mToolbar.setNavigationIcon(R.drawable.ic_stat_arrow_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goBack();
                }
            });
        } else {
            mToolbar.setNavigationIcon(R.drawable.ic_folder_open);
        }

        mFileRecyclerAdapter.setFileModelList(directoryList);


    }


    @Override
    public void onItemClick(View view, FileModel fileModel) {
        if (fileModel.isFolder() || fileModel.isParent()) {
            mCurrentDirectory = new File(fileModel.getPath());
            readDirectory(mCurrentDirectory);

        } else {
            mFileSelected = new File(fileModel.getPath());
            Toast.makeText(getApplicationContext(), "Cannot open!", Toast.LENGTH_SHORT).show();
        }
    }


    private void createNewFolderDailog() {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View view = layoutInflater.inflate(R.layout.view_input, null);
        builder.setView(view);
        builder.setTitle(getString(R.string.new_folder_at, mCurrentDirectory.getName()));
        final EditText folderNameEditText = (EditText) view.findViewById(R.id.folder_name_edit_text);
//        folderNameEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                builder.setTitle(mCurrentDirectory.getName()+"/"+charSequence);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String folderName = folderNameEditText.getText().toString();
                if (!TextUtils.isEmpty(folderName))
                    createNewFolder(folderName);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void createNewFolder(String folderName) {
        File dir = new File(mCurrentDirectory.getAbsolutePath()
                + "/" + folderName);

        if (!dir.isDirectory()) {
            dir.mkdirs();
            readDirectory(mCurrentDirectory);
        }
    }

    @Override
    public void onBackPressed() {
        if (!mCurrentDirectory.getName().equalsIgnoreCase("sdcard")) {
            goBack();
        } else
            super.onBackPressed();

    }

    private void goBack() {
        mCurrentDirectory = new File(mCurrentDirectory.getParent());
        readDirectory(mCurrentDirectory);
    }
}
