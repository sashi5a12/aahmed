
var IMAGE_TYPES_1= ['jpeg', 'jpg', 'png'];
var IMAGE_TYPES_2=['ai', 'eps', 'png', 'jpeg', 'jpg'];
var VIDEO_TYPES_1=['mp4'];
var DOC_TYPES_1=['doc','docx','txt'];
var PRESENTATION_TYPES=['ppt','pptx'];
var SPREAD_SHEET_TYPES=['xls','xlsx'];
var DOC_TYPES_2=['pdf','doc','docx'];
var DOC_TYPES_3=['pdf'];

var SIZE_1MB= 1048576; // 1MB = 1024 * 1024 bytes
var SIZE_10MB =10485760; // 10MB = 10240 * 1024 bytes
var SIZE_100MB =104857600; // 10MB = 10240 * 1024 bytes

var UPLOAD_BUTTON='<div class="upload-File">&nbsp;&nbsp;<span><img src="'+STATICS_URL+'/images/uploadFile-icon.png" width="20" height="20" alt="" /></span>Upload<div class="clearboth"></div></div>';
var UPLOAD_TEMPLATE='<div class="qq-uploader">' +
						'<pre class="qq-upload-drop-area"><span>{dragZoneText}</span></pre>' +
						'<div class="qq-upload-button btn btn-success" style="width: auto;">{uploadButtonText}</div>' +
						'<span class="qq-drop-processing"><span>{dropProcessingText}</span><span class="qq-drop-processing-spinner"></span></span>' +
						'<ul class="qq-upload-list" style="margin-top: 10px; text-align: center;"></ul>' +
					'</div>';
var UPLOAD_URL="../../json/upload.do";
var DELETE_URL="../../json/delete.do";
var DOWNLOAD_URL='../../secure/process/download.do';

var ERROR_IMAGE_TYPES_1='Invalid file extension. Valid extension(s): jpeg, jpg, png';
var ERROR_IMAGE_TYPES_2='Invalid file extension. Valid extension(s): ai, eps, jpeg, jpg, png';
var ERROR_VIDEO_TYPES_1='Invalid file extension. Valid extension(s): mp4';
var ERROR_DOC_TYPES_1='Invalid file extension. Valid extension(s): doc, docx, txt';
var ERROR_PRESENTATION_TYPES='Invalid file extension. Valid extension(s): ppt, pptx';
var ERROR_SPREAD_SHEET_TYPES='Invalid file extension. Valid extension(s): xls, xlsx';
var ERROR_DOC_TYPES_2='Invalid file extension. Valid extension(s): pdf, doc, docx';
var ERROR_DOC_TYPES_3='Invalid file extension. Valid extension(s): pdf';

var ERROR_1M_SIZE='File is too large, maximum file size is 1.0MB';
var ERROR_10MB_SIZE='File is too large, maximum file size is 10MB';
var ERROR_EMPTY_SIZE='File is empty, please select another file.';

function responseHandling(event, id, fileName, responseJSON){
	if (responseJSON.success) {
		//$('#'+responseJSON.fileType+'\\.mediaId').val(responseJSON.mediaId);
		//console.log('Response Handling mediaId: value for '+'#'+responseJSON.fileType+'.mediaId : '+$('#'+responseJSON.fileType+'\\.mediaId').val());
		
		var smallFileName=fileName;
		if (smallFileName.length>15){
			smallFileName=fileName.substring(0,14)+"...";
		}
		
		document.getElementById(responseJSON.fileType+".mediaId").value=""+responseJSON.mediaId;
		document.getElementById(responseJSON.fileType+".fileName").value=""+smallFileName;
		document.getElementById(responseJSON.fileType+".fileType").value=""+responseJSON.fileType;
		document.getElementById(responseJSON.fileType+".fileFullName").value=""+fileName;
		
		//console.log('Response Handling mediaId: value for '+'#'+responseJSON.fileType+'.mediaId : '+document.getElementById(responseJSON.fileType+".mediaId").value);
		//console.log('Response Handling fileName: value for '+'#'+responseJSON.fileType+'.fileName : '+document.getElementById(responseJSON.fileType+".fileName").value);
		$('#upload_'+responseJSON.fileType+' div.qq-uploader').hide();
		$('#upload_'+responseJSON.fileType+' ul.qq-upload-list').hide();
		
		$('#uploaded_'+responseJSON.fileType+' a:eq(0)').html(smallFileName);
		$('#uploaded_'+responseJSON.fileType+' a:eq(0)').attr('title',fileName);
		$('#uploaded_'+responseJSON.fileType+' a:eq(0)').attr('class',responseJSON.mediaId);
		$('#uploaded_'+responseJSON.fileType+' a:eq(1)').attr('rel',responseJSON.fileType).attr('class',responseJSON.mediaId).attr('name', fileName);
		$('#uploaded_'+responseJSON.fileType).show();
	}
	else {
		alert(responseJSON.error + " "+ fileName);
		$('#'+responseJSON.fileType + ' ul.qq-upload-list').hide();
	}
}


$(document).ready(function() {
	$("#delete_out_front_view, #delete_out_angeled_view, #delete_out_another_object, #delete_out_other_view1, " +
			"#delete_out_other_view2, #delete_out_other_view3, #delete_out_other_view4, #delete_in_front_view, " +
			"#delete_in_angeled_view, #delete_in_another_object, #delete_screen_shot1, #delete_screen_shot2, " +
			"#delete_screen_shot3, #delete_lifestyle_image1, #delete_lifestyle_image2, #delete_lifestyle_image3, " +
			"#delete_phone_splash_screen, #delete_tablet_splash_screen, #delete_application_icon, #delete_phone_in_app_screen, " +
			"#delete_tablet_in_app_screen, #delete_product_video, #delete_phone_app_video, #delete_tablet_app_video," +
			"#delete_product_copy_doc, #delete_app_copy_doc, #delete_launch_presentation_video, "+
			"#delete_testing_spreadsheet, #delete_product_label, #delete_sustainability_disclosure, #delete_packaging_label, #delete_pdf_sample_product").click(function(e) {
			
        e.preventDefault();
        var mediaId=$(this).attr('class');
        var fileType=$(this).attr('rel');
        var fileName=$(this).attr('name');
        //console.log("mediaId: "+mediaId);
        //console.log("fileType: "+fileType);
        //console.log("fileName: "+fileName);
       	if (confirm('Are you sure you want to delete this file?')) { 
			$.ajaxSetup({ cache: false });
			var url=DELETE_URL+"?mediaId="+mediaId+"&productId="+$('#id').val()+"&fileName="+fileName;
        	$.getJSON(url, function(data){
        		if (data.success){
            		alert("File deleted successfully.");
            		//$('#'+fileType+'\\.mediaId').val(null);
            		document.getElementById(fileType+".mediaId").value="";
            		document.getElementById(fileType+".fileName").value="";
            		//console.log('Delete value for '+'#'+fileType+'.mediaId : ' + document.getElementById(fileType+".mediaId").value);
            		$("#uploaded_"+fileType).hide();
            		$('#upload_'+fileType).show();
					$('#upload_'+fileType+' div.qq-uploader').show();
					$('#upload_'+fileType+' ul.qq-upload-list li:first > span.qq-upload-file').hide();
					$('#upload_'+fileType+' ul.qq-upload-list li:first > span.qq-upload-file').html("");
					$('#upload_'+fileType+' ul.qq-upload-list li:first > span.qq-upload-size').hide();
					$('#upload_'+fileType+' ul.qq-upload-list li:first > span.qq-upload-size').html("");					
					$('#upload_'+fileType+' ul.qq-upload-list').show();
            	}
            	else {
            		alert(data.error);
            	}
        	}).error(function(data){         	
            	alert("Error occurred while deleting the file.");
        	});		
		}
        return false;
    });

	$("#download_out_front_view, #download_out_angeled_view, #download_out_another_object, #download_out_other_view1, " +
			"#download_out_other_view2, #download_out_other_view3, #download_out_other_view4, #download_in_front_view, " +
			"#download_in_angeled_view, #download_in_another_object, #download_screen_shot1, #download_screen_shot2, " +
			"#download_screen_shot3, #download_lifestyle_image1, #download_lifestyle_image2, #download_lifestyle_image3, " +
			"#download_phone_splash_screen, #download_tablet_splash_screen, #download_application_icon, #download_phone_in_app_screen, " +
			"#download_tablet_in_app_screen, #download_product_video, #download_phone_app_video, #download_tablet_app_video, " +
			"#download_product_copy_doc, #download_app_copy_doc, #download_launch_presentation_video, "+
			"#download_testing_spreadsheet, #download_product_label, #download_sustainability_disclosure, #download_packaging_label, #download_pdf_sample_product").click(function(e) {
				
        //e.preventDefault();		
        var mediaId=$(this).attr('class');
        //console.log("mediaId: "+mediaId);
        var url=DOWNLOAD_URL+"?mediaId="+mediaId+"&productId="+$('#id').val();
        $(this).attr('href',url);		
        //e.trigger();
        //return false;
    });
	//FOR Product Out of Packaging - FRONT VIEW
	$('#upload_out_front_view').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'out_front_view',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#out_front_view').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});

	//FOR Product Out of Packaging - Angeled View
	$('#upload_out_angeled_view').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'out_angeled_view',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#out_angeled_view').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});

	//FOR Product Out of Packaging - View with another object
	$('#upload_out_another_object').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'out_another_object',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#out_another_object').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});
	
	
	//FOR Product Out of Packaging - Other View 1
	/*$('#upload_out_other_view1').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'out_other_view1',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}		
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	*/
	
	//FOR Product Out of Packaging - Other View 2
	/*$('#upload_out_other_view2').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'out_other_view2',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}		
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});*/		
	
	//FOR Product Out of Packaging - Other View 3
	/*$('#upload_out_other_view3').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'out_other_view3',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}		
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});*/
	
	//FOR Product Out of Packaging - Other View 4
	/*$('#upload_out_other_view4').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'out_other_view4',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}		
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	*/	
	
	//FOR Product in Packaging - Front View
	$('#upload_in_front_view').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'in_front_view',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#in_front_view').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});			
	
	//FOR Product in Packaging - Angeled View
	$('#upload_in_angeled_view').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'in_angeled_view',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#in_angeled_view').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
	//FOR Product in Packaging - View with another object
	$('#upload_in_another_object').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'in_another_object',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#in_another_object').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});
	
	//Screenshot of Each Application Required - Screen Shot1
	$('#upload_screen_shot1').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'screen_shot1',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#screen_shot1').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
	//Screenshot of Each Application Required - Screen Shot2
	$('#upload_screen_shot2').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'screen_shot2',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#screen_shot2').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});			
	
	//Screenshot of Each Application Required - Screen Shot3
	$('#upload_screen_shot3').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'screen_shot3',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#screen_shot3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
	//Lifestyle Images - 1
	$('#upload_lifestyle_image1').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'lifestyle_image1',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image1').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});			
	
	//Lifestyle Images - 2
	$('#upload_lifestyle_image2').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'lifestyle_image2',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image2').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	
	
	//Lifestyle Images - 3
	$('#upload_lifestyle_image3').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'lifestyle_image3',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	
	
	//Application Images Splash Screen For Phone
	$('#upload_phone_splash_screen').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'phone_splash_screen',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_2,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_2,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	
	
	//Application Images Splash Screen for Tablet
	$('#upload_tablet_splash_screen').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'tablet_splash_screen',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_2,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_2,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	

	//Application Images Application Icon
	$('#upload_application_icon').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'application_icon',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_2,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_2,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	
	
	//Application Images In-App Screen for Phone
	$('#upload_phone_in_app_screen').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'phone_in_app_screen',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_2,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_2,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	

	//Application Images In-App Screen for Tablet
	$('#upload_tablet_in_app_screen').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'tablet_in_app_screen',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_2,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_2,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	
	
	//Product Video
	$('#upload_product_video').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'product_video',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: VIDEO_TYPES_1,
			sizeLimit: SIZE_10MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_VIDEO_TYPES_1,
			sizeError: ERROR_10MB_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	
	
	//Application Video for Phone
	$('#upload_phone_app_video').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'phone_app_video',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: VIDEO_TYPES_1,
			sizeLimit: SIZE_10MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_VIDEO_TYPES_1,
			sizeError: ERROR_10MB_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});	

	//Application Video for Tablet
	$('#upload_tablet_app_video').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'tablet_app_video',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: VIDEO_TYPES_1,
			sizeLimit: SIZE_10MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_VIDEO_TYPES_1,
			sizeError: ERROR_10MB_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
	//Product Copy
	$('#upload_product_copy_doc').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'product_copy_doc',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: DOC_TYPES_1,
			sizeLimit: SIZE_10MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_DOC_TYPES_1,
			sizeError: ERROR_10MB_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
	//Product Copy
	$('#upload_app_copy_doc').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'app_copy_doc',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: DOC_TYPES_1,
			sizeLimit: SIZE_10MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_DOC_TYPES_1,
			sizeError: ERROR_10MB_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
	//Launch Presentation
	$('#upload_launch_presentation_video').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'launch_presentation_video',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 1
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: PRESENTATION_TYPES,
			sizeLimit: SIZE_10MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_PRESENTATION_TYPES,
			sizeError: ERROR_10MB_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
	
	//Product Info Upload - Testing Spreadsheet Upload
	$('#upload_testing_spreadsheet').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'testing_spreadsheet',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 2
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: SPREAD_SHEET_TYPES,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_SPREAD_SHEET_TYPES,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
		/*showMessage: function(message) {
			$('#lifestyle_image3').append('<div class="upload-error">' + message + '</div>');
		}*/
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});
	
	//Product Info Upload - Sustainability Disclosure
	$('#upload_sustainability_disclosure').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'sustainability_disclosure',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 2
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: SPREAD_SHEET_TYPES,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_SPREAD_SHEET_TYPES,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});			

	//Product Info Upload - Image of Product Label
	$('#upload_product_label').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'product_label',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 2
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
	//Product Info Upload - Image of Packaging label
	$('#upload_packaging_label').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'packaging_label',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 2
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: IMAGE_TYPES_1,
			sizeLimit: SIZE_1MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_IMAGE_TYPES_1,
			sizeError: ERROR_1M_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
	//Upload PDF of Sample Product & Final Images - Upload PDF of Sample Product
	$('#upload_pdf_sample_product').fineUploader({
		request : {
			endpoint : UPLOAD_URL,
			inputName : 'pdf_sample_product',
			params: {
				product_id: function(){return $('#id').val();},
				tab : 2
			}
		}, 
		multiple: false,
		validation: {
			allowedExtensions: DOC_TYPES_3,
			sizeLimit: SIZE_10MB
			//itemLimit: 1
		},
		text: {
			uploadButton: UPLOAD_BUTTON
		},
		template: UPLOAD_TEMPLATE,
		classes: {
			success: 'alert alert-success',
			fail: 'alert alert-error'
		},	
		deleteFile:{
			enabled:false,
			endpoint: DELETE_URL
		},
		messages: {
			typeError: ERROR_DOC_TYPES_3,
			sizeError: ERROR_10MB_SIZE,
			emptyError: ERROR_EMPTY_SIZE
		}
	}).on('complete', function(event, id, fileName, responseJSON) {
		responseHandling(event, id, fileName, responseJSON);
	});		
	
});