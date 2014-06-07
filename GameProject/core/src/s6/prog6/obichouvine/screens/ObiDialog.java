package s6.prog6.obichouvine.screens;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ObiDialog extends Dialog{
	Skin skin;
	public ObiDialog(String title, Skin skin) {
		super(title, skin);
		this.skin = skin;
		this.initialize();
		// TODO Auto-generated constructor stub
	}

	private void initialize() {  
        padTop(60); // set padding on top of the dialog title  
        getButtonTable().defaults().height(60); // set buttons height  
        setModal(true);  
        setMovable(false);  
        setResizable(false);  
    }  
	
	 public ObiDialog text(String text) {  
         super.text(new Label(text, skin , "medium-green"));  
         return this;  
     }  
	 
	 /**  
      * Adds a text button to the button table.  
      * @param listener the input listener that will be attached to the button.  
      */  
     public ObiDialog button(String buttonText, InputListener listener) {  
         TextButton button = new TextButton(buttonText, this.skin);  
         button.addListener(listener);  
         button(button);  
         return this;  
     }  
   
     @Override  
     public float getPrefWidth() {  
         // force dialog width  
         return 480f;  
     }  
   
     @Override  
     public float getPrefHeight() {  
         // force dialog height  
         return 240f;  
     }  

}
