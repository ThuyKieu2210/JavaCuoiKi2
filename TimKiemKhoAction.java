package Controler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import View.XuatNhapKhoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimKiemKhoAction implements ActionListener{
	private JPanel panel;
    private JTable table;
    private DefaultTableModel dataModel;
    private JTextField tfTimKiem;
    private XuatNhapKhoPanel kho;

    public TimKiemKhoAction(JPanel panel, JTable table, DefaultTableModel dataModel, JTextField tfTimKiem) {
        this.panel = panel;
        this.table = table;
        this.dataModel = dataModel;
        this.tfTimKiem = tfTimKiem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String keyword = tfTimKiem.getText().trim();
        if (!keyword.isEmpty()) {
            ((XuatNhapKhoPanel) panel).highlightRow(table, keyword);
        } else {
            ((XuatNhapKhoPanel) panel).display();
            JOptionPane.showMessageDialog(panel, "Vui lòng nhập từ khóa tìm kiếm.");
        }
    }
}
