package pkgCore;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import pkgEnum.eRank;
import pkgEnum.eSuit;

public class GamePlayBlackJackTest {

	@Test
	public void TestPlayerWinning() {
 
		Table t = new Table();
		Player p1 = new Player("Bert", 1);
		Player p2 = new Player("Joe", 2);
		
		t.AddPlayerToTable(p1);
		t.AddPlayerToTable(p2);
		Deck d = new Deck();
		
		GamePlayBlackJack gpBJ = new GamePlayBlackJack(t.getHmTablePlayer(),d);
		
		Iterator it = gpBJ.getHmGameHands().entrySet().iterator();

		ArrayList<Card> dcards = new ArrayList<Card>();
		dcards.add(new Card(eSuit.DIAMONDS, eRank.NINE));
		dcards.add(new Card(eSuit.DIAMONDS, eRank.NINE));		
		HandBlackJack hDealer = HandHelper(dcards, gpBJ.gethDealer().getHandID());
		
		gpBJ.sethDealer(hDealer);
	 
		ArrayList<Card> p1cards = new ArrayList<Card>();
		p1cards.add(new Card(eSuit.DIAMONDS, eRank.TEN));
		p1cards.add(new Card(eSuit.DIAMONDS, eRank.TEN));
		
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();

			HandBlackJack hBJ = (HandBlackJack) pair.getValue();
			GamePlayerHand GPH = (GamePlayerHand)pair.getKey();
			
			if (GPH.getPlayerID() == p1.getPlayerID())
			{
				hBJ = HandHelper(p1cards, hBJ.getHandID());	
				GamePlayerHand pGPH = new GamePlayerHand(GPH.getGameID(),GPH.getPlayerID(),hBJ.getHandID());				
				gpBJ.putHandToGame(pGPH, hBJ);				
				
				hBJ = (HandBlackJack) gpBJ.gethmGameHand(pGPH);
				
				System.out.println(hBJ);
				
			}

			
				
			
			//HandScoreBlackJack hsbj = (HandScoreBlackJack)hBJ.ScoreHand();			
			//assertEquals((int)hsbj.getNumericScores().get(0),20);			
			//gpBJ.putHandToGame(GPH, hBJ);				
		}
		
		
		
	}

	@Test
	public void TestPlayerLosing() {
		
	}
	
	@Test
	public void TestPlayerPush() {
		
	}
	
	@Test
	public void TestTwoPlayersWinning()
	{
		
	}
	
	
	public GamePlayBlackJack SetDealerHand(GamePlayBlackJack gpBJ, HandBlackJack hBJ )
	{
			
		return gpBJ;
	}
	
	public HandBlackJack HandHelper(ArrayList<Card> cards, UUID HandID) {

		HandBlackJack result = null;

		try {
			
			//	c = structure of class 'Hand'
			Class<?> c = Class.forName("pkgCore.HandBlackJack");

			//	the arguements for 'AddCard' is a instance of Card
			Class[] cArgsAddCard = new Class[1];
			cArgsAddCard[0] = pkgCore.Card.class;

			//	Create an instance of Hand
			Object inst = c.newInstance();

			//	Find the method 'AddCard' in Hand
			Method mAddCard = c.getDeclaredMethod("AddCardToHand", cArgsAddCard);
			
			//	Make the private method accessible 
			mAddCard.setAccessible(true);
			
			//	Invoke the AddCard method for each card passed into this method
			for (Card card: cards)
			{
				mAddCard.invoke(inst, card);
			}
			
			Class[] cSetHandID = new Class[1];
			cSetHandID[0] = java.util.UUID.class;
			
			Method mSetHandID = c.getDeclaredMethod("setHandID", cSetHandID);
			mSetHandID.setAccessible(true);
			
			mSetHandID.invoke(inst, HandID);
		
			
			
			result = (HandBlackJack)inst;
			

		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		} catch (InstantiationException e) {

			e.printStackTrace();
		}

		return result;
	}
}
