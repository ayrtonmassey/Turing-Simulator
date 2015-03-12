package turing.gui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AboutFrame extends JFrame {

	AboutFrame()
	{
		init();
	}
	
	public void init()
	{
		this.setTitle("About");
		
		Image icon = new ImageIcon(this.getClass().getResource("img/icon.png")).getImage();
		this.setIconImage(icon);
		
		Icon logo = new ImageIcon(this.getClass().getResource("img/aboutlogo.png"));
		JLabel logoLabel = new JLabel(logo);
			logoLabel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
		
		JLabel descriptionLabel = new JLabel(
											"<html><p style=\"text-align:justify;\">" +
												"This program was developed by " +
												"Andrew Hynes, Jack Maiden, Ayrton Massey and Alastair Ross " +
												"for the Heriot-Watt Turing Centenary Programming Challenge, " +
												"celebrating 100 years since the birth of Alan Turing." +
											"</p><br />" +
											"<p style=\"text-align:justify;\">" +
												"Turing was a pioneer in the field of Computer Science " +
												"who invented, among many things, the Turing Machine - " +
												"the concept upon which this program is based." +
											"</p></html>"
											);
		
			descriptionLabel.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
			descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
			descriptionLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		
		
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
		this.add(logoLabel);
		this.add(descriptionLabel);
		
		this.setPreferredSize(new Dimension(400,400));
		this.pack();
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
