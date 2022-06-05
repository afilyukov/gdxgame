package com.androidtest.gdxgame;

import static com.androidtest.gdxgame.GfxUtils.*;

import com.androidtest.gdxgame.screens.GameProc;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class BigShip extends BaseEnemy{
    private List<BaseEnemy> turrets;
    private int turretToDamage;
    private Music music;

    public BigShip(String name, float speed, float health, int column, int lines, int fps) {
        super(name, speed, health, column, lines, fps);
        music = Gdx.audio.newMusic(Gdx.files.internal("370b925a30aca01.mp3"));
        turretToDamage = -1;
        turrets = new ArrayList<>();
        turrets.add(new BaseEnemy("msSmallGun", speed, 10, 4, 1, 16));
        turrets.get(turrets.size()-1).setPosition(new Vector2(position.x+150.0f, position.y + (skin.getHeight()-35.0f)));
        turrets.get(turrets.size()-1).setOrigine(new Vector2(7, 12));
        turrets.get(turrets.size()-1).setDif(0);
        turrets.get(turrets.size()-1).setDamage(0.01f);

        turrets.add(new BaseEnemy("msSmallGun", speed, 10, 4, 1, 16));
        turrets.get(turrets.size()-1).setPosition(new Vector2(position.x+150.0f + 45, position.y + (skin.getHeight()-35.0f)));
        turrets.get(turrets.size()-1).setOrigine(new Vector2(7, 12));
        turrets.get(turrets.size()-1).setDif(0);
        turrets.get(turrets.size()-1).setDamage(0.01f);

        turrets.add(new BaseEnemy("msTurret", speed, 10, 3, 1, 6));
        turrets.get(turrets.size()-1).setPosition(new Vector2(position.x+150.0f + 80, position.y + (skin.getHeight()-50.0f)));
        turrets.get(turrets.size()-1).setOrigine(new Vector2(16, 21));
        turrets.get(turrets.size()-1).setDamage(0.1f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        for (int i = 0; i < turrets.size(); i++) {
            turrets.get(i).draw(batch);
        }
    }

    @Override
    public boolean step() {
        super.step();

        for (int i = 0; i < turrets.size(); i++) {
            if (turrets.get(i).step()) {
                GameProc.life -= turrets.get(i).getDamage();
                music.play();
            }
            turrets.get(i).setRotate();
        }
        return false;
    }

    @Override
    public boolean isHit(Vector2 pos) {
        for (int i = 0; i < turrets.size(); i++) {
            if (turrets.get(i).isHit(pos)) {
                turretToDamage = i;
                return true;
            }
        }
        return super.isHit(pos);
    }

    @Override
    public float damage(float damage) {
        if (turretToDamage>=0) {
            if (turrets.get(turretToDamage).damage(damage)<0) {
                turrets.remove(turretToDamage);
            }
            turretToDamage = -1;
            return health;
        } else return super.damage(damage);
    }
}
