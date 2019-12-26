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
import java.util.ArrayList;
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
                return;
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
        List<SurveyTaker> helperList = new ArrayList<>();
        SurveyTaker st = stList.get(stList.size()-1);
        for(int i=0; i<stList.size();i++){
            if(stList.get(i).getEmail().equals(email)){
                helperList.add(stList.get(i));
            }
        }
        if(!helperList.isEmpty()){
            st = helperList.get(helperList.size()-1);
        }
        
        List<Answers> answerList = as.findAll(st);
        List<Integer> sincerity = Arrays.asList(6,30,54,78);
        List<Integer> fairness = Arrays.asList(12,36,60,84);
        List<Integer> greedAvoidance = Arrays.asList(18,42,66,90);
        List<Integer> modesty = Arrays.asList(24,48,72,96);
        
        List<Integer> fearfulness = Arrays.asList(5,29,53,77);
        List<Integer> anxiety = Arrays.asList(11,35,59,83);
        List<Integer> dependence = Arrays.asList(17,41,65,89);
        List<Integer> sentimentality = Arrays.asList(23,47,71,95);
        
        List<Integer> socialSelfEsteem = Arrays.asList(4,28,52,76);
        List<Integer> socialBoldness = Arrays.asList(10,34,58,82);
        List<Integer> sociability = Arrays.asList(16,40,64,88);
        List<Integer> liveliness = Arrays.asList(22,46,70,94);
        
        List<Integer> forgiveness =  Arrays.asList(3,27,51,75);
        List<Integer> gentleness = Arrays.asList(9,33,57,81);
        List<Integer> flexibility = Arrays.asList(15,39,63,87);
        List<Integer> patience = Arrays.asList(21,45,69,93);
        
        List<Integer> organization = Arrays.asList(2,26,50,74);
        List<Integer> diligence = Arrays.asList(8,32,56,80);
        List<Integer> perfectionism = Arrays.asList(14,38,62,86);
        List<Integer> prudence = Arrays.asList(20,44,68,92);
        
        List<Integer> aestheticAppreciation = Arrays.asList(1,25,49,73);
        List<Integer> inquisitiveness = Arrays.asList(7,31,55,79);
        List<Integer> creativity = Arrays.asList(13,37,61,85);
        List<Integer> unconventionality = Arrays.asList(19,43,67,91);
        
        List<Integer> altruism = Arrays.asList(97,98,99,100);
        
        
        double sinceritySum = 0.0;
        double fairnessSum = 0.0;
        double greedAvoidanceSum = 0.0;
        double modestySum = 0.0;
        
        double fearfulnessSum = 0.0;
        double anxietySum = 0.0;
        double dependenceSum = 0.0;
        double sentimentalitySum = 0.0;
        
        double socialSelfEsteemSum = 0.0;
        double socialBoldnessSum = 0.0;
        double sociabilitySum = 0.0;
        double livelinessSum = 0.0;
        
        double forgivenessSum = 0.0;
        double gentlenessSum = 0.0;
        double flexibilitySum = 0.0;
        double patienceSum = 0.0;
        
        double organizationSum = 0.0;
        double diligenceSum = 0.0;
        double perfectionismSum = 0.0;
        double prudenceSum = 0.0;
        
        double aestheticAppreciationSum = 0.0;
        double inquisitivenessSum = 0.0;
        double creativitySum = 0.0;
        double unconventionalitySum = 0.0;
        
        double altruisSum = 0.0;
        
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
            else if(fearfulness.contains(questionNumber)){
                if(questionNumber == 5 || questionNumber == 53){
                    fearfulnessSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            fearfulnessSum+=1;
                            break;
                        case 4:
                            fearfulnessSum+=2;
                            break;
                        case 2:
                            fearfulnessSum+=4;
                            break;
                        case 1:
                            fearfulnessSum+=5;
                            break;
                        default:
                            fearfulnessSum+=answer.getAnswer();
                            break;
                    }
                }
                
            }
            else if(anxiety.contains(questionNumber)){
                if(questionNumber == 11 || questionNumber == 83){
                    anxietySum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            anxietySum+=1;
                            break;
                        case 4:
                            anxietySum+=2;
                            break;
                        case 2:
                            anxietySum+=4;
                            break;
                        case 1:
                            anxietySum+=5;
                            break;
                        default:
                            anxietySum+=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(dependence.contains(questionNumber)){
                if(questionNumber == 17 || questionNumber == 65){
                    dependenceSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            dependenceSum+=1;
                            break;
                        case 4:
                            dependenceSum+=2;
                            break;
                        case 2:
                            dependenceSum+=4;
                            break;
                        case 1:
                            dependenceSum+=5;
                            break;
                        default:
                            dependenceSum+=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(sentimentality.contains(questionNumber)){
                if(questionNumber == 23 || questionNumber == 47 || questionNumber == 71){
                    sentimentalitySum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            sentimentalitySum+=1;
                            break;
                        case 4:
                            sentimentalitySum+=2;
                            break;
                        case 2:
                            sentimentalitySum+=4;
                            break;
                        case 1:
                            sentimentalitySum+=5;
                            break;
                        default:
                            sentimentalitySum+=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(socialSelfEsteem.contains(questionNumber)){
                if(questionNumber == 4 || questionNumber == 28){
                    socialSelfEsteemSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            socialSelfEsteemSum +=1;
                            break;
                        case 4:
                            socialSelfEsteemSum +=2;
                            break;
                        case 2:
                            socialSelfEsteemSum +=4;
                            break;
                        case 1:
                            socialSelfEsteemSum +=5;
                            break;
                        default:
                            socialSelfEsteemSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(socialBoldness.contains(questionNumber)){
                if(questionNumber == 34 || questionNumber == 58){
                    socialBoldnessSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            socialBoldnessSum +=1;
                            break;
                        case 4:
                            socialBoldnessSum +=2;
                            break;
                        case 2:
                            socialBoldnessSum +=4;
                            break;
                        case 1:
                            socialBoldnessSum +=5;
                            break;
                        default:
                            socialBoldnessSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(sociability.contains(questionNumber)){
                if(questionNumber == 40 || questionNumber == 64 || questionNumber == 88){
                    sociabilitySum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            sociabilitySum +=1;
                            break;
                        case 4:
                            sociabilitySum +=2;
                            break;
                        case 2:
                            sociabilitySum +=4;
                            break;
                        case 1:
                            sociabilitySum +=5;
                            break;
                        default:
                            sociabilitySum +=answer.getAnswer();
                            break;
                    }
                }   
            }
            else if(liveliness.contains(questionNumber)){
                if(questionNumber == 22 || questionNumber == 46){
                    livelinessSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            livelinessSum +=1;
                            break;
                        case 4:
                            livelinessSum +=2;
                            break;
                        case 2:
                            livelinessSum +=4;
                            break;
                        case 1:
                            livelinessSum +=5;
                            break;
                        default:
                            livelinessSum +=answer.getAnswer();
                            break;
                    }
                }   
            }
            else if(forgiveness.contains(questionNumber)){
                if(questionNumber == 3 || questionNumber == 27){
                    forgivenessSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            forgivenessSum +=1;
                            break;
                        case 4:
                            forgivenessSum +=2;
                            break;
                        case 2:
                            forgivenessSum +=4;
                            break;
                        case 1:
                            forgivenessSum +=5;
                            break;
                        default:
                            forgivenessSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(gentleness.contains(questionNumber)){
                if(questionNumber == 33 || questionNumber == 57 || questionNumber == 81){
                    gentlenessSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            gentlenessSum +=1;
                            break;
                        case 4:
                            gentlenessSum +=2;
                            break;
                        case 2:
                            gentlenessSum +=4;
                            break;
                        case 1:
                            gentlenessSum +=5;
                            break;
                        default:
                            gentlenessSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(flexibility.contains(questionNumber)){
                if(questionNumber == 39){
                    flexibilitySum += 39;
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            flexibilitySum +=1;
                            break;
                        case 4:
                            flexibilitySum +=2;
                            break;
                        case 2:
                            flexibilitySum +=4;
                            break;
                        case 1:
                            flexibilitySum +=5;
                            break;
                        default:
                            flexibilitySum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(patience.contains(questionNumber)){
                if(questionNumber == 45 || questionNumber == 69){
                    patienceSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            patienceSum +=1;
                            break;
                        case 4:
                            patienceSum +=2;
                            break;
                        case 2:
                            patienceSum +=4;
                            break;
                        case 1:
                            patienceSum +=5;
                            break;
                        default:
                            patienceSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(organization.contains(questionNumber)){
                if(questionNumber == 2 || questionNumber == 26){
                    organizationSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            organizationSum +=1;
                            break;
                        case 4:
                            organizationSum +=2;
                            break;
                        case 2:
                            organizationSum +=4;
                            break;
                        case 1:
                            organizationSum +=5;
                            break;
                        default:
                            organizationSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(diligence.contains(questionNumber)){
                if(questionNumber == 8 || questionNumber == 32){
                    diligenceSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            diligenceSum +=1;
                            break;
                        case 4:
                            diligenceSum+=2;
                            break;
                        case 2:
                            diligenceSum +=4;
                            break;
                        case 1:
                            diligenceSum +=5;
                            break;
                        default:
                            diligenceSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(perfectionism.contains(questionNumber)){
                if(questionNumber == 14 || questionNumber == 62 || questionNumber == 86){
                    perfectionismSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            perfectionismSum +=1;
                            break;
                        case 4:
                            perfectionismSum+=2;
                            break;
                        case 2:
                            perfectionismSum +=4;
                            break;
                        case 1:
                            perfectionismSum +=5;
                            break;
                        default:
                            perfectionismSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(prudence.contains(questionNumber)){
                if(questionNumber == 68){
                    prudenceSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            prudenceSum +=1;
                            break;
                        case 4:
                            prudenceSum +=2;
                            break;
                        case 2:
                            prudenceSum +=4;
                            break;
                        case 1:
                            prudenceSum +=5;
                            break;
                        default:
                            prudenceSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(aestheticAppreciation.contains(questionNumber)){
                if( questionNumber == 49 || questionNumber == 73){
                    aestheticAppreciationSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            aestheticAppreciationSum +=1;
                            break;
                        case 4:
                            aestheticAppreciationSum +=2;
                            break;
                        case 2:
                            aestheticAppreciationSum +=4;
                            break;
                        case 1:
                            aestheticAppreciationSum +=5;
                            break;
                        default:
                            aestheticAppreciationSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(inquisitiveness.contains(questionNumber)){
                if( questionNumber == 7 || questionNumber == 31){
                    inquisitivenessSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            inquisitivenessSum +=1;
                            break;
                        case 4:
                            inquisitivenessSum +=2;
                            break;
                        case 2:
                            inquisitivenessSum +=4;
                            break;
                        case 1:
                            inquisitivenessSum +=5;
                            break;
                        default:
                            inquisitivenessSum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(creativity.contains(questionNumber)){
                if(questionNumber == 37 || questionNumber == 61){
                    creativitySum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            creativitySum +=1;
                            break;
                        case 4:
                            creativitySum +=2;
                            break;
                        case 2:
                            creativitySum +=4;
                            break;
                        case 1:
                            creativitySum +=5;
                            break;
                        default:
                            creativitySum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(unconventionality.contains(questionNumber)){
                if(questionNumber == 43 || questionNumber == 67){
                    unconventionalitySum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            unconventionalitySum +=1;
                            break;
                        case 4:
                            unconventionalitySum +=2;
                            break;
                        case 2:
                            unconventionalitySum +=4;
                            break;
                        case 1:
                            unconventionalitySum+=5;
                            break;
                        default:
                            unconventionalitySum +=answer.getAnswer();
                            break;
                    }
                }
            }
            else if(altruism.contains(questionNumber)){
                if(questionNumber == 97 || questionNumber == 98){
                    altruisSum += answer.getAnswer();
                }
                else{
                    if(null!=answer.getAnswer())switch (answer.getAnswer()){
                        case 5:
                            altruisSum +=1;
                            break;
                        case 4:
                            altruisSum +=2;
                            break;
                        case 2:
                            altruisSum +=4;
                            break;
                        case 1:
                            altruisSum +=5;
                            break;
                        default:
                            altruisSum +=answer.getAnswer();
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
        
        double finalFearfulness = fearfulnessSum/4;
        double finalAnxiety = anxietySum/4;
        double finalDependence = dependenceSum/4;
        double finalSentimentality = sentimentalitySum/4;
        double emotionality = ((finalFearfulness + finalAnxiety + finalDependence + finalSentimentality)/4);
        
        double finalSocialSelfSteem = socialSelfEsteemSum/4;
        double finalSocialBoldness = socialBoldnessSum/4;
        double finalSociability = sociabilitySum/4;
        double finalLiveliness = livelinessSum/4;
        double extraversion = (finalSociability + finalSocialBoldness + finalSocialSelfSteem + finalLiveliness)/4;
        
        double finalForgiveness = forgivenessSum/4;
        double finalGentleness = gentlenessSum/4;
        double finalFlexiblity = flexibilitySum/4;
        double finalPatience = patienceSum/4;
        double agreeableness = (finalForgiveness + finalGentleness + finalFlexiblity + finalPatience)/4;
        
        double finalOrganization = organizationSum/4;
        double finalDiligence = diligenceSum/4;
        double finalPerfectionism = perfectionismSum/4;
        double finalPrudence = prudenceSum/4;
        double conscientousness = (finalOrganization + finalDiligence + finalPerfectionism + finalPrudence)/4;
        
        double finalAestheticAppreciation = aestheticAppreciationSum/4;
        double finalInquisitiveness = inquisitivenessSum/4;
        double finalCreativity = creativitySum/4;
        double finalUnconventiality = unconventionalitySum/4;
        double openessToExperience = (finalAestheticAppreciation + finalInquisitiveness + finalCreativity + finalUnconventiality)/4;
        
        double altruis = altruisSum/4;
        
        values.put("altruis", MathUtil.redondearDecimales(altruis, 2)+"");
        
        values.put("aestheticAppreciation",MathUtil.redondearDecimales(finalAestheticAppreciation, 2)+"");
        values.put("inquisitiveness",MathUtil.redondearDecimales(finalInquisitiveness, 2)+"");
        values.put("creativity",MathUtil.redondearDecimales(finalCreativity, 2)+"");
        values.put("unconventionality",MathUtil.redondearDecimales(finalUnconventiality, 2)+"");
        values.put("openessToExperience", MathUtil.redondearDecimales(openessToExperience, 2)+"");
        
        values.put("organization",MathUtil.redondearDecimales(finalOrganization, 2)+"");
        values.put("diligence",MathUtil.redondearDecimales(finalDiligence, 2)+"");
        values.put("perfectionism",MathUtil.redondearDecimales(finalPerfectionism, 2)+"");
        values.put("prudence", MathUtil.redondearDecimales(finalPrudence, 2)+"");
        values.put("conscientousness",MathUtil.redondearDecimales(conscientousness, 2)+"");
        
        values.put("forgiveness",MathUtil.redondearDecimales(finalForgiveness, 2)+"");
        values.put("gentleness",MathUtil.redondearDecimales(finalGentleness, 2)+"");
        values.put("flexibility",MathUtil.redondearDecimales(finalFlexiblity, 2)+"");
        values.put("patience",MathUtil.redondearDecimales(finalPatience, 2)+"");
        values.put("agreeableness", MathUtil.redondearDecimales(agreeableness, 2)+"");
        
        values.put("socialSelfSteem",MathUtil.redondearDecimales(finalSocialSelfSteem, 2)+"");
        values.put("socialBoldness",MathUtil.redondearDecimales(finalSocialBoldness, 2)+"");
        values.put("sociability",MathUtil.redondearDecimales(finalSociability, 2)+"");
        values.put("liveliness",MathUtil.redondearDecimales(finalLiveliness, 2)+"");
        values.put("extraversion",MathUtil.redondearDecimales(extraversion, 2)+"");
        
        values.put("fearfulness",MathUtil.redondearDecimales(finalFearfulness, 2)+"");
        values.put("anxiety",MathUtil.redondearDecimales(finalAnxiety, 2)+"");
        values.put("dependence",MathUtil.redondearDecimales(finalDependence, 2)+"");
        values.put("sentimentality",MathUtil.redondearDecimales(finalSentimentality, 2)+"");
        values.put("emotionality", MathUtil.redondearDecimales(emotionality, 2)+"");
        
        values.put("sincerity", MathUtil.redondearDecimales(finalSincerity,2)+"");
        values.put("fairness", MathUtil.redondearDecimales(finalFairness,2)+"");
        values.put("greedAvoidance", MathUtil.redondearDecimales(finalGreedAvoid,2)+"");
        values.put("modesty", MathUtil.redondearDecimales(finalModesty,2)+"");
        values.put("honestyHumility", MathUtil.redondearDecimales(honestyHumility,2)+"");
        return values;
    }
    
}
