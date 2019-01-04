package com.deedat.nkrumahistcircle;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.deedat.nkrumahistcircle.finances.AddActivity;
import com.deedat.nkrumahistcircle.finances.FinanceManagement;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
FirebaseAuth auth;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager  pager=(ViewPager) findViewById(R.id.pager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tablayout);
        TabItem tabItem1=findViewById(R.id.tabhome);
        TabItem tabItem2=findViewById(R.id.tabchat);
        TabItem tabItem3=findViewById(R.id.news);
      tabs.setupWithViewPager(pager);


        pageAdapter MpageAdapter = new pageAdapter(getSupportFragmentManager(),3);


        pager.setAdapter(MpageAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));


auth=FirebaseAuth.getInstance();

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Home");

       // toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.item3:




                        break;


                    case R.id.item2:


                        break;
                    case R.id.signout:
                        signout();
                }
                return false;
            }
        });
    }

    public void nkfinances(){
        Intent intent= new Intent(this,FinanceManagement.class);
        startActivity(intent);


    }


    public void signout(){
        auth.signOut();


    }

}
