<?php
    if( ($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit']))
    {
//login.php
$DB['host']='localhost';
$DB['id']= 'interface518'; # MySQL 계정 아이디
$DB['pw']='518interface'; # MySQL 계정 패스워드
$DB['db']='interface518';  # DATABASE 이름

$mysqli = new mysqli($DB['host'], $DB['id'], $DB['pw'], $DB['db']);

if (mysqli_connect_error()) {
    exit('Connect Error (' . mysqli_connect_errno() . ') '. mysqli_connect_error());
}
#srname,srloc,srstime,srendtime,srmaxtime,srminper,srmaxper
$srname = $_GET["srname"];
$srloc = $_GET["srloc"];
$srstime = $_GET["srstime"];
$srendtime = $_GET["srendtime"];
$srmaxtime = $_GET["srmaxtime"];
$srminper = $_GET["srminper"];
$srmaxper = $_GET["srmaxper"];
$srbeacon = $_GET["beconid"];
$srid;

//select count(*)as resultsum from Reservate_state,Studyroom_info where Studyroom_info.SR_id='3'AND Reservate_state.R_SRid= '3' and Reservate_state.R_date='20190517' AND Reservate_state.R_starttime BETWEEN '11' AND '13'
$query = "SELECT count(*)as resultsum from Studyroom_info";
//$query = "SELECT U_penalty FROM User_info WHERE U_id = '$userid' ";
if (!$result = $mysqli->query($query)) {
	echo "죄송합니다. 문제가 발생했습니11다.";
	exit;
}

if($result->num_rows === 0)
{
	echo "문제가 발생했습니다.";
}else{
	$row = $result->fetch_assoc();
	$srid=$row["resultsum"]+1;
	echo $srid;
	
	
}

 $query2 = "INSERT INTO Studyroom_info(SR_id,SR_name,SR_place,SR_starttime,SR_endtime,SR_maxtime,SR_minperson,SR_maxperson,SR_beacon,S_NowReservate) VALUES('$srid','$srname','$srloc','$srstime','$srendtime','$srmaxtime','$srminper','$srmaxper','$srbeacon',0) ";
if (!$result2 = $mysqli->query($query2)) {
	echo "33죄송합니다. 문제가 발생했습니다.";
	exit;
}
if($result2->num_rows === 0)
{
	echo "suc.";
}else{
	
	echo "fail";
	
	
}

$result->free();
$mysqli->close();
}
?>

<html>
<head>
<meta charset="UTF-8">
</head>
   <body>
        <?php 
        if (isset($errMSG)) echo $errMSG;
        if (isset($successMSG)) echo $successMSG;
        ?> 
        //스터디룸 추가(스터디룸id는 디비에서 카운트로 가져와서넣어야함)
        <form action="<?php $_PHP_SELF ?>" method="POST">
            스터디룸명: <input type = "text" name = "srname" />
            장소: <input type = "text" name = "srloc" />
            시작시간: <input type = "text" name = "srstime" />
            종료시간: <input type = "text" name = "srendtime" />
            최대시간: <input type = "text" name = "srmaxtime" />
            최소인원: <input type = "text" name = "srminper" />
            최대인원: <input type = "text" name = "srmaxper" />
            비콘명: <input type = "text" name = "beconid" />
            <input type = "submit" name = "submit" />
        </form>
        //입실처리(휴대폰없는인원) R_free를 0으로 바꿔줌
        <form action="<?php $_PHP_SELF ?>" method="POST">
            학번: <input type = "text" name = "stid" />
            스터디룸명: <input type = "text" name = "stid" />
            시작시간: <input type = "text" name = "stid" />
            사용시간: <input type = "text" name = "stid" />
            날짜: <input type = "text" name = "stid" />
            <input type = "submit" name = "submit" />
        </form>

        //스터디룸 제거
        <form action="<?php $_PHP_SELF ?>" method="POST">
            스터디룸아이디: <input type = "text" name = "stid" />
            스터디룸명: <input type = "text" name = "stid" />
            <input type = "submit" name = "submit" />
        </form>
   
   </body>
</html>