

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BookFetch extends HttpServlet {
	private static final long serialVersionUID = 1L;

   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String id = request.getParameter("bookid");
		int id1 = Integer.parseInt(id);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "ali");
			//System.out.println("Success");
			PreparedStatement stat = con.prepareStatement("select * from book where book_id=?");
			stat.setInt(1, id1);
			boolean flag = false;
			String html="<html>";
            html+="<head><title>Book</title></head>";
            html+="<body>";
			ResultSet rs = stat.executeQuery();
			html+="<div>";
	        html+="<table border=1>";
			html+="<tr>";
	        html+="<th>Title</th>";
	        html+="<th>ID</th>";
	        html+="<th>Author</th>";
	        html+="<th>Publisher</th>";
	        html+="<th>Price</th>";
	        html+="</tr>";
			while(rs.next())
			{
				flag=true;
				int bookid = rs.getInt("BOOK_ID");
				String title = rs.getString("TITLE");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				int price = rs.getInt("PRICE");
				html+="<tr>";
	            html+="<td>"+title+"</td>";
	            html+="<td>"+bookid+"</td>";
	            html+="<td>"+author+"</td>";
	            html+="<td>"+publisher+"</td>";
	            html+="<td>"+price+"</td>";
	            html+="</tr>";
				
			}
				
				html+="</table></div></body></html>";
				if(flag)
				{
				out.println(html);
				//System.out.println(html);
				}
				
				else
				{
					out.println("<h1 align=\"center\">No book Found for this ID</h1>");
				}
				
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
