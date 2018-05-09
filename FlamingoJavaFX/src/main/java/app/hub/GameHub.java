package app.hub;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import netgame.common.Hub;
import pkgCore.Action;
import pkgCore.Deck;
import pkgCore.GamePlay;
import pkgCore.GamePlayBlackJack;
import pkgCore.Player;
import pkgCore.Table;
import pkgEnum.eAction;
import pkgEnum.eGameType;

public class GameHub extends Hub {

	private Table HubPokerTable = null;
	private GamePlay HubGamePlay = null;
	private eGameType eGameType = null;

	public GameHub(int port) throws IOException {
		super(port);
		this.setAutoreset(true);
	}

	@Override
	protected void messageReceived(int playerID, Object message) {

		System.out.println("Action received from the hub");

		if (HubPokerTable == null)
			HubPokerTable = new Table();

		if (message instanceof Action) {

			Action a = (Action) message;

			switch (a.geteAction()) {
			case Sit:
				HubPokerTable.AddPlayerToTable(a.getActPlayer());
				resetOutput();
				sendToAll(HubPokerTable);
				break;
			case Leave:
				HubPokerTable.RemovePlayerFromTable(a.getActPlayer());
				resetOutput();
				sendToAll(HubPokerTable);
				break;
			case TableState:
				resetOutput();
				sendToAll(HubPokerTable);
			case GameState:
				// TODO: Implement this
				resetOutput();
				sendToAll(HubGamePlay);
				break;
			case StartGameBlackJack:
				// TODO: Implement this
				this.eGameType = eGameType.BLACKJACK;

				HashMap<UUID, Player> tablePlayers = new HashMap<UUID, Player>();
				for (Player p : HubPokerTable.GetTablePlayers()) {
					tablePlayers.put(p.getPlayerID(), p);
				}

				HubGamePlay = new GamePlayBlackJack(tablePlayers, HubPokerTable.getTableDeck());

				// ?
				resetOutput();
				sendToAll(HubGamePlay);

				break;
			/*
			 * case StartGamePoker:
			 * 
			 * break;
			 */
			case Draw:
				// TODO: Implement this
				break;
			}

		}
	}

}
