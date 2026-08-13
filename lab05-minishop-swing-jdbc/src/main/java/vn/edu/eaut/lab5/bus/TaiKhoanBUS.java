package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.TaiKhoanDAL;
import vn.edu.eaut.lab5.model.TaiKhoan;

import java.sql.SQLException;

public class TaiKhoanBUS {
    private final TaiKhoanDAL taiKhoanDAL = new TaiKhoanDAL();

    public TaiKhoan dangNhap(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Vui long nhap ten dang nhap");
        }
        TaiKhoan tk = taiKhoanDAL.timTheoUsername(username.trim());
        if (tk == null || !tk.getPassword().equals(password)) {
            throw new IllegalArgumentException("Sai ten dang nhap hoac mat khau");
        }
        return tk;
    }
}