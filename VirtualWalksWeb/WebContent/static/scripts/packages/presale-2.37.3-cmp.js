var PresaleCalendar=new Class({initialize:function(a){var b=this;
var c=new Date();
this.DAY_MS=86400000;
this.controls=new Element("div",{"class":"controls"}).inject(a);
this.calendarTable=new Element("table",{"class":"presale calendar"}).inject(a);
this.calendarBody=new Element("tbody").inject(this.calendarTable);
this.rows=new CalendarRows();
this.events=new Element("br");
this.activeMonth=c.getMonth();
this.activeYear=c.getFullYear();
this.packages=[];
this.lowestDate=null;
this.highestDate=null;
this.availableDates={};
this.visibleDayElements={};
this.moveForward=this.moveForwardFunc(b);
this.moveBackward=this.moveBackFunc(b)
},moveForwardFunc:function(a){return function(){a.activeMonth++;
if(a.activeMonth>11){a.activeMonth=0;
a.activeYear++
}a.render();
return false
}
},moveBackFunc:function(a){return function(){a.activeMonth--;
if(a.activeMonth<0){a.activeMonth=11;
a.activeYear--
}a.render();
return false
}
},addPackage:function(b,c){var a=[];
b.each(function(d){var e=d.getMonth()+"/"+d.getDate()+"/"+d.getFullYear();
d.key=e;
this.availableDates[e]=d;
a.push(e);
if(this.lowestDate==null||d<this.lowestDate){this.lowestDate=new Date(d.getFullYear(),d.getMonth(),1)
}else{if(this.highestDate==null||d>this.highestDate){this.highestDate=new Date(d.getFullYear(),d.getMonth(),1)
}}},this);
this.packages.push({dates:b,keys:a,id:c,upperBound:null,lowerBound:null})
},concatenateClasses:function(){if(Browser.ie6){Object.each(this.visibleDayElements,function(d,a){var e="ie6-";
var b=d.className.split(" ");
b.each(function(f,c){if(f.indexOf("ie6-")!=-1){d.removeClass(f)
}},this);
if(d.hasClass("available")){e+="available-"
}if(d.hasClass("invalid")){e+="invalid-"
}if(d.hasClass("waitList")){e+="waitList-"
}if(d.hasClass("trip")){e+="trip-"
}if(d.hasClass("upperbound")){e+="upperbound-"
}if(d.hasClass("lowerbound")){e+="lowerbound-"
}if(e.charAt(e.length-1)=="-"){e=e.substr(0,e.length-1)
}d.className+=" "+e
},this)
}},render:function(){this.selectedPackage=null;
var g=this;
this.calendarBody.getChildren().destroy();
this.controls.getChildren().destroy();
var f=new Date(this.activeYear,this.activeMonth,1);
if(f<this.lowestDate){f=this.lowestDate
}else{if(f>this.highestDate){f=this.highestDate
}}this.activeMonth=f.getMonth();
this.activeYear=f.getFullYear();
var d=new Element("strong",{text:this.rows.getMonthName(this.activeMonth)+" "+this.activeYear}).inject(this.controls);
new Element("br").setStyle("clear","both").inject(this.controls);
var a=new Date(this.activeYear,this.activeMonth,1);
var b=(a.getDay()+7)%7;
var e=new Date(this.activeYear,this.activeMonth+1,0).getDate();
var c=new Date(this.activeYear,this.activeMonth,0).getDate();
this.visibleDayElements={};
var i=new Element("tr").inject(this.calendarBody);
new Element("th",{text:"S"}).inject(i);
new Element("th",{text:"M"}).inject(i);
new Element("th",{text:"T"}).inject(i);
new Element("th",{text:"W"}).inject(i);
new Element("th",{text:"T"}).inject(i);
new Element("th",{text:"F"}).inject(i);
new Element("th",{text:"S"}).inject(i);
var h=this.rows.getRows(this.activeMonth,this.activeYear);
h.each(function(j){i=new Element("tr").inject(this.calendarBody);
j.each(function(l){var n=l.year;
var p=l.month;
var k=l.day;
var m;
if(l.valid){m=new Element("td",{text:k.toString()}).inject(i)
}else{m=new Element("td",{"class":"invalid",html:"&nbsp;"}).inject(i)
}m.key=(p+"/"+k+"/"+n);
m.date=new Date(n,p,k);
var o=this.availableDates[m.key];
if(o){m.addClass("available");
if(o.waitList){m.addClass("waitList")
}}this.visibleDayElements[m.key]=m
},this)
},this);
this.concatenateClasses();
this.events.fireEvent("render")
}});
var PresaleCatalog=new Class({initialize:function(f,d){var k=this;
this.events=new Element("br");
this.calendars=null;
this.packageData=null;
this.calendarContainer1=new Element("div",{id:"presaleCalendar1"}).inject(f);
this.calendarContainer2=new Element("div",{id:"presaleCalendar2"}).inject(f);
this.calendar1=new PresaleCalendar(this.calendarContainer1);
this.calendar2=new PresaleCalendar(this.calendarContainer2);
var h=this.getPackagesFromData(d);
this.numMonths=0;
var a=null;
for(var e=0;
e<h.length;
e++){for(var b=0;
b<h[e].length;
b++){if(h[e][b].getMonth()!=a){a=h[e][b].getMonth();
this.numMonths++
}}}this.calendarContainer2.setStyle("display",this.numMonths>1?"block":"none");
var g=k.calendar1.rows.getSurroundingMonths(k.calendar1.activeMonth);
this.prevButton=new Element("a",{"class":"prev-month",href:"#",html:""}).inject($("presale-nav-controls"));
this.prevButton.addEvent("click",function(){if(Number(k.calendar1.lowestDate.getMonth())==Number(k.calendar1.activeMonth)){return false
}k.calendar1.moveBackward();
k.calendar2.moveBackward();
k.updateNavLinks(k);
return false
});
if(this.numMonths>1){var c=k.calendar2.rows.getSurroundingMonths(k.calendar2.activeMonth);
this.nextButton=new Element("a",{"class":"next-month",href:"#",html:k.calendar2.rows.getMonthName(c.next)+" &rsaquo;"}).inject($("presale-nav-controls"));
this.nextButton.addEvent("click",function(){if(Number(k.calendar2.highestDate.getMonth())==Number(k.calendar2.activeMonth)){return false
}k.calendar1.moveForward();
k.calendar2.moveForward();
k.updateNavLinks(k);
return false
})
}this.buildCalendars(h)
},updateNavLinks:function(g){var b=!(Number(g.calendar1.lowestDate.getMonth())==Number(g.calendar1.activeMonth));
var a=!(Number(g.calendar2.highestDate.getMonth())==Number(g.calendar2.activeMonth));
var f=g.calendar1.rows.getSurroundingMonths(g.calendar1.activeMonth);
var e=b?"&lsaquo; "+g.calendar1.rows.getMonthName(f.previous):"";
g.prevButton.set("html",e);
var d=g.calendar2.rows.getSurroundingMonths(g.calendar2.activeMonth);
var c=a?g.calendar2.rows.getMonthName(d.next)+" &rsaquo;":"";
g.nextButton.set("html",c)
},getPackagesFromData:function(e){var c;
var a;
var d=[];
this.calendar1.activeMonth=Number(e[0].substr(4,2))-1;
this.calendar1.activeYear=Number(e[0].substr(0,4));
this.calendar2.activeMonth=this.calendar1.activeMonth+1;
this.calendar2.activeMonth=this.calendar2.activeMonth==12?0:this.calendar2.activeMonth;
this.calendar2.activeYear=this.calendar1.activeMonth==11?this.calendar1.activeYear+1:this.calendar1.activeYear;
var b=new Date().clearTime().increment("day",2);
e.each(function(j,h){var i=Number(j.substr(0,4));
var k=Number(j.substr(4,2))-1;
var g=Number(j.substr(6));
var f=new Date(i,k,g);
if(f<b){return false
}if(!c){c=f;
a=new Array()
}else{c=new Date(c.getTime()+86400000)
}if(Number(c)!=Number(f)){d.push(a.slice());
a=new Array();
c=f
}a.push(f)
});
d.push(a);
return d
},buildCalendars:function(a){a.each(function(b){this.calendar1.addPackage(b);
this.numMonths>1&&this.calendar2.addPackage(b)
},this);
this.numMonths>1&&this.updateNavLinks(this);
this.calendar1.render();
this.numMonths>1&&this.calendar2.render()
}});
Jetsetter.packages.presale.init=function(){$$(".icon-cal").each(function(c){new CalTooltip(c)
});
$$(".icon-email").each(function(c){new EmailTooltip(c)
});
var b=$("presaleCatalog");
if(b){var a=new PresaleCatalog(b,presaleDates);
if(Jetsetter.user.guid&&Jetsetter.modules.EmailReminder){$("presaleCatalog").getElements(".available").set("style","cursor:pointer")
}}};
window.addEvent("domready",Jetsetter.packages.presale.init);