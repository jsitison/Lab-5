package pkgCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import pkgEnum.eCardDestination;
import pkgEnum.eCardNo;
import pkgEnum.eCardVisibility;
import pkgEnum.eGameType;
import pkgEnum.eDrawCount;

public class Rule  implements Serializable  {

	private int MaxNumberOfPlayers;
	private int PlayerNumberOfCards;
	private int NumberOfJokers;
	private int PlayerCardsMin;
	private int PlayerCardsMax;
	private int CommunityCardsMin;
	private int CommunityCardsMax;
	private int PossibleHandCombinations;
	private ArrayList<Card> WildCards = new ArrayList<Card>();
	private TreeMap hmCardDraw = new TreeMap<Integer, CardDraw>();
	
	public Rule(eGameType gme) {
		
		switch (gme)
		{
		case BLACKJACK:
			MaxNumberOfPlayers = 4;
			PlayerNumberOfCards = 999;
			NumberOfJokers = 0;
			PlayerCardsMin = 2;
			PlayerCardsMax = 999;
			CommunityCardsMin = 0;
			CommunityCardsMax = 0;
			PossibleHandCombinations = 0;
			hmCardDraw.put(eDrawCount.DEFAULT, new CardDraw(eCardNo.FIRST,eCardDestination.PLAYER,eCardVisibility.EVERYONE));
			
			
			break;
		case POKER:
			break;
		}		
	}
	
}
