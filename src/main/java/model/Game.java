package model;

import java.util.ArrayList;
import java.util.List;

import controller.ControllerInterface;
import view.ViewInterface;

/**
 * In this class the game is running.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
@SuppressWarnings("PMD.TooManyFields") // We need the language integer in game class.
public class Game extends Thread implements GameInterface{

	private ControllerInterface controller;
	private Board board;
	private Cemetery cemetery;
	private Player[] player;
	
	private List<ViewInterface> views;
	private RulesInterface gamemode;
	
	private String output;
	
	private int language;
	
	private boolean turn;
	private boolean running;
	private boolean newOutput;
	
	/**
	 * The constructor creates a new board, cemetery and the two players.
	 */
	public Game() {
		cemetery = new Cemetery();
		board = new Board(this);
		player = new Player[2];
		player[0] = new Human();
		player[1] = null;
		views = new ArrayList<ViewInterface>();
		language = 1;
		turn = true;
		newOutput = false;
		output = "";
	}

	/**
	 * This method notifies the attached views about changes.
	 */
	@Override
	public void viewNotify() {
		for(ViewInterface view : views) {
			view.update();
		}
	}
	
	/**
	 * This method attaches a new view object to the game.
	 * @param obs The view object.
	 */
	@Override
	public void attach(ViewInterface obs) {views.add(obs);	}
	
	/**
	 * This method detaches a view object from the game.
	 * @param obs The view object.
	 */
	@Override	
	public void detach(ViewInterface obs) {views.remove(obs);}
	
	/**
	 * This method contains the game loop.
	 */
	@Override 
	public void run() {
		viewNotify();
		while(running) {
			controller.userAction();
			if(newOutput) {
				newOutput = false;
				viewNotify();
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				System.out.println("hallo");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method starts the game loop.
	 *@param gui A boolean value if started in the GUI mode or not.
	 */
	public void start(boolean gui) {
		running = true;
		if(gui) {
			Thread gameLoop = new Thread(this);
			gameLoop.start();
		}
		else {
			run();
		}
	}
	
	/**
	 * This method stops the game loop.
	 */
	public void stopGame() {
		running = false;
	}
	
	private void updateLanguage() {
		language = Math.abs(--language);
		gamemode.setLanguage(language);
		views.get(0).setLanguage(language);
		if(views.size() > 1) {
			views.get(1).setLanguage(language);
		}
	}
	
	// Getters and setters
	
	/**
	 * Getter for the first human player
	 * @return player1
	 */
	public Player getPlayer1() {return player[0];}
	/**
	 * Setter for the second player which can be human or computer.
	 * @param x The input 1 for human or 2 for computer.
	 * @param intelligence An integer value which kind of AI to use. 
	 */
	public void setPlayer2(int x, int intelligence) {player[1] = x==1 ? new Human() : new Computer(intelligence);}
	/**
	 * Getter for the second player
	 * @return The player 2
	 */
	public Player getPlayer2() {return player[1];}
	
	public void setBoard(Board board) {this.board = board;}
	public RulesInterface getGamemode() {return this.gamemode;}
	public void setGamemode(RulesInterface gamemode) {this.gamemode = new Normal(this);} // The parameter is only needed to avoid a pmd warning
	public boolean isRunning() {return running;}
	public void setRunning(boolean running) {this.running = !this.running;} // The parameter is only needed to avoid a pmd warning
	public Board getBoard() {return board;}
	public void setCemetery(Cemetery cemetery) {this.cemetery = cemetery;}
	public Cemetery getCemetery() {return cemetery;}
	/**
	 * The normal setter for language.
	 * @param language The language.
	 */
	public void setLanguageTrue(int language) {this.language = language;}
	public void setLanguage(int language) {updateLanguage();}
	public int getLanguage() {return language;}
	public boolean isTurn() {return turn;}
	public void setTurn(boolean turn) {this.turn = turn;} 		// The parameter is only needed to avoid a pmd warning
	public String getOutput() {return output;}
	/**
	 * Get the current output from the output-string and set the string to "".
	 * @return The current output.
	 */
	public String getOutputClear() {
		String tmp = output;
		output = "";
		return tmp;
	}
	public void setOutput(String output) {this.output = output; newOutput = true;}
	public void setController(ControllerInterface controller) {this.controller = controller;}
	
	// For JUnit
	public ControllerInterface getController() {return controller;}
	public void setViews(List<ViewInterface> views) {this.views = views;}
	public List<ViewInterface> getViews(){return views;}
}
