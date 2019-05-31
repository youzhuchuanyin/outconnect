package out_connect_servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@WebServlet(
		name="ChaxunServlet",
		urlPatterns="/chaxunServlet")
public class ChaxunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_code=request.getParameter("user_code");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String own_depart=request.getParameter("own_depart");
		String out_depart=request.getParameter("out_depart");
		String connect_date=request.getParameter("connect_date");
		String connect_nature=request.getParameter("connect_nature");
		String flag=request.getParameter("flag");
		JSONArray jsonarray=null;
		if(flag.equals("four")){
			try {
				jsonarray=queryList(user_code,startDate,endDate,own_depart,connect_nature);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(flag.equals("five")){
			try {
				jsonarray=queryDetail(user_code,out_depart,own_depart,connect_date,connect_nature);
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
	private JSONArray queryList(String user_code,String startDate,String endDate,String own_depart,String connect_nature) throws Exception{
		String sql="select own_depart,out_depart,connect_date,user_code,connect_nature  from out_connect where user_code='"+user_code+"' and connect_date>='"+startDate+"' and connect_date<='"+endDate+"' and own_depart='"+own_depart+"' and connect_nature='"+connect_nature+"'";
		String supersql="select own_depart,out_depart,connect_date,user_code,connect_nature  from out_connect where  connect_date>='"+startDate+"' and connect_date<='"+endDate+"' and own_depart='"+own_depart+"' and connect_nature='"+connect_nature+"'";
		java.sql.Connection connect =null;
		java.sql.Statement state=null;
		ResultSet result=null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connect=DriverManager.getConnection("jdbc:oracle:thin:@172.19.63.129:1521:cjnowerp", "cjrs10", "rs10_cj");
		state=connect.createStatement();
		if(user_code.equals("010100405746")||user_code.equals("010100601173")||user_code.equals("010100404700")||user_code.equals("010100600110")||user_code.equals("010100400726")||user_code.equals("010100508581")||user_code.equals("010100502878")||user_code.equals("010100601100")||user_code.equals("010100601155")){
			result=state.executeQuery(supersql);
		}else{
			result=state.executeQuery(sql);
		}
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		while (result.next()) {
			jsonobj.put("own_depart", result.getString("own_depart"));
			jsonobj.put("out_depart", result.getString("out_depart"));
			jsonobj.put("connect_date", result.getString("connect_date"));
			jsonobj.put("user_code", result.getString("user_code"));
			jsonobj.put("connect_nature", result.getString("connect_nature"));
			jsonarray.add(jsonobj);
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}
	private JSONArray queryDetail(String user_code,String out_depart,String own_depart,String connect_date,String connect_nature) throws Exception{
		String sql="select connect_way, own_depart,out_depart,content,connect_date,record_date,user_code,connect_nature  from out_connect where user_code='"+user_code+"' and out_depart='"+out_depart+"' and own_depart='"+own_depart+"' and connect_date='"+connect_date+"' and connect_nature='"+connect_nature+"'";
		String supersql="select connect_way, own_depart,out_depart,content,connect_date,record_date,user_code,connect_nature  from out_connect where user_code='"+user_code+"' and out_depart='"+out_depart+"' and own_depart='"+own_depart+"' and connect_date='"+connect_date+"' and connect_nature='"+connect_nature+"'";
		java.sql.Connection connect =null;
		java.sql.Statement state=null;
		ResultSet result=null;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connect=DriverManager.getConnection("jdbc:oracle:thin:@172.19.63.129:1521:cjnowerp", "cjrs10", "rs10_cj");
		state=connect.createStatement();
		if(user_code.equals("010100405746")||user_code.equals("010100601173")||user_code.equals("010100404700")||user_code.equals("010100600110")||user_code.equals("010100400726")||user_code.equals("010100508581")||user_code.equals("010100502878")||user_code.equals("010100601100")||user_code.equals("010100601155")){
			result=state.executeQuery(supersql);
		}else{
			result=state.executeQuery(sql);
		}
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonobj = new JSONObject();
		while (result.next()) {
			jsonobj.put("connect_way", result.getString("connect_way"));
			jsonobj.put("own_depart", result.getString("own_depart"));
			jsonobj.put("out_depart", result.getString("out_depart"));
			jsonobj.put("content", result.getString("content"));
			jsonobj.put("connect_date", result.getString("connect_date"));
			jsonobj.put("record_date", result.getString("record_date"));
			jsonobj.put("user_code", result.getString("user_code"));
			jsonobj.put("connect_nature", result.getString("connect_nature"));
			jsonarray.add(jsonobj);
		}
		result.close();
		state.close();
		connect.close();
		return jsonarray;
	}

}
