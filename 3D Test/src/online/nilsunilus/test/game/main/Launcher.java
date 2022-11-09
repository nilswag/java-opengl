package online.nilsunilus.test.game.main;

import online.nilsunilus.test.engine.core.Game;
import online.nilsunilus.test.engine.core.GameContainer;
import online.nilsunilus.test.engine.utility.Logger;

public class Launcher {
	
	public static void main(String[] args) {
		Logger.log("Start test.", System.out);
		GameContainer game_container = new GameContainer();
		Game game = new Test(game_container);
		game_container.setGame(game);
		game_container.init();
		game_container.run();
	}

}
