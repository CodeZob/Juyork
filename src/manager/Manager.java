/**
 * �ۼ��� : 2015. 9. 8.
 * �ۼ��� : ���
 * ��  �� : 
 */
package manager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


/**
 * ���� ���� �Ŵ��� Ŭ����
 * @author ���
 */
public class Manager {
	
	private static Manager instance = null;
	
	public static final Object lock = new Object();
	public static String date = "";
	public static String time = "";
		
	private int width;
	private int height;
	private boolean isServerStarted;
	
	// ������ ������ ����
	private JFrame jFrame = null;	// ������
	private Container jContainer = null;	// ȭ�� ���� �ڵ�
	private JMenuBar jMenuBar = null;		// �޴���
	private JMenu[] jMenus = null;	// �޴��ٿ� ���̴� �޴���
	private BorderLayout jBorderLayout = new BorderLayout();	// ���̾ƿ�
	private JDesktopPane jDesktopPane = new JDesktopPane();	// ȭ�����
	
	@SuppressWarnings("unused")
	private class Style{
		
		// ������ ������ ��Ÿ��
		private static final String Black = "com.jtattoo.plaf.noire.NoireLookAndFeel";	// ���� ��Ÿ��
		private static final String Mac = "com.jtattoo.plaf.mcwin.McWinLookAndFeel"; // �� ��Ÿ��
	}
	
	// �����α�
	private ServerLogWindow jSystemLogWindow = null;	// �ý���
	
	// ��Ƽä��
	private ServerMultiChatLogWindow jServerMultiChatLogWindow = null;
	
	// ��������
	private ServerUserInfoWindow jServerUserInfoWindow = null; 
	
	// �޼���
	public static final String NoServerStartMSG = "������ ������� �ʾҽ��ϴ�.";
	public static final String OpenStandByOn = "���´�� ��带 �ѽðڽ��ϱ�?";
	public static final String OpenStandByOff = "���´�� ��带 ���ðڽ��ϱ�?";
	public static final String RealExitServer = "������ �����Ͻðڽ��ϱ�?";
	public static final String UserDelete = "������ ���� �������� ���� ĳ���͸� �����Ͻðڽ��ϱ�?";
	public static final String AllBuffMSG = "��ü������ �����Ͻðڽ��ϱ�?";
	

	

	/**
	 * ���α׷� ����!!
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
	 * �ܺ� ���� ���� �̱��� ���
	 */
	private Manager() {
		
		initialize();
	}
	
	/**
	 * �Ŵ��� �̱��� ����
	 */
	private static void create(){
		
		if(instance != null)
			return;
		
		instance = new Manager();
	}
	
	/**
	 * �Ŵ��� ���α׷� �ʱ�ȭ
	 */
	private void initialize(){
		
		initFrame("��弭��", 1920, 1080, Style.Mac);
		
		initMenuBar();
		
		actionFileMenu();
		
		actionMoniterMenu();
		
		actionHelpMenu();
		
		actionReloadMenu();
		
		actionInfoMenu();
		
		width = jFrame.getContentPane().getSize().width / 8;
		height = (jFrame.getContentPane().getSize().height - 50) / 3;
		
		/** ������ â ��ġ **/
		if (jSystemLogWindow == null) {
			
			jSystemLogWindow = new ServerLogWindow("�α���", 280, 0, width, height, true, true);
			jDesktopPane.add(jSystemLogWindow);
		}
	}
	
	/**
	 * ������ �������� �����.
	 * @param title ������ Ÿ��Ʋ
	 * @param width ���� ũ��
	 * @param height ���� ũ��
	 * @param style ������ UI��Ÿ��
	 */
	private void initFrame(final String title, int width, int height, final String style){
		
		try{
			
			UIManager.setLookAndFeel(style);
			JFrame.setDefaultLookAndFeelDecorated(true);
			
			// ������ �� ȭ�� ����.
			if(jFrame == null){
				
				jFrame = new JFrame();
				jFrame.setIconImage(new ImageIcon("img\\icon.gif").getImage());
				jFrame.setSize(width, height);
				jFrame.setVisible(true);
				jFrame.setTitle(":::" + title + ":::");
				jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				jContainer = jFrame.getContentPane();
				jContainer.setLayout(jBorderLayout);
				jContainer.add("Center", jDesktopPane);
			}
		}catch(Exception e){
			
		}
	}
	
	/**
	 * �޴� ���� ����
	 */
	private void initMenuBar(){
		
		if(jMenuBar == null){
			
			jMenuBar = new JMenuBar();
			jFrame.setJMenuBar(jMenuBar);
			
			jMenus = new JMenu[5];
			final String[] menuNames = {"����(F)", "�����(M)", "�����(H)", "���ε�(R)", "����(I)"};
			final int[] eventValues = {KeyEvent.VK_F, KeyEvent.VK_M, KeyEvent.VK_H, KeyEvent.VK_R, KeyEvent.VK_I};
			
			for(int i = 0; i < jMenus.length; ++i){
				
				jMenus[i] = new JMenu(menuNames[i]);
				jMenus[i].setMnemonic(eventValues[i]);
				
				jMenuBar.add(jMenus[i]);
			}
		}
	}
	
	/**
	 * ���� �޴� ���� �޴��������� �����ϰ� �׼��� ó���Ѵ�.
	 */
	private void actionFileMenu(){
		
		JMenuItem standByOn = new JMenuItem("���´�� �ѱ�");
		standByOn.setAccelerator(KeyStroke.getKeyStroke('Y', InputEvent.CTRL_DOWN_MASK));
		standByOn.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isServerStarted){
					
					if(questMsg(OpenStandByOn) == 0){
						
						// �߰��� �κ�
						jSystemLogWindow.append(getLogTime() + " [���� ����] ���´�� ��." + "\n", "Blue");
					}
				}else{
					
					errorMsg(NoServerStartMSG);
				}
			}
		});	// ���´�� �ѱ�
		
		JMenuItem standByOff = new JMenuItem("���´�� ����");
		standByOff.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		standByOff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(isServerStarted){
					
					if(questMsg(OpenStandByOff) == 0){
						
						// �߰��� �κ�
					}
				}else{
					
					errorMsg(NoServerStartMSG);
				}
			}
		});	// ���´�� ����
		
		JMenuItem serverSet = new JMenuItem("���� ����");
		serverSet.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
		serverSet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// �߰��� �κ�						
			}
		});	// ���� ����
		
		JMenuItem serverSave = new JMenuItem("���� ����");
		serverSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
		serverSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				// �߰��� �κ�			
			}
		});	// ���� ����
		
		JMenuItem serverExit = new JMenuItem("���� ����");
		serverExit.setAccelerator(KeyStroke.getKeyStroke(',', InputEvent.CTRL_DOWN_MASK));
		serverExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(questMsg(RealExitServer) == 0){
					
					// �߰��� �κ�
				}
			}
		});	// ���� ����
		
		JMenuItem serverNowExit = new JMenuItem("�ٷ� ����");
		serverNowExit.setAccelerator(KeyStroke.getKeyStroke('.', InputEvent.CTRL_DOWN_MASK));
		serverNowExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(questMsg(RealExitServer) == 0){
					
					// �߰��� �κ�
				}
			}
		});	// �ٷ� ����
		
		// ���� �޴��� �ڽĸ޴������� �߰�
		jMenus[0].add(standByOn);
		jMenus[0].add(standByOff);
		jMenus[0].add(serverSet);
		jMenus[0].add(serverSave);
		jMenus[0].add(serverExit);
		jMenus[0].add(serverNowExit);
	}
	
	/**
	 * ����� �޴��� ���� �ϰ� �׼��� ó���Ѵ�.
	 */
	private void actionMoniterMenu(){
		
		JMenuItem LoginLogWindow = new JMenuItem("�α���");
		LoginLogWindow.setAccelerator(KeyStroke.getKeyStroke('0', InputEvent.CTRL_DOWN_MASK));
		LoginLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// �α���
		
		JMenuItem worldChatLogWindow = new JMenuItem("��üä��");
		worldChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('1', InputEvent.CTRL_DOWN_MASK));
		worldChatLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ��üä��
		
		JMenuItem normalChatLogWindow = new JMenuItem("�Ϲ�ä��");
		normalChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('2', InputEvent.CTRL_DOWN_MASK));
		normalChatLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// �Ϲ�ä��
		
		JMenuItem whisperChatLogWindow = new JMenuItem("�ӼӸ�ä��");
		whisperChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('3', InputEvent.CTRL_DOWN_MASK));
		whisperChatLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// �ӼӸ�ä��
		
		JMenuItem clanChatLogWindow = new JMenuItem("����ä��");
		clanChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('4', InputEvent.CTRL_DOWN_MASK));
		clanChatLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ����ä��
		
		JMenuItem partyChatLogWindow = new JMenuItem("��Ƽä��");
		partyChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('5', InputEvent.CTRL_DOWN_MASK));
		partyChatLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ��Ƽä��
		
		JMenuItem tradeChatLogWindow = new JMenuItem("���ä��");
		tradeChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('6', InputEvent.CTRL_DOWN_MASK));
		tradeChatLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ���ä��
		
		JMenuItem wareHouseLogWindow = new JMenuItem("â��");
		wareHouseLogWindow.setAccelerator(KeyStroke.getKeyStroke('7', InputEvent.CTRL_DOWN_MASK));
		wareHouseLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// â��
		
		JMenuItem tradeLogWindow = new JMenuItem("�ŷ�");
		tradeLogWindow.setAccelerator(KeyStroke.getKeyStroke('8', InputEvent.CTRL_DOWN_MASK));
		tradeLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// �ŷ�
		
		JMenuItem enchantLogWindow = new JMenuItem("��æƮ");
		enchantLogWindow.setAccelerator(KeyStroke.getKeyStroke('9', InputEvent.CTRL_DOWN_MASK));
		enchantLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ��æƮ
		
		JMenuItem dropItemLogWindow = new JMenuItem("������ ���/����");
		dropItemLogWindow.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));
		dropItemLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ������ ���/����
		
		JMenuItem bugLogWindow = new JMenuItem("���׸����");
		bugLogWindow.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_DOWN_MASK));
		bugLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ���׸����
		
		JMenuItem bossLogWindow = new JMenuItem("����/���� Ÿ�̸�");
		bossLogWindow.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));
		bossLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ����/���� Ÿ�̸�
		
		JMenuItem accountLogWindow = new JMenuItem("��������");
		accountLogWindow.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
		accountLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ��������
		
		JMenuItem commandLogWindow = new JMenuItem("GMĿ�ǵ�");
		commandLogWindow.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.CTRL_DOWN_MASK));
		commandLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// GMĿ�ǵ�
		
		JMenuItem warLogWindow = new JMenuItem("���� �����");
		warLogWindow.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_DOWN_MASK));
		warLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ���� �����
		
		JMenuItem multiChatLogWindow = new JMenuItem("��Ƽä�� �����");
		multiChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('\\', InputEvent.CTRL_DOWN_MASK));
		multiChatLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ��Ƽä�� �����
		
		JMenuItem quantityItemLogWindow = new JMenuItem("������ ������ �����");
		quantityItemLogWindow.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.CTRL_DOWN_MASK));
		quantityItemLogWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// ������ ������ �����
		
		jMenus[1].add(LoginLogWindow);
		jMenus[1].add(worldChatLogWindow);
		jMenus[1].add(normalChatLogWindow);
		jMenus[1].add(whisperChatLogWindow);
		jMenus[1].add(clanChatLogWindow);
		jMenus[1].add(partyChatLogWindow);
		jMenus[1].add(tradeChatLogWindow);
		jMenus[1].add(wareHouseLogWindow);
		jMenus[1].add(tradeLogWindow);
		jMenus[1].add(enchantLogWindow);
		jMenus[1].add(dropItemLogWindow);
		jMenus[1].add(bugLogWindow);
		jMenus[1].add(bossLogWindow);
		jMenus[1].add(accountLogWindow);
		jMenus[1].add(commandLogWindow);
		jMenus[1].add(warLogWindow);
		jMenus[1].add(multiChatLogWindow);
		jMenus[1].add(quantityItemLogWindow);
	}
	
	/**
	 * ����� �޴��� �����ϰ� �׼��� ó���Ѵ�.
	 */
	private void actionHelpMenu(){
		
		JMenuItem characterDelete = new JMenuItem("ĳ���� ����");
		characterDelete.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));
		characterDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(isServerStarted){
					
					if(questMsg(UserDelete) == 0){
						
						// �߰��� �κ�
					}
				}else{
					
					errorMsg(NoServerStartMSG);
				}
				
			}
		});	// ĳ���� ����
		
		JMenuItem allBuff = new JMenuItem("��ü����");
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
		});	// ��ü����
		
		JMenuItem autoShop = new JMenuItem("���λ���");
		autoShop.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
		autoShop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isServerStarted){
					
					// �߰��� �κ�
				}else{
					
					errorMsg(NoServerStartMSG);
				}
			}
		});	// ���λ���
		
		JMenuItem worldChat = new JMenuItem("����ä��");
		worldChat.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));
		worldChat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isServerStarted){
					
					// �߰��� �κ�
				}else{
					
					errorMsg(NoServerStartMSG);
				}
			}
		});	// ����ä��
		
		jMenus[2].add(characterDelete);
		jMenus[2].add(allBuff);
		jMenus[2].add(autoShop);
		jMenus[2].add(worldChat);
	}
	
	/**
	 * ���ε� �޴��� �����ϰ� �׼��� ó���Ѵ�.
	 */
	private void actionReloadMenu(){
		
		JMenuItem dropReload = new JMenuItem("�������Ʈ");
		dropReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		dropReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 1�������Ʈ
		
		JMenuItem dieDropReload = new JMenuItem("������������");
		dieDropReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		dieDropReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 2������������
		
		JMenuItem polyReload = new JMenuItem("����");
		polyReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		polyReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 3����
		
		JMenuItem resolventReload = new JMenuItem("���ؾ�����");
		resolventReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		resolventReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 4���ؾ�����
		
		JMenuItem treasureBoxReload = new JMenuItem("���ھ�����");
		treasureBoxReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		treasureBoxReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 5���ھ�����
		
		JMenuItem skillsReload = new JMenuItem("��ų");
		skillsReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		skillsReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 6��ų
		
		JMenuItem mobSkillReload = new JMenuItem("�� ��ų");
		mobSkillReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		mobSkillReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 7�� ��ų
		
		JMenuItem mapFixKeyReload = new JMenuItem("�� �Ƚ� Ű");
		mapFixKeyReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		mapFixKeyReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 8 �� �Ƚ� Ű
		
		JMenuItem itemReload = new JMenuItem("������");
		itemReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		itemReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 9������
		
		JMenuItem shopReload = new JMenuItem("����");
		shopReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		shopReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 10����
		
		JMenuItem npcReload = new JMenuItem("npc");
		npcReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		npcReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 11 npc
		
		JMenuItem bossCycleReload = new JMenuItem("���� ����Ŭ");
		bossCycleReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		bossCycleReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 12 ���� ����Ŭ
		
		JMenuItem banIpReload = new JMenuItem("�� ������");
		banIpReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		banIpReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 13 �� ������
		
		JMenuItem robotReload = new JMenuItem("�κ�");
		robotReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		robotReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 14 �κ�
		
		JMenuItem configReload = new JMenuItem("����");
		configReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		configReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 15 ����
		
		JMenuItem weaponAddDamageReload = new JMenuItem("�����߰�Ÿ��");
		weaponAddDamageReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		weaponAddDamageReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 16 �����߰�Ÿ��
		
		JMenuItem giniReload = new JMenuItem("�����̺�Ʈ");
		giniReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));
		giniReload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});	// 17 �����̺�Ʈ
		
		jMenus[3].add(dropReload);
		jMenus[3].add(dieDropReload);
		jMenus[3].add(polyReload);
		jMenus[3].add(resolventReload);
		jMenus[3].add(treasureBoxReload);
		jMenus[3].add(skillsReload);
		jMenus[3].add(mobSkillReload);
		jMenus[3].add(mapFixKeyReload);
		jMenus[3].add(itemReload);
		jMenus[3].add(shopReload);
		jMenus[3].add(npcReload);
		jMenus[3].add(bossCycleReload);
		jMenus[3].add(banIpReload);
		jMenus[3].add(robotReload);
		jMenus[3].add(configReload);
		jMenus[3].add(weaponAddDamageReload);
		jMenus[3].add(giniReload);
	}
	
	/**
	 * ���� �޴��� �����ϰ� �׼��� ó���Ѵ�.
	 */
	private void actionInfoMenu(){
		
		JMenuItem developerInfo = new JMenuItem("������ ����");
		developerInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "�� ��", "������", JOptionPane.INFORMATION_MESSAGE);
			}
		});	// ������ ����
		
		jMenus[4].add(developerInfo);
	}
	
	/** �޼��� ó�� �κ� */
	/**
	 * Question Window 
	 * @param ���� ���ڿ�
	 * @return ���� �� ����
	 */
	public static int questMsg(String s){
		int result = JOptionPane.showConfirmDialog(null, s, "Server Message", 2, JOptionPane.INFORMATION_MESSAGE);
		return result;
	}
	
	/**
	 * Information Window
	 * @param ���� ���ڿ�
	 */
	public static void infoMsg(String s){
		JOptionPane.showMessageDialog(null, s, "Server Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * ErrorMessage Window
	 * @param ���� ���ڿ�
	 */
	public static void errorMsg(String s){
		JOptionPane.showMessageDialog(null, s, "Server Message", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * 
	 * @param text
	 * @param fileName
	 * @param date
	 */
	public static void flush(JTextPane text, String fileName, String date){
		
		try {
			
			RandomAccessFile rnd = new RandomAccessFile("ServerLog/" + date + "/" + fileName + ".txt", "rw");
			rnd.write(text.getText().getBytes());
			rnd.close();
			rnd = null;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * ���� �ð� ��������
	 * @return "MM.dd HH:mm:ss"
	 */
	private static String getLogTime(){
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm:ss");
		String time = dateFormat.format(currentDate.getTime());
		return time;
	}
	
	/**
	 * ��¥�� �ð��� ��´�.
	 * @return ��¥����(yyyy-MM-dd) �ð�(hh-mm) ���ڿ�
	 */
	public static String getDate(){
		
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh-mm", Locale.KOREA);
		return s.format(Calendar.getInstance().getTime());
	}
}
