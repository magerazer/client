<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'un client</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
    
    
    	<c:import url="inc/menu.jsp" />
    
    <br><br>
    
    <div id="corps">   
    
    
       	<span class="info"> ${ form.resultat } </span><br><br>
       
      
      
              
                 
          Nom : <c:out value="${ client.nom }"></c:out><br>
          Prénom : <c:out value="${ client.prenom }"></c:out><br>
          Adresse : <c:out value="${ client.adresse }"></c:out><br>
          Numéro de téléphone : <c:out value="${ client.telephone }"></c:out><br>
          Email : <c:out value="${ client.email }"></c:out><br>
               
	    
    
   </div>
          
       
    </body>
</html>