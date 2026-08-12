package vn.edu.eaut.lab3;

public class Student {
    private String studentId;
    private String fullName;
    private double diemTrungBinh;

    public Student(String studentId, String fullName, double diemTrungBinh) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.diemTrungBinh = diemTrungBinh;
    }

    public String getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    public double getDiemTrungBinh() { return diemTrungBinh; }

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setDiemTrungBinh(double diemTrungBinh) { this.diemTrungBinh = diemTrungBinh; }

    public String xepLoai() {
        if (diemTrungBinh >= 8.5) return "Giỏi";
        if (diemTrungBinh >= 7.0) return "Khá";
        if (diemTrungBinh >= 5.0) return "Trung bình";
        return "Yếu";
    }
}