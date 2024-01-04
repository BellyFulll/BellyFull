package com.example.bellyfull.modules.AdviceAndTips.Models;// AdviceItem.java

public class AdviceItem {
    private String title;
    private String content;

    public AdviceItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

