<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
.font{
font-family: Ubuntu-Bold;
}
.rosso{
color:red;
}
	.header {
		background-color: #0052CC;
		width: 100%;
		-webkit-box-flex: 0;
		-webkit-flex: none;
		-moz-box-flex: 0;
		-ms-flex: none;
		flex: none;
		z-index: 5;
		position: relative
		height: 88px
	}


	.header-content {
			height: 88px
	}

	html,
	body {
		margin: 0;
		padding: 0;
	}
	
	.bg-light {
    background-color: #0052CC!important;
	
	}
	.navbar-light .navbar-brand {
    color: #fff;
	}
	.navbar-light .navbar-nav .active>.nav-link, .navbar-light .navbar-nav .nav-link.active, .navbar-light .navbar-nav .nav-link.show, .navbar-light .navbar-nav .show>.nav-link {
    color: #fff;
	}
	
	.navbar-light .navbar-nav .nav-link {
    color: #fff;
	}
	
	 .navbar {
  min-height: 80px;
}

.btn-bd-download {
    font-weight: 500;
    color: #ffe484;
    border-color: #ffe484;
	font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol";
}

.btn-bd-download:hover {
  background-color: #ffe484; 
  color: black;
  font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol";
}

.nochange:hover { 
color: #fff!important; 
}
.nochange:link {
  color: #fff!important; 
}
.nochange:visited {
  color: #fff!important; 
}
.nochange:hover {
  color: #fff!important; 
}

.nochange:active {
  color: #fff!important; 
}

a{
	cursor: pointer;
}


</style>
<link rel="stylesheet" type="text/css" href="stileLogin.css">
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand nochange font" href="#" >Personal Safety - Login Page</a>
  
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  
  <div class="collapse navbar-collapse " id="navbarNav">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link font" onClick="window.location='index.php'" style="">Home</a>
      </li>
     <!-- <li class="nav-item">
        <a class="nav-link font" href="#">Features</a>
      </li>
      <li class="nav-item">
        <a class="nav-link font" href="#">Pricing</a>
      </li>-->
    </ul>
  </div>
  
  <ul class="navbar-nav flex-row ml-md-auto d-none d-md-flex ">
    <!--<li class="nav-item dropdown navbar-collapse">
      <a class="nav-item nav-link dropdown-toggle mr-md-2" href="#" id="bd-versions" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        v4.0
      </a>
      <div class="dropdown-menu dropdown-menu-right" aria-labelledby="bd-versions">
        <a class="dropdown-item" href="/docs/4.1/">Latest (v4.1.x)</a>
        <a class="dropdown-item active" href="/docs/4.0/">v4.0.0</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="https://v4-alpha.getbootstrap.com/">v4 Alpha 6</a>
        <a class="dropdown-item" href="https://getbootstrap.com/docs/3.3/">v3.3.7</a>
        <a class="dropdown-item" href="https://getbootstrap.com/2.3.2/">v2.3.2</a>
      </div>
    </li>-->
	
	<li>
	<a class="btn btn-bd-download d-none d-lg-inline-block mb-3 mb-md-0 ml-md-3" href="registratiPage.php">Registrati</a>
	</li>

  </ul>
</nav>

<div class="container-fluid text-center" style="padding-top:50px">    
	<div class="row content">
		<div class=".col-0 col-sm-1 col-xl-3 sidenav" style="padding: 0px!important;">

		</div>
		
		<div class=".col-12 col-sm-10 col-xl-6 sidenav" style="padding-right: 15px;padding-left: 15px;">
			<?php if(isset($_GET["err"]))
									echo "<h1 class='rosso'>Status: ".$_GET["err"]."</h1>";
							?>
		</div>
		
		<div class=".col-0 col-sm-1 col-xl-3 sidenav" style="padding-right: 15px;padding-left: 15px;">

		</div>
	</div>
	
	<div class="row content">
		<div class=".col-0 col-sm-1 col-xl-3 sidenav" style="padding: 0px!important;">

		</div>
		
		<div class=".col-12 col-sm-10 col-xl-6 sidenav" style="padding-right: 15px;padding-left: 15px;">
			<div class="limiter">
				<div class="container-login100" >
					<div class="wrap-login100" >
					
						<form action ="chkLogin.php" method="post" class="login100-form validate-form p-l-55 p-r-55 p-t-178" >
							<span class="login100-form-title">
							LOGIN
							</span>
							<div class="wrap-input100 validate-input m-b-16" data-validate="Please enter username">
								<input class="input100" type="text" name="username" placeholder="Username" value="<?php if(isset($_GET["oldUsername"]))echo $_GET["oldUsername"];?>">
								<span class="focus-input100"></span>
							</div>
							<div class="wrap-input100 validate-input" data-validate="Please enter password">
								<input class="input100" type="password" name="password" placeholder="Password" value="">
								<span class="focus-input100"></span>
							</div>
							<div class="text-right p-t-13 p-b-23">
								<span class="txt1">
								Dimenticato
								</span>
								<a href="#" class="txt2">
								Username / Password?
								</a>
							</div>
							<div class="container-login100-form-btn">
								<button class="login100-form-btn">
								Sign in
								</button>
							</div>
							<div class="flex-col-c p-t-170 p-b-40">
								<span class="txt1 p-b-9">
								Non hai un account?
								</span>
								<a href="registratiPage.php" class="txt3">
								Registrati cliccando qui
								</a>
							</div>
						</form>
						
					</div>
				</div>
			</div>
		</div>
		
		<div class=".col-0 col-sm-1 col-xl-3 sidenav" style="padding-right: 15px;padding-left: 15px;">

		</div>
	</div>
</div>

</body>

</html>