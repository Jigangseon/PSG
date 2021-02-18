package com.jigangseon.psg.search;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.jigangseon.psg.subway_line.*;
import com.jigangseon.psg.R;
import java.util.ArrayList;

public class Search extends AppCompatActivity {
    String[] subway_line_1 = new String[]{"가능", "가산디지털단지", "간석", "개봉", "관악", "광명", "광운대", "구로", "구일", "군포", "금정", "금천구청", "남영", "노량진", "녹양", "녹천", "당정", "대방", "덕계", "덕정", "도봉", "도봉산", "도원", "도화", "독산", "동대문", "동두천", "동두천중앙", "동묘앞", "동암", "동인천", "두정", "망월사", "명학", "방학", "배방", "백운", "병점", "보산", "봉명", "부개", "부천", "부평", "서동탄", "서울역", "서정리", "석계", "석수", "성균관대", "성환", "세류", "세마", "소사", "소요산", "송내", "송탄", "수원", "시청", "신길", "신도림", "신설동", "신이문", "신창", "쌍용", "아산", "안양", "양주", "역곡", "영등포", "오류동", "오산", "오산대", "온수", "온양온천", "외대앞", "용산", "월계", "의왕", "의정부", "인천", "제기동", "제물포", "종각", "종로3가", "종로5가", "주안", "중동", "지제", "지행", "직산", "진위", "창동", "천안", "청량리", "평택", "화서", "회기", "회룡"
    };


    //프래그먼트 객체화
    Subway_fragment subway_fragment = new Subway_fragment();
    Subway_fragment_line_1 subway_fragment_line_1 = new Subway_fragment_line_1();
    Subway_fragment_line_2 subway_fragment_line_2 = new Subway_fragment_line_2();
    Subway_fragment_line_3 subway_fragment_line_3 = new Subway_fragment_line_3();
    Subway_fragment_line_4 subway_fragment_line_4 = new Subway_fragment_line_4();
    Subway_fragment_line_5 subway_fragment_line_5 = new Subway_fragment_line_5();
    Subway_fragment_line_6 subway_fragment_line_6 = new Subway_fragment_line_6();
    Subway_fragment_line_7 subway_fragment_line_7 = new Subway_fragment_line_7();
    Subway_fragment_line_8 subway_fragment_line_8 = new Subway_fragment_line_8();
    Subway_fragment_line_9 subway_fragment_line_9 = new Subway_fragment_line_9();

    //프래그먼트 확인 변수
    int i=0;



    private ArrayList<Integer> imageList;
    ViewPager viewPager;
    private static final int DP = 24;
    private ListView subwayList;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

       /* ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);

        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (DP * density);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        viewPager.setAdapter(new Subway_ViewPagerAdapter(this, imageList));
*/


        setFragment(subway_fragment);



    }

    //프래그먼트 생성하는 메소드
    public Fragment setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.subway_fragment,fragment).commitAllowingStateLoss();
        return fragment;
    }

    //프래그먼트 지우는 메소드
    public Fragment removeFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment).commitAllowingStateLoss();

        return fragment;
    }

    //뒤로가기 버튼클릭시
    @Override
    public void onBackPressed(){

        //프래그먼트가 호선들이 클릭되어있을때 뒤로가기버튼을 누르면 초기화면으로 이동하게만듬
        if(i !=0 ){
            setFragment(subway_fragment);
            i=0;

        }
        //차후에 초기화면일때 뒤로가기 버튼을 누르면 페이지 이동하게 만들면 된다.
        else{/*
            Inflater inflater = new Inflater(R.layout.)*/
        }
    }

    //버튼 클릭 메소드
    public void Click(View view){


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //클릭된 값 확인 스위치
        switch (view.getId()){
            case R.id.subway_line_1:

                if(i ==1 ){
                    removeFragment(subway_fragment_line_1);
                    setFragment(subway_fragment);
                    i=0;
                }

                else{
                    subway_fragment_line_1 = new Subway_fragment_line_1();
                    setFragment(subway_fragment_line_1);
                    transaction.addToBackStack(null);

                  i=1;
                }
                break;
            case R.id.subway_line_2:
                if(i ==2 ){
                    removeFragment(subway_fragment_line_2);
                    setFragment(subway_fragment);
                    i=0;}
                else {
                    subway_fragment_line_2 = new Subway_fragment_line_2();
                    setFragment(subway_fragment_line_2);
                    transaction.addToBackStack(null);
                    i = 2;
                }
                break;
            case R.id.subway_line_3:
                if(i ==3 ){
                    removeFragment(subway_fragment_line_3);
                    setFragment(subway_fragment);
                    i=0;
                }
                else {
                    subway_fragment_line_3 = new Subway_fragment_line_3();
                    setFragment(subway_fragment_line_3);
                    transaction.addToBackStack(null);
                    i = 3;
                }
                break;
            case R.id.subway_line_4:
                if(i ==4 ){
                    removeFragment(subway_fragment_line_4);
                    setFragment(subway_fragment);
                    i=0;
                }
                else {
                    subway_fragment_line_4 = new Subway_fragment_line_4();
                    setFragment(subway_fragment_line_4);
                    transaction.addToBackStack(null);
                    i = 4;
                }
                break;
            case R.id.subway_line_5:
                if(i ==5 ){
                    removeFragment(subway_fragment_line_5);
                    setFragment(subway_fragment);
                    i=0;
                }
                else {
                    subway_fragment_line_5 = new Subway_fragment_line_5();
                    setFragment(subway_fragment_line_5);
                    transaction.addToBackStack(null);
                    i = 5;
                }
                break;
            case R.id.subway_line_6:
                if(i ==6 ){
                    removeFragment(subway_fragment_line_6);
                    setFragment(subway_fragment);
                    i=0;
                }
                else {
                    subway_fragment_line_6 = new Subway_fragment_line_6();
                    setFragment(subway_fragment_line_6);
                    transaction.addToBackStack(null);
                    i = 6;
                }
                break;
            case R.id.subway_line_7:
                if(i ==7 ){
                    removeFragment(subway_fragment_line_7);
                    setFragment(subway_fragment);
                    i=0;
                }
                else {
                    subway_fragment_line_7 = new Subway_fragment_line_7();
                    setFragment(subway_fragment_line_7);
                    transaction.addToBackStack(null);
                    i = 7;
                }
                break;
            case R.id.subway_line_8:
                if(i ==8 ){
                    removeFragment(subway_fragment_line_8);
                    setFragment(subway_fragment);
                    i=0;
                }
                else {
                    subway_fragment_line_8 = new Subway_fragment_line_8();

                    setFragment(subway_fragment_line_8);
                    transaction.addToBackStack(null);
                    i = 8;
                }
                break;
            case R.id.subway_line_9:
                if(i ==9 ){
                    removeFragment(subway_fragment_line_9);
                    setFragment(subway_fragment);
                    i=0;
                }
                else {
                    subway_fragment_line_9 = new Subway_fragment_line_9();
                    setFragment(subway_fragment_line_9);
                    transaction.addToBackStack(null);
                    i = 9;
                }
                break;

        }

    }

    public void initializeData()
    {
        imageList = new ArrayList();

        imageList.add(R.drawable.hodduk);
        imageList.add(R.drawable.fish_shaped);
        imageList.add(R.drawable.jun);
        imageList.add(R.drawable.tteokbokki);
    }



}

