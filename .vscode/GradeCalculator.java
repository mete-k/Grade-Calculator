import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        resetFrame();
    
        JLabel label = new JLabel("Select a course:", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        this.add(label, BorderLayout.NORTH);
    
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3,2,20,20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
    
        ArrayList<JRadioButton> radioButtons = new ArrayList<>();
        ButtonGroup group = new ButtonGroup();
    
        for (Course course : courses) {
            JPanel cellPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            JRadioButton rb = new JRadioButton(course.courseName);
            rb.setHorizontalAlignment(SwingConstants.CENTER);
            rb.setFont(new Font("Arial", Font.BOLD, 20));
            group.add(rb);
            radioButtons.add(rb);
            cellPanel.add(rb);
            centerPanel.add(cellPanel);
        }
    
        this.add(centerPanel, BorderLayout.CENTER);
    
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        continueButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 17));
        continueButton.setPreferredSize(new Dimension(120, 40));

        JButton userButton = new JButton("Saved");
        userButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 17));
        userButton.setPreferredSize(new Dimension(120, 40));

        bottomPanel.add(continueButton);
        bottomPanel.add(userButton);
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
            int[] arr = new int[selected.length];
            calculationPanel(selected,arr); 
        });

        userButton.addActionListener(_ -> {
            String studentName = JOptionPane.showInputDialog(null, "Enter your name:");
            getGrades(studentName);
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
        courses.add(new Econ108());
    }

    public void calculationPanel(Course course, int[] arr) {
        resetFrame();
    
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
    
            setGradeAreas(arr[i], i, fieldsPanel, textFields);
        }
        centerPanel.add(fieldsPanel);
    
        JPanel calcButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        JButton calcButton = new JButton("Calculate");
        calcButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 17));
        calcButton.setPreferredSize(new Dimension(200, 50)); 
        calcButton.addActionListener(_ -> {
            for (int i = 0; i < course.length; i ++) {
                if (isDouble(textFields[i].getText()))grades[i] = Math.min(100, Double.parseDouble(textFields[i].getText()));
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
            String studentName = JOptionPane.showInputDialog(null, "Enter your name:");
            if (studentName == null || studentName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid name.");
                return;
            }
            
            int[] intGrades = new int[course.length];
            for (int i = 0; i < course.length; i++) {
                try {
                    intGrades[i] = (int) Double.parseDouble(textFields[i].getText());
                } catch (NumberFormatException ex) {
                    intGrades[i] = 0; 
                }
            }
            
            boolean saved = saveToFile(course, studentName, intGrades);
            
            if (saved) {
                JOptionPane.showMessageDialog(null, "Grades saved successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Error saving grades.");
            }
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

    private boolean saveToFile(Course course, String name, int[] grades) {

        File file = course.file;
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!lines.isEmpty() && lines.get(lines.size() - 1).equals("end")) {
            lines.remove(lines.size() - 1);
        }

        ArrayList<String> newLines = new ArrayList<>();
        boolean userFound = false;
        for (int i = 0; i < lines.size(); ) {
            String currentName = lines.get(i).trim();
            if (currentName.equals(name)) {
                userFound = true;
                newLines.add(name);
                newLines.add(arrayToString(grades));
                i += 2; 
            } else {
                newLines.add(currentName);
                if (i + 1 < lines.size()) {
                    newLines.add(lines.get(i + 1));
                }
                i += 2;
            }
        }

        if (!userFound) {
            if (!newLines.isEmpty()) {
                newLines.add("");
            }
            newLines.add(name);
            newLines.add(arrayToString(grades));
        }

        newLines.add("end");
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String l : newLines) {
                pw.println(l);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
            
        return true;
    }

    private String arrayToString(int[] arr) {
        String s = "";
        for (int i = 0; i < arr.length; i++) {
            s += arr[i];
            if (i < arr.length - 1) {
                s += " ";
            }
        }
        return s;
    }

    private void getGrades(String name) {
        ArrayList<Course> userCourses = new ArrayList<>();
        if (!isNameInAnyCourse(name, userCourses)) JOptionPane.showMessageDialog(null,"The name " + name + " is not saved in any courses.", "Error",JOptionPane.ERROR_MESSAGE);
        int size = userCourses.size();
        displayUserGrades(name);
    }

    private boolean isNameInAnyCourse(String name, ArrayList<Course> userCourses) {
        for (Course course : courses) {
            try (BufferedReader reader = new BufferedReader(new FileReader(course.file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().equalsIgnoreCase(name)) {
                        userCourses.add(course);
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void displayUserGrades(String userName) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    
        for (Course course : courses) {
            try (BufferedReader reader = new BufferedReader(new FileReader(course.file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().equalsIgnoreCase(userName)) {
                        String gradeLine = reader.readLine();
                        int[] arr = parseGrades(gradeLine);
                        JPanel coursePanel = new JPanel(new GridLayout(1,3,10,20));
                        JLabel courseNameLabel = new JLabel(course.courseName, SwingConstants.CENTER);
                        JLabel gradesLabel = new JLabel("Grades: " + gradeLine, SwingConstants.CENTER);
                        JButton editButton = new JButton("Edit");
                        editButton.addActionListener(e -> {
                            calculationPanel(course,arr);
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mainPanel);
                            if (frame != null) {
                                frame.dispose();
                            }
                        });
                        coursePanel.add(courseNameLabel);
                        coursePanel.add(gradesLabel);
                        coursePanel.add(editButton);
                        mainPanel.add(coursePanel);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        JFrame frame = new JFrame("User Grades");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new JScrollPane(mainPanel));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }    

    private void resetFrame()  {
        this.getContentPane().removeAll();
        this.setLayout(new BorderLayout());
    }

    private void setGradeAreas(int grade, int index, JPanel panel, JTextField[] textFields) {
        JTextField field = new JTextField();
        if (grade != 0) field.setText(Integer.toString(grade));
        textFields[index] = field;
        panel.add(field);
    }

    private int[] parseGrades(String gradeLine) {
        String[] parts = gradeLine.trim().split("\\s+");
        int[] grades = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            int value = Integer.parseInt(parts[i]);
            grades[i] = Math.min(value, 100);
        }
        return grades;
    }
    
    public static void main(String[] args) {
        new GradeCalculator();
    }
}