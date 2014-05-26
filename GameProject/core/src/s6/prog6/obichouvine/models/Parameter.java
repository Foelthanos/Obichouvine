package s6.prog6.obichouvine.models;

public class Parameter {

	public enum EscapeMethod {

		Edge("Bord"), Corner("Coin"), EdgeWithoutMosco("Bord sauf rouge");

		String text;

		EscapeMethod(String text){
			this.text = text;
		}
	}

	public enum KingCaptureMethod{
		Can("Possible"), Cannot("Impossible"), NotAPillar("Pas un pilier");

		String text;

		KingCaptureMethod(String text){
			this.text = text;
		}
	}

	public enum KingMoveMethod{
		Unlimited("Illimité"), FourBlock("4 cases"), WithoutMosco("Obstrué"); 
		
		String text;

		KingMoveMethod(String text){
			this.text = text;
		}
	}

	public EscapeMethod esc;
	public KingCaptureMethod kingCap;
	public KingMoveMethod kingMove;

	public Parameter(EscapeMethod esc, KingCaptureMethod kingCap, KingMoveMethod kingMove){
		this.esc = esc;
		this.kingCap = kingCap;
		this.kingMove = kingMove;
	}

}
