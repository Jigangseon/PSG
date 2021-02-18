package com.jigangseon.psg.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.jigangseon.psg.R;
import com.jigangseon.psg.subway_line.*;

import java.util.ArrayList;

public class Subway_fragment extends Fragment {


  int i=0;
    private ArrayList<Integer> imageList;
    ViewPager viewPager;
    private static final int DP = 24;

    public Subway_fragment(){

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_subway,container,false);
       /* ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP * density);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        viewPager.setAdapter(new Subway_ViewPagerAdapter(getContext(), imageList));*/




        return view;
    }


  /*  public void initializeData()
    {
        imageList = new ArrayList();

        imageList.add(R.drawable.hodduk);
        imageList.add(R.drawable.jun);
        imageList.add(R.drawable.tteokbokki);
        imageList.add(R.drawable.fish_shaped);
    }*/



}
