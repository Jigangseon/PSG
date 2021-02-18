package com.jigangseon.psg.subway_line;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import com.jigangseon.psg.R;
import com.jigangseon.psg.search.*;

public class Subway_fragment_line_1 extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    String[] subway_line_1 = new String[]{"가능", "가산디지털단지", "간석", "개봉", "관악", "광명", "광운대", "구로", "구일", "군포", "금정", "금천구청", "남영", "노량진", "녹양", "녹천", "당정", "대방", "덕계", "덕정", "도봉", "도봉산", "도원", "도화", "독산", "동대문", "동두천", "동두천중앙", "동묘앞", "동암", "동인천", "두정", "망월사", "명학", "방학", "배방", "백운", "병점", "보산", "봉명", "부개", "부천", "부평", "서동탄", "서울역", "서정리", "석계", "석수", "성균관대", "성환", "세류", "세마", "소사", "소요산", "송내", "송탄", "수원", "시청", "신길", "신도림", "신설동", "신이문", "신창", "쌍용", "아산", "안양", "양주", "역곡", "영등포", "오류동", "오산", "오산대", "온수", "온양온천", "외대앞", "용산", "월계", "의왕", "의정부", "인천", "제기동", "제물포", "종각", "종로3가", "종로5가", "주안", "중동", "지제", "지행", "직산", "진위", "창동", "천안", "청량리", "평택", "화서", "회기", "회룡"
    };

    public boolean test = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        //첫 번째 아이템 추가

        for (int i=0; i< subway_line_1.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_1),subway_line_1[i]);

        }





    return super.onCreateView(inflater, container, savedInstanceState);
    }

}
