package com.jigangseon.psg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 분석중...
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<ChatDataItem> chatDataList = null;

    ChatAdapter(ArrayList<ChatDataItem> dataList){
        chatDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == ChatCode.ViewType.CENTER_CONTENT){
            view = inflater.inflate(R.layout.chat_center_content, parent, false);
            return new CenterViewHolder(view);
        }
        else if(viewType == ChatCode.ViewType.LEFT_CONTENT){
            view = inflater.inflate(R.layout.chat_left_content, parent, false);
            return new LeftViewHolder(view);
        }
        else{
            view = inflater.inflate(R.layout.chat_right_content, parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CenterViewHolder){
            ((CenterViewHolder) holder).content.setText(chatDataList.get(position).getContent());
        }
        else if(holder instanceof LeftViewHolder){
//            ((LeftViewHolder) holder).name.setText(chatDataList.get(position).getName());
            ((LeftViewHolder) holder).content.setText(chatDataList.get(position).getContent());
        }
        else{
//            ((RightViewHolder) holder).name.setText(chatDataList.get(position).getName());
            ((RightViewHolder) holder).content.setText(chatDataList.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return chatDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return chatDataList.get(position).getViewType();
    }

    public class CenterViewHolder extends RecyclerView.ViewHolder{
        TextView content;

        CenterViewHolder(View itemView)
        {
            super(itemView);

            content = itemView.findViewById(R.id.chat_content);
        }
    }

    public class LeftViewHolder extends RecyclerView.ViewHolder{
        TextView content;
//        TextView name;
//        ImageView image;

        LeftViewHolder(View itemView){
            super(itemView);

            content = itemView.findViewById(R.id.chat_content);
//            name = itemView.findViewById(R.id.name);
//            image = itemView.findViewById(R.id.imageView);
        }
    }

    public class RightViewHolder extends RecyclerView.ViewHolder{
        TextView content;
//        TextView name;
//        ImageView image;

        RightViewHolder(View itemView){
            super(itemView);

            content = itemView.findViewById(R.id.chat_content);
//            name = itemView.findViewById(R.id.name);
//            image = itemView.findViewById(R.id.imageView);
        }
    }

}