package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import common.DBConnection;
import dto.*;

public class OrderDAO {

	public static boolean insert() {

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
	
	public static boolean addOrder(OrderDTO order, MenuDTO menu, int amount) {
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
	
	public static Map<Long, OrderListDTO> getOrderDetail(Connection conn, long orderId) {
		Map<Long, OrderListDTO> map = new HashMap<Long, OrderListDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			sql += "select * from order_list where order_id = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, orderId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				OrderListDTO orderList = new OrderListDTO(
						rs.getLong("id"), orderId, rs.getLong("menu_id"),
						rs.getString("ordered_name"), rs.getInt("ordered_price"),
						rs.getInt("amount")
						);
				map.put(orderList.getMenuId(), orderList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static List<OrderAndPayment> getOrdersList() {
		List<OrderAndPayment> list = new ArrayList<>();
		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		
		try {
			sql += "select * from orders o left join payments p on o.id = p.order_id";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				OrderAndPayment order = new OrderAndPayment(
						rs.getLong("o.id"), rs.getInt("o.price_total"),
						rs.getString("o.status"), rs.getTimestamp("o.created_at").toLocalDateTime(),
						getOrderDetail(conn, rs.getLong("o.id")),
						(rs.getString("p.pay_option") == null) ? "" : rs.getString("p.pay_option")
						);
				
				
				list.add(order);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	

	public static String getListAsJson() {
		List<OrderAndPayment> list = getOrdersList();
		StringJoiner sj = new StringJoiner(",", "[", "]");

		for (OrderDTO order : list) {
			sj.add(order.toString());
		}
		
//		System.out.println(sj);

		return sj.toString();
	}
	
//	public static void main(String[] args) {
//		System.out.println(getListAsJson());
//	}
}
