import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GradeCalculator extends JFrame {
    private JPanel coursePanel;
    private ArrayList<Course> courses;
    private Course selected = null;

    public GradeCalculator() {
        this.setSize(400, 300); 
        this.setTitle("Grade Calculator App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        initializeCourses();
        courseSelectionMenu();

        this.setVisible(true);
    }

    public Course courseSelectionMenu() {
        coursePanel = new JPanel();
        coursePanel.setLayout(new BorderLayout()); 
        JLabel label = new JLabel("Select a course:", SwingConstants.CENTER);
        ArrayList<JRadioButton> radioButtons = new ArrayList<>();
        ButtonGroup group = new ButtonGroup();
    
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        continueButton.setSize(new Dimension(50,10));
    
        for (Course course : courses) {
            JRadioButton rb = new JRadioButton(course.getCourseName());
            radioButtons.add(rb);
            group.add(rb);
            coursePanel.add(rb);
    
            rb.addActionListener(e -> {
                continueButton.setEnabled(true);
            });
        }
    
        continueButton.addActionListener(e -> {
            for (JRadioButton rb : radioButtons) {
                if (rb.isSelected()) {
                    String selectedCourse = rb.getText();
                    selected = getCourseByName(selectedCourse);
                    break;
                }
            }
    
            JOptionPane.showMessageDialog(this, "You selected: " + selected.getCourseName());
        });
        
        coursePanel.add(label, BorderLayout.NORTH);
        coursePanel.add(continueButton, BorderLayout.SOUTH);
        this.add(coursePanel);
        return selected;
    }    

    public void initializeCourses() {
        courses = new ArrayList<>();
        courses.add(new Math102());
    }

    public Course getCourseByName(String name) {
        for (Course c : courses) {
            if (c.getCourseName().equals(name)) {
                return c;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        new GradeCalculator();
    }
}
