package engineTest;

import java.awt.Color;
import java.awt.Graphics2D;

import core.DrawableObject;

public class SquareObject implements DrawableObject{
	
	private int color = 0;
	private Color[] colors = new Color[6];
	
	private int x, y;
	private int size;
	private int vX, vY;
	
	public SquareObject(int i){
		x = i; y = i;
		size = 10;
		vX = 1; vY = 2;
		colors[0] = Color.red;
		colors[1] = Color.white;
		colors[2] = Color.green;
		colors[3] = Color.blue;
		colors[4] = Color.orange;
		colors[5] = Color.yellow;
	}

	@Override
	public void drawSelf(Graphics2D g) {
		
		if(color > 5){
			color = 0;
		}
		g.setColor(colors[color]);
		
		g.fillRect(x, y, size, size);
	}

	@Override
	public void updateSelf() {
		
		if(x < 0){
			
			if(vX == -2){
				vX = 1;
			}else{
				vX = 2;
			}
			color++;
		}else if(x > 390){
			if(vX == 2){
				vX = -1;
			}else{
				vX = -2;
			}
			color++;
		}
		if(y < 0){
			vY = 2;
			color++;
		}else if(y > 390){
			vY = -2;
			color++;
		}
	
		
		x+=vX;
		y+=vY;
	}

}
