;(function($) {

	$.noty.layouts.inline = {
		name: 'inline',
		options: {},
		container: {
			object: '<ul id="noty_inline_layout_container" />',
			selector: 'ul#noty_inline_layout_container',
			style: function() {
				$(this).css({
					width: '80%',
					height: 'auto',
					margin: 0,
					'text-align': 'center',
					display: 'inline-block',
					padding: 0,
					listStyleType: 'none',
					zIndex: 9999999
				});
			}
		},
		parent: {
			object: '<li />',
			selector: 'li',
			css: {}
		},
		css: {
			display: 'none'
		},
		addClass: ''
	};

})(jQuery);