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
import com.survey.util.MathUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javafx.application.Application;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public void addAnswer(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="option") Integer option, @RequestParam(name="question") Integer questionId){
        if(request.getSession().getAttribute("surveyTaker")==null){
            return;
        }
        Logger l = Logger.getLogger("main");
        List<Question> question = qs.findAll();
        Answers answer = new Answers();
        SurveyTaker surveyTaker = (SurveyTaker) request.getSession().getAttribute("surveyTaker");    
        answer.setAnswer(option);
        answer.setQuestion(qs.findByQuestionNumber(questionId));
        answer.setSurveyTaker(surveyTaker);
        as.saveAnswers(answer);
    }
    
    @RequestMapping(value="/setEmail", method=RequestMethod.POST)
    public void setEmail(HttpServletRequest request, HttpServletResponse response, @RequestParam(name="email") String email){
        HttpSession session = request.getSession();
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
    
    @RequestMapping(value="/processAnswers", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody HashMap processAnswers(HttpServletRequest request, HttpServletResponse response, @RequestParam("email") String email){
        HashMap<String,String> values = new HashMap();
        List<SurveyTaker> stList = sts.find(request.getRemoteAddr());
        SurveyTaker st = stList.get(stList.size()-1);
        List<Answers> answerList = as.findAll(st);
        List<Integer> sincerity = Arrays.asList(6,30,54,78);
        List<Integer> fairness = Arrays.asList(12,36,60,84);
        List<Integer> greedAvoidance = Arrays.asList(18,42,66,90);
        List<Integer> modesty = Arrays.asList(24,48,72,96);
        
        double sinceritySum = 0.0;
        double fairnessSum = 0.0;
        double greedAvoidanceSum = 0.0;
        double modestySum = 0.0;
        
        for(int i=0;i<answerList.size()-1;i++){
            Answers answer = answerList.get(i);
            int questionNumber = answer.getQuestion().getNumber();
            if(sincerity.contains(questionNumber)){
                if(questionNumber == 30 || questionNumber == 78){
                    sinceritySum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()) {
                        case 5:
                            sinceritySum+=1;
                            break;
                        case 4:
                            sinceritySum+=2;
                            break;
                        case 2:
                            sinceritySum+=4;
                            break;
                        case 1:
                            sinceritySum+=5;
                            break;
                        default:
                            sinceritySum+=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(fairness.contains(questionNumber)){
                if(questionNumber == 60){
                    fairnessSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            fairnessSum+=1;
                            break;
                        case 4:
                            fairnessSum+=2;
                            break;
                        case 2:
                            fairnessSum+=4;
                            break;
                        case 1:
                            fairnessSum+=5;
                            break;
                        default:
                            fairnessSum+=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(greedAvoidance.contains(questionNumber)){
                if(questionNumber == 18){
                    greedAvoidanceSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            greedAvoidanceSum+=1;
                            break;
                        case 4:
                            greedAvoidanceSum+=2;
                            break;
                        case 2:
                            greedAvoidanceSum+=4;
                            break;
                        case 1:
                            greedAvoidanceSum+=5;
                            break;
                        default:
                            greedAvoidanceSum+=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(modesty.contains(questionNumber)){
                if(questionNumber == 24 || questionNumber == 48){
                    modestySum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            modestySum+=1;
                            break;
                        case 4:
                            modestySum+=2;
                            break;
                        case 2:
                            modestySum+=4;
                            break;
                        case 1:
                            modestySum+=5;
                            break;
                        default:
                            modestySum+=answer.getAnswer();
                            break;
                    }
                }
            }
        }
        double finalSincerity = sinceritySum/4;
        double finalFairness = fairnessSum/4;
        double finalGreedAvoid = greedAvoidanceSum/4;
        double finalModesty = modestySum/4;
        double honestyHumility = (finalSincerity + finalFairness + finalGreedAvoid + finalModesty)/4 ;
        
        values.put("sincerity", MathUtil.redondearDecimales(finalSincerity,2)+"");
        values.put("fairness", MathUtil.redondearDecimales(finalFairness,2)+"");
        values.put("greedAvoidance", MathUtil.redondearDecimales(finalGreedAvoid,2)+"");
        values.put("modesty", MathUtil.redondearDecimales(finalModesty,2)+"");
        values.put("honestyHumility", MathUtil.redondearDecimales(honestyHumility,2)+"");
        return values;
    }
    
}
