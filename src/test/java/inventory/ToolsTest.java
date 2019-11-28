package inventory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import player.Player;
import game.Ressource;


public class ToolsTest {
	
	private Tools outils;
	private Player Playertest;

	@Test
	public void testResetToolsUsed() {
		this.outils = new Tools();
		
		//on test l'instanciation
		assertEquals(this.outils.getTool(),0);
		assertEquals(this.outils.getTools()[0], 0);
		assertEquals(this.outils.getTools()[1], 0);
		assertEquals(this.outils.getTools()[2], 0);
		assertEquals(this.outils.getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsUsed()[2],false);
		assertEquals(this.outils.getToolsIA().getTool(),0);
		assertEquals(this.outils.getToolsIA().getTools()[0], 0);
		assertEquals(this.outils.getToolsIA().getTools()[1], 0);
		assertEquals(this.outils.getToolsIA().getTools()[2], 0);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[2],false);
		
		this.outils.toolsUsed[1] = true;
		assertEquals(this.outils.getToolsUsed()[1],true);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[1],false);
		this.outils.resetToolsUsed();
		for (int i=0; i <this.outils.getToolsIA().toolsUsed.length; i++) {
			assertEquals(this.outils.getToolsIA().toolsUsed[i],false);
		}
	}
	
	@Test
	public void testIncrementTools() {
		this.outils = new Tools();
		
		//on test l'instanciation
		assertEquals(this.outils.getTool(),0);
		assertEquals(this.outils.getTools()[0], 0);
		assertEquals(this.outils.getTools()[1], 0);
		assertEquals(this.outils.getTools()[2], 0);
		assertEquals(this.outils.getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsUsed()[2],false);
		assertEquals(this.outils.getToolsIA().getTool(),0);
		assertEquals(this.outils.getToolsIA().getTools()[0], 0);
		assertEquals(this.outils.getToolsIA().getTools()[1], 0);
		assertEquals(this.outils.getToolsIA().getTools()[2], 0);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[0],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[1],false);
		assertEquals(this.outils.getToolsIA().getToolsUsed()[2],false);
		
		//on incremente une fois et on verifie le resultat
		this.outils.incrementTool();
		assertEquals(this.outils.getTool(),1);
		assertEquals(this.outils.getTools()[0],1);
		assertEquals(this.outils.getToolsIA().getTool(),1);
		assertEquals(this.outils.getToolsIA().getTools()[0],1);
		
		//on test dans une petite boucle
		for (int i=1; i<3;i++) {
			if (this.outils.tool<12) {
				this.outils.incrementTool();
				assertEquals(this.outils.getToolsIA().getTool(),1+i);
				assertEquals(this.outils.getToolsIA().tools[(this.outils.getToolsIA().tool)%3]+=1,i%3);}
		}
	}
	
	@Test
	public void useTools() {
				
	}
}