package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/*@Authour - Furqan Agwan 
* No Javadoc is created for the program as javadoc is not required for specification.
* However comments are available for better understanding of my code.*/


public class InputModuleMarks extends GridPane {

	/*
	 * class for the layout of ImpytmarksTab.
	 * The class contains methods to get labels in varrious form such as array or label.
	 * and some methods to set some default values for the hboxes and vboxesthese have been defined in the container defaults method.
	 * 
	 * 
	 * 
	 * */
	private Label[] title = { new Label("Module"), new Label("Cwk Mark"), new Label("Exam Mark") };
	private Label[] courses = { new Label("Profile Not Created"), new Label("Profile Not Created"),
			new Label("Profile Not Created"), new Label("Profile Not Created") };
	private TextField[] fields = { new TextField(), new TextField(), new TextField(), new TextField(), new TextField(),
			new TextField(), new TextField(), new TextField() };
	//
	Button clear = new Button("Clear");
	Button submit = new Button("Submit");
	HBox hbox = new HBox(clear, submit);

	GridPane grid = new GridPane();
	//
	VBox vbox = new VBox(35, title[0], courses[0], courses[1], courses[2], courses[3]);
	VBox vbox2 = new VBox(29, title[1], fields[0], fields[1], fields[2], fields[3]);
	VBox vbox3 = new VBox(29, title[2], fields[4], fields[5], fields[6], fields[7]);

	public InputModuleMarks() {
		// Seeting fonts for titles
		title[0].setFont(Font.font(title[0].getFont().getFamily(), FontWeight.BOLD, title[0].getFont().getSize()));
		title[1].setFont(Font.font(title[1].getFont().getFamily(), FontWeight.BOLD, title[1].getFont().getSize()));
		title[2].setFont(Font.font(title[2].getFont().getFamily(), FontWeight.BOLD, title[2].getFont().getSize()));

		//
		grid.addColumn(0, vbox);
		grid.addColumn(1, hbox);
		grid.addColumn(2, vbox2);
		grid.addColumn(3, vbox3);
		
		containerdefaults();
		this.setPadding(new Insets(30));
		this.setAlignment(Pos.TOP_CENTER);
		this.add(vbox, 0, 0);
		this.add(hbox, 1, 2);

		this.add(vbox2, 2, 0);
		this.add(vbox3, 3, 0);
		GridPane.setColumnSpan(hbox, 1);
		GridPane.setRowSpan(hbox, 2);
		this.setHgap(75);

	}

	private void containerdefaults() {

		vbox.setAlignment(Pos.CENTER);
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		HBox.setHgrow(hbox, Priority.ALWAYS);

		vbox2.setAlignment(Pos.CENTER);
		vbox3.setAlignment(Pos.CENTER);

		fields[0].setMaxWidth(75);
		fields[1].setMaxWidth(75);
		fields[2].setMaxWidth(75);
		fields[3].setMaxWidth(75);
		fields[4].setMaxWidth(75);
		fields[5].setMaxWidth(75);
		fields[6].setMaxWidth(75);
		fields[7].setMaxWidth(75);

		clear.setAlignment(Pos.BOTTOM_CENTER);
		submit.setAlignment(Pos.BOTTOM_CENTER);
		hbox.setAlignment(Pos.BOTTOM_CENTER);
		hbox.setPrefHeight(75);
		hbox.setSpacing(50);

		clear.setPadding(new Insets(10, 20, 10, 20));
		submit.setPadding(new Insets(10, 20, 10, 20));

	}

	public Label[] getcourselabelarray() {

		return courses;
	}

	public Label getcourselabel1() {

		return courses[0];
	}

	public Label getcourselabel2() {

		return courses[1];
	}

	public Label getcourselabel3() {

		return courses[2];
	}

	public Label getcourselabel4() {

		return courses[3];
	}

	public TextField[] gettextfieldsarray() {

		return fields;
	}

	public TextField gettextfield1() {
		return fields[0];
	}

	public TextField gettextfield2() {
		return fields[1];
	}

	public TextField gettextfield3() {
		return fields[2];
	}

	public TextField gettextfield4() {
		return fields[3];
	}

	public TextField gettextfield5() {
		return fields[4];
	}

	public TextField gettextfield6() {
		return fields[5];
	}

	public TextField gettextfield7() {
		return fields[6];
	}

	public TextField gettextfield8() {
		return fields[7];
	}

	public int gettextfield1asint(){
		
		return Integer.parseInt(fields[0].getText());
	}
public int gettextfield2asint(){
		
		return Integer.parseInt(fields[1].getText());
	}
	
public int gettextfield3asint(){
	
	return Integer.parseInt(fields[2].getText());
}
	
public int gettextfield4asint(){
	
	return Integer.parseInt(fields[3].getText());
}
	
public int gettextfield5asint(){
	
	return Integer.parseInt(fields[4].getText());
}
	
public int gettextfield6asint(){
	
	return Integer.parseInt(fields[5].getText());
}
public int gettextfield7asint(){
	
	return Integer.parseInt(fields[6].getText());
}

public int gettextfield8asint(){
	
	return Integer.parseInt(fields[7].getText());
}


public void inputcontrol() {
		fields[0].clear();
		fields[1].clear();
		fields[2].clear();
		fields[3].clear();
		fields[4].clear();
		fields[5].clear();
		fields[6].clear();
		fields[7].clear();
	}
 public void textfieldsdefaults(){
	    gettextfield1().setText("0");
		gettextfield2().setText("0");
		gettextfield3().setText("0");
		gettextfield4().setText("0");
		gettextfield5().setText("0");
		gettextfield6().setText("0");
		gettextfield7().setText("0");
		gettextfield8().setText("0");
 }
	public void addclearHandler(EventHandler<ActionEvent> handler) {
		clear.setOnAction(handler);
	}

	public void addsubmitHandler(EventHandler<ActionEvent> handler) {
		submit.setOnAction(handler);
	}
}
