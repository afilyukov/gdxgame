package com.androidtest.gdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GfxUtils {

    public static float getAngle(Vector2 pos) { return 360 - MathUtils.atan2(getPosition().x-pos.x, getPosition().y-pos.y) * MathUtils.radiansToDegrees; }
    public static float getAngle(Vector2 from, Vector2 to) { return 360 - MathUtils.atan2(to.x-from.x, to.y-from.y) * MathUtils.radiansToDegrees; }

    public static Vector2 getPosition() {
        float x = Gdx.input.getX();
        float y = Gdx.graphics.getHeight() - Gdx.input.getY();
        return new Vector2(x,y);
    }

    public static Vector2 getPosition(int width, int height){
        float x = Gdx.input.getX();
        float y = Gdx.graphics.getHeight() - Gdx.input.getY();
        x -= width/2;
        x = (x<Gdx.graphics.getWidth() - width) ? x : Gdx.graphics.getWidth()-width;
        x = (x<0) ? 0 : x;
        y -= height/2;
        y = (y<Gdx.graphics.getHeight() - height) ? y : Gdx.graphics.getHeight()-height;
        y = (y<0) ? 0 : y;
        return new Vector2(x,y);
    }
}
