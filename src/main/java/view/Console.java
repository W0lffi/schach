package view;

import java.util.Scanner;

import model.Computer;
import model.Game;
import model.Normal;
import model.Tile;
import schach.Constants;
//import java.util.Scanner;

/**
 * This class is for the console-mode and print all changes to the console. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Console implements ViewInterface {

	private Scanner sc;
	private Game game;
	private Tile[][] tiles;
	
	private int language;
	private boolean simple;

	/**
	 * The constructor initializes the scanner and creates a new game.
	 * @param simple A value if the simple flag was set.  
	 */
	public Console(boolean simple) {
		this.simple = simple;
		sc = new Scanner(System.in);
		game = new Game();
		language = -1;
		init(game);
	}
	
	/**
	 * Initialize the language, game-mode, opponent and color of the players from the game.
	 */
	@Override
	public void init(Game game) {
		if(!simple) {
			language = (initConsoleOut(Constants.CHOOSE_LANGUAGE,2) == 1) ? 0 : 1;
			mainMenu();
			game.setPlayer2(initConsoleOut(Constants.CHOOSE_ENEMY,Constants.CHOOSE_ENEMY.length) == 1 ? 1 : 0,1);
			if(game.getPlayer2().getClass().equals(Computer.class)) {
				game.setPlayer2(2, initConsoleOut(Constants.CHOOSE_COMPUTER, 3));
			}
		}else {
			language = 1;
			game.setPlayer2(1,1);
		}
		game.setGamemode(new Normal(game));
		boolean color = true;
		if(game.getPlayer2().getClass().equals(Computer.class)) {
			color = ((initConsoleOut(Constants.CHOOSE_COLOR,Constants.CHOOSE_COLOR.length) == 1 ? 0 : 1) == 1) ? false : true;
		}
		game.getPlayer1().setColor(color);
		game.getPlayer2().setColor(!color);
		game.attach(this);
	}
	
	/**
	 * Prints the output message and current positions of the figures of the board.
	 */
	@Override
	public void update() {
		System.out.println(game.getOutputClear());
		
		tiles = game.getBoard().getTiles();
		System.out.println("\n\n");
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			System.out.print(Constants.BOARD_DIMENSIONS-l + "  ");
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(tiles[l][c] == null) {
					System.out.print("  ");
				}
				else {
					System.out.print(tiles[l][c].getFigure().getName() + " ");	
				}
			}	
			System.out.println();
		}
		System.out.print("\n   ");
		for(char i : Constants.BOARD_LETTERS) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	/**
	 * Gives the options to start a new game, load an old game or quit the game.
	 */
	private void mainMenu() {
		int select = 0;
		boolean choice = false;
		do {
			select = initConsoleOut(Constants.MAIN_MENU,Constants.MAIN_MENU.length);
			switch(select) {
				case 1:
						choice = true;
				break;
				case 2:
						System.exit(0);
				break;
					default:
						System.out.println(Constants.MENU_ERROR[language]);
				break;
			}
		}while(!choice);
	}
	
	@Override
	public int initConsoleOut(String[] out, int smaller) {
		int select = -1;
		boolean first = true;
		do {
			if(!first) {
				if(language > -1){
					System.out.println(Constants.MENU_ERROR[language]);
				}
				else {
					System.out.println(Constants.MENU_ERROR[1]);
				}
			}
			for(int i=language;i<out.length;i+=2) {
				if(language > -1){
					System.out.println(out[i]);
				}
				else {
					System.out.println(out[0]);
				}
			}
			try {
				select = sc.nextInt();
			} catch(Exception e){
				sc.nextLine();
				first = false;
				continue;
			}
			first = false;
		}while(select < 1 || select > smaller);
		return select;
	}
	
	@Override
	public void gameEnd(String content) {}
	
	// Getter and setter
	@Override
	public Game getGame() {return game;}
	@Override
	public int getLanguage() {return language;}
	@Override
	public void setLanguage(int langugage) {this.language = langugage;}
	@Override
	public void updateBoard() {}
	@Override
	public ToggleSwitch getBtnLanguage() {return null;}
	// For JUnit
	
	/**
	 * Get the selected integer of initConsoleOut.
	 * @param out The String array which will be printed.
	 * @param smaller The upper limit of options. 
	 * @return The selected value.
	 */
	public int getInitConsoleOut(String[] out, int smaller) {return initConsoleOut(out, smaller);}
	public boolean isSimple() {return this.simple;}
	public void setSimple(boolean simple) {this.simple = simple;}
}
