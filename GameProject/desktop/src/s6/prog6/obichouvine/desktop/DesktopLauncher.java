package s6.prog6.obichouvine.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import s6.prog6.obichouvine.ObichouvineGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		config.title = "Obichouvine";
		config.fullscreen = true;
		config.resizable = false;
		/*config.width = 800;
		config.height = 480;*/
		new LwjglApplication(new ObichouvineGame(), config);
	}
}
