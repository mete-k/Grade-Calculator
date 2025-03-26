import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GradeCalculator extends JFrame {
    private ArrayList<Course> courses;
    private Course selected = null;
    private JPanel coursePanel;
    private JPanel calculationPanel;

    public GradeCalculator() {
        this.setSize(400, 300); 
        this.setTitle("Grade Calculator App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        coursePanel = new JPanel();
        calculationPanel = new JPanel();

        initializeCourses();
        courseSelectionMenu();

        this.setVisible(true);
    }

    public Course courseSelectionMenu() {
        coursePanel.setLayout(new BorderLayout());

        // Add the label to the top
        JLabel label = new JLabel("Select a course:", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        coursePanel.add(label, BorderLayout.NORTH);

        // Add the buttons to the center
        ArrayList<JRadioButton> radioButtons = new ArrayList<>();
        ButtonGroup group = new ButtonGroup();
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        coursePanel.add(radioPanel, BorderLayout.CENTER);

        // Add the continue button to the bottom
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        coursePanel.add(continueButton, BorderLayout.SOUTH);
    
        for (Course course : courses) {
            JRadioButton rb = new JRadioButton(course.getCourseName());
            radioButtons.add(rb);
            group.add(rb);
            radioPanel.add(rb);
            rb.addActionListener(_ -> continueButton.setEnabled(true));
        }
    
        continueButton.addActionListener(_ -> {
            for (JRadioButton rb : radioButtons) {
                if (rb.isSelected()) {
                    selected = getCourseByName(rb.getText());
                    break;
                }
            }
            calculationPanel(selected); //open the calculation screen
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

    public void calculationPanel(Course course) {
        removePanel(coursePanel);
    }

    private void removePanel(JPanel panel) {
        this.getContentPane().remove(panel);
        this.revalidate(); 
        this.repaint(); 
        calculationPanel.setLayout(new GridLayout());
    }

    private Course getCourseByName(String name) {
        for (Course c : courses) {
            if (c.getCourseName().equals(name)) return c;
        }
        return null;
    }

    public static void main(String[] args) {
        new GradeCalculator();
    }
}