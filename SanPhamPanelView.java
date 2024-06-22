package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import Controler.ThemSPAction;
import Controler.XoaSPAction;
import Controler.TimKiemSPAction;
import Controler.SuaSPAction;
import Model.HangHoa;
import java.util.ArrayList;

public class SanPhamPanelView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JLabel lblQLsach;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JLabel lblNewLabel;
    private DefaultTableModel dataModel;
    private ThemSP them;
    private XoaSP xoa;
    private JButton btnTimKiem;
    public SuaKho suaKho;
    private JLabel lblNewLabel_1;

    public SanPhamPanelView() {
        setBackground(new Color(255, 255, 187));
        setLayout(null);
        this.init();
        xoa = new XoaSP(this);
    }

    public JPanel getPanel() {
        return this;
    }

    public void init() {
        this.setBounds(274, 56, 653, 415);
        this.setForeground(new Color(0, 128, 255));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(14, 115, 620, 195);
        add(scrollPane);

        lblQLsach = new JLabel("QUẢN LÝ SÁCH");
        lblQLsach.setForeground(new Color(255, 128, 64));
        lblQLsach.setBackground(new Color(255, 128, 64));
        lblQLsach.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblQLsach);

        table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dataModel = new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Ma Sach", "Ten Sach", "Tac Gia", "The Loai", "Gia Sach", "So Luong Ton Kho"
            }
        );
        table.setModel(dataModel);
        scrollPane.setViewportView(table);

        btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnThem.setBounds(41, 330, 120, 43);
        btnThem.addActionListener(new ThemSPAction(dataModel, this));
        add(btnThem);

        btnSua = new JButton("Cập Nhật");
        btnSua.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnSua.setBounds(244, 330, 120, 43);
        btnSua.addActionListener(new SuaSPAction(table, dataModel, this));
        add(btnSua);
        xoa = new XoaSP(this);

        btnXoa = new JButton("Xóa");
        btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnXoa.setBounds(445, 330, 120, 43);
        btnXoa.addActionListener(new XoaSPAction(table, dataModel, xoa, this));
        add(btnXoa);

        JTextField tfTimKiem = new JTextField();
        tfTimKiem.setBounds(147, 66, 271, 34);
        add(tfTimKiem);
        tfTimKiem.setColumns(10);
        tfTimKiem.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Không dùng
            }

            private void filter() {
                String keyword = tfTimKiem.getText().trim();
                if (!keyword.isEmpty()) {
                    highlightRow(table, keyword);
                } else {
                    display();
                }
            }

        });

        lblNewLabel = new JLabel("Tìm Kiếm: ");
        lblNewLabel.setForeground(new Color(255, 128, 64));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setBounds(44, 66, 93, 34);
        add(lblNewLabel);

        btnTimKiem = new JButton("Tìm Kiếm");
        btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnTimKiem.setBounds(445, 62, 120, 43);
        btnTimKiem.addActionListener(new TimKiemSPAction(tfTimKiem, table, dataModel));
        add(btnTimKiem);

        lblNewLabel_1 = new JLabel("TRANG SẢN PHẨM");
        lblNewLabel_1.setForeground(new Color(255, 128, 64));
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 19));
        lblNewLabel_1.setBounds(244, 21, 184, 35);
        add(lblNewLabel_1);

        display();
        setVisible(true);
    }

    private void highlightRow(JTable table, String keyword) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        if (keyword.isEmpty()) {
            table.setRowSorter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
        }
    }

    public void display() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlysach", "root", "221004");
            String sql = "SELECT maSach, tenSach, tacGia, theLoai, giaBan, soLuongTonKho FROM them";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            DefaultTableModel qldtvsmodel_table = (DefaultTableModel) table.getModel();
            qldtvsmodel_table.setRowCount(0);

            while (rs.next()) {
                String maSach = rs.getString("maSach");
                String tenSach = rs.getString("tenSach");
                String tacGia = rs.getString("tacGia");
                String theLoai = rs.getString("theLoai");
                double gia = rs.getDouble("giaBan");
                DecimalFormat format = new DecimalFormat("0.#");
                int soLuongTonKho = rs.getInt("soLuongTonKho");

                qldtvsmodel_table.addRow(new Object[]{maSach, tenSach, tacGia, theLoai, format.format(gia), soLuongTonKho});
            }

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProductQuantity(String maSach, int soLuongNhap) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(maSach)) {
                int currentQuantity = Integer.parseInt(model.getValueAt(i, 5).toString());
                
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlysach", "root", "221004");
                    String sql = "UPDATE them SET soLuongTonKho = ? WHERE maSach = ?;";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, soLuongNhap+currentQuantity);
                    statement.setString(2, maSach);
                    
                    statement.execute();
                    
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void themSanPham() {
        them = new ThemSP("Thêm Sản Phẩm", new ArrayList<HangHoa>(), (DefaultTableModel) table.getModel(), this);
        them.setVisible(true);
    }
}
