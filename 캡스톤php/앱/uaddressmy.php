<?php  

$link=mysqli_connect("localhost","interface518","518interface","interface518" ); 
 
if (!$link)  
{  
    echo "MySQL 접속 에러 : ";
    echo mysqli_connect_error();
    exit();  
}  

	header("Content-Type:text/html;charset=utf-8");
mysqli_set_charset($link,"utf8"); 

$userid = $_POST['userid'];
$sql="SELECT DISTINCT * FROM User_address WHERE userid='$userid' ";

$result=mysqli_query($link,$sql);
$data = array();   

if($result){  
    
    while($row=mysqli_fetch_array($result)){
        array_push($data, 
            array('id'=>$row[0],
            'name'=>$row[1],
            'address'=>$row[2],
        ));
    }

    header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
    echo "SQL문 처리중 에러 발생 : "; 
    echo mysqli_error($link);
} 


 
mysqli_close($link);  
   
?>