package com.sujeet.filemanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mAddFloatingActionButton;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    //initialize UI
    private void initUI() {
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        mTabLayout=(TabLayout) findViewById(R.id.tabs);
        mAddFloatingActionButton = (FloatingActionButton) findViewById(R.id.addfab);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        setSupportActionBar(mToolbar);
    }
}
