

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 * Servlet implementation class InsertCoffeeServlet
 */
@WebServlet("/InsertCoffeeServlet")
public class InsertCoffeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertCoffeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InsertCoffee</title>");            
            out.println("</head>");
            out.println("<body>");
            String coffee=request.getParameter("coffee");
            String sale=request.getParameter("sale");
            String total=request.getParameter("total");         
            String supplier=request.getParameter("supplier");
            String price=request.getParameter("price");         
            try {
   					InsertCoffee(coffee,sale,total,supplier,price);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                out.println("新增失敗");
                return;
            }
            out.println("新增完成");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }

}
	public int InsertCoffee(String coffee, String sale,String total,String supplier,String price) throws SQLException
	{	
		Connection conn=null;
		PreparedStatement pt=null;
		String insert="insert into classicmodels.COFFEES(COF_NAME,SUP_ID,PRICE,SALES,TOTAL)"+
				"values (?,?,?,?,?)";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Taipei","root","1234");
			conn.setAutoCommit(false);
			pt=conn.prepareStatement(insert);
			pt.setString(1, coffee);
			pt.setInt(2, Integer.parseInt(supplier));
			pt.setDouble(3, Double.parseDouble(price));
			pt.setInt(4, Integer.parseInt(sale));
			pt.setInt(5,Integer.parseInt(total));
			int r=pt.executeUpdate();
			conn.commit();
			return r;
		} catch (SQLException|ClassNotFoundException e) {
			// TODO Auto-generated catch block
			 System.out.println(e.getMessage());
			  if (conn != null) {
		            try {
		                System.err.print("Transaction is being rolled back");
		                conn.rollback();
		            } catch(SQLException ex) {
		                System.out.println(ex.getMessage());
		            }
		        }
		    } finally {
		    	conn.setAutoCommit(true);
		    	if (pt != null) {
		            pt.close();
		        }       

		}
		return 0;

		    }
		   


}
