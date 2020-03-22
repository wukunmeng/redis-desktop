package org.home.common;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Boot {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Boot window = new Boot();
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
	public Boot() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			frame = new JFrame();
			frame.setBounds(100, 100, 450, 300);
			frame.setLocationRelativeTo(null);
			ImageIcon imageIcon = new ImageIcon("/opt/software/firefox/browser/chrome/icons/default/default128.png");
			frame.setIconImage(imageIcon.getImage());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception e) {
			
		}
	}

}
