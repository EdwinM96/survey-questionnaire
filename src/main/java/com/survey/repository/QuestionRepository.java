/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.survey.domain.Question;
import java.util.List;
/**
 *
 * @author HP PC
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
    
    public List<Question> findAll();
    
    public List<Question> findAllByOrderByNumberAsc();
    
    public Question findByNumber(Integer number);
    
}
