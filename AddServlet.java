package out_connect_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@WebServlet(
		name="AddServlet",
		urlPatterns="/addServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_code=request.getParameter("user_code");
//		System.out.println(user_code+"...........");
		String own_depart=request.getParameter("own_depart");
		String record_date=request.getParameter("record_date");
		String content=request.getParameter("content");
		String connect_date=request.getParameter("connect_date");
		String out_depart=request.getParameter("out_depart");
		String connect_way=request.getParameter("connect_way");
		String connect_nature=request.getParameter("connect_nature");
		String flag=request.getParameter("flag");
		JSONArray jsonarray=null;
		if(flag.equals("one")){
			try {
				jsonarray=getOwnDepart(user_code);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("two")){
			try {
				jsonarray=getOutDepart(own_depart);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				jsonarray=insertDepart(user_code,own_depart,record_date,content,connect_date,out_depart,connect_way,connect_nature);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		response.setContentType("text/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonarray);
				
		
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("get.............");
	}
	private JSONArray getOwnDepart(String user_code) throws Exception{
		String sql="select distinct own_depart from own_depart";
		java.sql.Connection connect =null;
		java.sql.Statement state=null;
		ResultSet result=null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connect=DriverManager.getConnection("jdbc:oracle:thin:@172.19.63.129:1521:cjnowerp", "cjrs10", "rs10_cj");
		state=connect.createStatement();
		result=state.executeQuery(sql);
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		while (result.next()) {
			jsonobj.put("own_depart", result.getString("own_depart"));
			jsonarray.add(jsonobj);
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}
	private JSONArray getOutDepart(String own_depart) throws Exception{
		String sql="select out_depart from out_depart where out_id in "+
					"(select outdepart_id from depart_connect where owndepart_id in"+
					"( select own_id from own_depart where own_depart='"+own_depart+"'))";
		java.sql.Connection connect =null;
		java.sql.Statement state=null;
		ResultSet result=null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connect=DriverManager.getConnection("jdbc:oracle:thin:@172.19.63.129:1521:cjnowerp", "cjrs10", "rs10_cj");
		state=connect.createStatement();
		result=state.executeQuery(sql);
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		while (result.next()) {
			jsonobj.put("out_depart", result.getString("out_depart"));
			jsonarray.add(jsonobj);
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}
	private JSONArray insertDepart(String user_code,String own_depart,String record_date,String content,String connect_date,String out_depart,String connect_way,String connect_nature) throws Exception{
		String sql="insert into out_connect (uuid,connect_way,out_depart,own_depart,content,connect_date,record_date,user_code,connect_nature) values('"+UUID.randomUUID().toString().replace("-", "")+"','"+connect_way+"','"+out_depart+"','"+own_depart+"','"+content+"','"+connect_date+"','"+record_date+"','"+user_code+"','"+connect_nature+"')";
		java.sql.Connection connect =null;
		java.sql.Statement state=null;
		int rs=0;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connect=DriverManager.getConnection("jdbc:oracle:thin:@172.19.63.129:1521:cjnowerp", "cjrs10", "rs10_cj");
		state=connect.createStatement();
		rs=state.executeUpdate(sql);
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		if (rs>0) {
			jsonobj.put("msgCode",rs);
			jsonarray.add(jsonobj);
		}else {
			jsonobj.put("msgCode",rs);
			jsonarray.add(jsonobj);
		}
		state.close();
		connect.close();
		return jsonarray;
	}
}
