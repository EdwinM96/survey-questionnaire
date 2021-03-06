/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.survey.domain.Answers;
import java.util.List;
import com.survey.domain.SurveyTaker;
/**
 *
 * @author HP PC
 */
@Repository
public interface AnswersRepository extends JpaRepository<Answers, Integer>{
    
    public List<Answers> findAll();
    
    public List<Answers> findBySurveyTaker(SurveyTaker st);
    
}
