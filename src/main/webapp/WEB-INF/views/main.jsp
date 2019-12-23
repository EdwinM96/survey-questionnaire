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
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script>

        <script>
            function exportOportunidades() {
            $.ajax({
            type: "GET",
                    headers: {
                    'Accept': 'application/json'
                    },
                    url: '${pageContext.request.contextPath}/oportunidad/getAll',
                    data: $('#search-form').serialize() + "&" + $('#export-form').serialize(),
                    success: function (data)
                    {
                    let csvContent = "data:text/csv;charset=utf-8,";
                    let i = 0;
                    let firstRow = "Fecha de Creacion, Comprador, Vendedor, Titulo, Estatus, Monto Trans, Descripcion \r\n";
                    csvContent += firstRow;
                    for (var x in data) {
                    let row = data[i]['creationDate'];
                    if (data[i]['user']['display'] != null && data[i]['user']['display'] != "null"){
                    row += data[i]['user']['display'];
                    }

                    if (data[i]['customValues']['vendedor'] != null && data[i]['customValues']['vendedor'] != "null" && data[i]['customValues']['vendedor'] != ""){
                    row += "," + data[i]['customValues']['vendedor'];
                    }
                    else{
                    row += ",";
                    }
                    if (data[i]['customValues']['titulo'] != null && data[i]['customValues']['titulo'] != "null" && data[i]['customValues']['titulo'] != ""){
                    row += "," + data[i]['customValues']['titulo'];
                    }
                    else{
                    row += ",";
                    }
                    if (data[i]['customValues']['estatus'] != null && data[i]['customValues']['estatus'] != "null" && data[i]['customValues']['estatus'] != ""){
                    row += "," + data[i]['customValues']['estatus'];
                    }
                    else{
                    row += ",";
                    }
                    if (data[i]['customValues']['montoTrans'] != null && data[i]['customValues']['montoTrans'] != "null" && data[i]['customValues']['montoTrans'] != ""){
                    row += "," + data[i]['customValues']['montoTrans'];
                    }
                    else{
                    row += ",";
                    }
                    if (data[i]['customValues']['descripcion'] != null && data[i]['customValues']['descripcion'] != "null" && data[i]['customValues']['descripcion'] != ""){
                    row += "," + data[i]['customValues']['descripcion'];
                    }
                    else{
                    row += ",";
                    }
                    csvContent += row + "\r\n";
                    i++;
                    }
                    var encodedUri = encodeURI(csvContent);
                    var link = document.createElement("a");
                    link.setAttribute("href", encodedUri);
                    link.setAttribute("download", "oportunidades.csv");
                    document.body.appendChild(link);
                    link.click();
                    }

            })
            }
            
            function addOnblurEmail(input,name){
            console.info("Entre en el OnBlur")
            console.info($(name).serialize())
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
            </script>
        <div class="container" style="margin-top:30px;">
            <div class="text-center"><h2 style="color:blue">Your title here</h2></div>
            <div class="row" style="margin-top:10px">
                <div class="col-8">Please provide your email address</div>
            </div>
            
            <div class="row" style="margin-top:20px; margin-bottom:20px">
                <div class="col-4">Email:</div>                
                <div class="col-4">
                    <form method="POST" id='email' action="${pageContext.request.contextPath}/setEmail">
                    <input type="text" class="form-control" onchange="addOnblurEmail(this,'#email')" name="email" autocomplete="off">
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
                <form>
                <div class="row" style="margin-top:20px;">
                    <div class="col-9"><strong>${index.index+1}.</strong> ${question.question}</div>
                    <div class="col-3">
                        <div class="form group">
                            <select name="opcion" class="form-control" id="answer-question${index.index+1}" name="option">
                                    <option value="">0</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="4">5</option>
                           </select>
                        </div>
                    </div>
                </div>
                </form>
            </c:forEach>
            
            <div class="center-text text-center" style="margin-top:30px; margin-bottom:40px;">
                <button class="btn btn-primary">Submit</button>
            </div>
        </div>
    </body>
</html>
