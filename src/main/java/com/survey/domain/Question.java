/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author HP PC
 */
@Entity
@Table(schema="public", name="Question")
public class Question {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="QuestionID")
    private Integer id;
    
    @Column(name="Question")
    private String question;

    public Question() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    
    
    
}
