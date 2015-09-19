/**
 * 작성일 : 2015. 9. 18.
 * 작성자 : 쥬욕
 * 설  명 : 
 */
package manager;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

/**
 * 윈도우의 슈퍼 클래스
 * @author 쥬욕
 *
 */
@SuppressWarnings("serial")
public abstract class Window extends JInternalFrame {

	/**
	 * 생성자에서 기본 윈도우 모양을 구성한다.
	 * @param windowName 윈도우 이름
	 * @param x x좌표
	 * @param y y좌표
	 * @param width 가로크기
	 * @param height 세로크기
	 * @param resizable 리사이즈 가능 여부
	 * @param closable 닫기 가능 여부
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
	 * 해당 윈도우의 초기화할 내용을 기술하세요.
	 */
	public abstract void initialize();

}
