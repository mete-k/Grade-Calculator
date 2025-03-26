import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GradeCalculator extends JFrame {
    private ArrayList<Course> courses;
    private Course selected = null;
    private JPanel coursePanel;
    private JPanel calculationPanel;
    private ArrayList<String> users;

    public GradeCalculator() {
        this.setSize(800, 600); 
        this.setTitle("Grade Calculator App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.users = new ArrayList<>();
        this.coursePanel = new JPanel();
        this.calculationPanel = new JPanel();

        initializeCourses();
        courseSelectionMenu();

        this.setVisible(true);
    }

    public Course courseSelectionMenu() {
        this.getContentPane().removeAll();
        this.setLayout(new BorderLayout());
    
        JLabel label = new JLabel("Select a course:", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        this.add(label, BorderLayout.NORTH);
    
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
    
        ArrayList<JRadioButton> radioButtons = new ArrayList<>();
        ButtonGroup group = new ButtonGroup();
    
        for (Course course : courses) {
            JRadioButton rb = new JRadioButton(course.getCourseName());
            rb.setFont(new Font("Arial", Font.BOLD, 20));
            group.add(rb);
            radioButtons.add(rb);
            centerPanel.add(rb);
            centerPanel.add(Box.createVerticalStrut(10));
        }
    
        this.add(centerPanel, BorderLayout.CENTER);
    
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        continueButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 17));
        continueButton.setPreferredSize(new Dimension(120, 40));
        bottomPanel.add(continueButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    
        for (JRadioButton rb : radioButtons) {
            rb.addActionListener(_ -> continueButton.setEnabled(true));
        }
    
        continueButton.addActionListener(_ -> {
            for (JRadioButton rb : radioButtons) {
                if (rb.isSelected()) {
                    selected = getCourseByName(rb.getText());
                    break;
                }
            }
            calculationPanel(selected); 
        });
    
        this.revalidate();
        this.repaint();
    
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
        this.getContentPane().removeAll();
        this.setLayout(new BorderLayout());
    
        JLabel title = new JLabel(course.getCourseName(), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));
        this.add(title, BorderLayout.NORTH);
    
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // margins
    
        JPanel fieldsPanel = new JPanel(new GridLayout(course.getExams().length, 2, 10, 10));
        for (int i = 0; i < course.getExams().length; i++) {
            JLabel label = new JLabel(course.getExams()[i], SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 20));
            fieldsPanel.add(label);
    
            JTextField field = new JTextField();
            fieldsPanel.add(field);
        }
        centerPanel.add(fieldsPanel);
    
        JPanel calcButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        JButton calcButton = new JButton("Calculate");
        calcButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 17));
        calcButton.setPreferredSize(new Dimension(200, 50)); 
        calcButtonPanel.add(calcButton);
        centerPanel.add(calcButtonPanel);
    
        this.add(centerPanel, BorderLayout.CENTER);
    
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    
        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 17));
        returnButton.setPreferredSize(new Dimension(120, 40));
        returnButton.addActionListener(_ -> {
            courseSelectionMenu();
            this.revalidate();
            this.repaint();
        });
        bottomPanel.add(returnButton);
    
        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 17));
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.addActionListener(_ -> {
            System.out.println("TO DO");
        });
        bottomPanel.add(saveButton);
    
        this.add(bottomPanel, BorderLayout.SOUTH);
        
        this.revalidate();
        this.repaint();
    }

    private Course getCourseByName(String name) {
        for (Course c : courses) {
            if (c.getCourseName().equals(name)) return c;
        }
        return null;
    }

    /*private boolean saveToFile(String username, int[] grades) {
        will be implemented later.
    }*/

    public static void main(String[] args) {
        new GradeCalculator();
    }
}