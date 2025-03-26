import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GradeCalculator extends JFrame {
    private JPanel coursePanel;
    private ArrayList<Course> courses;

    public GradeCalculator() {
        this.setSize(400, 300); 
        this.setTitle("Grade Calculator App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        initializeCourses();
        courseSelectionMenu();

        this.setVisible(true);
    }

    public void courseSelectionMenu() {
        coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS)); 

        ButtonGroup bg = new ButtonGroup();

        for (Course course : this.courses) {
            JRadioButton rb = new JRadioButton(course.getCourseName());
            bg.add(rb);
            coursePanel.add(rb);
        }

        this.add(coursePanel);
    }

    public void initializeCourses() {
        courses = new ArrayList<>();
        courses.add(new Math102());
    }

    public static void main(String[] args) {
        new GradeCalculator();
    }
}
