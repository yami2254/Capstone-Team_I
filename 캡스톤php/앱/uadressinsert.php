<?php

//대표자예약.php  ?userid=학번&rstart=시작시간&rstudy=스터디룸아디&rtimee=사용시간&rdatee=날짜
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
$adname = $_POST['adname'];
$useraddress = $_POST['useraddress'];

//insert into Reservate_state(R_Uid,R_SRid,R_SRname,R_starttime,R_usetime,R_date,R_free,R_first_id) VALUES(14000004,1,"스터디룸01",12,2,"20190517",1,1)
$query2 = "insert into User_address(userid,adname,useraddress) VALUES('$userid','$adname','$useraddress') ";


if (!$result2 = $mysqli->query($query2)) {
	echo "FAIL";
	//echo $userid;
	exit;
}

if($result2->num_rows === 0)
{

	echo "FAIL";
}else{
	echo "OK";
}
//$result2->free();
$mysqli->close();
?>
