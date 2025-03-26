public class Math102 extends Course {
    private String[] exams = {"Midterm 1", "Midterm 2", "Homework", "Quiz", "Final"};

    public Math102() {
        super("Math 102");
        this.notes = new double[5];
        this.weights = new double[] {80/3, 80/3, 10, 10, 80/3}; 
        this.letterGradeNotes = new int[] {80, 75, 70, 65, 60, 55, 50, 45, 40, 30};
    }

    public double calculateAverage(double[] notes) {
        double average = 0;
        for (int i = 0; i < notes.length; i++) {
            average += notes[i] * this.weights[i] / 100;
        }
        return average;
    }

    public double calculateRequiredFinal(double wantedNote) {
        double[] tempNotes = notes.clone();
        tempNotes[4] = 0; 
        double noteBeforeFinal = calculateAverage(tempNotes);
        return (wantedNote - noteBeforeFinal) * 100 / weights[4];
    }

    public String[] getExams() {
        return this.exams;
    }
}
