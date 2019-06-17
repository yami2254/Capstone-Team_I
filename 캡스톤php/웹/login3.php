<?php
//login.php
$DB['host']='localhost';
$DB['id']= 'interface518'; # MySQL 계정 아이디
$DB['pw']='518interface'; # MySQL 계정 패스워드
$DB['db']='interface518';  # DATABASE 이름

$mysqli = new mysqli($DB['host'], $DB['id'], $DB['pw'], $DB['db']);
			header("Content-Type:text/html;charset=utf-8");
			mysqli_query($mysqli, "set session character_set_connection=utf8;");
			mysqli_query($mysqli, "set session character_set_results=utf8;");
			mysqli_query($mysqli, "set session character_set_client=utf8;");
		
if (mysqli_connect_error()) {
    exit('Connect Error (' . mysqli_connect_errno() . ') '. mysqli_connect_error());
}


$userid = $_POST['userid']; 
$userpasswd = $_POST['userpasswd']; 

$query = "SELECT * FROM User_info WHERE U_id = '$userid' AND U_pw = '$userpasswd' ";

if (!$result = $mysqli->query($query)) {
	echo "죄송합니다. 문제가 발생했습니다.";
	exit;
}

if($result->num_rows === 0)
{

	echo "학번 또는 비밀번호가 잘못되었습니다.";
}else{
	$row = $result->fetch_assoc();
	//세션 설정
	session_start();
	$_SESSION["userid"] = $userid;
	$_SESSION["userpasswd"] = $row["userpasswd"];
	if($_SESSION["userid"]=='admin') echo "OK2";
	else echo "OK";
}
$result->free();
$mysqli->close();
?>