package src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
	private JButton JBmutate4;
	private JButton JBmutate6;
	private JButton JBstart;
	private JButton JBfilechooser;
	private JFileChooser fileChooser;
	private File selectedFile;
	private Generator generator;
	
	public View(){
		setTitle("SISTEMA ALGORITMOS GENÉTICOS");
        setSize(225, 250);
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
		
		JBfilechooser = new JButton("Clique para escolher");
		JBfilechooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//            	fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            	fileChooser = new JFileChooser();
            	fileChooser.setCurrentDirectory(new File("C:\\Users\\ipdmartins\\Documents\\udesc\\7th_semester\\INC\\trabalho 1"));
        		int returnValue = fileChooser.showOpenDialog(null);
        		if (returnValue == JFileChooser.APPROVE_OPTION) {
        			selectedFile = fileChooser.getSelectedFile();
        		}
            }
        });
		
		JLthreshold = new JLabel("Defina o ft threshold entre 0-100");
		TXTtreshold = new TextField();
		TXTtreshold.setPreferredSize(new Dimension(120,30));
		
		JBmutate4 = new JButton("Mutate position 4");
		JBmutate4.setPreferredSize(new Dimension(140,30));
		JBmutate4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	generator.setMutationOption(true);
            }
        });
		
		JBmutate6 = new JButton("Mutate position 6");
		JBmutate6.setPreferredSize(new Dimension(140,30));
		JBmutate6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generator.setMutationOption(false);
			}
		});
		
		JBstart = new JButton("INICIAR");
		JBstart.setPreferredSize(new Dimension(140,30));
		JBstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	generator.setThreshold(Integer.parseInt(TXTtreshold.getText()));
            	generator.sheetReader(selectedFile.getAbsolutePath());
            }
        });

		JPinicial.add(JLfile);
		JPinicial.add(JBfilechooser);
		JPinicial.add(JLthreshold);
		JPinicial.add(TXTtreshold);
		JPinicial.add(JBmutate4);
		JPinicial.add(JBmutate6);
		JPinicial.add(JBstart);
		
		add(JPinicial, BorderLayout.CENTER);
	}
	
}
