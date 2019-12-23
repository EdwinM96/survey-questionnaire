/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.repository;

import org.springframework.stereotype.Repository;
import com.survey.domain.SurveyTaker;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author HP PC
 */
@Repository
public interface SurveyTakerRepository extends JpaRepository<SurveyTaker, Integer>{
    
    public List<SurveyTaker> findSurveyTakerByIp(String ip);

    @Override
    public List<SurveyTaker> findAll();
    
}
