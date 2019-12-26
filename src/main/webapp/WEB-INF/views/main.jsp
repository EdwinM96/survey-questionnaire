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
        <style type="text/css">
          .lds-ring {
            display: inline-block;
            position: relative;
            width: 80px;
            height: 80px;
          }
          .lds-ring div {
            box-sizing: border-box;
            display: block;
            position: absolute;
            width: 64px;
            height: 64px;
            margin: 8px;
            border: 8px solid #cef;
            border-radius: 50%;
            animation: lds-ring 1.2s cubic-bezier(0.5, 0, 0.5, 1) infinite;
            border-color: grey transparent transparent transparent;
          }
          .lds-ring div:nth-child(1) {
            animation-delay: -0.45s;
          }
          .lds-ring div:nth-child(2) {
            animation-delay: -0.3s;
          }
          .lds-ring div:nth-child(3) {
            animation-delay: -0.15s;
          }
          @keyframes lds-ring {
            0% {
              transform: rotate(0deg);
            }
            100% {
              transform: rotate(360deg);
            }
          }

              
        </style>
    </head>
    <body>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script>
            var exportData;
            function exportResults() {
                var csvContent = "data:text/csv;charset=utf-8";
                csvContent+="Honesty-Humility,"+exportData.honestyHumility+"\r\n";
                csvContent+="Sincerity,"+exportData.sincerity+"\r\n";
                csvContent+="Fariness,"+exportData.fairness+"\r\n";
                csvContent+="Greed-Avoidance,"+exportData.greedAvoidance+"\r\n";
                csvContent+="Modesty,"+exportData.modesty+"\r\n";
                csvContent+="Emotionality,"+exportData.emotionality+"\r\n";
                csvContent+="Fearfulness,"+exportData.fearfulness+"\r\n";
                csvContent+="Anxiety,"+exportData.anxiety+"\r\n";
                csvContent+="Dependence,"+exportData.dependence+"\r\n";
                csvContent+="Sentimentality,"+exportData.sentimentality+"\r\n";
                csvContent+="Extraversion,"+exportData.extraversion+"\r\n";
                csvContent+="Social Self-Esteem,"+exportData.socialSelfEsteem+"\r\n";
                csvContent+="Social Boldness,"+exportData.socialBoldness+"\r\n";
                csvContent+="Sociability,"+exportData.sociability+"\r\n";
                csvContent+="Liveliness,"+exportData.liveliness+"\r\n";
                csvContent+="Agreeableness,"+exportData.agreeableness+"\r\n";
                csvContent+="Forgiveness,"+exportData.forgiveness+"\r\n";
                csvContent+="Gentleness,"+exportData.gentleness+"\r\n";
                csvContent+="Flexiblity,"+exportData.flexibility+"\r\n";
                csvContent+="Patience,"+exportData.patience+"\r\n";
                csvContent+="Conscientousness,"+exportData.conscientousness+"\r\n";
                csvContent+="Organization,"+exportData.organization+"\r\n";
                csvContent+="Diligence,"+exportData.diligence+"\r\n";
                csvContent+="Perfectionism,"+exportData.perfectionism+"\r\n";
                csvContent+="Prudence,"+exportData.prudence+"\r\n";
                csvContent+="Openness to Experience,"+exportData.openessToExperience+"\r\n";
                csvContent+="Aesthhetic Appreciation,"+exportData.aestheticAppreciation+"\r\n";
                csvContent+="Inquisitiveness,"+exportData.inquisitiveness+"\r\n";
                csvContent+="Creativity,"+exportData.creativity+"\r\n";
                csvContent+="Unconventionality,"+exportData.unconventionality+"\r\n";
                csvContent+="Altruis,"+exportData.altruis+"\r\n";
                
                var encodedUri = encodeURI(csvContent);
                    var link = document.createElement("a");
                    link.setAttribute("href", encodedUri);
                    link.setAttribute("download", "survey-results.csv");
                    document.body.appendChild(link);
                    link.click();       
            }
            
            function addOnblurEmail(input,name){
                $(input).removeClass("is-invalid");
            input.addEventListener("blur", function handler(e) {  
                    $.ajax({
                    type: "POST",
                            url: '${pageContext.request.contextPath}/setEmail',
                            data: $(name).serialize(),
                            success: function (data){
                                
                            }
                    })                

                e.currentTarget.removeEventListener("blur", handler);
                });              
            }
            function addOnBlurQuestion(input, name){
                console.info(input.value)
                console.info($(name).serialize());
                $(input).removeClass("is-invalid");
                input.addEventListener("blur", function handler(e){
                    $.ajax({
                        type: "POST",
                        url: '${pageContext.request.contextPath}/addAnswer',
                        data: $(name).serialize(),
                        success: function(data){
                        }
                    })
                })
            
            }
            function submitQuestions(){
                if($("#emailInput").val()===null || $("#emailInput").val()===""){
                    $("#messageModal").modal("show")
                    var elmt = document.getElementById("emailInput");
                    elmt.scrollIntoView();
                    $("#emailInput").addClass("is-invalid");
                    return;
                }
                else{
                    $("#loadingModal").modal("show");
                    setTimeout(function(){
                    console.info("Entre al listener del modal");
                        $.ajax({
                    type: "POST",
                            url: '${pageContext.request.contextPath}/setEmail',
                            data: $("#email").serialize(),
                            success: function (data){
                                
                            }
                    })
                        for(var i=1;i<=100;i++){
                        if($("#answer-question"+i).val()==="0"){
                            document.getElementById("answer-question"+i).scrollIntoView();
                            $("#answer-question"+i).addClass("is-invalid");
                            $("#loadingModal").modal('hide');
                            $("#messageModal").modal("show");
                            return;
                        }
                    else{
                        $.ajax({
                        type: "POST",
                        url: '${pageContext.request.contextPath}/addAnswer',
                        data: $("#question"+i).serialize(),
                        success: function(data){
                        }
                    })
                    }
                }
                
                $.ajax({
                        type: "GET",
                        url: '${pageContext.request.contextPath}/processAnswers',
                        data: $("#email").serialize(),
                        success: function(indicators){
                            exportData = indicators;
                            $("#loadingModal").modal("hide");
                            $("#exportModal").modal("show");
                            
                            $("#honesty-humility").text(indicators.honestyHumility);
                            $("#sincerity").text(indicators.sincerity);
                            $("#fairness").text(indicators.fairness);
                            $("#greed-avoidance").text(indicators.greedAvoidance);
                            $("#modesty").text(indicators.modesty);
                            
                            $("#emotionality").text(indicators.emotionality);
                            $("#fearfulness").text(indicators.fearfulness);
                            $("#anxiety").text(indicators.anxiety);
                            $("#dependence").text(indicators.dependence);
                            $("#sentimentality").text(indicators.sentimentality);
                            
                            $("#extraversion").text(indicators.extraversion);
                            $("#socialSelfEsteem").text(indicators.socialSelfEsteem);
                            $("#socialBoldness").text(indicators.socialBoldness);
                            $("#sociability").text(indicators.sociability);
                            $("#liveliness").text(idicators.liveliness);
                            
                            $("#agreeableness").text(indicators.agreeableness);
                            $("#forgiveness").text(indicators.forgiveness);
                            $("#gentleness").text(indicators.gentleness);
                            $("#flexibility").text(indicators.flexiblity);
                            $("#patience").text(indicators.patience);
                            
                            $("#conscientousness").text(indicators.conscientousness);
                            $("#organization").text(indicatos.organization);
                            $("#diligence").text(indicators.diligence);
                            $("#perfectionism").text(indicators.perfectionism);
                            $("#prudence").text(indicators.prudence);
                            
                            $("#openessToExperience").text(indicators.openessToExperience);
                            $("#aestheticAppreciation").text(indicators.aestheticAppreciation);
                            $("#inquisitivness").text(indicators.insquisitivness);
                            $("#creativity").text(indicators.creativity);
                            $("#unconventionality").text(indicators.uncoventionality);
                            
                            $("#altruism").text(indicators.altruism);
                            
                        }
                    })},500);
                }
                                   
            }
            </script>
        <div class="container" style="margin-top:30px;">
            <div class="text-center"><h2 style="color:blue">Your title here</h2></div>
            <div class="row" style="margin-top:10px">
                <div class="col-8">Please provide your email address</div>
            </div>
            
            <div class="row" style="margin-top:20px; margin-bottom:20px">
                <div class="col-4">Email:</div>                
                <div class="col-4 form-group" id="email-group">
                    <form method="POST" id='email' action="${pageContext.request.contextPath}/setEmail">
                    <input type="text" class="form-control" onchange="addOnblurEmail(this,'#email')" name="email" autocomplete="off" id="emailInput" title="Please provide a correct email." patter="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])">
                    </form>
                </div>               
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
                <form method="POST" id="question${index.index+1}" action="${pageContext.request.contextPath}/addAnswer">
                <div class="row" style="margin-top:20px;">
                    <div class="col-9"><strong>${index.index+1}.</strong> ${question.question}</div>
                    <div class="col-3">
                        <div class="form group">
                            <select name="option" class="form-control" id="answer-question${index.index+1}" name="option" onchange="addOnBlurQuestion(this,'#question${index.index+1}')">
                                    <option value="0">0</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                           </select>
                        </div>
                        <input type="hidden" value="${index.index+1}" name="question">
                    </div>
                </div>
                </form>
            </c:forEach>
            
            <div class="center-text text-center" style="margin-top:30px; margin-bottom:40px;">
                <button class="btn btn-primary" onclick="submitQuestions()">Submit</button>
            </div>
            
            <div id="messageModal" class="modal fade" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Please fill out the survey completly to get your results.</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
            
            <!--------------Export Modal ------------------>
        <div id="exportModal" class="modal fade" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Survey Results</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-6 text-center"><Strong>Honesty-Humility</strong></div>
                            <div class="col-6 text-center" id="honesty-humility"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Sincerity</div>
                            <div class="col-6 text-center" id="sincerity"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Fairness</div>
                            <div class="col-6 text-center" id="fairness"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Greed-Avoidance</div>
                            <div class="col-6 text-center" id="greed-avoidance"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Modesty</div>
                            <div class="col-6 text-center" id="modesty"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center"><strong>Emotionality</strong></div>
                            <div class="col-6 text-center" id="emotionality"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Fearfulness</div>
                            <div class="col-6 text-center" id="fearfulness"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Anxiety</div>
                            <div class="col-6 text-center" id="anxiety"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Dependence</div>
                            <div class="col-6 text-center" id="dependence"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Sentimentality</div>
                            <div class="col-6 text-center" id="sentimentality"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center"><strong>Extraversion</strong></div>
                            <div class="col-6 text-center" id="extraversion"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Social Self-Esteem</div>
                            <div class="col-6 text-center" id="socialSelfEsteem"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Social Boldness</div>
                            <div class="col-6 text-center" id="socialBoldness"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Sociability</div>
                            <div class="col-6 text-center" id="sociability"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Liveliness</div>
                            <div class="col-6 text-center" id="liveliness"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center"><strong>Agreeableness</strong></div>
                            <div class="col-6 text-center" id="agreeableness"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Forgivness</div>
                            <div class="col-6 text-center"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Gentlness</div>
                            <div class="col-6 text-center" id="gentlness"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Flexibility</div>
                            <div class="col-6 text-center" id="flexibility"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Patience</div>
                            <div class="col-6 text-center" id="patience"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center"><strong>Conscientiousness</strong></div>
                            <div class="col-6 text-center" id="conscientiousness"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Organization</div>
                            <div class="col-6 text-center" id="organization"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Diligence</div>
                            <div class="col-6 text-center" id="diligence"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Perfectionism</div>
                            <div class="col-6 text-center" id="perfectionism"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Prudence</div>
                            <div class="col-6 text-center" id="prudence"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center"><strong>Openness to Experience</strong></div>
                            <div class="col-6 text-center"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Aesthetic Appreciation</div>
                            <div class="col-6 text-center" id="aestheticAppreciation"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Inquisitivness</div>
                            <div class="col-6 text-center" id="inquisitivness"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Creativity</div>
                            <div class="col-6 text-center" id="creativity"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Unconventionality</div>
                            <div class="col-6 text-center" id="unconventionality"></div>
                        </div>
                        <div class="row">
                            <div class="col-6 text-center">Altruism</div>
                            <div class="col-6 text-center" id="altruism"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary"  onclick="exportResults()">Export Results</button>
                    </div>
                </div>
            </div>
        </div>
            <!--------------Loading Modal ------------------>
        <div id="loadingModal" class="modal fade" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="text-center">Loading your results</div>
                        <div class="text-center"><div class="lds-ring"><div></div><div></div><div></div><div></div></div></div>
                    </div>
                </div>
            </div>
        </div>
        </div>
    </body>
</html>
