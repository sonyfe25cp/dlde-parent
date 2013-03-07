/**
 * 
 */
package mycomponents;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 自定义文本组件，使用此类时，应当自定义Model实现TextEditorModel（可以继承AbstractTextEditorModel），然后调用setTestEditorModel进行设置
 * @author hz
 *
 */
@SuppressWarnings("serial")
public class TextEditor extends GBLPanel{
	private JTextPane content = null;
	private String title = null;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JTextPane getContent() {
		return content;
	}
	private JLabel state = null;
	private TextEditorModel testEditorModel = null;
	public TextEditorModel getTestEditorModel() {
		return testEditorModel;
	}
	private boolean isSaved = true;
	private String savePath = null;
	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
	private boolean editable = true;
	
	/**
	 * @param 
	 */
	public TextEditor()
	{
		super();
		content = new JTextPane();
		state = new JLabel();
		state.setText("0"+" chars");
		testEditorModel = null;
		savePath = null;
		
		MakeView();
		MakeControl();
	}
	
	private void MakeView()
	{
		//test auto linewrap and scroll
		JScrollPane scroll = new JScrollPane(content);
		// auto display
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		this.addAComponent(scroll, new Dimension(500,100), 0, 0, 1, 1, 1, 1, GBLPanel.GBCFill.BOTH, GBLPanel.GBCAnchor.NORTH);
		this.addAComponent(state, null, 0, 1, 1, 1, 1, 0, GBLPanel.GBCFill.HORIZONTAL, GBLPanel.GBCAnchor.SOUTH);
	}
	private void MakeControl()
	{
		content.getDocument().addDocumentListener(new DocumentListener(){

			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}

			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				state.setText(content.getText().length()+" chars");
				if(testEditorModel!= null)
				{
					testEditorModel.ContentIsChanged();
				}
				if(editable)
				{
					isSaved = false;
				}
			}

			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				state.setText(content.getText().length()+" chars");
				if(testEditorModel!= null)
				{
					testEditorModel.ContentIsChanged();
				}
				if(editable)
				{
					isSaved = false;
				}
			}
			
		});
	}
	/**
	 * 设置文本
	 */
	public void SetContent(String _content)
	{
		content.setText(_content);
		content.setCaretPosition(0);
		if(editable)
		{
			isSaved = false;
		}
	}
	public void setTestEditorModel(TextEditorModel testEditorModel) {
		this.testEditorModel = testEditorModel;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	/**
	 * 设置文本是否允许编辑
	 */
	public void setEditable(boolean editable) {
		content.setEditable(editable);
		this.editable = editable;
	}

	/**
	 *  需要实现的接口
	 */
	public interface TextEditorModel
	{
		
		/**
		 *当文本内容更改时应当做的工作
		 */
		public void ContentIsChanged();
		/**
		 * if the value of "editable" is false,this function will do nothing .
		 */
		public boolean IsSaved();
		public void ReSetSaved();
		public boolean UISaveContent();
		public String UIGetSavePath();
		public void SaveAtPath(String content,String filePath);
		public String GetUITitle();
	}
	/**
	 *  抽象类，提供了TextEditorModel接口的部分默认实现
	 */
	public abstract class AbstractTextEditorModel implements TextEditorModel
	{
		public AbstractTextEditorModel(){}
		/* (non-Javadoc)
		 * @see mycomponents.TextEditor.TextEditorModel#UISaveContent()
		 */
		public boolean IsSaved()
		{
			if(!editable)
			{
				return true;
			}
			else
			{
				return isSaved;
			}
		}
		public void ReSetSaved()
		{
			isSaved = true;
			savePath = null;
		}
		public boolean UISaveContent()
		{
			if(!editable)
			{
				return true;
			}
			else
			{
				if (!isSaved) {
					if (savePath == null) {
						savePath = UIGetSavePath();
						if(savePath == null)
						{
							return false;
						}
					}

					SaveAtPath(content.getText(), savePath);
					isSaved = true;
				}
			}
			return true;
		}
		public String GetUITitle()
		{
			return title;
		}
	}
}
