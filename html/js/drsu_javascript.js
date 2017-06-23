'use strict';


// AJAX HTTP REQUEST ***********************************************************
// Sends HTTP request from str to php_action php and displays result 
// in #resultId HTML element.
// asynchronous - async=true 
// synchronous  - async=false (deprecated)
//
// Ex.
//
// <button onclick="httpRequestResult('filter.php', '?request=' + this.value, 'httpRequestResult');">Delete</button>
// <table><tr><td> <span id="httpRequestResult"></span> </td></tr></table>
//
function httpRequestResult( php_action, str, resultId, async){
  console.log(php_action + "-" + str + "-" + resultId);
  if (str.length==0) { 
    document.getElementById(resultId).innerHTML="";
    return;
  } else {
    var xmlhttp=new XMLHttpRequest();
    xmlhttp.onreadystatechange=function() {
        if (xmlhttp.readyState==4 && xmlhttp.status==200) {
			var resultElement = document.getElementById(resultId);
			if (resultElement != null){
				resultElement.innerHTML=xmlhttp.responseText;
			} else {
				console.log("No result element for this request!" + this)
			}
        }
    }
    xmlhttp.open("GET", php_action + str, async);
    xmlhttp.send();
	
  }    
}
// *****************************************************************************



/***** MODULE: WEB WORKERS API *****/
var ww = (function(){
	
	var resultsWorker;
	var resultsWorkerScript = "js/resultsUpdateWorker.js";

	var publicAPI = {
		
	  startResultsWorker: function(){
		if(typeof(Worker) !== "undefined") {
			if(typeof(resultsWorker) == "undefined") {
				resultsWorker = new Worker(resultsWorkerScript);

			}
			resultsWorker.onmessage = function(event) {
				ajaxApi.ajaxJQ(resultFilters.getFiltersRequestUrl(), 'content');
			};
		} else {
			alert("Live update does not work for this browser !");
		}
	  },

      stopResultsWorker: function(){ 
		if ( typeof(resultsWorker) !== "undefined" ){
			resultsWorker.terminate();
			resultsWorker = undefined;
		}
	  },
	  
	  getResultsWorkerScript: function(){return resultsWorkerScript;}
	}
	
	return publicAPI
})();




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



/***** Module: JENKINS *****/
var jenkins = (function(){
  var wattUrl = "http://10.56.1.57";

  var publicAPI = {
    openJenkinsJobWindow: function(jobName){
		window.open(wattUrl + ':1111/job/' + jobName.trim());
	},
	  
	openTestDoc: function(object){
		var jobName = $(object).parents('tr').eq(0).find('td:nth-child(1)').text();
		var projectName = $(object).parents('table').eq(0).find('tr:nth-child(1) th').text().trim().toLowerCase();
		window.open(wattUrl + '/docs/' + projectName + "/" + jobName.trim() + ".html");
	},
	
	openXml: function(object){
		var jobName = $(object).parents('tr').eq(0).find('td:nth-child(1)').text();
		var projectName = $(object).parents('table').eq(0).find('tr:nth-child(1) th').text().trim().toLowerCase();
		window.open(wattUrl + '/testXML/' + projectName + "/" + jobName.trim() + ".xml");
	},

    htmlReportToDoc: function (){
	  $('.js_errors').hide();
		
	  for (var i=2; i<=3; i++ ) { 
		$('table.suiteResults th:nth-of-type('+ i + ')').hide(); 
		$('table.suiteResults td:nth-of-type('+ i + ')').hide();
	  }

	  for (var i=5; i<=7; i++ ) { 
		$('table.suiteResults th:nth-of-type('+ i + ')').hide(); 
		$('table.suiteResults td:nth-of-type('+ i + ')').hide();
	  }

	  for (var i=3; i<=4; i++ ) { 
		$('table.results th:nth-of-type('+ i + ')').hide(); 
		$('table.results td:nth-of-type('+ i + ')').hide();
	  }

	  //$('table.results th:last-child').click();
	  
	  $('.suiteResults').css("width", "20%");
	  
	  $('head title').text($('table.suiteResults td:first-of-type').text());
	  

	  $('#showDetails').click();

	}
  }
	
  return publicAPI;
})();	
  




/***** Module: MENU *****/
var menu = (function(){
	
	
	var publicAPI = {
		
		storeCurrentMenu: function(menuId){
								localStorage["currentMenu"]=menuId;
		},
							
		loadLastMenu: function(){
								if(!localStorage["firstLoad"]) {
									$('#menu_results').click();
									localStorage["firstLoad"] = true;
									return;
								}
								//console.log('loadLastMenu: ' + localStorage["currentMenu"]);
								$('#' + localStorage["currentMenu"]).click();
		},
		
		mainMenuClick: function(){
			$('a').removeClass('currentMenu');
			$(this).addClass('currentMenu');
			menu.setup();
			
			// highlight current selected menu
			$(this).css('background-color','rgba(255, 255, 255, 0.2)');
			$('a:not(#' + this.id + ')').css('background-color','rgba(255, 255, 255, 0)');
			
			publicAPI.storeCurrentMenu(this.id);
			
			
			$('#content').html("");
			switch (this.id){
				case "menu_results":
					api.show('resultsFilters');
					ajaxApi.ajaxJQ(resultFilters.getFiltersRequestUrl(), 'content');
					ajaxApi.ajaxJQ(resultFilters.getFiltersRequestCounterUrl(), 'resultsCounter');
					
					console.log('START web worker: ' + ww.getResultsWorkerScript());
					ww.startResultsWorker();
					break;
				
				case "menu_modules":
					ajaxApi.ajaxJQ('testDocsToTable.php', 'content');
					setModulesCounter(500);
					
					break;
					
				case "menu_download":
					$('#content').html('');
					api.jsonToTable('download.json', '#content', 'download');
					break;
					
				case "menu_jenkins":
					ajaxApi.ajaxJQ('jenkins.txt', 'content');
					break;
			}
			
			if (this.id != "menu_results"){
				api.hide('resultsFilters');
				console.log('STOP web worker: ' + ww.getResultsWorkerScript());
				ww.stopResultsWorker()
			}
		},
			
		setup: function(){
				$('nav a[id]:not(.currentMenu)').on('mouseover', function(){
					if (this.id != localStorage["currentMenu"]) {
						$(this).css('background-color', 'rgba(255,255,255,0.1)');
					}
				});
				
				$('nav a[id]:not(.currentMenu)').on('mouseout', function(){ 
					if (this.id != localStorage["currentMenu"]) {
						$(this).css('background-color', 'rgba(255,255,255,0)');
					}
				});
				
				var filters  = $('#resultsFilters input');
				for (let i=0; i<5; i++){
					$(filters[i]).on('focus', function(){
						resultFilters.removeFilterInfo.call(this);
						resultFilters.addFilterInfo.call(this)
					});
					$(filters[i]).on('focusout', function(){resultFilters.removeFilterInfo.call(this)});
				}
		}
			
	}
	
	return publicAPI;
})();




/***** MODULE: FILTERS *****/
var resultFilters = (function(){
	
	
	$(window).resize(function(){
		resultFilters.setFilterInfoPosition();
	})
	
	
	function restoreValues(ids){
		for (var i=0; i<ids.length; i++){
			var value = localStorage[ids[i]];
			if (value!=undefined){
				api.setValue(ids[i], value);
			}
		}
	}
	

	var publicAPI = {
		
		getFiltersRequestUrl: function(){
								return 'getTestResults.php?suiteName=' + api.getValue('suiteNameFilter')
									+ '&testServer=' + api.getValue('testServerFilter')
									+ '&user=' + api.getValue('userFilter')
									+ '&result=' + api.getValue('resultFilter')
                                    + '&resultsLimit=' + api.getValue('resultsLimit');
		},
							
		restoreFilters: function(){
							restoreValues(['userFilter','suiteNameFilter','resultFilter','testServerFilter', 'resultsLimit']);
		},
		
		getFiltersRequestCounterUrl: function(){
							return 'getTestResultsCounter.php?suiteName=' + api.getValue('suiteNameFilter')
								+ '&testServer=' + api.getValue('testServerFilter')
								+ '&user=' + api.getValue('userFilter')
								+ '&result=' + api.getValue('resultFilter');
		},
		
		// apply all filters
		filter: function(){
            console.log("Stored: " + this.id + "=" + this.value);
			localStorage[this.id]= this.value;
			ajaxApi.ajaxJQ(resultFilters.getFiltersRequestUrl(),'content');
			ajaxApi.ajaxJQ(resultFilters.getFiltersRequestCounterUrl(), 'resultsCounter');
		},

		resetAll: function(){
                  $('#resultsFilters input').val("");
				  localStorage.clear();
				  $('#resultsLimit').val(15);
                  localStorage["resultsLimit"] = "15";
                  $('#resultsFilters input').eq(1).keyup(); 
        },
	      
		addFilterInfo: function(){
			var d = document.createElement('div');
	
			$(d).text($(this).attr('title'));
			$(d).css({
				'position':'absolute', 
				'width':$(this).parent().width(),
				'height':$(this).parent().height(),
				'margin' :'0 0',
				'padding': '0px'
			});
			$(d).addClass('filterInfo');
			
			var defaultOffset = 9;
			if( $(window).width() < 550 ){
				defaultOffset = -10;
			}
			
			$(d).offset(
				{top: ($(this).parent().offset().top) - ($(this).parent().height()) + defaultOffset, 
				left: $(this).parent().offset().left}
			);
			$(d).appendTo($(this).parent());
			
		},
		
		removeFilterInfo: function (){
			$(this).parent().find('div.filterInfo').remove();
		},
		
		setFilterInfoPosition: function(){
			setTimeout(function(){
			var parent = $('#resultsFilters td div.filterInfo').parent();
			$('#resultsFilters td div.filterInfo').remove();
			parent.find('input').focus()
			},
			5);
		}

		  
        /*
        filterOnEvent: function(event){
          $('#resultsFilters input').on(event, function(){ resultFilters.filter(this)} );
        }
        */	
	}
	
	return publicAPI;
})();



/***** MODULE: AJAX *****/
var ajaxApi = (function(){
		$.ajaxSetup({
					// before jQuery send, the request we will push it to our array
					beforeSend: function(jqXHR) { 
						ajaxApi.xhrPool.push(jqXHR);
						//console.log("added to xhrPool: " + jqXHR);
					},
					
					// when some of the requests completed it will splice from the array
					complete: function(jqXHR) { 
						var index = ajaxApi.xhrPool.indexOf(jqXHR);
						if (index > -1) {
						ajaxApi.xhrPool.splice(index, 1);
						//console.log("removed from xhrPool: " + jqXHR);
						}
					}
		});
		
	
		var publicAPI = {
			
			xhrPool: [], // array of AJAX handlers
				
			/*
			e.g.
			ajaxJQ('getFileConteant.php?file=jenkins.txt', 'content');"
			*/
			ajaxJQ: function(url, resultId){
				// Assign handlers immediately after making the request,
				// and remember the jqXHR object for this request
				console.log("ajaxJQ: " + url);
				var jqxhr = $.ajax( url )
					.done(function(result) {
							if($('#' + resultId) != null ){
								$('#'+resultId).html(result);
							}
					})
					.fail(function(result) {
						console.log( "Request: " + this.url + "\n" 
							+ "Error: " + result);
					})
					.always(function() {
						//alert( "complete" );
					});
			},
			
			
			abortAll: function(){ 
				console.log("Abort all active ajax requests from pool: " + ajaxApi.xhrPool);
				ajaxApi.xhrPool.forEach(function(jqXHR) { 
				    console.log("Abort: " + jqXHR);
					jqXHR.abort();
				});
				ajaxApi.xhrPool.length = 0;
			}

		}
		
		return publicAPI;
})();



// Test modules docs
function updateTestDocs(){
  $("#testCasesDocs").html(
    '&nbsp;&nbsp;<img src="images/spinner_16x16_gray.gif" alt="" />' +
    '&nbsp;&nbsp;<span>Updating modules documentation from jar ...</span>');
  ajaxApi.ajaxJQ("getTestDocs.php","testCasesDocs");
}





function filterModules(value){
	$('#testCasesDocs tr').show();
	$('#testCasesDocs td:nth-child(1)').not($('#testCasesDocs td:contains("' + value + '")')).parents('tr').hide();
	
	setModulesCounter(100);
} 



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


function validateNrOfResults(){
	if (parseInt(this.value) > 500) {
		alert("The maximum number of displayed results is 500!")
		this.value=500;
	}
}

function setModulesCounter(timeout){
	
	setTimeout(function(){
	  var counter = $('.testCasesDocs tr:visible').size();
	  //console.log("modules counter: " + counter);
	  $('#modulesCounter').html( counter + ' &nbsp;modules');
	  
	}, 
	timeout);
} 



	



