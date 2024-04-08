package com.software.dao;

import com.software.entity.HoaDon;


import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HoaDonDAOTest {

    private static HoaDonDAO hoaDonDAO;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        hoaDonDAO = new HoaDonDAO();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        hoaDonDAO = null;
    }

    @BeforeEach
    void setUp() throws Exception {
        // Chuẩn bị dữ liệu trước mỗi test nếu cần
    }

    @AfterEach
    void tearDown() throws Exception {
        // Dọn dẹp dữ liệu sau mỗi test nếu cần
    }

    @Test
    void insertTest() {
        HoaDon entity = new HoaDon(1, "3", 1, 10, 1000.0, "2024-04-08", "Đã thanh toán", 900.0);
        hoaDonDAO.insert(entity);
        assertNotNull(entity.getMaHD());
    }

    @Test
    void updateTest() {
        HoaDon entity = new HoaDon(1, "3", 1, 10, 1000.0, "2024-04-08", "Đã thanh toán", 900.0);
        hoaDonDAO.update(entity);
        // Kiểm tra logic cập nhật nếu cần
    }

    @Test
    void deleteTest() {
        // Kiểm tra trước khi xóa để đảm bảo dữ liệu tồn tại
        Integer idToDelete = 1;
        HoaDon existingHoaDon = hoaDonDAO.SelectByID(idToDelete);
        assertNotNull(existingHoaDon, "HoaDon không tồn tại trước khi xóa");
        // Thực hiện xóa
        hoaDonDAO.delete(idToDelete);

    }


    @Test
    void SelectByIDTest() {
        Integer validID = 1;
        HoaDon result = hoaDonDAO.SelectByID(validID);
        assertNotNull(result);
        assertEquals(validID, result.getMaHD());
    }

    @Test
    void SelectAllTest() {
        List<HoaDon> list = hoaDonDAO.SelectAll();
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }
    
    @Test
    void testThanhToanThanhCong() {
        // Tạo một hoá đơn mới để kiểm tra thanh toán
        HoaDon hoaDon = new HoaDon(1, "3", 1, 10, 1000.0, "2024-04-08", "Chưa thanh toán", 900.0);
        
        // Thực hiện thanh toán cho hoá đơn
        boolean result = hoaDonDAO.thanhToan(hoaDon);
        
        // Kiểm tra kết quả
        assertTrue(result, "Thanh toán không thành công");
        
        // Kiểm tra xem hoá đơn đã được cập nhật thành công chưa
        HoaDon updatedHoaDon = hoaDonDAO.SelectByID(1);
        assertNotNull(updatedHoaDon, "Không thể tìm thấy hoá đơn đã được cập nhật");
        assertEquals("Đã thanh toán", updatedHoaDon.getTrangThai(), "Trạng thái của hoá đơn sau khi thanh toán không đúng");
    }
    @Test
    void testThanhToanHoaDonChiCoKhachHang() {
        // Tạo một hoá đơn mới chỉ có thông tin khách hàng
        HoaDon hoaDon = new HoaDon(1, "3", 1, 10, 1000.0, "2024-04-08", "Chưa thanh toán", 900.0);
        
        // Thực hiện thanh toán cho hoá đơn chỉ có thông tin khách hàng
        boolean result = hoaDonDAO.thanhToanHoaDon(hoaDon);
        
        // Kiểm tra kết quả
        assertTrue(result, "Thanh toán hoá đơn chỉ có thông tin khách hàng không thành công");
        
        // Kiểm tra xem hoá đơn đã được cập nhật thành công chưa
        HoaDon updatedHoaDon = hoaDonDAO.SelectByID(1);
        assertNotNull(updatedHoaDon, "Không thể tìm thấy hoá đơn đã được cập nhật");
        assertEquals("Đã thanh toán", updatedHoaDon.getTrangThai(), "Trạng thái của hoá đơn sau khi thanh toán không đúng");
    }
    @Test
    void testThanhToanHoaDonChiCoSanPham() {
        // Tạo một hoá đơn mới chỉ có thông tin sản phẩm
        HoaDon hoaDon = new HoaDon(1, "3", null, 10, 1000.0, "2024-04-08", "Chưa thanh toán", 900.0);
        
        // Thực hiện thanh toán cho hoá đơn chỉ có thông tin sản phẩm
        boolean result = hoaDonDAO.thanhToanHoaDon(hoaDon);
        
        // Kiểm tra kết quả
        assertTrue(result, "Thanh toán hoá đơn chỉ có thông tin sản phẩm không thành công");
        
        // Kiểm tra xem hoá đơn đã được cập nhật thành công chưa
        HoaDon updatedHoaDon = hoaDonDAO.SelectByID(1);
        assertNotNull(updatedHoaDon, "Không thể tìm thấy hoá đơn đã được cập nhật");
        assertEquals("Đã thanh toán", updatedHoaDon.getTrangThai(), "Trạng thái của hoá đơn sau khi thanh toán không đúng");
    }
    @Test
    void testSelectBySDT() {
        String sdt = "0773994195"; // Điều chỉnh số điện thoại cho phù hợp với dữ liệu của bạn
        List<HoaDon> hoaDonList = hoaDonDAO.selectBySDT(sdt);
        System.out.println("Danh sách hoá đơn đã thanh toán của khách hàng có số điện thoại " + sdt + ":");
        if (hoaDonList.isEmpty()) {
            System.out.println("Không tìm thấy hoá đơn nào.");
        } else {
            for (HoaDon hoaDon : hoaDonList) {
                System.out.println("Mã hoá đơn: " + hoaDon.getMaHD());
                // In thêm thông tin khác nếu cần
            }
        }
        
    }
    @Test
    void testSelectByTenKH() {
        String tenKH = "Nguyễn Văn A"; // Điều chỉnh tên khách hàng cho phù hợp với dữ liệu của bạn
        List<HoaDon> hoaDonList = hoaDonDAO.selectByTenKH(tenKH);
        System.out.println("Danh sách hoá đơn đã thanh toán của khách hàng " + tenKH + ":");
        if (hoaDonList.isEmpty()) {
            System.out.println("Không tìm thấy hoá đơn nào.");
        } else {
            for (HoaDon hoaDon : hoaDonList) {
                System.out.println("Mã hoá đơn: " + hoaDon.getMaHD());
                // In thêm thông tin khác nếu cần
            }
        }
       
    }
    @Test
    void testSelectByMaHDThanhToan() {
        Integer maHD = 123; // Điều chỉnh mã hoá đơn cho phù hợp với dữ liệu của bạn
        List<HoaDon> hoaDonList = hoaDonDAO.selectByMaHDThanhToan(maHD);
        System.out.println("Danh sách hoá đơn đã thanh toán với mã hoá đơn " + maHD + ":");
        if (hoaDonList.isEmpty()) {
            System.out.println("Không tìm thấy hoá đơn nào.");
        } else {
            for (HoaDon hoaDon : hoaDonList) {
                System.out.println("Mã hoá đơn: " + hoaDon.getMaHD());
                // In thêm thông tin khác nếu cần
            }
        }
    }



    
}
