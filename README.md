# Licence 3 IT project even semester : 
Stone Age (l'Age de pierre) | *Groupe : SAH | Pseudo : Mentra20 (now YannMartinDes)*
![image](https://github.com/user-attachments/assets/93ec8f5b-1fb5-40b8-a0e8-ac6cb990a29a)

## Context : 
The project consisted of recreating the console version of the game [L'Age de pierre](https://www.philibertnet.com/fr/jeux-de-societe/8136-age-de-pierre-l--8435407629387.html) with different AI playing against each other (no graphics, no real players). Statistics have also been added.  

The project is entirely in Java and includes a Maven Project.  

In the project repository you'll find the iterations that my group and I have chosen to make, as well as the source code for our game. Development was done in agile mode.  

Full details of the EU are contained in the [syllabus](https://github.com/Mentra20/StoneAge/blob/master/doc/Syllabus.pdf).

You can find the rules of the game [here](/doc/lage-de-pierre-regle.pdf) the building cards [here](/doc/batiments.pdf) and civilisation cards [there](/doc/Cartecivilisation.pdf) as well as their [effects](/doc/description_card.pdf).  

The project was presented to a jury. ([presentation slides](/doc/DiaposPresentation.pdf)).
 

# How to run the game 
use `mvn exec:java` for running the game.  
use `mvn exec:java@StoneAgeStatistique` for running stats. (will be created in **stats** folder)  
use `mvn tes` for running tests.

_Notes: Some tests may failed if you runned in a recent java version_

# Screenshots 
![Capture d'écran 2024-11-15 211336](https://github.com/user-attachments/assets/043a7852-79eb-4d1b-9ca4-788677a26f37)
![Capture d'écran 2024-11-15 211417](https://github.com/user-attachments/assets/f376267a-19b8-45d6-9b89-91b6db96d055)
![Capture d'écran 2024-11-15 211601](https://github.com/user-attachments/assets/b60f92c1-4ff2-4445-9f20-86c21a9e7aa2)
![téléchargement](https://github.com/user-attachments/assets/b91c5e91-a6a3-47a0-8903-b5d614f05425)


# Iterations
  
Surname/first name of group members:   
- Iopama : Goulot Thomas  
- Idrash : Tognetti Yohann  
- Mentra20 (YannMartinDes) : Martin d'escrienne Yann  
- Theoricien : Jerome Maxime  
- AnthonyS06 : Santi Anthony  
  
## First iteration:        
- 1 player.      
- 1 miniature.    
- 1 resource zone.  
- First version of the player's inventory.  
- During the first phase of the game, the AI (not yet fully developed) will place its miniature in the only available zone, followed by the phase in which it receives its resource, and then the end of the game turn.  
- The value will be constant for the resource harvest.  
- No dice.  
- At the end of the round, the player is the winner.  
  
## Second iteration:  
- 2 players  
- The player starts with 5 miniatures.  
- Addition of missing resource zones (except food)  
- Addition of the dice system  
- Addition of a ‘semi-complete’ harvesting system, with the roll of a die.  
- Development of a first simplistic AI, playing at least one miniature in a random zone.  
- The first round doesn't end until the AI has placed all its miniatures (first game loop).  
- If possible, add the reproduction hut and its constraints (optional).  
- A player wins if he has at least 1 wood.  
  
## Third iteration:  
- Addition of a second player.  
- Addition of the field and the accompanying system.  
- Add the breeding hut and its constraints if not done.  
- Added the hunting area and the complete food system.  
- Finalisation of the turn system: the player must now feed the miniatures before the turn ends.  
- One or more players win as soon as they have at least 1 wood.  
  
## Fourth iteration:  
- Before launching the program, we ask the user how many players they want in their game, this number will be between 2 and 4.  
- We'll add the craft hut as the last special zone in the village.  
- To continue with the game loop, we'll be improving the resource shortage system for a player. For example, when a player no longer has enough food to feed his miniatures at the start of a new turn, he'll have the choice of keeping his collectable resources or losing victory points.  
- We'll then add a rudimentary victory point system  
- We'll add a building and map type that will be new special zones.  
  
## Fifth iteration:  
- Addition of (almost) all maps and buildings  
- Addition of a new AI  
- Refinement of the gameplay loop to a normal game with realistic victory conditions  
- Improving the maintainability of the code  
  
## Sixth iteration:  
- Addition of rules based on the number of players  
- Addition of a statistics system available to the user  
- Added interesting AI to implement for testing advanced strategy  
- Added the missing civilisation map  
  
## Just before delivery:  
- Refinement of the statistics system for a more user-friendly use and correction of possible bugs.  
- Refinement or addition of AI, providing the user with a clean, advanced system.  
- Improving the maintainability of the code.  
- Finalise unit testing.  
