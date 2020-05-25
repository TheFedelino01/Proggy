<?php
session_start();


function tryLogin($tableName, $user, $pw)
{
	include 'connection.php';
	$stmt = $conn->prepare("SELECT * FROM $tableName where username=? AND password=?");
	$user = mysqli_real_escape_string($conn, $user);
	$stmt->bind_param("ss", $user, $pw);
	if (!$stmt->execute())
		die("errore db");


	$result = $stmt->get_result();
	//$conn->close();
	return $result;
}


$username =  trim($_POST['username']);
$pw = md5($_POST['password']);

//PROVO CON ADMIN
$result = tryLogin("admin", $username, $pw);
if ($result->num_rows == 1) {
	$row = $result->fetch_assoc();
	$_SESSION["username"] = $_POST['username'];
	$_SESSION["idAdmin"] = $row["id"];
	$_SESSION["nome"] = $row["nome"];
	$_SESSION["cognome"] = $row["cognome"];
	header('Location: admin.php?');
} else {
	$result = tryLogin("cliente", $username, $pw);
	if ($result->num_rows == 1) {
		$row = $result->fetch_assoc();
		$_SESSION["username"] = $_POST['username'];
		$_SESSION["idCliente"] = $row["id"];
		$_SESSION["nome"] = $row["nome"];
		$_SESSION["cognome"] = $row["cognome"];
		header('Location: home.php?');
	} else
		header('Location: login.php?err=Credenziali errate!&oldUsername=' . $_POST['username']);
}
