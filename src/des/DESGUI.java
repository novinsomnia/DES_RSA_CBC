package des;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

public class DESGUI {

	static JFrame fr = new JFrame("6-Round DES Encryption/Decryption System ©YIJING XIA 2015");
	static JLabel title = new JLabel("6-Round DES Encryption/Decryption System");
	static Font t = new Font("微软雅黑", Font.PLAIN, 20);
	static Font all = new Font("微软雅黑", Font.PLAIN, 13);
	static JLabel keylabel = new JLabel("KEY:");
	static JTextField key = new JTextField(8);
	static JButton load = new JButton("Load input file");
	static JTextArea input = new JTextArea();
	static JTextArea output = new JTextArea();
	static JScrollPane scroll1 = new JScrollPane(input);
	static JScrollPane scroll2 = new JScrollPane(output);
	static JRadioButton encrypt = new JRadioButton("encrypt", true);
	static JRadioButton decrypt = new JRadioButton("decrypt");
	static ButtonGroup choice = new ButtonGroup();
	static JButton ok = new JButton("Generate");
	static JButton save = new JButton("Save the output");
	
	static String inputstr = "";
	static String outputstr = "";
	static boolean f = false;
	
	static DES des = new DES();
	
	public static void readFile(File file) throws IOException {
		if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
		inputstr = "";
		FileReader fr = new FileReader(file);
        
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        line = br.readLine();
        
        while(line != null) {
        	inputstr += line;
        	line = br.readLine();
        }
	}
	
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		fr.setSize(600, 600);
		fr.setLocation(100, 100);
		fr.setResizable(false);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.getContentPane().setLayout(null);
		
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setLocation(0, 0);
		title.setFont(t);
		title.setSize(600, 50);
		
		keylabel.setFont(all);
		keylabel.setLocation(30, 50);
		keylabel.setSize(40, 20);
		//IVlabel.setLocation(30, 75);
		//IVlabel.setSize(600, 20);
		key.setLocation(70, 50);
		key.setSize(500, 20);
		key.setToolTipText("Please enter an 8-byte string.");
		
		load.setLocation(30, 80);
		load.setSize(140, 30);
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
		        JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
		        jfc.setAcceptAllFileFilterUsed(false);
		        jfc.setFileFilter(new FileFilter() {
		            @Override
		            public boolean accept(File f){
		                if(f.getName().endsWith("txt") || f.isDirectory()) return true;
		                return false;
		            }
		 
		            @Override
		            public String getDescription(){
		                // TODO Auto-generated method stub
		                return "(*.txt)";
		            }
		        });
		        int result = jfc.showOpenDialog(new JLabel()); 
		        if (result == JFileChooser.APPROVE_OPTION) {
			        File file=jfc.getSelectedFile();
			        try {
						readFile(file);
						int i;
						for (i = 0; i < inputstr.length(); i++) {
							if (inputstr.charAt(i) != '1' && inputstr.charAt(i) != '0') {
								break;
							}
						}
						if (i == inputstr.length()) {
							f = true;
					        input.setText(inputstr);
					        outputstr = "";
					        output.setText(outputstr);
						}
						else {
							JOptionPane.showMessageDialog(null, "The content of input file should be binary.", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
							f = false;
							inputstr = "";
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
            }
		});
		
		//input.setSize(260, 430);
		//input.setLocation(30, 120);
		input.setEditable(false);
		input.setLineWrap(true);
		
		//output.setSize(260, 430);
		//output.setLocation(310, 120);
		output.setEditable(false);
		output.setLineWrap(true);
		
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll1.setSize(260, 430);
		scroll1.setLocation(30, 120);
		scroll2.setSize(260, 430);
		scroll2.setLocation(310, 120);

		encrypt.setSize(80, 20);
		encrypt.setLocation(170, 85);
		decrypt.setSize(80, 20);
		decrypt.setLocation(250, 85);
		choice.add(encrypt);
		choice.add(decrypt);
		
		ok.setSize(90, 30);
		ok.setLocation(330, 80);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				if (f == false) {
					JOptionPane.showMessageDialog(null, "Please load an input file first!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (key.getText().length() != 8) {
					JOptionPane.showMessageDialog(null, "The key should be an 8-byte string!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
		        boolean e;
		        if (encrypt.isSelected()) {
		        	e = true;
		        }
		        else {
		        	e = false;
		        }
		        
		        if (e == true) {
		        	if (inputstr.length() % 8 != 0) {
		        		JOptionPane.showMessageDialog(null, "The length of input should be a multiple of 8!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
		        		return;
		        	}
		        	else {
		        		outputstr = des.execute(inputstr, key.getText(), e);
		        		output.setText(outputstr);
		        	}
		        }
		        else {
		        	if (inputstr.length() % 64 != 0) {
		        		JOptionPane.showMessageDialog(null, "The length of input should be a multiple of 64!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
		        		return;
		        	}
		        	else {
		        		outputstr = des.execute(inputstr, key.getText(), e);
		        		output.setText(outputstr);
		        	}
		        }
            }
		});
		
		save.setSize(135, 30);
		save.setLocation(435, 80);
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
	        	if (outputstr.length() == 0) {
	        		JOptionPane.showMessageDialog(null, "The output is empty. Please load an input file / generate the output first.", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
	        		return;
	        	}
		        JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);  
		        jfc.setAcceptAllFileFilterUsed(false);
		        jfc.setFileFilter(new FileFilter() {
		            @Override
		            public boolean accept(File f){
		                if(f.getName().endsWith("txt") || f.isDirectory()) return true;
		                return false;
		            }
		 
		            @Override
		            public String getDescription(){
		                // TODO Auto-generated method stub
		                return "(*.txt)";
		            }
		        });
		        int result = jfc.showSaveDialog(new JLabel());
		        if (result == JFileChooser.APPROVE_OPTION) {
		        	File file = jfc.getSelectedFile();
		        	String ends = ".txt";
		        	if (!file.getAbsolutePath().toUpperCase().endsWith(ends.toUpperCase())) {
		        	    file = new File(file.getAbsolutePath() +ends);
		        	}
		        	if (file.exists()) {
		        		JOptionPane.showMessageDialog(null, "The file is existed.", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
		        		return;
		        	}
		        	else {
		        		try {
							file.createNewFile();
							FileWriter writer;
							writer = new FileWriter(file, true); 
				            writer.write(outputstr);
				        	writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	}
		        }
            }
		});
		
		fr.getContentPane().add(title);
		fr.getContentPane().add(keylabel);
		fr.getContentPane().add(key);
		//fr.getContentPane().add(IVlabel);
		fr.getContentPane().add(load);
		fr.getContentPane().add(encrypt);
		fr.getContentPane().add(decrypt);
		fr.getContentPane().add(ok);
		fr.getContentPane().add(save);
		fr.getContentPane().add(scroll1);
		fr.getContentPane().add(scroll2);
		//fr.getContentPane().add(input);
		//fr.getContentPane().add(output);
		fr.setVisible(true);
	}
	*/

}
