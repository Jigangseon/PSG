package com.jigangseon.psg.subway_line;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;
import com.jigangseon.psg.search.*;
import com.jigangseon.psg.R;
public class Subway_fragment_line_8 extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    String[] subway_line_8 = new String[]{"가락시장","강동구청","남한산성입구","단대오거리","모란","몽촌토성","문정","복정","산성","석촌","송파","수진","신흥","암사","잠실","장지","천호"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        //첫 번째 아이템 추가

        for (int i=0; i< subway_line_8.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_8),subway_line_8[i]);

        }




        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
