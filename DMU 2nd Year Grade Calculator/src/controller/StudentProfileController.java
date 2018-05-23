package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import main.ApplicationLoader;
import model.*;
import model.Module;
import view.CreateStudentProfile;
import view.GUITabPane;
import view.InputModuleMarks;
import view.OverviewResults;
import view.TheMenuBar;
/*
 * @Authour - Furqan Agwan 
 * No Javadoc is created for the program as javadoc is not required for specification.
 * However comments are available for better understanding of my code.
 * 
 * References 
 * 
 * Website
 * Dmu.ac.uk. (2017). Courses. [Online] Available at: http://www.dmu.ac.uk/study/courses/courses.aspx [Accessed 4 Apr. 2017]
 * 
 * Images
 * Anon, (2017). [Image] Available at: https://twitter.com/dmuleicester [Accessed 4 Apr. 2017].
 * 
 * */
public class StudentProfileController {

	/*	Declare fields of the view and model, controller is essentially the connection between the model and view.
	 * All operation happen here and so created methods for the controller would be private not accessible to the model nor the view, once again the 
	 * controller does have access to both. So methods used in the model and view are accessible to the controller.
	 * No methods are also static as then the model or view can use the method with creating an object of the class.
	 *
	 * */
	private GUITabPane view;
	private CreateStudentProfile csp;
	private InputModuleMarks imm;
	private OverviewResults ovr;
	private TheMenuBar tmb;
	private StudentProfile model;
	/*
	 * Constructor on the controller this will contain all operations on buttons label i.e event handler
	 * Make code clean create a separate method to put all handlers and uses the method in the constructor by calling it.
	 * */
	public StudentProfileController(GUITabPane view, StudentProfile model) {
		
		csp = view.getStundentProfile();
		imm = view.getInputModuleMarks();
		ovr = view.getOverviewResults();
		tmb = view.getTheMenuBar();
		//
		this.model = model;
		this.view = view;
		this.initialize();
		this.attachEventHandlers();

	}

	public GUITabPane getView() {
		return view;
	}

	public StudentProfile getModel() {
		return model;
	}

	/*
	 * Method with all required handlers for the programs, using lambda because its easier but each handler just points to
	 * a private method created in this class.
	 *
	 * */ 

	private void attachEventHandlers() {
		// Event handlers for MenuBar see method below
		tmb.addLoadHandler(e -> {
			loadDataState();
		});
		// Save model handler see method below
		tmb.addSaveHandler(e -> {
			saveDataState();
		});
		// Exit program handler
		tmb.addExitHandler(e -> {
			Platform.exit();
		});
		// About menu handler see method below
		tmb.addAboutHandler(e -> {
			createPopUpBox();
		});
		// Create Profile Handler see method below
		csp.addCreateProfileHandler(e -> {
			getStudentDetails(1);
		});
		// Input Marks Handler see method below
		imm.addclearHandler(e -> {
			clearImputMarks();
		});
		// Clear Button handler see method below
		imm.addsubmitHandler(e -> {
			getAllSubmitInfo();
		});
		// Overview Handler see method below
		ovr.addSaveOverviewHandler(e -> {
			saveOverviewOutput();
		});
	}
/*
 * Method to initialise the combo box with relevant course works in combination of the setupandgetcourse method.
 * */
	private void initialize() {
		Course[] courses = this.setupAndGetCourses();
		view.getCreateProfileTab().populateComboBox(courses);
	
	}
/*
 * This method will ultimately create the popup box the method is used in the About handler which 2 methods above this one (attachEventHandlers).
 * The method acts more as a convenience method.
 * */
	private void createPopUpBox() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		ImageView dmuimage = new ImageView("DMUICON.png");
		String alertboxtext = new String("DMU STUDENT SUBMISSION TOOL V1.0\n\n"
				+ "This application allows users to capture information about a second year undergraduate computing student it also allows the user to input information regading the course and module marks.\n\n For detailed information regarding the courses on offer at DMU click the logo ");
		dmuimage.setOnMouseClicked(e -> {
			ApplicationLoader application = new ApplicationLoader();
			application.getHostServices().showDocument("http://www.dmu.ac.uk/study/courses/courses.aspx");
		});
		alert.setTitle("About");
		alert.setHeaderText(null);
		alert.setContentText(alertboxtext);
		alert.setGraphic(dmuimage);
		alert.showAndWait();
	}
/*
 * Two methods below used to save state and restore state, handling file states need to be surrounded with 
 * try-catch-finally for the posibillity of the file not saving (saveDataState) or file not found (loadDataState).
 * The loadDataState contains the method getLoadDataState which is defined in this class, done to avoid code clutter.
 * 
 * The loadDataState will essentially refresh the view(GUI) this includes the first two tabs and populate the view with model loaded in.
 * Note look at the getLoadDataState comments for a better explanation on how this work because we are just calling the method here
 * logically this is were it would be placed because this method is then used in its respective handler.
 * For the getLoadData method look below the loadDataState method.
 * 
 * */
	private void saveDataState() {
		try {
	
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("StudentData.dat"));
			oos.writeObject(model);
			oos.flush();
			oos.close();
	
			Alert savefile = new Alert(AlertType.INFORMATION);
			savefile.setTitle("File Saved");
			savefile.setHeaderText(null);
			savefile.setContentText("File is saved");
			savefile.showAndWait();
	
		}
	
		catch (IOException e) {
	
			Alert filenotsave = new Alert(AlertType.ERROR);
			filenotsave.setTitle("Error");
			filenotsave.setHeaderText(null);
			filenotsave.setContentText("File did not save succesfully, please try again");
			filenotsave.showAndWait();
			e.printStackTrace();
	
		}
	
	}

	private void loadDataState() {
	
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("StudentData.dat"))) {
	
			model = (StudentProfile) ois.readObject();
			getLoadDataState();
			Alert loadSucces = new Alert(AlertType.INFORMATION);
			loadSucces.setTitle("Succes");
			loadSucces.setHeaderText(null);
			loadSucces.setContentText("File succesfully loaded");
			loadSucces.showAndWait();
	
		} catch (IOException ioExcep) {
			ioExcep.printStackTrace();
		}
	
		catch (ClassNotFoundException c) {
			c.printStackTrace();
		}
	
	}
/*
 * Now the model has bee assigned the external file we now have some data in the model, but it will not display/prepopulate the view to reflect this.
 * In that case the method below treats the data as a new profile and therefore sets the course for the combobox and so forth to essentially populate the view from the information 
 * in now assigned to the model.
 * 
 * */
	private void getLoadDataState() {
	
		csp.getcoursecbo().setValue(model.getCourse());
		csp.getpno().setText(model.getpNumber());
		csp.getfn().setText(model.getStudentName().getFirstName());
		csp.getsn().setText(model.getStudentName().getFamilyName());
		csp.getem().setText(model.getEmail());
		csp.getdat().setValue(model.getDate());
		getStudentDetails(0);
		if (model.getCourse().getCourseName().equals("Computer Science")) {
			getComputerScienceMarks();
	
		} else if (model.getCourse().getCourseName().equals("Computer Security")) {
			getComputerSecurityMarks();
		} else if (model.getCourse().getCourseName().equals("Forensic Computing")) {
			getForensiccomputingmarks();
	
		} else if (model.getCourse().getCourseName().equals("Software Engineering")) {
			getSoftwareEngineeringMarks();
		}
	}
/*
 * According to specigfication we had to use printwritert to write the result in the AreaText in text file. This method directly is used in the saveOverView handler
 * Notice the printwriter variable uses the println method taking the parameter of the text of the are. The submit handler needs to be pressed in order to get some
 * figures for the student else the function will still work on empty values but will have 0 as credits passed and average yearmark.
 * */
	private void saveOverviewOutput() {
		try {
			String filename = "StudentOverviewResults.txt";
			PrintWriter outputstream = new PrintWriter(filename);
			Alert sucess = new Alert(AlertType.INFORMATION);
			outputstream.println(ovr.getAreaInput());
			outputstream.close();
			sucess.setTitle("File Saved Sucessfully");
			sucess.setContentText(
					"File saved succesfully, please check whithin the StudentProfile file in this project directory to see the txt file for the Student");
			sucess.setHeaderText(null);
			sucess.showAndWait();
		} catch (FileNotFoundException e) {

			Alert fail = new Alert(AlertType.ERROR);
			fail.setTitle("File not Saved");
			fail.setHeaderText(null);
			fail.setContentText("Error saving Student Overview Results");
			e.printStackTrace();
		}

	}
/*
 * Method created in the imputmodulemarks class, simply calling it here under another method in the controller class.
 * Open declaration of the method will show that the clear method is just applied to every textfield.
 * 
 * */
	private void clearImputMarks() {
		imm.inputcontrol();
	}
/*
 * 
 * 
 * */
	private void getStudentDetails(int tab) {

		//
		model.setCourse(view.getStundentProfile().getcourse());
		model.setpNumber(view.getStundentProfile().getpnumber());
		model.setStudentName(new Name(view.getStundentProfile().getstudnetfirstname(),view.getStundentProfile().getstudnetsurname()));
		model.setEmail(view.getStundentProfile().getemail());
		model.setDate(view.getStundentProfile().getdate());

		// Get the information from the model since it had now been updated by
		// the functions above, this whole execution is a function to avoid
		// writing the entire thing in the handler

		model.getpNumber();
		model.getStudentName();
		model.getEmail();
		model.getDate();

		// needs to get this to the input marks tabs to prepopulate the labels
		// and textfields

		imm.gettextfield5().setVisible(true);
		imm.gettextfield6().setVisible(true);
		imm.gettextfield7().setVisible(true);
		imm.gettextfield8().setVisible(true);

		if (model.getCourse().getCourseName().equals("Computer Science")) {
			populatecomputerscience();

		} else if (model.getCourse().getCourseName().equals("Computer Security")) {
			populatecomputersecurity();
		} else if (model.getCourse().getCourseName().equals("Forensic Computing")) {
			populateforensiccomputing();

		} else if (model.getCourse().getCourseName().equals("Software Engineering")) {
			populatesoftwareengineering();
		} else {

		}
		// Switch tabs
		view.changeTab(tab);

	}
/*
 * 
 * Methods to retrieve all information to pass to the overview results tab, also allows the use of the use of specific methods in the model such as yearaverage method and credits passed method.
 * We can now also prepoulate the area text as we have the information regarding the student and his/her marks to workout weather they achieved 30 credits for that specific module.
 * 
 * */
	private void getAllSubmitInfo() {

		if (model.getCourse().getCourseName().equals("Computer Science")) {

			setComputerSciencemMarks();
			model.getCourse().getModuleByCode("CTEC2121").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2602").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2701").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2901").getModuleMark();

		} else if (model.getCourse().getCourseName().equals("Computer Security")) {

			setComputerSecurityMarks();
			model.getCourse().getModuleByCode("CTEC2121").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2122").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2701").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2903").getModuleMark();

		} else if (model.getCourse().getCourseName().equals("Forensic Computing")) {

			setForensiccomputingmarks();
			model.getCourse().getModuleByCode("CTEC2121").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2122").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2701").getModuleMark();
			model.getCourse().getModuleByCode("LAWG2003").getModuleMark();
		} else if (model.getCourse().getCourseName().equals("Software Engineering")) {

			setSoftwareEngineeringMarks();
			model.getCourse().getModuleByCode("CTEC2121").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2602").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2701").getModuleMark();
			model.getCourse().getModuleByCode("CTEC2901").getModuleMark();

		}
		ovr.setAreaText(model.getStudentName().getFullName(), model.getpNumber(), model.getEmail(),
				model.getDate().toString(), model.getCourse().toString(), model.getCourse().creditsPassed(),
				model.getCourse().yearAverageMark());
		view.changeTab(2);

	}
	/*
	 * Methods to use in the loadDataSate method, essentially these methods will prepoulate all textfields with data gather from the model. 
	 * Since the model has all the relevant information regarding the course, modules and module marks we have to set the corresponding textfields 
	 * 
	 * Depending on what course is saved in the model. This method will set the available textfields with correct cwk mark and exam mark in their right textfiedls.
	 * There are four methods for each course as some courses have different modules and may be coursework or exam only in different textfiedls boxes.
	 * 
	 * */
	private void getComputerScienceMarks() {
		imm.gettextfield1().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2121").getCwkMark()));
		imm.gettextfield2().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2602").getCwkMark()));
		imm.gettextfield6().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2602").getExamMark()));
		imm.gettextfield3().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2701").getCwkMark()));
		imm.gettextfield4().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2901").getCwkMark()));
		imm.gettextfield8().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2901").getExamMark()));

	}

	private void getComputerSecurityMarks() {
		imm.gettextfield1().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2121").getCwkMark()));
		imm.gettextfield2().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2122").getCwkMark()));
		imm.gettextfield6().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2122").getExamMark()));
		imm.gettextfield3().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2701").getCwkMark()));
		imm.gettextfield4().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2903").getCwkMark()));
	}

	private void getForensiccomputingmarks() {

		imm.gettextfield1().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2121").getCwkMark()));
		imm.gettextfield2().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2122").getCwkMark()));
		imm.gettextfield6().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2122").getExamMark()));
		imm.gettextfield3().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2701").getCwkMark()));
		imm.gettextfield4().setText(String.valueOf(model.getCourse().getModuleByCode("LAWG2003").getCwkMark()));
		imm.gettextfield8().setText(String.valueOf(model.getCourse().getModuleByCode("LAWG2003").getExamMark()));
	}

	private void getSoftwareEngineeringMarks() {
		imm.gettextfield1().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2121").getCwkMark()));
		imm.gettextfield2().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2602").getCwkMark()));
		imm.gettextfield6().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2602").getExamMark()));
		imm.gettextfield3().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2701").getCwkMark()));
		imm.gettextfield4().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2901").getCwkMark()));
		imm.gettextfield8().setText(String.valueOf(model.getCourse().getModuleByCode("CTEC2901").getExamMark()));

	}

	private void setComputerSciencemMarks() {

		model.getCourse().getModuleByCode("CTEC2121").setCwkMark(imm.gettextfield1asint());
		model.getCourse().getModuleByCode("CTEC2602").setCwkMark(imm.gettextfield2asint());
		model.getCourse().getModuleByCode("CTEC2602").setExamMark(imm.gettextfield6asint());
		model.getCourse().getModuleByCode("CTEC2701").setCwkMark(imm.gettextfield3asint());
		model.getCourse().getModuleByCode("CTEC2901").setCwkMark(imm.gettextfield4asint());
		model.getCourse().getModuleByCode("CTEC2901").setExamMark(imm.gettextfield8asint());

	}

	private void setComputerSecurityMarks() {
		model.getCourse().getModuleByCode("CTEC2121").setCwkMark(imm.gettextfield1asint());
		model.getCourse().getModuleByCode("CTEC2122").setCwkMark(imm.gettextfield2asint());
		model.getCourse().getModuleByCode("CTEC2122").setExamMark(imm.gettextfield6asint());
		model.getCourse().getModuleByCode("CTEC2701").setCwkMark(imm.gettextfield3asint());
		model.getCourse().getModuleByCode("CTEC2903").setCwkMark(imm.gettextfield4asint());
	}

	private void setForensiccomputingmarks() {

		model.getCourse().getModuleByCode("CTEC2121").setCwkMark(imm.gettextfield1asint());
		model.getCourse().getModuleByCode("CTEC2122").setCwkMark(imm.gettextfield2asint());
		model.getCourse().getModuleByCode("CTEC2122").setExamMark(imm.gettextfield6asint());
		model.getCourse().getModuleByCode("CTEC2701").setCwkMark(imm.gettextfield3asint());
		model.getCourse().getModuleByCode("LAWG2003").setCwkMark(imm.gettextfield4asint());
		model.getCourse().getModuleByCode("LAWG2003").setExamMark(imm.gettextfield8asint());
	}

	private void setSoftwareEngineeringMarks() {
		model.getCourse().getModuleByCode("CTEC2121").setCwkMark(imm.gettextfield1asint());
		model.getCourse().getModuleByCode("CTEC2602").setCwkMark(imm.gettextfield2asint());
		model.getCourse().getModuleByCode("CTEC2602").setExamMark(imm.gettextfield6asint());
		model.getCourse().getModuleByCode("CTEC2701").setCwkMark(imm.gettextfield3asint());
		model.getCourse().getModuleByCode("CTEC2901").setCwkMark(imm.gettextfield4asint());
		model.getCourse().getModuleByCode("CTEC2901").setExamMark(imm.gettextfield8asint());

	}

	private Course[] setupAndGetCourses() {
		Module ctec2121 = new Module("CTEC2121", "Organisations, Project Management and Research", true);
		Module ctec2122 = new Module("CTEC2122", "Forensics and Security", false);
		Module ctec2602 = new Module("CTEC2602", "OO Software Design and Development", false);
		Module ctec2701 = new Module("CTEC2701", "Multi-tier Web Applications", true);
		Module ctec2901 = new Module("CTEC2901", "Data Structures and Algorithms", false);
		Module lawg2003 = new Module("LAWG2003", "Issues in Criminal Justice", false);
		Module ctec2903 = new Module("CTEC2903", "System Defence Strategies", true);
		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec2121);
		compSci.addModule(ctec2602);
		compSci.addModule(ctec2701);
		compSci.addModule(ctec2901);
		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec2121);
		softEng.addModule(ctec2602);
		softEng.addModule(ctec2701);
		softEng.addModule(ctec2901);
		Course compSecu = new Course("Computer Security");
		compSecu.addModule(ctec2121);
		compSecu.addModule(ctec2122);
		compSecu.addModule(ctec2701);
		compSecu.addModule(ctec2903);
		Course forenComp = new Course("Forensic Computing");
		forenComp.addModule(ctec2121);
		forenComp.addModule(ctec2122);
		forenComp.addModule(ctec2701);
		forenComp.addModule(lawg2003);
		Course[] courses = new Course[4];
		courses[0] = compSci;
		courses[1] = softEng;
		courses[2] = compSecu;
		courses[3] = forenComp;
		return courses;
	}
/*
 * Methods to populate labels and also textfields based on user course choice 
 * each course has its own method and its own modules which the model has information about already beforehand, so methods have been created with a combination of 
 * if and elseif statements in the actual create profile button handler to determine what course the user choose
 * keep in mind the user details will be stored in the model (Student Profile Class) once the tab changes from "create profile" to "input marks".
 * 
 * Furthermore set all applicable textfields to display 0 everytime the user changes his/her mind regarding course and presses the "Create Profile" button.
 * In the case the user changes details regarding the student at the input mark tab then the model will simply go back and update the information, 
 * however the program will treat it as a new student in that regards.
 * 
 * */
	private void populatecomputerscience() {
		imm.getcourselabel1().setText(model.getCourse().getModuleByCode("CTEC2121").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2121").getModuleName());
		imm.getcourselabel2().setText(model.getCourse().getModuleByCode("CTEC2602").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2602").getModuleName());
		imm.getcourselabel3().setText(model.getCourse().getModuleByCode("CTEC2701").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2701").getModuleName());
		imm.getcourselabel4().setText(model.getCourse().getModuleByCode("CTEC2901").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2901").getModuleName());
		imm.gettextfield5().setVisible(false);
		imm.gettextfield7().setVisible(false);
		imm.textfieldsdefaults();

	}

	private void populatecomputersecurity() {
		imm.getcourselabel1().setText(model.getCourse().getModuleByCode("CTEC2121").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2121").getModuleName());
		imm.getcourselabel2().setText(model.getCourse().getModuleByCode("CTEC2122").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2122").getModuleName());
		imm.getcourselabel3().setText(model.getCourse().getModuleByCode("CTEC2701").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2701").getModuleName());
		imm.getcourselabel4().setText(model.getCourse().getModuleByCode("CTEC2903").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2903").getModuleName());
		imm.gettextfield5().setVisible(false);
		imm.gettextfield7().setVisible(false);
		imm.gettextfield8().setVisible(false);
		imm.textfieldsdefaults();
	}

	private void populateforensiccomputing() {
		imm.getcourselabel1().setText(model.getCourse().getModuleByCode("CTEC2121").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2121").getModuleName());
		imm.getcourselabel2().setText(model.getCourse().getModuleByCode("CTEC2122").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2122").getModuleName());
		imm.getcourselabel3().setText(model.getCourse().getModuleByCode("CTEC2701").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2701").getModuleName());
		imm.getcourselabel4().setText(model.getCourse().getModuleByCode("LAWG2003").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("LAWG2003").getModuleName());
		imm.gettextfield5().setVisible(false);
		imm.gettextfield7().setVisible(false);
		imm.textfieldsdefaults();

	}

	private void populatesoftwareengineering() {
		imm.getcourselabel1().setText(model.getCourse().getModuleByCode("CTEC2121").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2121").getModuleName());
		imm.getcourselabel2().setText(model.getCourse().getModuleByCode("CTEC2602").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2602").getModuleName());
		imm.getcourselabel3().setText(model.getCourse().getModuleByCode("CTEC2701").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2701").getModuleName());
		imm.getcourselabel4().setText(model.getCourse().getModuleByCode("CTEC2901").getModuleCode() + " "
				+ model.getCourse().getModuleByCode("CTEC2901").getModuleName());
		imm.gettextfield5().setVisible(false);
		imm.gettextfield7().setVisible(false);
		imm.textfieldsdefaults();
	}

}
