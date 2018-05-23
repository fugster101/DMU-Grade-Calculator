package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class OverviewResults extends BorderPane {
/*
 * Layout for the OverviewResults tab using borderpane, setting the centre the TextArea and bottom the button.
 * 
 * */
	private TextArea area = new TextArea();
	private Button saveOverview = new Button("Save Overview");
	private VBox vbox = new VBox(saveOverview);

public OverviewResults()
{
	area.setEditable(false);
	area.setText("Overview will appear here");
	area.setMaxSize(950, 325);

	BorderPane.setAlignment(saveOverview, Pos.BASELINE_CENTER);
	BorderPane.setAlignment(area, Pos.TOP_CENTER);
	BorderPane.setMargin(area, new Insets(60, 60, 60, 60));
	BorderPane.setMargin(saveOverview, new Insets(15, 15, 15, 15));
	containerprefrence();
	this.setCenter(area);
	this.setBottom(saveOverview);
}




private void containerprefrence(){

	vbox.setAlignment(Pos.CENTER);
	saveOverview.setMaxHeight(500);
	saveOverview.setPadding(new Insets(10, 25, 10, 25));

}

public void setAreaText(String Name, String Pnum , String Email, String Date, String Course, int Credits, int Averagepass){


	Name = "Name:"+ " " + Name + "\n";
	Pnum = "PNo:" + " " +  Pnum + "\n";
	Email = "Email:" +" "+ Email + "\n";
	Date = "Date:" +" "+ Date + "\n";
	Course = "Course:" +" "+ Course + "\n\n"  ;
	String areafiller = "2nd year average:" + "\n" + "=============="+ "\n";
	String cred = "Credits passed:" + " " +  Integer.toString(Credits) + "\n";
	String avera = "Year average mark:" + " " + Integer.toString(Averagepass) + "\n";
	String studentinfo = Name + Pnum + Email + Date + Course + areafiller + cred + avera;
	area.setText(studentinfo);
}


public String getAreaInput() {
	return area.getText();
}

public void addSaveOverviewHandler(EventHandler<ActionEvent> handler) {
	saveOverview.setOnAction(handler);
}


}
