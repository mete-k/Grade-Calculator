public class Math102 extends Course {

    public Math102() {
        super("MATH 102");
        this.isCatalog = true;
        this.notes = new double[5];
        this.exams = new String[] {"Midterm 1", "Midterm 2", "Homework", "Quiz", "Final"};
        this.weights = new double[] {80/3, 80/3, 10, 10, 80/3}; 
        this.catalogNotes = new int[] {80, 75, 70, 65, 60, 55, 50, 45, 40, 30};
    }
}

class Math132 extends Course {
    public Math132() {
        super("MATH 132");
        this.isCatalog = false;
        this.exams = new String[]{"Midterm 1", "Midterm 2", "Final"};
        this.notes = new double[3];
        this.weights = new double[] {33, 33, 34};
    }
}

class Cs102 extends Course {
    public Cs102() {
        super("CS 102");
        this.isCatalog = false;
        this.exams = new String[]{"Midterm", "Lab", "Project", "Homework and Quiz", "Final"};
        this.notes = new double[5];
        this.weights = new double[] {25, 15, 25, 10, 25};
    }
}

class Phys102 extends Course {
    public Phys102() {
        super("PHYS 102");
        this.isCatalog = true;
        this.exams = new String[]{"Midterm 1", "Midterm 2", "Lab", "Homework", "Quiz", "Final"};
        this.notes = new double[6];
        this.weights = new double[] {20, 20, 20, 10, 10, 20};
        this.catalogNotes = new int[] {75, 70, 65, 60, 55, 50, 45, 40};
    }
}

class Ee102 extends Course {
    public Ee102() {
        super("EE 102");
        this.isCatalog = false;
        this.exams = new String[]{"Midterm", "Lab", "Project", "Attendance", "Final"};
        this.notes = new double[5];
        this.weights = new double[] {30, 14, 16, 5, 35};
    }
}