
<?php  
session_start(); if(isset($_SESSION['userid'])){
$link=mysqli_connect("localhost","interface518","518interface","interface518" ); 
 
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  
$userid = $_GET["userid"];

mysqli_set_charset($link,"utf8"); 

$sql="SELECT * FROM Studyroom_info";

$result=mysqli_query($link,$sql);
$data = array();   

if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('sid'=>$row[0],
            'srname'=>$row[1],
            'splace'=>$row[2],
	'usetime'=>$row[3],
	'rdate'=>$row[4]
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

<?}else if(!isset($_SESSION['userid'])){?>

<meta http-equiv="refresh" content="0;url=login2.html"/> <?}?>

