package com.csu.util;

public interface Constant {
	
	static final int Tag_Int = 0;//tag��ǩint����
	static final int Tag_String = 1;//tag��ǩstring����
	static final int Tag_Bytes = 2;//tag��ǩbyte[]����
	
	static final int Fail = -200;
	static final int success = 200;
	
	  //Btn�ı�ʶ  
    public static final int BTN_FLAG_MESSAGE = 0x01;  
    public static final int BTN_FLAG_CONTACTS = 0x01 << 1;  
    public static final int BTN_FLAG_NEWS = 0x01 << 2;  
    public static final int BTN_FLAG_SETTING = 0x01 << 3;  
      
    //Fragment�ı�ʶ  
    public static final String FRAGMENT_FLAG_MESSAGE = "��Ϣ";   
    public static final String FRAGMENT_FLAG_CONTACTS = "��ϵ��";   
    public static final String FRAGMENT_FLAG_NEWS = "����";   
    public static final String FRAGMENT_FLAG_SETTING = "����";   
    public static final String FRAGMENT_FLAG_SIMPLE = "simple"; 
    
    static final int Task_Regist = 0;//ע��������
	static final int Task_Login = 1;//��½������
	static final int Task_friend_Refresh = 2;//�����б�ˢ������
	static final int Task_Chat_Msg = 3;//���췢������
	static final int Task_Check_Regist = 4;//ע���û����ظ���ѯ
	static final int Task_Video_Chat = 5;//ҕ�l�����΄�
	static final int Task_Question_Post = 6;//������������
	static final int Task_Question_Ask = 7;//���������б�����
	
	
}
