package panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import mycomponents.GBLPanel;
import mycomponents.LCBPanel;
import mycomponents.LCBPanel.TextComponent;
import mycomponents.TextEditor;
import mycomponents.TextEditor.AbstractTextEditorModel;
import edu.bit.dlde.HtmlFile;

/**
 * 网页文本解析对比工具
 * 
 * @author hz
 *
 */
public class FileParserPanel extends GBLPanel {


	private String[] charSets = { "utf-8", "gb2312" };

	private GBLPanel WNPanel, WSPanel, ENPanel, ESPanel;
	private JTabbedPane srcTP, contentTP;
	private LCBPanel urlLCB, filepathLCB, toolLCB, titleLCB;
	private TextEditor srcEditor, contentEditor,contentEx1Editor;

	private JFileChooser openChooser, saveChooser;
	private ActionListener openAction, saveAction;

	public FileParserPanel() {
		WNPanel = new GBLPanel(BorderFactory.createEtchedBorder());
		WSPanel = new GBLPanel(BorderFactory.createEtchedBorder());

		ENPanel = new GBLPanel(BorderFactory.createEtchedBorder());
		ESPanel = new GBLPanel(BorderFactory.createEtchedBorder());

		srcTP = new JTabbedPane();
		contentTP = new JTabbedPane();

		urlLCB = new LCBPanel("url: ", new Dimension(425, 32), "apply",
				TextComponent.COMBOBOX);
		filepathLCB = new LCBPanel("file: ", new Dimension(425, 32), "view..",
				TextComponent.COMBOBOX);
		toolLCB = new LCBPanel("tools: ", new Dimension(425, 32), "save",
				TextComponent.COMBOBOX);
		for (int i = 0; i < charSets.length; i++) {
			toolLCB.getComboBox().addItem(charSets[i]);
		}
		titleLCB = new LCBPanel("title: ", new Dimension(425, 32), "next",
				TextComponent.TEXTAREA);

		srcEditor = new TextEditor();
		contentEditor = new TextEditor();
		contentEx1Editor = new TextEditor();

		openChooser = new JFileChooser();
		openChooser.setFileFilter(new FileNameExtensionFilter("*.htm/*.html",
				"htm", "html"));
		openChooser.setDialogTitle("Open");
		openChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		openChooser.setAcceptAllFileFilterUsed(true);

		saveChooser = new JFileChooser();
		saveChooser
				.setFileFilter(new FileNameExtensionFilter("*.dlde", "dlde"));
		saveChooser.setDialogTitle("Save");
		saveChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		saveChooser.setAcceptAllFileFilterUsed(false);

		MakeView();
		MakeControl();
	}

	private void MakeView() {
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory
						.createEtchedBorder(), "", TitledBorder.LEFT,
						TitledBorder.TOP));

		srcEditor.getContent().setBorder(
				BorderFactory.createMatteBorder(1, 5, 1, 5, Color.ORANGE));
		srcEditor.setEditable(false);
		contentEditor.getContent().setBorder(
				BorderFactory.createMatteBorder(1, 5, 1, 5, Color.GREEN));
		contentEx1Editor.getContent().setBorder(
				BorderFactory.createMatteBorder(1, 5, 1, 5, Color.GREEN));
		//contentEx1Editor.setEditable(false);

		srcTP.addTab("src", srcEditor);
		contentEditor.setTitle(HtmlFile.ParseMethod.SimpleHtmlExtracter.toString());
		contentTP.addTab(contentEditor.getTitle(), contentEditor);
		contentEx1Editor.setTitle(HtmlFile.ParseMethod.BlockExtractor.toString());
		contentTP.addTab(contentEx1Editor.getTitle(), contentEx1Editor);

		this.setGBC(new Insets(2, 1, 2, 1), -1, -1);
		this.addAComponent(WNPanel, null, 0, 0, 1, 1, 0.5, 0,
				GBLPanel.GBCFill.HORIZONTAL, GBLPanel.GBCAnchor.NORTHWEST);
		this.addAComponent(WSPanel, null, 0, 1, 1, 1, 0.5, 1, GBCFill.BOTH,
				GBCAnchor.SOUTHWEST);
		this.addAComponent(ENPanel, null, 1, 0, 1, 1, 0.5, 0,
				GBLPanel.GBCFill.HORIZONTAL, GBLPanel.GBCAnchor.NORTHEAST);
		this.addAComponent(ESPanel, null, 1, 1, 1, 1, 0.5, 1, GBCFill.BOTH,
				GBCAnchor.SOUTHEAST);

		urlLCB.getComboBox().setAutoscrolls(true);
		urlLCB.getComboBox().setEditable(true);
		filepathLCB.getComboBox().setAutoscrolls(true);
		filepathLCB.getComboBox().setEditable(false);

		WNPanel.setGBC(new Insets(1, 1, 1, 1), -1, -1);
		WNPanel.addAComponent(urlLCB, null, 0, 0, 1, 1, 1, 0,
				GBCFill.HORIZONTAL, GBCAnchor.NORTH);
		WNPanel.addAComponent(filepathLCB, null, 0, 1, 1, 1, 1, 0,
				GBCFill.HORIZONTAL, GBCAnchor.SOUTH);

		WSPanel.setGBC(new Insets(0, 0, 0, 0), -1, -1);
		WSPanel.addAComponent(srcTP, new Dimension(475, 600), 0, 0, 1, 1, 1, 1,
				GBCFill.BOTH, GBCAnchor.SOUTH);

		ENPanel.setGBC(new Insets(1, 1, 1, 1), -1, -1);
		ENPanel.addAComponent(toolLCB, null, 0, 0, 1, 1, 1, 0,
				GBCFill.HORIZONTAL, GBCAnchor.NORTH);
		ENPanel.addAComponent(titleLCB, null, 0, 1, 1, 1, 1, 0,
				GBCFill.HORIZONTAL, GBCAnchor.SOUTH);

		ESPanel.setGBC(new Insets(0, 0, 0, 0), -1, -1);
		ESPanel.addAComponent(contentTP, new Dimension(475, 600), 0, 0, 1, 1,
				1, 1, GBCFill.BOTH, GBCAnchor.SOUTH);
	}

	// for JCombox'list look style
	private class FileCellRenderer extends JLabel implements ListCellRenderer {
		public FileCellRenderer() {
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setText(value.toString());
			setBackground(isSelected ? new Color(255, 69, 0) : Color.white);
			setForeground(Color.black);
			return this;
		}
	}

	private class DirCellRenderer extends JLabel implements ListCellRenderer {
		public DirCellRenderer() {
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setText(value.toString());
			setBackground(isSelected ? new Color(255, 69, 0) : Color.white);
			setForeground(isSelected ? Color.black : Color.green);
			return this;
		}
	}

	private class MyTextEditorModel extends AbstractTextEditorModel {
		public MyTextEditorModel(TextEditor textEditor) {
			// need test
			textEditor.super();
		}

		public void ContentIsChanged() {
			// TODO Auto-generated method stub
			// do nothing here
		}

		public void SaveAtPath(String content, String filePath) {
			// TODO Auto-generated method stub

			DLDEToolModel.SaveContentAt(content, filePath);
		}

		public String UIGetSavePath() {
			// TODO Auto-generated method stub
			saveChooser.setFileFilter(new FileNameExtensionFilter("*."+GetUITitle(), GetUITitle()));
			if (saveChooser.showSaveDialog(getParent()) == JFileChooser.APPROVE_OPTION) {

				String filePath = saveChooser.getSelectedFile()
						.getAbsolutePath();
				
				
				if (!filePath.endsWith("."+GetUITitle())) {
					filePath += ("."+GetUITitle());
				}
				
				return filePath;
			}
			return null;
		}
	}

	private void MakeControl() {
		//DLDEToolModel.mtemContent = new MyTextEditorModel(contentEditor);
		contentEditor.setTestEditorModel(new MyTextEditorModel(contentEditor));
		contentEx1Editor.setTestEditorModel(new MyTextEditorModel(contentEx1Editor));

		this.urlLCB.getConfirm().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox urlComboBox = urlLCB.getComboBox();
				int Count = urlComboBox.getItemCount(), i = 0;
				String currentNet = urlComboBox.getEditor().getItem()
						.toString();
				if (currentNet.equals("") || currentNet == null) {
					System.out
							.println("cannot apply url :" + currentNet + "\n");
					return;
				}
				for (i = 0; i < Count; i++) {
					if (urlComboBox.getItemAt(i).toString().equals(currentNet)) {
						System.out.println("find url :" + currentNet + "\n");
						break;
					}
				}
				if (i == Count) {
					System.out.println("add url :" + currentNet + "\n");
					urlComboBox.addItem(currentNet);
				}
				switch (DLDEToolModel.SetWithUrl(currentNet)) {
				case 0:
					ReSetContent();
					break;
				case 1:
					SetContent();
					JComboBox charSetComboBox = toolLCB.getComboBox();
					if (!charSetComboBox.getSelectedItem().toString()
							.equals(DLDEToolModel.htmlFile.getCharSet())) {
						int count = charSetComboBox.getItemCount();
						for (i = 0; i < count; i++) {
							if (charSetComboBox.getItemAt(i).toString()
									.equals(DLDEToolModel.htmlFile.getCharSet())) {
								break;
							}
						}
						if (i == count) {
							charSetComboBox.addItem(DLDEToolModel.htmlFile.getCharSet());
						}
						charSetComboBox.setSelectedIndex(i);
					}
					break;
				default:
					;
				}
			}
		});

		openAction = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox pathComboBox = filepathLCB.getComboBox();

				if (openChooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
					if (openChooser.getSelectedFile().isDirectory()) {

						pathComboBox.setRenderer(new DirCellRenderer());
						pathComboBox.removeAllItems();
						File parentDir = openChooser.getSelectedFile();

						for (File f : parentDir.listFiles()) {
							if (f.isDirectory()) {
								continue;
							} else if (f.getName().endsWith(".htm")
									|| f.getName().endsWith(".html")) {
								pathComboBox.addItem(f.getAbsoluteFile());
							}
						}

						if (pathComboBox.getItemCount() > 0) {
							switch (DLDEToolModel.SetWithFile(pathComboBox.getItemAt(0)
									.toString())) {
							case 0:
								ReSetContent();
								break;
							case 1:
								SetContent();
								break;
							default:
								;
							}
						}

					} else {
						pathComboBox.setRenderer(new FileCellRenderer());

						String currentFile = openChooser.getSelectedFile()
								.toString();
						pathComboBox.removeAllItems();
						pathComboBox.addItem(currentFile);
						switch (DLDEToolModel.SetWithFile(currentFile)) {
						case 0:
							ReSetContent();
							break;
						case 1:
							SetContent();
							break;
						default:
							;
						}
					}
				}
			}
		};
		this.filepathLCB.getConfirm().addActionListener(openAction);

		this.filepathLCB.getComboBox().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox pathComboBox = filepathLCB.getComboBox();

				// when open file is a directory,this works
				if (pathComboBox.getItemCount() > 1) {
					switch (DLDEToolModel.SetWithFile(pathComboBox.getSelectedItem()
							.toString())) {
					case 0:
						ReSetContent();
						break;
					case 1:
						SetContent();
						break;
					default:
						;
					}
				}
			}

		});

		this.toolLCB.getComboBox().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DLDEToolModel.SetWithCharSet(toolLCB.getComboBox()
						.getSelectedItem().toString());
				SetContent();
			}

		});

		saveAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				((TextEditor) contentTP.getSelectedComponent()).getTestEditorModel().UISaveContent();
			}
		};

		this.toolLCB.getConfirm().addActionListener(saveAction);

		this.titleLCB.getConfirm().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox pathComboBox = filepathLCB.getComboBox();
				if (pathComboBox.getItemCount() > 1) {
					if (pathComboBox.getSelectedIndex() < (pathComboBox
							.getItemCount() - 1)) {
						// invoke filepathLCB.getComboBox()'s ActionListener
						pathComboBox.setSelectedIndex(pathComboBox
								.getSelectedIndex() + 1);
					} else {
						JOptionPane.showMessageDialog(null,
								"no more files in the dirctory!");
					}
				}
			}
		});

	}
	private void SetContent() {
		if(DLDEToolModel.htmlFile.getSource() == null)
		{
			return;
		}
		else
		{
			srcEditor.SetContent(DLDEToolModel.htmlFile.getSource());
			contentEditor.SetContent(DLDEToolModel.htmlFile.getContent());
			contentEx1Editor.SetContent(DLDEToolModel.htmlFile.getContent(HtmlFile.ParseMethod.BlockExtractor));			
			
			titleLCB.getTextArea().setText(DLDEToolModel.htmlFile.getTitle());
		}
	}

	private void ReSetContent() {
		srcEditor.SetContent("");
		contentEditor.SetContent("");
		contentEditor.getTestEditorModel().ReSetSaved();
		contentEx1Editor.SetContent("");
		contentEx1Editor.getTestEditorModel().ReSetSaved();
		titleLCB.getTextArea().setText("");
	}

	private static class DLDEToolModel {
		private static String currentPath = null;
		private static HtmlFile htmlFile = new HtmlFile();
		//private static MyTextEditorModel mtemContent = null;
		//store history saved contents
		@SuppressWarnings("unchecked")
		private static HashMap history = new HashMap();

		// to ask whether need and save old then open new if needed
		public static int SetWithUrl(String url) {
//			switch (AskForSave()) {
//			case -1:
//				return -1;
//			case 1:
//				if(!mtemContent.UISaveContent())
//				{
//					return -1;
//				}
//				break;
//			default:
//				;
//			}
//			mtemContent.ReSetSaved();
			Pattern p = Pattern
					.compile("(https?://)?((?:[\\da-z.-]+).(?:[a-z.]{2,6}))((?:[/\\w .-]*)*/?)");
			Matcher m = p.matcher(url);
			System.out.println("2:" + url + "\n");
			if (m.find()) {
				if (m.group(2) == null) {
					JOptionPane.showMessageDialog(null, "invaild url!");
					return 0;
				} else {
					try {
						// htmlFile = new HtmlFile();
						htmlFile.GetHtmlFileFromNet(m.group(2), m.group(3));
						currentPath = url;
						return 1;
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1.getMessage());
						e1.printStackTrace();
						return 0;
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "invaild url!");
				return 0;
			}
		}

		// to ask whether need and save old then open new if needed
		public static int SetWithFile(String filePath) {
//			switch (AskForSave()) {
//			case -1:
//				return -1;
//			case 1:
//				if(!mtemContent.UISaveContent())
//				{
//					return -1;
//				}
//				break;
//			default:
//				;
//			}
//			mtemContent.ReSetSaved();
			try {
				if (filePath == null) {
					JOptionPane.showMessageDialog(null,
							"no html file is found!");
				}
				String charSet = null;
				htmlFile.GetHtmlFileFromLocalFile(filePath, charSet);
				currentPath = filePath;
				return 1;

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, e1.getMessage());
				e1.printStackTrace();
				return 0;
			}
		}

//		private static int AskForSave() {
//			if (!mtemContent.IsSaved()) {
//				int result = JOptionPane.showConfirmDialog(new JFrame(),
//						"Do you want to save the current content?",
//						"need save ?", JOptionPane.YES_NO_CANCEL_OPTION);
//				if (result == JOptionPane.YES_OPTION) {
//					return 1;
//				} else if (result == JOptionPane.NO_OPTION) {
//					return 0;
//				} else if (result == JOptionPane.CANCEL_OPTION) {
//					return -1;
//				} else {
//					return 0;
//				}
//			} else {
//				return 1;
//			}
//		}

		public static void SetWithCharSet(String charSet) {
			try {
				htmlFile.setCharSet(charSet);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@SuppressWarnings("unchecked")
		
		public static void SaveContentAt(String content, String filePath) {
			try {
				java.io.FileOutputStream out = new java.io.FileOutputStream(
						filePath);

				java.io.BufferedWriter bw = new java.io.BufferedWriter(
						new java.io.OutputStreamWriter(out, "utf-8"));

				char chars[] = content.toCharArray();
				bw.write(chars, 0, chars.length);
				bw.flush();
				bw.close();
				history.put(currentPath, filePath);

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}


}
