function callOperators(){
	
	var operator_number=$('#sel1 :selected').val();	
	$('#calenderPickerPreviousOperator').hide();
	$('#calenderPickerNextOperator').hide();
	$('#calenderPickerEvolutionColumnOperator').hide();
	$('#calenderPickerNextDayColumnOperator').hide();
	$('#calenderPickerNextHourColumnOperator').hide();	
	$('#calenderPickerNextMonthColumnOperator').hide();	
	$('#getStatus').hide();	
	
	
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
    	$('#calenderPickerNextDayColumnOperator').show();
        break;
    case  '52':
    	$('#calenderPickerNextHourColumnOperator').show();
        break;
    case  '53':    	
    	$('#calenderPickerNextMonthColumnOperator').show();
        break;
    case  '6':
    	    evolution_History_Operator();  //it gives all data till now
        break;
    case  '7':
    	    $('#calenderPickerEvolutionColumnOperator').show();  //it gives all data till given date
        break;
    case  '81':
    	    firstEvolutionOperator();
        break;
    case  '82':
    	    lastEvolutionOperator();
        break;
    case  '10':
    	$('#getStatus').show();
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


function evolution_History_Operator(){
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/give_evolution_history",
	        contentType:"text/plain",
 	        datatype:"json",
           
           success: function(response){   
               if(response!=null){
              	 var data = JSON.parse(JSON.stringify(response)); 
              	
              	 

              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';

              	for (var i=0; i < data.length; i++){
              		 var status = data[i].msg;
                  	 var d = new Date(data[i].validFrom);
                     	 element = element +
                     	 			'<tr>'+
                     	 			'<td>'+status+'</td>'+
                     	 			'<td>'+d+'</td>'+
                     	 			'<tr>';
              	
              	}
              	 
              	 
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


function evolutionColumnOperator(){
	var dateString = document.getElementById('from3').value;
	var date = new Date(dateString).getTime();
	//alert("im u next");
	$.cookie("date1",date);
	$('#showResult').empty();
	$.ajax({
		    type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/get_evolution_column",
	        contentType:"application/json",
	        datatype:"json",
          
	           success: function(response){   
	               if(response!=null){
	              	 var data = JSON.parse(JSON.stringify(response)); 
	              	 
	              	 element = '<tr>'+
	              	 			'<th>Status</th>'+
	              	 			'<th>PostedOn</th></tr>';

	              	for (var i=0; i < data.length; i++){
	              		 var status = data[i].msg;
	                  	 var d = new Date(data[i].validFrom);
	                     	 element = element +
	                     	 			'<tr>'+
	                     	 			'<td>'+status+'</td>'+
	                     	 			'<td>'+d+'</td>'+
	                     	 			'<tr>';
	              	
	              	}
	              	 
	              	 
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

function firstEvolutionOperator(){
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/get_evolution_first",
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



function lastEvolutionOperator(){
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/get_evolution_last",
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


function next_day_scale_Operator(){
	
	var dateString = document.getElementById('from4').value;
	var date1 = new Date(dateString).getTime();
	$.cookie("date1",date1);
	
	var date2 = new Date(date1+86400000).getTime();
	//alert(date2);
	$.cookie("date2",date2);
	
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/get_next_day_scale",
	        contentType:"text/plain",
 	        datatype:"json",
           
           success: function(response){   
               if(response!=null){
              	 var data = JSON.parse(JSON.stringify(response)); 
              	
              	 

              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';

              	for (var i=0; i < data.length; i++){
              		 var status = data[i].msg;
                  	 var d = new Date(data[i].validFrom);
                     	 element = element +
                     	 			'<tr>'+
                     	 			'<td>'+status+'</td>'+
                     	 			'<td>'+d+'</td>'+
                     	 			'<tr>';
              	
              	}
              	 
              	 
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

function next_hour_scale_Operator(){
	
	var dateString = document.getElementById('from5').value;
	var date1 = new Date(dateString).getTime();
	$.cookie("date1",date1);
	
	var date2 = new Date(date1+3600000).getTime();
	//alert(date2);
	$.cookie("date2",date2);
	
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/get_next_day_scale",
	        contentType:"text/plain",
 	        datatype:"json",
           
           success: function(response){   
               if(response!=null){
              	 var data = JSON.parse(JSON.stringify(response)); 
              	
              	 

              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';

              	for (var i=0; i < data.length; i++){
              		 var status = data[i].msg;
                  	 var d = new Date(data[i].validFrom);
                     	 element = element +
                     	 			'<tr>'+
                     	 			'<td>'+status+'</td>'+
                     	 			'<td>'+d+'</td>'+
                     	 			'<tr>';
              	
              	}
              	 
              	 
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

function next_month_scale_Operator(){
	
	var dateString = document.getElementById('from6').value;
	var date1 = new Date(dateString).getTime();
	$.cookie("date1",date1);
	
	var date2 = new Date(date1+2592000000).getTime();
	$.cookie("date2",date2);
	
	$('#showResult').empty();
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/get_next_day_scale",
	        contentType:"text/plain",
 	        datatype:"json",
           
           success: function(response){   
               if(response!=null){
              	 var data = JSON.parse(JSON.stringify(response)); 
              	
              	 

              	 element = '<tr>'+
              	 			'<th>Status</th>'+
              	 			'<th>PostedOn</th></tr>';

              	for (var i=0; i < data.length; i++){
              		 var status = data[i].msg;
                  	 var d = new Date(data[i].validFrom);
                     	 element = element +
                     	 			'<tr>'+
                     	 			'<td>'+status+'</td>'+
                     	 			'<td>'+d+'</td>'+
                     	 			'<tr>';
              	
              	}
              	 
              	 
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


function get_column_timestamp_operator(){
	
	$('#showResult').empty();
	var status = document.getElementById('status').value;
	//alert(status);
	$.cookie("status",status);

	
	$.ajax({
 		 type: "GET",
	        url:"http://localhost:8080/DM_temporal/webapi/user/get_column_timestamp",
	        contentType:"application/json",
 	        datatype:"json",
           success: function(response){   
               if(response!=null){
              	 var obj = JSON.parse(JSON.stringify(response)); 
             
              	 var validFrom = new Date(obj.validFrom);
              	 var validTo = new Date(obj.validTo);
              	 
              	 if(obj.msg){
              		element = '<tr>'+
      	 			'<th>Status</th>'+
      	 			'<th>validFrom</th>'+
      	 			'<th>validTo</th></tr>';
              		element = element +
      	 			'<tr>'+
      	 			'<td>'+obj.msg+'</td>'+
      	 			'<td>'+validFrom+'</td>'+
      	 			'<td>'+validTo+'</td>'+
      	 			'<tr>';
              		$('#showResult').append(element); 
              	 }
              	 else{
              		
              		 element = '<h2> No result found </h2>';
              		$('#showResult').append(element);
              		
              	 }
              	 
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





