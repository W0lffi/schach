package controller;

import java.util.List;

import javafx.scene.image.ImageView;
import model.Position;
import view.MainMenuView;
import view.Playground;

/**
 * The controller interface.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public interface ControllerInterface {
	/**
	 * Expects user action.
	 * @return True if input was valid.
	 */
	boolean userAction();
	
	/**
	 * Get the boardPos position.
	 * @return The board position.
	 */
	Position getBoardPos();
	
	/**
	 * Get the last edited images.
	 * @return The last edited images.
	 */
	List<ImageView> getLastImages();
	
	/**
	 * Get the playground of the gui
	 * @return The playground.
	 */
	Playground getPlayground();
	
	/**
	 * Getter for settings.
	 * @return The settings.
	 */
	boolean[] getSettings();
	
	/**
	 * Setter for settings.
	 * @param settings The settings.
	 */
	void setSettings(boolean[] settings);

	/**
	 * Getter for the main menu.
	 * @return The main menu.
	 */
	MainMenuView getMainMenu();
}
