/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.util;

import java.time.LocalDateTime;
import java.util.Date;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author Edwin Morales
 */
@WebListener
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("-- HttpSessionListener#sessionCreated invoked --");
      HttpSession session = se.getSession();
      System.out.println("session id: " + session.getId());
      session.setMaxInactiveInterval(60*60*2);//in seconds
    }
  

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
      System.out.println("-- HttpSessionListener#sessionDestroyed invoked --" + LocalDateTime.now());
  }
}
