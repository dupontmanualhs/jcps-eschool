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

			ed.addCommand('mceProd', function(){
			    ed.windowManager.open({
			        file : url + '/prodMenu.html',
			        width : 500,
			        height : 250,
			        inline : 1
			    }, {
			        plugin_url : url
			    });
			});

			ed.addCommand('mceExp', function(){
			    var str = t._expo("Base", "Exponent");

			    ed.execCommand('mceInsertContent', false, str);
			});

			ed.addCommand('mceSubscript', function(){
			    var str = t._subscript("Expression", "Subscript");

			    ed.execCommand('mceInsertContent', false, str);
			});

			ed.addCommand('mceGreek', function(){
                ed.windowManager.open({
			        file : url + '/greekMenu.html',
			        width : 750,
			        height : 250,
			        inline : 1
			    }, {
			        plugin_url : url
			    });
			});

			//add buttons
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

            ed.addButton('prodpopup', {
                title : 'Product Insert Menu',
                cmd : 'mceProd',
                image : url + '/img/prod.png'
            });

            ed.addButton('exp', {
                title : 'Exponentiation',
                cmd : 'mceExp',
                image : url + '/img/super.png'
            });

            ed.addButton('subsc', {
                title : 'Subscript',
                cmd : 'mceSubscript',
                image : url + '/img/sub.png'
            });

            ed.addButton('greek', {
                title : 'Greek Alphabet',
                cmd : 'mceGreek',
                image : url + '/img/pi.png'
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

		_expo : function(b, e) {
		    return "{" + b + "}^{" + e + "}";
		},

		_subscript : function(b, i) {
		    return "{" + b + "}_{" + i + "}";
		}
	});

	// Register plugin
	tinymce.PluginManager.add('mathematik', tinymce.plugins.Mathematik);
})();