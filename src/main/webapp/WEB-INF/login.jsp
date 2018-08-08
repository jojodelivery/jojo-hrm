<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link href="<c:url value="resources/images/favicon.ico" />"
	rel="shortcut icon" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jojo Delivery</title>
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/bootstrap-theme.min.css"  rel="stylesheet" />
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.min.js" ></script>
</head>
<body>
	    <div class="container">    
        <div id="loginbox" style="margin-top:180px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
                    <div class="panel-heading">
                        <div class="panel-title">Login</div>
                        <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">Forgot password?</a></div>
                    </div>     

                    <div style="padding-top:30px" class="panel-body" >

                        <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>
                            
                        <form id="loginform" class="form-horizontal" role="form" action="authenticate" method="post">
                                    
                            <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                                        <input id="userName" type="text" class="form-control" name="username" placeholder="username" required="true">                                        
                                    </div>
                                
                            <div style="margin-bottom: 25px" class="input-group">
                                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                                        <input id="password" type="password" class="form-control" name="password" placeholder="password" required="true">
                                    </div>
                                    

                                
                            <div class="input-group">
                                      <c:if test="${not empty ErrorMessage}"><p class="small" style="color: red;">${ErrorMessage}</p></c:if>
                                    </div>


                                <div style="margin-top:10px;float: right;" class="form-group">
                                    <!-- Button -->

                                    <div class="col-sm-12 controls">
                                   		 <input type="submit" class="btn btn-primary" value="Login"  />
                                    </div>
                                </div>


                                
                            </form>     



                        </div>                     
                    </div>  
        </div>
        
    </div>
    
</body>
</html>