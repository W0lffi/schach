package schach;

import javafx.scene.paint.Color;

/**
 * A class that contains all the constant values for the game.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Constants {
	
	/**
	 * The font color for the text in the list view which are repeatable.
	 */
	public static final Color REPEATABLE = Color.GRAY;
	
	/**
	 * The font color for the text in the list view which are reversible.
	 */
	public static final Color REVERSIBLE = Color.BLACK;
	
	/**
	 * The opacity value for the hit marker.
	 */
	public static final Double OPACITY_HIT_ENEMY = 0.8d;
	
	/**
	 * After this time in milliseconds is done the tool tip is shown.
	 */
	public static final Double TOOLTIP_TIME = 100.0d;
	
	/**
	 * The opacity value for possible fields and check marker.
	 */
	public static final Double OPACITY_POSSIBLE_FIELD_CHECK = 0.4d;
	
	/**
	 * The x position of Johnny.
	 */
	public static final Double JOHNNY_X = 1000.0d;
	
	/**
	 * The y position of Johnny for white.
	 */
	public static final Double JOHNNY_Y_WHITE = 600.0d;
	
	/**
	 *  The y position of Johnny for black.
	 */
	public static final Double JOHNNY_Y_BLACK = 10.0d;
	
	/**
	 * The x position of the bubble of Johnny.
	 */
	public static final Double BUBBLE_X = 1090.0d;
	
	/**
	 * The y position of the bubble of Johnny for white.
	 */
	public static final Double BUBBLE_Y_WHITE = 610.0d;
	
	/**
	 *  The y position of the bubble of Johnny for black.
	 */
	public static final Double BUBBLE_Y_BLACK = 20.0d;
	
	/**
	 * The value for width and height for the button images.
	 */
	public static final Integer BUTTON_IMG_WIDTH_HEIGHT = 30;
	
	/**
	 * The default container size.
	 */
	public static final Integer CONTAINER_SIZE = 49;
	
	/**
	 * The screen width of the game.
	 */
	public static final Integer SCREEN_WIDTH = 1280;
	
	/**
	 * The screen height of the game.
	 */
	public static final Integer SCREEN_HEIGHT = 720;
	
	/**
	 * The width and height of the figures on the board.
	 */
	public static final Integer FIG_WIDTH_HEIGHT = 80;
	
	/**
	 * The width and height of the figures on the cemetery.
	 */
	public static final Integer FIG_CEMETERY_WIDTH_HEIGHT = 50;
	
	/**
	 * The cemetery first horizontal position.
	 */
	public static final Integer CEMETERY_POSITIONS_X = 10;
	
	/**
	 * The cemetery first vertical position.
	 */
	public static final Integer CEMETERY_POSITIONS_Y = 42;
	/**
	 * The number of lines and columns of the chess board.
	 */
	public static final Integer BOARD_DIMENSIONS = 8;
	
	/**
	 * The length which an input has to have to count as a valid turn.
	 */
	public static final Integer INPUT_LENGTH = 6;
	
	/**
	 * The text for our Johnny.
	 */
	public static final String JOHNNY = "Johnny";
	
	/**
	 * The 'enemy' entry for the move possibilities string-array.
	 */
	public static final String HAS_ENEMY = "enemy";
	
	/**
	 * The 'true' entry for the move possibilities string-array.
	 */
	public static final String HAS_MOVE = "true";
	
	/**
	 * The 'false' entry for the move possibilities string-array.
	 */
	public static final String HAS_NO_MOVE = "false";
	
	/**
	 * The text for thinking.
	 */
	public static final String[] THINKING_AI = {"Denke nach..", "Thinking.."};
	
	/**
	 * The directory paths for the different kinds of images.
	 */
	public static final String[] DIRECTORY = {"backgrounds/", "boards/", "figures/", "states/", "buttons/"};
	
	/**
	 * The first part of the output message when a player is in check followed by the color.
	 */
	public static final String[] CHECK_MSG_1 = {"Der ", "The "};
	
	/**
	 * The second part of the output message when a player is in check for white.
	 */
	public static final String[] CHECK_MSG_2_1 = {"wei\u00DFe ", "white "};
	
	/**
	 * The second part of the output message when a player is in check for black.
	 */
	public static final String[] CHECK_MSG_2_2 = {"schwarze ", "black "};
	
	/**
	 * The third part of the output message when a player is in check after the color.
	 */
	public static final String[] CHECK_MSG_3 = {"König steht im Schach!", "king is in check!"};
	
	/**
	 * The first part of the win message when a player won for white.
	 */
	public static final String[] WIN_MSG_1_1 = {"Wei\u00DF ", "White "};
	
	/**
	 * The first part of the win message when a player won for black.
	 */
	public static final String[] WIN_MSG_1_2 = {"Schwarz ", "Black "};
	
	/**
	 * The second part of the win message when a player won.
	 */
	public static final String[] WIN_MSG_2 = {"hat gewonnen.", "won."};
	
	/**
	 * The drawn message which appear if its a drawn.
	 */
	public static final String[] UNDECIDED = {"Remis" , "Draw"};
	
	/**
	 * The error message which appear if an invalid input is submitted in the menu.
	 */
	public static final String[] MENU_ERROR = {"Bitte taetige eine gueltige Eingabe!", "Please submit a valid input!"};
	
	/**
	 * The error message which appears while the game is running if the input is syntactical invalid.
	 */
	public static final String[] SYNTAX_ERROR = {"!Ungueltige Eingabe", "!Invalid move"};
	
	/**
	 * The error message which appears when the wrong color wants move.
	 */
	public static final String[] TURN_ERROR = {"dU bIsT nIcHt Am ZuG!", "NoT yOuR tUrN!"};
	
	/**
	 * The error message which appears while the game is running if a move is semantically invalid.
	 */
	public static final String[] SEMANTICS_ERROR = {"!Unerlaubter Zug", "!Move not allowed"};
	
	/**
	 * The error message which appears while the game is running if the move put the own king in check. 
	 */
	public static final String[] DEFEND_KING_ERROR = {"Du darfst deinen König nicht im Stich lassen!","You have to defend your king!"};
	
	/**
	 * The text for choosing the language in the menu (currently German and English).
	 */
	public static final String[] CHOOSE_LANGUAGE = {"Choose language:\n\n1 German\n2 English"};
	
	/**
	 * The message which appears after the language has been changed by the player while the game is running.
	 */
	public static final String[] SWITCH_LANGUAGE = {"Sprache geändert!", "Switched language!"};
	
	/**
	 * The text for choosing the options in the menu.
	 */
	public static final String[] MAIN_MENU = {"Hauptmenue:\n\n1 Spiel starten", "Main menu:\n\n1 Start game", "2 Beenden", "2 Quit"};
	
	/**
	 * Start button text with human as enemy in the main menu.
	 */
	public static final String[] MAIN_MENU_START_HUMAN = {"Mensch", "Human"};
	
	/**
	 * Start button text with computer as enemy in the main menu.
	 */
	public static final String MAIN_MENU_START_COM = "Computer";
	
	/**
	 * Quit button text in main menu.
	 */
	public static final String[] MAIN_MENU_BTN_QUIT = {"Spiel beenden", "Quit game"};
	
	/**
	 * The text for the main menu button on the playground.
	 */
	public static final String[] MAIN_MENU_BTN = {/*"Zurück zum*/"Hauptmenü", /*"Back to*/"Main menu"};
	
	/**
	 * The text for the move possibilities button on the playground.
	 */
	public static final String[] MOVE_POSSIBILITIES_BTN = {"Laufmöglichkeiten anzeigen", "Show move possibilities"};
	
	/**
	 * The text for the move first figure button.
	 */
	public static final String[] FIRST_FIGURE_BTN = {"Ausgewählte Figur sperren", "Lock the chosen figure"};
	
	/**
	 * The text for the mirror field button.
	 */
	public static final String[] MIRROR_FIELD_BTN = {"Feld drehen", "Turn field"};
	
	/**
	 * The text for the draw check button.
	 */
	public static final String[] DRAW_CHECK_BTN = {"Schach anzeigen", "Display check"};
	
	/**
	 * The text for the redo-button.
	 */
	public static final String[] REDO_BTN = {"Wiederholen", "Redo"};
	
	/**
	 * The text for the undo-button.
	 */
	public static final String[] UNDO_BTN = {"Zurücksetzen", "Undo"};
	
	/**
	 * The text for the tool tip of the undo-button.
	 */
	public static final String[] UNDO_TOOLTIP = {"Setze deinen letzten Zug zurück.", "Undo your last move."};
	
	/**
	 * The text for the tool tip of the redo-button.
	 */
	public static final String[] REDO_TOOLTIP = {"Führe einen zuvor zurückgesetzten Zug erneut aus.", "Redo a withdrawn move again."};
	
	/**
	 * The text for the tool tip of the surrender button.
	 */
	public static final String[] SURRENDER_TOOLTIP = {"Gib die aktuelle Partie auf", "Surrender the current game"};
	
	/**
	 * The text for the surrender alert.
	 */
	public static final String[] SURRENDER_DIALOG = {", möchtest du wirklich aufgeben?", ", do you really want to surrender?"};
	
	/**
	 * The text for the tool tip of the draw button.
	 */
	public static final String[] DRAW_TOOLTIP = {"Biete deinem Gegner Remis an", "Offer draw to your enemy"};
	
	/**
	 * The text for the draw alert-box.
	 */
	public static final String[] DRAW_DIALOG = {"bietet dir Remis an", "offers you a draw"};
	
	/**
	 * The text for the console output if a player tries draw against a computer. 
	 */
	public static final String[] NO_DRAW = {"hat deine Anfrage auf Remis zurückgewiesen... das bedeutet nein ~", "rejected your request of draw... means no ~"};
	
	/**
	 * The text for the yes answer in the draw and surrender dialog.
	 */
	public static final String[] YES = {"Ja", "Yes"};
	
	/**
	 * The text for the no answer in the draw and surrender dialog.
	 */
	public static final String[] NO = {"Nein", "No"}; 
	
	/**
	 * The Text for the tool tip of the exit Button.
	 */
	public static final String[] EXIT_TOOLTIP = {"Beende das laufende Spiel und kehre zurück ins Hauptmenü.", "Quit the current game and go back to the main menu."};
	
	/**
	 * The text for the tool tip of the move possibilities check item. 
	 */
	public static final String[] MOVE_POSSIBILITIES_TOOLTIP = {"Schalte die Sichtbarkeit der möglichen Züge an und aus.", "Toggle whether accessable fileds are highlighted."};
	
	/**
	 * The text for the tool tip of the first figure check item.
	 */
	public static final String[] FIRST_FIGURE_TOOLTIP = {"Die erste Figur, die ausgewählt wird, muss auch bewegt werden.", "The first figure which was clicked, has to be moved."};
	
	/**
	 * The text for the tool tip of the mirror field check item.
	 */
	public static final String[] MIRROR_FIELD_TOOLTIP = {"Das Spielfeld wird immer zu der Person gedreht, welche am Zug ist.", "The field is turned to the player whose turn it is."};
	
	/**
	 * The text for the tool tip of the draw check check item.
	 */
	public static final String[] DRAW_CHECK_TOOLTIP = {"Wenn der König im Schach steht, wird dies durch ein rotes Feld angezeigt.", "If the king stands in check, the field he stands on is highlighted."};
	
	/**
	 * The text for choosing the game mode in the menu.
	 */
	public static final String[] CHOOSE_MODE = {"Spielmodus:\n\n1 Normal", "Gamemode:\n\n1 Normal", "2 Blitz" , "2 Blitz"};
	
	/**
	 * The text for choosing the opponent in the menu.
	 */
	public static final String[] CHOOSE_ENEMY = {"Bestimme deinen Gegenspieler:\n\n1 Mensch", "Choose your opponent:\n\n1 Human", "2 Computer","2 Computer"};
	
	/**
	 * The text for choosing the kind of AI.
	 */
	public static final String[] CHOOSE_COMPUTER = {"Willst du gegen die leichte oder schwere KI spielen?\n\n1 Leicht\n2 Schwer\n3 Ich möchte Zeit für einen Kaffee haben!", "Do you want to play against the easy or difficult AI?\n\n1 Easy\n2 Difficult\n3 I want to have time to drink my coffee"};
	
	/**
	 * The text for depth warning.
	 */
	public static final String[] WARNING_DEPTH = {"Warnung, dies könnte etwas Zeit in Anspruch nehmen, mach dir doch einen Kaffee... oder mehere...", "Warning, this could take some time, better make a coffee ... or a few more..."};
	
	/**
	 * The text for choosing the color of player one in the menu.
	 */
	public static final String[] CHOOSE_COLOR = {"W\u00E4hle die Farbe von Spieler 1:\n\n1 Wei\u00DF","Choose the color of player 1:\n\n1 White" , "2 Schwarz", "2 Black"};
	
	/**
	 * The text for the title of color choosing alert.
	 */
	public static final String[] CHOOSE_COLOR_ALERT = {"Wähle deine Farbe", "Choose your color"};
	
	/**
	 * The text for choosing the color of player one for the dialog.
	 */
	public static final String[] CHOOSE_COLOR_DIALOG = {"W\u00E4hle die Farbe von Spieler 1:", "Choose the color of player 1:"};
	
	/**
	 * The text for the title of computer choosing alert.
	 */
	public static final String[] CHOOSE_COMPUTER_ALERT = {"Wähle KI", "Choose AI"};
	
	/**
	 * The text for choosing the kind of AI alert.
	 */
	public static final String[] CHOOSE_COMPUTER_DIALOG = {"Willst du gegen die leichte oder schwere KI spielen?", "Do you want to play against the easy or difficult AI?"};
	
	/**
	 * The text for the easy submit button.
	 */
	public static final String[] EASY = {"Einfach", "Easy"};
	
	/**
	 * The text for the difficult submit button.
	 */
	public static final String[] DIFFICULT = {"Schwer", "Difficult"};
	
	/**
	 * The text for the coffee submit button. 
	 */
	public static final String[] COFFEE = {"Ich will zeit für Kaffee", "I want time for coffee"};
	
	/**
	 * The text for reversed moves.
	 */
	public static final String[] UNDO = {"Zug zurückgesetzt!", "Move reversed!"};
	
	/**
	 * The text if no reversal of moves is possible.
	 */
	public static final String[] UNDO_ERROR = {"Rücksetzen zu Beginn nicht möglich!", "Can't reverse in the beginning!"};
	
	/**
	 * The text for repeated moves
	 */
	public static final String[] REDO = {"Zug wiederholt!", "Move repeated!"};
	
	/**
	 * Text if no repetition of moves is possible. 
	 */
	public static final String[] REDO_ERROR = {"Wiederholung nicht möglich.", "Can't reapeat in the current situation."};
	
	/**
	 * The letters to identify the columns of the chess board.
	 */
	public static final char[] BOARD_LETTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
	
	/**
	 * The enumerations to identify the lines of the chess board.
	 */
	public static final char[] VALID_ENUMS = {'8', '7', '6', '5', '4', '3', '2', '1'};
	
	/**
	 * The letters for allowed transformation of a pawn.
	 */
	public static final char[] VALID_TRANS = {'R', 'B', 'N', 'Q'};
}