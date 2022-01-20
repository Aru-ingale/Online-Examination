package com.example.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/* DAO classes are used to write database logic
 * @Repository is used for them .
 DAO(Data Access object)
 
   *
   */
@Repository
public class MyDao {

	@Autowired
	SessionFactory factory;
	
	Session sesssion;
	
	
	public List<Question> getQuestion()
	{
		sesssion=factory.openSession();
		System.out.println("session created..");
		List<Question> l=sesssion.createQuery("from Question").list();
		
		return l;
	}
	

	public String getPassword(String name) {
		
		sesssion=factory.openSession();
		System.out.println("session created..");
		
		System.out.println(name);
		User user=sesssion.get(User.class,name);  
		if(user==null)
			return null;
		else
			
		return user.getPass();
	}

	public void insert(User user) {
				
		sesssion=factory.openSession();
		
		Transaction tx=sesssion.beginTransaction();
		
				sesssion.save(user);
		
		tx.commit();
		
		
	}
	
}
