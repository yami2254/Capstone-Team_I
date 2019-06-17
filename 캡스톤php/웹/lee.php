<?php session_start(); if(isset($_SESSION['userid'])){ $userid=$_SESSION['userid'];?>
<!DOCTYPE HTML>
<!--
	Prologue by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>스터디룸 예약</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/css/main.css" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
		<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
		<link rel="stylesheet" type="text/css" href="${ctx }/css/sweetalert.css" />
		<script type="text/javascript" src="<c:url value='/js/sweetalert.min.js'/>"></script>
		<script> var rrsult; </script>
		<script language="javascript">
			function getSelectValue0(frm)
			{
			frm.textValue0.value = frm.selectBox0.options[frm.selectBox0.selectedIndex].text;
			frm.optionValue0.value = frm.selectBox0.options[frm.selectBox0.selectedIndex].value;
			}
			function getSelectValue1(frm)
			{
			frm.textValue1.value = frm.selectBox1.options[frm.selectBox1.selectedIndex].text;
			frm.optionValue1.value = frm.selectBox1.options[frm.selectBox1.selectedIndex].value;
			}
			function getSelectValue2(frm)
			{
			frm.textValue2.value = frm.selectBox2.options[frm.selectBox2.selectedIndex].text;
			frm.optionValue2.value = frm.selectBox2.options[frm.selectBox2.selectedIndex].value;
			}
			function getSelectValue3(frm)
			{
			frm.textValue3.value = frm.selectBox3.options[frm.selectBox3.selectedIndex].text;
			frm.optionValue3.value = frm.selectBox3.options[frm.selectBox3.selectedIndex].value;
			}
			function getSelectValue4(frm)
			{
			frm.textValue4.value = frm.selectBox4.options[frm.selectBox4.selectedIndex].text;
			frm.optionValue4.value = frm.selectBox4.options[frm.selectBox4.selectedIndex].value;
			}
			function getSelectValue5(frm)
			{
				frm.textValue5.value = frm.selectBox5.options[frm.selectBox5.selectedIndex].text;
				frm.optionValue5.value = frm.selectBox5.options[frm.selectBox5.selectedIndex].value;
			}
			function getSelectValue6(frm)
			{
				frm.textValue6.value = frm.selectBox6.options[frm.selectBox6.selectedIndex].text;
				frm.optionValue6.value = frm.selectBox6.options[frm.selectBox6.selectedIndex].value;
			}
			</script>
		 <?php
			$today3 = "aa";
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
			$sql="SELECT * FROM User_info WHERE U_id = '$userid' ";
			$res = $mysqli->query($sql);
			
			while($row = mysqli_fetch_array($res, MYSQL_BOTH)){
				$login_username = $row['U_name'];
			}
			$sql="SELECT * FROM Studyroom_info";
			$res = $mysqli->query($sql);
			$count = 0;
			while($row2 = mysqli_fetch_array($res, MYSQL_BOTH)){
				$count+=1;
				$SR_n[$count] = $row2['SR_name'] . '(' . $row2['SR_place'] . ')';
				if($row2['SR_endtime']==23)$SR_t[$count] = $row2['SR_starttime'] . ":00 - " . ($row2['SR_endtime']+1) . ":00";
				else $SR_t[$count] = $row2['SR_starttime'] . ":00 - " . $row2['SR_endtime'] . ":00";
				$SR_p[$count] = $row2['SR_minperson'] . " - " . $row2['SR_maxperson'] . "명";
				$SR_u[$count] = "최대 " . $row2['SR_maxtime'] . "시간";
				$SR_start[$count] = $row2['SR_starttime'];
				$SR_end[$count] = $row2['SR_endtime'];

			}
			$sql3 = "SELECT * FROM Reservate_state WHERE R_date >= CURRENT_DATE() and R_uid = '$userid'";
			$res3 = $mysqli->query($sql3);
			$count3 = 0;
			while($row3 = mysqli_fetch_array($res3, MYSQL_BOTH)){
				$count3+=1;
				$myreservate[$count3] = $row3['R_SRname'] . ' / ' . $row3['R_date'] . ' / ' . $row3['R_starttime'] . '시부터 ' . $row3['R_usetime'] . '시간';
			}
			
			?>
	</head>
	<body class="is-preload">

		<!-- Header -->
			<div id="header">

				<div class="top">

					<!-- Logo -->
						<div id="logo">
						<a href="http://interface518.dothome.co.kr/caps/logout.php"><span class="image avatar48"><img src="images/logout_image.png" alt="" /></span></a>
							<h1 id="title"><?php echo $login_username;?></h1> <!--DB이름-->
							<p><?php echo $userid;?></p> <!--DB학번-->
						</div>

					<!-- Nav -->
						<nav id="nav">
							<ul>
								<li><a href="#top" id="top-link"><span class="icon fa-home">홈</span></a></li>
								<li><a href="#portfolio" id="portfolio-link"><span class="icon fa-th">예약현황</span></a></li>
								<li><a href="#about" id="about-link"><span class="icon fa-envelope">예약하기</span></a></li>
								<li><a href="#contact" id="contact-link"><span class="icon fa-user">마이페이지</span></a></li>
							</ul>
						</nav>

				</div>

				<div class="bottom">

					<!-- Social Icons 
						<ul class="icons">
							<li><a href="#" class="icon fa-twitter"><span class="label">Twitter</span></a></li>
							<li><a href="#" class="icon fa-facebook"><span class="label">Facebook</span></a></li>
							<li><a href="#" class="icon fa-github"><span class="label">Github</span></a></li>
							<li><a href="#" class="icon fa-dribbble"><span class="label">Dribbble</span></a></li>
							<li><a href="#" class="icon fa-envelope"><span class="label">Email</span></a></li>
						</ul>
-->
				</div>

			</div>

		<!-- Main -->
			<div id="main">

				<!-- Intro -->
					<section id="top" class="one dark cover">
						<div class="container">

							<header>
								<h2 class="alt">세종대학교 학술정보원 예약시스템<br /></h2>
								<!--<p><a href="http://library.sejong.ac.kr">기존사이트</a>와 비교하기</p>-->
							</header>
<!--
							<footer>
								<a href="#about" class="button scrolly">예약현황 보러가기</a>
							</footer>
-->
						</div>
					</section>

				<!-- Portfolio -->
					<section id="portfolio" class="two">
						<div class="container">

							<header>
								<h3>이용방법</h3>
							</header>

							<p>1. 날짜를 선택하고 조회를 눌러 현재 스터디룸의 예약 현황을 확인한다.<br/>
								2. 예약할 날짜, 스터디룸, 시작 시간, 사용 시간, 인원을 선택하여 조회를 눌러 예약 가능한지 확인한다.<br/>
								3. 사용 인원 만큼 동반이용자를 입력한다.<br/>
								 <strong>(단, 대표자를 포함하여 모든 이용자는 패널티가 없어야한다.)</strong> <br/>
								4. 사용 목적을 입력한다. <br/>
								5. 예약 버튼을 누른다. <br />
							</p>
							<header>
								<h2>예약현황</h2>
							</header>
							<form id="nowroom">
							<ul class = "icons">
								<li>
										<select name="selectBox6" onChange="getSelectValue6(this.form);">
											<option value="">날짜</option>
											<?php
											for($i=0; $i<7; $i++){
												$select_date = strtotime("$i days");
												echo "<option value=\"";
												echo date("Ymd",$select_date);
												echo "\">";
												echo date("Y년 m월 d일" , $select_date);
												echo "</option>";
											}
											?>
										</select>
								</li>
								<li><input type="submit" value="조회"/></li>
								<script src="//code.jquery.com/jquery.min.js"></script>
									<script language="javascript">
									
										var flag2=0;
											$("#nowroom").on("submit", function(event) {
											event.preventDefault(); // Prevent the form from submitting via the browser
											var form = $(this);
											$.ajax({
											  type: "GET",
											  url: "t4.php",
											  data: $('#nowroom').serialize()
											}).done(function(data) {
												var date_sr = document.getElementById("date_sr");
												if(flag2==1){
													$("#date_sr").empty();
												}
												data = $.trim(data);
												if(data=='FAIL')
													swal("","날짜를 선택해주세요.","error");
												else
												{
													var arr=[[],[]] ,ii,start=0,jj;
													for(ii=1; ii<=34; ++ii){
														arr[ii]=data.substr(start,24);
														start+=24;
													}
													ii=0;
												/*	var no[40][26] , i,j,pl=0;
													for(i=1; i<=33; ++i)
													{
														no[i] = data.substr(pl, 24);
														pl+=24;
													}*/
													date_sr = document.getElementById('date_sr');
													
													x='<nav id="totot"><table class="table_style">';
													/*x+='<ul>';
													x+='<li class="nav-list" id="fb">스터디룸</li>';
													x+='<li class="nav-list" id="fb">이용시간</li>';
													x+='<li class="nav-list" id="fb">이용인원</li>';
													x+='<li class="nav-list" id="fb">최대 이용시간</li>';
											
													x+='</ul>';*/
													x+='<tr bgcolor="#545454" >';
													x+='<td><font color=white size=4>스터디룸</td>';
													x+='<td><font color=white size=4>이용시간</td>';
													x+='<td><font color=white size=4>이용인원</td>';
													x+='<td><font color=white size=4>최대 이용시간</td>';
													x+='</tr>';
													x+= '<colgroup>';
													x+= '<col width="*"/>';
													x+= '<col width="20%"/>';
													x+= '<col width="20%"/>';
													x+= '<col width="20%"/>';
													x+= '</colgroup>';
													<? for($i=1; $i<=$count; $i++) {?>
													ii+=1;
													x+='<tr>';
													x+='<td>'; 
													x+= '<?echo $SR_n[$i];?>';
													x+='</td>';
													x+='<td>';
													x+= '<?echo $SR_t[$i]; ?>';
													x+='</td>';
													x+='<td>';
													x+= '<?echo $SR_p[$i]; ?>';
													x+='</td>';
													x+='<td>';
													x+= '<?echo $SR_u[$i]; ?>';
													x+='</td>';
													x+='</tr>';
													x+= '<tr><td colspan="4"><table class="table_style"><tr>';
												
													for(jj=<? echo $SR_start[$i];?>; jj<<? if($SR_end[$i]==23) echo ($SR_end[$i]+1); else echo $SR_end[$i];?>; ++jj)
													{
														/*x+='<td>';
														if(arr[ii][jj]==0) x+='X';
														else x+='O';
														x+='</td>';*/
														if(arr[ii][jj]==0) x+='<td bgcolor="skyblue">' + jj + '</td>';
														else x+='<td bgcolor="#FF7E8F" ><font color=white>' + jj + '</td>';
													}
													x+= '</tr></table></td></tr>';
													<?}?>
													x+='</table></nav>';

													date_sr.innerHTML = x;

													swal("","선택한 날짜로 조회합니다.","success");
													flag2=1;
												}
												
											}).fail(function(data) {
												swal("","오류발생 : 다시 시도해 보시기 바랍니다.","error"); //"success"
											});
									  	}); // submit listner's end point
									</script>
							</ul>
							<div id="date_sr">
								
							</div>
							</form>
						</div>
					</section>
				
				<!-- About Me -->
					<section id="about" class="three">
						<div class="container">

							<header>
								<h2>예약하기</h2>
							</header>
							<!--<a href="#" class="image featured"><img src="images/pic08.jpg" alt="" /></a> -->
							<form id="roomchk">
							<ul>
								<li>
										<select name="selectBox0" onChange="getSelectValue0(this.form);">
											<option value="">날짜</option>
											<?php
											for($i=0; $i<7; $i++){
												$select_date = strtotime("$i days");
												echo "<option value=\"";
												echo date("Ymd",$select_date);
												echo "\">";
												echo date("Y년 m월 d일" , $select_date);
												echo "</option>";
											}
											?>
										</select>
								</li>
							</ul>
							<ul class="icons">
<!--										<li> 스터디룸 선택 </li> -->
										<li>
										<select name="selectBox1" onChange="getSelectValue1(this.form);">
											<option value="">스터디룸</option>
											<?php
											for($i=1; $i<=$count; $i++){
												echo "<option value=\"option";
												echo $i;
												echo "\">";
												echo $SR_n[$i];
												echo "</option>";
											}
											?>
										</select>
										</li>
										<li>
										<select name="selectBox2" onChange="getSelectValue2(this.form);">
											<option value="">시작 시간</option>
											<?php
											for($i=0; $i<=23; $i++){
												echo "<option value=\"option";
												echo $i;
												echo "\">";
												echo $i . "시";
												echo "</option>";
											}
											?>
										</select>
										</li>
										<li>
										<select name="selectBox3" onChange="getSelectValue3(this.form);">
											<option value="">사용 시간</option>
											<?php
											for($i=1; $i<=4; $i++){
												echo "<option value=\"option";
												echo $i;
												echo "\">";
												echo $i . "시간";
												echo "</option>";
											}
											?>
										</select>
										</li>
										<li>
										<select name="selectBox4" onChange="getSelectValue4(this.form);">
											<option value="">인원</option>
											<?php
											for($i=2; $i<=32; $i++){
												echo "<option value=\"option";
												echo $i;
												echo "\">";
												echo $i . "명";
												echo "</option>";
											}
											?>
										</select>
										</li>
										<li><input type="submit" value="조회"/></li>
<script src="//code.jquery.com/jquery.min.js"></script>
<script>
	var flag=0;
										$("#roomchk").on("submit", function(event) {
		event.preventDefault(); // Prevent the form from submitting via the browser
		
		
		var form = $(this);
		$.ajax({
		  type: "GET",
		  url: "t2.php",
		  data: $('#roomchk').serialize()
		}).done(function(data) {
			var cou = document.getElementById("cou");
			if(flag==1){
				$("#cou").empty();
				
			}
			  data = $.trim(data);
			  var ok=data.replace("OK","");
			  ok=ok.toString();
			  //swal("","ok.length","success");
			  if(data == "FAIL")swal("","예약불가능","error");
			  else if(data=="FAIL2")swal("","스터디룸 정보와 맞지 않음","error");
			  else if(ok.length > 0)
			  {
			  	_person = ok;
				_person *= 1;
			    	//document.write("통과댐");

				swal("","예약 가능","success"); //
				//document.write("<p> 야호 </p>");
				rrsult = $('#roomchk').serialize();
				cou = document.getElementById('cou');
				var unun = '<? echo $login_username; ?>';
				var uiui = '<? echo $_SESSION['userid']; ?>';
				cou.innerHTML += '<div class="col-6 col-12-mobile"><input type="text" id="n1" name="name" value="' + unun + '" readOnly/></div>';
				cou.innerHTML += '<div class="col-6 col-12-mobile"><input type="text" id="1" name="email" value="' + uiui + '" readOnly/></div>';
	
				for(var i=2; i<=_person; i++){
                cou.innerHTML += '<div class="col-6 col-12-mobile"><input type="text" id=n' + i + ' name="name" placeholder="이름" /></div>';
				cou.innerHTML += '<div class="col-6 col-12-mobile"><input type="text" id=' + i + ' name="email" placeholder="학번" /></div>';
				}
				flag=1;
			}
		}).fail(function(data) {
			swal("","오류발생 : 다시 시도해 보시기 바랍니다.","error"); //"success"
		});
  	}); // submit listner's end point
</script>
									
</ul>

</form>
<form id="personchk">
    <div class="row">
        
        <div class="row" id="cou">

        </div>
        <div class="col-12">
            
            <textarea id="purpose" name="message" placeholder="사용목적"></textarea>
        </div>
        
        <div class="col-12">
            <input type="submit" value="예약" />
            <script src="//code.jquery.com/jquery.min.js"></script>
            <script>
                //var combi = $('#roomchk').serialize();

            $("#personchk").on("submit", function(event) {
                event.preventDefault(); // Prevent the form from submitting via the browser
                //alert(rrsult);
                //여기서부터 패널티검사
                var chknum = 0; // 패널티 여부 개수
                var numm=$("input[name=email]").length;
                for(i=1;i<=numm;i++){
                //alert($("#" + i ).val());
                var temp = "userid=" + $("#" + i ).val()+"&username=" + $("#n" + i ).val();
                var form = $(this);
                $.ajax({
                  type: "GET",
                  url: "panel.php",
                  async: false,
                  data: temp 
                }).done(function(data) {
                    data = $.trim(data);
                    if(data=="0"){
                        chknum=chknum+1;
                    
                    }
                    
                        
                }).fail(function(data) {
                    //swal("","오류발생 : 다시 시도해 보시기 바랍니다.","error"); //"success"
                });
                }//for의끝
                //alert($('#personchk').serialize());
                //var temp = $('#personchk').serialize();
                //var combi = rrsult + temp;
                //alert(combi);
                //여기부터 패널티체크 끝나고 if
                //여기서 부터 최대시간 조회
                if(chknum==numm){
                    var chknum = 0; // 패널티 여부 개수
                var numm=$("input[name=email]").length;
                var rdatee=rrsult.substring(rrsult.search("Box0")+5,rrsult.search("&select"));//예약날짜	
                var rtimee=rrsult.substring(rrsult.search("Box3=option")+11,rrsult.search("&selectBox4"));//예약사용시간
                    
                //alert(rdatee);
                //alert(rtimee);
                //swal("","패널티가 없수다.","success");
                //alert("통과");	

            
                for(i=1;i<=numm;i++){
                //alert($("#" + i ).val());
                var temp = "userid=" + $("#" + i ).val() + "&rdate=" + rdatee;
                var form = $(this);
                $.ajax({
                  type: "GET",
                  url: "timechk.php",
                  async: false,
                  data: temp 
                }).done(function(data) {
                    data = $.trim(data);
                    if(rtimee=="2"){
                        if(data=="0"){
                        chknum=chknum+1;
                        }
                    }
                    else if(rtimee=="1"){
                        if(data=="0"||date=="1"){
                        chknum=chknum+1;
                        }
                    }
                                                                                                                                                    
                }).fail(function(data) {
                    //swal("","오류발생 : 다시 시도해 보시기 바랍니다.","error"); //"success"
                });
                    }//for의끝


                }//if의끝
                if(chknum==numm){
                    var rdatee=rrsult.substring(rrsult.search("Box0")+5,rrsult.search("&select"));//예약날짜
                    var rtimee=rrsult.substring(rrsult.search("Box3=option")+11,rrsult.search("&selectBox4"));//예약사용시간
                    var rstudy=rrsult.substring(rrsult.search("Box1=option")+11,rrsult.search("&selectBox2"));//스터디룸id
                    var rstart=rrsult.substring(rrsult.search("Box2=option")+11,rrsult.search("&selectBox3"));//예약사용시작시간
                    var chknum = 0; // 예약 여부 개수
                    //alert(rdatee);
                    //alert(rtimee);
                    //alert("스터디룸아디"+rstudy);
                    //alert("시작시간"+rstart);
                    

                    //여기서부터 예약
                    var numm=$("input[name=email]").length;
                for(i=1;i<=numm;i++){
                    if(i==1){
                //alert($("#" + i ).val());
				
                //var temp = "userid=" + $("#" + i ).val() + "&rstart=" + rstart + "&rstudy=" + rstudy + "&rtimee=" + rtimee + "&rdatee=" + rdatee;
                var form = $(this);
                $.ajax({
                  type: "POST",
                  url: "freservate.php",
                  async: false,
                  data: {userid: $("#" + i ).val(), rstart: rstart, rstudy: rstudy, rtimee: rtimee, rdatee: rdatee, ruse: $("#purpose").val()}
                }).done(function(data) {
                    data = $.trim(data);
                    
                        if(data=="OK"){
                        chknum=chknum+1;
                        }
                
                                                                                                                                                    
                }).fail(function(data) {
                    //swal("","오류발생 : 다시 시도해 보시기 바랍니다.","error"); //"success"
                });
                    }//if==1끝
                    else{
                        //alert($("#" + i ).val());
                //var temp = "userid=" + $("#" + i ).val() + "&rstart=" + rstart + "&rstudy=" + rstudy + "&rtimee=" + rtimee + "&rdatee=" + rdatee;
                var form = $(this);
                $.ajax({
                  type: "POST",
                  url: "freservate2.php",
                  async: false,
                  data: {userid: $("#" + i ).val(), rstart: rstart, rstudy: rstudy, rtimee: rtimee, rdatee: rdatee, ruse: $("#purpose").val()}
                }).done(function(data) {
                    data = $.trim(data);
                    
                        if(data=="OK"){
                        chknum=chknum+1;
                        }
                                                                                                                                                    
                }).fail(function(data) {
                    //swal("","오류발생 : 다시 시도해 보시기 바랍니다.","error"); //"success"
                });

                    }//else끝
                    }//for의끝	
                }//if의끝
                else {
                    swal("","예약이 불가능 합니다.","error");
                }
                //최종출력
                if(chknum==numm){
                    swal("","예약 되었습니다.","success");
                }
                else{
                    swal("","예약이 불가능 합니다.","error");
                }
                
                
            });//submit의끝
        </script>
        </div>
    </div>
</form>

</div>
</section>
					<!-- Contact -->
					<section id="contact" class="four">
						<div class="container">

							<header>
								<h2>마이페이지</h2>
							</header>
							<form id = "r_cancel">
							<select name="selectBox5" onChange="getSelectValue5(this.form);">
											<option value="">예약현황</option>
											<?php
											for($i=1; $i<=$count3; $i++){
												echo "<option value=\"";
												echo $myreservate[$i];
												echo "\">";
												echo $myreservate[$i];
												echo "</option>";
											}
											?>
										</select><br>
										
										<input type="submit" value="예약취소">
									<script src="//code.jquery.com/jquery.min.js"></script>
									<script>
										var flag=0;
											$("#r_cancel").on("submit", function(event) {
											event.preventDefault(); // Prevent the form from submitting via the browser
											
											
											var form = $(this);
											$.ajax({
											  type: "GET",
											  url: "t3.php",
											  data: $('#r_cancel').serialize()
											}).done(function(data) {
												data = $.trim(data);
												if(data=="OK")
													swal("","예약이 취소되었습니다.","success");
												else if(data=="FAIL2")
													swal("","취소할 예약을 선택해주세요.","error");
												else
													swal("","대표자만 예약을 취소할 수 있습니다.","error");
											}).fail(function(data) {
												swal("","오류발생 : 다시 시도해 보시기 바랍니다.","error"); //"success"
											});
									  	}); // submit listner's end point
									</script>
							</form>
						</div>
					</section>
				

			</div>

		<!-- Footer -->
			<div id="footer">

				

			</div>

		<!-- Scripts -->
			<script src="assets/js/jquery.min.js"></script>
			<script src="assets/js/jquery.scrolly.min.js"></script>
			<script src="assets/js/jquery.scrollex.min.js"></script>
			<script src="assets/js/browser.min.js"></script>
			<script src="assets/js/breakpoints.min.js"></script>
			<script src="assets/js/util.js"></script>
			<script src="assets/js/main.js"></script>
			<style>
            *, body {margin:0;padding:0}
            #totot {
                width: 100%;
                display: table;
                border-collapse: collapse;
                table-layout:fixed;
                border: none;
            }
			#ft{
				font-size:medium;
			}
			#fb{
				font-weight: 900;
			}
            #totot ul {
                display: table-row;
				
            }
            #totot li {
                display: table-cell;
                text-align:center;
                padding: 5px 0;
                 
                /*아래는 메뉴 영역을 구분하기 위한 옵션입니다.*/
                border-top:1px solid #69c;
                border-bottom:1px solid #69c;
            }
            /*아래는 메뉴 영역을 구분하기 위한 옵션입니다.*/
            .nav-list+.nav-list{border-left:1px solid #69c}
 
            .nav-list:hover,
            .nav-list:focus {
                background: #ecf2f9;
            }
						.table_style{
							border-collapse: collapse;
							width: 100%;
							table-layout:fixed;
							text-align: center;
						}
						.table_style tr{
							display: table-row;
							width:100%;
						}
						.table_style td{
							border: 1px solid #333;
							font-size: small;
							font-weight: bold;
						}
        </style>
	</body>
</html>
<?}else if(!isset($_SESSION['userid'])){?>
<meta http-equiv="refresh" content="0;url=login2.html"/> <?}?>
