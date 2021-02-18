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

public class Subway_fragment_line_9 extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    String[] subway_line_9 = new String[]{"가양","개화","고속터미널","공항시장","구반포","국회의사당","김포공항","노들","노량진","당산","동작","둔촌오륜","등촌","마곡나루","봉은사","사평","삼성중앙","삼전","샛강","석촌","석촌고분","선유도","선정릉","송파나루","신논현","신목동","신반포","신방화","양천향교","언주","여의도","염창","올림픽공원","종합운동장","중앙보훈병원","증미","한성백제","흑석"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        //첫 번째 아이템 추가

        for (int i=0; i< subway_line_9.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_9),subway_line_9[i]);

        }




        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
