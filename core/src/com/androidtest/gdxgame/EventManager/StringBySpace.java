package com.androidtest.gdxgame.EventManager;

public class StringBySpace extends BaseEvent{
    private final String text;
    private int textLength;

    public StringBySpace(float delay, String text) {
        super(delay);
        this.text = text;
    }

    public String event(){
        if (textLength < text.length()) {
            textLength++;
        }
        return text.subSequence(textLength, text.length()).toString();
    }
}