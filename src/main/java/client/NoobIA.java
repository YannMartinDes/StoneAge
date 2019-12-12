package client;

import java.util.Random;
import java.util.stream.IntStream;

import game.Ressource;
import game.Settings;
import game.building.*;
import game.zone.*;
import inventory.InventoryIA;
import player.PlayerIA;


/**
 * Priorite decroissante:
 * ZoneHunt:		[4]
 * ZoneField: 		[5]
 * ZoneHut: 		[6]
 * ZoneTool:		[7]
 * ZoneBuildings:	[12-15]
 * ZoneCarteCiv:	[8-11]
 * ZoneRessource:	[0-3]
 */

public class NoobIA extends IA 
{

	//Random de l'IA (elle ne peux pas acceder a Settings).
	private Random rand = new Random();
	private int currentZone;
	private int choosedRessource;
	
	public NoobIA (PlayerIA playerIA, InventoryIA inventoryIA) 
	{
		super(playerIA, inventoryIA);
		this.choosedRessource = -1;
	}


	/**
	 * chooseZone retourne l'indice de la zone choisie par l'IA . 
	 * @param zoneAvailableSpace le tableau avec l'espace disponible de chaque zone. 
	 * @param zoneName le tableau des noms des zones.
	 * @param buildings la copie des zones batiment
	 * @param cV la copie des zones carte civilisation
	 * @return l'indice de la zone
	 */
	public int chooseZone (int[] zoneAvailableSpace, String[] zoneName, ZoneBuilding[] buildings, ZoneCarteCivilisation[] cV)
	{
		//pour eviter les parti infini
		if(inventoryIA.availableResourceToFeed()>100) {
			// CHECK DES CARTES Civilisation
			for (int i = 0; i < cV.length; i++)
			{
				// SI Y'A DE LA PLACE
				if (zoneAvailableSpace[i+8] >= 1)
				{
					this.currentZone = i + 8;
					return currentZone;
				}
			}
		}
		// RESET
		this.currentZone = -1;
		// SI ON A PAS ASSEZ POUR NOURRIR TLM A LA FIN DU TOUR
		if (this.inventoryIA.getRessource(Ressource.FOOD) < this.playerIA.getMaxFigurine() - this.inventoryIA.getRessource(Ressource.FIELD))
		{
			// SI AVEC 1 DE FIELD ON PEUT SURVIVRE
			if (this.playerIA.getMaxFigurine() - this.inventoryIA.getRessource(Ressource.FIELD) + 1 == this.inventoryIA.getRessource(Ressource.FOOD))
			{
				// SI LA FERME EST DISPO
				if (zoneAvailableSpace[5] == 1)
				{
					if (this.rand.nextInt(2) == 1) this.currentZone = 5;
				}
				// SINON ON CHECK TOUTES LES CARTES CIVILISATION
				else
				{
					for (int i = 0; i < cV.length; i++)
					{
						// SI ELLE OFFRE 1 MARQUEUR NOURRITURE
						if (cV[i].getCard().getRessource() == Ressource.FIELD)
						{
							if (this.rand.nextInt(2) == 1) this.currentZone = i+8;
						}
					}
					// SINON ON VA DANS LA CHASSE
					if (this.currentZone == -1) 
						this.currentZone = 4;
				}
				// SINON ON VA DANS LA CHASSE
				if (this.currentZone == -1) 
					this.currentZone = 4;
			}
		}
		// SI ON A ASSEZ DE NOURRITURE, ON SE CONCENTRE D'ABORD SUR LE VILLAGE
		else 
		{
			if (zoneAvailableSpace[5] >= 1)
			{
				if (this.rand.nextInt(3) == 1) this.currentZone = 5;
			}
			else if (zoneAvailableSpace[6] >= 1)
			{
				if (this.rand.nextInt(3) == 1) this.currentZone = 6;
			}
			else if (zoneAvailableSpace[7] >= 1)
			{
				if (this.rand.nextInt(3) == 1) this.currentZone = 7;
			}

			// SI LE VILLAGE EST PLEIN, ON SE CONCENTRE SUR LE GAIN DE POINTS, ET SUR LES RESSOURCES NECESSAIRES
			else
			{
				// CHECK DES CARTES BATIMENT
				for (int i = 0; i < buildings.length; i++)
				{
					// SI Y'A DE LA PLACE
					if (zoneAvailableSpace[i+12] >= 1)
					{
						Building tmpBuilding = buildings[i].getBuilding();
						// RESSOURCE IMPOSED
						if (tmpBuilding.getType() == 0)
						{
							// SI C'EST UN BUILDING AVEC RESSOURCE IMPOSEES ET QU'ON A LES RESSOURCES
							if (((BuildingRessourceImposed) tmpBuilding).checkNeededRessource(this.inventoryIA.getCopyRessources()))
							{
								if (this.rand.nextInt(2) == 1) this.currentZone = i + 12;
							}
						}
						else if (tmpBuilding.getType() == 1)
						{
							// SI C'EST UN BUILDING AVEC RESSOURCE NON IMPOSEES ET QU'ON A LES RESSOURCES
							if (((BuildingRessourceNotImposed) tmpBuilding).checkNeededRessource())
							{
								if (this.rand.nextInt(2) == 1) this.currentZone = i + 12;
							}
						}
						else if (tmpBuilding.getType() == 2)
						{
							// SI C'EST UN BUILDING AVEC RESSOURCE A CHOIX ET QU'ON A LES RESSOURCES
							if (((BuildingRessourceChoosed) tmpBuilding).checkNeededRessource())
							{
								if (this.rand.nextInt(2) == 1) this.currentZone = i + 12;
							}
						}
						// AUCUN AUTRE BATIMENT POSSIBLE
						else {throw new RuntimeException("Building type not expected");}
					}
				}

				if (this.currentZone == -1)
				{
					// REGARDER SI ON PEUT PRENDRE UNE CARTE CIVILISATION
					for (int i = 0; i < cV.length; i++)
					{
						// SI ON A ASSEZ DE RESSOURCE
						if (IntStream.of(this.inventoryIA.getCopyRessources()).sum() >= 4 - i && zoneAvailableSpace[i+8] == 1)
						{
							if (this.rand.nextInt(2) == 1) this.currentZone = i + 8;
							break;
						}
					}
				}

				// IL NOUS RESTE LES RESSOURCES
				if (this.currentZone == -1)
				{
					for (int i = 0; i < 4; i++)
					{
						if (zoneAvailableSpace[i] >= 1)
						{
							if (this.rand.nextInt(2) == 1) this.currentZone = i;						}
					}
				}

				// A LA FIN, IL RESTE LA CHASSE
				if (this.currentZone == -1)
					this.currentZone = 4;
			}
			// SINON ON VA DANS LA CHASSE
			if (this.currentZone == -1) 
				this.currentZone = 4;
		}
		// SINON ON VA DANS LA CHASSE
		if (this.currentZone == -1) 
			this.currentZone = 4;

		return this.currentZone;
	}

	/**
	 * chooseNumber retourne le nombre de figurines choisie par l'IA. 
	 * @param min : le nombre de figurines minimum.
	 * @param max : le nombre de figurines maximum. 
	 * @return le nombre de figurines que l'IA place.
	 */
	public int chooseNumber (int min, int max) 
	{
		// CE JOUEUR SUPPOSE QUE 2 FIGURINES = 3 DE NOURRITURE
		if (this.currentZone == 4)
		{
			int nbFoodRequired = ((this.inventoryIA.getRessource(Ressource.FIELD) + this.inventoryIA.getRessource(Ressource.FOOD)) - this.playerIA.getMaxFigurine());
			if (((2 * nbFoodRequired) / 3) % max < min)
				return min;
			return ((2 * nbFoodRequired) / 3) % max;
		}
		// SINON IL RENVOIE UN NOMBRE ALEATOIRE
		return this.rand.nextInt(max - min + 1) + min;
	}

	/**
	 * chooseRessource retourne le nombre de ressources choisie par l'IA. 
	 * @param figurinesToFeed Le nombre de figurines a nourrir
	 * @return le tableau des ressources sacrifiee.
	 */
	public int[] chooseRessource(int figurinesToFeed) {
		int ressource;
		int index = -1;
		// BOIS - ARGILE - PIERRE - OR
		do {
			index++;
			ressource = inventoryIA.getRessource(Ressource.indexToRessource(index));
		}while(ressource==0);

		int nb = this.rand.nextInt(Math.min(ressource, figurinesToFeed))+1;

		int[] res = new int[] {index,nb};
		return res;
	}

	/**
	 * useRessourceToFeed renvoie true ou false selon le choix de l'IA pour nourrir 
	 * ses figurines avec des ressources. Le choix se fait au hasard.  
	 * @return Booleen : true si le joueur veut utiliser ses ressources pour nourrir, false sinon.
	 */
	public boolean useRessourceToFeed()
	{
		// LE JOUEUR PRIVILEGIE LE SCORE
		return true;
	}

	/**
	 * pickCard renvoie les ressources donnee pour l'achat de la carte civilisation. 
	 * Si l'IA renvoie un tableau vide, elle refuse l'achat de la carte. 
	 * @param numberRessourceRequire : le nombre de ressource pour l'achat de la carte. 
	 * @return res : tableau des ressources donnes pour la carte. 
	 */
	public int[] pickCard(int numberRessourceRequire) 
	{    
		int[] res = new int[] {0,0,0,0};
		int[] ressourceNumber = new int[4];
		for(int i = 0; i<4; i++) {
			ressourceNumber[i] = inventoryIA.getRessource(Ressource.indexToRessource(i));
		}
		if(this.rand.nextInt(2) == 1) {
			while(numberRessourceRequire > 0) {
				int index = 0;
				// CHOISI UNE RESSOURCE: PRIORITES: bois, argile, pierre, or
				while(index == -1 || ressourceNumber[index] == 0) {
					index++;
				}
				// CB DE RESSOURCE
				int number = this.rand.nextInt(Math.min(numberRessourceRequire, ressourceNumber[index])) + 1;
				ressourceNumber[index] -= number;
				res[index] += number;
				numberRessourceRequire -= number;
			}
		}
		return res; 
	}

	/**
	 * Renvoie un choix aleatoire sur la prise d'une carte batiment
	 * @return true ou false, aleatoirement
	 */
	public boolean pickBuilding ()
	{
		// IL JOUE LE SCORE
		return true;
	}

	/**
	 * pickTools renvoie un tableau de boolean pour dire quel outils l'IA utilise.
	 * @return tableau des outils que le joueur veux utiliser
	 */
	public boolean[] pickTools() {
		int[] toolsToUse = inventoryIA.getTools().getTools();
		boolean[] useTools = inventoryIA.getTools().getToolsUsed();
		boolean[] res = new boolean[toolsToUse.length];
		
		for (int i = 0; i < useTools.length; i++)
		{
			if (useTools[i] == false && toolsToUse[i]>0)
			{
				res[i] = true;
			}
			else
			{
				res[i] = false;
			}
		}
		return res;
	}

	/**
	 * Phase de tirage
	 * @param listeTirage des des tires
	 * @param alreadyChoose si un autre joueur l'a deja choisi ou non
	 * @return l'index de ce que veut le joueur dans le tirage
	 */
	public int chooseTirage(int[] listeTirage, boolean[] alreadyChoose) 
	{
		int choose;
		do {
			choose = Settings.RAND.nextInt(alreadyChoose.length);
		}while(alreadyChoose[choose]);

		return choose;
	}
	
	/**
	 * useRessourceCard demande a l'ia si elle veut utiliser ses ressources au choix. 
	 * Si oui, elle demande lesquelles (id de la ressource).
	 * @return ressource que le joueur veux -1 pour ne pas l'utiliser
	 */
	// WOOD - CLAY - STONE - GOLD
	public int useRessourceCard() {
		this.choosedRessource++;
		this.choosedRessource %= 4;
		return this.choosedRessource;
	}

	public String toString () {return "Debutant";}

}
