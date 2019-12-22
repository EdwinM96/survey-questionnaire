/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.service;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.survey.domain.Answers;
import com.survey.repository.AnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author HP PC
 */
@Service
public class AnswersService {
    
    @Autowired
    AnswersRepository answersRepo;
    
    public List<Answers> findAll(){
        return  answersRepo.findAll();
    }
    
    public void saveAnswers(Answers answers){
        answersRepo.saveAndFlush(answers);
    }
    
}
