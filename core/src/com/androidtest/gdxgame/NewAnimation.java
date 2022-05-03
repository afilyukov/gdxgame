package com.androidtest.gdxgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NewAnimation {
    private Animation<TextureRegion> animation;
    private float time;
    private Texture texture;
    public NewAnimation(String name, Animation.PlayMode mode, int columns, int lines, int fps){
        texture = new Texture(name);
        TextureRegion[][] tmpRegion = new TextureRegion(texture).split(texture.getWidth()/columns, texture.getHeight()/lines);
        TextureRegion[] regions = new TextureRegion[tmpRegion.length * tmpRegion[0].length];
        int cnt =0;
        for (int i = 0; i < tmpRegion.length; i++) {
            for (int j = 0; j < tmpRegion[0].length; j++) {
                regions[cnt++] = tmpRegion[i][j];
            }
        }
        time = 0;
        animation = new Animation<TextureRegion>(1.0f/fps, regions);
        animation.setPlayMode(mode);
    }

    public TextureRegion getRegion() {return animation.getKeyFrame(time);}
    public void setTime(float dTime){time+=dTime;}
    public boolean isFinished(){return animation.isAnimationFinished(time);}
    public void resetTime(){time=0;}
    public void dispose(){texture.dispose();}
}
