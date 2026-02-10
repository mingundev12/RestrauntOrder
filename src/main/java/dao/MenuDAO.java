package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBConnection;
import dto.MenuDTO;

public class MenuDAO {

	public List<MenuDTO> getMenuList() {
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
//		dao.getMenuList();
//	}
}
