package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.DanhMucDAL;
import vn.edu.eaut.lab5.model.DanhMuc;

import java.sql.SQLException;
import java.util.List;

public class DanhMucBUS {
    private final DanhMucDAL danhMucDAL = new DanhMucDAL();

    public List<DanhMuc> findAll() throws SQLException {
        return danhMucDAL.findAll();
    }

    public boolean save(DanhMuc dm) throws SQLException {
        if (dm.getTenDm() == null || dm.getTenDm().trim().isEmpty()) {
            throw new IllegalArgumentException("Ten danh muc khong duoc rong");
        }
        if (dm.getMaDm() == 0) {
            return danhMucDAL.insert(dm);
        }
        return danhMucDAL.update(dm);
    }

    public boolean delete(int maDm) throws SQLException {
        int soLuongSp = danhMucDAL.demSanPhamTrongDanhMuc(maDm);
        if (soLuongSp > 0) {
            throw new IllegalArgumentException(
                "Khong the xoa: con " + soLuongSp + " san pham thuoc danh muc nay");
        }
        return danhMucDAL.delete(maDm);
    }
}