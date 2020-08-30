package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class View extends JFrame{
	private JPanel JPinicial;
	private JLabel JLfile;
	private JLabel JLthreshold;
	private TextField TXTtreshold;
	private JButton JBstart;
	private JButton JBfilechooser;
	private JFileChooser fileChooser;
	private File selectedFile;
	private Generator generator;
	
	public View(){
		setTitle("SISTEMA ALGORITMOS GENÉTICOS");
        setSize(200, 180);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
		init();
	}

	private void init() {
		generator = new Generator();
        JPinicial = new JPanel(new FlowLayout());
		JLfile = new JLabel("Escolha abaixo arquivo XLS");
		
		JBstart = new JButton("Iniciar");
		JBstart.setPreferredSize(new Dimension(140,30));
		JBstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	generator.setThreshold(Integer.parseInt(TXTtreshold.getText()));
            	generator.sheetReader(selectedFile.getAbsolutePath());
            }
        });
		
		JBfilechooser = new JButton("Clique para escolher");
		JBfilechooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
        		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        		int returnValue = jfc.showOpenDialog(null);
        		if (returnValue == JFileChooser.APPROVE_OPTION) {
        			selectedFile = jfc.getSelectedFile();
        		}
            }
        });
		
		
		JLthreshold = new JLabel("Defina o ft threshold entre 0-100");
		TXTtreshold = new TextField();
		TXTtreshold.setPreferredSize(new Dimension(120,30));
		
		JPinicial.add(JLfile);
		JPinicial.add(JBfilechooser);
		JPinicial.add(JLthreshold);
		JPinicial.add(TXTtreshold);
		JPinicial.add(JBstart);
		
		add(JPinicial, BorderLayout.CENTER);
	}
	
	
	
	
	
}
