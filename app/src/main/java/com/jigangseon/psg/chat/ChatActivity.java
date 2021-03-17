package com.jigangseon.psg.chat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.jigangseon.psg.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

// 채팅 액티비티

public class ChatActivity extends AppCompatActivity {
    // 서버 접속 여부를 판별하기 위한 변수
    boolean isConnect = false;
    EditText edit1;
    Button btn1;
    LinearLayout container;
    ScrollView scroll;
    ProgressDialog pro;
    // 어플 종료시 스레드 중지를 위해...
    boolean isRunning=false;
    // 서버와 연결되어있는 소켓 객체
    Socket member_socket;
    // 사용자 닉네임( 내 닉넴과 일치하면 내가보낸 말풍선으로 설정 아니면 반대설정)
    String user_nickname;

    private DrawerLayout mDrawerLayout;
    private Context context = this;

    String nickName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 소프트 키보드 설정
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);

        edit1 = findViewById(R.id.editText);
        btn1 = findViewById(R.id.button);
        container=findViewById(R.id.container);
        scroll=findViewById(R.id.scroll);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
//        actionBar.setHomeAsUpIndicator(R.drawable.view_menu_icon); //뒤로가기 버튼 이미지 지정

        // 프로필 동그랗게
        ImageView profileImage = findViewById(R.id.chat_toolbar_food_image);
        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage.setClipToOutline(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

//                int id = menuItem.getItemId();
//                String title = menuItem.getTitle().toString();

//                if(id == R.id.account){
//                    Toast.makeText(context, title + ": 계정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
//                } else if(id == R.id.setting){
//                    Toast.makeText(context, title + ": 설정 정보를 확인합니다.", Toast.LENGTH_SHORT).show();
//                } else if(id == R.id.logout){
//                    Toast.makeText(context, title + ": 로그아웃 시도중", Toast.LENGTH_SHORT).show();
//                }
                return true;
            }
        });
    }

    /* 툴바 메뉴 메소드 */
    // 툴바 메뉴 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
//                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
            }
            case R.id.chat_menu_more:{ // 오른쪽 상단 메뉴 버튼 눌렀을 때
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
        menuInflater.inflate(R.menu.chat_menu, menu);

        return true;
    }

    /* 채팅 메소드 */
    // 버튼과 연결된 메소드
    public void btnMethod(View v) {

        String sendmsg = "vision_write";

        if (isConnect == false) {   //접속전
            //사용자가 입력한 닉네임을 받는다.
            nickName = edit1.getText().toString();
            if (nickName.length() > 0 && nickName != null) {
                //서버에 접속한다.
                pro = ProgressDialog.show(this, null, "접속중입니다");
                // 접속 스레드 가동
                ChatActivity.ConnectionThread thread = new ChatActivity.ConnectionThread();
                thread.start();

            }
            // 닉네임이 입력되지않을경우 다이얼로그창 띄운다.
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("닉네임을 입력해주세요");
                builder.setPositiveButton("확인", null);
                builder.show();
            }
        } else {                  // 접속 후
            // 입력한 문자열을 가져온다.
            String msg = edit1.getText().toString();
            // 송신 스레드 가동
            ChatActivity.SendToServerThread thread=new ChatActivity.SendToServerThread(member_socket,msg);

            // DB 연결
            try{
                String rst = new ChatTask(sendmsg).execute(nickName, msg, "vision_write").get();
            }catch (Exception e) {
                e.printStackTrace();
            }

            thread.start();
        }

        // 소프트 키보드 표시 제어
        // 키보드 숨기기
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }

    // 서버접속 처리하는 스레드 클래스 - 안드로이드에서 네트워크 관련 동작은 항상
    // 메인스레드가 아닌 스레드에서 처리해야 한다.
    class ConnectionThread extends Thread {

        @Override
        public void run() {
            try {
                // 접속한다.
                final Socket socket = new Socket("15.164.67.3", 9898);
                member_socket=socket;
                // 미리 입력했던 닉네임을 서버로 전달한다.
                String nickName = edit1.getText().toString();
                user_nickname = nickName;     // 화자에 따라 말풍선을 바꿔주기위해
                // 스트림을 추출
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                // 닉네임을 송신한다.
                dos.writeUTF(nickName);
                // ProgressDialog 를 제거한다.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pro.dismiss();
                        edit1.setText("");
                        edit1.setHint("메세지 입력");
                        btn1.setText("전송");
                        // 접속 상태를 true로 셋팅한다.
                        isConnect=true;
                        // 메세지 수신을 위한 스레드 가동
                        isRunning=true;
                        ChatActivity.MessageThread thread=new ChatActivity.MessageThread(socket);
                        thread.start();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class MessageThread extends Thread {
        Socket socket;
        DataInputStream dis;

        public MessageThread(Socket socket) {
            try {
                this.socket = socket;
                InputStream is = socket.getInputStream();
                dis = new DataInputStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try{
                while (isRunning){
                    // 서버로부터 데이터를 수신받는다.
                    final String msg=dis.readUTF();
                    // 화면에 출력
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 텍스트뷰의 객체를 생성
                            TextView tv = new TextView(ChatActivity.this);
                            tv.setTextColor(Color.BLACK);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);

                            // 레이아웃 크기
                            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                            // 나인패치 백그라운드 이미지
                            tv.setBackgroundResource(R.drawable.chat_bubble);

                            tv.setText(msg);

                            // 레이아웃에 뷰 추가
                            container.addView(tv);

                            // 제일 하단으로 스크롤 한다
                            scroll.fullScroll(View.FOCUS_DOWN);

                        }
                    });
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 서버에 데이터를 전달하는 스레드
    class SendToServerThread extends Thread{
        Socket socket;
        String msg;
        DataOutputStream dos;

        public SendToServerThread(Socket socket, String msg){
            try{
                this.socket=socket;
                this.msg=msg;
                OutputStream os=socket.getOutputStream();
                dos=new DataOutputStream(os);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try{
                // 서버로 데이터를 보낸다.
                dos.writeUTF(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edit1.setText("");
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 채팅방 나가기
    public void exitChatRoom(View v){
        finish();
    }

    // 액티비티 종료 시 소켓 닫기
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            member_socket.close();
            isRunning=false;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}