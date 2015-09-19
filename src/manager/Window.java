/**
 * �ۼ��� : 2015. 9. 18.
 * �ۼ��� : ���
 * ��  �� : 
 */
package manager;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

/**
 * �������� ���� Ŭ����
 * @author ���
 *
 */
@SuppressWarnings("serial")
public abstract class Window extends JInternalFrame {

	/**
	 * �����ڿ��� �⺻ ������ ����� �����Ѵ�.
	 * @param windowName ������ �̸�
	 * @param x x��ǥ
	 * @param y y��ǥ
	 * @param width ����ũ��
	 * @param height ����ũ��
	 * @param resizable �������� ���� ����
	 * @param closable �ݱ� ���� ����
	 */
	public Window(String title, int x, int y, int width, int height, boolean resizable, boolean closable){
		
		super();
		
		this.title = title;
		this.closable = closable;
		this.isMaximum = false;
		this.maximizable = false;
		this.resizable = resizable;
		this.iconable = true;
		this.isIcon = false;
		
		setSize(width, height);
		setBounds(x, y, width, height);
		setVisible(true);
		frameIcon = new ImageIcon("");
		setRootPaneCheckingEnabled(true);
		
		updateUI();
	}
	
	/**
	 * �ش� �������� �ʱ�ȭ�� ������ ����ϼ���.
	 */
	public abstract void initialize();

}
