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

public class Subway_fragment_line_3 extends ListFragment {
    private ListView subwayList;
    private Subway_ListViewAdapter adapter;
    String[] subway_line_3 = new String[]{"신사","가락시장","경복궁","경찰병원","고속터미널","교대","구파발","금호","남부터미널","녹번","대곡","대청","대치","대화","도곡","독립문","동대입구","마두","매봉","무악재","백석","불광","삼송","수서","안국","압구정","약수","양재","연신내","오금","옥수","원당","원흥","을지로3가","일원","잠원","정발산","종로3가","주엽","지축","충무로","학여울","홍제","화정"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Adapter 생성 및 Adapter 지정
        adapter = new Subway_ListViewAdapter();
        setListAdapter(adapter);

        //첫 번째 아이템 추가

        for (int i=0; i< subway_line_3.length; i++){
            adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.subway_line_3),subway_line_3[i]);

        }




        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
