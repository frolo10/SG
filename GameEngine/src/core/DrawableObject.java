package core;

import java.awt.Graphics2D;

public interface DrawableObject {
	

	/**
	 * This will be called for each repaint
	 * The object is in charge of handling the actual drawing to g
	 * @param g
	 */
	public void drawSelf(Graphics2D g);
	
	/**
	 * This will be called during every event loop
	 * The object is in charge of dealing with it's own variables
	 */
	public void updateSelf();
	
	
}
