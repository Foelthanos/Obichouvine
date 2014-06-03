package s6.prog6.obichouvine.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
//import com.badlogic.gdx.tools.hiero.*;

public class ObiPacker {
        public static void main (String[] args) throws Exception {
                TexturePacker2.process("assets/spriteSource", "assets/images-atlases", "pages");
        	//TexturePacker2.process("assets/skinSprite/obiSkin", "assets/obiSkin", "obiSkin");
        	//Hiero.main(args);
        }
}
