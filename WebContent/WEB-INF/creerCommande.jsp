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
    
        <div>
            <form method="post" action="creationCommande" enctype="multipart/form-data">
               
               <c:import url="inc/inc_client_var.jsp" />
               
                <fieldset>
                    <legend>Informations commande</legend>
                    
                    <label for="dateCommande">Date <span class="requis">*</span></label>
                    <input type="text" id="dateCommande" name="dateCommande" value="" size="20" maxlength="20" disabled />
                   	<br />
                    
                    <label for="montantCommande">Montant <span class="requis">*</span></label>
                    <input type="text" id="montantCommande" name="montantCommande" value="<c:out value="${ commande.montant }" />" size="20" maxlength="20" />
                    <span class="erreur">${ form.erreurs['montant'] }</span>
                    <br />
                    
                    <label for="modePaiementCommande">Mode de paiement <span class="requis">*</span></label>
                    <input type="text" id="modePaiementCommande" name="modePaiementCommande" value="<c:out value="${ commande.modePaiement }" />" size="20" maxlength="20" />
                    <span class="erreur">${ form.erreurs['modePaiement'] }</span>
                    <br />
                    
                    <label for="statutPaiementCommande">Statut du paiement</label>
                    <input type="text" id="statutPaiementCommande" name="statutPaiementCommande" value="<c:out value="${ commande.statutPaiement }" />" size="20" maxlength="20" />
                    <span class="erreur">${ form.erreurs['statutPaiement'] }</span>
                    <br />
                    
                    <label for="modeLivraisonCommande">Mode de livraison <span class="requis">*</span></label>
                    <input type="text" id="modeLivraisonCommande" name="modeLivraisonCommande" value="<c:out value="${ commande.modeLivraison }" />" size="20" maxlength="20" />
                    <span class="erreur">${ form.erreurs['modeLivraison'] }</span>
                    <br />
                    
                    <label for="statutLivraisonCommande">Statut de la livraison</label>
                    <input type="text" id="statutLivraisonCommande" name="statutLivraisonCommande" value="<c:out value="${ commande.statutLivraison }" />" size="20" maxlength="20" />
                    <span class="erreur">${ form.erreurs['statutLivraison'] }</span>
                    <br />
                    <br><br>
               	<span class="info"> ${ form.resultat } </span><br><br>
                </fieldset>
                <input type="submit" value="Valider"  />
                <input type="reset" value="Remettre à zéro" /> <br />
            </form>
        </div>
    </body>
</html>