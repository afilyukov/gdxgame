package com.androidtest.gdxgame;

//import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
//import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.androidtest.gdxgame.MyGdxGame;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.audioDeviceSimultaneousSources = 128;
//		config.width = 1920;
//		config.height = 1080;
//		config.fullscreen = true;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
