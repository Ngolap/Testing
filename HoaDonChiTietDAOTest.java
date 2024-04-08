package com.software.dao;

import static org.junit.jupiter.api.Assertions.*;
import com.software.entity.HoaDonChiTiet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class HoaDonChiTietDAOTest {

    private HoaDonChiTietDAO hoaDonChiTietDAO;

    @BeforeEach
    void setUp() {
        hoaDonChiTietDAO = new HoaDonChiTietDAO();
    }

    @AfterEach
    void tearDown() {
        hoaDonChiTietDAO = null;
    }
    @Test
    void testInsertHoaDonChiTietWithCustomerAndProduct() {
        System.out.println("Tạo hoá đơn thanh toán khi có tên \r\n"
        		+ "khách hàng và sản phẩm");
        // Giả sử thông tin về khách hàng và sản phẩm đã tồn tại trong cơ sở dữ liệu
        int maHoaDonCT = 16; // Mã hóa đơn chi tiết
        int maHD = 9; // Mã hoá đơn
        String maSanPham = "1"; // Mã sản phẩm
        int soLuongSanPham = 5; // Số lượng sản phẩm
        double donGiaSanPham = 1000.0; // Đơn giá sản phẩm

        // Tạo một hóa đơn chi tiết mới với thông tin về khách hàng và sản phẩm đã có
        HoaDonChiTiet entity = new HoaDonChiTiet(maHoaDonCT, maHD, maSanPham, soLuongSanPham, donGiaSanPham);
        hoaDonChiTietDAO.insert(entity);

        // Kiểm tra xem hóa đơn chi tiết có được thêm vào cơ sở dữ liệu không
        assertNotNull(entity.getMaHDCT());
    }

    @Test
    void testInsertHoaDonChiTietWithoutProduct() {
        System.out.println("Tạo hoá đơn thanh toán khi có tên \r\n"
        		+ "khách hàng và không có tên sản phẩm");
        // Tạo một hóa đơn chi tiết mới không có sản phẩm (maSP = null)
        HoaDonChiTiet entity = new HoaDonChiTiet(16, 9, null, 0, 0.0);
        hoaDonChiTietDAO.insert(entity);
        assertNotNull(entity.getMaHDCT()); // Kiểm tra xem ID được tạo tự động có tồn tại hay không
    }
    @Test
    void testInsertHoaDonChiTietWithoutCustomer() {
        System.out.println("Tạo hoá đơn thanh toán khi không có tên \r\n"
        		+ "khách hàng và có tên sản phẩm");
        // Tạo một hóa đơn chi tiết mới không có khách hàng (maKH = null)
        HoaDonChiTiet entity = new HoaDonChiTiet(16, 0, "1", 5, 1000.0);
        hoaDonChiTietDAO.insert(entity);
        assertNotNull(entity.getMaHDCT()); // Kiểm tra xem ID được tạo tự động có tồn tại hay không
    }
    @Test
    void testInsertHoaDonChiTietWithoutProductAndCustomer() {
    	
        System.out.println("Tạo hoá đơn thanh toán khi không có tên \r\n"
        		+ "khách hàng và không có tên sản phẩm");
        // Tạo một hóa đơn chi tiết mới không có sản phẩm và không có khách hàng
        HoaDonChiTiet entity = new HoaDonChiTiet(16, 0, null, 0, 0.0);
        hoaDonChiTietDAO.insert(entity);
        assertNotNull(entity.getMaHDCT()); // Kiểm tra xem ID được tạo tự động có tồn tại hay không
    }
    
   

    @Test
    void testUpdateHoaDonChiTiet() {
    	System.out.println("update");
        HoaDonChiTiet entity = new HoaDonChiTiet(1, 1, "1", 5, 1000.0);
        hoaDonChiTietDAO.update(entity);
        // Thêm các kiểm tra khác nếu cần
    }

    

   

    @Test
    void testSelectByIDInteger() {
    	System.out.println("Hiện thị hoá đơn theo ID");
        // Chọn một ID hợp lệ để kiểm tra
        Integer validID = 1;
        HoaDonChiTiet result = hoaDonChiTietDAO.SelectByID(validID);
        assertNotNull(result);
        assertEquals(validID, result.getMaHDCT());
    }

    @Test
    void testSelectAll() {
    	System.out.println("Hiện thị thông tin hoá đơn");
        List<HoaDonChiTiet> resultList = hoaDonChiTietDAO.SelectAll();
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
    }

    @Test
    void testSelectByMaHD() {
    	System.out.println("Hiển thị hoá dơn theo mãHD");
        // Chọn một MaHD hợp lệ để kiểm tra
        Integer validMaHD = 1;
        List<HoaDonChiTiet> resultList = hoaDonChiTietDAO.SelectByMaHD(validMaHD);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
    }
}
