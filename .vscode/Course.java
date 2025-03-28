import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Course {
    public String[] letterGrades = {"A ", "A-", "B+", "B ", "C+", "C ", "C-", "D+", "D ", "F "};
    protected String courseName;
    protected double[] notes;
    protected double[] weights;
    protected String[] exams;
    protected boolean isCatalog;
    protected int[] catalogNotes;
    protected int length;
    protected int startFrom;
    protected int endAt;
    protected File file;

    public Course(String courseName) {
        this.courseName = courseName;
        this.file = new File(this.courseName);
        createFile(file);
    }  

    public double calculateAverage(double[] notes) {
        double average = 0;
        for (int i = 0; i < notes.length; i++) {
            average += notes[i] * this.weights[i] / 100;
        }
        return average;
    }

    public double calculateRequiredFinal(double wantedNote) {
        double noteBeforeFinal = calculateAverage(notes); 
        return (wantedNote - noteBeforeFinal) * 100 / this.weights[this.length - 1];
    }      

    public String getLetterGrade(double average) {
        if (!this.isCatalog) return null;
        for (int i = 0; i < catalogNotes.length; i++) {
            if (average >= catalogNotes[i]) {
                return letterGrades[i];
            }
        }
        return letterGrades[letterGrades.length - 1];
    }

    protected int[] createCatalog(Course course, int i) {
        int start = course.startFrom;
        int end = course.endAt;
        ArrayList<Integer> catalog = new ArrayList<>();
        while (start > end) {
            catalog.add(start);
            start -= i; 
        }
        return ArrayListToArray(catalog);
    }

    private int[] ArrayListToArray(ArrayList<Integer> a1) {
        int[] returnArr = new int[a1.size()];
        for (int i = 0; i < a1.size(); i++ ) {
            returnArr[i] = a1.get(i);
        }
        return returnArr;
    }

    private void createFile(File file) {
        try {
            if (file.createNewFile()) System.out.println("File created: " + file.getName());    
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
class Math102 extends Course {

    public Math102() {
        super("MATH 102");
        this.isCatalog = true;
        this.notes = new double[5];
        this.exams = new String[] {"Midterm 1", "Midterm 2", "Homework", "Quiz", "Final"};
        this.weights = new double[] {80/3.0, 80/3.0, 10, 10, 80/3.0}; 
        this.catalogNotes = new int[] {80, 75, 70, 65, 60, 55, 50, 45, 40, 30};
        this.length = 5;
    }
}

class Math132 extends Course {
    public Math132() {
        super("MATH 132");
        this.isCatalog = false;
        this.exams = new String[]{"Midterm 1", "Midterm 2", "Final"};
        this.notes = new double[3];
        this.weights = new double[] {33, 33, 34};
        this.length = 3;
        this.startFrom = 80;
        this.endAt = 40;
        this.catalogNotes = createCatalog(this,5);
    }
}

class Cs102 extends Course {
    public Cs102() {
        super("CS 102");
        this.isCatalog = false;
        this.exams = new String[]{"Midterm", "Lab", "Project", "Homework and Quiz", "Final"};
        this.notes = new double[5];
        this.weights = new double[] {25, 15, 25, 10, 25};
        this.length = 5;
        this.startFrom = 90;
        this.endAt = 30;
        this.catalogNotes = createCatalog(this,5); 
    }
}

class Phys102 extends Course {
    public Phys102() {
        super("PHYS 102");
        this.isCatalog = true;
        this.exams = new String[]{"Midterm 1", "Midterm 2", "Lab", "Homework", "Quiz", "Final"};
        this.notes = new double[6];
        this.weights = new double[] {20, 20, 20, 10, 10, 20};
        this.catalogNotes = new int[] {85, 80, 75, 70, 65, 60, 55, 50, 45, 40};
        this.length = 6;
    }
}

class Ee102 extends Course {
    public Ee102() {
        super("EE 102");
        this.isCatalog = false;
        this.exams = new String[]{"Midterm", "Lab", "Project", "Attendance", "Final"};
        this.notes = new double[5];
        this.weights = new double[] {30, 14, 16, 5, 35};
        this.length = 5;
        this.startFrom = 85;
        this.endAt = 40;
        this.catalogNotes = createCatalog(this,5); 
    }
}

class Econ108 extends Course {
    public Econ108() {
        super("ECON 108");
        this.isCatalog = false;
        this.exams = new String[]{"1st Midterm", "2nd Midterm", "Final", "Quiz"};
        this.notes = new double[4];
        this.weights = new double[] {25, 25, 30, 20};
        this.length = 4;
        this.startFrom = 85;
        this.endAt = 40;
        this.catalogNotes = createCatalog(this,5); 
    }
}