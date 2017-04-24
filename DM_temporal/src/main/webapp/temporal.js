function callOperators(){
	
	var operator_number=$('#sel1 :selected').val();	
	$('#calenderPickerPreviousOperator').hide();
	$('#calenderPickerNextOperator').hide();
	$('#showResult').empty();
	
	switch (operator_number) {
    case '11':
    		firstOperator();    	
        break;
    case '12':
    		lastOperator();   
        break;
    case '2':
        $('#calenderPickerPreviousOperator').show();
        break;
    case '3':
    	$('#calenderPickerNextOperator').show();
        break;
    case '41':
    	previous_Day_Operator();
        break;
    case '42':
    	previous_Hour_Operator();
        break;
    case  '43':
    	previous_Month_Operator();
        break;
    case  '51':
        break;
    case  '52':
        break;
    case  '53':
        break;
    case  6:
        break;
    case  7:
        break;
    case  81:
        break;
    case  82:
        break;
	}
}
//callOperators closes

function firstOperator(){
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/first_operator",
	        contentType:"text/plain",
 	        
           
           success: function(response){   
               if(response!=null){
              	 var obj = JSON.parse(JSON.stringify(response)); 
              	 var status = obj.msg;
              	 var d = new Date(obj.validFrom);
              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';
              	 element = element +
              	 			'<tr>'+
              	 			'<td>'+status+'</td>'+
              	 			'<td>'+d+'</td>'+
              	 			'<tr>';
              	 $('#showResult').append(element);
               }  
               
               else
            	   {
            	  	alert("data cannot be fetched");
            	   }
             },
	    
  			error: function(e){  
    					alert('Error121212: ' + e);  
    					alert(e.toString());
  			}				
    
	  });//ajax call ends
}

function lastOperator(){
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/last_operator",
	        contentType:"text/plain",
 	        datatype:"json",
           
           success: function(response){   
               if(response!=null){
              	 var obj = JSON.parse(JSON.stringify(response)); 
              	 var status = obj.msg;
              	 var d = new Date(obj.validFrom);
              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';
              	 element = element +
              	 			'<tr>'+
              	 			'<td>'+status+'</td>'+
              	 			'<td>'+d+'</td>'+
              	 			'<tr>';
              	 $('#showResult').append(element);
               }  
               
               else
            	   {
            	  	alert("data cannot be fetched");
            	   }
             },
	    
  			error: function(e){  
    					alert('Error121212: ' + e);  
    					alert(e.toString());
  			}				
    
	  });//ajax call ends
}

function previousOperator(){
	var dateString = document.getElementById('from').value;
	var date = new Date(dateString).getTime();
	
	$.cookie("date1",date);
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/previous_operator",
	        contentType:"application/json",
 	        datatype:"json",
           
           success: function(response){   
               if(response!=null){
              	 var obj = JSON.parse(JSON.stringify(response)); 
              	 var status = obj.msg;
              	 var d = new Date(obj.validFrom);
              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';
              	 element = element +
              	 			'<tr>'+
              	 			'<td>'+status+'</td>'+
              	 			'<td>'+d+'</td>'+
              	 			'<tr>';
              	 $('#showResult').append(element);
               }  
               
               else
            	   {
            	  	alert("data cannot be fetched");
            	   }
             },
	    
  			error: function(e){  
    					alert('Error121212: ' + e);  
    					alert(e.toString());
  			}				
    
	  });//ajax call ends
}

function nextOperator(){
	var dateString = document.getElementById('from2').value;
	var date = new Date(dateString).getTime();
	//alert("im u next");
	$.cookie("date1",date);
	$('#showResult').empty();
	$.ajax({
		    type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/Varun_operator",
	        contentType:"application/json",
	        datatype:"json",
          
          success: function(response){   
              if(response!=null){
             	 var obj = JSON.parse(JSON.stringify(response)); 
             	 var status = obj.msg;
             	 var d = new Date(obj.validFrom);
             	 element = '<tr>'+
             	 			'<th>Status</th>'+
             	 			'<th>PostedOn</th></tr>';
             	 element = element +
             	 			'<tr>'+
             	 			'<td>'+status+'</td>'+
             	 			'<td>'+d+'</td>'+
             	 			'<tr>';
             	 $('#showResult').append(element);
              }  
              
              else
           	   {
           	  	alert("data cannot be fetched");
           	   }
            },
	    
 			error: function(e){  
   					alert('Error121212: ' + e);  
   					//alert(e.toString());
 			}				
   
	  });//ajax call ends
}

function previous_Day_Operator(){
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/previous_Day_operator",
	        contentType:"text/plain",
 	        
           
           success: function(response){   
               if(response!=null){
              	 var obj = JSON.parse(JSON.stringify(response)); 
              	 var status = obj.msg;
              	 var d = new Date(obj.validFrom);
              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';
              	 element = element +
              	 			'<tr>'+
              	 			'<td>'+status+'</td>'+
              	 			'<td>'+d+'</td>'+
              	 			'<tr>';
              	 $('#showResult').append(element);
               }  
               
               else
            	   {
            	  	alert("data cannot be fetched");
            	   }
             },
	    
  			error: function(e){  
    					alert('Error121212: ' + e);  
    					alert(e.toString());
  			}				
    
	  });//ajax call ends
}

function previous_Hour_Operator(){
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/previous_Hour_operator",
	        contentType:"text/plain",
 	        
           
           success: function(response){   
               if(response!=null){
              	 var obj = JSON.parse(JSON.stringify(response)); 
              	 var status = obj.msg;
              	 var d = new Date(obj.validFrom);
              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';
              	 element = element +
              	 			'<tr>'+
              	 			'<td>'+status+'</td>'+
              	 			'<td>'+d+'</td>'+
              	 			'<tr>';
              	 $('#showResult').append(element);
               }  
               
               else
            	   {
            	  	alert("data cannot be fetched");
            	   }
             },
	    
  			error: function(e){  
    					alert('Error121212: ' + e);  
    					alert(e.toString());
  			}				
    
	  });//ajax call ends
}

function previous_Month_Operator(){
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/previous_Month_operator",
	        contentType:"text/plain", 	        
           
           success: function(response){   
               if(response!=null){
              	 var obj = JSON.parse(JSON.stringify(response)); 
              	 var status = obj.msg;
              	 var d = new Date(obj.validFrom);
              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';
              	 element = element +
              	 			'<tr>'+
              	 			'<td>'+status+'</td>'+
              	 			'<td>'+d+'</td>'+
              	 			'<tr>';
              	 $('#showResult').append(element);
               }  
               
               else
            	   {
            	  	alert("data cannot be fetched");
            	   }
             },
	    
  			error: function(e){  
    					alert('Error121212: ' + e);  
    					alert(e.toString());
  			}				
    
	  });//ajax call ends
}


