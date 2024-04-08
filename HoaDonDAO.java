
package com.software.dao;

import com.software.entity.HoaDon;
import com.software.jdbcHelper.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO extends SoftwareDAO<HoaDon, Integer> {

    String INSERT_SQL = "INSERT INTO HDBan (MaHD, MaNV, MaKH, PhanTramGiam, NgayLapHD, TrangThai, GiaGiam, ThanhTien) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE HDBan SET MaNV = ?, MaKH = ?,  PhanTramGiam = ?, NgayLapHD = ?, TrangThai = ?, GiaGiam = ?, ThanhTien = ? WHERE MaHD = ?";
    String DELETE_SQL = "DELETE FROM HDBan WHERE MaHD = ? AND MaHD NOT IN (SELECT MaHD FROM ChiTietHoaDon)";
    String SELECT_ALL_SQL = "select hd.MaHD, hd.MaNV, hd.MaKH, hd.PhanTramGiam, IIF(LEN(CONVERT(nvarchar(2), DAY(hd.NgayLapHD)))=2,CONVERT(nvarchar(2), DAY(hd.NgayLapHD)),'0'+CONVERT(nvarchar(2), DAY(hd.NgayLapHD)))\n" +
"+N'-'+IIF(LEN(CONVERT(nvarchar(2), MONTH(hd.NgayLapHD)))=2,CONVERT(nvarchar(2), MONTH(hd.NgayLapHD)),'0'+CONVERT(nvarchar(2), MONTH(hd.NgayLapHD)))\n" +
"+N'-'+CONVERT(nvarchar(4), YEAR(hd.NgayLapHD)) AS N'NgayLapHD', hd.TrangThai, hd.GiaGiam, hd.ThanhTien from HDBan hd inner join KhachHang kh  on hd.MaKH = kh.MaKH inner join NhanVien nv on hd.MaNV = nv.MaNV";
//    String SELECT_ALL_SQL = "select * from HDBan";
    String SELECT_BY_ID_SQL = "SELECT MaHD, MaNV, MaKH, PhanTramGiam,IIF(LEN(CONVERT(nvarchar(2), DAY(NgayLapHD)))=2,CONVERT(nvarchar(2), DAY(NgayLapHD)),'0'+CONVERT(nvarchar(2), DAY(NgayLapHD)))+N'-'+IIF(LEN(CONVERT(nvarchar(2), MONTH(NgayLapHD)))=2,CONVERT(nvarchar(2), MONTH(NgayLapHD)),'0'+CONVERT(nvarchar(2), MONTH(NgayLapHD)))+N'-'+CONVERT(nvarchar(4), YEAR(NgayLapHD)) AS N'NgayLapHD', TrangThai, GiaGiam, ThanhTien FROM HDBan WHERE MaHD = ?";
    String SELECT_BY_KEYWORD = "SELECT hd.MaHD, hd.MaNV, hd.MaKH, hd.PhanTramGiam, hd.NgayLapHD, hd.TrangThai, hd.GiaGiam, hd.ThanhTien from HDBan hd inner join KhachHang kh  on hd.MaKH = kh.MaKH inner join NhanVien nv on hd.MaNV = nv.MaNV where hd.MaHD LIKE ? OR kh.SoDT LIKE ?";

    @Override
    public void insert(HoaDon entity) {
        try {
            XJdbc.update(INSERT_SQL, entity.getMaHD(),
                    entity.getMaNV(),
                    entity.getMaKH(),
                    entity.getPhanTramGiam(),
                    entity.getNgayLapHD(),
                    entity.getTrangThai(),
                    entity.getGiaGiam(),
                    entity.getThanhTien());
        } catch (SQLException ex) {
            System.out.println("LổiHD"+ex);
        }
    }

    @Override
    public void update(HoaDon entity) {
        try {
            XJdbc.update(UPDATE_SQL, entity.getMaNV(),
                    entity.getMaKH(),
                    entity.getPhanTramGiam(),
                    entity.getNgayLapHD(),
                    entity.getTrangThai(),
                    entity.getGiaGiam(),
                    entity.getThanhTien(),
                    entity.getMaHD());
        } catch (SQLException ex) {
        }
    }

    @Override
    public void delete(Integer ID) {
        try {
            XJdbc.update(DELETE_SQL, ID);
        } catch (SQLException ex) {
        }
    }

    @Override
    public HoaDon SelectByID(Integer ID) {
        List<HoaDon> list = this.SelectBySQL(SELECT_BY_ID_SQL, ID);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> SelectAll() {
        return this.SelectBySQL(SELECT_ALL_SQL);
    }

    @Override
    public List<HoaDon> SelectBySQL(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = XJdbc.query(sql, args);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt("MaHD"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setMaKH(rs.getInt("MaKH"));
                hd.setPhanTramGiam(rs.getInt("PhanTramGiam"));
                hd.setNgayLapHD(rs.getString("NgayLapHD"));
                hd.setTrangThai(rs.getString("TrangThai"));
                hd.setGiaGiam(rs.getDouble("GiaGiam"));
                hd.setThanhTien(rs.getDouble("ThanhTien"));
                list.add(hd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean thanhToan(HoaDon hoaDon) {
        try {
            // Thực hiện các thao tác cần thiết để cập nhật thông tin của hoá đơn sau khi thanh toán
            // Ví dụ:
            hoaDon.setTrangThai("Đã thanh toán");
            
            // Cập nhật thông tin hoá đơn vào cơ sở dữ liệu
            update(hoaDon);
            
            // Trả về true để chỉ ra rằng thanh toán thành công
            return true;
        } catch (Exception ex) {
            // Xử lý các ngoại lệ khi có lỗi xảy ra trong quá trình thực hiện thanh toán
            System.out.println("Lỗi khi thanh toán hoá đơn: " + ex.getMessage());
            return false;
        }
    }
    public List<HoaDon> selectBySDT(String sdt) {
        String SELECT_BY_SDT_SQL = "SELECT hd.MaHD, hd.MaNV, hd.MaKH, hd.PhanTramGiam, hd.NgayLapHD, hd.TrangThai, hd.GiaGiam, hd.ThanhTien " +
                "FROM HDBan hd " +
                "INNER JOIN KhachHang kh ON hd.MaKH = kh.MaKH " +
                "WHERE kh.SoDT = ? AND hd.TrangThai = 'Đã thanh toán'";
        return this.SelectBySQL(SELECT_BY_SDT_SQL, sdt);
    }
    public List<HoaDon> selectByTenKH(String tenKH) {
        String SELECT_BY_TENKH_SQL = "SELECT hd.MaHD, hd.MaNV, hd.MaKH, hd.PhanTramGiam, hd.NgayLapHD, hd.TrangThai, hd.GiaGiam, hd.ThanhTien " +
                "FROM HDBan hd " +
                "INNER JOIN KhachHang kh ON hd.MaKH = kh.MaKH " +
                "WHERE kh.TenKH LIKE ? AND hd.TrangThai = 'Đã thanh toán'";
        return this.SelectBySQL(SELECT_BY_TENKH_SQL, "%" + tenKH + "%");
    }
    public List<HoaDon> selectByMaHDThanhToan(Integer maHD) {
        String SELECT_BY_MAHDTT_SQL = "SELECT hd.MaHD, hd.MaNV, hd.MaKH, hd.PhanTramGiam, hd.NgayLapHD, hd.TrangThai, hd.GiaGiam, hd.ThanhTien " +
                "FROM HDBan hd " +
                "WHERE hd.MaHD = ? AND hd.TrangThai = 'Đã thanh toán'";
        return this.SelectBySQL(SELECT_BY_MAHDTT_SQL, maHD);
    }

    public boolean thanhToanHoaDon(HoaDon hoaDon) {
        try {
            // Thực hiện các thao tác cần thiết để cập nhật thông tin của hoá đơn sau khi thanh toán
            // Ví dụ:
            hoaDon.setTrangThai("Đã thanh toán");
            
            // Cập nhật thông tin hoá đơn vào cơ sở dữ liệu
            update(hoaDon);
            
            // Trả về true để chỉ ra rằng thanh toán thành công
            return true;
        } catch (Exception ex) {
            // Xử lý các ngoại lệ khi có lỗi xảy ra trong quá trình thực hiện thanh toán
            System.out.println("Lỗi khi thanh toán hoá đơn: " + ex.getMessage());
            return false;
        }
    }
    
    public List<HoaDon> selectByKeyWord(String keyword) {
//        String sql = "SELECT * FROM HDBan WHERE MaHDCT LIKE ? OR  LIKE ? OR SoDT LIKE ?";
        return SelectBySQL(SELECT_BY_KEYWORD, "%" + keyword + "%", "%" + keyword + "%");
    }

    public List<HoaDon> SelectByIDS(Integer ID) {
        List<HoaDon> list = this.SelectBySQL(SELECT_BY_ID_SQL, ID);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
}
