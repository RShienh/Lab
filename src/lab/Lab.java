/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author rajbi
 */
public class Lab extends Application {

    protected BorderPane getPane() {

        BorderPane borderPane = new BorderPane();
        //Create grid
        //create vbox
        //create another vbox
        //create one hbox
        HBox hBox = new HBox(20);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        Button Bdisplay = new Button("Display");
        Button BSave = new Button("Save");
        Button Bexit = new Button("Exit");
        hBox.getChildren().addAll(Bdisplay, BSave, Bexit);

        GridPane gp = new GridPane();
        Label id = new Label("ID :");
        Label pr = new Label("Program: ");
        Label sem = new Label("Semester: ");
        Label cr = new Label("Courses :");
        TextField tid = new TextField();

        String[] programs = {"ITS", "ITE", "ITC"};
        ComboBox cb = new ComboBox();
        cb.getItems().addAll(programs);

        //Hbox for radiobuttons
        HBox radioBox = new HBox(20);
        radioBox.setPadding(new Insets(5, 5, 5, 5));
        RadioButton r1 = new RadioButton("1");
        RadioButton r2 = new RadioButton("2");
        RadioButton r3 = new RadioButton("3");
        RadioButton r4 = new RadioButton("4");
        radioBox.getChildren().addAll(r1, r2, r3, r4);

        ToggleGroup group = new ToggleGroup();
        r1.setToggleGroup(group);
        r2.setToggleGroup(group);
        r3.setToggleGroup(group);
        r4.setToggleGroup(group);

        //HBox for checkbuttons
        HBox checkBox = new HBox(20);
        checkBox.setPadding(new Insets(5, 5, 5, 5));
        CheckBox c1 = new CheckBox("C1");
        CheckBox c2 = new CheckBox("C2");
        CheckBox c3 = new CheckBox("C3");
        CheckBox c4 = new CheckBox("C4");
        CheckBox c5 = new CheckBox("C5");
        checkBox.setAlignment(Pos.CENTER);
        checkBox.getChildren().addAll(c1, c2, c3, c4, c5);

        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.add(id, 0, 0);
        gp.add(tid, 1, 0);
        gp.add(pr, 0, 1);
        gp.add(cb, 1, 1);
        gp.add(sem, 0, 2);
        gp.add(radioBox, 1, 2);
        gp.add(cr, 0, 3);
        gp.add(checkBox, 1, 3);

        VBox lBox = new VBox();
        lBox.getChildren().add(gp);

        VBox cBox = new VBox(50);
        ListView listView = new ListView();
        
        cBox.setPadding(new Insets(10, 10, 10, 10));
        cBox.setAlignment(Pos.CENTER);
        cBox.getChildren().add(listView);

        BSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                StudentRecord record = new StudentRecord();
                record.setId(tid.getText());
                ArrayList<String> courses = new ArrayList<>();
                StudentRecord sr = new StudentRecord();

                sr.setId(tid.getText());

                try {
                    if (cb.getValue().toString() != null) {
                        sr.setProgram(cb.getValue().toString());
                    }
                } catch (RuntimeException ex) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR, " Program can not be null");
                    alert.showAndWait();

                }

                if (r1.isSelected()) {
                    sr.setSemester(r1.getText());
                } else if (r2.isSelected()) {
                    sr.setSemester(r2.getText());
                } else if (r3.isSelected()) {
                    sr.setSemester(r3.getText());
                } else if (r4.isSelected()) {
                    sr.setSemester(r4.getText());
                }

                if (c1.isSelected()) {
                    courses.add(c1.getText());
                }
                if (c2.isSelected()) {
                    courses.add(c2.getText());
                }
                if (c3.isSelected()) {
                    courses.add(c3.getText());
                }
                if (c4.isSelected()) {
                    courses.add(c4.getText());
                }
                sr.setCourse(courses);

                Alert alert;
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("Data.txt", true));
                    dataOutputStream.writeUTF(sr.getId());
                    dataOutputStream.writeUTF(sr.getProgram());
                    dataOutputStream.writeUTF(sr.getSemester());
                    //out.writeUTF(sr.getCourse());
                    String[] s = sr.getCourses();
                    String stud = "";
                    for (String studentCourse : s) {
                        stud += studentCourse + "|";
                    }
                    dataOutputStream.writeUTF(stud);
                    dataOutputStream.close();
                    alert = new Alert(Alert.AlertType.INFORMATION, " Saved");
                    alert.showAndWait();

                } catch (IOException ex) {
                    alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                    alert.showAndWait();
                }
            }
        });

        Bdisplay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<StudentRecord> records;
                try {
                    records = DataDB.readFromFile();
                    listView.getItems().addAll(FXCollections.observableArrayList(records));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Lab.class.getName()).log(Level.SEVERE, null, ex);
                }               
            }
        });

        Bexit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        borderPane.setLeft(lBox);
        borderPane.setCenter(cBox);
        borderPane.setBottom(hBox);

        return borderPane;
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(getPane(), 550, 200);

        primaryStage.setTitle("Semester ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
