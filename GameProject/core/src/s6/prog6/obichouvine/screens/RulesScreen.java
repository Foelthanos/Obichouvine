package s6.prog6.obichouvine.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.controllers.SoundManager.ObiSound;
import s6.prog6.obichouvine.utils.DefaultInputListener;

public class RulesScreen extends AbstractScreen {

	public RulesScreen(ObichouvineGame game) {
		super(game);
	}
	
    @Override
    public void show()
    {
        super.show();

        // retrieve the default table actor
        Table table = super.getTable();
        table.defaults().spaceBottom( 10 );
        table.columnDefaults( 0 ).padRight( 20 );
        
        table.add( "Presentation" ).colspan( 3 );
        table.row();
        table.add("Le Tablut est un jeu strategique qui se joue à deux sur un terrain 9*9.\nLe jeu comporte seize pions jaunes (appelés aussi moscovites),\nhuit pions verts (soldats) et un gros pion vert (le roi).\nNote le trone occupe par le roi en debut de partie est interdite Ã  tout autres pions");
        table.row();
        table.add("But du jeu");
        table.row();
        table.add("le roi doit parvenir a une des cases forteresse.\nS'il s'echappe, il a gagne. \nLes moscovites doivent eux capturer le roi. S'ils le font prisonnier ils ont gagne.");
        table.row();
        table.add("Regle");
        table.row();
        table.add("1.les pions autres que le roi n'ont pas non plus le droit de passer sur le trone.\n2. prise : le roi peut manger uniquement s'il a l'initiative\n(il ne peut pas servir de pillier)exeption si le roi est sur le trone.\n3.les moscovites commencent;\n");
        // register the back button
        TextButton backButton = new TextButton( "Retour au menu principal", getSkin() );
        backButton.addListener( new DefaultInputListener() {
            @Override
            public void touchUp(
                InputEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play( ObiSound.CLICK );
                game.setScreen( new MenuScreen( game ) );
            }
        } );
        table.row();
        table.add( backButton ).size( 250, 60 ).colspan( 3 );
    }
	

}
