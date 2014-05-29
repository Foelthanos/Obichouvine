package s6.prog6.obichouvine.models;

public class Parameter {

	public enum FirstStrike {
		
		Moscovite("Moscovite"), Suedois("Suedois");
		
		public String text;

		FirstStrike(String text){
			this.text = text;
		}
		public String toString(){
			return this.text;
		}
	}
	public enum EscapeMethod {

		Edge("Bord"), Corner("Coin"), EdgeWithoutMosco("Bord sauf rouge");

		public String text;

		EscapeMethod(String text){
			this.text = text;
		}
		public String toString(){
			return this.text;
		}
	}

	public enum KingCaptureMethod{
		Can("Possible"), Cannot("Impossible"), NotAPillar("Pas un pilier");

		public String text;

		KingCaptureMethod(String text){
			this.text = text;
		}
		public String toString(){
			return this.text;
		}
	}

	public enum KingMoveMethod{
		Unlimited("Illimité"), FourBlock("4 cases"), WithoutMosco("Obstrué"); 
		
		public String text;

		KingMoveMethod(String text){
			this.text = text;
		}
		public String toString(){
			return this.text;
		}
	}

	private EscapeMethod esc;
	private KingCaptureMethod kingCap;
	private KingMoveMethod kingMove;
	private FirstStrike fStrike;

	
	public Parameter(EscapeMethod esc, KingCaptureMethod kingCap, KingMoveMethod kingMove, FirstStrike fStrike){
		this.esc = esc;
		this.kingCap = kingCap;
		this.kingMove = kingMove;
		this.fStrike = fStrike;
	}
	
	public EscapeMethod getEsc() {
		return esc;
	}

	public void setEsc(EscapeMethod esc) {
		this.esc = esc;
	}

	public KingCaptureMethod getKingCap() {
		return kingCap;
	}

	public void setKingCap(KingCaptureMethod kingCap) {
		this.kingCap = kingCap;
	}

	public KingMoveMethod getKingMove() {
		return kingMove;
	}

	public void setKingMove(KingMoveMethod kingMove) {
		this.kingMove = kingMove;
	}
	
	public FirstStrike getfStrike() {
		return fStrike;
	}

	public void setfStrike(FirstStrike fStrike) {
		this.fStrike = fStrike;
	}

	

}
