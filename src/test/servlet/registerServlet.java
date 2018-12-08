package test.servlet;

import java.sql.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import test.model.user;


/**
 * ���ע���Servlet�������жϺʹ洢����
 * @author ����
 *
 */
public class registerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	//�������ݿ�����Ҫ��һЩ����
	Connection conn = null;
	PreparedStatement pStmt = null;
	String sql = "insert into userdata (nickname,account,password) values (?,?,?)";
	Statement Stm = null;

    public registerServlet() {
    	//��ʵ����servlet��ʱ����������ݿ�
    	String url = "jdbc:mysql://localhost:3306/myfirstwebdata?characterEncoding=utf8&useSSL=true";
		String user = "root";
		String password = "zl19981225520";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�����յ����ݽ��б������ã���Ȼ�����룬���ڿ������б�д���������
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();		//����session���������洢����
		//��ȡ������ǳƺ��˻����Ա������֤
		String nickname = request.getParameter("nickname");
		String account = request.getParameter("account");
		String newpassword1 = request.getParameter("newpassword1");
		String newpassword2 = request.getParameter("newpassword2");
		if(!(newpassword1.equals(newpassword2))){
			//������ε��������벻һ������ת��ע��ʧ�ܵ�ҳ��
			session.setAttribute("err","�����������벻��ȷ���뷵������ע��");	
			//�洢������Ϣ���Ա�������ҳ�����
			response.sendRedirect("registerFail.jsp");		//ҳ����ض���
			//��response.sendRedirect()����Ҫ��return����Ϊ�����ߺ���Ĵ���û���ˣ���ֹ��������
			return;
		}else {
			//data�����������ݵĽ����
			ResultSet rst = data();
			try {
				while(rst.next()) {
					//���������
					if(nickname.equals(rst.getString("nickname"))) {
						//��������ݿ�������ǳ���ͬ��ת��ע��ʧ�ܵ�ҳ�棬ͬʱ��¼������Ϣ
						session.setAttribute("err", "�ǳ��Ѵ��ڣ��뷵������ע��");
						response.sendRedirect("registerFail.jsp");
						return;
					}else if(account.equals(rst.getString("account"))) {
						//�������ݿ�������˺���ͬ��ת��ע��ʧ�ܵ�ҳ�棬ͬʱ��¼������Ϣ
						session.setAttribute("err", "�˺��Ѵ��ڣ��뷵������ע��");
						response.sendRedirect("registerFail.jsp");
						return;
						
					}
				}
				rst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//�����е���֤��ͨ����ʱ����ʱ�Ϳ��Խ����������û����ݴ洢�������ݿ���
//		try {
//			pStmt = conn.prepareStatement(sql);
//			pStmt.setString(1,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               );
//			pStmt.setString(2, account);
//			pStmt.setString(3, newpassword1);
//			pStmt.execute();
//			
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
		Configuration config = new Configuration().configure();
		SessionFactory sf = config.buildSessionFactory();
		Session s =  sf.openSession();
		Transaction tx = s.beginTransaction();
		s.save(new user(nickname, account, newpassword1));
		tx.commit();
		s.close();
		sf.close();

		session.setAttribute("nickname", nickname);
		response.sendRedirect("registerSuccess.jsp");
		return;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	
	/**
	 * �÷����������õ����ݿ�Ľ����
	 * @return
	 */
	public ResultSet data() {
		
		ResultSet rst = null ;
		try {
			Stm =  conn.createStatement();
			rst = Stm.executeQuery("select * from userdata");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rst;
	}

}
