$(function() { 

// When a link is clicked
$("a.tab").click(function () {
	// switch all tabs off
	$(".active").removeClass("active");
	// switch this tab on
	$(this).addClass("active");
	// slide all content up
	$(".content").slideUp(150);
	// slide this content up
	var content_show = $(this).attr("title");
	$("#"+content_show).slideDown(150);
});

$('#addrSearch').on( "click", function() {
 $("#resultDIV").slideUp('fast');
 $('#addrSearch').removeClass("awesome").addClass("awesomeDisabled");
 $('#addrSearch').html("<img src='/g/spinner.gif'> working");
 $('#addrSearch').fadeTo('fast','0.4');
 $('#addrInput').fadeTo('fast','0.4');
 
 $.get("/getDividends.php?addr="+$('#addrInput').val(), function(data) {
  $("#resultDIV").html(data).slideDown('fast');
  $('#addrSearch').removeClass("awesomeDisabled").addClass("awesome");
  $('#addrSearch').html("Search!");
  $('#addrSearch').fadeTo('fast','1');
  $('#addrInput').fadeTo('fast','1');
 });
});

});
