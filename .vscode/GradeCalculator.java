import java.awt.*;
import java.text.DecimalFormat;
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
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            JRadioButton rb = new JRadioButton(course.courseName);
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
    
        JLabel title = new JLabel(course.courseName, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));
        this.add(title, BorderLayout.NORTH);
    
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); 
    
        double[] grades = new double[course.length];
        JTextField[] textFields = new JTextField[course.length];
        JPanel fieldsPanel = new JPanel(new GridLayout(course.length, 2, 10, 10));
        for (int i = 0; i < course.length; i++) {
            JLabel label = new JLabel(course.exams[i], SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 20));
            fieldsPanel.add(label);
    
            JTextField field = new JTextField();
            textFields[i] = field;
            fieldsPanel.add(field);
        }
        centerPanel.add(fieldsPanel);
    
        JPanel calcButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        JButton calcButton = new JButton("Calculate");
        calcButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 17));
        calcButton.setPreferredSize(new Dimension(200, 50)); 
        calcButton.addActionListener(_ -> {
            for (int i = 0; i < course.length; i ++) {
                if (isDouble(textFields[i].getText()))grades[i] = Math.min(100,Double.parseDouble(textFields[i].getText()));
                else grades[i] = 0.0;
            }
            course.notes = grades;
            double average = course.calculateAverage(grades);
            if (grades[course.length - 1] == 0) neededGradeTable(course, average);
            else gradeMessage(course, average);
            
        });
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
            if (c.courseName.equals(name)) return c;
        }
        return null;
    }

    private boolean isDouble(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void neededGradeTable(Course course, double average) {
        JFrame frame = new JFrame();
        frame.setSize(300, 400); 
        frame.setTitle("Letter Grade Table");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
    
        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        JLabel lbl1 = new JLabel("Letter Grade", SwingConstants.CENTER);
        if (!course.isCatalog) lbl1.setText("Average");
        JLabel lbl2 = new JLabel("Needed Final", SwingConstants.CENTER);
        titlePanel.add(lbl1);
        titlePanel.add(lbl2);
    
        int iterations = Math.min(course.letterGrades.length - 1, course.catalogNotes.length);
        int validCount = 0;
        for (int i = 0; i < iterations; i++) {
            double neededGrade = Math.ceil(course.calculateRequiredFinal(course.catalogNotes[i]));
            if (neededGrade >= 0 && neededGrade <= 100) {
                validCount++;
            }
        }
    
        JPanel gradesPanel = new JPanel(new GridLayout(validCount, 2));
    
        for (int i = 0; i < iterations; i++) {
            double neededGrade = Math.ceil(course.calculateRequiredFinal(course.catalogNotes[i]));
            if (neededGrade >= 0 && neededGrade <= 100) {
                if (course.isCatalog) gradesPanel.add(new JLabel(course.letterGrades[i], SwingConstants.CENTER));
                else gradesPanel.add(new JLabel(Double.toString(course.catalogNotes[i]), SwingConstants.CENTER));
                gradesPanel.add(new JLabel(String.format("%.2f", neededGrade), SwingConstants.CENTER));
            }
        }
    
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(gradesPanel, BorderLayout.CENTER);
    
        frame.add(mainPanel);
        frame.setVisible(true);
    }
    

    private void gradeMessage (Course course, double average) {
        DecimalFormat df = new DecimalFormat("#.00");
        String avg = df.format(average);
        if (course.isCatalog)JOptionPane.showMessageDialog(null, "Your average is " + avg + "\n Letter Grade: " + course.getLetterGrade(average), "Grade", JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(null, "Your average is " + avg, "Grade", JOptionPane.INFORMATION_MESSAGE);
    }

    /*private boolean saveToFile(Course course, String name, int[] grades) {
        will be implemented later.
        /* create a txt file for each course (done previously) 
         * when the user saves their grades, add their name and grades to this file.
         * the users grades should start with the users name and end with another users name.
         * to handle the user's name being the last in the file, add a string to the end like "end" or sth idk.
         * save the grades to an array then add them to this file. 
         * ust sync the grades array to the txt file if there are any changes. youre going to sync any way no need to waste time iterating
         * 
    }*/

    public static void main(String[] args) {
        new GradeCalculator();
    }
}