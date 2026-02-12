package dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OrderDTO {
	private long id;
	private int price_total;
	private String status;
	private LocalDateTime created_at;
	private Map<Long, OrderListDTO> orderList;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPrice_total() {
		return price_total;
	}

	public void setPrice_total(int price_total) {
		this.price_total = price_total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", price_total=" + price_total + ", status=" + status + ", created_at="
				+ created_at + "]";
	}

	public OrderDTO(long id, int price_total, String status, LocalDateTime created_at) {
		this.id = id;
		this.price_total = price_total;
		this.status = status;
		this.created_at = created_at;
		this.setOrderList(new HashMap<>());
	}

	public OrderDTO() {
	}

	public Map<Long, OrderListDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(Map<Long, OrderListDTO> orderList) {
		this.orderList = orderList;
	}

}
