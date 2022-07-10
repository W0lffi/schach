package view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Game;
import schach.Constants;

/**
 * A toggle switch
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class ToggleSwitch extends HBox {
	
	private Game game;
	private final Label label = new Label();
	private final Button button = new Button();
	private SimpleBooleanProperty switchedOn;
	
	/**
	 * Getter for property.
	 * @return The property.
	 */
	public SimpleBooleanProperty switchOnProperty() { return switchedOn; }
	
	
	/**
	 * Updates the graphics and the Button of the toggle switch.
	 * @param game The current game. 
	 */
	public ToggleSwitch(Game game) {
		this.game = game;
		switchedOn = new SimpleBooleanProperty(false);
		init();
		switchedOn.addListener((a,b,c) -> {
			if (c) {
               switchGerman();
            }
            else {
            	switchEnglish();
            }
			updateImgs();
		});
	}
	
	/**
	 * Updates the other language button.
	 */
	public void updateBtn() {
			if (switchedOn.get()) {
                switchGerman();
            }
            else {
            	switchEnglish();
            }
			updateImgs();
    		switchedOn.set(!switchedOn.get());
	}
	
	private void switchGerman() {
		 label.setGraphic(new ImageView(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "german")));
         label.toFront();
	}
	
	private void switchEnglish() {
		label.setGraphic(new ImageView(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "english")));
        button.toFront();
	}
	
	private void updateImgs() {
		((ImageView)label.getGraphic()).setFitHeight(20);
		((ImageView)label.getGraphic()).setFitWidth(40);
		setStyle("-fx-background-color: transparent;");
	}
	
	private void init() {
		
		label.setGraphic(new ImageView(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "english")));
		((ImageView)label.getGraphic()).setFitHeight(20);
		((ImageView)label.getGraphic()).setFitWidth(40);
		getChildren().addAll(label, button);	
		button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
			game.setLanguage(-1);
		});
		label.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
			game.setLanguage(-1);
		});
		setStyle();
		bindProperties();
	}
	
	private void setStyle() {
		setWidth(80);
		label.setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: transparent; -fx-text-fill:black; -fx-background-radius: 4;");
		setAlignment(Pos.CENTER_LEFT);
	}
	
	private void bindProperties() {
		label.prefWidthProperty().bind(widthProperty().divide(2));
		label.prefHeightProperty().bind(heightProperty());
		button.setMinHeight(20.0d);
		button.setMaxHeight(20.0d);
		button.setStyle("-fx-background-color:#ededed; -fx-background-radius: 0;");
		button.prefWidthProperty().bind(widthProperty().divide(2));
	}
}