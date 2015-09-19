/**
 * 작성일 : 2015. 9. 9.
 * 작성자 : 쥬욕
 * 설  명 : 
 */
package manager;

/**
 * 유저 정보 윈도우 클래스
 * @author 쥬욕
 *
 */
@SuppressWarnings("serial")
public class ServerUserInfoWindow extends Window{

	/**
	 * 생성자
	 * @param windowName 윈도우 이름
	 * @param x x좌표
	 * @param y y좌표
	 * @param width 가로크기
	 * @param height 세로크기
	 * @param resizable 리사이즈 가능 여부
	 * @param closable 닫기 가능 여부
	 */
	public ServerUserInfoWindow(String title, int x, int y, int width, int height, boolean resizable, boolean closable) {
		super(title, x, y, width, height, resizable, closable);
		
		initialize();
	}

	/**
	 * 유저 정보 윈도우 초기화
	 */
	@Override
	public void initialize() {
		
				
	}

}
