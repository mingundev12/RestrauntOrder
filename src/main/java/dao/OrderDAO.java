package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import common.DBConnection;
import dto.MenuDTO;
import dto.OrderDTO;
import dto.OrderListDTO;

public class OrderDAO {

	public boolean insert() {

		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		
		String sql = "insert into orders (price_total, status) values (0, \'결제대기\')";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int result = pstmt.executeUpdate();
			
			if (result != 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	public boolean addOrder(OrderDTO order, MenuDTO menu, int amount) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		
		String sql = "";
		boolean res = false;
		
		try {
			conn.setAutoCommit(false);

			sql += "insert into order_list (order_id, menu_id, ordered_name, ordered_price, amount) ";
			sql += "values (?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, order.getId());
			pstmt.setLong(2, menu.getId());
			pstmt.setString(3, menu.getMenuName());
			pstmt.setInt(4, menu.getPrice());
			pstmt.setInt(5, amount);
			
			int result = pstmt.executeUpdate();
			
			if (result == 0) {
				throw new Exception("추가주문 생성 안됨");
			}
			
			sql = "update orders set price_total = price_total + ? where id = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, menu.getPrice());
			pstmt.setLong(2, order.getId());
			
			result = pstmt.executeUpdate();
			
			if(result == 0) {
				throw new Exception("주문 추가 실패");
			}
			res = true;
		} catch (Exception e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return res;
	}
}
