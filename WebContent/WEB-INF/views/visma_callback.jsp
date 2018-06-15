<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">

	<c:choose>
		<c:when test="${not empty model.user.cssEspedsg}">
			<link href="resources/${model.user.cssEspedsg}?ver=${user.versionEspedsg}" rel="stylesheet" type="text/css"/>
		</c:when>
		<c:otherwise>
			<link href="resources/espedsg.css?ver=${user.versionEspedsg}" rel="stylesheet" type="text/css"/>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${ fn:contains(model.user.cssEspedsg, 'Toten') }"> 
			<link rel="SHORTCUT ICON" type="image/ico" href="resources/images/toten_ico.ico"></link>
		</c:when>
		<c:otherwise>
			<link rel="SHORTCUT ICON" type="image/png" href="resources/images/systema_logo.png"></link>
		</c:otherwise>
	</c:choose>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />
	<title>${model.user.espedsgLoginTitle}</title>
 
	<style type="text/css">
		.container-fluid {
			  max-width:600px;
			  margin:auto;
		  }
	</style>
 
  </head>
  <body>
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" ></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" ></script>
 	<script type="text/javascript" src="resources/js/headerReports.js?ver=${user.versionEspedsg}"></script>	

	<div class="padded-row">&nbsp;</div>	
 
	<div class="container-fluid loginFrameE2">
		<div class="row">
		  <div class="col" align="center">
		    Sikkerhetsnøkler generert og lagret i databasen.
		  </div>
		</div>
		
		<div class="padded-row">&nbsp;</div>	
		
		<div class="row">
		  <div class="col" align="center">
		     <button name="closeButton" id="closeButton" class="buttonGrayWithGreenFrame" type="button">Steng vindu</button>
		  </div>
		</div>
	 
	</div>

	<div class="padded-row">&nbsp;</div>
 
  </body>
</html>