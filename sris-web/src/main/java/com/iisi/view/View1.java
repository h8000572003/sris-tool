package com.iisi.view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.iisi.tool.Tool;
import com.iisi.web.ExcelController;

@Controller
public class View1 {

	private JFrame frame;

	@Autowired
	private transient ExcelController excelController;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");

		View1 view1 = context.getBean(View1.class);
		view1.frame.setVisible(true);

	}

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.entryPoint
	 */

	public View1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
