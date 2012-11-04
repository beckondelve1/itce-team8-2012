var GalleryModal=new Class({Extends:Modal,options:{title:"",byline:"",images:[]},initialize:function(e){this.parent({id:"gallery-modal",overlayOpacity:1,dismissable:false,minWidth:1220});
this.setOptions(e);
this.addCloseButton();
var c=new Element("ul",{"class":"images"});
var f=new Element("div");
var b=new Element("div",{"class":"nav previous unselectable",html:'<a><span class="icon"></span> Previous</a>'});
var a=new Element("div",{"class":"nav next unselectable",html:'<a>Next <span class="icon"></span></a>'});
this.options.images.each(function(i,g){if(i.type==="img"){var h=new Element("li",{"data-id":i.id,"data-src":i.url,"data-mediatype":"img"}).adopt([new Element("div",{"class":"caption",html:"<p>"+i.caption+"</p>"})])
}else{if(i.type==="pano"&&Browser.Plugins.Flash&&Browser.Plugins.Flash.version>=9){var h=new Element("li",{"data-id":i.id,"data-mediatype":"pano"}).adopt([new Element("div",{"class":"pano-wrap","data-pano1":i.pano1,"data-pano2":i.pano2}),new Element("div",{"class":"caption",html:"<p>"+i.caption+"</p>"})])
}else{if(i.type==="pano"&&(!Browser.Plugins.Flash||Browser.Plugins.Flash.version<9)){var h=new Element("li",{"data-id":i.id,"data-src":i.thumb,"data-mediatype":"img"}).adopt([new Element("div",{"class":"caption",html:"<p>"+i.caption+" (Flash Required)</p>"})])
}}}c.adopt(h)
});
var d=new GalleryDots(f,{numOfDots:this.options.images.length,onChange:function(g){this.slideShow.show(g)
}.bind(this)});
this.slideShow=new SlideShow(c,{onShow:function(g){this._loadImage(g.next.element);
this._loadImage(this._getNextSlide());
d.select(g.next.index);
this.fireEvent("change",[g.next.index,this._getCurrentImageId()])
}.bind(this)});
if(this.options.showFbLike){this.slideShow.addEvents({onShow:function(g){this._hideFbLikeButton(g.next.index)
}.bind(this),onShowComplete:function(g){this._showFbLikeButton(g.next.index)
}.bind(this)})
}this.rootEl=this.content.adopt([new Element("div",{"class":"gallery"}).adopt([c,b,a,f]),new Element("div",{"class":"meta"}).adopt([new Element("h1",{"class":"title",text:this.options.title}),new Element("p",{"class":"byline",text:this.options.byline})])]).addEvents({"click:relay(.previous)":function(){this.slideShow.show("previous")
}.bind(this),"click:relay(.next)":function(){this.slideShow.show("next")
}.bind(this),"mouseover:relay(.gallery)":function(h,g){this._fadeIn(f);
this._fadeIn(b);
this._fadeIn(a)
}.bind(this),"mouseout:relay(.gallery)":function(h,g){this._fadeOut(f);
this._fadeOut(b);
this._fadeOut(a)
}.bind(this)});
this.rootEl.getElements(".nav").each(function(g){g.get("tween").set("opacity",0);
g.setStyle("visibility","visible")
});
f.setStyle("visibility","visible").get("tween").set("opacity",0);
this._onKeyUp=function(g){switch(g.key){case"left":this.slideShow.show("previous");
break;
case"right":this.slideShow.show("next");
break
}}.bind(this);
this.rootEl.addEvent("keyup:relay(object)",function(g){g.stop()
})
},_fadeIn:function(a){a.set("tween",{duration:300,transition:Fx.Transitions.Quint.easeOut}).tween("opacity",1);
return this
},_fadeOut:function(a){a.set("tween",{duration:300,transition:Fx.Transitions.Quint.easeOut}).tween("opacity",0).get("tween").chain(function(){a.setStyle("visibility","visible")
});
return this
},_loadImage:function(d){var b=d.get("data-src");
if(b&&d.get("data-mediatype")==="img"){var f=new Element("img",{width:1200,height:600,src:b}).inject(d,"top");
d.removeProperty("data-src")
}else{if(d.get("data-mediatype")==="pano"&&!d.getElement("object")){var c=d.getElement(".pano-wrap"),a=c.get("data-pano1")+","+c.get("data-pano2"),e=new Swiff(Jetsetter.CDN_HOST+"/static/images/pano/WebPano.swf?v=2",{width:1200,height:600,params:{bgcolor:"#000000",allowFullscreen:true,wmode:"opaque",wMode:"opaque"},vars:{maxFOVUp:20,maxFOVDown:20,textures:a,fullscreenButton:true,startInertia:-15}});
e.inject(c)
}}return this
},_showFbLikeButton:function(b){var c=this.slideShow.slides[b].getElement(".fb-like");
if(c){setTimeout(function(){c.fade("in")
},500)
}else{var a=Jetsetter.HOST+this.options.images[b].pageUrl;
c=new Element("div",{"class":"fb-like",html:'<fb:like href="'+a+'" send="false" width="0" show_faces="false" colorscheme="dark" layout="standard" font="lucida grande"></fb:like>'}).inject(this.slideShow.current);
c.get("tween").set("opacity",0);
FB.Event.subscribe("xfbml.render",function(){$(c).fade("in")
});
FB.XFBML.parse(c)
}return this
},_hideFbLikeButton:function(a){var b=this.slideShow.slides[a].getElement(".fb-like");
if(b){b.get("tween").set("opacity",0)
}return this
},_getNextSlide:function(){var a=this.slideShow.index;
return this.slideShow.slides[(a+1)%this.slideShow.slides.length]
},_getCurrentImageId:function(){return this.slideShow.current.get("data-id").toInt()
},toElement:function(){return this.rootEl
},show:function(a){this.parent();
if(this.slideShow.index===a){this._loadImage(this.slideShow.current)
}else{this.slideShow.show(a,{transition:"none"})
}this._loadImage(this._getNextSlide());
if(this.options.showFbLike){this._showFbLikeButton(a)
}document.addEvent("keyup",this._onKeyUp);
return this
},close:function(){this.parent();
this.fireEvent("close",this._getCurrentImageId());
document.removeEvent("keyup",this._onKeyUp)
}});
window.addEvent("domready",function(){window.addEvent("fbReady",function(){FB.XFBML.parse()
});
var a=$("hero-gallery"),b=$("hero-dots");
heroGallery=new SlideShow(a),galleryDots=new GalleryDots(b,{numOfDots:a.getElements("li").length,onChange:function(c){heroGallery.show(c);
omniture.trackFeature("Bon Voyage: Viewed Hero Slide "+c)
}}),prevArr=$("inspiration").getElement(".arrow-prev"),nextArr=$("inspiration").getElement(".arrow-next"),actions=$("inspiration").getElement(".hero-actions"),look=$("look-inside");
actions.fade("hide");
prevArr.addEvent("click",function(){heroGallery.show("previous");
galleryDots.select(heroGallery.index)
});
nextArr.addEvent("click",function(){heroGallery.show("next");
galleryDots.select(heroGallery.index)
});
look.addEvent("click",function(){look.getParent().fade("out");
actions.fade("in");
heroGallery.show("next");
galleryDots.select(heroGallery.index);
omniture.trackFeature("Bon Voyage: Hero - Look Inside")
});
$("inspiration").addEvent("click:relay(.track-link)",function(f,d){f.stop();
var c="Bon Voyage: ";
if(d.get("href").contains("advice")){c+="PTP Link "
}else{c+="To Gilt Sale Link "
}if(d.hasClass("top")){c+="- Top"
}else{c+="- Bottom"
}omniture.trackFeature(c)
})
});