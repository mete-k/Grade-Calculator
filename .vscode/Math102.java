public class Math102 extends Course {

    public Math102() {
        super("MATH 102");
        this.notes = new double[5];
        this.exams = new String[] {"Midterm 1", "Midterm 2", "Homework", "Quiz", "Final"};
        this.weights = new double[] {80/3, 80/3, 10, 10, 80/3}; 
        this.letterGradeNotes = new int[] {80, 75, 70, 65, 60, 55, 50, 45, 40, 30};
    }
}

class Math132 extends Course {
    public Math132() {
        super("MATH 132");
        this.exams = new String[]{"Midterm 1", "Midterm 2", "Final"};
        this.notes = new double[3];
        this.weights = new double[] {33, 33, 34};
    }
}

class Cs102 extends Course {
    public Cs102() {
        super("CS 102");
        this.exams = new String[]{"Midterm", "Lab", "Project", "Homework and Quiz", "Final"};
        this.notes = new double[5];
        this.weights = new double[] {25, 15, 25, 10, 25};
    }
}

class Phys102 extends Course {
    public Phys102() {
        super("PHYS 102");
        this.exams = new String[]{"Midterm", "Lab", "Project", "Homework and Quiz", "Final"};
        this.notes = new double[5];
        this.weights = new double[] {25, 15, 25, 10, 25};
    }
}

class Ee102 extends Course {
    public Ee102() {
        super("EE 102");
        this.exams = new String[]{"Midterm", "Lab", "Project", "Homework and Quiz", "Final"};
        this.notes = new double[5];
        this.weights = new double[] {25, 15, 25, 10, 25};
    }
}