package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.DanhMuc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAL {

    public List<DanhMuc> findAll() throws SQLException {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT ma_dm, ten_dm FROM danh_muc";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new DanhMuc(rs.getInt("ma_dm"), rs.getString("ten_dm")));
            }
        }
        return list;
    }

    public boolean insert(DanhMuc dm) throws SQLException {
        String sql = "INSERT INTO danh_muc(ten_dm) VALUES (?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dm.getTenDm());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(DanhMuc dm) throws SQLException {
        String sql = "UPDATE danh_muc SET ten_dm = ? WHERE ma_dm = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dm.getTenDm());
            ps.setInt(2, dm.getMaDm());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int maDm) throws SQLException {
        String sql = "DELETE FROM danh_muc WHERE ma_dm = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDm);
            return ps.executeUpdate() > 0;
        }
    }

    // Dem xem con san pham nao dang thuoc danh muc nay khong
    public int demSanPhamTrongDanhMuc(int maDm) throws SQLException {
        String sql = "SELECT COUNT(*) FROM san_pham WHERE ma_dm = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDm);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }
}