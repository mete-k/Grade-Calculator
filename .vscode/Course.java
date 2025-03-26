public abstract class Course {
    public static String[] letterGrades = {"A ", "A-", "B+", "B ", "C+", "C ", "C-", "D+", "D ", "F "};
    protected String courseName;
    protected int[] letterGradeNotes;
    protected double[] notes;
    protected double[] weights;

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public abstract double calculateAverage(double[] notes);
    public abstract double calculateRequiredFinal(double wantedNote);

    public String getCourseName() {
        return this.courseName;
    }
    
    public double[] getNotes() {
        return this.notes;
    }

    public double[] getWeights() {
        return this.weights;
    }
}
