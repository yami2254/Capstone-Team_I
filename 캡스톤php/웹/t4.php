<?
    
    $DB['host']='localhost';
    $DB['id']= 'interface518'; # MySQL 계정 아이디
    $DB['pw']='518interface'; # MySQL 계정 패스워드
    $DB['db']='interface518';  # DATABASE 이름

    $mysqli = new mysqli($DB['host'], $DB['id'], $DB['pw'], $DB['db']);

    if (mysqli_connect_error()) {
        exit('Connect Error (' . mysqli_connect_errno() . ') '. mysqli_connect_error());
    }
    $sql1 = "SELECT * FROM Studyroom_info ";
    $res1 = $mysqli->query($sql1);
    $count1 = 0;
    while($row1 = mysqli_fetch_array($res1, MYSQL_BOTH)){
        $count1+=1;
        $ST_id[$count1] = $row1['SR_id'];
        $ST_st[$count1] = $row1['SR_starttime'];
        $ST_ed[$count1] = $row1['SR_endtime'];
    }
    $select_date = $_GET["selectBox6"];
    if($select_date=='')echo "FAIL";
    else{
        $sql2 = "SELECT * FROM Reservate_state WHERE R_date= '$select_date' and R_first_id=1";
        $res2 = $mysqli->query($sql2);
        $count2 = 0;
        while($row2 = mysqli_fetch_array($res2, MYSQL_BOTH)){
            $count2+=1;
            $R_id[$count2] = $row2['R_SRid'];
            $R_st[$count2] = $row2['R_starttime'];
            $R_ed[$count2] = $row2['R_usetime'];
        }
        for($j=1; $j<=$count1; ++$j){
            for($i=0; $i<24; ++$i)$time_table[$j][$i]=0;
        }
        for($i=1; $i<=$count2; ++$i)
        {
            for($j=$R_st[$i]; $j<($R_st[$i]+$R_ed[$i]); ++$j)
            {
                $time_table[$R_id[$i]][$j]=1;
            }
        }
        for($i=1; $i<=$count1; ++$i){
            for($j=0; $j<24; ++$j)
                echo $time_table[$i][$j];
        }
    }
?>