package com.deedat.nkrumahistcircle.finances;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.deedat.nkrumahistcircle.MainActivity;
import com.deedat.nkrumahistcircle.R;
import com.deedat.nkrumahistcircle.pageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FinanceManagement extends AppCompatActivity {
    private RecyclerView mrecyclerview;
    private RecyclerView mrecyclerview2;
    private RecyclerView.Adapter madapter;
    private RecyclerView.Adapter madapter2;
    private RecyclerView.LayoutManager mlayoutmanager;
    private RecyclerView.LayoutManager mlayoutmanager2;

    ArrayList<money> moneylist= new ArrayList<>();
    ArrayList<money> moneylist2= new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("expenses");
    DatabaseReference myRef2 = database2.getReference("incomes");
    ProgressBar ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_management);
        ViewPager pager=(ViewPager) findViewById(R.id.pager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tablayout);
        TabItem tabItem1=findViewById(R.id.tabhome);
        TabItem tabItem2=findViewById(R.id.tabchat);

        tabs.setupWithViewPager(pager);


        FinancePageAdapter MpageAdapter = new FinancePageAdapter(getSupportFragmentManager(),2);


        pager.setAdapter(MpageAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        Toolbar toolbar=findViewById(R.id.toolbar);

        toolbar.setTitle(" Finances");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

backicon();
          }
      });

        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String title=(String) menuItem.getTitle();
                Toast.makeText(FinanceManagement.this,title + " selected",Toast.LENGTH_SHORT).show();
                switch (menuItem.getItemId()){
                    case R.id.item1:


                        break;

                    case R.id.item2:


                        break;

                }
                return false;
            }
        });












    }
@Override
protected  void onStart(){
        super.onStart();

        fetchexpensedata();

fetchincomedata();
}


    public void backicon(){

finish();
    }

    public void fetchexpensedata(){



        myRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                moneylist.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    money finances=snapshot.getValue(money.class);

                  moneylist.add(finances);

                }



                mrecyclerview=findViewById(R.id.recyclerView);
                mrecyclerview.setHasFixedSize(true);
                mlayoutmanager=new LinearLayoutManager(FinanceManagement.this);
               madapter=new itemsAdapter(moneylist);
                mrecyclerview.setLayoutManager(mlayoutmanager);
               mrecyclerview.setAdapter(madapter);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
Toast.makeText(FinanceManagement.this,"there was an error",Toast.LENGTH_SHORT).show();
            }

        });
    }


    public void fetchincomedata(){



        myRef2.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                moneylist2.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    money finances=snapshot.getValue(money.class);

                    moneylist2.add(finances);

                }



                mrecyclerview2=findViewById(R.id.recyclerView2);
                mrecyclerview2.setHasFixedSize(true);
                mlayoutmanager2=new LinearLayoutManager(FinanceManagement.this);
                madapter2=new itemsAdapter(moneylist2);
                mrecyclerview2.setLayoutManager(mlayoutmanager2);
                mrecyclerview2.setAdapter(madapter2);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FinanceManagement.this,"there was an error",Toast.LENGTH_SHORT).show();
            }

        });
    }




}
