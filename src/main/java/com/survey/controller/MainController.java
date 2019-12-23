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
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @RequestMapping(value="/addAnswer", method=RequestMethod.POST)
    public void addAnswer(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="option") String option, @RequestParam(name="question") Integer questionId){
        if(request.getSession().getAttribute("surveyTaker")==null){
            return;
        }
        Answers answer = new Answers();
        Question question = new Question();
        question.setId(questionId);
        SurveyTaker surveyTaker = (SurveyTaker) request.getSession().getAttribute("surveyTaker");    
        answer.setAnswer(option);
        answer.setQuestion(question);
        answer.setSurveyTaker(surveyTaker);
        as.saveAnswers(answer);
    }
    
    @RequestMapping(value="/setEmail", method=RequestMethod.POST)
    public void setEmail(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="email") String email){
        HttpSession session = request.getSession();
        Logger l = Logger.getLogger("controller");
        l.info("Local address: "+request.getRemoteAddr());
        if(session.getAttribute("surveyTaker")!=null){
            SurveyTaker thisST = (SurveyTaker) session.getAttribute("surveyTaker");
            thisST.setEmail(email);
            sts.saveSurveyTaker(thisST);
            return;
        }
        else{
            List<SurveyTaker> surveyTakers = sts.find(request.getRemoteAddr());
            if(!surveyTakers.isEmpty()){
                SurveyTaker st = surveyTakers.get(surveyTakers.size()-1);
                st.setEmail(email);
                session.setAttribute("surveyTaker", st);
            }
        }
        SurveyTaker surveyTaker = new SurveyTaker();
        surveyTaker.setEmail(email);
        surveyTaker.setIp(request.getRemoteAddr());
        SurveyTaker userSurveyTaker = sts.saveSurveyTaker(surveyTaker);       
        session.setAttribute("surveyTaker", userSurveyTaker);
    }
    
}
