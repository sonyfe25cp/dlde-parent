//package panel;
//
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.io.IOException;
//import java.io.StringReader;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JTextArea;
//import javax.swing.UIManager;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//
//import mycomponents.GBLPanel;
//
//import org.wltea.analyzer.IKSegmentation;
//import org.wltea.analyzer.Lexeme;
//
//public class AnalyzerPanel extends GBLPanel {
//
//	private JTextArea inputArea, ikArea_true, ikArea_false;
//
//	private JLabel input_label, ik_true_label, ik_false_label;
//
//	private JButton button;
//
//	public AnalyzerPanel() {
//
//		input_label = new JLabel("输入句子");
//		input_label.setSize(300, 10);
//		ik_true_label = new JLabel("IK分词(最大):");
//		ik_true_label.setSize(30, 10);
//		ik_false_label = new JLabel("IK分词(最细):");
//		ik_false_label.setSize(30, 10);
//
//		inputArea = new JTextArea("input the Words", 2, 40);
//		inputArea.setLineWrap(true);
//		ikArea_true = new JTextArea("最大最长匹配", 2, 40);
//		ikArea_true.setLineWrap(true);
//		ikArea_false = new JTextArea("最小粒度匹配", 2, 40);
//		ikArea_false.setLineWrap(true);
//
//		button = new JButton("分词");
//		init();
//		action();
//	}
//
//	public void init() {
//		inputArea.setLocation(10, 5);
//		inputArea.setVisible(true);
//
//		this.addAComponent(input_label, null, 0, 0, 1, 1,0.5, 0,
//				GBLPanel.GBCFill.HORIZONTAL,GBLPanel.GBCAnchor.NORTH);
//		this.addAComponent(inputArea, null, 1, 0, 3, 1,0.5, 0,
//				GBLPanel.GBCFill.HORIZONTAL,GBLPanel.GBCAnchor.NORTH);
//		this.addAComponent(ik_true_label, null, 0, 1, 1, 1,0.5, 0,
//				GBLPanel.GBCFill.HORIZONTAL,GBLPanel.GBCAnchor.NORTH);
//		this.addAComponent(ikArea_true, null, 1, 1, 3, 1,0.5, 0,
//				GBLPanel.GBCFill.HORIZONTAL,GBLPanel.GBCAnchor.NORTH);
//		this.addAComponent(ik_false_label, null, 0, 2, 1, 1,0.5, 0,
//				GBLPanel.GBCFill.HORIZONTAL,GBLPanel.GBCAnchor.NORTH);
//		this.addAComponent(ikArea_false, null, 1, 2, 3, 1,0.5, 0,
//				GBLPanel.GBCFill.HORIZONTAL,GBLPanel.GBCAnchor.NORTH);
//		this.addAComponent(button, null, 0, 3, 1, 1,0.5, 0,
//				GBLPanel.GBCFill.HORIZONTAL,GBLPanel.GBCAnchor.NORTH);
//
//	}
//
//	public void action() {
//		button.addChangeListener(new ChangeListener() {
//			public void stateChanged(ChangeEvent e) {
//				String words = inputArea.getText();
//				String ik_true;
//				try {
//					ik_true = ikAnalyzer(words, true);
//					String ik_false = ikAnalyzer(words, false);
//
//					ikArea_true.setText(ik_true);
//					ikArea_false.setText(ik_false);
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			}
//		});
//	}
//
//	public String ikAnalyzer(String words, boolean flag) throws IOException {
//		IKSegmentation ik = new IKSegmentation(new StringReader(words), flag);
//		Lexeme lex = ik.next();
//		String segs = "";
//		while (lex != null) {
//			String word = lex.getLexemeText() + " ";
//			lex = ik.next();
//			segs += word;
//		}
//		return segs;
//	}
//
//	public static void main(String[] args) {
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//
//		JFrame frame = new JFrame();
//		frame.setSize(400, 300);
//		frame.add(new AnalyzerPanel());
//		frame.setVisible(true);
//
//		frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
//	}
//
//}
