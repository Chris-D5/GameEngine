package engine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
	private int width,height;
	private String title;
	private long glfwWindow;
	
	private static Window window;
	
	private static Scene currentScene = null; 
	
	private Window() {
		this.width = 1920;
		this.height = 1080;
		this.title = "GameEngine";
	}
	
	public static Window get() {
		if (window == null) {
			window = new Window();
		}
		return window;
		
	}
	
	public void run() {
		System.out.println("Testing - LWJGL " + Version.getVersion() );
		
		init();
		loop();
		
		//free memory and close window
		
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to Initialize GLFW.");
			
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);
		
		glfwWindow = glfwCreateWindow(this.width,this.height,this.title,NULL,NULL);
		
		if (glfwWindow == NULL) {
			throw new IllegalStateException("Failed to Create the GLFW window");
		}
		
		glfwSetCursorPosCallback(glfwWindow, MouseListener::cursorPositionCallback);
		glfwSetMouseButtonCallback(glfwWindow,MouseListener::mouseCallback);
		glfwSetScrollCallback(glfwWindow,MouseListener::scrollCallback);
		glfwSetKeyCallback(glfwWindow,KeyListener::keyCallback);
		
		
		glfwMakeContextCurrent(glfwWindow);
		
		glfwSwapInterval(1);
		
		glfwShowWindow(glfwWindow);
		
		GL.createCapabilities();
		
		currentScene = new Scene();
	}
	
	public void loop() {
		while(!glfwWindowShouldClose(glfwWindow)) {
			glfwPollEvents();
			
			glClearColor(1.0f,1.0f,1.0f,1.0f);
			glClear(GL_COLOR_BUFFER_BIT);
			
			Listener.changeListener(KeyListener.get());
			if (KeyListener.getButton(GLFW_KEY_SPACE)) {
				System.out.println("Space is pressed");
			}
			
			Listener.changeListener(MouseListener.get());
			if (MouseListener.getButton(GLFW_MOUSE_BUTTON_LEFT)) {
				System.out.println("Left Click");
				System.out.println("X: " + MouseListener.getXPosition() + " Y: " + MouseListener.getYPosition());
			}
			
			currentScene.update();
			
			glfwSwapBuffers(glfwWindow);
		}
	}
	
}
