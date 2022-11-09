package online.nilsunilus.test.engine.io;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.glViewport;

import org.lwjgl.opengl.GL;

import online.nilsunilus.test.engine.utility.Logger;

public class WindowManager {
	
	private int window_width, window_height;
	private String window_title;
	private long glfwWindow = 0;
	
	public WindowManager(int window_width, int window_height, String window_title) {
		this.window_width = window_width;
		this.window_height = window_height;
		this.window_title = window_title;
	}
	
	public void createWindow() {
		glfwWindow = glfwCreateWindow(window_width, window_height, window_title, 0, 0);
		if(glfwWindow == 0)
			Logger.log("glfwCreateWindow() failed.", System.err);
		
		glfwMakeContextCurrent(glfwWindow);
		GL.createCapabilities();
		glViewport(0, 0, window_width, window_height);
	}
	
	public void clean() {
		glfwDestroyWindow(glfwWindow);
	}
	
	public long getGlfwWindow() {
		return glfwWindow;
	}

}
