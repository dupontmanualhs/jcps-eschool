/**
 * editor_plugin_src.js
 *
 * Math
 */

(function() {
	// Load plugin specific language pack
	//tinymce.PluginManager.requireLangPack('mathematik');

	tinymce.create('tinymce.plugins.Mathematik', {
		/**
		 * Initializes the plugin, this will be executed after the plugin has been created.
		 * This call is done before the editor instance has finished it's initialization so use the onInit event
		 * of the editor instance to intercept that event.
		 *
		 * @param {tinymce.Editor} ed Editor instance that the plugin is initialized in.
		 * @param {string} url Absolute URL to where the plugin is located.
		 */
		init : function(ed, url) {

		    this.editor = ed;
		    var t = this;

			// Register commands
			ed.addCommand('mceSqrt', function(){
			    var str = t._sqrt("Radicand");

			    ed.execCommand('mceInsertContent', false, str);
			});

			ed.addCommand('mceFrac', function(){
			    var str = t._frac("Numerator", "Denominator");

			    ed.execCommand('mceInsertContent', false, str);
			});

			ed.addCommand('mceMathPopup', function(){
			    ed.windowManager.open({
			        file : url + '/insertMenu.html',
			        width : 500,
			        height : 450,
			        inline : 1
			    }, {
			        plugin_url : url
			    });
			});

			ed.addCommand('mceSum', function(){
			    ed.windowManager.open({
			        file: url + '/sumMenu.html',
			        width : 500,
			        height : 250,
			        inline : 1
			    }, {
			        plugin_url : url
			    });
			});

			// Register buttons
			ed.addButton('squareroot', {
				title : 'Root',
				cmd : 'mceSqrt',
				image : url + '/img/sqrt.png'
			});

            ed.addButton('fraction', {
                title : 'Fraction',
                cmd : 'mceFrac',
                image : url + '/img/frac.png'
            });

            ed.addButton('mathpopup', {
                title : 'Mathematik Insert Pane',
                cmd : 'mceMathPopup',
                image : url + '/img/sum.png'
            });


            ed.addButton('sumpopup', {
                title : 'Sum Insert Menu',
                cmd : 'mceSum',
                image : url + '/img/sum.png'
            });
            //ed.onNodeChange.add(function(ed, cm, n){
            //   cm.setActive('squareroot', true);
            //});
		},

		/**
		 * Returns information about the plugin as a name/value array.
		 * The current keys are longname, author, authorurl, infourl and version.
		 *
		 * @return {Object} Name/value array containing information about the plugin.
		 */
		getInfo : function() {
			return {
				longname : 'Mathematik: LaTeX Math in TinyMCE',
				author : 'Tyler Darnell',
				authorurl : '',
				infourl : '',
				version : "1.0"
			};
		},

		//start LaTeX functions
		_sqrt : function(s) {
		    return "\\sqrt[Index]{" + s + "}";
		},

		_frac : function(n, d) {
		    return "\\frac{" + n + "}{" + d + "}";
		},

		_sum : function(t, b, r) {
		    return "\\sum_{" + b + "}^{" + t + "} " + r;
		}
	});

	// Register plugin
	tinymce.PluginManager.add('mathematik', tinymce.plugins.Mathematik);
})();