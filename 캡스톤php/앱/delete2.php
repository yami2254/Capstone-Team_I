
<?php

//delete.php
$DB['host']='localhost';
$DB['id']= 'interface518'; # MySQL 계정 아이디
$DB['pw']='518interface'; # MySQL 계정 패스워드
$DB['db']='interface518';  # DATABASE 이름

$mysqli = new mysqli($DB['host'], $DB['id'], $DB['pw'], $DB['db']);

if (mysqli_connect_error()) {
    exit('Connect Error (' . mysqli_connect_errno() . ') '. mysqli_connect_error());
}
	header("Content-Type:text/html;charset=utf-8");
	mysqli_query($mysqli, "set session character_set_connection=utf8;");
	mysqli_query($mysqli, "set session character_set_results=utf8;");
	mysqli_query($mysqli, "set session character_set_client=utf8;");

$userid = $_POST['userid'];
$rsname = $_POST['rsname'];
$rstime = $_POST['rstime'];
$rusetime = $_POST['rusetime'];
$rdate = $_POST['rdate'];

$query2 = "DELETE FROM Reservate_state WHERE R_SRid='$rsname' and R_starttime = '$rstime' and R_usetime = '$rusetime' and R_date = '$rdate'";

$query = "SELECT * FROM Reservate_state WHERE R_SRid='$rsname' and R_starttime = '$rstime' and R_usetime = '$rusetime' and R_date = '$rdate' and R_first_id = 1 and R_Uid = '$userid'";

if (!$result = $mysqli->query($query)) {
	echo "죄송합니다. 문제가 발생했습니다.";
	exit;
}

if($result->num_rows === 0)
{
	if($flag==1) echo "FAIL2";
	else echo "FAIL";
}else{
	$mysqli->query($query2);
	echo "OK";

}
//$result->free();
$mysqli->close();
?>
