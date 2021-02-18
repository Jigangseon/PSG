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

public class Subway_fragment_line_4 extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    String[] subway_line_4 = new String[]{"경마공원","고잔","과천","금정","길음","남태령","노원","당고개","대공원","대야미","동대문","동대문역사문화공원","동작","명동","미아","미아사거리","반월","범계","사당","산본","삼각지","상계","상록수","서울역","선바위","성신여대입구","수리산","수유","숙대입구","신길온천","신용산","쌍문","안산","오이도","이촌","인덕원","정부과천청사","정왕","중앙","창동","초지","총신대입구","충무로","평촌","한대앞","한성대입구","혜화","회현"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        //첫 번째 아이템 추가

        for (int i=0; i< subway_line_4.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_4),subway_line_4[i]);

        }




        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
