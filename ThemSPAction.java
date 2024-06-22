package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import View.SanPhamPanelView;
import View.ThemSP;
import Model.HangHoa;

public class ThemSPAction implements ActionListener {
	private DefaultTableModel dataModel;
    private SanPhamPanelView panelView;

    public ThemSPAction(DefaultTableModel dataModel, SanPhamPanelView panelView) {
        this.dataModel = dataModel;
        this.panelView = panelView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ThemSP them = new ThemSP("Thêm Sản Phẩm", new ArrayList<HangHoa>(), dataModel, panelView);
        them.setVisible(true);
    }
}
