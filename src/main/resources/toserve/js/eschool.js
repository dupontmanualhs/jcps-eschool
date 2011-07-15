// eschool.js
// This file contains all of the javascript code to go along with JQuery and JQuery-UI

// This is for the site navigation tabs
jQuery(function(){
			jQuery('ul.sf-menu').superfish();
		});

// This is the code for the left tree nav
$(function () {
    $.jstree._themes = "/classpath/js/themes/";
	$("#navTree").jstree({
        "themes" : {
            "theme" : "classic",
            "dots" : false,
            "icons" : false,
                },
        "plugins" : [ "themes", "html_data" ]
	});
});

