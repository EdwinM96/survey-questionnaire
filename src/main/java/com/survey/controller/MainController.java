/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.controller;

import com.survey.domain.Answers;
import com.survey.domain.Question;
import com.survey.domain.SurveyTaker;
import com.survey.service.AnswersService;
import com.survey.service.QuestionService;
import com.survey.service.SurveyTakerService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author HP PC
 */

@Controller
public class MainController {
    
    @Autowired
    QuestionService qs;
    
    @Autowired
    SurveyTakerService sts;
    
    @Autowired
    AnswersService as;
    
    @RequestMapping("/")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("main");
        mv.addObject("questions", qs.findAll());
        return mv;        
    }
    
    @RequestMapping(name="/addAnswer", method=RequestMethod.POST)
    public void addAnswer(HttpServletRequest request, HttpServletResponse response){
        Answers answer = new Answers();
        Question question = new Question();
        SurveyTaker surveyTaker = new SurveyTaker();
        
    }
    
    @RequestMapping(name="/setEmail", method=RequestMethod.POST)
    public void setEmail(HttpServletRequest request, HttpServletResponse response){
        SurveyTaker surveyTaker = new SurveyTaker();
        surveyTaker.setEmail((String) request.getParameter("email"));
        sts.saveSurveyTaker(surveyTaker);
        SurveyTaker userSurveyTaker = new SurveyTaker();
        List<SurveyTaker> surveyTakers = sts.findAll();
        for(SurveyTaker st:surveyTakers){
            if(st.getEmail().equals(surveyTaker.getEmail())){
                userSurveyTaker = st;
                break;
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("surveyTaker", userSurveyTaker);
    }
    
}
