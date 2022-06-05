package com.androidtest.gdxgame.EventManager;

import com.badlogic.gdx.Gdx;

public abstract class BaseEvent implements EventInterface {
    private final float delay;
    private float currentTime;

    protected BaseEvent(float delay) { this.delay = delay; }

    public boolean step() {
        currentTime += Gdx.graphics.getDeltaTime();
        if (currentTime >= delay) {
            currentTime =0;
            return true;
        }
        return false;
    }
}