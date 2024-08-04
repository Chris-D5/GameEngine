package engine;

import java.util.ArrayList;
import java.util.List;

import Components.Component;

public class GameObject {
	private List<Component> components; 
	
	public GameObject() {
		this.components = new ArrayList<Component>();
	}
	
	public <T extends Component>T getComponent(Class<T> componentClass) {
		for (Component component : components) {
			if (componentClass.isInstance(component)) {
				try {
					return componentClass.cast(component);
				}
				catch(ClassCastException e) {
					System.out.println("Error: Could Not Casting Component");
					assert false : ""; 
				}
			}
		}
		return null;
	}
	
	public <T extends Component> void removeComponent(Class<T> componentClass) {
		for (Component component : components) {
			if (componentClass.isInstance(component)) {
				if (components.remove(component)) {
					return;
				}
				else {
					System.out.println("Error: Componenet Could Not Be Removed");
					assert false : "";
					return;
				}
			}
		}
		System.out.println("Error: Componenet Not Found");
		assert false : "";
	}
	
	public void addComponent(Component component) {
		components.add(component);
		component.setGameObject(this);
	}
	
	public void start() {
		
	}
	
	public void update() {
		
	}
}
