package Controler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.HangHoa;
import View.SanPhamPanelView;
import View.SuaSP;
public class SuaSPAction implements ActionListener {
	private JTable table;
    private DefaultTableModel dataModel;
    private SanPhamPanelView panelView;

    public SuaSPAction(JTable table, DefaultTableModel dataModel, SanPhamPanelView panelView) {
        this.table = table;
        this.dataModel = dataModel;
        this.panelView = panelView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length == 1) {
            int selectedRow = selectedRows[0];
            String maSach = (String) dataModel.getValueAt(selectedRow, 0);
            String tenSach = (String) dataModel.getValueAt(selectedRow, 1);
            String tacGia = (String) dataModel.getValueAt(selectedRow, 2);
            String theLoai = (String) dataModel.getValueAt(selectedRow, 3);
            double giaBan = Double.parseDouble(dataModel.getValueAt(selectedRow, 4).toString());
            int soLuongTonKho = Integer.parseInt(dataModel.getValueAt(selectedRow, 5).toString());

            HangHoa hangHoa = new HangHoa(maSach, tenSach, tacGia, theLoai, giaBan, soLuongTonKho);
            SuaSP sua = new SuaSP("Cập nhật sản phẩm", hangHoa, dataModel, panelView);
            sua.setVisible(true);
        } else if (selectedRows.length > 1) {
            JOptionPane.showMessageDialog(panelView, "Chỉ được chọn một sản phẩm để cập nhật.");
        } else {
            JOptionPane.showMessageDialog(panelView, "Vui lòng chọn một sản phẩm để cập nhật.");
        }
    }
}
