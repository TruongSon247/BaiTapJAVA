package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.TaiKhoan;

import java.sql.*;

public class TaiKhoanDAL {

    public TaiKhoan timTheoUsername(String username) throws SQLException {
        String sql = "SELECT username, password, ho_ten, vai_tro FROM tai_khoan WHERE username = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TaiKhoan tk = new TaiKhoan();
                    tk.setUsername(rs.getString("username"));
                    tk.setPassword(rs.getString("password"));
                    tk.setHoTen(rs.getString("ho_ten"));
                    tk.setVaiTro(rs.getString("vai_tro"));
                    return tk;
                }
                return null;
            }
        }
    }
}