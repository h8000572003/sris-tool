package com.iisi.web;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iisi.view.ExcelPanel;
import com.iisi.view.WordPanel;

import javax.swing.JMenu;

public class Tool {
	
	private Logger LOG = LoggerFactory.getLogger(ExportController.class);

	private JFrame frame;

	private static Map<String, JPanel> pageMap = new HashMap<String, JPanel>();

	static {
		pageMap.put("產出總表Excel", new ExcelPanel());
		pageMap.put("產出總表Word", new WordPanel());
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tool window = new Tool();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		this.config(frame, mnNewMenu);
		


	}

	private void config(JFrame frame, JMenu menuBar) {
		final Map<String, JPanel> map = this.getPageMap();

		for (final Entry<String, JPanel> entry : map.entrySet()) {
			frame.getContentPane().add(entry.getValue());

			JMenuItem menuItem = new JMenuItem(entry.getKey());
			menuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LOG.info("");
					for (Entry<String, JPanel> mentry : map.entrySet()) {
						entry.getValue().setVisible(false);
					}

					entry.getValue().setVisible(true);
					
				}
			});
			menuBar.add(menuItem);

		}
	}

	public static Map<String, JPanel> getPageMap() {
		return pageMap;
	}

}
