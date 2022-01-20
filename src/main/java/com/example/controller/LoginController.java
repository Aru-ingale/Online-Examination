package com.example.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.Answer;
import com.example.dao.Question;
import com.example.dao.User;
import com.example.service.QuestionService;

@Controller
public class LoginController {

	@Autowired
	QuestionService service;
	
	@RequestMapping("/login")
	public ModelAndView login(User user , HttpServletRequest request)
	{
		List<Question> list = service.getAllQuestions();
		list.forEach(Question -> System.out.println(Question));
		
		if(service.validate(user))
		{
			   // put questions in session
			   
			   HttpSession session=request.getSession();
			   session.setAttribute("questions",list);
			   
			   session.setAttribute("qno",-1);
			   session.setAttribute("score",0);
			   
			   session.setAttribute("submittedDetails", new HashMap<Integer,Answer>());
			   
			  ModelAndView mv = new ModelAndView();
			  mv.setViewName("questions");
			  mv.addObject("wel","welcome to online exam " + user.getName());
			  mv.addObject("allquestions",list);
			  return mv;
			    
			//return new ModelAndView("questions","wel","Welcome to online exam  " + user.getName());
		}
		
		else
		{
			return new ModelAndView("index","error","Invalid Password");
		}
	}
	
	
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response,User user) throws Exception {
		
		service.insert(user);
		
		PrintWriter pw=response.getWriter();
		
		pw.println("<script>alert 'Registration Succefully Please Login' </script>");
		
		return new ModelAndView("index");
 
	}
	
	@RequestMapping("/registration")
	public ModelAndView showRegistration(HttpServletRequest req,HttpServletResponse res)
	{ 
		
				return new ModelAndView("registration");
	}

	
	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
