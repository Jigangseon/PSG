package com.jigangseon.psg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ArrayList<ChatDataItem> dataList;

    private DrawerLayout mDrawerLayout;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 커스텀 툴바 사용
        Toolbar toolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);

        // 툴바 앱 이름 표시 안함
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // 툴바 home버튼(좌측 상단 버튼) 사용
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 프로필 동그랗게
        ImageView profileImage = findViewById(R.id.chat_toolbar_food_image);
        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage.setClipToOutline(true);

        // DrawerLayout ID 가져옴
        mDrawerLayout = findViewById(R.id.chat_drawer);

        // NavigationView ID 가져옴 (사이드 메뉴)
        NavigationView navigationView = (NavigationView) findViewById(R.id.chat_nav_view);

        // 메뉴 아이템 클릭 이벤트
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

/*                if(id == R.id.account){
                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                } else if(id == R.id.setting){
                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
                } else if(id == R.id.logout){
                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
                }*/
                return true;
            }
        });

        this.initializeData();  // 초기 화면 설정
        RecyclerView recyclerView = findViewById(R.id.chat_recyclerview);
        LinearLayoutManager manager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new ChatAdapter(dataList));  // Adapter 등록
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
//                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
            }
            case R.id.chat_menu_more:{ // 오른쪽 상단 메뉴 버튼 눌렀을 때
                // 왼쪽에서 오른쪽으로 슬라이드
                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // 툴바에 메뉴 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.chat_toolbar_menu, menu);

        return true;
    }

    // 초기 화면 설정
    public void initializeData() {
        dataList = new ArrayList<>();

        // 채팅 컨텐츠 추가
        dataList.add(new ChatDataItem("사용자1님이 입장하셨습니다.", null, ChatCode.ViewType.CENTER_CONTENT));
        dataList.add(new ChatDataItem("사용자2님이 입장하셨습니다.", null, ChatCode.ViewType.CENTER_CONTENT));
        dataList.add(new ChatDataItem("안녕하세요안녕하세요안녕하세요\n안녕하세요안녕하세요안녕하세요\n안녕하세요", "사용자1", ChatCode.ViewType.LEFT_CONTENT));
        dataList.add(new ChatDataItem("안녕하세요", "사용자2", ChatCode.ViewType.RIGHT_CONTENT));

    }
}