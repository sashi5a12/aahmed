
$(document).ready(function(){
	var FREQ = 10000 ;
	var refreshFlag=true;
	function showFrequency(){
		$("#freq").html( "Page refreshes every " + FREQ/1000 + " second(s).");
	}
	function getTime(){
        var a_p = "";
        var d = new Date();
        var curr_hour = d.getHours();
        
        (curr_hour < 12) ? a_p = "AM" : a_p = "PM";
        (curr_hour == 0) ? curr_hour = 12 : curr_hour = curr_hour;
        (curr_hour > 12) ? curr_hour = curr_hour - 12 : curr_hour = curr_hour;
        
        var curr_min = d.getMinutes().toString();
        var curr_sec = d.getSeconds().toString();
        
        if (curr_min.length == 1) { curr_min = "0" + curr_min; }
        if (curr_sec.length == 1) { curr_sec = "0" + curr_sec; } 
        
        $('#updatedTime').html(curr_hour + ":" + curr_min + ":" + curr_sec + " " + a_p );
    }
	
	function getRacersJSON(){
		$.getJSON("/jQuery/runnerServlet?action=getRunners",
			function(json){
				if(json.runners.length>0){
					$('#finishers_m').empty();
					$('#finishers_f').empty();
					$('#finishers_all').empty();
					$.each(json.runners, function (){
						var firstName=this['firstName'];
						var lastName=this['lastName'];
						var time=this['time'];
						var info="<li> Name: "+firstName+" "+lastName+" Time: "+time+"</li>";
						var gender=this['gender'];
						if(gender=='m'){
							$('#finishers_m').append(info);
						}
						else if(gender=='f'){
							$('#finishers_f').append(info);
						}
						else {}
						$('#finishers_all').append(info);
					});
				}
			}
		);
		getTime();
	}
	function refresh(){
		if(refreshFlag){
			setTimeout(function (){
				getRacersJSON();
				refresh();
			},FREQ);
		}
	}
	$('#btnStart').click(function(){
		refreshFlag=true;
		refresh();
		showFrequency();
	});
	$('#btnStop').click(function(){
		refreshFlag=false;
		$("#freq").html( "Updates paused." );
	});
	getRacersJSON();
	refresh();
	showFrequency();
	
	$('#btnSave').click(function (){
		var data = $("#addRunner :input").serializeArray();
		$.post(
				$('#addRunner').attr('action'),
				data,
				function(json){
					if(json.status=='success'){
						alert(json.message);
						clearInput();
					}
					else {
						alert('There was some issue in saving the data.');
					}
				},
				"json"
			);
	});
	function clearInput(){
		$('#addRunner :input').not("input[type='hidden']").each(function (){
			$(this).val('');
		});
	}
	$('#addRunner, #btnSave').submit(function (){
		return false;
	});
});
