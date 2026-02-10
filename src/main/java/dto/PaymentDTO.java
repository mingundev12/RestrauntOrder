package dto;

import java.util.Date;

public class PaymentDTO {
	private long id;
	private long orderId;
	private String payOption;
	private Date createdAt;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getPayOption() {
		return payOption;
	}
	public void setPayOption(String payOption) {
		this.payOption = payOption;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "PaymentDTO [id=" + id + ", orderId=" + orderId + ", payOption=" + payOption + ", createdAt=" + createdAt
				+ "]";
	}
	public PaymentDTO(long id, long orderId, String payOption, Date createdAt) {
		this.id = id;
		this.orderId = orderId;
		this.payOption = payOption;
		this.createdAt = createdAt;
	}
	
	
}
