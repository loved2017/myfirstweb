package test.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ��½��֤��servlet
 * @author ����
 *
 */

public class loadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn = null;
	Statement Stm = null;
       
  //�ڳ�ʼ��servlet��ʱ��ͼ������ݿ�
    public loadServlet() {
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
			//��һ��ʼ��ʱ�����ý��յ����ݵı���
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//����session����
		HttpSession session = request.getSession();
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		ResultSet rst = data();
		try {
			while(rst.next()) {
				 if(account.equals(rst.getString("account"))
						&& password.equals(rst.getString("password"))) {
					//��ȷ
					session.setAttribute("nickname", rst.getString("nickname"));
					response.sendRedirect("loadsuccess.jsp");
					return;
				}
			}
			
			//�˳�ѭ���Ժ�û��ת��ҳ��˵���˺Ż����������ת����Ӧ��ҳ��
			session.setAttribute("err", "����˺Ż������������ϸ���");
			response.sendRedirect("loadFail.jsp");
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	
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
