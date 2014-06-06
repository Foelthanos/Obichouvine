package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.models.ia.IA;
import s6.prog6.obichouvine.models.ia.IA.IaType;
import s6.prog6.obichouvine.models.ia.MiniMax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameStatusWidget extends Table{

	private Label p1Pseudo, p1Status, p2Pseudo, p2Status, p1Type, p2Type;
	private TextureRegion p1Icon, p2Icon, p1IconHighlight, p2IconHighlight, p1Current, p2Current;

	private int nbMosc, nbVik;


	public GameStatusWidget(Skin skin, Player p1, Player p2){
		super(skin);

		if( ObichouvineGame.DEV_MODE ) {
			this.debug();
		}
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
		
		p1Icon = atlas.findRegion("Moscovit");
		p2Icon = atlas.findRegion("Suedois");
		p1IconHighlight = atlas.findRegion("MoscovitSelect");
		p2IconHighlight = atlas.findRegion("SuedoisSelect");
		
		
		
		Label.LabelStyle titleStyle = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("skin2/titleFont.fnt")), Color.WHITE);

		nbMosc = 16;
		nbVik = 9;

		p1Pseudo = new Label(p1.getPseudo(), skin);
		p2Pseudo = new Label(p2.getPseudo(), skin);

		p1Status = new Label("Pions restants : "+nbMosc, skin);
		p2Status = new Label("Pions restants : "+nbVik, skin);

		p1Type = new Label("Homo Erectus", skin);
		p2Type = new Label("Homo Erectus", skin);

		if(p1 instanceof IA){
			switch(((IA) p1).getType()){
			case Defensive :
				p1Type.setText("IA :"+IaType.Defensive.toString());
				break;
			case Aggressive :
				p1Type.setText("IA :"+IaType.Aggressive.toString());
				break;
			case Difficile :
				p1Type.setText("IA :"+IaType.Difficile.toString());
				break;
			case Facile :
				p1Type.setText("IA :"+IaType.Facile.toString());
				break;
			default :
				p1Type.setText("Inconnu");
			}
		}
		
		if(p2 instanceof IA){
			switch(((IA) p2).getType()){
			case Defensive :
				p2Type.setText("IA :"+IaType.Defensive.toString());
				break;
			case Aggressive :
				p2Type.setText("IA :"+IaType.Aggressive.toString());
				break;
			case Difficile :
				p2Type.setText("IA :"+IaType.Difficile.toString());
				break;
			case Facile :
				p2Type.setText("IA :"+IaType.Facile.toString());
				break;
			default :
				p2Type.setText("Inconnu");
			}
		}
		
		this.p1Pseudo.setStyle(titleStyle);
		this.p2Pseudo.setStyle(titleStyle);

		this.add(p1Status).left().expandX().fill();
		this.row();
		this.add(p1Type).left().expandX().fill();
		this.row();
		this.add(p1Pseudo).left().expandX().fill();
		//this.add(p1Icon).
		this.row();
		this.add(p2Pseudo).left().expandX().fill();
		this.row();
		this.add(p2Type).left().expandX().fill();
		this.row();
		this.add(p2Status).left().expandX().fill();
		this.row();
	}

	public void killPawn(PawnType type){

	}

	public void updateWidget(int mosc, int vik){
		this.p1Status.setText("Pions restants : "+mosc);
		this.p2Status.setText("Pions restants : "+vik);
	}

}
