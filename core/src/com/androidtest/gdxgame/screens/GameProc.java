package com.androidtest.gdxgame.screens;

import com.androidtest.gdxgame.BaseEnemy;
import com.androidtest.gdxgame.BigShip;
import com.androidtest.gdxgame.Explosion;
import com.androidtest.gdxgame.NewAnimation;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static com.androidtest.gdxgame.GfxUtils.getAngle;
import static com.androidtest.gdxgame.GfxUtils.getPosition;

public class GameProc implements Screen, InputProcessor {
    final Game game;

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    NewAnimation turretAnm, bodyAnim, headAnim;
    List<Explosion> explosions;
    Sprite headSpr, tBase;
    public static TextureAtlas mainAtlas;
    float x, y;
    int cnt, enemyOnScreen, enemyAll;
    List<BigShip> enemyList;
    float enemyTime, enemyTimeCnt, damage;
    private Texture fon, turretBase;
    public static float life;
    private ShaderProgram shaderNormal, shaderInvert, shaderAlpha;

    public GameProc(Game game){
        this.game = game;
        Gdx.input.setInputProcessor(this);

        life = 100;

        fon = new Texture("gameFon.png");
        turretBase = new Texture("gameTurretBase.png");

        tBase = new Sprite(turretBase);
        tBase.setPosition(-150,-60);
        tBase.setOrigin(turretBase.getWidth()/2, turretBase.getHeight()/2);
        tBase.setRotation(6);

        enemyList = new ArrayList<>();
        enemyOnScreen = 4;
        enemyTime = 1;
        enemyTimeCnt = 0;
        enemyAll = 8;

        damage = 1;
        cnt = 0;
        batch = new SpriteBatch();
        x = Gdx.graphics.getWidth()+100;
        shapeRenderer = new ShapeRenderer();
        mainAtlas = new TextureAtlas("atlas/main.atlas");
        explosions = new ArrayList<>();
        turretAnm = new NewAnimation(mainAtlas.findRegion("turret-sprites-deployment"), Animation.PlayMode.NORMAL, 8, 1, 8);
        bodyAnim = new NewAnimation(mainAtlas.findRegion("turret-sprites-body"), Animation.PlayMode.LOOP, 2, 1, 16);
        headAnim = new NewAnimation(mainAtlas.findRegion("turret-sprites-head-shot-idle"), Animation.PlayMode.NORMAL, 5, 1, 60);

        String vertex = Gdx.files.internal("shaders/normal/VERTEX").readString();
        String fragment = Gdx.files.internal("shaders/normal/FRAGMENT").readString();
        shaderNormal = new ShaderProgram(vertex, fragment);
        vertex = Gdx.files.internal("shaders/invert/VERTEX").readString();
        fragment = Gdx.files.internal("shaders/invert/FRAGMENT").readString();
        shaderInvert = new ShaderProgram(vertex, fragment);
        vertex = Gdx.files.internal("shaders/grayscale/VERTEX").readString();
        fragment = Gdx.files.internal("shaders/grayscale/FRAGMENT").readString();
        shaderAlpha = new ShaderProgram(vertex, fragment);

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        if (life < 0 ) {
            dispose();
            game.setScreen(new MainMenu(game));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        ScreenUtils.clear(Color.FOREST);

        boolean fire =false;
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) fire=true;

        batch.begin();
        batch.draw(fon, 0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.setShader(shaderAlpha);
        shaderAlpha.setUniformf("inFloat", life/100.0f);

        tBase.draw(batch);
        if (!turretAnm.isFinished()) {
            turretAnm.setTime(Gdx.graphics.getDeltaTime());
            batch.draw(turretAnm.getRegion(), 0, 0);
        } else {
            bodyAnim.setTime(Gdx.graphics.getDeltaTime());
            batch.draw(bodyAnim.getRegion(), 0, 0);
            batch.draw(headAnim.getRegion(), 11, 12,14,22, headAnim.getRegion().getRegionWidth(),headAnim.getRegion().getRegionHeight(), 1,1,getAngle(new Vector2(0,0)),false);
        }
        batch.setShader(shaderNormal);
        ListIterator<BigShip> iterator1  = enemyList.listIterator();
        while (iterator1.hasNext()){
            BaseEnemy enemy = iterator1.next();
            enemy.step();
            enemy.draw(batch);
            Vector2 tmpVector = enemy.getPosition();
            if (tmpVector.x < -enemy.getSkin().getWidth()) life = -1;
        }

        ListIterator<Explosion> iterator = explosions.listIterator();
        while (iterator.hasNext()){
            Explosion ex = iterator.next();
            ex.setTime(Gdx.graphics.getDeltaTime());
            if (ex.isFinished()) {
                ex.dispose();
                iterator.remove();
            } else {
                batch.draw(ex.getRegion(), ex.getPos().x, ex.getPos().y);
            }
        }

        ShaderProgram s = batch.getShader();
        batch.end();

        if ((fire & !headAnim.isFinished()) || (!fire & !headAnim.isFinished())) headAnim.setTime(Gdx.graphics.getDeltaTime());
        if (fire & headAnim.isFinished()) {
            headAnim.resetTime();
            explosions.add(new Explosion(mainAtlas.findRegion("explosion"), Animation.PlayMode.NORMAL, 4, 4, 16, "370b925a30aca01.mp3", damage));
            iterator1  = enemyList.listIterator(enemyList.size());
            while (iterator1.hasPrevious()){
                BaseEnemy enemy = iterator1.previous();
                if (enemy.isHit(getPosition())) {
                    if (enemy.damage(explosions.get(explosions.size()-1).getDamage())<0) iterator1.remove();
                    explosions.get(explosions.size()-1).setDamage(0);
                    cnt++;
                    break;
                }
            }
        }
        Gdx.graphics.setTitle("Врагов подбито: "+String.valueOf(cnt));

        enemyTimeCnt += Gdx.graphics.getDeltaTime();
        if (enemyTimeCnt > enemyTime && enemyList.size()<enemyOnScreen) {
            enemyTimeCnt = 0;
            enemyList.add(new BigShip("enemy", 0.5f, 50, 1, 1, 16));
            enemyAll--;
        }

        if (enemyAll<1 && enemyList.size()<1) {
            dispose();
            game.setScreen(new GameProc(game));
        }
    }

    @Override
    public void resize(int width, int height) {

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
        bodyAnim.dispose();
        headAnim.dispose();
        turretAnm.dispose();
        shapeRenderer.dispose();
        fon.dispose();
        turretBase.dispose();
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
