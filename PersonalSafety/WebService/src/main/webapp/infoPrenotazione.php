<?php
date_default_timezone_set('UTC');

if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
if(!isset($_SESSION["username"]) || isset($_SESSION["idAdmin"])){
	header("Location: index.php?err=Devi effettuare il login!");
	die();
}

include 'connection.php';


$idPrenotazione=$_GET["id"];

//Prendo la prenotazione
$sql ="SELECT * FROM `prenotazione` WHERE id='$idPrenotazione'";
$result = $conn->query($sql);
$row = $result->fetch_assoc();

$linkQR="";
//$linkQR="http://localhost/clientPersonalSafety/prenotazioneQR.php?idCliente=$idCliente&oraInizio=$oraInizio&oraFine=$oraFine&data=$data&idPrenotazione=$idPrenotazione";
//echo('<tr class="noStile"><td class="noStile"><b>QRCode personale</b><br>Conserva il seguente QRCode e mostralo quanto sarai arrivato nei pressi dell\'Ente:</td>');
//echo('<td class="noStile"><img src="https://chart.googleapis.com/chart?chs=400x400&cht=qr&chl='.$linkQR.'&choe=UTF-8"  title="Link to Google.com" /></td></tr>');
if ($result->num_rows > 0) {
	$oraInizio = $row["oraInizio"];
	$oraFine = $row["oraFine"];
	$data = $row["data"];
	$idCliente = $row["idCliente"];
	
	$linkQR="http://localhost/clientPersonalSafety/prenotazioneQR.php?idCliente=$idCliente&oraInizio=$oraInizio&oraFine=$oraFine&data=$data&idPrenotazione=$idPrenotazione";
	
	//Prendo info ente
	$sql ="SELECT * FROM `riferimento` WHERE idPrenotazione='$idPrenotazione'";
	$result = $conn->query($sql);
	$row = $result->fetch_assoc();
	$idEnte=$row["codEnte"];
	
	$sql ="SELECT * FROM `ente` WHERE cod='$idEnte'";
	$result = $conn->query($sql);
	$row = $result->fetch_assoc();
	$nomeEnte=$row["nome"];
	
}else{

}

$conn->close();
?>

<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="stileRegistrazione.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>



	<title>Personal Safety</title>
	
	<style>
	input[type=button]{
		cursor:pointer;
	}
	table {

	  border-collapse: collapse;
	  width: 100% !important;
	  margin:auto;
	}

	 td, th {
	  border: 1px solid #ddd;
	  padding: 8px;
	}

	.noStile{
	  border: 0px solid #ddd;
	  padding: 0px;
	}

	tr:hover {
		background-color: #ffff99!important;
	}
	
	.noStile:hover {
		background-color: #fff!important;
	}
	
	th {
	  padding-top: 12px;
	  padding-bottom: 12px;
	  text-align: left;
	  background-color: #1074e7;
	  color: white;
	  text-align:center;
	}
	.pari{
		background-color: #fff;
	}
	.dispari{
		background-color: #f0f0f0;
	}
	
	tr {
		text-align:center;
	}
	table {
		width:50%;
	}
	
	.imgGrande{
		margin:auto;
		display:block; 
		padding-bottom:20px;
		width:60%;
	}
	@media only screen and (max-width: 1000px) {
		table {
			width:100%;
		}
		
		.imgGrande{

			width:100%;
		}
	}
	

	</style>
</head>

<!--  required errored        form-control input py-1 is-autocheck-errored -->
<body class="logged-out env-production page-responsive intent-mouse">

<div class="position-relative js-header-wrapper ">
    <header id="miaBarra" class="Header-old header-logged-out js-details-container Details position-relative f4 py-2" role="banner" style="background-color:#1074e7;">
        <div class="container-xl d-lg-flex flex-items-center p-responsive">
            <div class="d-flex flex-justify-between flex-items-center">
                <a class="mr-4" href="index.php" aria-label="Homepage" data-ga-click="(Logged out) Header, go to homepage, icon:logo-wordmark">
				<!-- IMMAGINE LATO SX -->

					<svg width="32" class="octicon octicon-mark-github text-white" height="32" viewBox="0 0 512 512" xmlns="http://www.w3.org/2000/svg">
					<path fill="#fff" d="m321.32 83.367c-12.812-33.246-28.918-61.246-47.426-82.715-6.0781-0.42969-12.195-0.65234-18.344-0.65234-6.1523 0-12.27 0.22266-18.344 0.65234-18.512 21.469-34.617 49.465-47.426 82.715-4.1367 10.734-7.8555 21.863-11.156 33.316h153.85c-3.3047-11.453-7.0234-22.582-11.156-33.316z"/>
					<path fill="#fff" d="m171.1 365.32h168.9c6.457-30.008 10.207-61.766 11.09-94.316h-191.08c0.88281 32.555 4.6328 64.309 11.09 94.316z"/>
					<path fill="#fff" d="m340 146.68h-168.9c-6.457 30.008-10.207 61.766-11.09 94.316h191.08c-0.88281-32.555-4.6328-64.309-11.09-94.316z"/>
					<path fill="#fff" d="m189.78 428.63c12.812 33.246 28.918 61.246 47.426 82.715 6.0781 0.42969 12.195 0.65234 18.348 0.65234 6.1484 0 12.266-0.22266 18.344-0.65234 18.508-21.469 34.613-49.465 47.422-82.715 4.1367-10.734 7.8555-21.863 11.156-33.316h-153.85c3.3008 11.453 7.0195 22.582 11.152 33.316z"/>
					<path fill="#f00" d="m370.59 146.68c6.0508 29.953 9.6602 61.664 10.508 94.316h130c-1.8984-33.09-10.055-65-23.93-94.34-0.14453 0.003906-0.28516 0.023438-0.42969 0.023438z"/>
					<path fill="#0f0" d="m140.52 365.32c-6.0547-29.953-9.6641-61.664-10.512-94.316h-130c1.8984 33.09 10.055 65 23.93 94.34 0.14453-0.003906 0.28516-0.023438 0.42969-0.023438z"/>
					<path fill="#f00" d="m363.55 395.32c-11.168 41.645-27.277 78.977-47.363 109.48 45.199-10.93 86.664-34.066 120.38-67.781 12.848-12.848 24.145-26.828 33.832-41.703z"/>
					<path fill="#0f0" d="m147.55 116.68c11.172-41.645 27.277-78.977 47.363-109.48-45.195 10.93-86.664 34.066-120.38 67.781-12.844 12.848-24.141 26.828-33.828 41.703z"/>
					<path fill="#0f0" d="m147.55 395.32h-106.85c9.6875 14.875 20.984 28.855 33.828 41.703 33.719 33.715 75.184 56.852 120.38 67.781-20.082-30.508-36.188-67.84-47.359-109.48z"/>
					<path fill="#f00" d="m363.55 116.68h106.85c-9.6875-14.875-20.98-28.855-33.828-41.703-33.719-33.715-75.184-56.852-120.38-67.781 20.086 30.508 36.195 67.84 47.363 109.48z"/>
					<path fill="#f00" d="m381.1 271c-0.84766 32.652-4.4531 64.363-10.508 94.316h116.15c0.14453 0 0.28516 0.019532 0.42969 0.023438 13.875-29.34 22.031-61.25 23.93-94.34z"/>
					<path fill="#0f0" d="m130 241c0.84766-32.652 4.457-64.363 10.512-94.316h-116.16c-0.14453 0-0.28516-0.019532-0.42969-0.023438-13.875 29.34-22.031 61.25-23.93 94.34z"/>
					</svg>
                </a>

                <div class="d-lg-none css-truncate css-truncate-target width-fit p-2">

                </div>

                <div class="d-flex flex-items-center">
					<b><a style="color:yellow!important;" href="home.php" class="d-inline-block d-lg-none f5 text-white no-underline border-gray-dark rounded-2 px-2 py-1 mr-3 mr-sm-5">
						  Home
					</a></b>
                    <a href="login.php" class="d-inline-block d-lg-none f5 text-white no-underline border border-gray-dark rounded-2 px-2 py-1 mr-3 mr-sm-5">
					  <?php echo($_SESSION["nome"]." ".$_SESSION["cognome"]); ?>
					</a>
					
					
					<!--  
					Header-old header-logged-out js-details-container Details position-relative f4 py-2
					Header-old header-logged-out js-details-container Details position-relative f4 py-2 open Details--on -->
					<!-- BOTTONE HAMBURGER -->
                    <button class="btn-link d-lg-none mt-1 js-details-target" type="button" onClick="document.getElementById('miaBarra').classList.add('Details--on');document.getElementById('miaBarra').classList.add('open');" >
                        <svg height="24" class="octicon octicon-three-bars text-white" viewBox="0 0 12 16" version="1.1" width="18" aria-hidden="true">
                            <path fill="#fff" fill-rule="evenodd" d="M11.41 9H.59C0 9 0 8.59 0 8c0-.59 0-1 .59-1H11.4c.59 0 .59.41.59 1 0 .59 0 1-.59 1h.01zm0-4H.59C0 5 0 4.59 0 4c0-.59 0-1 .59-1H11.4c.59 0 .59.41.59 1 0 .59 0 1-.59 1h.01zM.59 11H11.4c.59 0 .59.41.59 1 0 .59 0 1-.59 1H.59C0 13 0 12.59 0 12c0-.59 0-1 .59-1z"></path>
                        </svg>
                    </button>
                </div>
            </div>
			
			<!-- ELEMENTI IN MEZZO -->
            <div class="HeaderMenu HeaderMenu--logged-out position-fixed top-0 right-0 bottom-0 height-fit position-lg-relative d-lg-flex flex-justify-between flex-items-center flex-auto">
                <div class="d-flex d-lg-none flex-justify-end border-bottom bg-gray-light p-3">
                    <button class="btn-link js-details-target" type="button" onClick="document.getElementById('miaBarra').classList.remove('Details--on');document.getElementById('miaBarra').classList.remove('open');">
                        <svg height="24" class="octicon octicon-x text-gray" viewBox="0 0 12 16" version="1.1" width="18" aria-hidden="true">
                            <path fill-rule="evenodd" d="M7.48 8l3.75 3.75-1.48 1.48L6 9.48l-3.75 3.75-1.48-1.48L4.52 8 .77 4.25l1.48-1.48L6 6.52l3.75-3.75 1.48 1.48L7.48 8z"></path>
                        </svg>
                    </button>
                </div>
				
				<!-- ELEMENTI LATO SX -->
                <nav class="mt-0 px-3 px-lg-0 mb-5 mb-lg-0" aria-label="Global">
                    <ul class="d-lg-flex list-style-none">
						
                        <li class="d-block d-lg-flex flex-lg-nowrap flex-lg-items-center border-bottom border-lg-bottom-0 mr-0 mr-lg-3 edge-item-fix position-relative flex-wrap flex-justify-between d-flex flex-items-center ">
                            <details class="HeaderMenu-details details-overlay details-reset width-full">
                                <summary class="HeaderMenu-summary HeaderMenu-link px-0 py-3 border-0 no-wrap d-block d-lg-inline-block">
                                    Sezioni
                                    <svg x="0px" y="0px" viewBox="0 0 14 8" xml:space="preserve" fill="none" class="icon-chevon-down-mktg position-absolute position-lg-relative">
                                        <path d="M1,1l6.2,6L13,1"></path>
                                    </svg>
                                </summary>
                                <div class="dropdown-menu flex-auto rounded-1 bg-white px-0 mt-0 pb-4 p-lg-4 position-relative position-lg-absolute left-0 left-lg-n4">
									<!-- SOTTO ELEMENTO BOLD -->
                                    <a  class="py-2 lh-condensed-ultra d-block link-gray-dark no-underline h5 Bump-link--hover">Pagine del sito</a>
									<!-- SOTTO ELEMENTO -->
                                    <ul class="list-style-none f5 pb-3">
                                        <li class="edge-item-fix"><a href="index.php" class="py-2 lh-condensed-ultra d-block link-gray no-underline f5">Welcome Page</a></li>
										<li class="edge-item-fix"><a href="login.php" class="py-2 lh-condensed-ultra d-block link-gray no-underline f5">Login</a></li>
										<li class="edge-item-fix"><a href="registratiPage.php" class="py-2 lh-condensed-ultra d-block link-gray no-underline f5">Registrazione</a></li>
                                    </ul>
									
									<!-- SOTTO ELEMENTI BOLDs -->
                                    <ul class="list-style-none mb-0 border-lg-top pt-lg-3">
                                        <li class="edge-item-fix"><a href="#" class="py-2 lh-condensed-ultra d-block no-underline link-gray-dark no-underline h5 Bump-link--hover">Informazioni<span class="Bump-link-symbol float-right text-normal text-gray-light">→</span></a></li>
                                    </ul>
                                </div>
                            </details>
                        </li>	
						
						<li class="border-bottom border-lg-bottom-0 mr-0 mr-lg-3">
						  <b><a style="color:yellow;" href="home.php" class="HeaderMenu-link no-underline py-3 d-block d-lg-inline-block" data-ga-click="(Logged out) Header, go to Team">Home</a></b>
						</li>
                    </ul>
                </nav>
				
				<!-- ELEMENTI LATO DX-->
                <div class="d-lg-flex flex-items-center px-3 px-lg-0 text-center text-lg-left">
					<a href="slog.php" class="HeaderMenu-link no-underline mr-3">
						Esci
					</a>
					
                    <a href="account.php" class="HeaderMenu-link d-inline-block no-underline border border-gray-dark rounded-1 px-2 py-1">
						<?php echo($_SESSION["nome"]." ".$_SESSION["cognome"]); ?>
					</a>
                </div>
            </div>
			
        </div>
    </header>
</div>


<!-- BODY DEL DOCUMENTO -->

<div class="container-lg p-responsive pb-6" style="padding-bottom: 0px!important;">

    <p class="h00-mktg lh-condensed mt-3 mb-2 text-center" >

    </p>
    <p class="lead-mktg text-gray text-center col-md-8 mx-auto mb-4">

		<br>
		
		Riepilogo:
		<table>
			<?php 

				echo("<tr><td>Giorno:</td><td>$data</td></tr>");
				echo("<tr><td>Orario:</td><td>dalle $oraInizio alle $oraFine</td></tr>");
				echo("<tr><td>Luogo:</td><td>$nomeEnte</td></tr>");
			?>
		</table>
		<br>
		<table class="noStile">
			<?php 
				//Visualizzo il QR
				echo('<tr class="noStile"><td class="noStile"><b>QRCode personale</b><br>Conserva il seguente QRCode e mostralo quanto sarai arrivato nei pressi dell\'Ente:</td>');
				echo('<td class="noStile"><img src="https://chart.googleapis.com/chart?chs=400x400&cht=qr&chl='.$linkQR.'&choe=UTF-8"  title="QR personale prenotazione" /></td></tr>');
				
			?>
		</table>
    </p>
	

</div>

</body>

</html>