//package com.jigangseon.psg;
//
//import android.app.Activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.naver.maps.geometry.LatLng;
//import com.naver.maps.map.overlay.Marker;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class MarkerList extends Activity {
//
//    private static Marker marker;
//    private static JSONObject obj;
//    private List<Marker> markers;
//
//
//
//    public MarkerList() {
//    }
//
//    public List<Marker> getMarkerList() {
//
//
//        try {
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            HashMap<String, String> map = new HashMap<>();
//
//            String json = objectMapper.writeValueAsString(map);
//
//            HttpUtil util = new HttpUtil();
//            util.execute(json);
//
//            JSONArray jsonArray = util.get();
//
//
//            markers = new ArrayList<>();
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//                    obj = jsonArray.getJSONObject(i);
//                    marker = new Marker();
//                    marker.setTag(obj.getInt("store_code"));
//                    marker.setPosition(new LatLng(Double.parseDouble(obj.getString("store_latitude")), Double.parseDouble(obj.getString("store_longitude"))));
//                    marker.setWidth(50);
//                    marker.setHeight(70);
//                    markers.add(marker);
//            }
//
//
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return markers;
//    }
//
//}
