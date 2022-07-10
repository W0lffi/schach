package controller;

import java.util.List;
import java.util.Scanner;

import javafx.scene.image.ImageView;
import model.Computer;
import model.Figure;
import model.Game;
import model.Position;
import model.RulesInterface;
import model.Tile;
import schach.Constants;
import view.MainMenuView;
import view.Playground;
import view.ViewInterface;

/**
 * This class processes the player's inputs in the game and modifies the model.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
@SuppressWarnings("PMD.CyclomaticComplexity") // The code would be harder to understand if we out source methods to
public class ConsoleController implements ControllerInterface {	// other classes
	
	private ViewInterface view;
	private Game game;
	private RulesInterface rules;
	private Scanner sc;
	private Tile[][] tiles;
	private Figure fig;
	
	private String input;
	
	// Needed for JUnit
	private String listOfBeaten;
	private int characterValue;
	
	/**
	 * The controller gets the view.
	 * @param view The view that is connected to this controller.
	 */
	public ConsoleController(ViewInterface view) {
		this.view = view;
		game = this.view.getGame();
		game.setController(this);
		sc = new Scanner(System.in);
		rules = game.getGamemode();
		rules.setGame(game);
		rules.setLanguage(view.getLanguage());
		game.setLanguageTrue(view.getLanguage());
		game.setRunning(true);
		game.start(false);
	}
	/**
	 * A constructor for the JUnit stub.
	 */
	public ConsoleController() {}	// for JUnit 
	
	/**
	 * This method manages the user interactions with the game.
	 * @return True if the move was made otherwise false.
	 */
	@Override
	public boolean userAction() {
		String input = expectInput();
		if(analizeInput() && validateInput(input) && input.length() > 2 && memberOf(input.charAt(5), Constants.VALID_TRANS)) { 	// If the input is a valid    
			rules.moveTheFigure(input.charAt(5));																								// and a allowed move
			String s = game.getOutput();
			game.setOutput("!" + input.substring(0, 5) + "\n" + s);
			return true;
		}
		if(game.getOutput() == "") {
			game.setOutput("");
		}
		return false;
	}
	
	/**
	 * This method awaits the input of the player whose turn it is and returns it as String.
	 * @return The user input.
	 */
	private String expectInput() {
		if (game.getPlayer2().getClass().equals(Computer.class) && game.getPlayer2().isColor() == game.isTurn()) {
			input = game.getPlayer2().getIntelligence().makeMove(game);
		}else {
			input = sc.nextLine();
		}
		if(input.length() < Constants.INPUT_LENGTH)	{							// Default transformation letter 
			input += Constants.VALID_TRANS[3];									// is 'Q' (e2-e3 -> e2-e3Q)
		}
		return input;
	}
	
	/**
	 * This method differentiates whether the input is a valid move, a special command like "beaten" or "language", an invalid move or an invalid input.
	 * @return True if it's a valid or non-valid move.
	 */
	@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NcssCount" }) // It's a switch case and this is easy to read if it has a few more lines or branches
	private boolean analizeInput() {
		switch (input) {
			case "friedhof":
			case "beaten":														// If one player typed "beaten "
				String list = generateListOfBeaten();
				game.setOutput(list);
				return false;
			case "sprache":
			case "language":													// If the typed "language"
				game.setLanguage(-1);											// toggle the language
				rules.setLanguage(view.getLanguage());
				game.setOutput(Constants.SWITCH_LANGUAGE[view.getLanguage()]);
				return false;
			case "rückgängig":
			case "undoQ":
				rules.undo();
				if(game.getPlayer2().getClass().equals(Computer.class) && !game.getPlayer2().getIntelligence().isThinking()) {
					rules.undo();
				}
				return false;
			case "wiederholen":
			case "redoQ":
				if(rules.redo()) {
					if(game.getPlayer2().getClass().equals(Computer.class)) {
						rules.redo();
					}
					Figure pl1King = Figure.getKingPos(tiles, game.getPlayer1().isColor());
					Figure pl2King = Figure.getKingPos(tiles, game.getPlayer2().isColor());
					String checkColor;
					if(pl1King.isCheck() ) {
						checkColor = pl1King.isColor() ? Constants.CHECK_MSG_2_1[game.getLanguage()] : Constants.CHECK_MSG_2_2[game.getLanguage()];
						game.setOutput(Constants.CHECK_MSG_1[game.getLanguage()] + checkColor + Constants.CHECK_MSG_3[game.getLanguage()]);
						
					}else {
						checkColor = pl2King.isColor() ? Constants.CHECK_MSG_2_1[game.getLanguage()] : Constants.CHECK_MSG_2_2[game.getLanguage()];
						game.setOutput(Constants.CHECK_MSG_1[game.getLanguage()] + checkColor + Constants.CHECK_MSG_3[game.getLanguage()]);
					}
				}
				return false;
			case "remisQ":
			case "drawQ":
				draw();
				return false;
			case "surrender":
			case "aufgeben":
				game.stopGame();
				String color = game.isTurn() ? Constants.WIN_MSG_1_2[game.getLanguage()] : Constants.WIN_MSG_1_1[game.getLanguage()];
				game.setOutput(color + Constants.WIN_MSG_2[game.getLanguage()]);
				game.getViews().get(0).gameEnd(color + Constants.WIN_MSG_2[game.getLanguage()]);
				return false;
			default:
				return true;
			}
	}
	
	/**
	 * Manages the draw stuff.
	 */
	private void draw() {
		int result;
		String rejectedColor;
		if(!game.getPlayer2().getClass().equals(Computer.class)) {
			String drawColor = game.isTurn() ? Constants.WIN_MSG_1_1[game.getLanguage()] : Constants.WIN_MSG_1_2[game.getLanguage()]; 
			String draw = drawColor + Constants.DRAW_DIALOG[game.getLanguage()] + "\n1 " + Constants.YES[game.getLanguage()] + "\n2 " + Constants.NO[game.getLanguage()];
			String[] out = {draw, draw};
			result = view.initConsoleOut(out, 2);
			rejectedColor = drawColor.equals(Constants.WIN_MSG_1_1[game.getLanguage()]) ? Constants.WIN_MSG_1_2[game.getLanguage()] : Constants.WIN_MSG_1_1[game.getLanguage()];
		}else {
			boolean end = game.getPlayer2().getIntelligence().drawRequest(game);
			result = end ? 1 : 0;
			rejectedColor = Constants.JOHNNY + " ";
		}
		if(result == 1) {
			game.stopGame();
			game.setOutput(Constants.UNDECIDED[game.getLanguage()]);
		}else {
			game.setOutput(rejectedColor + Constants.NO_DRAW[game.getLanguage()]);
		}
		
	}
	/**
	 * This method generates the list of beaten characters as a String. 
	 * @return The list of beaten characters as String.
	 */
	private String generateListOfBeaten() {
		String list = "";												// write the figures 
		List<Figure> beaten = game.getCemetery().getBeaten();	// to the output string
		for (Figure fig : beaten) {
			list += Character.toString(fig.getName()) + ", ";
		}
		if(list.length() > 1) {
			list = list.substring(0, list.length() - 2);
		}
		listOfBeaten = list;
		return list;
	}
	
	/**
	 * This method Checks whether the input is valid and allowed.
	 * @param input The user input.
	 * @return True if move is valid and allowed.
	 */
	private boolean validateInput(String input) {
		boolean validInput = authorizeMove(input);
		if (validInput && game.isTurn() != game.getBoard().getTiles()[fig.getPosition().getLine()][fig.getPosition().getColumn()].getFigure().isColor()) {
			validInput = false;
			game.setOutput(Constants.SEMANTICS_ERROR[view.getLanguage()]); // change to TURN_ERROR later
		}
		if(validInput) {
			rules.setFig(fig);
			rules.setCurrentLine(fig.getPosition().getLine());
			rules.setCurrentColumn(fig.getPosition().getColumn());
			rules.setDestinationLine(fig.getPosition().getDestLine());
			rules.setDestinationColumn(fig.getPosition().getDestColumn());
		}
		return validInput;
	}
	
	/**
	 * This method calls the other methods verifyInput and verifyMove to check if a move can be done.
	 * @param input The input command of the user.
	 * @return Returns true if the input is syntactically and semantically correct
	 */
	private boolean authorizeMove(String input) {
		game.getBoard().clearMovePos();
		if(verifyInput(input)) {
			tiles = game.getBoard().getTiles();
			if(tiles[Constants.BOARD_DIMENSIONS - Character.getNumericValue(input.charAt(1))][getCharacterValue(input.charAt(0))] == null) {
				game.setOutput(Constants.SEMANTICS_ERROR[view.getLanguage()]);
				return false;
			}
			fig = tiles[Constants.BOARD_DIMENSIONS - Character.getNumericValue(input.charAt(1))][getCharacterValue(input.charAt(0))].getFigure();
			fig.getPosition().setDestColumn(getCharacterValue(input.charAt(3)));
			fig.getPosition().setDestLine(Constants.BOARD_DIMENSIONS - Character.getNumericValue(input.charAt(4)));
			return verifyMove();
		}
		else {
			return false;
		}
	}
	
	/**
	 * This method verifies the syntax of a player's input command.
	 * @param input The input of the player.
	 * @return Returns true if the syntax of the input is correct.
	 */
	private boolean verifyInput(String input) {
		if(input.length() == Constants.INPUT_LENGTH
		&& memberOf(input.charAt(0),Constants.BOARD_LETTERS)
		&& memberOf(input.charAt(1),Constants.VALID_ENUMS)
		&& input.charAt(2) == '-'
		&& memberOf(input.charAt(3),Constants.BOARD_LETTERS)
		&& memberOf(input.charAt(4),Constants.VALID_ENUMS)
		&& memberOf(input.charAt(5),Constants.VALID_TRANS))
			return true;
		game.setOutput(Constants.SYNTAX_ERROR[view.getLanguage()]);
		return false;
	}
	
	/**
	 * This method calculates the corresponding number for each column/letter.
	 * @param c The character letter from the input.
	 * @return Return the right number of the letter.
	 */
	private int getCharacterValue(char c) {
		int n;
			switch(c) {
			case 'a': n = 0;
			break;
			case 'b': n = 1;
			break;
			case 'c': n = 2;
			break;
			case 'd': n = 3;
			break;
			case 'e': n = 4;
			break;
			case 'f': n = 5;
			break;
			case 'g': n = 6;
			break;
			case 'h': n = 7;
			break;
				default: n = -1;
			break;
			}
		characterValue = n;
		return n;
	}
	
	/**
	 * This method compares a single character of an input with an array of allowed possibilities.
	 * @param input The character.
	 * @param equals The array.
	 * @return Returns true if the character is a part of the array.
	 */
	private boolean memberOf(char input, char[] equals) {
		for(Character c : equals) {
			if(c == input) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method checks whether a turn is according to the rules and thus semantically valid.
	 * @return Returns true if the turn is valid.
	 */
	private boolean verifyMove(){
		Tile[][] tiles = game.getBoard().getTiles();
		int destinationLine  = fig.getPosition().getDestLine();
		int destinationColumn = fig.getPosition().getDestColumn();
		
		game.getBoard().setMovePos(fig.move(tiles, true));
		if(game.getBoard().getMovePos()[destinationLine][destinationColumn] == Constants.HAS_MOVE 
		|| game.getBoard().getMovePos()[destinationLine][destinationColumn] == Constants.HAS_ENEMY) { 
			return true;
		}
		game.setOutput(Constants.SEMANTICS_ERROR[view.getLanguage()]);
		return false;
	}
	
	// Setters and getters for JUnit
	public String getListOfBeaten() {return listOfBeaten;}
	public void setListOfBeaten(String beaten) {generateListOfBeaten();}
	public void setView(ViewInterface view) {this.view = view;}
	public Game getGame() {return game;}
	public void setGame(Game game) {this.game = game;}
	public void setRules(RulesInterface rules) {this.rules = rules;}
	public void setSc(Scanner sc) {this.sc = sc;}
	public void setCharacterValue(char c) {getCharacterValue(c);}
	public int getCharacterValue() {return characterValue;}
	@Override
	public Position getBoardPos() {return null;}
	@Override
	public List<ImageView> getLastImages() {return null;}
	@Override
	public Playground getPlayground() {return null;}
	@Override
	public boolean[] getSettings() {return new boolean[0];}
	@Override
	public void setSettings(boolean[] settings) {}
	@Override
	public MainMenuView getMainMenu() {return null;}
}