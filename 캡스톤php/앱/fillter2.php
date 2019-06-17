<?php
	$DB['host']='localhost';
	$DB['id']= 'interface518'; # MySQL ���� ���̵�
	$DB['pw']='518interface'; # MySQL ���� �н�����
	$DB['db']='interface518';  # DATABASE �̸�
		 
	$conn = mysqli_connect($DB['host'], $DB['id'], $DB['pw'], $DB['db']);
	mysqli_set_charset($conn,"utf8");
	
	$loc = $_POST['loc']; 
	$start = $_POST['start']; 
	$use = $_POST['use']; 
	$people = $_POST['people']; 
	

	$cdate = $_GET["cdate"];
	/* �������� ������ */
	$str_sql = "SELECT COUNT(1) AS cnt FROM Studyroom_info";
	if(($object = mysqli_query($conn, $str_sql)) !== FALSE) {
		$obj = mysqli_fetch_object($object);
	}

	if(!isset($obj) || (isset($obj) && intval($obj->cnt) < 1)) {
		mysqli_close($conn);
		$results = array(
			'code' => -1
		);
		echo json_encode($results);
		exit;
	}

	$str_sql = "SELECT * FROM Studyroom_info ORDER BY SR_id ASC";
	if(($object = mysqli_query($conn, $str_sql)) !== FALSE) {
		while($obj = mysqli_fetch_object($object)) {
			$_room[] = $obj;
		}	
	}

	if(!isset($_room)) {
		//"[WARNNING]�� ������ �������� ����.";
		mysqli_close($conn);
		$results = array(
			'code' => -2
		);
		echo json_encode($results);
		exit;
	}

	/* ���� ������ ������ *//*���⿡ ��¥�κ��� �ش糯¥�� �ٲٸ� ��¥�����*/
	$str_sql = "SELECT COUNT(1) AS cnt FROM Reservate_state where R_date='$cdate' and R_first_id='1' and R_free='2'";
	if(($object = mysqli_query($conn, $str_sql)) !== FALSE) {
		$obj2 = mysqli_fetch_object($object);
	}

	$_reserv = array();
	if(isset($obj2) && 0 < intval($obj2->cnt)) {/*���⿡ ��¥�κ��� �ش糯¥�� �ٲٸ� ��¥�����*/
		$str_sql = "SELECT * FROM Reservate_state where R_date='$cdate' and R_first_id='1' and R_free='2' ORDER BY R_SRid ASC, R_starttime ASC";	
		if(($object = mysqli_query($conn, $str_sql)) !== FALSE) {
			while($obj = mysqli_fetch_object($object)) {
				$_reserv[] = $obj;
			}	
		}
	}

	$t_cnt = 0;
	for($rc=0; $rc<count($_room); $rc++) {
		$rvc_arr = array();
		for($rvc=0; $rvc<count($_reserv); $rvc++) {
			if($_room[$rc]->SR_id == $_reserv[$rvc]->R_SRid) array_push($rvc_arr, $_reserv[$rvc]);
		}

		if(!empty($rvc_arr)) $_room[$rc]->reserv = $rvc_arr;
		else $_room[$rc]->reserv = null;
	}

	$results = array(
		'code' => 200,
		'room' => $_room,
		'reserv' => $_reserv
	);

	mysqli_close($conn);
	echo json_encode($results);
	exit;
?>