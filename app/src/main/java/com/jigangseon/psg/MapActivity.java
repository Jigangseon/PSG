package com.jigangseon.psg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jigangseon.psg.find.MainActivity;
import com.jigangseon.psg.search.Search;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.CompassView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MainActivity";

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;
    private UiSettings uiSettings;

    private SlidingUpPanelLayout main_panel;

    private Context context = this;

    private static JSONObject obj;

//    MarkerList markerList = new MarkerList();
//    List<Marker> markers = markerList.getMarkerList();
    private JSONArray jsonArray;

    private Marker marker;
    private List<Marker> markers;

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

    }


    @SuppressLint("WrongConstant")
    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) { // 처음 어플을 실행할 때 맵을 로드해주는 메소드
        Log.d( TAG, "onMapReady");

        // 드로어 레이아웃 (사이드 메뉴)
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer) ;
        drawer.setDrawerLockMode(0x00000001);
        Button buttonOpen = (Button) findViewById(R.id.draw_side_menu_button) ;
        buttonOpen.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.openDrawer(Gravity.LEFT);
                    main_panel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

                }

            }
        });

        TextView marker_main_title = (TextView) findViewById(R.id.main_title);
        TextView marker_title = (TextView) findViewById(R.id.text_title);
        TextView marker_info = (TextView) findViewById(R.id.text_info);
        TextView marker_address = (TextView) findViewById(R.id.text_address);
        TextView marker_main = (TextView) findViewById(R.id.text_main);
        RatingBar marker_rating = (RatingBar) findViewById(R.id.ratingBar);

        mNaverMap = naverMap; // 네이버맵 생성
        mNaverMap.setLocationSource(mLocationSource); // 현위치 정보

        // 기본 UI셋팅
        uiSettings = mNaverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true); // 현재위치 트래킹 버튼
        uiSettings.setCompassEnabled(false); // 나침반
        CompassView compassView = findViewById(R.id.compass);
        compassView.setMap(mNaverMap);


        Executor executor = Executors.newFixedThreadPool(2);
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // 백그라운드 스레드

            try {

                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String, String> map = new HashMap<>();

                String json = objectMapper.writeValueAsString(map);

                Map_HttpUtil util = new Map_HttpUtil();
                util.execute(json);

                JSONArray jsonArray = util.get();


                markers = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    obj = jsonArray.getJSONObject(i);
                    marker = new Marker();
                    marker.setTag(obj.getInt("store_code"));
                    marker.setPosition(new LatLng(Double.parseDouble(obj.getString("store_latitude")), Double.parseDouble(obj.getString("store_longitude"))));
                    marker.setWidth(50);
                    marker.setHeight(70);
                    marker.setCaptionText(obj.getString("store_category_id"));
                    marker.setOnClickListener(new Overlay.OnClickListener() {
                        @Override
                        public boolean onClick(@NonNull Overlay overlay) {
                            main_panel = (SlidingUpPanelLayout) findViewById(R.id.main_panel);
                            main_panel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                            try {
                                obj = jsonArray.getJSONObject((Integer) overlay.getTag()-1);
                                marker_main_title.setText(obj.getString("store_name"));
                                marker_title.setText(obj.getString("store_hours"));
                                marker_info.setText(obj.getString("store_info"));
                                marker_address.setText(obj.getString("store_address"));
                                marker_main.setText(obj.getString("store_explanation"));
                                marker_rating.setRating(Float.parseFloat(obj.getString("store_rating")));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return false;
                        }
                    });
                    markers.add(marker);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            handler.post(() -> {
                // 메인 스레드
                for (Marker marker : markers) {
                    marker.setMap(naverMap);

                }

            });
        });





        // 맵 클릭시 호출되는 메소드
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) { // 맵 클릭시 호출되는 메소드
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



    public void onCheckBoxClicked(View v){
        boolean checked = ((CheckBox) v).isChecked();
        String[] store_cate = new String[]{"호떡","붕어빵/잉어빵","떡볶이","문어빵","빵 종류","파전","기타"};
        switch (v.getId()){
            case R.id.side_menu_1:
                if (checked){
                    Toast.makeText(getApplicationContext(), "1 checked", Toast.LENGTH_LONG).show();
                    for (int i=0; i < markers.size(); i++) {
                            if (markers.get(i).getCaptionText().equals(store_cate[0])) {
                                markers.get(i).setVisible(true);
                            }
                    }
                }else{
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[0])) {
                            markers.get(i).setVisible(false);
                        }
                    }
                }
                break;
            case R.id.side_menu_2:
                if (checked){
                    Toast.makeText(getApplicationContext(), "2 checked", Toast.LENGTH_LONG).show();
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[1])) {
                            markers.get(i).setVisible(true);
                        }
                    }
                }else{
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[1])) {
                            markers.get(i).setVisible(false);
                        }
                    }
                }
                break;

            case R.id.side_menu_3:
                if (checked){
                    Toast.makeText(getApplicationContext(), "3 checked", Toast.LENGTH_LONG).show();
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[2])) {
                            markers.get(i).setVisible(true);
                        }
                    }
                }else{
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[2])) {
                            markers.get(i).setVisible(false);
                        }
                    }
                }
                break;

            case R.id.side_menu_4:
                if (checked){
                    Toast.makeText(getApplicationContext(), "4 checked", Toast.LENGTH_LONG).show();
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[3])) {
                            markers.get(i).setVisible(true);
                        }
                    }
                }else{
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[3])) {
                            markers.get(i).setVisible(false);
                        }
                    }
                }
                break;

            case R.id.side_menu_5:
                if (checked){
                    Toast.makeText(getApplicationContext(), "5 checked", Toast.LENGTH_LONG).show();
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[4])) {
                            markers.get(i).setVisible(true);
                        }
                    }
                }else{
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[4])) {
                            markers.get(i).setVisible(false);
                        }
                    }
                }
                break;

            case R.id.side_menu_6:
                if (checked){
                    Toast.makeText(getApplicationContext(), "6 checked", Toast.LENGTH_LONG).show();
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[5])) {
                            markers.get(i).setVisible(true);
                        }
                    }
                }else{
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[5])) {
                            markers.get(i).setVisible(false);
                        }
                    }
                }
                break;

            case R.id.side_menu_7:
                if (checked){
                    Toast.makeText(getApplicationContext(), "7 checked", Toast.LENGTH_LONG).show();
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[6])) {
                            markers.get(i).setVisible(true);
                        }
                    }
                }else{
                    for (int i=0; i < markers.size(); i++) {
                        if (markers.get(i).getCaptionText().equals(store_cate[6])) {
                            markers.get(i).setVisible(false);
                        }
                    }
                }
                break;
        }
    }


    public void clickMenu(View view) { // 사이드 메뉴
        int id = view.getId();
        LinearLayout linearLayout = findViewById(id);

                switch (id) {
                    //로그인쪽 누르는것
                    case R.id.side_profile_layout:
                        Toast.makeText(context, "로그인 하기", Toast.LENGTH_SHORT).show();
                        Intent side_profile_layout = new Intent(this,LoginActivity.class);
                        startActivity(side_profile_layout);
                        break;

                    case R.id.side_together:
                        Toast.makeText(context, "함께 먹을 사람 찾기", Toast.LENGTH_SHORT).show();
                        Intent side_menu1 = new Intent(this,MainActivity.class);
                        startActivity(side_menu1);
                        // 강이 레이아웃으로 인텐트
                        break;

                }

    }

    public void searchView(View v){  // 검색창
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }
}