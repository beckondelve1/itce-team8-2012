var LoginPage=new Class({Implements:Screen.SlideShowHelpers,initialize:function(f){var d=0;
var b=1;
var g=2;
var e=new LoginPage.LoginScreen(f.getElement("#sign-in"));
if(f.getElement("#forgot-password")){var c=new ForgotPasswordScreen(f.getElement("#forgot-password"))
}if(f.getElement("#enter-email")){var i=new EnterEmailScreen(f.getElement("#enter-email"));
i.addEvents({onRegisteredEmailFound:function(k){e.emailFieldEl.set("value",k);
i.emailField.showTooltip('This email address is already registered. <a href="#sign-in">Login here</a>.')
}.bind(this),onSubmit:function(k){createPasswordScreen.setEmail(k);
this.showScreenById("create-password");
omniture.trackFeature("two-steps:login:create-password")
}.bind(this)})
}this.rootEl=f;
var j=new SlideShow(f,{duration:1200}).addEvents({show:function(k){switch(k.next.index){case d:e.loginAttempts=0;
break;
case b:c.emailFieldEl.set("value",e.emailFieldEl.get("value"));
c.reset();
break
}f.setStyle("overflow","hidden")
},showComplete:function(k){this.setFocus();
f.setStyle("overflow","visible")
}.bind(this)});
f.addEvent("click:relay(a)",function(m,k){var l=new URI(k.get("href"));
if(l.get("file")===new URI().get("file")){m.preventDefault();
this.showScreenById(l.get("fragment"))
}}.bind(this));
e.addEvents({loginAttemptLimitReached:function(k){c.emailFieldEl.set("value",k);
j.show(b)
},login:function(){location.replace(this.getReturnUrl())
}.bind(this),registrationComplete:function(){location.replace(Jetsetter.data.targetUrl)
}.bind(this)});
this.modalScreens=j;
var a=new URI();
if(a.getData("fpwd")){var h=new ResetPasswordScreen(f.getElement("#reset-password")).addEvents({passwordReset:function(){location.replace(this.getReturnUrl())
}.bind(this)});
j.show(g,{transition:"none"})
}else{if(a.get("fragment")=="fpw"){j.show(b,{transition:"none"})
}else{e.setFocus()
}}}});
LoginPage.LoginScreen=new Class({Extends:LoginScreen,initialize:function(b){this.parent(b);
this.emailFieldEl.setPlaceholder();
this.passwordFieldEl&&this.passwordFieldEl.setPlaceholder();
var a=b.getElement(".fb-login");
if(a){a.addEvent("click",function(c){c.preventDefault();
this._fbLogin()
}.bind(this))
}},_fbLogin:function(){FB.getLoginStatus(function(a){if(a.session){this._loginWithFacebook()
}else{omniture.trackFeature("Attempting Facebook connection with Jetsetter");
FB.login(function(b){if(b.session&&b.perms){this._loginWithFacebook();
omniture.trackFeature("Accepted Facebook connection with Jetsetter")
}else{omniture.trackFeature("Declined Facebook connection with Jetsetter")
}}.bind(this),{perms:"email,publish_stream,offline_access"})
}return false
}.bind(this));
return false
},_loginWithFacebook:function(){FB.api("/me",function(a){var b=new Request.JSONP({url:Jetsetter.SECURE_HOST+"/auth/login",data:Object.merge(a,{fbUserId:a.id}),onSuccess:function(c){if(c.success){omniture.trackFeature("login:facebook:success");
this._handleAuthResponse(c)
}else{omniture.trackFeature("login:facebook:no-connection");
this.fbTooltip=this.fbTooltip||new RightTooltip(this.emailFieldEl.getOffsetParent());
this.fbTooltip.show("There was a problem connecting to Facebook.  Please try again later.",this.emailFieldEl,300)
}}.bind(this)}).send()
}.bind(this))
},setFocus:function(){var a=null;
if(!this.emailFieldEl.get("value")){}else{if(this.passwordFieldEl){a=(this.passwordFieldEl.supports("placeholder")||this.passwordFieldEl.get("value"))?this.passwordFieldEl:a=this.passwordFieldEl.retrieve("passwordPlaceholderEl")
}}setTimeout(function(){try{a.focus()
}catch(b){}},0)
}});
var EnterEmailScreen=new Class({Extends:Screen,options:{email:""},initialize:function(b,a){this.parent(b,a);
this.emailField=new RegistrationEmailField(this.rootEl.getElement("input[type=email]"),{onValidationComplete:function(c){if(c){this.fireEvent("submit",this.getEmail())
}else{this.emailField.showTooltip()
}}.bind(this),onRegisteredEmailFound:function(c){this.fireEvent("registeredEmailFound",c)
}.bind(this)});
if(this.options.email){this.emailField.setEmail(this.options.email)
}this.rootEl.getElement("form").addEvent("submit",function(c){c.preventDefault();
this.validate()
}.bind(this))
},getEmail:function(){return $(this.emailField).get("value").trim()
},validate:function(){return this.emailField.validate()
},setFocus:function(){this.emailField.setFocus()
}});
var CreatePasswordScreen=new Class({Extends:Screen,options:{regMethod:null,promoId:null,referrerGuid:null,emails:{daily:false,weekly:false}},initialize:function(b,a){this.parent(b,a);
if(!(this.options.promoId||this.options.referrerGuid)){throw"CreatePasswordScreen: missing either a promoId or an inviter referrerGuid)"
}this.formEl=this.rootEl.getElement("form");
this.submitButtonEl=this.rootEl.getElement("button").set("disabled",false);
this.passwordField=new PasswordField(this.rootEl.getElement("input[type=password]"));
this.tosField=new FormField(this.rootEl.getElement("input[type=checkbox]"),{validators:[{tester:function(c){return(c.get("checked")===true)
},message:"You must agree to Jetsetter’s terms and conditions."}],tooltipStyle:"top"});
$(this.tosField).getParent("div").addEvent("click:relay(a)",function(c){c.preventDefault();
window.open("/terms-of-service","_blank","menubar=no,width=650,height=700,toolbar=no,scrollbars=yes,resizable=yes")
});
this.rootEl.getElement("form").addEvent("submit",function(c){c.preventDefault();
if(this.validate()){this.register()
}}.bind(this))
},validate:function(){if(!this.passwordField.validate()){this.passwordField.showTooltip();
return false
}if(!this.tosField.validate()){this.tosField.showTooltip();
return false
}return true
},setFocus:function(){this.passwordField.setFocus()
},setEmail:function(a){this.email=a
},register:function(){this.submitButtonEl.set("disabled",true);
new Request.JSONP({url:Jetsetter.SECURE_HOST+"/registerasync.php",data:Object.merge(this.formEl.toQueryObject(),{action:"register",emailAddress:this.email,method:this.options.regMethod,promoId:this.options.promoId,referrerGuid:this.options.referrerGuid,travelSaleReminder:this.options.emails.daily?"on":"off",travelUpcomingSales:this.options.emails.weekly?"on":"off",userSearchReferralId:this.options.userSearchReferralId,click_hash:this.options.click_hash}),onSuccess:function(f){if(f.success){GA.trackRegistration(f.data.promoAlias);
omniture.trackRegistration(f.data.promoAlias);
this.eventFired=false;
var d=Math.random()+"";
var c=d*10000000000000;
var h=new Element("iframe",{src:"http://fls.doubleclick.net/activityi;src=3081328;type=jsreg570;cat=jsmem724;u1="+f.data.guid+";ord="+c+"?",width:"1",height:"1",frameborder:"0",style:"display:none",events:{load:function(){if(!this.eventFired){this.fireEvent("registrationComplete");
this.eventFired=true
}}.bind(this)}});
document.getElementsByTagName("body")[0].appendChild(h);
setTimeout(function(){if(!this.eventFired){this.fireEvent("registrationComplete");
this.eventFired=true
}}.bind(this),1000);
Jetsetter.user=new User()
}else{var b=f.errors[0][0].toInt(),g=f.errors[0][1];
this.passwordField.showTooltip(g);
this.submitButtonEl.set("disabled",false)
}}.bind(this)}).send()
}});
window.addEvent("domready",function(){var b=(function(){if(Jetsetter.data.backgroundImageUrl){return Jetsetter.data.backgroundImageUrl
}var e=19;
return Jetsetter.CDN_HOST+"/static/images/login/backgrounds/"+Number.random(1,e)+".jpg"
}());
var c=new BackgroundImage(b,{showGradients:true});
var a=new LoginPage($("screens"));
var d=$("login-footer-gilt");
if(d){d.getElements("ul").each(function(e){e.store("height",e.getStyle("height").toInt());
e.store("open",false)
});
d.getElements("ul").set("tween",{duration:250,transition:Fx.Transitions.Pow.easeOut}).setStyle("height",0);
c.setImagePosition();
d.addEvent("click:relay(span)",function(g,f){var h=f.getParent().getElement("ul");
d.getElements("ul").each(function(e){if(h===e&&h.retrieve("open")==true){h.tween("height",0);
h.store("open",false);
f.set("text","[+]");
return
}else{if(h===e&&h.retrieve("open")==false){h.tween("height",h.retrieve("height").toInt());
h.store("open",true);
f.set("text","[-]");
return
}}if(e.retrieve("open")==true){e.store("open",false);
e.tween("height",0);
e.getSiblings("span")[0].set("text","[+]")
}})
})
}(function(){var g=$("sweepstakes");
if(!g){return
}var f=g.getElement("ul");
var e=f.getStyle("height").toInt();
f.set("tween",{duration:250,transition:Fx.Transitions.Pow.easeOut}).setStyle("height",0);
g.getElement("a").addEvent("click",function(h){if(f.getStyle("height").toInt()===0){f.tween("height",e)
}else{f.tween("height",0)
}})
}())
});