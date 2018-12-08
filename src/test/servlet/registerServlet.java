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
 * 这个注册的Servlet，进行判断和存储数据
 * @author 张亮
 *
 */
public class registerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	//连接数据库所需要的一些变量
	Connection conn = null;
	PreparedStatement pStmt = null;
	String sql = "insert into userdata (nickname,account,password) values (?,?,?)";
	Statement Stm = null;

    public registerServlet() {
    	//在实例化servlet的时候就连接数据库
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
		//将接收的数据进行编码设置，不然不乱码，后期可以自行编写编码过滤器
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();		//设置session对象，用来存储对象
		//获取输入的昵称和账户，以便进行验证
		String nickname = request.getParameter("nickname");
		String account = request.getParameter("account");
		String newpassword1 = request.getParameter("newpassword1");
		String newpassword2 = request.getParameter("newpassword2");
		if(!(newpassword1.equals(newpassword2))){
			//如果两次的密码输入不一样，则转到注册失败的页面
			session.setAttribute("err","两次密码输入不正确，请返回重新注册");	
			//存储错误信息，以便在其他页面输出
			response.sendRedirect("registerFail.jsp");		//页面的重定向
			//在response.sendRedirect()后面要加return是因为，告诉后面的代码没用了，防止发生错误
			return;
		}else {
			//data方法返回数据的结果集
			ResultSet rst = data();
			try {
				while(rst.next()) {
					//遍历结果集
					if(nickname.equals(rst.getString("nickname"))) {
						//如果和数据库里面的昵称相同这转到注册失败的页面，同时记录错误信息
						session.setAttribute("err", "昵称已存在，请返回重新注册");
						response.sendRedirect("registerFail.jsp");
						return;
					}else if(account.equals(rst.getString("account"))) {
						//入伙和数据库里面的账号相同则转到注册失败的页面，同时记录错误信息
						session.setAttribute("err", "账号已存在，请返回重新注册");
						response.sendRedirect("registerFail.jsp");
						return;
						
					}
				}
				rst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//当所有的验证都通过的时候，这时就可以将所创建的用户数据存储进入数据库了
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
	 * 该方法是用来得到数据库的结果集
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
