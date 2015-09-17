/**
 * �ۼ��� : 2015. 9. 9.
 * �ۼ��� : ���
 * ��  �� : 
 */
package manager;

import java.awt.Color;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Log ������ Ŭ����
 * @author ���
 *
 */
@SuppressWarnings("serial")
public class ServerLogWindow extends JInternalFrame {
	
	private JTextPane textPane = null;
	private JScrollPane scrollPane = null;
	
	/**
	 * ������
	 * @param windowName ������ �̸�
	 * @param x x��ǥ
	 * @param y y��ǥ
	 * @param width ����ũ��
	 * @param height ����ũ��
	 * @param resizable �������� ���� ����
	 * @param closable �ݱ� ���� ����
	 */
	public ServerLogWindow(String windowName, int x, int y, int width, int height, boolean resizable, boolean closable){
		
		super();
		
		initialize(windowName, x, y, width, height, resizable, closable);
	}
	
	/**
	 * �α������� �ʱ�ȭ
	 * @param windowName ������ �̸�
	 * @param x x��ǥ
	 * @param y y��ǥ
	 * @param width ����ũ��
	 * @param height ����ũ��
	 * @param resizable �������� ���� ����
	 * @param closable �ݱ� ���� ����
	 */
	public void initialize(String windowName, int x, int y, int width, int height, boolean resizable, boolean closable){
		
		this.title = windowName;
		this.closable = closable;
		this.isMaximum = false;
		this.maximizable = true;
		this.resizable = resizable;
		this.iconable = true;
		this.isIcon = false;
		
		setSize(width, height);
		setBounds(x, y, width, height);
		setVisible(true);
		
		this.frameIcon = new ImageIcon("");
		setRootPaneCheckingEnabled(true);
		
		/** �׼� **/
		addInternalFrameListener(new InternalFrameAdapter() {
			
			public void internalFrameClosing(InternalFrameEvent e){
				
				try {
					
					File f = null;
					String sTemp = "";
					
					synchronized (Manager.lock) {
						sTemp = Manager.getDate();
						StringTokenizer s = new StringTokenizer(sTemp, " ");
						Manager.date = s.nextToken();
						Manager.time = s.nextToken();
						
						f = new File("ServerLog/" + Manager.date);
						if (!f.exists()) {
							f.mkdir();
						}
						Manager.flush(textPane, "[" + Manager.time + "] " + title + " Window", Manager.date);
						sTemp = null;
						Manager.date = null;
						Manager.time = null;
						s = null;
						f = null;
					}
					
					textPane.setText("");
					
				} catch (Exception ex) {
					// TODO: handle exception
				}
			}
		}); /** �׼� �� **/
		
		updateUI();
		
		textPane = new JTextPane();
		scrollPane = new JScrollPane(textPane);
		
		textPane.setEditable(false);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAutoscrolls(true);
		add(scrollPane);
		
		Style style = null;
		style = textPane.addStyle("Black", null);
		StyleConstants.setForeground(style, Color.black);
		style = textPane.addStyle("Red", null);
		StyleConstants.setForeground(style, Color.red);
		style = textPane.addStyle("Orange", null);
		StyleConstants.setForeground(style, Color.orange);
		style = textPane.addStyle("Yellow", null);
		StyleConstants.setForeground(style, Color.yellow);
		style = textPane.addStyle("Green", null);
		StyleConstants.setForeground(style, Color.green);
		style = textPane.addStyle("Blue", null);
		StyleConstants.setForeground(style, Color.blue);
		style = textPane.addStyle("DarkGray", null);
		StyleConstants.setForeground(style, Color.darkGray);
		style = textPane.addStyle("Pink", null);
		StyleConstants.setForeground(style, Color.pink);
		style = textPane.addStyle("Cyan", null);
		StyleConstants.setForeground(style, Color.cyan);
		style = textPane.addStyle("Purle", null);
		StyleConstants.setForeground(style, Color.magenta);
	}
	
	/**
	 * �α� �����쿡 �ؽ�Ʈ ���
	 * @param msg ��� �޼���
	 * @param color �÷� ����
	 */
	public void append(String msg, String color){
		
		StyledDocument doc = textPane.getStyledDocument();
		
		try {
			
			doc.insertString(doc.getLength(), msg, textPane.getStyle(color));
			//scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			textPane.setCaretPosition(textPane.getDocument().getLength());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ý��� �α������� ����
	 */
	public void savelog(){
		
		savelog("[" + Manager.time + "] 0.�ý���");
	}
	
	/**
	 * �α� ������ ����
	 * @param strLog �α� ���ڿ�
	 */
	public void savelog(String strLog){
		
		try {
			
			File f = null;
			String sTemp = "";
			
			synchronized (Manager.lock) {
				
				sTemp = Manager.getDate();
				StringTokenizer s = new StringTokenizer(sTemp, " ");
				Manager.date = s.nextToken();
				Manager.time = s.nextToken();
				
				f = new File("ServerLog/" + Manager.date);
				if (!f.exists()) {
					f.mkdir();
				}
				Manager.flush(textPane, strLog, Manager.date);
				sTemp = null;
				Manager.date = null;
				Manager.time = null;
				s = null;
				f = null;
			}
			
			textPane.setText("");
			
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}
}
