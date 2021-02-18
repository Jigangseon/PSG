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

public class Subway_fragment_line_2 extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    String[] subway_line_2 = new String[]{"강남","강변","건대입구","교대","구로디지털단지","구의","까치산","낙성대","당산","대림","도림천","동대문역사문화공원","뚝섬","문래","방배","봉천","사당","삼성","상왕십리","서울대입구","서초","선릉","성수","시청","신답","신당","신대방","신도림","신림","신설동","신정네거리","신촌","아현","양천구청","역삼","영등포구청","왕십리","용답","용두","을지로3가","을지로4가","을지로입구","이대","잠실","잠실나루","잠실새내","종합운동장","충정로","한양대","합정","홍대입구"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        //첫 번째 아이템 추가

        for (int i=0; i< subway_line_2.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_2),subway_line_2[i]);

        }




        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
