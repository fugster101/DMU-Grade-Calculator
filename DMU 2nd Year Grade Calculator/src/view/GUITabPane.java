package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;

public class GUITabPane extends BorderPane {

	private CreateStudentProfile csp;
	private InputModuleMarks imm;
	private OverviewResults ovr;
	private TheMenuBar tmb;
	private TabPane tp;
	
	
	/*
	 * GUITabPane consists of three tabs pushed into a single tabpane using three tabs separate tabs. The constructor of each individual tabs gui contains the entire layout. 
	 * Explore each class for each tab by looking at the OverviewResults class constructor or the CreateStudentProfile constructor the code in the constructor essentially builds
	 * each tabs gui.
	 * 
	 * We need method to get each tab for its respective class. Ultimately this class will pass though the constructor for the controller
	 * 
	 * */
	public GUITabPane() {
		//
		tp = new TabPane();
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); 
		//
		csp = new CreateStudentProfile();
		imm = new InputModuleMarks();
		ovr = new OverviewResults();
		tmb = new TheMenuBar();
		//
		Tab t1 = new Tab("Create Profile", csp);
		Tab t2 = new Tab("Input Marks", imm);
		Tab t3 = new Tab("Overview Results", ovr);
		
		//add tabs to tab pane
		tp.getTabs().addAll(t1, t2, t3);
		// Setting in BorderPane layout top and center logically the centre will be the tabpane and top will be the menu bar completing a full GUI.
		this.setTop(tmb);
		this.setCenter(tp);
		
	}

	
	public CreateStudentProfile getStundentProfile() {
		return csp;
	}

	
	public OverviewResults getOverviewResults() {
		return ovr;
	}
	
	public InputModuleMarks getInputModuleMarks() {
		return imm;
	}
	
	public TheMenuBar getTheMenuBar() {

		return tmb;
	}
	
		public TabPane getTabPane() {
		return tp;
	}
		public CreateStudentProfile getCreateProfileTab() {
			return csp;
		}

		public InputModuleMarks getInputModuleMarksTab() {
			return imm;
		}

		public OverviewResults getOverviewResultsTab() {
			return ovr;
		}
		
	/*
	 * Method to change tabs this class is the most suitable place to put this method or in the controller.
	 * */
	public void changeTab(int index) {
		tp.getSelectionModel().select(index);
	}


}
