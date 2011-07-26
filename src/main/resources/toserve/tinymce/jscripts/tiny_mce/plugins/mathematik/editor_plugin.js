(function(){tinymce.create("tinymce.plugins.Mathematik",{init:function(a,b){this.editor=a;var c=this;a.addCommand("mceSqrt",function(){var d=c._sqrt("Radicand");a.execCommand("mceInsertContent",false,d)});a.addCommand("mceFrac",function(){var d=c._frac("Numerator","Denominator");a.execCommand("mceInsertContent",false,d)});a.addCommand("mceMathPopup",function(){a.windowManager.open({file:b+"/insertMenu.html",width:500,height:450,inline:1},{plugin_url:b})});a.addCommand("mceSum",function(){a.windowManager.open({file:b+"/sumMenu.html",width:500,height:250,inline:1},{plugin_url:b})});a.addButton("squareroot",{title:"Root",cmd:"mceSqrt",image:b+"/img/sqrt.png"});a.addButton("fraction",{title:"Fraction",cmd:"mceFrac",image:b+"/img/frac.png"});a.addButton("mathpopup",{title:"Mathematik Insert Pane",cmd:"mceMathPopup",image:b+"/img/sum.png"});a.addButton("sumpopup",{title:"Sum Insert Menu",cmd:"mceSum",image:b+"/img/sum.png"})},getInfo:function(){return{longname:"Mathematik: LaTeX Math in TinyMCE",author:"Tyler Darnell",authorurl:"",infourl:"",version:"1.0"}},_sqrt:function(a){return"\\sqrt[Index]{"+a+"}"},_frac:function(b,a){return"\\frac{"+b+"}{"+a+"}"},_sum:function(c,a,d){return"\\sum_{"+a+"}^{"+c+"} "+d}});tinymce.PluginManager.add("mathematik",tinymce.plugins.Mathematik)})();