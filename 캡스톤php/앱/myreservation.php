<?php  
 
$link=mysqli_connect("localhost","interface518","518interface","interface518" ); 
 
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  
$userid = $_GET["userid"];

mysqli_set_charset($link,"utf8"); 
//$sql="SELECT * FROM Reservate_state WHERE R_date >= CURRENT_DATE() and R_uid = '$userid' and R_free = '1'";
$sql="SELECT R_Uid,R_SRid,R_SRname,R_starttime,R_usetime,R_date,SR_beacon,SR_Major,SR_Minor FROM Reservate_state,Studyroom_info WHERE R_date >= CURRENT_DATE() and R_uid = '$userid' and R_free = '1' and R_SRid = SR_id ";

$result=mysqli_query($link,$sql);
$data = array();   

if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('sid'=>$row[0],
            'srloc'=>$row[1],
            'srname'=>$row[2],
            'stime'=>$row[3],
   'usetime'=>$row[4],
   'rdate'=>$row[5],
'srbeacon'=>$row[6],
'srmajor'=>$row[7],
'srminor'=>$row[8]
        ));
    }

    header('Content-Type: application/json; charset=utf8');
$json = json_encode(array(result=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 

mysqli_close($link); 
?>
