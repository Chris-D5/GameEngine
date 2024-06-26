package jade;

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
		
		glfwMakeContextCurrent(glfwWindow);
		
		glfwSwapInterval(1);
		
		glfwShowWindow(glfwWindow);
		
		GL.createCapabilities();
	}
	
	public void loop() {
		while(!glfwWindowShouldClose(glfwWindow)) {
			glfwPollEvents();
			
			glClearColor(1.0f,0.0f,0.0f,1.0f);
			glClear(GL_COLOR_BUFFER_BIT);
			
			glfwSwapBuffers(glfwWindow);
		}
	}
	
}
