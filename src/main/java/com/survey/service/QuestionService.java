/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.service;

import com.survey.repository.QuestionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.survey.domain.Question;
/**
 *
 * @author HP PC
 */
@Service
public class QuestionService {
    
    @Autowired
    QuestionRepository questionRepo;
    
    public List<Question> findAll(){
        return questionRepo.findAllByOrderByNumberAsc();
    }
    public Question findOne(Integer id){
        return questionRepo.findOne(id);
    }
    public Question findByQuestionNumber(Integer number){
        return questionRepo.findByNumber(number);
    }
    
}
