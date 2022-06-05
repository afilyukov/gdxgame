package com.androidtest.gdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class MyFont {
    private BitmapFont cyrillicFont;

    public MyFont(int size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ofont.ru_Tolkien.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = size;
        fontParameter.characters = "ЁЯЧСМИТЬБЮФЫВАПРОЛДЖЭЙЦУКЕНГШЩЗХЪячсмитьбюэждлорёпавыфйцукенгшщзхъ!?.,";
        cyrillicFont = generator.generateFont(fontParameter);
        generator.dispose();
    }

    public void setColor(Color color){cyrillicFont.setColor(color);}
    public void draw(SpriteBatch batch, String string, int x, int y){cyrillicFont.draw(batch, string, x, y);}
    public void draw(SpriteBatch batch, String string, int x, int y, int width){cyrillicFont.draw(batch, string, x, y, width, -1,true );}
    public int getHight(){return (int)cyrillicFont.getCapHeight();}
    public void dispose(){cyrillicFont.dispose();}
}
