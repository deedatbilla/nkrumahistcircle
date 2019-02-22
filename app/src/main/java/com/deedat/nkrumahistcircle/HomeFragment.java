package com.deedat.nkrumahistcircle;


import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.deedat.nkrumahistcircle.activity.AddArticle;
import com.deedat.nkrumahistcircle.activity.Article_content;
import com.deedat.nkrumahistcircle.activity.MainActivity;
import com.deedat.nkrumahistcircle.activity.loginpage;
import com.deedat.nkrumahistcircle.adapter.ArticleListAdapter;

import com.deedat.nkrumahistcircle.helper.RecyclerItemTouchHelper;
import com.deedat.nkrumahistcircle.model.Article;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    FirebaseAuth auth;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Article> articleList;
    private ArticleListAdapter mAdapter;
    SwipeRefreshLayout swipeContainer;
    ProgressBar progressBar;
    FloatingActionButton fab;
    private static final String URL = "https://api.androidhive.info/json/menu.json";
    FrameLayout fl;


    Toolbar toolbar;



    public HomeFragment() {

        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

   // @Override
    //public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    //    inflater.inflate(R.menu.menu, menu);
   // }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_home, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        fab=view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                article();
            }
        });
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {




                fetchArticles();


                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        toolbar=view.findViewById(R.id.toolbar);
        fl=view.findViewById(R.id.fl);






        recyclerView = view.findViewById(R.id.recycler_view);
        //  coordinatorLayout = findViewById(R.id.coordinator_layout);
        articleList = new ArrayList<>();
        mAdapter = new ArticleListAdapter(getActivity(), articleList);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
      //  recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);




        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
//articleList.add(new Article(1,"The Menzgold confusion","Yusef Gariba","The foundational elements of print based design typography, grids, space, scale, color","https://api.androidhive.info/images/food/1.jpg"));
        fetchArticles();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);

        auth=FirebaseAuth.getInstance();




    }





    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ArticleListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String title = articleList.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final Article deletedItem = articleList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(fl, title+ " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);

            snackbar.show();

        }

    }
    public void article(){
        Intent intent=new Intent(getActivity(),AddArticle.class);
        startActivity(intent);
    }

    public void fetchArticles() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("articles");
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                articleList.clear();
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                String uid = firebaseUser.getUid();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Article art = snapshot.getValue(Article.class);

                    articleList.add(art);

                }
                mAdapter = new ArticleListAdapter(getActivity(), articleList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);

                recyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                mAdapter.setOnItemClickListener(new ArticleListAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent=new Intent(getActivity(),Article_content.class);
                        intent.putExtra("article_data", articleList.get(position));
                        startActivity(intent);
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}

