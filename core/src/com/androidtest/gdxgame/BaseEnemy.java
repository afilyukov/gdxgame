package com.androidtest.gdxgame;

import static com.androidtest.gdxgame.screens.GameProc.mainAtlas;
import static com.androidtest.gdxgame.GfxUtils.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

public class BaseEnemy {
    protected Vector2 position, origine;
    protected float health, speed, damage, rotDif;
    protected Sprite skin;
    protected boolean isDamaged;
    NewAnimation animation;

    public Sprite getSkin() {
        return skin;
    }

    public BaseEnemy(String name, float speed, float health, int anmColumns, int anmLines, int anmFps){
        isDamaged = false;
        animation = new NewAnimation(mainAtlas.findRegion(name), Animation.PlayMode.NORMAL, anmColumns, anmLines, anmFps);
        animation.setTime(0);
        skin = new Sprite(animation.getRegion());
        position = new Vector2();
        position.x = Gdx.graphics.getWidth();
        position.y = MathUtils.random(Gdx.graphics.getHeight()/3, Gdx.graphics.getHeight()-skin.getHeight());
        skin.setPosition(position.x, position.y);
        skin.setScale(1);
        this.speed = speed;
        this.health = health;
        rotDif = 90;
    }

    public float getDamage() {
        return damage;
    }

    public float damage(float damage){
        isDamaged = true;
        health -= damage;
        return health;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setRotate(){
        skin.setRotation(getAngle(position, new Vector2(0,0))+rotDif);
//        skin.setRotation(getAngle(position)+rotDif);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setDif(float dif) {
        this.rotDif = dif;
    }

    public void setOrigine(Vector2 origine) {
        this.origine = origine;
        skin.setOrigin(origine.x, skin.getHeight() - origine.y);
    }

    public boolean step(){
        position.x -= speed;
        skin.setPosition(position.x, position.y);
        animation.setTime(Gdx.graphics.getDeltaTime());
        if (animation.isFinished()) {
            animation.resetTime();
            return true;
        }
        return false;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        skin.setPosition(this.position.x, this.position.y);
    }

    public boolean isHit(Vector2 pos){
        Rectangle tRect = skin.getBoundingRectangle();
        return  tRect.contains(pos);
    }

    public void draw(SpriteBatch batch){
        if (isDamaged) {
            isDamaged = false;
            skin.setColor(Color.RED);
        } else skin.setColor(Color.WHITE);
        skin.setRegion(animation.getRegion());
        skin.draw(batch);
    }
}
