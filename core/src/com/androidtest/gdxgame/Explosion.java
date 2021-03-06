package com.androidtest.gdxgame;

import static com.androidtest.gdxgame.GfxUtils.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion {
    private NewAnimation animation;
    private Music music;
    private Vector2 position;
    private float damage;

    public Explosion(TextureRegion region, Animation.PlayMode mode, int columns, int lines, int fps, String mName, float damage){
        animation = new NewAnimation(region, mode, columns, lines, fps);
        music = Gdx.audio.newMusic(Gdx.files.internal(mName));
        music.play();
        position = getPosition(animation.getRegion().getRegionWidth(), animation.getRegion().getRegionHeight());
        this.damage = damage;
    }

    public void setDamage(float damage) { this.damage = damage;}

    public float getDamage(){return damage;}

    public void setTime(float dTime) { animation.setTime(dTime);}
    public Vector2 getPos() {return position;}
    public TextureRegion getRegion() {return animation.getRegion();}
    public boolean isFinished(){return animation.isFinished();}
    public void dispose(){
        animation.dispose();
        music.dispose();
    }
}
