<?php
session_start();
?>
<!DOCTYPE HTML>
<html lang="en"><head><title>Evoulve</title><meta charset="utf-8"><meta name="viewport" content="width=device-width, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"><meta name="apple-mobile-web-app-capable" content="yes"><meta name="apple-mobile-web-app-status-bar-style" content="black"><link rel="stylesheet" href="css/css-style.css"><meta name="description" content="Evoulve is a digital technology innovation Company. We focus on designing innovations in digital technology industry, and transform emerging ideas into viable products that move the digital industry forward."><link rel="apple-touch-icon" sizes="57x57" href="favicons/icons-apple-icon-57x57.png"><link rel="apple-touch-icon" sizes="60x60" href="favicons/icons-apple-icon-60x60.png"><link rel="apple-touch-icon" sizes="72x72" href="favicons/icons-apple-icon-72x72.png"><link rel="apple-touch-icon" sizes="76x76" href="favicons/icons-apple-icon-76x76.png"><link rel="apple-touch-icon" sizes="114x114" href="favicons/icons-apple-icon-114x114.png"><link rel="apple-touch-icon" sizes="120x120" href="favicons/icons-apple-icon-120x120.png"><link rel="apple-touch-icon" sizes="144x144" href="favicons/icons-apple-icon-144x144.png"><link rel="apple-touch-icon" sizes="152x152" href="favicons/icons-apple-icon-152x152.png"><link rel="apple-touch-icon" sizes="180x180" href="favicons/icons-apple-icon-180x180.png"><link rel="icon" type="image/png" sizes="192x192" href="favicons/icons-android-icon-192x192.png"><link rel="icon" type="image/png" sizes="32x32" href="favicons/icons-favicon-32x32.png"><link rel="icon" type="image/png" sizes="96x96" href="favicons/icons-favicon-96x96.png"><link rel="icon" type="image/png" sizes="16x16" href="favicons/icons-favicon-16x16.png"><link rel="manifest" href="assets/img/icons/manifest.json"><meta name="msapplication-TileColor" content="#ffffff"><meta name="msapplication-TileImage" content="assets/img/icons/ms-icon-144x144.png"><meta name="theme-color" content="#ffffff"><!-- Global site tag (gtag.js) - Google Analytics --><script async src="https://www.googletagmanager.com/gtag/js?id=UA-20462557-9"></script><script>
		  window.dataLayer = window.dataLayer || [];
		  function gtag(){dataLayer.push(arguments);}
		  gtag('js', new Date());

		  gtag('config', 'UA-20462557-9');
		</script>
		   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
  
  <style>
	  @media (max-width: 768px) {
		h1 {
			font-size: 15px;
		}
	}
	@media (min-width: 769px) and (max-width: 1024px) {
		h1 {
			font-size: 17px;
		}
	}
  </style>
		</head><body>
		<div id="loading"></div>
		
		<div style="color:white;padding:0px;z-index:100;position:fixed;top: 50%;left: 50%;width:35em;height:18em;margin-top: -9em;margin-left: -15em;">
			<form action ="chkLogin.php" method="post">
					<div class="container-fluid">
						<div class="row">
							<div class="col-xs-6" style="text-align:right"><h1>Username:<h1></div>
							<div class="col-xs-6" style="text-align:left;margin:auto;height:auto"><input type="text" name="username" value="<?php if(isset($_GET["oldUsername"]))echo $_GET["oldUsername"];?>"></div>
						</div>
						
						
						<div  class="row">
							<div class="col-xs-6" style="text-align:right"><h1>Password:<h1></div>
							<div class="col-xs-6" style="text-align:left;margin:auto;height:auto"><input style="width:100%" type="password" name="password" value=""></div>
						</div>
					
						<div  class="row">
							<div class="col-xs-12" style="text-align:center">
							<?php if(isset($_GET["err"]))
									echo "<h1 class='rosso'>Status: </h1><p>".$_GET["err"]."</p><br>";
							?>
						</div>
							
						</div>
						
						<br>
						<div  class="row">
							<div class="col-6" style="text-align:right;height:40px"><input type="submit" style="width: 60%; height:100%" value="Login"></div>
							<div class="col-6" style="text-align:left;height:40px"><input type="reset" style="width: 60%;height:100%"value="Cancella"></div>
						</div>
						<br><br>
						<div  class="row">
						<div class="col-xs-12" style="text-align:center"><h1>Per registrarsi cliccare <a href="registratiPage.php">qui</a></h1></div>
						</div>
					</div>
			</form>
		</div>	
		<div id="wrapper">
			<!--<div class="sphere-overlay"></div>-->
			<div id="container" style="pointer-events: none;color: rgb(255, 255, 255); font: 13px / 20px Arial, sans-serif; cursor: auto;"><canvas width="1527" height="1211" style="position: absolute;"></canvas></div>
			<div class="o-grid__item" id="logo" style="z-index: 100;position: absolute;">
				
			</div>
		</div>
		<div id="info"></div>




		

		<script src="js/js-jquery-1.9.1.min.js"></script><script type="text/javascript" src="js/js-scripts.js"></script></body></html>
