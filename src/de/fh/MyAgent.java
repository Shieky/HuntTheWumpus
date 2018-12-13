package de.fh;

import de.fh.agent.WumpusHunterAgent;
import de.fh.game.Entity;
import de.fh.game.Level;
import de.fh.game.Tile;
import de.fh.wumpus.Hunter;
import de.fh.wumpus.HunterPercept;
import de.fh.wumpus.Wumpus;
import de.fh.wumpus.enums.HunterAction;
import de.fh.wumpus.enums.HunterActionEffect;
import de.fh.wumpus.enums.WumpusTileType;

import java.util.ArrayList;
import java.util.Hashtable;

/*
 * DIESE KLASSE VERÄNDERN SIE BITTE NUR AN DEN GEKENNZEICHNETEN STELLEN
 * wenn die Bonusaufgabe bewertet werden soll.
 */
public class MyAgent extends WumpusHunterAgent {

	HunterPercept percept;
	HunterActionEffect actionEffect;
	Hashtable<Integer, Integer> stenchRadar;

	public static void main(String[] args) {

		MyAgent agent = new MyAgent("");
		MyAgent.start(agent,"127.0.0.1", 5000);
	}
	public int x,y = 1;
	public String[][] map = new String[15][15];
	Entity.Direction richtung = Entity.Direction.EAST;
	public MyAgent(String name) {

		super(name);
	}

	/**
	 * In dieser Methode kann das Wissen über die Welt (der State, der Zustand)
	 * entsprechend der aktuellen Wahrnehmungen anpasst, und die "interne Welt",
	 * die Wissensbasis, des Agenten kontinuierlich ausgebaut werden.
	 *
	 * Wichtig: Diese Methode wird aufgerufen, bevor der Agent handelt, d.h.
	 * bevor die action()-Methode aufgerufen wird...
	 *
	 * @param percept Aktuelle Wahrnehmung
	 * @param actionEffect Reaktion des Servers auf vorhergewählte Aktion
	 */
	@Override
	public void updateState(HunterPercept percept, HunterActionEffect actionEffect) {

		/**
		 * Je nach Sichtbarkeit & Schwierigkeitsgrad (laut Serverkonfiguration)
		 * aktuelle Wahrnehmung des Hunters.
		 * Beim Wumpus erhalten Sie je nach Level mehr oder weniger Mapinformationen.
		 */
		this.percept = percept;

        /*
         Aktuelle Reaktion des Server auf die letzte übermittelte Action.

         // Alle möglichen Serverrückmeldungen:
         if(actionEffect == HunterActionEffect.GAME_INITIALIZED) {
         //Erster Aufruf
         }

         if(actionEffect == HunterActionEffect.GAME_OVER) {
         //Das Spiel ist verloren
         }

         if(actionEffect == HunterActionEffect.BUMPED_INTO_WALL) {
         //Letzte Bewegungsaktion führte in eine Wand
         }

         if(actionEffect == HunterActionEffect.BUMPED_INTO_HUNTER) {
         //Nur bei Multiplayermodus
         //Letzte Bewegungsaktion war ein Zusammenstoß mit einem weiteren Hunter
         }

         if(actionEffect == HunterActionEffect.MOVEMENT_SUCCESSFUL) {
         //Letzte Bewegungsaktion war gültig
         }

         if(actionEffect == HunterActionEffect.GOLD_FOUND) {
         //Gold wurde aufgesammelt
         }

         if(actionEffect == HunterActionEffect.WUMPUS_KILLED) {
         //Ein Wumpus wurde getroffen
         }

         if(actionEffect == HunterActionEffect.NO_MORE_SHOOTS) {
         //Schuss ungültig, keine Pfeile mehr
         }
         */

		System.out.println("-------------------------------");
		this.actionEffect = actionEffect;
		System.out.println(actionEffect.toString()+ "\n");

		/*
		Mögliche Percepts über die Welt erhält der Wumpushunter:

		percept.isBreeze();
        percept.isBump();
        percept.isGlitter();
        percept.isRumble();
        percept.isScream();
        percept.isStench();
        percept.getWumpusStenchRadar()
        */

		/*
        percept.getWumpusStenchRadar() enthält alle Wumpi in max. R(ie)eichweite in einer Hashtable.
        Jeder Wumpi besitzt eine unique WumpusID (getKey).
        Die Manhattendistanz zum jeweiligen Wumpi ergibt sich aus der Gestanksitensität (getValue).
		*/

		//Beispiel:
		/*
		stenchRadar = this.percept.getWumpusStenchRadar();

		//Gebe alle riechbaren Wumpis aus
		System.out.println("WumpusID: Intensitaet");
		if(stenchRadar.isEmpty())
		{
			System.out.println("Kein Wumpi zu riechen");
		}
		for(Map.Entry<Integer, Integer> g : stenchRadar.entrySet()){
			System.out.println(g.getKey() + ":\t\t" + g.getValue() );
		}
		System.out.println("");
*/


	}

	/**
	 * Diesen Part erweitern Sie so, dass die nächste(n) sinnvolle(n) Aktion(en),
	 * auf Basis der vorhandenen Zustandsinformationen und gegebenen Zielen, ausgeführt wird/werden.
	 * Der action-Part soll den Agenten so intelligent wie möglich handeln lassen
	 *
	 * Beispiel: Wenn die letzte Wahrnehmung
	 * "percept.isGlitter() == true" enthielt, ist "HunterAction.GRAB" eine
	 * geeignete Tätigkeit. Wenn Sie wissen, dass ein Quadrat "unsicher"
	 * ist, können Sie wegziehen
	 *
	 * @return Die nächste HunterAction die vom Server ausgeführt werden soll
	 */
	@Override
	public HunterAction action() {

		/*HunterAction
        Mögliche HunterActions sind möglich:

       	HunterAction.GO_FORWARD
       	HunterAction.TURN_LEFT
		HunterAction.TURN_RIGHT
		HunterAction.SHOOT
		HunterAction.SIT
		HunterAction.GRAB
		HunterAction.QUIT_GAME
		*/

		if(actionEffect == HunterActionEffect.GAME_INITIALIZED) {
			for (int i = 0 ; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					System.out.print(map[i][j] = "X");
				}
			}
			nextAction = HunterAction.GO_FORWARD;
		}else{
			if(richtung == Entity.Direction.EAST){
				if(map[x+1][y].equals("O")){
					nextAction = HunterAction.TURN_RIGHT;
					switchRichtung();
				}
				else{
					nextAction = HunterAction.GO_FORWARD;
					FillArray();
				}
			}else
			if(richtung == Entity.Direction.SOUTH){
				if(map[x][y+1].equals("O")){
					nextAction = HunterAction.TURN_RIGHT;
					switchRichtung();
				}
				else{
					nextAction = HunterAction.GO_FORWARD;
					FillArray();
				}
			}else
			if(richtung == Entity.Direction.WEST){
				if(map[x-1][y].equals("O")){
					nextAction = HunterAction.TURN_RIGHT;
					switchRichtung();
				}
				else{
					nextAction = HunterAction.GO_FORWARD;
					FillArray();
				}
			}else
			if(richtung == Entity.Direction.NORTH){
				if(map[x][y-1].equals("O")){
					nextAction = HunterAction.TURN_RIGHT;
					switchRichtung();
				}
				else{
					nextAction = HunterAction.GO_FORWARD;
					FillArray();
				}
			}
			}

		if(actionEffect == HunterActionEffect.BUMPED_INTO_WALL){
			nextAction = HunterAction.TURN_RIGHT;
			// wäre theoretisch bei rechts-> NESW und bei links -> NWSE
			switchRichtung();
		}





		System.out.println("nextAction: "+nextAction);

		System.out.println("X: " + x + " Y: " +y);
		return nextAction;
	}
	public void FillArray(){
		switch (richtung){
			case EAST: if(x != 13)x++ ; map[x][y] = "O"; break;
			case SOUTH: if(y != 13)y++ ; map[x][y] = "O" ; break;
			case WEST: if(x !=1 )x-- ; map[x][y] = "O"; break;
			case NORTH: if(y !=1)y--; map[x][y] = "O"; break;
		}
		for (int i = 0 ; i < map.length; i++){
			for (int j = 0 ; j < map[i].length; j++){
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}
	public void switchRichtung(){
		switch (richtung){
			case EAST: richtung = Entity.Direction.SOUTH; break;
			case SOUTH: richtung = Entity.Direction.WEST;break;
			case WEST: richtung = Entity.Direction.NORTH;break;
			case NORTH: richtung = Entity.Direction.EAST;break;
		}
	}
}