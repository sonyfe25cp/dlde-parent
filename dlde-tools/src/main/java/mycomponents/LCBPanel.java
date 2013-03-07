package mycomponents;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LCBPanel extends GBLPanel{
	public static enum TextComponent{
		COMBOBOX,TEXTAREA
	}
	JLabel name;
	JComboBox comboBox;
	JTextField textfeild;
	public JTextField getTextArea() {
		return textfeild;
	}
	public JComboBox getComboBox() {
		return comboBox;
	}
	JButton confirm;
	public JButton getConfirm() {
		return confirm;
	}
	public LCBPanel (String _name ,Dimension _TextComponentSize,String _confirmBuutonName,TextComponent textComponent)
	{
		name =  new JLabel(_name, JLabel.LEFT);
		//max for five chars
		name.setPreferredSize(new Dimension(45,_TextComponentSize.height));
		if(textComponent == TextComponent.COMBOBOX)
		{
			comboBox = new JComboBox();
			textfeild = null;
		}
		else
		{
			textfeild = new JTextField();
			comboBox = null;
		}
		confirm = new JButton(_confirmBuutonName);
		//max for six chars
		confirm.setPreferredSize(new Dimension(55,_TextComponentSize.height));
		MakeView(_TextComponentSize,textComponent);
	}
	private void MakeView(Dimension _TextComponentSize,TextComponent textComponent)
	{
		this.addAComponent(name,null, 0, 1, 1, 1, 0, 0,
				GBLPanel.GBCFill.NONE,GBLPanel.GBCAnchor.WEST);
		if(textComponent == TextComponent.COMBOBOX)
		{
			this.addAComponent(comboBox,_TextComponentSize, 1, 1, 1, 1, 1, 0,
					GBLPanel.GBCFill.HORIZONTAL,GBLPanel.GBCAnchor.CENTER);
		}
		else
		{
			this.addAComponent(textfeild,_TextComponentSize, 1, 1, 1, 1, 1, 0,
					GBLPanel.GBCFill.HORIZONTAL,GBLPanel.GBCAnchor.CENTER);
		}

		this.addAComponent(confirm,null, 2, 1, 1, 1, 0, 0,
				GBLPanel.GBCFill.NONE,GBLPanel.GBCAnchor.EAST);
	}
}
