package vn.edu.eaut.lab5.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HoaDon {
    private int maHd;
    private LocalDate ngayLap;
    private int maKh;
    private BigDecimal tongTien;

    public HoaDon() {}

    public int getMaHd() { return maHd; }
    public void setMaHd(int maHd) { this.maHd = maHd; }

    public LocalDate getNgayLap() { return ngayLap; }
    public void setNgayLap(LocalDate ngayLap) { this.ngayLap = ngayLap; }

    public int getMaKh() { return maKh; }
    public void setMaKh(int maKh) { this.maKh = maKh; }

    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
}