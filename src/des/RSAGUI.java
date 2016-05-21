package des;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;

public class RSAGUI {

	static JFrame fr = new JFrame("DES with RSA Encryption/Decryption System ©YIJING XIA 2015");
	static JLabel title = new JLabel("DES with RSA Encryption/Decryption System");
	static Font t = new Font("微软雅黑", Font.PLAIN, 20);
	static Font all = new Font("微软雅黑", Font.PLAIN, 13);
	static JButton rsakey = new JButton("Get keys for RSA");
	static JLabel nlabel = new JLabel("n: ");
	static JLabel elabel = new JLabel("e: ");
	static JLabel dlabel = new JLabel("d: ");
	static JTextArea n = new JTextArea();
	static JTextArea e = new JTextArea();
	static JTextArea d = new JTextArea();
	static JLabel keylabel = new JLabel("KEY (64 bits): ");
	static JTextArea key = new JTextArea();
	static JLabel keylabelrsa = new JLabel("KEY after RSA: ");
	static JTextArea keyrsa = new JTextArea();
	static JButton load = new JButton("Load input file");
	static JTextArea input = new JTextArea();
	static JTextArea output = new JTextArea();
	static JScrollPane scroll1 = new JScrollPane(input);
	static JScrollPane scroll2 = new JScrollPane(output);
	static JRadioButton encrypt = new JRadioButton("encrypt", true);
	static JRadioButton decrypt = new JRadioButton("decrypt");
	static ButtonGroup choice = new ButtonGroup();
	static JButton ok = new JButton("Generate the output");
	static JButton save = new JButton("Save the output");
	
	static String inputstr = "";
	static String outputstr = "";
	static boolean f = false;
	static boolean rsakeyflag = false;
	
	static DES des = new DES();
	static RSA rsa = new RSA();
	
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		fr.setSize(600, 750);
		fr.setLocation(100, 50);
		fr.setResizable(false);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.getContentPane().setLayout(null);
		
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setLocation(0, 0);
		title.setFont(t);
		title.setSize(600, 50);
		
		encrypt.setSize(100, 20);
		encrypt.setLocation(200, 45);
		decrypt.setSize(100, 20);
		decrypt.setLocation(300, 45);
		choice.add(encrypt);
		choice.add(decrypt);
		
		rsakey.setSize(160, 30);
		rsakey.setLocation(30, 70);
		rsakey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				rsa.generateKey();
				e.setText(rsa.gete());
				n.setText(rsa.getn());
				d.setText(rsa.getd());
				rsakeyflag = true;
            }
		});
		
		elabel.setFont(all);
		elabel.setLocation(200, 75);
		elabel.setSize(30, 20);
		e.setSize(340, 20);
		e.setLocation(230, 75);
		
		nlabel.setFont(all);
		nlabel.setLocation(30, 110);
		nlabel.setSize(30, 20);
		n.setLineWrap(true);
		n.setSize(515, 35);
		n.setLocation(55, 110);
		
		dlabel.setFont(all);
		dlabel.setLocation(30, 155);
		dlabel.setSize(30, 20);
		d.setEditable(true);
		d.setLineWrap(true);
		d.setSize(515, 35);
		d.setLocation(55, 155);
		
		keylabel.setFont(all);
		keylabel.setLocation(30, 200);
		keylabel.setSize(100, 20);
		key.setLocation(130, 200);
		key.setSize(440, 35);
		key.setToolTipText("Please enter 64 bits.");
		key.setLineWrap(true);
		
		keylabelrsa.setFont(all);
		keylabelrsa.setLocation(30, 250);
		keylabelrsa.setSize(100, 20);
		keyrsa.setLocation(130, 250);
		keyrsa.setSize(440, 50);
		keyrsa.setToolTipText("Please enter the key.");
		keyrsa.setLineWrap(true);
		
		load.setLocation(30, 310);
		load.setSize(160, 30);
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
		
		input.setEditable(false);
		input.setLineWrap(true);
		output.setEditable(false);
		output.setLineWrap(true);
		
		scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll1.setSize(260, 360);
		scroll1.setLocation(30, 345);
		scroll2.setSize(260, 360);
		scroll2.setLocation(310, 345);
		
		ok.setSize(180, 30);
		ok.setLocation(210, 310);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				if (f == false) {
					JOptionPane.showMessageDialog(null, "Please load an input file first!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
					return;
				}

		        boolean en;
		        if (encrypt.isSelected()) {
		        	en = true;
		        }
		        else {
		        	en = false;
		        }
		        
		        if (en == true) {
		        	if (key.getText().length() != 64) {
						JOptionPane.showMessageDialog(null, "The key should be 64 bits!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
						return;
					}
		        	int i;
					for (i = 0; i < key.getText().length(); i++) {
						if (key.getText().charAt(i) != '1' && key.getText().charAt(i) != '0') {
							break;
						}
					}
					if (i != key.getText().length()) {
						JOptionPane.showMessageDialog(null, "Key should be binary string.", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
						return;
					}
		        	
					if ((n.getText().length() == 0) || (e.getText().length() == 0)) {
						rsa.generateKey();
						n.setText(rsa.getn());
						e.setText(rsa.gete());
						d.setText(rsa.getd());
					}
					else {
						for (i = 0; i < n.getText().length(); i++) {
							if (n.getText().charAt(i) != '1' && n.getText().charAt(i) != '0') {
								break;
							}
						}
						if (i != n.getText().length()) {
							JOptionPane.showMessageDialog(null, "n should be binary string.", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						for (i = 0; i < e.getText().length(); i++) {
							if (e.getText().charAt(i) != '1' && e.getText().charAt(i) != '0') {
								break;
							}
						}
						if (i != e.getText().length()) {
							JOptionPane.showMessageDialog(null, "e should be binary string.", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					rsa.setn(n.getText());
					rsa.sete(e.getText());
		        	keyrsa.setText(rsa.encode(key.getText()));
		        	
		        	
		        	
		        	if (inputstr.length() % 8 != 0) {
		        		JOptionPane.showMessageDialog(null, "The length of input should be a multiple of 8!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
		        		return;
		        	}
		        	else {
		        		outputstr = des.execute(inputstr, key.getText(), en);
		        		output.setText(outputstr);
		        	}
		        }
		        
		        else {
		        	if ((n.getText().length() == 0) || (d.getText().length() == 0)) {
		        		JOptionPane.showMessageDialog(null, "Please enter n & d!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
		        		return;
		        	}
		        	else {
		        		int i;
		        		for (i = 0; i < n.getText().length(); i++) {
							if (n.getText().charAt(i) != '1' && n.getText().charAt(i) != '0') {
								break;
							}
						}
						if (i != n.getText().length()) {
							JOptionPane.showMessageDialog(null, "n should be binary string.", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						for (i = 0; i < d.getText().length(); i++) {
							if (d.getText().charAt(i) != '1' && d.getText().charAt(i) != '0') {
								break;
							}
						}
						if (i != d.getText().length()) {
							JOptionPane.showMessageDialog(null, "d should be binary string.", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
							return;
						}
		        	}
		        	
					rsa.setn(n.getText());
					rsa.setd(d.getText());
					key.setText(rsa.decode(keyrsa.getText()));
		        	if (key.getText().length() != 64) {
						JOptionPane.showMessageDialog(null, "The key should be 64 bits!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
						return;
					}
		        	
		        	if (inputstr.length() % 64 != 0) {
		        		JOptionPane.showMessageDialog(null, "The length of input should be a multiple of 64!", "ERROR_MESSAGE",JOptionPane.ERROR_MESSAGE);
		        		return;
		        	}
		        	else {
		        		outputstr = des.execute(inputstr, key.getText(), en);
		        		output.setText(outputstr);
		        	}
		        }
            }
		});
		
		save.setSize(160, 30);
		save.setLocation(410, 310);
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
		fr.getContentPane().add(rsakey);
		fr.getContentPane().add(elabel);
		fr.getContentPane().add(e);
		fr.getContentPane().add(nlabel);
		fr.getContentPane().add(n);
		fr.getContentPane().add(dlabel);
		fr.getContentPane().add(d);
		fr.getContentPane().add(keylabel);
		fr.getContentPane().add(key);
		fr.getContentPane().add(keylabelrsa);
		fr.getContentPane().add(keyrsa);
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

}
