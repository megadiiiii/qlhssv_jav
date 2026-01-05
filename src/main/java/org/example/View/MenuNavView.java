package org.example.View;

import javax.swing.*;

public class MenuNavView extends JMenuBar {
    public JMenuItem ttsv, facu, major, className, kt, kl, logoutItem, homepage, cohort, teacher, sus;

    public MenuNavView() {
        JMenu hp = new JMenu("Trang chủ");
        homepage = new JMenuItem("Trang chủ");
        hp.add(homepage);
        add(hp);

        JMenu qlhv = new JMenu("Quản lý thông tin SV");
        ttsv = new JMenuItem("Thông tin SV");
        qlhv.add(ttsv);
        add(qlhv);

        JMenu department = new JMenu("Khoa - Ngành - Lớp");
        facu = new JMenuItem("Quản lý khoa");
        major = new JMenuItem("Quản lý chuyên ngành");
        className = new JMenuItem("Quản lý lớp");
        cohort = new JMenuItem("Quản lý khóa đào tạo");
        teacher = new JMenuItem("Quản lý thông tin giảng viên");
        department.add(facu);
        department.add(major);
        department.add(className);
        department.add(cohort);
        department.add(teacher);
        add(department);

        JMenu hc = new JMenu("Khen thưởng - Kỷ luật");
        kt = new JMenuItem("Khen thưởng");
        kl = new JMenuItem("Kỷ luật");
        sus = new JMenuItem("Bảo lưu");
        hc.add(kt);
        hc.add(kl);
        hc.add(sus);
        add(hc);




        add(Box.createHorizontalGlue());

        JMenu logout = new JMenu("Tài khoản");
        logoutItem = new JMenuItem("Đăng xuất");
        logout.add(logoutItem);
        add(logout);
    }
}
