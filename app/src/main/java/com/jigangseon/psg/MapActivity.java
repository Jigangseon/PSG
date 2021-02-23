package com.jigangseon.psg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.CompassView;
import com.naver.maps.map.widget.LocationButtonView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import com.jigangseon.psg.search.*;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback  {
    private static final String TAG = "MainActivity";

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;
    private UiSettings uiSettings;
    private Overlay overlay;


    private SlidingUpPanelLayout main_panel;

    private DrawerLayout mDrawerLayout;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        // 네이버 지도
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        if(mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map_view, mapFragment).commit();
        }
        // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        // onMapReady에서 NaverMap 객체를 받음
        mapFragment.getMapAsync(this);

        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        main_panel = (SlidingUpPanelLayout)findViewById(R.id.main_panel);


        // 드로어 레이아웃 (사이드 메뉴)
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        Button buttonOpen = (Button) findViewById(R.id.draw_side_menu_button) ;
        buttonOpen.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer) ;
                if (!drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.openDrawer(Gravity.LEFT);
                }
                if (main_panel.getPanelState()== SlidingUpPanelLayout.PanelState.COLLAPSED){   // 드로어 레이아웃을 열면 슬라이드 패널을 닫는다.
                    main_panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                }
            }
        });
    }


    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) { // 처음 어플을 실행할 때 맵을 로드해주는 메소드
        Log.d( TAG, "onMapReady");

        TextView marker_main_title = (TextView) findViewById(R.id.main_title);
        TextView marker_title = (TextView) findViewById(R.id.text_title);
        TextView marker_info = (TextView) findViewById(R.id.text_info);
        TextView marker_address = (TextView) findViewById(R.id.text_address);
        TextView marker_main = (TextView) findViewById(R.id.text_main);

        mNaverMap = naverMap; // 네이버맵 생성
        mNaverMap.setLocationSource(mLocationSource); // 현위치 정보

        // 기본 UI셋팅
        uiSettings = mNaverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true); // 현재위치 트래킹 버튼
        uiSettings.setCompassEnabled(false); // 나침반
        CompassView compassView = findViewById(R.id.compass);
        compassView.setMap(mNaverMap);

        Marker marker = new Marker();
        marker.setTag("마커1");
        marker.setPosition(new LatLng(37.36135327233796, 127.96061019229224));
        marker.setWidth(50);
        marker.setHeight(70);

        Marker marker1 = new Marker();
        marker1.setTag("마커2");
        marker1.setPosition(new LatLng(37.36099795000704, 127.9608173062845));
        marker1.setWidth(50);
        marker1.setHeight(70);

        marker.setMap(naverMap);
        marker1.setMap(naverMap);

        marker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Log.v("태그","onClick");

                main_panel = (SlidingUpPanelLayout) findViewById(R.id.main_panel);
                main_panel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED); // 패널을 접힌상태로 보여준다.

                marker_main_title.setText(getString(R.string.menu_main_title));
                marker_title.setText(getString(R.string.menu_select_title));
                marker_info.setText(getString(R.string.menu_select_info));
                marker_address.setText(getString(R.string.menu_select_address));
                marker_main.setText(getString(R.string.menu_select_main));

                return true;

            }
        });

        marker1.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Log.v("태그","onClick");

                main_panel = (SlidingUpPanelLayout) findViewById(R.id.main_panel);
                main_panel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED); // 패널을 접힌상태로 보여준다.

                marker_main_title.setText(getString(R.string.menu_main_title1));
                marker_title.setText(getString(R.string.menu_select_title1));
                marker_info.setText(getString(R.string.menu_select_info1));
                marker_address.setText(getString(R.string.menu_select_address1));
                marker_main.setText(getString(R.string.menu_select_main1));

                return true;
            }
        });

        // 맵 클릭시 호출되는 메소드
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                Log.v("태그", "MapClick");
                if (main_panel.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED){ // 맵 클릭시 패널을 숨긴다.
                    main_panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                }
            }
        });


        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE); // 위치정보 퍼미션을 전달

        main_panel = (SlidingUpPanelLayout)findViewById(R.id.main_panel);
        main_panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN); // 처음에 맵을 로드할때 패널이 숨겨져 있는 상태로 로드


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) { // 권한을 확인하는 메소드
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // request code와 권환획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }



    @Override
    protected void onDestroy() { // 종료시 호출되는 메소드
        super.onDestroy();

    }



















    public void clickMenu(View view) { // 사이드 메뉴
        int id = view.getId();
        ConstraintLayout constraintLayout = findViewById(id);

        switch (id) {
            case R.id.side_menu1:
                Toast.makeText(context, "첫번째메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.side_menu2:
                Toast.makeText(context, "두번째메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.side_menu3:
                Toast.makeText(context, "세번째메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.side_menu4:
                Toast.makeText(context, "네번째메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.side_menu5:
                Toast.makeText(context, "다섯번째메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.side_menu6:
                Toast.makeText(context, "여섯번째메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.side_menu7:
                Toast.makeText(context, "일곱번째메뉴", Toast.LENGTH_SHORT).show();
                break;
            case R.id.side_menu8:
                Toast.makeText(context, "여덟번째메뉴", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void searchView(View v){  // 검색창
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }
}