import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class Course {
    public String[] letterGrades = {"A ", "A-", "B+", "B ", "C+", "C ", "C-", "D+", "D ", "F "};
    protected String courseName;
    protected double[] notes;
    protected double[] weights;
    protected String[] exams;
    protected boolean isCatalog;
    protected int[] catalogNotes;

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public double calculateAverage(double[] notes) {
        double average = 0;
        for (int i = 0; i < notes.length; i++) {
            average += notes[i] * this.weights[i] / 100;
        }
        return average;
    }

    public double calculateRequiredFinal(double wantedNote) {
        double[] tempNotes = notes.clone();
        tempNotes[4] = 0; 
        double noteBeforeFinal = calculateAverage(tempNotes);
        return (wantedNote - noteBeforeFinal) * 100 / weights[4];
    }
    
    public String[] getExams() {
        return this.exams;
    }

    public String getCourseName() {
        return this.courseName;
    }
    
    public double[] getNotes() {
        return this.notes;
    }

    public double[] getWeights() {
        return this.weights;
    }

    public boolean isCatalog() {
        return this.isCatalog;
    }
}