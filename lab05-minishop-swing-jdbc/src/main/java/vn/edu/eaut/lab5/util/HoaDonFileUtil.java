package vn.edu.eaut.lab5.util;

import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.KhachHang;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class HoaDonFileUtil {

    public static void xuatFileTxt(String duongDan, int maHd, LocalDate ngayLap, KhachHang kh,
                                    List<ChiTietHoaDon> chiTietList, BigDecimal tongTien) throws IOException {
        try (FileWriter writer = new FileWriter(duongDan)) {
            writer.write("HOA DON BAN HANG\n");
            writer.write("================\n");
            writer.write("Ma hoa don: " + maHd + "\n");
            writer.write("Ngay lap: " + ngayLap + "\n");
            writer.write("Khach hang: " + kh.getTenKh() + "\n");
            writer.write("So dien thoai: " + kh.getSdt() + "\n");
            writer.write("----------------\n");
            writer.write(String.format("%-25s %8s %12s %14s%n", "Ten san pham", "SL", "Don gia", "Thanh tien"));
            for (ChiTietHoaDon ct : chiTietList) {
                writer.write(String.format("%-25s %8d %12s %14s%n",
                        ct.getTenSp(), ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien()));
            }
            writer.write("----------------\n");
            writer.write("TONG TIEN: " + tongTien + " VND\n");
        }
    }
}