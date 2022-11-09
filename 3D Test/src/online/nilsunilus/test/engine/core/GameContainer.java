package online.nilsunilus.test.engine.core;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import online.nilsunilus.test.engine.io.WindowManager;
import online.nilsunilus.test.engine.io.gfx.Renderer;
import online.nilsunilus.test.engine.io.gfx.ShaderManager;
import online.nilsunilus.test.engine.utility.Logger;

public class GameContainer {
	
	public static final int WINDOW_WIDTH = 2000;
	public static final int WINDOW_HEIGHT = 1500;
	public static final String WINDOW_TITLE = "3D Test";
	
	private WindowManager window_manager;
	private Renderer renderer;
	private ShaderManager shader_manager;
	private Game game = null;
	
	public GameContainer() {
		this.window_manager = new WindowManager(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE);
		this.renderer = new Renderer();
		this.shader_manager = new ShaderManager();
	}
	
	public void init() {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		
		if(!glfwInit())
			Logger.log("glfwInit() failed.", System.err);
		
		window_manager.createWindow();
		shader_manager.init();
		game.init();
	}
	
	public void run() {
		if(game == null)
			Logger.log("Game is not set.", System.err);
		
		while(!glfwWindowShouldClose(window_manager.getGlfwWindow())) {
			glfwPollEvents();
			renderer.render(window_manager, game);
		}
		
		clean();
		Logger.log("End test.", System.out);
		System.exit(-1);
	}
	
	private void clean() {
		window_manager.clean();
		Renderer.clean();
		shader_manager.clean();
		glfwTerminate();
	}
	
	public ShaderManager getShaderManager() {
		return shader_manager;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}

}
