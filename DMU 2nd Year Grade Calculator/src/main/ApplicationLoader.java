
package main;

import controller.StudentProfileController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.GUITabPane;

public class ApplicationLoader extends Application {

	private GUITabPane gui;	
	
	/*
	 * Application lifecycle entry point, overriding init() method in application but works in similar fashion, the methods gather its resources before the application gets to main();
	 * Lot more can be done to this application using Java FX CSS, we can apply certain style sheets to buttons hbox, vboxes etc.
	 * Notice how a controller object is initialised here gathering the model and gui bringing the the two together.
	 * */
	@Override
	public void init() {
		
		StudentProfile model = new StudentProfile();
		gui = new GUITabPane();
		new StudentProfileController(gui, model);	
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Student Profile Mark Submission Tool");
		stage.setScene(new Scene(gui,1025,550));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
