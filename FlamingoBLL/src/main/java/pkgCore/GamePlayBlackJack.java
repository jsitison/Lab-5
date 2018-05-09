package pkgCore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import pkgException.DeckException;
import pkgException.HandException;
import pkgEnum.eBlackJackResult;
import pkgEnum.eGameType;

public class GamePlayBlackJack extends GamePlay implements Cloneable {

	private Player pDealer = new Player("Dealer", 0);
	private Hand hDealer = new HandBlackJack();

	
	
	public GamePlayBlackJack(HashMap<UUID, Player> hmTablePlayers, Deck dGameDeck) {

		super(eGameType.BLACKJACK, hmTablePlayers, dGameDeck);

		Iterator it = hmTablePlayers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player) pair.getValue();
			Hand h = new HandBlackJack();
			GamePlayerHand GPH = new GamePlayerHand(this.getGameID(), p.getPlayerID(), h.getHandID());
			this.putHandToGame(GPH, h);
		}
	}

	@Override
	protected Card Draw(GamePlayerHand GPH) throws DeckException, HandException {

		Card c = null;

		if (bCanPlayerDraw(GPH)) {
			Hand h = this.gethmGameHand(GPH);
			c = h.Draw(this.getdGameDeck());

			h.AddCard(c);

			this.putHandToGame(GPH, h);

		}
		return c;
	}

	protected Card Draw(GamePlayerHand GPH, Card c) throws DeckException, HandException {


		if (bCanPlayerDraw(GPH)) {
			Hand h = this.gethmGameHand(GPH);

			h.AddCard(c);

			this.putHandToGame(GPH, h);

		}
		return c;
	}

	private boolean bCanPlayerDraw(GamePlayerHand GPH) throws HandException {
		boolean bCanPlayerDraw = false;

		Hand h = this.gethmGameHand(GPH);

		HandScoreBlackJack HSB = (HandScoreBlackJack) h.ScoreHand();

		// TODO: Determine if the player can draw another card (are they busted?)
		int min = 0;
		for (int i = 0; i < HSB.getNumericScores().size(); i++) {
			if (i==0) {
				min = HSB.getNumericScores().get(i).intValue();
			} else if (min > HSB.getNumericScores().get(i).intValue()) {
				min = HSB.getNumericScores().get(i).intValue();
			}
		}
		
		if (min < 21) {
			bCanPlayerDraw = true;
		}
			
		return bCanPlayerDraw;
	}

	public boolean bDoesDealerHaveToDraw() throws HandException {
		boolean bDoesDealerHaveToDraw = true;

		HandScoreBlackJack HSB = (HandScoreBlackJack) hDealer.ScoreHand();

		// TODO: Determine if the dealer MUST draw.
		// Dealer must draw unless they are bust and they don't have a
		// numeric score between 17-21.
		// 5-5 = 10, they must draw
		// A-6 = 7 or 17, they must stay (because they have 17)
		// J-J-J = 30, they are busted, they can't draw

		int max = 0;
		for (int i = 0; i < HSB.getNumericScores().size(); i++) {
			if (i==0) {
				max = HSB.getNumericScores().get(i).intValue();
			} else if (max < HSB.getNumericScores().get(i).intValue()) {
				max = HSB.getNumericScores().get(i).intValue();
			}
		}
		
		if (max >= 17) {
			bDoesDealerHaveToDraw = false;
		}
		
		return bDoesDealerHaveToDraw;
	}

	public void ScoreGame(GamePlayerHand GPH) {
		boolean bIsHandWinner = false;

		try {
			hDealer.ScoreHand();
		} catch (HandException e) {

		}
		
		Iterator it = this.getHmGameHands().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			GamePlayerHand sGPH = (GamePlayerHand) pair.getKey();
			HandBlackJack sH = (HandBlackJack) pair.getValue();

			HandScoreBlackJack sHSB = (HandScoreBlackJack) sH.ScoreHand();

			if (sGPH.getGameID() == GPH.getGameID()) {
				if (isBusted((HandScoreBlackJack) sH.getHS())) {
					sH.seteBlackJackResult(eBlackJackResult.LOSE);
					this.putHandToGame(sGPH, sH);
					continue;
				}
				// Player is not busted, continue eval

				if (isBusted((HandScoreBlackJack) hDealer.getHS())) {
					sH.seteBlackJackResult(eBlackJackResult.WIN);
					this.putHandToGame(sGPH, sH);
					continue;
				}

				// Dealer is not busted, continue eval

				if (HighestNotBustedScore((HandScoreBlackJack) sH.getHS()) == (HighestNotBustedScore(
						(HandScoreBlackJack) hDealer.getHS()))) {
					sH.seteBlackJackResult(eBlackJackResult.TIE);
					this.putHandToGame(sGPH, sH);
					continue;
					
				}
				
				if (HighestNotBustedScore((HandScoreBlackJack) sH.getHS()) < (HighestNotBustedScore(
						(HandScoreBlackJack) hDealer.getHS()))) {
					sH.seteBlackJackResult(eBlackJackResult.LOSE);
					this.putHandToGame(sGPH, sH);
					continue;
					
				}
				
				if (HighestNotBustedScore((HandScoreBlackJack) sH.getHS()) > (HighestNotBustedScore(
						(HandScoreBlackJack) hDealer.getHS()))) {
					sH.seteBlackJackResult(eBlackJackResult.WIN);
					this.putHandToGame(sGPH, sH);
					continue;
					
				}

			}

			// TODO: Add the player to the game
			
		}

		// TODO: Determine if player is a winner

		// TODO: Find the Dealer's hand
		// TODO: Score Dealer's hand

		// TODO: Find Player's hand
		// TODO: Score Player's hand

		// TODO: If Player's hand > Dealer's hand and <= 21, then eBlackJackResult = WIN
		// If Player's hand < Dealer's hand and Dealer didn't bust = LOSE
		// If Player's hand == Dealer's hand and both didn't bust = TIE

	}

	private boolean isBusted(HandScoreBlackJack HSB) {

		boolean isBusted = true;

		for (Integer i : HSB.getNumericScores()) {
			if (i <= 21) {
				isBusted = false;
				break;
			}
		}

		return isBusted;
	}

	private int HighestNotBustedScore(HandScoreBlackJack HSB) {
		int iHighestScore = 0;
		for (Integer i : HSB.getNumericScores()) {
			if (i <= 21) {
				iHighestScore = i;
			}
		}
		return iHighestScore;
	}

	protected Hand gethDealer() {
		return hDealer;
	}
	
	

	protected void sethDealer(Hand hDealer) {
		this.hDealer = hDealer;
	}

	public Player getpDealer() {
		return pDealer;
	}
}
