package core;

import java.awt.Graphics2D;
import java.util.HashSet;

public class ObjectHandler {
	
	private HashSet<DrawableObject> objectSet0 = new HashSet<DrawableObject>();
	private HashSet<DrawableObject> objectSet1 = new HashSet<DrawableObject>();
	private HashSet<DrawableObject> objectSet2 = new HashSet<DrawableObject>();
	private HashSet<DrawableObject> objectSet3 = new HashSet<DrawableObject>();

	/**
	 * adds drawable object to hashset.
	 * 
	 * Calling this will cause object to be updated and drawn
	 * @param o object
	 * @param l layer (0-background, 1-lower mid, 2-upper mid, 3-foreground)
	 * @return true if successful
	 */
	public synchronized boolean registerObject(DrawableObject o, int l){
		switch(l){
		case 0:
			return objectSet0.add(o);
		case 1:
			return objectSet1.add(o);
		case 2:
			return objectSet2.add(o);
		case 3:
			return objectSet3.add(o);
		default:
//			System.err.println("registerObject min level: 0, max level: 3.  Level requested: " + l);
			throw new IllegalArgumentException("registerObject level range: 0-3.  Given: " + l);
//			return false;

		}
		
	}
	
	/**
	 * removes drawable object from all hashsets.
	 * @param o
	 * @return  True if successful.
	 */
	public synchronized boolean unregisterObject(DrawableObject o){
		boolean check = false;
		check = objectSet0.remove(o) || check;
		check = objectSet1.remove(o) || check;
		check = objectSet2.remove(o) || check;
		check = objectSet3.remove(o) || check;
		
		return check;
		
	}
	
	/**
	 * Draw all the objects in order of level
	 * @param g Graphics2D object to draw to
	 */
	public synchronized void drawAllObjects(Graphics2D g){
		for(DrawableObject o: objectSet0){
			o.drawSelf(g);
		}
		for(DrawableObject o: objectSet1){
			o.drawSelf(g);
		}
		for(DrawableObject o: objectSet2){
			o.drawSelf(g);
		}
		for(DrawableObject o: objectSet3){
			o.drawSelf(g);
		}
	}
	
	/**
	 * update all objects.  Will also occur in order of level
	 */
	public synchronized void updateAllObjects(){
		for(DrawableObject o: objectSet0){
			o.updateSelf();
		}
		for(DrawableObject o: objectSet1){
			o.updateSelf();
		}
		for(DrawableObject o: objectSet2){
			o.updateSelf();
		}
		for(DrawableObject o: objectSet3){
			o.updateSelf();
		}
	}
}
