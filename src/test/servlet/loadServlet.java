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
 * 登陆验证的servlet
 * @author 张亮
 *
 */

public class loadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection conn = null;
	Statement Stm = null;
       
  //在初始化servlet的时候就加载数据库
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
			//在一开始的时候设置接收的数据的编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//建立session对象
		HttpSession session = request.getSession();
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		ResultSet rst = data();
		try {
			while(rst.next()) {
				 if(account.equals(rst.getString("account"))
						&& password.equals(rst.getString("password"))) {
					//正确
					session.setAttribute("nickname", rst.getString("nickname"));
					response.sendRedirect("loadsuccess.jsp");
					return;
				}
			}
			
			//退出循环以后还没有转换页面说明账号或密码错误，则转到相应的页面
			session.setAttribute("err", "你的账号或密码错误，请仔细检查");
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
