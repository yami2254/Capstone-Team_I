<?php
//login.php
$DB['host']='localhost';
$DB['id']= 'interface518'; # MySQL 계정 아이디
$DB['pw']='518interface'; # MySQL 계정 패스워드
$DB['db']='interface518';  # DATABASE 이름

$mysqli = new mysqli($DB['host'], $DB['id'], $DB['pw'], $DB['db']);

if (mysqli_connect_error()) {
    exit('Connect Error (' . mysqli_connect_errno() . ') '. mysqli_connect_error());
}

$sr_date = $_GET["selectBox0"];
$sr_name = str_replace("option","",$_GET["selectBox1"]);
$sr_starttime = str_replace("option", "", $_GET["selectBox2"]);
$sr_usetime = str_replace("option","",$_GET["selectBox3"]);
$sr_person = str_replace("option","",$_GET["selectBox4"]);
//select count(*)as resultsum from Reservate_state,Studyroom_info where Studyroom_info.SR_id='3'AND Reservate_state.R_SRid= '3' and Reservate_state.R_date='20190517' AND Reservate_state.R_starttime BETWEEN '11' AND '13'
$query = "SELECT count(*)as resultsum from Reservate_state,Studyroom_info where Studyroom_info.SR_id='$sr_name' and Reservate_state.R_SRid= '$sr_name' and Reservate_state.R_date='$sr_date' AND ((Reservate_state.R_starttime + Reservate_state.R_usetime) > '$sr_starttime' AND ('$sr_starttime' + '$sr_usetime' > Reservate_state.R_starttime))";
 $query2 = "SELECT count(*)as resultsum2 from Studyroom_info WHERE Studyroom_info.SR_id='$sr_name' AND (Studyroom_info.SR_minperson <= '$sr_person' AND Studyroom_info.SR_maxperson >= '$sr_person') AND (Studyroom_info.SR_starttime <= '$sr_starttime' AND Studyroom_info.SR_endtime >= ('$sr_starttime' + '$sr_usetime')) AND Studyroom_info.SR_maxtime >= '$sr_usetime'";
//$query = "SELECT U_penalty FROM User_info WHERE U_id = '$userid' ";
if (!$result = $mysqli->query($query)) {
	echo "죄송합니다. 문제가 발생했습니다.";
	exit;
}
if (!$result2 = $mysqli->query($query2)) {
	echo "죄송합니다. 문제가 발생했습니다.";
	exit;
}

if($result->num_rows === 0)
{
	echo "학번 또는 비밀번호가 잘못되었습니다.";
}else{
	$row = $result->fetch_assoc();
	$row2 = $result2->fetch_assoc();
	if($row["resultsum"]==0){ //패널티 없을 때 
		if($row2["resultsum2"] > 0)
			echo "OK".$sr_person;
		else
			echo "FAIL2";

	}
	else{
		echo "FAIL";
	//echo $row["resultsum"];
	} 
}
$result->free();
$mysqli->close();
?>