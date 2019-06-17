<?php
//timechk.php
$DB['host']='localhost';
$DB['id']= 'interface518'; # MySQL 계정 아이디
$DB['pw']='518interface'; # MySQL 계정 패스워드
$DB['db']='interface518';  # DATABASE 이름

$mysqli = new mysqli($DB['host'], $DB['id'], $DB['pw'], $DB['db']);

if (mysqli_connect_error()) {
    exit('Connect Error (' . mysqli_connect_errno() . ') '. mysqli_connect_error());
}


$userid = $_GET["userid"];
$rdate = $_GET["rdate"];

$query = "SELECT sum(R_usetime) AS ssum FROM Reservate_state WHERE R_date = '$rdate' and R_Uid = '$userid'";
//$query = "SELECT U_penalty FROM User_info WHERE U_id = '$userid' ";

if (!$result = $mysqli->query($query)) {
	echo "죄송합니다. 문제가 발생했습니다.";
	exit;
}

if($result->num_rows === 0)
{

	echo "학번 또는 비밀번호가 잘못되었습니다.";
}else{
	$row = $result->fetch_assoc();
	if($row["ssum"]>=2){
	echo $row["ssum"];
	}
	else if($row["ssum"]==1){
	echo $row["ssum"];
	}
	else{
	echo "0";
	}

}
$result->free();
$mysqli->close();
?>