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
	<c:when test="${! empty sessionScope.listeSessionCommandes }">
	<table>
		<thead>
			<tr>
			<th>Client</th>
			<th>Date</th>
			<th>Montant</th>
			<th>Mode de paiement</th>
			<th>Statut de paiement</th>
			<th>Mode de livraison</th>
			<th>Statut de livraison</th>
			<th id="action">Action</th>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach var="comm" items="${ sessionScope.listeSessionCommandes }">
			<tr>
				<td>${ comm.client.prenom } ${ comm.client.nom }</td>
				<td>${ comm.date }</td>
				<td>${ comm.montant }</td>
				<td>${ comm.modePaiement }</td>
				<td>${ comm.statutPaiement }</td>
				<td>${ comm.modeLivraison }</td>
				<td>${ comm.statutLivraison }</td>
				<td id ="action">
				<a href="<c:url value="./suppressionCommande?date=${ comm.date }" />" >
					<img src="./resources/images/croixRouge.jpg" width="30" ></td>			
				</a>
			</tr>
			</c:forEach>
		
		</tbody>
		
	</table>
	
	</c:when>
	<c:otherwise>
		<div class="erreur">
		Auncune commande enregistrée.
		</div>
	</c:otherwise>
	</c:choose>
		
</div>


	
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<div>
	<form action="./listeCommandes" method="post">
		<input type="submit" value="remplir liste" />
	</form>
	</div>

</body>
</html>