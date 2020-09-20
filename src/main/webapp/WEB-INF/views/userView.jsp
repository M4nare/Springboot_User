<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content= "text/html; charset=UTF-8">
<!-- BootStrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
 
<script>
    $(document).ready(function(){
        
            $.ajax({
                
                url : "http://localhost:9999/board/userinfo/${nickname}",
                dataType : "json",
                success : function(data){
                    
                    $("table").html("<tr><th>글번호</th><th>제목</th><th>날짜</th>");
                    
                    var show = "";

                    
                    $.each(data,function(index, item){
                    
                    	 for(var i=0; i<item.length; i++){

                        show += "<td>"+item[i].bno+"</td>";
                        show += "<td>"+'<a href="http://localhost:1010/board/'+item[i].bno+ "\">"+item[i].subject+"</td>";
                        show += "<td>"+item[i].reg_date+"</td></tr>";
                    	
                    	 }
                    	});
                    
                    $("table").append(show);
                    
                }
            
            });
    });
 
</script>





<title>유저정보</title>

</head>
<body>
    <h3>유저정보 상세</h3>
    <div style="padding : 30px;">
      <div class="form-group">
        <label>카카오 이메일 ID:</label>
        <span>${user.id}</span>
      </div>
      <div class="form-group">
        <label>닉네임:</label>
        <span>${nickname}</span>
      </div>
      <div class="form-group">
        <label>상태메세지</label>
        <p>${user.msg}</p>
      </div>
      
      


      
      
      <div class="form-group">
         <button type = "button" style="float :left;" onclick='location.href="/edit/${user.id}"'>수정</button>
          <p></p>
          
          
      </div>
      <br/>
    
    </div>
      <h4>최근에 쓴 게시물</h4>
      <div style="padding : 30px;">
     <table border="1" width="500" height="100">
     <tr><th>글번호</th><th>제목</th><th>날짜</th></tr>
     <tr>
     <td><%=session.getAttribute("bno") %></td>
     <td><a href="http://localhost:1010/board/<%=session.getAttribute("bno") %>"><%=session.getAttribute("subject") %></a></td>
     <td><%=session.getAttribute("date") %></td>
     </tr>
     
</table>
</div>



    
  </body>
    
</body>
</html>


