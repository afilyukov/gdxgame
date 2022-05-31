package com.androidtest.gdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.androidtest.gdxgame.screens.GameProc.*;

public class Enemy {
    private Vector2 position;
    private float speed, health;
    private Sprite skin;
    private boolean isDamaged;

    public Enemy() {
        isDamaged = false;
        skin = mainAtlas.createSprite("enemy");
        position = new Vector2();
        position.x = Gdx.graphics.getWidth();
        position.y = MathUtils.random(0, Gdx.graphics.getHeight()-skin.getHeight());
        skin.setPosition(position.x, position.y);
        Vector2 headOrigine = new Vector2(skin.getWidth()/2, skin.getRegionHeight()/2);
        skin.setOrigin(headOrigine.x, headOrigine.y);
        skin.setScale(1);
        speed = MathUtils.random(0.25f, 1.0f);
        health = 10;
    }

    public void draw(SpriteBatch batch){
        if (isDamaged) {
            isDamaged = false;
            skin.setColor(Color.RED);
        } else skin.setColor(Color.WHITE);
        skin.draw(batch);
    }

    public void step(){
        position.x -= speed;
        skin.setPosition(position.x, position.y);
    }

    public float damage(float damage){
        isDamaged = true;
        health -= damage;
        return health;
    }

    public boolean isHit(Vector2 pos){
        Rectangle tRect = skin.getBoundingRectangle();
        return  tRect.contains(pos);
    }

    public void dipose(){};
}
