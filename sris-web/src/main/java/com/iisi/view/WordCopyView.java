package com.iisi.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.iisi.web.WordCopyTaskController;
import javax.swing.JPasswordField;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;

@Controller
public class WordCopyView {

	private JFrame frame;

	@Autowired
	private transient WordCopyTaskController wordCopyTaskController;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

					WordCopyView wordCopyView = context.getBean(WordCopyView.class);
					wordCopyView.frame.setVisible(true);
			        
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WordCopyView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("templatePath:");
		panel_1.add(lblNewLabel);
		
				textField = new JTextField();
				panel_1.add(textField);
				textField.setColumns(10);
				
						JButton btnNewButton = new JButton("複製");
						panel_1.add(btnNewButton);
						btnNewButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								wordCopyTaskController.getDto().setTemplatePath(
										textField.getText());
								wordCopyTaskController.copy();
							}
						});

	}

}
