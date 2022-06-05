package com.androidtest.gdxgame.screens;

import com.androidtest.gdxgame.MyFont;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import static com.androidtest.gdxgame.GfxUtils.*;

public class MainMenu implements Screen, InputProcessor {
    final Game game;
    SpriteBatch batch;
    MyFont myFont;
    Sprite playSpr, playHSpr, helpSpr, helpHSpr, quitSpr, quitHSpr;
    Texture fon;
    Rectangle playRect,helpRect, quitRect;
    boolean play, help, quit;
    OrthographicCamera camera;

    TextureAtlas atlas;

    public MainMenu(Game game){
        this.game = game;

        fon = new Texture("fon.jpg");
        play = help = quit = false;

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.x = Gdx.graphics.getWidth()/2;
        camera.position.y = Gdx.graphics.getHeight()/2;
        camera.update();

        myFont = new MyFont((int)(Gdx.graphics.getWidth()*0.05f));
        myFont.setColor(Color.YELLOW);

        atlas = new TextureAtlas("atlas/main.atlas");
        playSpr = atlas.createSprite("PlayButton");
        playSpr.setPosition(Gdx.graphics.getWidth()/2 - playSpr.getWidth()/2, Gdx.graphics.getHeight()/2 + playSpr.getHeight()*0.55f - myFont.getHight());
        playRect = playSpr.getBoundingRectangle();
        playHSpr = atlas.createSprite("PlayButtonHighlight");
        playHSpr.setPosition(playSpr.getX(), playSpr.getY());

        helpSpr = atlas.createSprite("HelpButton");
        helpSpr.setPosition(Gdx.graphics.getWidth()/2 - helpSpr.getWidth()/2, Gdx.graphics.getHeight()/2 - helpSpr.getHeight()/2 - myFont.getHight());
        helpRect = helpSpr.getBoundingRectangle();
        helpHSpr = atlas.createSprite("HelpButtonHighlight");
        helpHSpr.setPosition(helpSpr.getX(), helpSpr.getY());

        quitSpr = atlas.createSprite("QuitButton");
        quitSpr.setPosition(Gdx.graphics.getWidth()/2 - quitSpr.getWidth()/2, Gdx.graphics.getHeight()/2 - quitSpr.getHeight()*1.55f - myFont.getHight());
        quitRect = quitSpr.getBoundingRectangle();
        quitHSpr = atlas.createSprite("QuitButtonHighlight");
        quitHSpr.setPosition(quitSpr.getX(), quitSpr.getY());

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLUE);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(fon, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        myFont.draw(batch, "Далёкое расстояние!", 0, Gdx.graphics.getHeight()- myFont.getHight());

        if (play) playSpr.draw(batch); else playHSpr.draw(batch);
        if (help) helpSpr.draw(batch); else helpHSpr.draw(batch);
        if (quit) quitSpr.draw(batch); else quitHSpr.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.position.x = Gdx.graphics.getWidth()/2;
        camera.position.y = Gdx.graphics.getHeight()/2;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        myFont.dispose();
        atlas.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (quit) {
            dispose();
            game.dispose();
            Gdx.app.exit();
        }
        if (play) {
            dispose();
            game.setScreen(new GameProc(game));
        }
        if (help) {
            dispose();
            game.setScreen(new Dialog(game));
        }
       return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector2 tmpVec = getPosition();
        play = help = quit = false;

        if (playRect.contains(tmpVec)) play = true;
        if (quitRect.contains(tmpVec)) quit = true;
        if (helpRect.contains(tmpVec)) help = true;

        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
