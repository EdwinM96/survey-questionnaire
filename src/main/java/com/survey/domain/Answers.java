/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author HP PC
 */
@Entity
@Table(schema="public", name="QuestionXSurveyTaker")
public class Answers {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="QuestionXSurveyTakerID")
    private Integer id;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="QuestionID")
    private Question question;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="SurveyTakerID")
    private SurveyTaker surveyTaker;
    
    @Column(name="Answer")
    private Integer answer;

    public Answers() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public SurveyTaker getSurveyTaker() {
        return surveyTaker;
    }

    public void setSurveyTaker(SurveyTaker surveyTaker) {
        this.surveyTaker = surveyTaker;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }
    
    
}
