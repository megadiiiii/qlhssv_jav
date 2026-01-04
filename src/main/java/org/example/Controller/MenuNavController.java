package org.example.Controller;

import org.example.View.LoginView;
import org.example.View.MainFrame;
import org.example.View.MenuNavView;

public class MenuNavController {
    public MenuNavController(MainFrame currentFrame, MenuNavView menu) {
        menu.homepage.addActionListener(e ->
                currentFrame.showView("HOME")
        );

        menu.ttsv.addActionListener(e ->
                currentFrame.showView("SV")
        );

        menu.facu.addActionListener(e ->
                currentFrame.showView("FACU")
        );

        menu.major.addActionListener(e ->
                currentFrame.showView("MAJOR")
        );

        menu.className.addActionListener(e ->
                currentFrame.showView("CLASS")
        );

        menu.kt.addActionListener(e ->
                currentFrame.showView("KT")
        );

        menu.hb.addActionListener(e ->
                currentFrame.showView("HB")
        );

        menu.logoutItem.addActionListener(e -> {
            new LoginView().setVisible(true);
            currentFrame.dispose();
        });
    }
}
