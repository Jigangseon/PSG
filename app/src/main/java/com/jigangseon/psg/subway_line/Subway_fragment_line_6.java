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

public class Subway_fragment_line_6 extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    String[] subway_line_6 = new String[]{"고려대","공덕","광흥창","구산","녹사평","대흥","독바위","돌곶이","동묘앞","디지털미디어시티","마포구청","망원","버티고개","보문","봉화산","불광","삼각지","상수","상월곡","새절","석계","신내","신당","안암","약수","역촌","연신내","월곡","월드컵경기장","응암","이태원","증산","창신","청구","태릉입구","한강진","합정","화랑대","효창공원앞"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        //첫 번째 아이템 추가

        for (int i=0; i< subway_line_6.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_6),subway_line_6[i]);

        }




        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
