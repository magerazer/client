<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="inc/style.css" />
</head>
<body>

<c:import url="inc/menu.jsp" />

<div id="corps">
	<c:choose>
	<c:when test="${! empty sessionScope.listeSessionClients }">
	<table>
		<thead>
			<tr>
			<th>Nom</th>
			<th>Prenom</th>
			<th>Adresse</th>
			<th>Telephone</th>
			<th>Email</th>
			<th id="action">Action</th>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach var="cli" items="${ sessionScope.listeSessionClients }">
			<tr>
				<td>${ cli.nom }</td>
				<td>${ cli.prenom }</td>
				<td>${ cli.adresse }</td>
				<td>${ cli.telephone }</td>
				<td>${ cli.email }</td>
				<td id ="action"><a href="./suppressionClient?nom=${ cli.nom }">
				<img src="./resources/images/croixRouge.jpg" width="30" id="croix"></a></td>			
			</tr>
			</c:forEach>
					
		</tbody>
		
	</table>
	</c:when>
	<c:otherwise>
	<div class="erreur">
	Aucun client enregistré.
	</div>	
	</c:otherwise>
	</c:choose>		
	</div>
	
	
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<div>
	<form action="./listeClients" method="post">
		<input type="submit" value="remplir liste" />
	</form>
	</div>
</body>
</html>