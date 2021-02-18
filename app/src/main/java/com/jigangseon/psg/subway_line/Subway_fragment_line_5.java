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

public class Subway_fragment_line_5 extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    String[] subway_line_5 = new String[]{"강동","개롱","개화산","거여","고덕","공덕","광나루","광화문","군자","굽은다리","길동","김포공항","까치산","답십리","동대문역사문화공원","둔촌동","마곡","마장","마천","마포","명일","목동","미사","발산","방이","방화","상일동","서대문","송정","신금호","신길","신정","아차산","애오개","양평","여의나루","여의도","영등포구청","영등포시장","오금","오목교","올림픽공원","왕십리","우장산","을지로4가","장한평","종로3가","천호","청구","충정로","하남풍산","행당","화곡"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        //첫 번째 아이템 추가

        for (int i=0; i< subway_line_5.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_5),subway_line_5[i]);

        }




        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
