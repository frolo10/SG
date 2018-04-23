package core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class GameWindow extends JFrame{
	
	//internal variables
	private static final long serialVersionUID = 1L;
	private boolean initialized, isRunning;
	private Runnable backgroundPainter, logicRunner;
	private BufferedImage bf, backBuffer, forwardBuffer;
	private Insets insets;
	
	//set-able variables
	private int updateRate = 0;
	
	
	//usable variables
	private ObjectHandler objHandle;
	


	
	////////////////////////////////////////STARTUP FUNCTIONS////////////////////////////////////////
	/**
	 * Initializes window parameters.  Starts painting loop.
	 * 
	 * Can be called multiple times to change variables
	 * @param windowTitle
	 * @param width
	 * @param height
	 * @param updateRate
	 * @return
	 */
	public boolean initialize(String windowTitle, int width, int height, int updateRate){
		if(initialized){
			System.err.println("WARNING: window already initialized.  May result in unbounded behavior.");
		}
		initialized = true;
		isRunning = true;
		this.updateRate = updateRate;
		
		this.objHandle = new ObjectHandler();
		
		
		setTitle(windowTitle);
		setSize(width,height);
		// setResizable(false);
		
		
		//TODO: create icon support 
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		

		bf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

		insets = getInsets();
		setSize(insets.left + insets.right + width, insets.top + insets.bottom + height);

		backBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// start the drawing thread
		this.backgroundPainter = null;
		this.backgroundPainter = new Runnable() {
			public void run() {
				while (true) {
					try {
						draw();
						Thread.sleep(10);//we want to draw as quickly as possible, but don't overdo it
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		};
		new Thread(backgroundPainter).start();
		
		return true;
	}
	
	
	/**
	 * Starts the logic loop
	 * @return
	 */
	public boolean run(){
		if(!initialized){
			System.err.println("Failed to call initialize before run");
			return false;
		}
		this.logicRunner = null;
		this.logicRunner = new Runnable() {
			public void run() {
				while (isRunning) {
					long time = System.currentTimeMillis();

					updateAll();
					
					// delay to specified update rate
					time = (1000 / updateRate) - (System.currentTimeMillis() - time);

					// actually causes the delay
					if (time > 0) {
						try {
							Thread.sleep(time);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			}
		};
		new Thread(logicRunner).start();
		
		return true;
	}
	
	////////////////////////////////////////STARTUP FUNCTIONS////////////////////////////////////////
	///////////////////////////////////////SHUT DOWN FUNCTIONS///////////////////////////////////////
	
	
	/**
	 * Shuts down the window and exits the program.
	 */
	public void shutDown(){
		isRunning = false;
		setVisible(false);
		System.exit(0);
		
	}
	
	///////////////////////////////////////SHUT DOWN FUNCTIONS///////////////////////////////////////
	
	///////////////////////////////////////USABILITY FUNCTIONS///////////////////////////////////////
	
	
	/**
	 * Get the object handler that the window uses.
	 * Put all objects to be updated and drawn into the object handler
	 * @return
	 */
	public ObjectHandler getObjectHandler(){
		return this.objHandle;
	}
	
	///////////////////////////////////////USABILITY FUNCTIONS///////////////////////////////////////
	
	/**
	 * constructor for window everything will be put on
	 * 
	 * @param width window width
	 * @param height window height
	 * @param updateRate steps per second
	 */
	public GameWindow(){
		initialized = false;
		
	}
	
	
	///////////////////////////////////INTERNALS (DO NOT TOUCH)//////////////////////////////////////
	/**
	 * will draw everything
	 */
	private void draw() throws Exception {
		if (backBuffer == null) {
			backBuffer = new BufferedImage(this.getWidth() + insets.left + insets.right,
					this.getHeight() + insets.top + insets.bottom, BufferedImage.TYPE_INT_ARGB);
		}
		Graphics g = getGraphics();
		Graphics2D g2 = (Graphics2D) backBuffer.getGraphics();

		// buffers the image
		g2.drawImage(backBuffer, insets.left, insets.top, this);

		g2.drawImage(bf, 0, 0, this);

		// draw everything you need here
		this.objHandle.drawAllObjects(g2);

		// more buffering
		g2.dispose();

		BufferedImage temp = forwardBuffer;
		forwardBuffer = backBuffer;

		backBuffer = temp;
		flipBuffers(g);
	}
	
	private void updateAll(){
		//tell the object handler to update everything
		this.objHandle.updateAllObjects();
	}

	private void flipBuffers(Graphics g) {

		Graphics2D g2 = (Graphics2D) g.create(0, 0, this.getWidth() + insets.left + insets.right,
				this.getHeight() + insets.top + insets.bottom);
		g2.drawImage(forwardBuffer, insets.left, insets.top, this);
		g2.dispose();
	}

}
