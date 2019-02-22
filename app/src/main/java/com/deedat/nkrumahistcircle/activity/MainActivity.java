package com.deedat.nkrumahistcircle.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import com.deedat.nkrumahistcircle.ChatListFragment;
import com.deedat.nkrumahistcircle.DocFragment;
import com.deedat.nkrumahistcircle.FinanceFragment;
import com.deedat.nkrumahistcircle.HomeFragment;
import com.deedat.nkrumahistcircle.NewFragment;
import com.deedat.nkrumahistcircle.R;

import com.deedat.nkrumahistcircle.helper.BottomNavigationBehavior;
import com.deedat.nkrumahistcircle.helper.RecyclerItemTouchHelper;
import com.deedat.nkrumahistcircle.adapter.ArticleListAdapter;

import com.deedat.nkrumahistcircle.model.Article;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    FirebaseAuth auth;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Article> articleList;
    private ArticleListAdapter mAdapter;
    SwipeRefreshLayout swipeContainer;
    ProgressBar progressBar;
    FloatingActionButton fab;
    Fragment fragment;

    private static final String URL = "https://api.androidhive.info/json/menu.json";
    public CoordinatorLayout coordinatorLayout;
    Toolbar toolbar;
    BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    //  toolbar.setTitle("Home");
                    return true;
                case R.id.navigation_documents:
                    toolbar.setTitle("Documents");
                    fragment = new DocFragment();
                    loadFragment(fragment);


                    return true;
                case R.id.navigation_notifications:
                    toolbar.setTitle("Messages");
                    fragment = new ChatListFragment();
                    loadFragment(fragment);

                    return true;
                case R.id.navigation_finance:

                    toolbar.setTitle("Finances");


                    fragment = new FinanceFragment();
                    loadFragment(fragment);



                    return true;
                case R.id.navigation_news_feed:
                    toolbar.setTitle("News");
                    fragment = new NewFragment();
                    loadFragment(fragment);
                    // toolbar.setTitle("Finances");
                //    nkfinances();

                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager  pager=(ViewPager) findViewById(R.id.pager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tablayout);
         navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTextDirection(88);
        fragment = new HomeFragment();
        loadFragment(fragment);
        coordinatorLayout=findViewById(R.id.container);

        Log.v("frag tag",""+fragment.getTag()+"");
        // toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        toolbar.inflateMenu(R.menu.menu);
        toolbar.setElevation(16f);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()){


                    case R.id.settings:
                        Intent intent=new Intent(MainActivity.this,Settings.class);
                        startActivity(intent);


                        break;
                    case R.id.signout:
                        signout();
                }
                return false;
            }
        });

    }



    public void nkchat(){
        Intent intent= new Intent(this,ChatActivity.class);
        startActivity(intent);


    }






    public void article(){
        Intent intent=new Intent(this,AddArticle.class);
        startActivity(intent);
    }



    @Override
    protected  void onStart(){
        super.onStart();


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        int count = getSupportFragmentManager().getBackStackEntryCount();
        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
        if (count > 1) {
            Log.v("mytagxxx",""+fragment.getTag()+"");
           // fragments.popBackStackImmediate();
           //getSupportFragmentManager().popBackStack(getSupportFragmentManager().getBackStackEntryAt(0).getId(), getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);
        } else {
           // super.onBackPressed();
        }


    }


    public void signout(){
        auth.signOut();
        Intent intent =new Intent(this,loginpage.class);
        startActivity(intent);
        finish();


    }

    @Override
    public void onBackPressed() {

super.onBackPressed();
this.addOnBackPressedCallback(new OnBackPressedCallback() {
    @Override
    public boolean handleOnBackPressed() {
        return false;
    }
});
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, " press the back buttton again to exit", Snackbar.LENGTH_LONG);

        snackbar.setActionTextColor(Color.YELLOW);

        snackbar.show();




navigation.setSelectedItemId(R.id.navigation_home);
        //}

       // else {
           // super.onBackPressed();
       // }
    }
}
