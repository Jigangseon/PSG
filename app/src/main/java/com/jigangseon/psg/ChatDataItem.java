package com.jigangseon.psg;

// 분석중...
public class ChatDataItem {
    private String content;
    private String name;
    private int viewType;

    public ChatDataItem(String content, String name ,int viewType) {
        this.content = content;
        this.viewType = viewType;
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public int getViewType() {
        return viewType;
    }
}
