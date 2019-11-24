package game.zone;

import game.CarteCivilisation;
import game.Player;
import game.Ressource;
import game.Dice;
import game.Inventory;

/**
 * ZoneCarteCivilisation represente les 4 zones ou se trouvent les cartes civilisation dans le jeu.
 * @author Groupe SAH
 */
public class ZoneCarteCivilisation extends ZoneOnePlayer{

	private CarteCivilisation carteCivilisation;//La carte Civilisation. 
	private int numberRessourceNeed;//Le nombre de ressource pour la carte civilisation

	/* CONSTRUCTOR */
	public ZoneCarteCivilisation(String name, int numberRessourceNeed){
		super(name,1); //Le nombre de figurine minimal requis est de 1.
		this.numberRessourceNeed = numberRessourceNeed;
	}

	/* GETTERS */
	public CarteCivilisation getCard(){return this.carteCivilisation;}

	/* SETTERS */
	public void setCard(CarteCivilisation cv){this.carteCivilisation = cv;}



	/**
	 * playerRecoveryFigurine rend les figurines au joueur dans la zone. 
	 * @param player : le joueur concerne.
	 * @param inventory : l'inventaire du joueur concerne.
	 */ 
	public void playerRecoveryFigurine(Player player, Inventory inventory) {
		//recuperation des figurines
		int number = super.howManyPlayerFigurine(player);

		if (number > 0){//Si il n'y a pas de probleme
			super.removeFigurine(player);

			//choisi si il veux recuperer la carte et quelle ressource il utilise pour ce faire
			boolean pickCard = chooseRecoveryCard(player,inventory,numberRessourceNeed);

			if(!pickCard) {//Le joueur ne prend pas la carte
				System.out.println("Le joueur "+player.getName()+" ne prend pas la carte de "+this.getName()+".");
				return;
			}

			inventory.addCardCivilisation(carteCivilisation);
			System.out.println("Le joueur "+player.getName()+" obtient la carte civilisation de "+this.getName()+".");
			
			switch(carteCivilisation.getTypeUpPart()){
			case 0 : //cas ou c'est un gain de ressource direct
				int total = ressourceEffect(carteCivilisation);
				inventory.addRessource(carteCivilisation.getRessource(),total);
				System.out.println("Le joueur "+player.getName()+" obtient "+total
				+" "+carteCivilisation.getRessource().toString()+ " (carte).");
				break;

			case 1 : //cas ou le joueur gagne des point de victoire
				player.addScore(carteCivilisation.getNumberEffect());
				System.out.println("Le joueur "+player.getName()+" obtient "+carteCivilisation.getNumberEffect()+" point de victoire (carte).");
				break;

			default :
				System.out.println("Erreur Carte Civilisation");
			}
			carteCivilisation=null; //suppresion de la carte civilisation de la zone.
		}
	}

	/**
	 * ressourceEffect concerne les cartes civilisation donnant des ressources. 
	 * La fonction renvoie les ressources rapportees.
	 * @param carteCivilisation: la carte concernee. 
	 */
	private int ressourceEffect(CarteCivilisation carteCivilisation){
		int total;
		Ressource ressource = carteCivilisation.getRessource();//La ressource de la carte.

		if(carteCivilisation.getNumberEffect()==0){//Si le nombre de ressource se recupere par lancer de de. 
			int [] dice = Dice.rollDice(2);
			total = 0;

			for(int x : dice){
				total+=x;
			}

			total = (int) (total/ressource.getDivisor());
		}
		else{total = carteCivilisation.getNumberEffect();}//Si la carte a un nombre de ressource fixe. 
		
		return total;
	}

	/**
	 * chooseRecoveryCard renvoie true ou false selon que l'IA decide de prendre la carte ou non.
	 * @param player : le joueur concerne
	 * @param inventory : l'inventaire du joueur concerne.
	 * @param ressourceRequire : le nombre de ressource pour la carte.
	 * @return boolean : true si le joueur prend la carte, false sinon. 
	 */
	private boolean chooseRecoveryCard(Player player,Inventory inventory, int ressourceRequire) {
		int sum;
		boolean verif;

		//verification ressource suffisant
		sum=0;
		int[] copie = inventory.getCopyRessources();
		for(int i = 0; i<4; i++) {
			sum += copie[i];
		}
		if(sum<ressourceRequire) {//Si le joueur n'a pas assez de ressource pour acheter la carte. 
			return false;
		}

		//on demande au joueur
		while(true) {
			sum=0;
			verif = true;
			//On demande a l'IA
			int[] choix = player.getIA().pickCard(inventory.getCopyRessources(), ressourceRequire);//AJOUTER AU FUTUR LA CARTE CONCERNEE

			for(int i = 0; i<4 && verif; i++) {
				if(choix[i] > inventory.getRessource(Ressource.indexToRessource(i)) || choix[i] < 0) 
				{ 
					verif=false; //Le retour de l'IA a un probleme. 
				}
				sum+=choix[i];
			}

			if (sum==0 && verif) {return false; } //cas ou il ne recupere pas la carte

			if(verif && sum==ressourceRequire) //Cas ou l'IA prend la carte. 
			{
				for(int i = 0; i<4 && verif; i++) {
					inventory.subRessource(Ressource.indexToRessource(i), choix[i]);//On retire les ressources.

					if(choix[i] != 0) {//On affiche.
						System.out.println("Le joueur "+player.getName()+" depense "+ choix[i]
								+" "+ Ressource.indexToRessource(i)+ " pour la carte civilisation de "+this.getName()+".");
					}
				}
				return true;
			}
		}
	}
}