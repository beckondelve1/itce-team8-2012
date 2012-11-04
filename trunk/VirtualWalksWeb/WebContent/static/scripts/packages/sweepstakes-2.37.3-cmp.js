var SweepstakesRegistration=new Class({Implements:[Options,Events],options:{inviteScreenTitle:"Thanks for Registering!"},initialize:function(d,b){this.attachTo=d;
this.setOptions(b);
this.containerEl=this.attachTo.getParent("#register");
this.nofb=false;
if(arguments[2]==="nofb"){this.nofb=true
}if(this.options.state=="winner"){this.attachTo.adopt(new Element("h2",{text:"Sorry, the contest is closed"}),new Element("p",{html:this.options.hasEndedPrefix}),new Element("p",{html:this.options.winnerMessage}),new Element("p",{html:'If you missed out, don’t worry; You can still find exclusive offers on the world’s greatest vacations every day at Jetsetter. <a href="/register/promo/'+this.options.alias+'">Go now.</a>'}));
return
}else{if(this.options.state=="closed"){this.attachTo.adopt(new Element("h2",{text:"Sorry, the contest is closed"}),new Element("p",{html:this.options.hasEndedPrefix+" "+this.options.closeMessage}),new Element("p",{html:'If you missed out, don’t worry; You can still find exclusive offers on the world’s greatest vacations every day at Jetsetter. <a href="/register/promo/'+this.options.alias+'">Go now.</a>'}));
return
}}var e=new Element("form",{id:"form-selector"}).adopt(new Element("label",{"for":"non-member",html:'<input type="radio" name="selected-form" id="non-member" checked="checked"> I’m <strong>not</strong> currently a Jetsetter or Gilt member.'}),new Element("label",{"for":"member",html:'<input type="radio" name="selected-form" id="member"> I’m already a Jetsetter or Gilt member.'})).addEvent("click:relay(input)",function(i,h){if(h.get("id")=="member"){this.showLoginScreen()
}else{this.showRegisterScreen()
}}.bind(this)).inject(this.attachTo);
var g={message:"<p>Complete the fields below to enter and become a Jetsetter member.</p>",promoId:b.promoId,ep:"sweepstakes",optins:b.optins,alias:b.alias};
var c=new SweepstakesRegistration.RegisterScreen(this.attachTo,g).addEvents({registrationComplete:this.onRegistrationComplete.bind(this),loginComplete:this.onLoginComplete.bind(this),loginFail:function(h){this.showFbScreen(h)
}.bind(this)});
if(!this.nofb){var f=new SweepstakesRegistration.FbScreen(this.attachTo,g).addEvents({registrationComplete:this.onRegistrationComplete.bind(this),loginComplete:this.onLoginComplete.bind(this),back:this.showRegisterScreen.bind(this)})
}var a=new SweepstakesRegistration.LoginScreen(this.attachTo,{optins:b.optins,alias:b.alias}).addEvent("loginComplete",this.onLoginComplete.bind(this));
this.registerScreen=c;
this.fbScreen=f;
this.loginScreen=a;
this.showRegisterScreen();
d.addEvent("click:relay(.tooltip a)",function(j,h){var i=new URI(h.get("href"));
if(i.get("fragment")==="login"){j.preventDefault();
this.showLoginScreen(true)
}}.bind(this))
},onLoginComplete:function(){this.showInviteScreen();
var b=new URI(window.location);
var a=new URI(document.referrer);
var c=[{eventType:"jetsetSweepsTakesLogin",key5:encodeURIComponent(b.get("directory")+b.get("file")),key6:encodeURIComponent(b.get("query")),key25:Jetsetter.user.visitorId,key32:Jetsetter.CHANNEL_ID}];
Jetsetter.tracker.trackEvent(c[0])
},onRegistrationComplete:function(){this.isNewRegistration=true;
this.showInviteScreen();
this.fireEvent("registrationComplete");
var b=new URI(window.location);
var a=new URI(document.referrer);
var c=[{eventType:"jetsetSweepsTakesRegistration",key5:encodeURIComponent(b.get("directory")+b.get("file")),key6:encodeURIComponent(b.get("query")),key25:Jetsetter.user.visitorId,key32:Jetsetter.CHANNEL_ID}];
Jetsetter.tracker.trackEvent(c[0])
},showRegisterScreen:function(){if(!this.nofb){this.fbScreen.hide()
}this.loginScreen.hide();
this.registerScreen.show()
},showFbScreen:function(a){this.fbScreen.show(a);
this.loginScreen.hide();
this.registerScreen.hide()
},showLoginScreen:function(a){if(a){this.loginScreen.emailFieldEl.set("value",this.registerScreen.emailFieldEl.get("value"));
$("member").set("checked",true)
}if(!this.nofb){this.fbScreen.hide()
}this.registerScreen.hide();
this.loginScreen.show()
},showInviteScreen:function(e){e=e||this.options.inviteScreenTitle;
var c=new Element("div",{id:"invitation"});
c.adopt(new Element("div",{"class":"banner"}).adopt(new Element("h2",{text:e}),new Element("p",{html:"For each friend you invite who registers, you will get another entry into the drawing."})));
var g=new InviteEmailForm(c,{message:"",requestParams:{type:"sweepstakesinvite",alias:this.options.alias},onInvitesSent:function(h){GA.trackInvites(h,"sweepstakesinvite");
omniture.trackInvites(h,"sweepstakesinvite")
}});
g.formEl.getElement("p.details").destroy();
g.textAreaEl.setPlaceholder("Enter your personal message here.");
var f=new Element("a",{"class":"facebook action",target:"_blank",href:this.options.facebookUrl,text:"Facebook"}).addEvent("click",function(){omniture.trackFeature("sweepstakes-facebook")
});
var d=new Element("a",{"class":"twitter action",target:"_blank",href:this.options.twitterUrl,text:"Twitter"}).addEvent("click",function(){omniture.trackFeature("sweepstakes-twitter")
});
var a=new Element("div",{"class":"share-wrapper fl"}).adopt(new Element("h4",{text:"Invite by Facebook or Twitter"}),f,d);
a.inject(g.formEl.getLast(),"before");
var b=new Element("a",{id:"skip","class":"action",text:"No thanks, go to Jetsetter"}).addEvent("click",this.exitSweepstakes.bind(this));
g.formEl.getElement(".button-wrapper").adopt(new Element("div").adopt(b));
g.addEvent("invitesSent",this.showConfirmationScreen.bind(this));
this.containerEl.empty().adopt(c);
setTimeout(function(){g.formEl.getElement(".email").focus()
},0)
},showConfirmationScreen:function(){var b=new Element("div",{id:"confirmation"});
var a=new Element("button",{"class":"invite-more large-button",text:"Invite more friends"}).addEvent("click",function(){omniture.trackFeature("sweepstakes-invite-more");
this.showInviteScreen("Invite More Friends!")
}.bind(this));
var c=new Element("button",{"class":"exit large-button",text:"Go to Jetsetter"}).addEvent("click",this.exitSweepstakes.bind(this));
b.adopt(new Element("div",{"class":"banner"}).adopt(new Element("h2",{text:"Your Emails Have Been Sent!"}),new Element("p",{html:this.options.confirmationMessage})),new Element("div",{"class":"actions"}).adopt(a,c));
this.containerEl.empty().adopt(b)
},exitSweepstakes:function(){omniture.trackFeature("sweepstakes-exit");
location.replace("/sales"+(this.isNewRegistration?"#tour":""))
}});
SweepstakesRegistration.Utils=new Class({setOptinFields:function(a){this.options.optins.each(function(b){a.adopt(new Element("label").adopt(new Element("input",{"class":"checkbox",type:"checkbox",name:b.checkboxName,checked:b.checked})).appendText(b.title),new Element("p",{text:b.copy}))
})
}});
SweepstakesRegistration.RegisterScreen=new Class({Extends:Registration.RegisterScreen,Implements:SweepstakesRegistration.Utils,initialize:function(b,a){this.parent(b,a);
this.rootEl.getElement("ul.quotes").destroy();
this.rootEl.getElement("h2").destroy();
this.rootEl.getElement(".message").destroy();
this.loginActionEl.destroy();
this.formEl.getElement("div.tos").inject(this.formEl);
this.submitButtonEl.inject(this.formEl);
this.alertsEl.empty();
this.submitButtonEl.set("text","Register to Win");
if(this.rootEl.getElement("fieldset.facebook h4")){this.rootEl.getElement("fieldset.facebook h4").set("text","Simply use your Facebook identity to enter and become a Jetsetter member:")
}this.formEl.adopt(new Element("input",{type:"hidden",name:"sweepstakes",value:this.options.alias}),new Element("input",{type:"hidden",name:"sweepstakesInviteId",value:this.options.inviteId}),new Element("input",{type:"hidden",name:"travelSaleReminderBoth",value:"on"}));
this.setOptinFields(this.alertsEl);
var c="/tos/"+this.options.alias;
this.tosLinkEl.set("href",c).removeEvents().addEvent("click",function(d){d.preventDefault();
window.open(c,"_blank","menubar=no,width=650,height=700,toolbar=no,scrollbars=yes")
})
}});
SweepstakesRegistration.FbScreen=new Class({Extends:SweepstakesRegistration.RegisterScreen,initialize:function(b,a){this.parent(b,a);
this.rootEl.getElement("fieldset.facebook").destroy();
this.rootEl.getElement("fieldset.user-info h4").set("text","Simply choose a password below and you’ll be on your way:");
new Element("div",{"class":"back"}).adopt(new Element("a",{"class":"action",html:"&larr; Back"}).addEvent("click",function(){this.fireEvent("back")
}.bind(this))).inject(this.formEl,"top")
},show:function(a){this.fbUserEl=this.fbUserEl||new Element("div",{id:"fb-user"}).adopt(new Element("img",{src:"http://graph.facebook.com/"+a.id+"/picture","class":"thumb",alt:"",width:50,height:50}),new Element("span",{"class":"name",text:a.first_name+" "+a.last_name})).inject(this.formEl.getElement(".back"),"after");
this.emailFieldEl.set("value",a.email);
this.parent();
this.passwordFieldEl.focus()
}});
SweepstakesRegistration.LoginScreen=new Class({Extends:Registration.LoginScreen,Implements:SweepstakesRegistration.Utils,initialize:function(b,a){this.parent(b,a);
this.rootEl.getElement("h2").destroy();
this.rootEl.getElement("p").destroy();
this.rootEl.getElement("a.back").destroy();
this.submitButtonEl.set("text","Log In to Win");
this.formEl.adopt(new Element("input",{type:"hidden",name:"sweepstakes",value:this.options.alias}));
this.alertsEl=new Element("fieldset",{"class":"alerts"}).inject(this.formEl);
this.setOptinFields(this.alertsEl)
}});
window.addEvent("domready",function(){if(Browser.ie6){$(document.body).setStyle("backgroundColor","#333")
}else{new BackgroundImage(Jetsetter.sweepstakes.backgroundImageUrl)
}window.addEvent("fbReady",function(){if(!Jetsetter.sweepstakesRegistration&&Jetsetter.sweepstakes.state=="open"){Jetsetter.sweepstakesRegistration=new SweepstakesRegistration($("form-container"),Jetsetter.sweepstakes)
}});
setTimeout(function(){if(!Jetsetter.sweepstakesRegistration&&Jetsetter.sweepstakes.state=="open"){Jetsetter.sweepstakesRegistration=new SweepstakesRegistration($("form-container"),Jetsetter.sweepstakes,"nofb")
}},2000);
var b=new URI(window.location);
var a=new URI(document.referrer);
var c=[{eventType:"jetsetSweepsTakesView",key3:Jetsetter.TRACKER_SITE,key4:encodeURIComponent(a.get("directory")+a.get("file")),key5:encodeURIComponent(b.get("directory")+b.get("file")),key6:encodeURIComponent(b.get("query")),key25:Jetsetter.user.visitorId,key32:Jetsetter.CHANNEL_ID}];
window.addEvent("load",function(){Jetsetter.tracker.trackEvent(c[0])
})
});