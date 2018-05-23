
package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/*@Authour - Furqan Agwan 
* No Javadoc is created for the program as javadoc is not required for specification.
* However comments are available for better understanding of my code.*/

public class TheMenuBar extends MenuBar {
/*
 * Layout for the menubar using MenuBar.
 * Adding menuitems to the menu and then adding the menus to the menubar to complete the menubar, relatively easy to do.
 * Added get methods for menus not necessilary bit may be useful.
 * 
 * */
	private Menu file,help;
	private MenuItem load, save, exit, about;

	public TheMenuBar() {      

		//
		file = new Menu("_File");
		//
		load = new MenuItem("_Load Student Data");
		load.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));
		//
		save = new MenuItem("_Save Student Data");
		save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		//
		exit = new MenuItem("E_xit");
		exit.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		//
		file.getItems().addAll(load,save,exit);
		//
		help = new Menu("_Help");
		//
		about = new MenuItem("_About");
		about.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));
		//
		help.getItems().add(about);
		//
		this.getMenus().addAll(file,help);
	}

	
	//
	public Menu getfilemenu(){
		return file;
	}
	public Menu gethelpmenu(){
		return help;
	}
	
	//
	public void addLoadHandler(EventHandler<ActionEvent> handler) {
		load.setOnAction(handler);
	}
    
    public void addSaveHandler(EventHandler<ActionEvent> handler) {
  		save.setOnAction(handler);
  	}
    
    public void addExitHandler(EventHandler<ActionEvent> handler) {
  		exit.setOnAction(handler);
  	}
    
    public void addAboutHandler(EventHandler<ActionEvent> handler) {
  		about.setOnAction(handler);
  	}

}
