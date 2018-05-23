package view;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.*;


/*@Authour - Furqan Agwan 
* No Javadoc is created for the program as javadoc is not required for specification.
* However comments are available for better understanding of my code.*/

public class CreateStudentProfile extends VBox {

	/*
	 * class for the layout of CreateStudentProfiel using a entire VBox with a custom create box method using GirdPane as its Type.
	 * Methods in this class get the course, firstname, surname,e-mail etc.
	 * */
	private ComboBox<Course> cboCourses;
	private TextField pnumber, firstname, surname, email;
	private DatePicker inputdate;
	private Button createProfile;

	public CreateStudentProfile()
	{
		//
		cboCourses = new ComboBox<>();
		pnumber = new TextField();
		firstname = new TextField();
		surname = new TextField();
		email = new TextField();
		inputdate = new DatePicker(LocalDate.now());
		createProfile = new Button("Create Profile");
		createProfile.setPrefHeight(30);
		createProfile.setPadding(new Insets(5, 10, 5, 10));
		createProfile.setAlignment(Pos.CENTER);
		//
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(20,20,20,20));
		this.setSpacing(20);
		//
		cboCourses.setMinWidth(100);
		GridPane courses = createGrid("Select course:", cboCourses);
		GridPane inputpnumber = createGrid("Input P number:", pnumber);
		GridPane inputfirstname = createGrid("Input first name:", firstname);
		GridPane inputsurname = createGrid("Input surname:", surname);
		GridPane inputemail = createGrid("Input e-mail:", email);
		GridPane date = createGrid("Input Date:", inputdate);
		//

		//
		this.getChildren().addAll(courses,inputpnumber,inputfirstname, inputsurname, inputemail, date, createProfile );

	}
	private GridPane createGrid(String text, Control control) {
		GridPane pane = new GridPane();
		pane.setHgap(20);
		Label label = new Label(text);

		control.setPrefWidth(200);
		control.setMinWidth(200);
		label.setPrefWidth(100);
		label.setMinWidth(100);
		label.setAlignment(Pos.CENTER_RIGHT);

		pane.add(label, 0, 0);
		pane.add(control, 1, 0);
		pane.setAlignment(Pos.CENTER);
		return pane;
	}

	public void addCreateProfileHandler(EventHandler<ActionEvent> handler) {
		createProfile.setOnAction(handler);
	}



	public void populateComboBox(Course[] courses) {
		cboCourses.getItems().addAll(courses);
		cboCourses.getSelectionModel().select(0);
	}

	public Course getcourse(){
		return cboCourses.getSelectionModel().getSelectedItem();
	}
	public String getpnumber(){
		return pnumber.getText();
	}
	public String getstudnetfirstname(){

		return firstname.getText();
	}

	public String getstudnetsurname(){

		return surname.getText();
	}

	public String getemail(){
		return email.getText();
	}
	public LocalDate getdate(){
		return inputdate.getValue();
	}
	public ComboBox<Course> getcoursecbo(){

		return cboCourses;
	}
	public TextField getpno(){

		return pnumber;
	}
public TextField getfn(){

		return firstname;
	}

public TextField getsn(){

	return surname;
}

public TextField getem(){

	return email;
}
public DatePicker getdat(){

	return inputdate;
}

}
