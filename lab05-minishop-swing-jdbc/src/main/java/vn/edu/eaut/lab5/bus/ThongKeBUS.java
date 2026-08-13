package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ThongKeDAL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThongKeBUS {
    private final ThongKeDAL thongKeDAL = new ThongKeDAL();

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        if (tuNgay.isAfter(denNgay)) {
            throw new IllegalArgumentException("Tu ngay phai truoc hoac bang den ngay");
        }
        return thongKeDAL.tinhDoanhThu(tuNgay, denNgay);
    }

    public String hoaDonCaoNhat() throws SQLException {
        return thongKeDAL.hoaDonCaoNhat();
    }

    public String sanPhamBanChayNhat() throws SQLException {
        return thongKeDAL.sanPhamBanChayNhat();
    }
}