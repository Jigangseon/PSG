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

public class Subway_fragment_line_7 extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    String[] subway_line_7 = new String[]{"가산디지털단지","강남구청","건대입구","고속터미널","공릉","광명사거리","군자","굴포천","까치울","남구로","남성","내방","노원","논현","대림","도봉산","뚝섬유원지","마들","먹골","면목","반포","보라매","부천시청","부천종합운동장","부평구청","사가정","삼산체육관","상도","상동","상봉","수락산","숭실대입구","신대방삼거리","신중동","신풍","어린이대공원","온수","용마산","이수","장승배기","장암","중계","중곡","중화","천왕","철산","청담","춘의","태릉입구","하계","학동"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        //첫 번째 아이템 추가

        for (int i=0; i< subway_line_7.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_7),subway_line_7[i]);

        }




        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
