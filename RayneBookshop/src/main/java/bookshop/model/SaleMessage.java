package bookshop.model;

import org.hibernate.Session;

import bookshop.util.HibernateUtil;

public class SaleMessage {
    
    private int basic;
    private int minus;
    private int discount;
    private String category;
     
    
    public void setBasic(int basic){
    	this.basic = basic;
    }
    
    public int getBasic(){
    	return basic;
    }
    
    public void setMinus(int minus){
    	this.minus = minus;
    }
    
    public int getMinus(){
    	return minus;
    }
    
    public void setDiscount(int discount){
    	this.discount = discount;
    }
    
    public int getDiscount(){
    	return discount;
    }
    
    public void setCategory(String category){
    	this.category = category;
    }
    
    public String getCategory(){
    	return category;
    }
 
}

