package com.deedat.nkrumahistcircle;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.deedat.nkrumahistcircle.finances.FinanceManagement;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment  {

    Toolbar toolbar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                  //  toolbar.setTitle("Home");
                    return true;
                case R.id.navigation_dashboard:

                   // toolbar.setTitle("Dashboard");
                    return true;
                case R.id.navigation_notifications:

                   // toolbar.setTitle("Notifications");
                    return true;
                case R.id.navigation_finance:

                   // toolbar.setTitle("Finances");
                    //nkfinances();

                    return true;
            }
            return false;
        }
    };



    public HomeFragment() {

        // Required empty public constructor
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

        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

      //  toolbar=view.findViewById(R.id.toolbar);

    }



}

