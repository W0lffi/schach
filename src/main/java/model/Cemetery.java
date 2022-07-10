package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Whenever a figure is beaten it is put on the cemetery.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Cemetery {
	private List<Figure> beaten;
	private boolean newDead;

	/**
	 * This method creates a list which shows the names of the beaten figures.
	 */
	public Cemetery() {
		beaten = new ArrayList<Figure>();
		newDead = false;
	}
	
	/**
	 * Counts the figures on enemy by color.
	 * @param color The color.
	 * @return The count of figures.
	 */
	public int countFigsByColor(boolean color) {
		int count = 0;
		for(Figure c : beaten) {
			if(c.isColor() == color) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Clears the cemetery.
	 */
	public void clear() {
		beaten = new ArrayList<Figure>();
	}
	
	/**
	 * Checks whether a figure is at the cemetery.
	 * @param fig The testing figure.
	 * @return True if the figure is at the cemetery.
	 */
	public boolean isDead(Figure fig) {
		for(Figure figure : beaten) {
			if(figure == fig) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Translation method which checks the char and translates it into a respective figure object.
	 * @param figureName The char name of the figure.
	 * @return The figure object which is specified by the char.
	 */
	private Figure translateFromCharToFigure(char figureName) {
		boolean isWhite = Character.isUpperCase(figureName);	
		switch(Character.toUpperCase(figureName)) {
			case 'P': 
				return new Pawn(isWhite,0,0);
			case 'R':
				return new Rook(isWhite,0,0);
			case 'K':
				return new King(isWhite,0,0);
			case 'Q':
				return new Queen(isWhite,0,0);
			case 'B':
				return new Bishop(isWhite,0,0);
			case 'N' :
				return new Knight(isWhite,0,0);
				default: 
					return null;
		}
	}
	
	/**
	 * This method translates a String into a list of figure objects.
	 * @param figureList A String with figure characters.
	 * @return A list of figures specified by an input String. 
	 */
	public List<Figure> translateFromStringToList(String figureList){
		List<Figure> temp = new ArrayList<Figure>();
		for(int i =0; i < figureList.length(); i++) {
			temp.add(translateFromCharToFigure(figureList.charAt(i)));
		}
		return temp;
	}
	
	// Setters and Getters
	/**
	 * Creates a beaten-figure list from String(from board Storage).
	 * @param figList The list of figures as string.
	 */
	public void setBeatenFromString(String figList) {this.beaten = translateFromStringToList(figList);}
	public void setBeaten(Figure fig) {beaten.add(fig);}
	public List<Figure> getBeaten(){return beaten;}
	public void setNewDead(boolean newDead) {this.newDead = newDead;}
	public boolean isNewDead() {return newDead;}
}
