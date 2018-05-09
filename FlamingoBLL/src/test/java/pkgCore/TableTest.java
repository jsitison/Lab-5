package pkgCore;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class TableTest {

	@Test
	public void TableTest1() {
		
		//	TODO: Create 2 players.
		//			Add them to the a Table
		//			Ensure that each are added to the table
		//			Remove each from the Table, ensure they are removed from the table.
		
		Player p1 = new Player("Joe",1);
		Player p2 = new Player("Bert",2);
		
		Table t = new Table();
		
		assertNotNull(t);
		
		t.AddPlayerToTable(p1);
		
		List<Player> lstPlayer = t.GetTablePlayers();
		
		assertEquals(1,lstPlayer.size());
		
		Player pGet = t.GetPlayerFromTable(p1);
		
		assertNotNull(pGet);
		
		assertEquals(p1,pGet);
		
		t.ClearTable();
		

		assertEquals(0,t.GetTablePlayers().size());
		
		t = null;
		
		assertNull(t);
		
	}

}
