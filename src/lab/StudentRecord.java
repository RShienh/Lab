/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rajbi
 */
public class StudentRecord {

    private String id, program, semester;
    private String[] course = null;

    public String[] getCourses() {
        return course;
    }

    public void setCourse(ArrayList<String> course) {
        List<String> list = course;
        this.course = list.toArray(new String[0]);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) throws RuntimeException {
        if (program != null) {
            this.program = program;
        } else {
            throw new RuntimeException("Id cannot be empty");
        }
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
           String courses = "";
        for (String course : course) {
            courses += course;
        }
        return "ID: " + this.id + " | Program: " + this.program + " | Semester: " + this.semester + " | Courses=" + courses + " \n";
    }

}
