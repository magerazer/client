<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Création d'une commande</title>
        <link type="text/css" rel="stylesheet" href="inc/style.css" />
    </head>
    <body>
    
    	<c:import url="inc/menu.jsp" />
    
         <div id="corps">
          <span class="info"> ${ form.resultat } </span><br><br>
          
          
          Client <br><br>
                 
          Nom : <c:out value="${ commande.client.nom }"></c:out>  <br>
          Prénom : <c:out value="${ commande.client.prenom }"></c:out><br>
          Adresse : <c:out value="${ commande.client.adresse }"></c:out><br>
          Numéro de téléphone : <c:out value="${ commande.client.telephone }"></c:out><br>
          Email : <c:out value="${ commande.client.email }"></c:out><br>
         
                   
          <br><br>Commande<br><br>
          
          Date : <c:out value="${ commande.date }" /> <br>
          Montant : <c:out value="${ commande.montant }" /> <br>
          Mode de paiement : <c:out value="${ commande.modePaiement }" /> <br>
          Statut du paiement : <c:out value="${ commande.statutPaiement }" /> <br>
          Mode de livraison : <c:out value="${ commande.modeLivraison }" /> <br>
          Statut de la livraison : <c:out value="${ commande.statutLivraison }" /> <br>
          <br>
       
       
       </div>
                   
    </body>
</html>