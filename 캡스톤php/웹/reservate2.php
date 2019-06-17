<!doctype html> 
<html> 
<head> 
<title></title> 
<meta charset='utf-8'> 
<script src="http://code.jquery.com/jquery-1.7.1.js"></script> 
<script type='text/javascript'> 
$(function() { 
 var setTableHTML = "";
  $(document).ready(function() {  // 버튼 눌럿을 때로 하려면 $('#btn').click(function() { 
    $.ajax({ 
      url: './reservate.php', 
      type: 'POST', 
      data:({}), 
      dataType: 'json', // or html 
      contentType : "application/x-www-form-urlencoded; charset=UTF-8", 
      success: function(data) { 
        //alert(data.result[0].sid + " : " + data.result[0].srname + "테스트시작" + data.result.length); 
	setTableHTML+='<select name ="Room" id="Room" onselect=roomchk><option value="">스터디룸목록</option>';
	for(let i=0;i<data.result.length;i++){
		setTableHTML+='<option value="' + data.result[i].sid + '">' + data.result[i].srname + '(' + data.result[i].splace + ')' + '</option>';
		//setTableHTML+=data.result[i].sid + " : " + data.result[i].srname;
		
	}
      
      
	$("#res_field").html(setTableHTML);

     }, 
     error: function(xhr, err) { 
       alert(xhr.responseText); 
       return false; 
    } 
 }); 
}); 
}); 
</script> 
</head> 
<body> 
  
	<div id="res_field">
	</div>
  
  
</body> 
</html> 