package pkgCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import pkgEnum.eGameType;
import pkgEnum.ePlayerIdentity;
import pkgInterfaces.iPlayer;

public class Table implements Serializable {

	private UUID TableID;
	private HashMap<UUID, Player> hmTablePlayer = new HashMap<UUID, Player>();
	
	private Deck TableDeck;

	public Table() {
		super();
		this.TableID = UUID.randomUUID();
	}

	public void AddPlayerToTable(Player p) {
		hmTablePlayer.put(p.getPlayerID(), p);
	}

	public void RemovePlayerFromTable(Player p) {
		hmTablePlayer.remove(p.getPlayerID());
	}

	public Player GetPlayerFromTable(Player p) {
		return (Player) hmTablePlayer.get(p.getPlayerID());

	}

	protected HashMap<UUID, Player> getHmTablePlayer() {
		return hmTablePlayer;
	}
	
	public ArrayList<Player> GetTablePlayers() {

		return new ArrayList<Player>(hmTablePlayer.values());
	}
	
	public ArrayList<iPlayer> GetTablePlayers(UUID PlayerID)
	{
		ArrayList<iPlayer> iPlayers = new ArrayList<iPlayer>();
		for (Player p: hmTablePlayer.values())
		{
			if (p.getPlayerID().equals(PlayerID))
			{
				p.seteIdent(ePlayerIdentity.ME);
			}
			else
			{
				p.seteIdent(ePlayerIdentity.OTHER);
			}		
			iPlayers.add(p);
		}		
		return iPlayers;
	}
	
	protected void CreateDeck(eGameType eGT)
	{
		switch (eGT)
		{
		case BLACKJACK:
			TableDeck = new Deck(6);
			break;
		case POKER:
			TableDeck = new Deck();
			break;
		}
			
	}
	
	public Deck getTableDeck() {
		return TableDeck;
	}
	
	public void ClearTable() {
		hmTablePlayer.clear();
		
	}

}
