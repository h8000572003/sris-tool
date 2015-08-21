package com.iisi.view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class ExcelPanel extends JPanel{
	private JTextField textField;
	public ExcelPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		add(btnNewButton);
	}

}
