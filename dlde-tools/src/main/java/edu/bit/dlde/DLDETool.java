package edu.bit.dlde;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

//import panel.AnalyzerPanel;
import panel.FileParserPanel;
import panel.WKSFileReaderTools;

@SuppressWarnings("serial")
public class DLDETool extends JFrame{
	
	public DLDETool(){
		super("数字图书馆实验室--检索组工具");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);

		JTabbedPane tool = new JTabbedPane();
		tool.addTab("网页正文抽取工具", null, new FileParserPanel(), "parser");
//		tool.addTab("分词工具", null, new AnalyzerPanel(), "analyzer");
		tool.addTab("爬虫文件解析工具", null, new WKSFileReaderTools(), "wksreader");
		add(tool);
	}
	

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		DLDETool tools=new DLDETool();
		tools.pack();
		tools.setMinimumSize(new Dimension(tools.getSize()));
		tools.setVisible(true);
		
		
		tools.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}