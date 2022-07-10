package view;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

/**
 * The image handler which managing images.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class ImageHandler {
	private static ImageHandler instance;
	
	private Map<String, Image> imgs = new HashMap<String, Image>();
	
	private ImageHandler() {}
	
	/**
	 * This getter returns a static instance of himself, or if it not exist creates one.
	 * @return The static instance.
	 */
	public static ImageHandler getInstance() {
		if(instance == null) {
			instance = new ImageHandler();
		}
		return instance;
	}
	
	/**
	 * Load the image in the specific directory and the specific name and saves it in a map.
	 * @param dir The directory of the image in the resources directory.
	 * @param name The name of the image.
	 */
	private void loadImage(String dir, String name) {
		Image image = new Image(ImageHandler.class.getResource(dir + name + ".png").toExternalForm(), true);
		imgs.put(name, image);
	}
	
	/**
	 * Loads the image from the map if it there or calls loadImage.
	 * @param dir The directory of the image in the resources directory.
	 * @param key The ID of the image to get.
	 * @return The wanted image.
	 */
	public Image getImage(String dir, String key) {
		if(imgs.get(key) == null) {
			loadImage(dir, key);
		}
		return imgs.get(key);
	}	
}