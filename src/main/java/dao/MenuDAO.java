package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBConnection;
import dto.MenuDTO;

public class MenuDAO implements ShopDAO{

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public long getId(MenuDTO menu) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		sql += "select id from menu where menu_name = " + menu.getMenuName();
		long id = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			

			while (rs.next()) {
				id = rs.getLong("id");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return id;
	}

	@Override
	public boolean insert() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean insert(MenuDTO menu) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		
//		menu_img 는 "./resources/img/id.jpg" 로 저장
		menu.setMenuImg("./resources/img/" + menu.getId() + ".jpg");
		
		String sql = "";
		sql += "insert into menu (menu_name, menu_img, price, category) values";
		sql += " (?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, menu.getMenuName());
			pstmt.setString(2, menu.getMenuImg());
			pstmt.setInt(3, menu.getPrice());
			pstmt.setString(4, menu.getCategory());
			
			int result = pstmt.executeUpdate();
			
			if (result != 0) {
				System.out.println("메뉴 정상등록");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean update(MenuDTO menu) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		
		
		String sql = "";
		sql += "update menu set ";
		sql += "menu_name = ?,";
		sql += "menu_img = ?,";
		sql += "price = ?,";
		sql += "category = ?,";
		sql += "where id = " + menu.getId();
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, menu.getMenuName());
			pstmt.setString(2, menu.getMenuImg());
			pstmt.setInt(3, menu.getPrice());
			pstmt.setString(4, menu.getCategory());
			
			int result = pstmt.executeUpdate();
			
			if (result != 0) {
				System.out.println("메뉴 정상수정");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean delete(MenuDTO menu) {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		
		
		String sql = "";
		sql += "delete from menu where id = " + menu.getId();
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int result = pstmt.executeUpdate();
			
			if (result != 0) {
				System.out.println("메뉴 정상삭제");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<MenuDTO> getList() {
		Connection conn = DBConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<MenuDTO> menuList = new ArrayList<>();
		
		String sql = "";
		sql += "select * from menu";
		sql += " order by category desc";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				long id = rs.getLong("id");
				String menuName = rs.getString("menu_name");
				String menuImg = rs.getString("menu_img");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				
				MenuDTO menu = new MenuDTO(id, menuName, menuImg, price, category);
				
				menuList.add(menu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for (MenuDTO menu : menuList) {
//			System.out.println(menu);
//		}
		
		return menuList;
	}
	
//	public static void main(String[] args) {
//		MenuDAO dao = new MenuDAO();
//		dao.getList();
//	}
}
