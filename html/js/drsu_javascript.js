'use strict';





/***** MODULE: GENERIC WEB PAGE API *****/
var api = (function(){
	
	var publicAPI = { 
		
		focusOnEnter: function (e, id){
						var key=e.keyCode || e.which;
						if (key==13){
							setFocusToElement(id);
						}
		},
		
		clickOnEnter: function (e, id){
		  var key=e.keyCode || e.which;
		  if (key==13){
			click(id);    
		  }
		},

		setFocusToElement: function (id){
			document.getElementById(id).focus();
		},
		
		click: function (id){
		  document.getElementById(id).click();
		},

		getValue: function (id){
		  return  document.getElementById(id).value;
		},
		
		setValue: function (id, value){
		  return  document.getElementById(id).value=value;
		},

		increaseSize: function (id, value){
		  var el = document.getElementById(id);
		  el.width -= value;
		},

		hide: function (id){
		  $('#'+id).hide();
		},

		show: function (id){
			$('#'+id).show();
		},

		isDisplayed: function (id){
		  var e = document.getElementById(id);
		  if (e!=null){
			return (e.style.display != 'none');
		  }
		  else {
			return false;
		  }
		},
		
		// evaluate xpath
		// print all founded elements
		// return first element or null if no element was found
		evalXpath: function(xpath){ 
			var elements =  document.evaluate(xpath, 
				  document, 
				  null, 
				  XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, 
				  null
				  );

			console.log("elements found: " + elements.snapshotLength);

			for ( var i=0 ; i < elements .snapshotLength; i++ )
			{    
				console.log( "element "  + i + ": " );
				console.log( elements .snapshotItem(i) );
			}

		        return elements.snapshotItem(0);
		},
		
		jsonToTable: function(jsonFile, cssSelector, tableClass){
			$.getJSON( jsonFile, function( data ) {
					//alert('json');
					var items = [];

					$.each( data, function( key, val ) {
						console.log();
						items.push( '<tr><td></td><td>' 
							+ key + '</td><td>' 
							+ val + '</td><td></td></tr>' );
						
					});
		 
					$( "<table/>", {
						"class": tableClass,
						html: items.join( "" )
					}).appendTo( cssSelector );
			});
		}
		
	}
	
	return publicAPI;
})();		





function resultsPageSetup(){
	// trick to position Details in the center ....modified info svf has long length
	$('#showDetails').parents('th').append((function(){ 
			var newSvg  = $('#showDetails').parent().clone(); 
			newSvg.css('opacity','0'); 
			newSvg.attr('title', ''); 
			return newSvg;
		})()
	);
	
    // succeeded -> green color; failed -> red color
	if ($('#suiteResultsContainer td:nth-child(2) font').text()=="Succeeded") 
	{
		$('#suiteResultsContainer td:nth-child(2)').css('background','rgb(0, 128, 0)');
		$('#suiteResultsContainer td:nth-child(2) font').css('color','rgb(255, 255, 255)');
	}else 
	{
		$('#suiteResultsContainer td:nth-child(2)').css('background','rgb(255, 0, 0)');	
		$('#suiteResultsContainer td:nth-child(2) font').css('color','rgb(255, 255, 255)');
	}
	
	$('.results td:nth-child(1):not(:contains("/"))').parent().children().css({'border-top': '3px solid #0868a1', 'font-weight':'bold'});

        // colapse tescases within test on test row click
        var $testRows = $('.results tr td:first-child:not(:contains("/"))').parent();
		$testRows.click(function(){
			var i = $.trim($(this).find('td:first-child').text().split('/')[0]);
			$('.results td:first-child:contains("'+ i +'/")').parent().slideToggle("fast");
		});
        $testRows.attr('title', 'Collapse/Expand test cases');
        $testRows.find('td').css({'background-color':'#ceecfd', 'cursor':'pointer'});
		$testRows.mouseenter(function(){$(this).children().css('opacity',0.7)});
		$testRows.mouseleave(function(){$(this).children().css('opacity',1)});
		

        // display testCaseDocs on Details column click (only for test cases rows)
        $('table.results tr td:first-child:contains("/")').parent().find('td:last-child').click(
          function(){
            $(this).find('div.testCaseDocs').toggle();
          });
       

       var $showDetails =  $('#showDetails');
	   $showDetails.attr('title', 'Show test cases info');
	   localStorage.setItem("showDetails", "false");
       $showDetails.click(function(){
		   if(localStorage.getItem("showDetails") == "false"){
			if(localStorage.getItem("colapsedAll") == "true"){
				$('#expandTestcases').click();
			}
			$('div.testCaseDocs').show();
			localStorage.setItem("showDetails", "true");
		 } else {
			$('div.testCaseDocs').hide(); 
			localStorage.setItem("showDetails","false");
		 }      
       });
	   
	   
       
       
       var $detailsCell = $('table.results td:last-child');
       $detailsCell.css({
         'line-height':'1.5em'
       });
        
		
       // click + to expand/collapse all testcases
	   localStorage["colapsedAll"] = "true";
	   $('#expandTestcases').click(function(){
		 if(localStorage["colapsedAll"] == "false"){
			$('.results td:first-child:contains("/")').parent().hide();
			localStorage["colapsedAll"] = "true";
		 } else {
			$('.results td:first-child:contains("/")').parent().show(); 
			localStorage["colapsedAll"] = "false";
		 }
       });

}
