package Controler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.NhapKho;
import View.SanPhamPanelView;
import View.SuaKho;
import View.XuatNhapKhoPanel;

public class SuaKhoAction implements ActionListener {
    private XuatNhapKhoPanel panel; // Ensure this is of type XuatNhapKhoPanel
    private JTable table;
    private DefaultTableModel dataModel;
    private SanPhamPanelView sanPhamPanel;

    public SuaKhoAction(XuatNhapKhoPanel panel, JTable table, DefaultTableModel dataModel, SanPhamPanelView sanPhamPanel) {
        this.panel = panel;
        this.table = table;
        this.dataModel = dataModel;
        this.sanPhamPanel = sanPhamPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length == 1) {
            int selectedRow = selectedRows[0];
            String maNK = (String) dataModel.getValueAt(selectedRow, 0);
            String ngayNhapStr = (String) dataModel.getValueAt(selectedRow, 1);
            int soLuongNhap = (int) dataModel.getValueAt(selectedRow, 2);
            String tenSachNhap = (String) dataModel.getValueAt(selectedRow, 3);

            NhapKho nhapkho = new NhapKho(maNK, ngayNhapStr, soLuongNhap, tenSachNhap);
            SuaKho sua = new SuaKho("Sửa Kho", nhapkho, dataModel, panel, sanPhamPanel, soLuongNhap); // Ensure panel is of correct type
            sua.setVisible(true);
        } else if (selectedRows.length > 1) {
            JOptionPane.showMessageDialog(panel, "Chỉ được chọn một sản phẩm để cập nhật.");
        } else {
            JOptionPane.showMessageDialog(panel, "Vui lòng chọn một sản phẩm để cập nhật.");
        }
    }
}
