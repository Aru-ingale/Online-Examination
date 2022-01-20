package com.example.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.example.dao.Answer;
import com.example.dao.Question;

// HashMap hashmap = new HashMap();
// hashmap.put(1,90)
// hashmap.put(1,80);
// LoginController ===>  session.setAttribute("qno",-1);

// QuestionControlller ===> int x= session.getAttribute("qno") + 1;
// session.setAttribute("qno",x);


@Controller
public class QuestionController {
	
	@RequestMapping("/next")
	public ModelAndView next(HttpServletRequest req,HttpServletResponse res)
	{
try {
		HttpSession session=req.getSession();
		
		int nextQno=(Integer)session.getAttribute("qno") + 1;
			
		ArrayList<Question> al=(ArrayList<Question>)session.getAttribute("questions");
		
		if(nextQno==al.size())
		{			
			//nextQno=0;
			ModelAndView mv = new ModelAndView();
			mv.setViewName("Result");
			return mv;
			
		}				
		session.setAttribute("qno",nextQno);//updating qno from session object
			
		Question question=al.get(nextQno);

		
		ModelAndView mv = new ModelAndView();
		mv.addObject("question",question);
		mv.setViewName("questions");
		
		System.out.println(question);
		return mv;
}
catch(Exception e)
{
	
}

return null;
		
	}
		
		
		
	
	
	
	@RequestMapping("/submitAns")
			
	public void submitAnswer(HttpServletRequest req,HttpServletResponse res)
	{
		HttpSession session=req.getSession();
		System.out.println();
		
		String submittedAnswer=req.getParameter("ans");
		String qtext=req.getParameter("qtxt");
		String originalAnswer=req.getParameter("oans");
		int qno=Integer.parseInt(req.getParameter("qno"));
		
		
		System.out.println("Submitted Answer :- " + submittedAnswer + "Original Answer :- " + originalAnswer);
		//  Key      Value
		// Qno     Answer
		// 	1      AnswerObject   
		//   HashMap Object
		
		HashMap<Integer, Answer> hm=(HashMap<Integer, Answer>)session.getAttribute("submittedDetails");
		hm.put(qno,new Answer(qno, submittedAnswer, originalAnswer,qtext));
		
		session.setAttribute("submittedDetails",hm);
		
		
	  }
	
	
	
	
	@RequestMapping("/endExam")
	public ModelAndView endExam(HttpServletRequest req,HttpServletResponse res)
	
	{
		HttpSession session=req.getSession();
		
		HashMap<Integer, Answer> hm=(HashMap<Integer, Answer>)session.getAttribute("submittedDetails");
		Collection<Answer> c=hm.values();
		
//		for(Answer ans:c)
//		{
//			System.out.println(ans.submittedAnswer + " " + ans.originalAnswer);
//		}
		
		
		for(Answer obj : c)
		{
			if(obj.originalAnswer.equals(obj.submittedAnswer))
			{
							
				session.setAttribute("score",(Integer)session.getAttribute("score") + 1);
				
			}
		}
		
		String show="Your Score is " + (Integer)session.getAttribute("score");
		System.out.println(show);
	
		return new ModelAndView("Result","show", show);
		
		
	}
	
	
	
	@RequestMapping("/previous")
	public ModelAndView previous(HttpServletRequest req,HttpServletResponse res)
	{
		
		
		HttpSession session=req.getSession();
		
		ArrayList<Question> al=(ArrayList<Question>)session.getAttribute("questions");
		
		int preQno=(Integer)session.getAttribute("qno") - 1;
		
		
		if(preQno<0)
			preQno=al.size()-1;
		
		session.setAttribute("qno",preQno);

		
		Question question=al.get(preQno);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("question",question);
		mv.setViewName("questions");
		
		System.out.println(question);
		return mv;
		


		
	}
	
	
	

}
