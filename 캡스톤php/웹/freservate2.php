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

$rstart = $_POST['rstart'];
$rstudy = $_POST['rstudy'];
$rtimee = $_POST['rtimee'];
$rdatee = $_POST['rdatee'];
$ruse = $_POST['ruse'];
//$rsname = $_POST['rsname'];
//여기서부터 스터디룸 id로 이름 가져오기
$query = "SELECT SR_name FROM Studyroom_info WHERE SR_id='$rstudy' ";


if (!$result = $mysqli->query($query)) {
	echo "죄송합니다. 문제가 발생했습니다.";
	echo $userid;
	exit;
}

if($result->num_rows === 0)
{

	echo "학번 또는 비밀번호가 잘못되었습니다.";
}else{
	$row = $result->fetch_assoc();
		//echo $rstudy;
		$rsname = $row["SR_name"];
		//echo $rsname ;
}
$result->free();
//여기까지 이름가져오기 끝 아래부터 예약시작
//insert into Reservate_state(R_Uid,R_SRid,R_SRname,R_starttime,R_usetime,R_date,R_free,R_first_id) VALUES(14000004,1,"스터디룸01",12,2,"20190517",1,1)
$query2 = "insert into Reservate_state(R_Uid,R_SRid,R_SRname,R_starttime,R_usetime,R_date,R_free,R_first_id,R_use) VALUES('$userid','$rstudy','$rsname','$rstart','$rtimee','$rdatee',1,0,'$ruse') ";


if (!$result2 = $mysqli->query($query2)) {
	echo "FAIL";
	//echo $userid;
	exit;
}

if($result2->num_rows === 0)
{

	echo "fai1l";
}else{
	echo "OK";
}
//$result2->free();
$mysqli->close();
?>
