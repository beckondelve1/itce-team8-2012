var DenominationField=new Class({Extends:FormField,options:{minValue:25},initialize:function(b,a){this.setOptions(a);
this.parent(b,{validators:[{tester:function(c){return(this.getValue()>=this.options.minValue)
},message:"Please enter an amount of "+this.options.minValue.formatCurrency()+" or more"}]})
},getValue:function(){var a=this.fieldEl.get("value").replace("/[^0-9]/g","");
return a.toInt()||0
}});
var SendDateCalendar=new Class({Extends:CalendarBoundTextfield,initialize:function(b,a){this.earliestDate=new Date().normalize("day");
this.latestDate=new Date().normalize("day").increment("year",1);
var a=Object.merge(a||{},{earliestMonth:this.earliestDate.clone().normalize("month"),latestMonth:this.latestDate.clone().normalize("month")});
this.parent(b,a);
this.addEvent("monthChange",function(){this.showDates()
}.bind(this));
this.showDates()
},showDates:function(){$(this).getElements("td").each(function(b){var a=b.retrieve("bookingDate");
if(a>=this.earliestDate&&a<=this.latestDate){b.addClass("available")
}},this)
}});
var GiftCardLanding=new Class({initialize:function(a){this.rootEl=a.addEvent("submit",function(d){d.preventDefault();
if(this.validate()){Jetsetter.secureLogin({dismissOnLogin:true,allowRegistration:true,promoId:475,onLogin:function(){a.submit()
}})
}}.bind(this));
this.denominationsEl=$("denominations").addEvent("click:relay(input)",function(e,d){this.denominationsEl.getElement(".selected").removeClass("selected");
d.getParent("li").addClass("selected")
}.bind(this));
this.denominationsEl.getElement("[type=radio]").set("checked",true);
var c=this.denominationsEl.getElement("[name=denomination-other]");
c.addEvent("focus",function(e){var d=$(e.target);
d.getParent("li").getElement("[type=radio]").set("checked",true)
}.bind(this));
this.otherDenominationField=new DenominationField(c);
this.requiredFields=[new FormField(a.getElement("[name=recipient-name]")),new EmailField(a.getElement("[name=recipient-email]")),new FormField(a.getElement("[name=sender-name]"))];
var b=new SendDateCalendar(a.getElement("[name=send-date]"))
},validate:function(){var f=true,c=[],a=this.requiredFields;
var e=this.denominationsEl.getElement("[name=denomination]:checked");
if(e.get("value")==="other"&&!this.otherDenominationField.validate()){c.push(this.otherDenominationField);
this.otherDenominationField.highlight()
}a.each(function(g){if(!$(g).get("disabled")&&!g.validate()){c.push(g);
g.highlight()
}});
var b=c[0];
if(b){f=false;
b.showTooltip();
var d=$(b).getPosition().y-20;
if(window.getScroll().y>d){this.scrollFx=this.scrollFx||new Fx.Scroll(window);
this.scrollFx.start(0,d)
}}return f
}});
window.addEvent("domready",function(){new GiftCardLanding($("order-form"))
});