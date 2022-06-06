package com.androidtest.gdxgame.EventManager;

public class StringByChar extends BaseEvent{
    private final String text;
    private int textLength;

    public StringByChar(float delay, String text) {
        super(delay);
        this.text = text;
    }

    public String event(){
        if (textLength < text.length()) {
            textLength++;
        }
        return text.substring(0, textLength);
    }
}
