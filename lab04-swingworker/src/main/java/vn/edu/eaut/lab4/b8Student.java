package vn.edu.eaut.lab4;

public class b8Student {
    private String studentId;
    private String fullName;
    private double diem;

    public b8Student(String studentId, String fullName, double diem) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.diem = diem;
    }

    public String getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    public double getDiem() { return diem; }
}