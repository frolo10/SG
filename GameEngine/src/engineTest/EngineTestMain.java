package engineTest;

import core.ObjectHandler;
import core.GameWindow;

public class EngineTestMain {

	private static ObjectHandler obj;

	public static void main(String[] args) {
		GameWindow w = new GameWindow();
		w.initialize("blah", 400, 400, 100);

		obj = w.getObjectHandler();
		w.run();
		
		for (int i = 0; i < 100000; i++) {
			SquareObject testSquare = new SquareObject(i);

			obj.registerObject(testSquare, i/(3200));
		}
//		for(int j = 0; j < 100000; j++){
//			SquareObject testSquare = new SquareObject(10000 - j);
//			obj.registerObject(testSquare, j/(32000));
//		}
		


//		w.shutDown();
	}
}
