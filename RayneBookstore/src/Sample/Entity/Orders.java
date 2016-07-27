package Sample.Entity;

import java.util.Date;
import java.util.Map;
import java.util.Vector;

public class Orders {
	private int id;
	private int user_id;
	private int book_id;
	private int amount;
	private Date date;
	private Map<Integer,Integer> idmap;
	private Map<Integer,Integer> amountmap;
	private Map<Integer,Date> datemap;
	private Vector<Booklist> bookordered;
	
	public Map<Integer,Date> getDatemap(){
		return datemap;
	}
	
	public void setDatemap(Map<Integer,Date> map){
		this.datemap=map;
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
	
	public Vector<Booklist> getBookordered(){
		return bookordered;
	}
	
	public void setBookordered(Vector<Booklist> bookordered){
		this.bookordered=bookordered;
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
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}
