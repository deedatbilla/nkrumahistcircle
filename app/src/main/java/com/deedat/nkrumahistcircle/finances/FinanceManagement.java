package com.deedat.nkrumahistcircle.finances;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.widgets.Snapshot;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FinanceManagement extends AppCompatActivity {
    private RecyclerView mrecyclerview;
    private RecyclerView.Adapter madapter;
    private RecyclerView.LayoutManager mlayoutmanager;
    private SwipeRefreshLayout swipeContainer;
    ArrayList<money> moneylist= new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("expenses");
    ProgressBar ProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_management);

        Toolbar toolbar=findViewById(R.id.toolbar);

        toolbar.setTitle(" Finances");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

backicon();
          }
      });
        toolbar.setElevation(10f);
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









        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //  fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click.
                add_data();
            }
        });


    }
@Override
protected  void onStart(){
        super.onStart();

        fetchdata();


}
    public void add_data(){
        Intent intent=new Intent(this,AddActivity.class);
        startActivity(intent);
    }

    public void backicon(){

finish();
    }

    public void fetchdata(){



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

}
