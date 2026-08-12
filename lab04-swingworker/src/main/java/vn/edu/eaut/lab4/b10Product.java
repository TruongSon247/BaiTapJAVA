package vn.edu.eaut.lab4;

public class b10Product {
    private String maSp;
    private String tenSp;
    private double donGia;

    public b10Product(String maSp, String tenSp, double donGia) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.donGia = donGia;
    }

    public String getMaSp() { return maSp; }
    public String getTenSp() { return tenSp; }
    public double getDonGia() { return donGia; }

    public void setTenSp(String tenSp) { this.tenSp = tenSp; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
}