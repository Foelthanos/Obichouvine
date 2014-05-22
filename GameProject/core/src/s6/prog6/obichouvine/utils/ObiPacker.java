package s6.prog6.obichouvine.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;


public class ObiPacker {
        public static void main (String[] args) throws Exception {
                TexturePacker2.process("assets/spriteSource", "assets/images-atlases", "pages");
        }
}
