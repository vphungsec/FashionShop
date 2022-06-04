package fashion_shop.entity;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id_order;
	
	@Column(name = "Date")
	private Date order_date;
	
	@OneToMany(mappedBy = "idOrder", fetch = FetchType.EAGER)
	private Collection<OrderDetail> details;
	
	@ManyToOne
	@JoinColumn(name = "Cus_email")
	private Account emails;
	
	@Column(name = "TotalPrice")
	private Float totalPrice;
	
	@Column(name = "Cus_username")
	private String username;
	
	@Column(name = "Phone")
	private String phone;
	
	@Column(name = "Cus_address")
	private String address;
	
	public Account getEmails() {
		return emails;
	}

	public void setEmails(Account emails) {
		this.emails = emails;
	}

	public Integer getId_order() {
		return id_order;
	}

	public void setId_order(Integer id_order) {
		this.id_order = id_order;
	}

	public Collection<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(Collection<OrderDetail> detail) {
		this.details = detail;
	}


	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
	