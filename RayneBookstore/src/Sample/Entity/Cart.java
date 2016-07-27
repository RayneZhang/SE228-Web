
package Sample.Entity;

import java.util.Map;
import java.util.Vector;

public class Cart {
	private int id;
	private int user_id;
	private int book_id;
	private int total_amount;
	private int total_price;
	private Map<Integer,Integer> idmap;
	private Map<Integer,Integer> amountmap;
	private Vector<Integer> priceset;
	private Vector<Booklist> bookincart;

	public Cart() {
	}
	
	public Map<Integer,Integer> getAmountmap(){
		return amountmap;
	}
	
	public void setAmountmap(Map<Integer,Integer> map){
		this.amountmap=map;
	}
	
	public Map<Integer,Integer> getIdmap(){
		return idmap;
	}
	
	public void setIdmap(Map<Integer,Integer> map){
		this.idmap=map;
	}
	
	public Vector<Booklist> getBookincart(){
		return bookincart;
	}
	
	public void setBookincart(Vector<Booklist> bookincart){
		this.bookincart=bookincart;
	}
	
	public int getTotal_price() {
		return total_price;
	}
	
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	
	public int getId() {
		return id;
	}
	
	private void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int id) {
		this.user_id = id;
	}
	
	public int getBook_id() {
		return book_id;
	}
	
	public void setBook_id(int bookid) {
		this.book_id = bookid;
	}
	
	public int getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(int amount) {
		this.total_amount = amount;
	}
	
	
	public void setPricetset(Vector<Integer> priceset) {
		this.priceset = priceset;
	}
	
	public Vector<Integer> getPriceset() {
		return priceset;
	}
}

