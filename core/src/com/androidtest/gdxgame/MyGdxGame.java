package com.androidtest.gdxgame;

import static com.androidtest.gdxgame.GfxUtils.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	NewAnimation turretAnm, bodyAnim, headAnim;
	List<Explosion> explosions;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		explosions = new ArrayList<>();
		turretAnm = new NewAnimation("turret-sprites-deployment.png", Animation.PlayMode.NORMAL, 8, 1, 8);
		bodyAnim = new NewAnimation("turret-sprites-body.png", Animation.PlayMode.LOOP, 2, 1, 16);
		headAnim = new NewAnimation("turret-sprites-head-shot-idle.png", Animation.PlayMode.NORMAL, 5, 1, 60);
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.FOREST);
		float rotation = 360 - MathUtils.atan2(getPosition().x-25, getPosition().y-34) * MathUtils.radiansToDegrees;
		boolean fire =false;
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) fire=true;

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
			batch.draw(headAnim.getRegion(), 11, 12,14,22, headAnim.getRegion().getRegionWidth(),headAnim.getRegion().getRegionHeight(), 1,1,rotation,false);
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
			explosions.add(new Explosion("79a0eceb71bb6a9939df582317038b81.png", Animation.PlayMode.NORMAL, 4, 4, 16, "370b925a30aca01.mp3"));
		}
		Gdx.graphics.setTitle(String.valueOf(explosions.size()));
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
