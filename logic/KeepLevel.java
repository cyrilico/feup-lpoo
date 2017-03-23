package logic;

import java.util.ArrayList;
import java.util.Random;

public class KeepLevel extends Level {

	/* Calculate how many ogres will be generated */
	Random ogreGenerator;
	/* The villains for the level */
	ArrayList<Ogre> ogres;
	
	public KeepLevel(int nOgres) {
		super();

		levelIndex = 1;
		map = new KeepMap();

		/*Create level's characters*/
		//The hero
		hero = new Hero(1,7);
		hero.setRepresentation('A');
		//The ogres
		ogreGenerator = new Random();

		int numberOfOgres = nOgres;

		ogres = new ArrayList<Ogre>();
		for(int i = 0; i < numberOfOgres; i++)
			ogres.add(new Ogre(4,1));   	
	}
	
	public KeepLevel(ArrayList<Ogre> ogres, Hero hero, Map map) {
		levelIndex = 1;
		this.map = map;
		this.ogres = ogres;
		this.hero = hero;
	}

	public KeepLevel() {
		super();

		levelIndex = 1;
		map = new KeepMap();

		/*Create level's characters*/
		//The hero
		hero = new Hero(1,7);
		hero.setRepresentation('A');
		//The ogres
		ogreGenerator = new Random();

		int numberOfOgres = ogreGenerator.nextInt(2)+1; //1 or 2 ogres, starting in the same position (tried with 3 and 4, nearly impossible to escape)

		ogres = new ArrayList<Ogre>();
		for(int i = 0; i < numberOfOgres; i++)
			ogres.add(new Ogre(4,1));
	}

	public void checkIfHeroCaptured(){
		int[] heroCoordinates = hero.getCoordinates();
		for(Ogre ogre : ogres) {
			if(ogre.hasCaughtHero(heroCoordinates[0],heroCoordinates[1]))
				levelStatus = LevelState.LOST;
		}
	}

	public void checkIfHeroStuns() {
		int[] heroCoordinates = hero.getCoordinates();

		for(Ogre ogre : ogres) {
			if(ogre.isStunned() != 0) { //If the ogre is stun, update the stun counter
				ogre.updateStun();
				ogre.setRepresentation('8');
			}
			else if(ogre.isNearHero(heroCoordinates[0],heroCoordinates[1])) {
				ogre.setStun(); //Can't just use updateStun because the ogre may already be stunned
				ogre.setRepresentation('8');
			}
		}
	}

	public void updatePositions(int[] input) {
		//Update the hero's position
		int dx = input[0];
		int dy = input[1];

		int heroX = hero.getCoordinates()[0];
		int heroY = hero.getCoordinates()[1];

		char currentChar = map.elementAt(heroX+dx, heroY+dy);

		updateHero(dx, dy, currentChar);

		//Update the villains' position
		for(Ogre ogre : ogres) {
			//Update the ogre's position
			int ogreX = ogre.getCoordinates()[0];
			int ogreY = ogre.getCoordinates()[1];
			int[] nextMovement;
			generateOgreMovement:
				do{
					nextMovement = ogre.getNextMovement();
					currentChar = map.elementAt(ogreX+nextMovement[0],ogreY+nextMovement[1]);
					switch(currentChar) {
					case 'k':
						ogre.setRepresentation('$');
						break generateOgreMovement;
					case '.':
						ogre.setRepresentation('0');
						break generateOgreMovement;
					default:
						break;
					}
				} while(true); //Risky...

			ogre.setCoordinates(ogreX+nextMovement[0],ogreY+nextMovement[1]);
			//Re-get coordinates so club isn't looking for a new position based on old ones
			ogreX = ogre.getCoordinates()[0];
			ogreY = ogre.getCoordinates()[1];

			//Update the ogre's club's position
			generateClubMovement:
				do {
					nextMovement = ogre.getNextClubMovement();
					currentChar = map.elementAt(ogreX+nextMovement[0],ogreY+nextMovement[1]);
					switch(currentChar) {
					case 'k':
						ogre.putClubOnKey();
						break generateClubMovement;
					case '.':
						ogre.removeClubFromKey();
						break generateClubMovement;
					default:
						break;
					}
				} while(true); //Risky...

			ogre.setClubOffset(nextMovement[0],nextMovement[1]);
		}

		checkIfHeroStuns();
		checkIfHeroCaptured();
	}
	
	public void updateHero(int dx, int dy, char currentChar) {
		int heroX = hero.getCoordinates()[0];
		int heroY = hero.getCoordinates()[1];
		
		switch(currentChar) { //Checking what is present in the cell the hero wants to move to
		case 'S':
			levelStatus = LevelState.WON;
			return; /* Avoids checking for enemies on negative indexes */
		case 'k':
			heroHasKey = true;
			hero.setRepresentation('K');
		case '.':
			break;
		case 'I':
			if(heroHasKey)
				map.openDoors(); /* No break statement because while he's opening the door, he's still. No break forces dx=dy=0, like we want */
		default: /* currentChar == 'X' so we can't move through */
			dy = 0;
			dx = 0;
		}

		hero.setCoordinates(heroX+dx, heroY+dy);	
	}

	public ArrayList<Ogre> getOgres() {
		ArrayList<Ogre> clone = (ArrayList<Ogre>)ogres.clone();
		return clone;
	}

	public char[][] getLevelMatrix() {
		char[][] matrix = map.getCurrentPlan();
		drawHero(matrix);
		drawOgres(matrix);
		return matrix;
	}
	
	private void drawHero(char[][] mapClone){
		int[] heroCoordinates = hero.getCoordinates();
		int heroX = heroCoordinates[0];
		int heroY = heroCoordinates[1];
		mapClone[heroY][heroX] = hero.getRepresentation();
	}
	
	private void drawOgres(char[][] mapClone){
		for(Ogre ogre : ogres){
			int[] ogreCoordinates = ogre.getCoordinates();
			int[] ogreClubOffsetCoordinates = ogre.getClubOffset();
			int ogreX = ogreCoordinates[0];
			int ogreClubX = ogreX + ogreClubOffsetCoordinates[0];
			int ogreY = ogreCoordinates[1];
			int ogreClubY = ogreY + ogreClubOffsetCoordinates[1];
			mapClone[ogreY][ogreX] = ogre.getRepresentation();
			mapClone[ogreClubY][ogreClubX] = (ogre.clubIsOnKey() ? '$' : '*');
		}
	}

	public Level getNextLevel() {
		return null;
	}
}
