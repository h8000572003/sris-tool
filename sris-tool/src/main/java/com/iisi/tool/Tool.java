/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package com.iisi.tool;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */

@Configurable
public class Tool {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

//    @Autowired
//    private ExportController controller;

    // ================================================
    // == [Enumeration types] Block Start
    // == [Enumeration types] Block End
    // ================================================
    // == [static variables] Block Start
    // == [static variables] Block Stop
    // ================================================
    // == [instance variables] Block Start
    // == [instance variables] Block Stop
    // ================================================
    // == [static Constructor] Block Start
    // == [static Constructor] Block Stop
    // ================================================
    // == [Constructors] Block Start (含init method)
    // == [Constructors] Block Stop
    // ================================================
    // == [Static Method] Block Start
    // == [Static Method] Block Stop
    // ================================================
    // == [Accessor] Block Start
    // == [Accessor] Block Stop
    // ================================================
    // == [Overrided Method] Block Start (Ex. toString/equals+hashCode)
    // == [Overrided Method] Block Stop
    // ================================================
    // == [Method] Block Start
    // ####################################################################
    // ## [Method] sub-block :
    // ####################################################################
    // == [Method] Block Stop
    // ================================================
    // == [Inner Class] Block Start
    // == [Inner Class] Block Stop
    // ================================================

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        Tool tool = context.getBean(Tool.class);
        tool.frame.setVisible(true);

    }

    /**
     * Create the application.
     */
    public Tool() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("職更檔案產生器");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("功能代號");
        lblNewLabel.setBounds(10, 39, 60, 15);
        panel.add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(80, 36, 96, 21);
        panel.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("輸出");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

//                controller.export();
            }
        });
        btnNewButton.setBounds(10, 100, 87, 23);
        panel.add(btnNewButton);

        textField_1 = new JTextField();
        textField_1.setBounds(80, 10, 96, 21);
        panel.add(textField_1);
        textField_1.setColumns(10);

        JLabel label = new JLabel("");
        label.setBounds(74, 56, 46, 15);
        panel.add(label);

        JLabel label_1 = new JLabel("輸入位置");
        label_1.setBounds(10, 13, 60, 15);
        panel.add(label_1);

        JLabel lblNewLabel_1 = new JLabel("Table");
        lblNewLabel_1.setBounds(10, 64, 46, 15);
        panel.add(lblNewLabel_1);

        textField_2 = new JTextField();
        textField_2.setBounds(80, 61, 96, 21);
        panel.add(textField_2);
        textField_2.setColumns(10);
    }
}
