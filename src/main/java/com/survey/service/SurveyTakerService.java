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
/**
 *
 * @author HP PC
 */
@Repository
public class SurveyTakerService {
    
    @Autowired
    SurveyTakerRepository surveyTakerRepo;
    
    
    @Transactional
    public void saveSurveyTaker(SurveyTaker surveyTaker){
        surveyTakerRepo.saveAndFlush(surveyTaker);
    }
    
    public List<SurveyTaker> findAll(){
        return surveyTakerRepo.findAll();
    }
    
}
