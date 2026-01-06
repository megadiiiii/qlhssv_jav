package org.example.Controller;

import org.example.View.HomepageView;
import org.example.View.MainFrame;

public class HomepageController {
    public HomepageController(MainFrame currentFrame, HomepageView view) {
        view.btnStudent.addActionListener(e ->
                currentFrame.showView("SV")
        );

        view.btnFaculties.addActionListener(e ->
                currentFrame.showView("FACU")
        );

        view.btnMajor.addActionListener(e ->
                currentFrame.showView("MAJOR")
        );

        view.btnClass.addActionListener(e ->
                currentFrame.showView("CLASS")
        );

        view.btnReward.addActionListener(e ->
                currentFrame.showView("KT")
        );

        view.btnDiscipline.addActionListener(e ->
                currentFrame.showView("KL")
        );

        view.btnCohort.addActionListener(e ->
                currentFrame.showView("COHORT")
        );

        view.btnTeacher.addActionListener(e ->
                currentFrame.showView("TEACHER")
        );

        view.btnScholarship.addActionListener(e ->
                currentFrame.showView("HB")
        );

        view.btnSuspension.addActionListener(e ->
                currentFrame.showView("BL")
        );

        view.btnRole.addActionListener(e ->
                currentFrame.showView("CBL")
        );
    }
}
