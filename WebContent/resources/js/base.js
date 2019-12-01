/**
 * fix footer
 */
$('document').ready(function() {
	if ($('body').height() < $(window).height()) {
		$('footer').addClass('fixed-bottom');
	} else {	
		$('footer').removeClass('fixed-bottom');
	}
	window.onresize = function(event) {
		if ($('document').height() < $(window).height()) {
			$('footer').addClass('fixed-bottom');
		} else {
			$('footer').removeClass('fixed-bottom');
		}
	};
})



$(window).resize(function() {
	if ($('document').height() < $(window).height()) {
		$('footer').addClass('fixed-bottom');
	} else {
		$('footer').removeClass('fixed-bottom');
	}
});