<?php
session_start();
?>
<html>
<head>
<script>
function chkUserAndPass(){
	//Maggiore di 4 caratteri e conferma password rinserimento
	
	var user = document.getElementById("user").value;
	var pass = document.getElementById("pass").value;
	var passR = document.getElementById("passR").value;
	var err="";
	
	if(pass.length<4)
		err+="La password deve avere una lunghezza maggiore di 4 caratteri<br>";
	
	if(pass!=passR){
		err+="Le password non corrispondono<br>";
			
	if(err=="") return true;
	
	window.location="registrati.php?err="+err;
	return false;
}

</script>
</head>

<body>

<!--SI POTREBBE USARE L'EVENTO ON SUBMIT SUL FORM. SE RETURN TRUE IL FORM EFFETTUA
IL SUBMIT ALTRIMENTI SE FALSE NO-->
<form enctype="multipart/form-data" action ="registrazioneEsegui.php" method="post" onsubmit="chkUserAndPass()">
	<h1>Registrazione Account</h1><br><br>

	Nome:
	<input type="text" name="nome" value="<?php if(isset($_GET["oldNome"]))echo $_GET["oldNome"];?>">
	<br>
	
	Cognome:
	<input type="text" name="cognome" value="<?php if(isset($_GET["oldCognome"]))echo $_GET["oldCognome"];?>">
	<br>
	
	Foto Profilo: 
	<input name="userfile" type="file"><br>
	<br>
	
	Codice Fiscale:
	<input type="text" name="cf" value="<?php if(isset($_GET["oldCF"]))echo $_GET["oldCF"];?>">
	<br>
	
	Data di Nascita:
	<input type="date" name="data" value="<?php if(isset($_GET["oldData"]))echo $_GET["oldData"];?>">
	<br>
	
	Sesso:
	<select name="sesso">
		<option value="M">Maschio</option>
		<option value="F">Femmina</option>
	</select>
	<br>
	
	Username:
	<input type="text" name="username" id="user" value="<?php if(isset($_GET["oldUsername"]))echo $_GET["oldUsername"];?>">
	<br>
	
	Nuova password:
	<input type="password" name="password" id="pass" value="">
	<br>
	
	Reinserisci password:
	<input type="password" name="passwordR" id="passR">

	
	<?php if(isset($_GET["err"]))
		echo "<h1 class='rosso'>Status: </h1><p>".$_GET["err"]."</p><br>";
	?>
	
	<input type="submit" value="Registrati">
	<input type="reset" value="Cancella">

</form>
<?php


?>

</body>

</html>