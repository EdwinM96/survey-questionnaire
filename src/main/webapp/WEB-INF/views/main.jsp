<%-- 
    Document   : main
    Created on : 21/12/2019, 08:12:41 PM
    Author     : HP PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Personality Survey</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <style>
            
        </style>
    </head>
    <body>
        <div class="container" style="margin-top:30px;">
            <div class="text-center"><h2 style="color:blue">Your title here</h2></div>
            <div class="row" style="margin-top:10px">
                <div class="col-8">Please provide your email address</div>
            </div>
            <div class="row" style="margin-top:20px; margin-bottom:20px">
                <div class="col-4">Email:</div>
                <div class="col-4"><input type="text" class="form-control"></div>
            </div>
            <div>
                <div>On the following page you will find a series of statements about you. Please read
each statement and decide how much you agree or disagree with that statement.
Then write your response in the space next to the statement using the following
scale:</div>
                    <strong>
                        <div class="row">
                            <div class="col-5"></div>
                            <div class="col-7" style="margin-top:10px">5 = strongly agree</div>
                        </div>
                        <div class="row">
                            <div class="col-5"></div>
                            <div class="col-7">4 = agree</div>
                        </div>
                        <div class="row">
                            <div class="col-5"></div>
                            <div class="col-7">3 = neutral (neither agree nor disagree)</div>
                        </div>
                        <div class="row">
                            <div class="col-5"></div>
                            <div class="col-7">2 = disagree</div>
                        </div>
                        <div class="row">
                            <div class="col-5"></div>
                            <div class="col-7">1 = strongly disagree</div>
                        </div>
                        </strong>
Please answer every statement, even if you are not completely sure of your response. 
                
            </div>
            <div style="margin-top:40px;"></div>
            <c:forEach items="${questions}" var="question" varStatus="index">
                <div class="row" style="margin-top:20px;">
                    <div class="col-9"><strong>${index.index+1}.</strong> ${question.question}</div>
                    <div class="col-3">
                        <div class="form group">
                            <select name="opcion" class="form-control" id="answer-question${index.index+1}">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="4">5</option>
                           </select>
                        </div>
                    </div>
                </div>
            </c:forEach>
            
            <div class="center-text text-center" style="margin-top:30px; margin-bottom:40px;">
                <button class="btn btn-primary">Submit</button>
            </div>
        </div>
    </body>
</html>
