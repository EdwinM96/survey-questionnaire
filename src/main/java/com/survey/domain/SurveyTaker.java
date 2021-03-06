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

/**
 *
 * @author HP PC
 */
@Entity
@Table(schema="public", name="SurveyTaker")
public class SurveyTaker {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="SurveyTakerID")
    private Integer id;
    
    @Column(name="Email")
    private String email;
    
    @Column(name="IP")
    private String ip;

    public SurveyTaker() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    
    
}
