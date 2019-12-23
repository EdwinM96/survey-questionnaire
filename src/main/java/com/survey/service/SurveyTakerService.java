/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.service;

import com.survey.repository.SurveyTakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.survey.domain.SurveyTaker;
import java.util.List;
import org.springframework.stereotype.Service;
/**
 *
 * @author HP PC
 */
@Service
public class SurveyTakerService {
    
    @Autowired
    SurveyTakerRepository surveyTakerRepo;
    
    
    @Transactional
    public SurveyTaker saveSurveyTaker(SurveyTaker surveyTaker){
        return surveyTakerRepo.saveAndFlush(surveyTaker);
    }
    
    public List<SurveyTaker> findAll(){
        return surveyTakerRepo.findAll();
    }
    
    public List<SurveyTaker> find(String ip){
        SurveyTaker st = new SurveyTaker();
        st.setIp(ip);
        return surveyTakerRepo.findSurveyTakerByIp(ip);
    }
    
}
