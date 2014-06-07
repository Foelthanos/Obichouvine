package s6.prog6.obichouvine.utils;

import s6.prog6.obichouvine.ObichouvineGame;
import s6.prog6.obichouvine.models.Pawn.PawnType;
import s6.prog6.obichouvine.models.Player;
import s6.prog6.obichouvine.models.ia.IA;
import s6.prog6.obichouvine.models.ia.IA.IaType;
import s6.prog6.obichouvine.models.ia.MiniMax;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class GameStatusWidget extends Table{

	private Label p1Pseudo, p1Status, p2Pseudo, p2Status, p1Type, p2Type;
	private TextureRegion p1Icon, p2Icon, p1IconHighlight, p2IconHighlight;
	private Image p1Current, p2Current, p1Load, p2Load;
	private int nbMosc, nbVik;

	public Animation anim;

	public float stateTime;
	public PawnType turn;
	
	public boolean p1Computing, p2Computing;
	
	public GameStatusWidget(Skin skin, Player p1, Player p2){
		super(skin);

		if( ObichouvineGame.DEV_MODE ) {
			this.debug();
		}
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images-atlases/pages.atlas"));
		
		this.anim = com.holidaystudios.tools.GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("images-atlases/loading.gif").read());
		stateTime = 0f;
		//Image load = new 
		
		p1Icon = atlas.findRegion("Moscovit");
		p2Icon = atlas.findRegion("Suedois");
		p1IconHighlight = atlas.findRegion("MoscovitSelect");
		p2IconHighlight = atlas.findRegion("SuedoisSelect");
		
		p1Load = new Image(anim.getKeyFrame(stateTime));
		p2Load = new Image(anim.getKeyFrame(stateTime));
		
		p1Load.setVisible(false);
		p2Load.setVisible(false);
		
		p1Current = new Image((turn==PawnType.MOSCOVITE)?p1IconHighlight:p1Icon);
		p2Current = new Image((turn==PawnType.SUEDOIS)?p2IconHighlight:p2Icon);
		
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
		this.add(p1Load).size(40, 40);
		this.row();
		this.add(p1Type).left().expandX().fill();
		this.row();
		this.add(p1Pseudo).left().expandX().fill();
		this.add(p1Current).expandX().fill().size(40, 40);
		this.row();
		this.add(p2Pseudo).left().expandX().fill();
		this.add(p2Current).expandX().fill().size(40, 40);
		this.row();
		this.add(p2Type).left().expandX().fill();
		this.row();
		this.add(p2Status).left().expandX().fill();
		this.add(p2Load).size(40, 40);;
		this.row();
	}

	public void updateWidget(int mosc, int vik){
		stateTime += Gdx.graphics.getDeltaTime();
		this.p1Status.setText("Pions restants : "+mosc);
		this.p2Status.setText("Pions restants : "+vik);
		
		if(p1Computing == true)
			p1Load.setVisible(true);
		else
			p1Load.setVisible(false);
		
		if(p2Computing == true)
			p2Load.setVisible(true);
		else
			p2Load.setVisible(false);
		
		p1Current.setDrawable(new TextureRegionDrawable((turn==PawnType.MOSCOVITE)?p1IconHighlight:p1Icon));
		p1Load.setDrawable(new TextureRegionDrawable(anim.getKeyFrame(stateTime)));
		p2Current.setDrawable(new TextureRegionDrawable((turn==PawnType.SUEDOIS)?p2IconHighlight:p2Icon));
		p2Load.setDrawable(new TextureRegionDrawable(anim.getKeyFrame(stateTime)));
		
		
	}

	public void switchTurn() {
		// TODO Auto-generated method stub
		this.turn = (this.turn==PawnType.SUEDOIS)?PawnType.MOSCOVITE:PawnType.SUEDOIS;
	}

}
