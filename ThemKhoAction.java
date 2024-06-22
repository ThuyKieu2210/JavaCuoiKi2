package Controler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Model.NhapKho;
import View.ThemKho;
import View.XuatNhapKhoPanel;

public class ThemKhoAction implements ActionListener {
    private JPanel panel;
    private JTable table;
    private DefaultTableModel dataModel;
    private XuatNhapKhoPanel kho; // Đảm bảo rằng kho được khởi tạo đúng cách

    public ThemKhoAction(XuatNhapKhoPanel kho) {
        this.kho = kho;
    }

    public ThemKhoAction(JPanel panel, JTable table, DefaultTableModel dataModel, XuatNhapKhoPanel kho) {
        this.panel = panel;
        this.table = table;
        this.dataModel = dataModel;
        this.kho = kho;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ThemKho them = new ThemKho("Thêm Kho", new ArrayList<NhapKho>(), dataModel, kho);
        them.setVisible(true);
    }
}
