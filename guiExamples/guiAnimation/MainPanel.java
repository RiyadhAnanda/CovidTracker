package guiAnimation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import project1package.AllData;
import project1package.Person;

import javax.swing.event.ChangeEvent;
import javax.swing.border.BevelBorder;

public class MainPanel extends JPanel {
	   private final int WIDTH = 800, HEIGHT = 500;

	   private int delay;
	   
	   private JPanel controlPanel;
	   private ReboundPanel imagePanel;
	   
	   private JButton showIcon;
	   private JButton changeSpeed;
	   private JSlider slider;
	   private JLabel lblCurrentSpeed;
	   private JTextField speedText;
	   
	   private AllData myData;
	   private JTextArea textArea;
	   private final ButtonGroup buttonGroup = new ButtonGroup();
	   private JMenuItem mntmOpen;
	   
	   //-----------------------------------------------------------------
	   //  Sets up the panel, including the timer for the animation.
	   //-----------------------------------------------------------------
	   public MainPanel()
	   {
		   super(new BorderLayout());
		   delay = 20;
		   
		   myData = new AllData("./contactData.txt");
		   System.out.println(myData);
		   
		   controlPanel = new JPanel();
		   controlPanel.setPreferredSize (new Dimension(WIDTH / 2, HEIGHT));
		   controlPanel.setBackground (Color.DARK_GRAY);		   

		   showIcon = new JButton("show animation");
		   showIcon.setBounds(20, 106, 115, 23);
		   showIcon.addActionListener(new ShowIconListener());
		   changeSpeed = new JButton("change speed");
		   changeSpeed.setBounds(20, 72, 115, 23);
		   changeSpeed.addActionListener(new ChangeSpeedListener());
		   controlPanel.setLayout(null);
		   controlPanel.add(changeSpeed);		   
		   controlPanel.add(showIcon);
		   imagePanel = new ReboundPanel(WIDTH/2,HEIGHT);
		   
	      setPreferredSize (new Dimension(WIDTH, HEIGHT));
	      setBackground (Color.white);
	      add(controlPanel, BorderLayout.WEST);
	      
	       slider = new JSlider();
	      slider.addChangeListener(new ChangeListener() {
	      	public void stateChanged(ChangeEvent e) {
	      		imagePanel.setDelay(slider.getValue());
	      		delay = slider.getValue();
	      		speedText.setText(""+delay);
	      	}
	      });
	      slider.setPaintTicks(true);
	      slider.setPaintLabels(true);
	      slider.setMajorTickSpacing(10);
	      slider.setBounds(10, 150, 164, 47);
	      controlPanel.add(slider);
	      
	      lblCurrentSpeed = new JLabel("Covid Contact Tracer");
	      lblCurrentSpeed.setFont(new Font("Tahoma", Font.PLAIN, 18));
	      lblCurrentSpeed.setForeground(Color.WHITE);
	      lblCurrentSpeed.setBounds(114, 0, 182, 29);
	      controlPanel.add(lblCurrentSpeed);
	      
	      speedText = new JTextField();
	      speedText.setText("20");
	      speedText.setBounds(20, 208, 41, 20);
	      controlPanel.add(speedText);
	      speedText.setColumns(10);
	      
	      Iterator<String> iter = myData.getIterator();
	      ArrayList<String> ids = new ArrayList<String>();
	      while (iter.hasNext()) {
	    	  ids.add(iter.next());
	      }
	      
	      JComboBox idComboBox = new JComboBox();
	      idComboBox.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		String id = idComboBox.getSelectedItem().toString();
	      		Person p = myData.findPerson(id);
	      		textArea.setText(p.toString());
	      	}	      });
	      idComboBox.setModel(new DefaultComboBoxModel(ids.toArray()));
	      idComboBox.setBounds(247, 69, 143, 29);
	      controlPanel.add(idComboBox);
	      
	      JLabel lblNewLabel = new JLabel("New label");
	      lblNewLabel.setIcon(new ImageIcon(MainPanel.class.getResource("/guiAnimation/mask1.jpg")));
	      lblNewLabel.setBounds(20, 262, 85, 73);
	      controlPanel.add(lblNewLabel);
	      
	      JRadioButton rdbtnSick = new JRadioButton("sick");
	      rdbtnSick.setSelected(true);
	      buttonGroup.add(rdbtnSick);
	      rdbtnSick.setBounds(286, 235, 109, 23);
	      controlPanel.add(rdbtnSick);
	      
	      JRadioButton rdbtnWell = new JRadioButton("well");
	      buttonGroup.add(rdbtnWell);
	      rdbtnWell.setBounds(286, 261, 109, 23);
	      controlPanel.add(rdbtnWell);
	      
	      JRadioButton rdbtnCured = new JRadioButton("cured");
	      buttonGroup.add(rdbtnCured);
	      rdbtnCured.setBounds(286, 287, 109, 23);
	      controlPanel.add(rdbtnCured);
	      
	      JMenuBar menuBar = new JMenuBar();
	      menuBar.setBounds(10, 407, 101, 22);
	      controlPanel.add(menuBar);
	      
	      JButton btnCovidQuickFacts = new JButton("Covid Quick Facts Video");
	      btnCovidQuickFacts.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		try {
					Desktop.getDesktop().browse(new URI("https://youtu.be/Ma07a6svw5w"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	      	}
	      });
	      btnCovidQuickFacts.setBounds(10, 354, 159, 23);
	      controlPanel.add(btnCovidQuickFacts);
	      
	      JMenu mnFile = new JMenu("file");
	      mnFile.setBounds(0, 0, 29, 20);
	      controlPanel.add(mnFile);
	      
	      mntmOpen = new JMenuItem("open");
	      mntmOpen.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	    		JFileChooser jfc = new JFileChooser("./");

	    		int returnValue = jfc.showOpenDialog(null);

	    		if (returnValue == JFileChooser.APPROVE_OPTION) {
	    			File selectedFile = jfc.getSelectedFile();
	    			imagePanel.changeImage(selectedFile.getAbsolutePath());
	    		}  

	      	}
	      });
	      mnFile.add(mntmOpen);
	      
	      JLabel lblCovidIconSpeed = new JLabel("Speed");
	      lblCovidIconSpeed.setFont(new Font("Tahoma", Font.PLAIN, 16));
	      lblCovidIconSpeed.setForeground(Color.WHITE);
	      lblCovidIconSpeed.setBounds(60, 125, 51, 26);
	      controlPanel.add(lblCovidIconSpeed);
	      
	      textArea = new JTextArea();
	      textArea.setBounds(184, 105, 209, 92);
	      controlPanel.add(textArea);
	      
	      JLabel lblIconSettings = new JLabel("Icon Settings");
	      lblIconSettings.setForeground(Color.WHITE);
	      lblIconSettings.setBounds(44, 47, 91, 14);
	      controlPanel.add(lblIconSettings);
	      
	      JLabel lblPatientSearch = new JLabel("Patient Search");
	      lblPatientSearch.setForeground(Color.WHITE);
	      lblPatientSearch.setBounds(270, 47, 101, 14);
	      controlPanel.add(lblPatientSearch);
	      
	      JLabel lblIsThisPatient = new JLabel("Is this patient...");
	      lblIsThisPatient.setForeground(Color.WHITE);
	      lblIsThisPatient.setBounds(286, 211, 91, 14);
	      controlPanel.add(lblIsThisPatient);
	      
	      JLabel lblRemovePatient = new JLabel("Remove Patient?");
	      lblRemovePatient.setForeground(Color.WHITE);
	      lblRemovePatient.setBounds(286, 317, 85, 14);
	      controlPanel.add(lblRemovePatient);
	      
	      JRadioButton rdbtnNewRadioButton = new JRadioButton("Yes");
	      rdbtnNewRadioButton.setBounds(286, 338, 51, 23);
	      controlPanel.add(rdbtnNewRadioButton);
	      
	     
	      add(imagePanel, BorderLayout.EAST);

	   }
	   private class ShowIconListener implements ActionListener {
		   public void actionPerformed (ActionEvent event) {
			   if (imagePanel.isVisible())
				   imagePanel.setVisible(false);
			   else
				   imagePanel.setVisible(true);
		   }
	   }
	   private class ChangeSpeedListener implements ActionListener {
		   public void actionPerformed (ActionEvent event) {
			   do {
				   try {
					   String delayStr = JOptionPane.showInputDialog("Enter an integer 0-100");
					   delay = Integer.parseInt(delayStr);
				   } catch (Exception e) {
					   delay = -1;
				   } 
			   } while (delay < 0 || delay > 100); 
			   imagePanel.setDelay(delay);
			   slider.setValue(delay);
			   speedText.setText(""+delay);
		   }
	   }	   
}
