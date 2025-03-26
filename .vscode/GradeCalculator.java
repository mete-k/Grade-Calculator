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
        label.setFont(new Font("Arial", Font.BOLD, 20));
        coursePanel.add(label, BorderLayout.NORTH);
    
        ArrayList<JRadioButton> radioButtons = new ArrayList<>();
        ButtonGroup group = new ButtonGroup();
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
    
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
    
        for (Course course : courses) {
            JRadioButton rb = new JRadioButton(course.getCourseName());
            radioButtons.add(rb);
            group.add(rb);
            radioPanel.add(rb);
    
            rb.addActionListener(_ -> continueButton.setEnabled(true));
        }
    
        coursePanel.add(radioPanel, BorderLayout.CENTER);
        coursePanel.add(continueButton, BorderLayout.SOUTH);
    
        continueButton.addActionListener(_ -> {
            for (JRadioButton rb : radioButtons) {
                if (rb.isSelected()) {
                    selected = getCourseByName(rb.getText());
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "You selected: " + selected.getCourseName());
        });
    
        this.add(coursePanel);
        return selected;
    }    

    public void initializeCourses() {
        courses = new ArrayList<>();
        courses.add(new Math102());
        courses.add(new Math132());
        courses.add(new Cs102());
        courses.add(new Phys102());
        courses.add(new Ee102());
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
