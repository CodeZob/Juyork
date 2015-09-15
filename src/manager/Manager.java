/**
 * 작성일 : 2015. 9. 8.
 * 작성자 : 쥬욕
 * 설  명 : 
 */
package manager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;



/**
 * 서버 관리 매니저 클래스
 * @author 쥬욕
 */
public class Manager {
	
	private static Manager instance = null;
	
	public boolean isServerStarted;
	
	// 보여질 윈도우 구성
	public JFrame jFrame = null;	// 프레임
	private Container jContainer = null;	// 화면 구성 핸들
	private JMenuBar jMenuBar = null;		// 메뉴바
	private BorderLayout jBorderLayout = new BorderLayout();	// 레이아웃
	public JDesktopPane jDesktopPane = new JDesktopPane();	// 화면바탕
	
	// 서버로그
	public ServerLogWindow jSystemLogWindow = null;	// 시스템
	
	// 메세지
	public static final String NoServerStartMSG = "서버가 실행되지 않았습니다.";
	public static final String OpenStandByOn = "오픈대기 모드를 켜시겠습니까?";
	public static final String OpenStandByOff = "오픈대기 모드를 끄시겠습니까?";
	public static final String RealExitServer = "서버를 종료하시겠습니까?";
	public static final String UserDelete = "일주일 동안 접속하지 않은 캐릭터를 삭제하시겠습니까?";
	public static final String AllBuffMSG = "전체버프를 시전하시겠습니까?";
	

	

	/**
	 * 프로그램 시작!!
	 * @param args
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				Manager.create();
			}
		});
	}
	
	/**
	 * 외부 생성 금지 싱글턴 방식
	 */
	private Manager() {
		
		initialize();
	}
	
	/**
	 * 매니저 싱글턴 생성
	 */
	private static void create(){
		
		if(instance != null)
			return;
		
		instance = new Manager();
	}
	
	/**
	 * 매니저 프로그램 초기화
	 * 보여질 윈도우를 셋팅한다.
	 */
	private void initialize(){
		
		try{
			
			//UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");	// UI 스타일 검정
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");	// UI 맥스타일
			JFrame.setDefaultLookAndFeelDecorated(true);
			
			// 프레임 및 화면 구성.
			if(jFrame == null){
				
				jFrame = new JFrame();
				jFrame.setIconImage(new ImageIcon("img\\icon.gif").getImage());
				jFrame.setSize(1920, 1050);
				jFrame.setVisible(true);
				jFrame.setTitle("::: 쥬욕서버 매니져 프로그램 :::");
				jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				jContainer = jFrame.getContentPane();
				jContainer.setLayout(jBorderLayout);
				jContainer.add("Center", jDesktopPane);
			}
			
			/** 메뉴구성 */
			if(jMenuBar == null){
				
				jMenuBar = new JMenuBar();
				jFrame.setJMenuBar(jMenuBar);
				
				JMenu[] jMenu = new JMenu[5];
				final String[] menuNames = {"파일(F)", "모니터(M)", "도우미(H)", "리로드(R)", "정보(I)"};
				final int[] eventValues = {KeyEvent.VK_F, KeyEvent.VK_M, KeyEvent.VK_H, KeyEvent.VK_R, KeyEvent.VK_I};
				
				for(int i = 0; i < jMenu.length; ++i){
					
					jMenu[i] = new JMenu(menuNames[i]);
					jMenu[i].setMnemonic(eventValues[i]);
				}
				
				/** 파일 메뉴 구성 */
				JMenuItem standByOn = new JMenuItem("오픈대기 켜기");
				standByOn.setAccelerator(KeyStroke.getKeyStroke('Y', InputEvent.CTRL_DOWN_MASK));
				standByOn.addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if(isServerStarted){
							
							if(questMsg(OpenStandByOn) == 0){
								
								// 추가할 부분
							}
						}else{
							
							errorMsg(NoServerStartMSG);
						}
					}
				});	// 오픈대기 켜기
				
				JMenuItem standByOff = new JMenuItem("오픈대기 끄기");
				standByOff.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
				standByOff.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {

						if(isServerStarted){
							
							if(questMsg(OpenStandByOff) == 0){
								
								// 추가할 부분
							}
						}else{
							
							errorMsg(NoServerStartMSG);
						}
					}
				});	// 오픈대기 끄기
				
				JMenuItem serverSet = new JMenuItem("서버 설정");
				serverSet.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
				serverSet.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						// 추가할 부분						
					}
				});	// 서버 설정
				
				JMenuItem serverSave = new JMenuItem("서버 저장");
				serverSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
				serverSave.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {

						// 추가할 부분			
					}
				});	// 서버 저장
				
				JMenuItem serverExit = new JMenuItem("서버 종료");
				serverExit.setAccelerator(KeyStroke.getKeyStroke(',', InputEvent.CTRL_DOWN_MASK));
				serverExit.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if(questMsg(RealExitServer) == 0){
							
							// 추가할 부분
						}
					}
				});	// 서버 종료
				
				JMenuItem serverNowExit = new JMenuItem("바로 종료");
				serverNowExit.setAccelerator(KeyStroke.getKeyStroke('.', InputEvent.CTRL_DOWN_MASK));
				serverNowExit.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {

						if(questMsg(RealExitServer) == 0){
							
							// 추가할 부분
						}
					}
				});	// 바로 종료
				
				// 파일 메뉴에 자식메뉴아이템 추가
				jMenu[0].add(standByOn);
				jMenu[0].add(standByOff);
				jMenu[0].add(serverSet);
				jMenu[0].add(serverSave);
				jMenu[0].add(serverExit);
				jMenu[0].add(serverNowExit);
				/** 파일 메뉴 구성 끝 */
				
				/** 모니터 메뉴 구성 */
				JMenuItem LoginLogWindow = new JMenuItem("로그인");
				LoginLogWindow.setAccelerator(KeyStroke.getKeyStroke('0', InputEvent.CTRL_DOWN_MASK));
				LoginLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 로그인
				
				JMenuItem worldChatLogWindow = new JMenuItem("전체채팅");
				worldChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('1', InputEvent.CTRL_DOWN_MASK));
				worldChatLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 전체채팅
				
				JMenuItem normalChatLogWindow = new JMenuItem("일반채팅");
				normalChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('2', InputEvent.CTRL_DOWN_MASK));
				normalChatLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 일반채팅
				
				JMenuItem whisperChatLogWindow = new JMenuItem("귓속말채팅");
				whisperChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('3', InputEvent.CTRL_DOWN_MASK));
				whisperChatLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 귓속말채팅
				
				JMenuItem clanChatLogWindow = new JMenuItem("혈맹채팅");
				clanChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('4', InputEvent.CTRL_DOWN_MASK));
				clanChatLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 혈맹채팅
				
				JMenuItem partyChatLogWindow = new JMenuItem("파티채팅");
				partyChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('5', InputEvent.CTRL_DOWN_MASK));
				partyChatLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 파티채팅
				
				JMenuItem tradeChatLogWindow = new JMenuItem("장사채팅");
				tradeChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('6', InputEvent.CTRL_DOWN_MASK));
				tradeChatLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 장사채팅
				
				JMenuItem wareHouseLogWindow = new JMenuItem("창고");
				wareHouseLogWindow.setAccelerator(KeyStroke.getKeyStroke('7', InputEvent.CTRL_DOWN_MASK));
				wareHouseLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 창고
				
				JMenuItem tradeLogWindow = new JMenuItem("거래");
				tradeLogWindow.setAccelerator(KeyStroke.getKeyStroke('8', InputEvent.CTRL_DOWN_MASK));
				tradeLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 거래
				
				JMenuItem enchantLogWindow = new JMenuItem("인챈트");
				enchantLogWindow.setAccelerator(KeyStroke.getKeyStroke('9', InputEvent.CTRL_DOWN_MASK));
				enchantLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 인챈트
				
				JMenuItem dropItemLogWindow = new JMenuItem("아이템 드랍/삭제");
				dropItemLogWindow.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));
				dropItemLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 아이템 드랍/삭제
				
				JMenuItem bugLogWindow = new JMenuItem("버그모니터");
				bugLogWindow.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_DOWN_MASK));
				bugLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 버그모니터
				
				JMenuItem bossLogWindow = new JMenuItem("보스/던전 타이머");
				bossLogWindow.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));
				bossLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 보스/던전 타이머
				
				JMenuItem accountLogWindow = new JMenuItem("계정생성");
				accountLogWindow.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
				accountLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 계정생성
				
				JMenuItem commandLogWindow = new JMenuItem("GM커맨드");
				commandLogWindow.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.CTRL_DOWN_MASK));
				commandLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// GM커맨드
				
				JMenuItem warLogWindow = new JMenuItem("공성 모니터");
				warLogWindow.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
				warLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 공성 모니터
				
				JMenuItem multiChatLogWindow = new JMenuItem("멀티채팅 모니터");
				multiChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('\\', InputEvent.CTRL_DOWN_MASK));
				multiChatLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 멀티채팅 모니터
				
				JMenuItem quantityItemLogWindow = new JMenuItem("수량성 아이템 모니터");
				quantityItemLogWindow.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.CTRL_DOWN_MASK));
				quantityItemLogWindow.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 수량성 아이템 모니터
				
				jMenu[1].add(LoginLogWindow);
				jMenu[1].add(worldChatLogWindow);
				jMenu[1].add(normalChatLogWindow);
				jMenu[1].add(whisperChatLogWindow);
				jMenu[1].add(clanChatLogWindow);
				jMenu[1].add(partyChatLogWindow);
				jMenu[1].add(tradeChatLogWindow);
				jMenu[1].add(wareHouseLogWindow);
				jMenu[1].add(tradeLogWindow);
				jMenu[1].add(enchantLogWindow);
				jMenu[1].add(dropItemLogWindow);
				jMenu[1].add(bugLogWindow);
				jMenu[1].add(bossLogWindow);
				jMenu[1].add(accountLogWindow);
				jMenu[1].add(commandLogWindow);
				jMenu[1].add(warLogWindow);
				jMenu[1].add(multiChatLogWindow);
				jMenu[1].add(quantityItemLogWindow);
				
				/** 모니터 메뉴 구성 끝 */
				
				/** 도우미 메뉴 구성 */
				JMenuItem characterDelete = new JMenuItem("캐릭터 삭제");
				characterDelete.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));
				characterDelete.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
					
						if(isServerStarted){
							
							if(questMsg(UserDelete) == 0){
								
								// 추가할 부분
							}
						}else{
							
							errorMsg(NoServerStartMSG);
						}
						
					}
				});	// 캐릭터 삭제
				
				JMenuItem allBuff = new JMenuItem("전체버프");
				allBuff.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
				allBuff.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if(isServerStarted){
							
							if(questMsg(AllBuffMSG) == 0){
								
								
							}
						}else{
							
							errorMsg(NoServerStartMSG);
						}
					}
				});	// 전체버프
				
				JMenuItem autoShop = new JMenuItem("무인상점");
				autoShop.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
				autoShop.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if(isServerStarted){
							
							// 추가할 부분
						}else{
							
							errorMsg(NoServerStartMSG);
						}
					}
				});	// 무인상점
				
				JMenuItem worldChat = new JMenuItem("월드채팅");
				worldChat.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
				worldChat.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						if(isServerStarted){
							
							// 추가할 부분
						}else{
							
							errorMsg(NoServerStartMSG);
						}
					}
				});	// 월드채팅
				
				jMenu[2].add(characterDelete);
				jMenu[2].add(allBuff);
				jMenu[2].add(autoShop);
				jMenu[2].add(worldChat);
				/** 도우미 메뉴 구성 끝 */
				
				/** 리로드 메뉴 구성 */
				JMenuItem dropReload = new JMenuItem("드랍리스트");
				dropReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				dropReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 1드랍리스트
				
				JMenuItem dieDropReload = new JMenuItem("사망드랍아이템");
				dieDropReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				dieDropReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 2사망드랍아이템
				
				JMenuItem polyReload = new JMenuItem("변신");
				polyReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				polyReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 3변신
				
				JMenuItem resolventReload = new JMenuItem("용해아이템");
				resolventReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				resolventReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 4용해아이템
				
				JMenuItem treasureBoxReload = new JMenuItem("상자아이템");
				treasureBoxReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				treasureBoxReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 5상자아이템
				
				JMenuItem skillsReload = new JMenuItem("스킬");
				skillsReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				skillsReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 6스킬
				
				JMenuItem mobSkillReload = new JMenuItem("몹 스킬");
				mobSkillReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				mobSkillReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 7몹 스킬
				
				JMenuItem mapFixKeyReload = new JMenuItem("맵 픽스 키");
				mapFixKeyReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				mapFixKeyReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 8 맵 픽스 키
				
				JMenuItem itemReload = new JMenuItem("아이템");
				itemReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				itemReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 9아이템
				
				JMenuItem shopReload = new JMenuItem("상점");
				shopReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				shopReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 10상점
				
				JMenuItem npcReload = new JMenuItem("npc");
				npcReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				npcReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 11 npc
				
				JMenuItem bossCycleReload = new JMenuItem("보스 사이클");
				bossCycleReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				bossCycleReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 12 보스 사이클
				
				JMenuItem banIpReload = new JMenuItem("밴 아이피");
				banIpReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				banIpReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 13 밴 아이피
				
				JMenuItem robotReload = new JMenuItem("로봇");
				robotReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				robotReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 14 로봇
				
				JMenuItem configReload = new JMenuItem("설정");
				configReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				configReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 15 설정
				
				JMenuItem weaponAddDamageReload = new JMenuItem("무기추가타격");
				weaponAddDamageReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				weaponAddDamageReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 16 무기추가타격
				
				JMenuItem giniReload = new JMenuItem("지니이벤트");
				giniReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
				giniReload.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
				});	// 17 지니이벤트
				
				jMenu[3].add(dropReload);
				jMenu[3].add(dieDropReload);
				jMenu[3].add(polyReload);
				jMenu[3].add(resolventReload);
				jMenu[3].add(treasureBoxReload);
				jMenu[3].add(skillsReload);
				jMenu[3].add(mobSkillReload);
				jMenu[3].add(mapFixKeyReload);
				jMenu[3].add(itemReload);
				jMenu[3].add(shopReload);
				jMenu[3].add(npcReload);
				jMenu[3].add(bossCycleReload);
				jMenu[3].add(banIpReload);
				jMenu[3].add(robotReload);
				jMenu[3].add(configReload);
				jMenu[3].add(weaponAddDamageReload);
				jMenu[3].add(giniReload);
				/** 리로드 메뉴 구성 끝 */
				
				/** 정보 메뉴 구성 */
				JMenuItem developerInfo = new JMenuItem("개발자 정보");
				developerInfo.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						JOptionPane.showMessageDialog(null, "쥬 욕", "개발자", JOptionPane.INFORMATION_MESSAGE);
					}
				});	// 개발자 정보
				
				jMenu[4].add(developerInfo);
				/** 정보 메뉴 구성 끝 */
				
				for(int i = 0; i < jMenu.length; ++i){
					
					jMenuBar.add(jMenu[i]);
				}
				
			}	/** 메뉴구성 끝 */
		}
		catch(Exception e)
		{
			
		}
	}
	
	/** 메세지 처리 부분 */
	/**
	 * Question Window 
	 * @param 질문 문자열
	 * @return 선택 값 리턴
	 */
	public static int questMsg(String s){
		int result = JOptionPane.showConfirmDialog(null, s, "Server Message", 2, JOptionPane.INFORMATION_MESSAGE);
		return result;
	}
	
	/**
	 * Information Window
	 * @param 정보 문자열
	 */
	public static void infoMsg(String s){
		JOptionPane.showMessageDialog(null, s, "Server Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * ErrorMessage Window
	 * @param 에러 문자열
	 */
	public static void errorMsg(String s){
		JOptionPane.showMessageDialog(null, s, "Server Message", JOptionPane.ERROR_MESSAGE);
	}
}
