package com.androidtest.gdxgame;

import static com.androidtest.gdxgame.GfxUtils.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	NewAnimation turretAnm, bodyAnim, headAnim;
	List<Explosion> explosions;
	Sprite headSpr;
	TextureAtlas mainAtlas;
	float x, y;
	int cnt;

	@Override
	public void create () {
		cnt = 0;
		batch = new SpriteBatch();
		x = Gdx.graphics.getWidth()+100;

		y = new Random().nextInt(Gdx.graphics.getHeight());
		shapeRenderer = new ShapeRenderer();
		mainAtlas = new TextureAtlas("atlas/main.atlas");
		explosions = new ArrayList<>();
  	turretAnm = new NewAnimation(mainAtlas.findRegion("turret-sprites-deployment"), Animation.PlayMode.NORMAL, 8, 1, 8);
		bodyAnim = new NewAnimation(mainAtlas.findRegion("turret-sprites-body"), Animation.PlayMode.LOOP, 2, 1, 16);
		headAnim = new NewAnimation(mainAtlas.findRegion("turret-sprites-head-shot-idle"), Animation.PlayMode.NORMAL, 5, 1, 60);
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
		ScreenUtils.clear(Color.FOREST);

		boolean fire =false;
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) fire=true;

		headSpr = new Sprite(mainAtlas.findRegion("msTurret"));
		Vector2 headOrigin = new Vector2(headSpr.getWidth()/2, headSpr.getRegionHeight()/2);
		headSpr.setOrigin(headOrigin.x, headOrigin.y);
		headSpr.setScale(1);
		headSpr.setColor(Color.PINK);
		x -= 1f;
		batch.begin();
			Vector2 headPosition = new Vector2(x, y);
			headSpr.setPosition(headPosition.x - headOrigin.x, headPosition.y - headOrigin.y);
			//headSpr.setRotation(getAngle(headPosition)+90);
			headSpr.draw(batch);
			batch.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.line(getPosition().x-10, getPosition().y, getPosition().x+10, getPosition().y);
		shapeRenderer.line(getPosition().x, getPosition().y-10, getPosition().x, getPosition().y+10);
		shapeRenderer.end();

		batch.begin();
		if (!turretAnm.isFinished()) {
			turretAnm.setTime(Gdx.graphics.getDeltaTime());
			batch.draw(turretAnm.getRegion(), 0, 0);
		} else {
			bodyAnim.setTime(Gdx.graphics.getDeltaTime());
			batch.draw(bodyAnim.getRegion(), 0, 0);
			batch.draw(headAnim.getRegion(), 11, 12,14,22, headAnim.getRegion().getRegionWidth(),headAnim.getRegion().getRegionHeight(), 1,1,getAngle(new Vector2(0,0)),false);
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
		batch.end();

		if ((fire & !headAnim.isFinished()) || (!fire & !headAnim.isFinished())) headAnim.setTime(Gdx.graphics.getDeltaTime());
		if (fire & headAnim.isFinished()) {
			headAnim.resetTime();
			explosions.add(new Explosion(mainAtlas.findRegion("explosion"), Animation.PlayMode.NORMAL, 4, 4, 16, "boom.mp3"));
			if (headSpr.getBoundingRectangle().contains(getPosition())){
				x=Gdx.graphics.getWidth()+100;
				y=MathUtils.random(0, Gdx.graphics.getHeight()-headSpr.getHeight());
				cnt++;
			}
		}
		Gdx.graphics.setTitle("Спрайтов подбито: "+String.valueOf(cnt));
	}

	@Override
	public void dispose () {
		batch.dispose();
		bodyAnim.dispose();
		headAnim.dispose();
		turretAnm.dispose();
		shapeRenderer.dispose();
	}
}
