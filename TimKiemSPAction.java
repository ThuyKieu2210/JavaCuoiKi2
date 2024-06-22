package Controler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TimKiemSPAction implements ActionListener {
	private JTextField tfTimKiem;
    private JTable table;
    private DefaultTableModel dataModel;

    public TimKiemSPAction(JTextField tfTimKiem, JTable table, DefaultTableModel dataModel) {
        this.tfTimKiem = tfTimKiem;
        this.table = table;
        this.dataModel = dataModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String keyword = tfTimKiem.getText().trim();
        if (!keyword.isEmpty()) {
            TimKiem(keyword);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập từ khóa tìm kiếm.");
        }
    }

    private void TimKiem(String keyword) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlysach", "root", "221004");
            String sql = "SELECT maSach, tenSach, tacGia, theLoai, giaBan, soLuongTonKho FROM them WHERE maSach LIKE ? OR tenSach LIKE ? OR tacGia LIKE ? OR theLoai LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 1; i <= 4; i++) {
                statement.setString(i, "%" + keyword + "%");
            }
            ResultSet rs = statement.executeQuery();

            dataModel.setRowCount(0);

            int count = 0;
            while (rs.next()) {
                String maSach = rs.getString("maSach");
                String tenSach = rs.getString("tenSach");
                String tacGia = rs.getString("tacGia");
                String theLoai = rs.getString("theLoai");
                double gia = rs.getDouble("giaBan");
                int soLuongTonKho = rs.getInt("soLuongTonKho");

                dataModel.addRow(new Object[]{maSach, tenSach, tacGia, theLoai, gia, soLuongTonKho});
                count++;
            }

            if (count == 0) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm phù hợp.");
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
