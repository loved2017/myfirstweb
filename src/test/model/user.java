package test.model;
/**
 * �û���
 * @author ����
 *
 */
public class user {
	private String nickname;		//�ǳ�
	private String account ;		//�˺�
	private String password;		//����
	
	
	
	public user(String nickname, String account, String password) {
		this.nickname = nickname;
		this.account = account;
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
