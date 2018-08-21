<fieldset>
    <legend>Informations client</legend>
	<script>
	function choix(rep) {
		if(document.getElementById(rep).checked == true ) {
			if( rep == "Oui" ) {
				document.getElementById("div_Oui").style.display = "block";
				document.getElementById("div_Non").style.display = "none";				
			} else {
				document.getElementById("div_Oui").style.display = "none";
				document.getElementById("div_Non").style.display = "block";
			
			}
		}
	}
	
	
	</script>
	<c:if test="${ pageComm }">
		<label for="newCli">Nouveau client ? <span class="requis">*</span></label>
		<input type="radio" id="Oui" name="newCli" value="Oui" onclick='choix("Oui");'/> Oui
		<input type="radio" id="Non" name="newCli" value="Non" onclick='choix("Non");'/> Non
				
	</c:if>
	<br />
	<br />


	
	<br>

	<c:if test="${ !pageComm }">

  		<c:import url="inc/inc_client_form.jsp"></c:import>
    
    </c:if>
    
    <div id="div_Oui" style="display:none">
   	<c:import url="inc/inc_client_form.jsp"></c:import>
   	</div>
   	<div id="div_Non" style="display:none">
   	<select name="choixCli">
   	<option value="">Choisissez un client...</option>
   	<c:forEach var="cli" items="${ listeSessionClients }" >
	  <option value="${cli.nom}">${ cli.prenom } ${ cli.nom }</option>
	</c:forEach>
	</select>
	</div>
    
</fieldset>

	




