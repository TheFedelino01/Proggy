<?php
//RETURN ELEMENT ARTICLE 

if (session_status() == PHP_SESSION_NONE) {
    session_start();
}
if(!isset($_SESSION["username"]) || isset($_SESSION["idAdmin"])){
	header("Location: index.php?err=Devi effettuare il login!");
	die();
}

include 'connection.php';

//$entiArray=array();

$sql = "SELECT * FROM ente";
$result = $conn->query($sql);

while($row = $result->fetch_assoc()) {
	//array_push($entiArray,$row["nome"].";".$row["cod"]);	
	
	indenta($row["nome"],$row["cod"],$row["img"]);
}

//print_r($entiArray);
//return $entiArray;
$conn->close();
?>


<?php
function indenta($nome, $codice,$img){
$imgLoc="https://managementcue.it/wp-content/uploads/2020/04/opera-industry-4-0.jpg";
if($img!="") $imgLoc="img/ente/$img";

echo('<article class="col-12 col-sm-6 col-md-4 col-lg-3 d-flex flex-column py-2 py-md-3" id="ente'.$codice.'">
			  <a style="cursor: pointer;" onClick="dettaglioEnte('.$codice.',\''.$imgLoc.'\')" class="Bump-link customer-story-card bg-white rounded-1 px-3 pt-3 no-underline d-flex flex-column position-relative height-full">
				<div class="customer-story-card-hero position-relative rounded-1" style="background-image: url('.$imgLoc.');">
				  <div class="customer-story-btn position-absolute top-0 right-0 mt-2 mr-2" role="button">
					<span class="btn-mktg mx-auto py-2 px-3" role="button">
					  <span class="f2">↗</span>
					</span>
				  </div>

				</div>

				<div class="pt-3 d-flex flex-column flex-auto">
				  <h1 class="h4-mktg text-gray-dark mb-1">'.$nome.'</h1>

				  <p class="text-gray f6 flex-auto">
					  <span class="d-block mb-2">
						Codice: '.$codice.'
					  </span>
				  </p>

				  <div class="d-flex width-full f5 border-top flex-justify-between py-3 text-blue-mktg">
					<span>Informazioni</span>
					<span class="Bump-link-symbol">→</span>
				  </div>
				</div>
			  </a>
			</article>');
}

?>