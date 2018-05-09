package pkgCore;

import pkgEnum.eCardDestination;
import pkgEnum.eCardNo;
import pkgEnum.eCardVisibility;

public class CardDraw {

	private eCardNo CardCount;
	private eCardDestination CardDestination;
	private eCardVisibility CardVisibility;
	
	public CardDraw(eCardNo cardCount, eCardDestination cardDestination, eCardVisibility cardVisiblity) {
		super();
		CardCount = cardCount;
		CardDestination = cardDestination;
		CardVisibility = CardVisibility;
	}
	public eCardNo getCardCount() {
		return CardCount;
	}
	public eCardDestination getCardDestination() {
		return CardDestination;
	}
	public eCardVisibility getCardVisibility() {
		return CardVisibility;
	}
}




