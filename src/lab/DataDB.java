/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rajbi
 */
public class DataDB {

    private static File file = new File("data.txt");

   // public static List<StudentRecord> writeToFile() {
        
  //  }

    public static List<StudentRecord> readFromFile() throws FileNotFoundException {
        List<StudentRecord> records = new ArrayList<>();
        try {
            DataInputStream inFile = new DataInputStream(new FileInputStream(file));
            while (true) {
                try {
                    StudentRecord record = new StudentRecord();
                    record.setId(inFile.readUTF());
                    record.setSemester(inFile.readUTF());
                    record.setProgram(inFile.readUTF());
                    String tCourses = inFile.readUTF();
                    record.setCourse(new ArrayList<String>(Arrays.asList(tCourses.split(","))));
                    records.add(record);
                } catch (EOFException e) {
                    System.out.println(e);
                    inFile.close();
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not Found!");
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error in reading file");
        }
        return records;
    }
}
