var FAQModal=new Class({Extends:Modal,initialize:function(b,a){var d=b.clone().show(),c=d.getElement(".start");
this.parent(Object.merge(a||{},{id:"faq-modal"}));
this.canvas.adopt(d);
c.addEvent("click",function(e){e.preventDefault();
this.addEvent("closeEnd",function(){this.fireEvent("ctaClick",$(e.target).get("href"))
});
this.close()
}.bind(this))
}});
var PostcardGallery=new Class({Implements:Events,initialize:function(b){var a=this;
a.rootEl=b;
a.left=a.rootEl.getElement(".arrow-left");
a.right=a.rootEl.getElement(".arrow-right");
a.dots=a.rootEl.getElement(".dots");
a.locked=false;
a.expertsElem=a.rootEl.getElement("#expert-info");
a.categories=a.rootEl.getElements("#categories li");
a.catWidth=a.categories[0].getSize().x*a.categories.length;
a.postcards=new SlideShow(a.rootEl.getElement("#postcards ul"),{onShow:function(c){a.locked=true;
c.next.element.setStyle("background-image","url("+c.next.element.get("data-image")+")")
},onShowComplete:function(){a.locked=false
}});
a.experts=new SlideShow(a.rootEl.getElement("#expert-info"));
if(a.postcards.slides.length>1){a.right.addEvent("click",function(c){c.preventDefault();
if(!a.locked){a.next()
}});
a.left.addEvent("click",function(c){c.preventDefault();
if(!a.locked){a.prev()
}})
}else{a.right.hide();
a.left.hide()
}a.categories.each(function(c){c.addEvent("click",function(){a.switchToCategory(this.get("data-category"),"next")
})
});
a.dots.addEvent("click:relay(a)",function(d,c){d.preventDefault();
a.goToSlide(parseInt(c.get("data-index")))
});
a.postcards.slides[0].setStyle("background-image","url("+a.postcards.slides[0].get("data-image")+")");
a.categories.getParent("#categories").setStyle("width",a.catWidth);
a.switchToCategory(1,"next")
},switchToCategory:function(f,g){var b=this,e=false,d=0;
b.categories.each(function(h){if(f==h.get("data-category")){h.addClass("selected")
}else{h.removeClass("selected")
}});
b.dots.empty();
b.postcards.slides.each(function(h,k){if(f==h.get("data-category")){if(!e&&g==="next"){b.postcards.show(k);
b.experts.show(k);
e=true
}var j=new Element("a",{"data-index":h.retrieve("slideshow-index")});
if(g==="next"&&d===0){j.addClass("selected")
}j.inject(b.dots,"bottom");
d++
}});
if(d<2){b.dots.hide();
b.expertsElem.setStyle("margin-top",0)
}else{b.expertsElem.setStyle("margin-top",30)
}if(g==="prev"){var a=b.dots.getElements("a")[b.dots.getElements("a").length-1],c=parseInt(a.get("data-index"));
b.postcards.show(c);
b.experts.show(c);
a.addClass("selected")
}},goToSlide:function(a){this.postcards.show(a);
this.experts.show(a);
this.changeDot(a)
},changeDot:function(a){this.dots.getElements("a").each(function(b){if(b.get("data-index")==a){b.addClass("selected")
}else{b.removeClass("selected")
}})
},next:function(){var c=this,a=c.postcards.current,b=a.retrieve("slideshow-index"),e=(b==c.postcards.slides.length-1)?c.postcards.slides[0]:c.postcards.slides[b+1],d=e.retrieve("slideshow-index");
if(a.get("data-category")==e.get("data-category")){c.postcards.show("next");
c.experts.show("next");
c.changeDot(d)
}else{c.switchToCategory(e.get("data-category"),"next")
}},prev:function(){var d=this,b=d.postcards.current,c=b.retrieve("slideshow-index"),a=(c==0)?d.postcards.slides[d.postcards.slides.length-1]:d.postcards.slides[c-1],e=a.retrieve("slideshow-index");
if(b.get("data-category")==a.get("data-category")){d.postcards.show("previous");
d.experts.show("previous");
d.changeDot(e)
}else{d.switchToCategory(a.get("data-category"),"prev")
}}});
window.addEvent("domready",function(){var f=$("main-wrap");
if(f){var h=new PostcardGallery(f)
}var g=$("meet").getElements(".dots");
var a=new SlideShow($("experts"),{selector:".row-wrap",onShow:function(i){g.getElement(".selected").removeClass("selected");
g.getElement(":nth-child("+(this.index+1)+")").addClass("selected")
}});
g.addEvent("click:relay(a)",function(k,j){var i=j.getParent().getChildren().indexOf(j);
var l=(a.index<i)?"pushLeft":"pushRight";
a.show(i,{transition:l})
});
var c=$("bio-hero");
if(c){var b=c.getElement("h1").get("text"),d=0;
a.slides.each(function(k,j){k.getElements("h5").each(function(i,l){if(i.get("text").contains(b)){d=j
}})
});
a.show(d)
}$("meet").getElements(".arrow").addEvent("click",function(i){i.preventDefault();
if(this.hasClass("arrow-left")){a.show("previous",{transition:"pushRight"})
}else{a.show("next",{transition:"pushLeft"})
}});
function e(i){Jetsetter.secureLogin({promoId:342,defaultToRegistration:(!Jetsetter.user.guid),showRememberMe:false,registrationScreenCopy:{title:"Get ready for the perfect vacation",message:'<p>We’re excited to help plan your trip. First, tell us how we can reach you, and create a password so you can continue later.</p><p>Already have an account? <a href="#login-screen">Log in here.</a></p>'},emails:{weekly:true},onLogin:function(){location=i
}})
}$("page-content").addEvents({"click:relay(.faq)":function(){new FAQModal($("faq"),{onCtaClick:function(i){e(i)
}}).show()
},"click:relay(.start)":function(j,i){j.preventDefault();
e(i.get("href"))
}})
});