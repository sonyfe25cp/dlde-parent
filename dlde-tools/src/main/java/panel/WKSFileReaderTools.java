package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import bit.crawl.store.PageStoreReader;
import bit.crawl.store.StoredPage;

public class WKSFileReaderTools extends JPanel {

	private JFileChooser openChooser;

	private JList webpageList;

	private ActionListener openAction, doubleClickAction;

	public WKSFileReaderTools() {
		openChooser = new JFileChooser();
		openChooser.setFileFilter(new FileNameExtensionFilter("*.pages","pages"));
		openChooser.setDialogTitle("Open");
		openChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		openChooser.setAcceptAllFileFilterUsed(true);
		
		webpageList=new JList();
		
		init();
	}
	
	public void init(){
		action();
		showPanel();
	}

	public void action() {

		openAction = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (openChooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {

					String currentFile = openChooser.getSelectedFile()
							.toString();
					System.out.println("currentFile:"+currentFile);
				}
			}
		};

		openChooser.addActionListener(openAction);
	}
	
	public void showPanel(){
		this.add(openChooser);
		this.add(webpageList);
	}
	
	private String filePath;
	
	public void parsePages(){
		File file=new File(filePath);
		PageStoreReader psr=new PageStoreReader(file);
		ArrayList<StoredPage> pages=psr.loadAll();
		
		
	}
	
	

}
