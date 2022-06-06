package com.androidtest.gdxgame.screens;

import com.androidtest.gdxgame.EventManager.EventManager;
import com.androidtest.gdxgame.EventManager.StringByChar;
import com.androidtest.gdxgame.MyFont;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Dialog implements Screen, InputProcessor {
    private final Game game;
    private SpriteBatch batch;
    private MyFont myFont;
    private final int width;
    private EventManager eventManager;
    private Map<Integer, Question> questionList;
    private Map<Integer, Answers> answersList;
    private int power;

    public Dialog(Game game) {
        this.game = game;
        myFont = new MyFont(25);
        batch = new SpriteBatch();
        width = Gdx.graphics.getWidth();
        eventManager = new EventManager();

//        answersList = new HashMap<>();
//        answersList.put(0, new Answers(1, "sgsg"));
//        answersList.put(1, new Answers(-1, "shjkgsg"));
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String str = gson.toJson(answersList);

        FileHandle file = Gdx.files.internal("Json/question.json");
        String text = file.readString("UTF-8");
        Type type = new TypeToken<HashMap<Integer, Question>>(){}.getType();
        questionList = new Gson().fromJson(text, type);
        file = Gdx.files.internal("Json/answer.json");
        text = file.readString("UTF-8");
        type = new TypeToken<HashMap<Integer, Answers>>(){}.getType();
        answersList = new Gson().fromJson(text, type);

        power = 3;

        eventManager.addEvent(new StringByChar(MathUtils.random(0.016f, 0.025f), questionList.get(power).text));
        for (int i=0;i<questionList.get(power).answerList.size();i++){
            eventManager.addEvent(new StringByChar(MathUtils.random(0.016f, 0.025f), answersList.get(questionList.get(power).answerList.get(i)).text));
        }



    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLUE);

        eventManager.step();

        batch.begin();

        myFont.draw(batch, (String) eventManager.getData(0), 0, Gdx.graphics.getHeight()-(myFont.getHight() * 2), width);
        for (int i=1; i<eventManager.size();i++) {
            String tmp = (String) eventManager.getData(i);
            myFont.draw(batch, tmp, 0, Gdx.graphics.getHeight()/2 - (myFont.getHight()*2)*i, width);
        }
        batch.end();

    }

    @Override
    public void dispose() {
        batch.dispose();
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

    @Override
    public void show() {

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

    private class Question{
        private List<Integer> answerList;
        private String text;

        public Question(List<Integer> answerList, String text) {
            this.answerList = answerList;
            this.text = text;
        }
    }

    private class Answers{
        private int power;
        private String text;

        public Answers(int power, String text) {
            this.power = power;
            this.text = text;
        }
    }
}
