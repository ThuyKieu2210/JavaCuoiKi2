package Controler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.HangHoa;
import View.SanPhamPanelView;
import View.XoaSP;

public class XoaSPAction implements ActionListener {
    private JTable table;
    private DefaultTableModel dataModel;
    private XoaSP xoa;
    private SanPhamPanelView panelView;

    public XoaSPAction(JTable table, DefaultTableModel dataModel, XoaSP xoa, SanPhamPanelView panelView) {
        this.table = table;
        this.dataModel = dataModel;
        this.xoa = xoa;
        this.panelView = panelView;
        System.out.println("XoaSPAction initialized: " + (xoa != null));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String maSach = (String) dataModel.getValueAt(selectedRow, 0);
            String tenSach = (String) dataModel.getValueAt(selectedRow, 1);
            String tacGia = (String) dataModel.getValueAt(selectedRow, 2);
            String theLoai = (String) dataModel.getValueAt(selectedRow, 3);
            double giaBan = Double.parseDouble(dataModel.getValueAt(selectedRow, 4).toString());
            int soLuongTonKho = Integer.parseInt(dataModel.getValueAt(selectedRow, 5).toString());

            HangHoa hangHoa = new HangHoa(maSach, tenSach, tacGia, theLoai, giaBan, soLuongTonKho);

            int confirm = JOptionPane.showConfirmDialog(panelView, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (xoa != null) {
                    boolean success = xoa.XoaHangHoaToDatabase(maSach);
                    if (success) {
                        xoa.sendProductToServer(hangHoa);
                        dataModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(panelView, "Xóa sản phẩm thành công.");
                    } else {
                        JOptionPane.showMessageDialog(panelView, "Xóa sản phẩm thất bại.");
                    }
                } else {
                    System.err.println("XoaSP instance is null.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(panelView, "Vui lòng chọn một sản phẩm để xóa.");
        }
    }
}
