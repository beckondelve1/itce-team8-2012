


1. How-to write a WS Request class
- open Onvif Device Manager to connect to the IP Camera  
- use WireShark to capture the WS request message you want to simulate
- right-click on that message and click 'Follow TCP stream'
- create a new class extending the class onvif.model.OnvifRequest
- paste the SOAP content from step 2 to the new class (see GetProfileRequest.java for an example) 

2. How-to write a WS Response class
- see GetStreamUriResponse.java for an example (XMLParser.java provides the simplest way to access DOM 
structure of XML doc)

NOTE: 
* Each request consists of header and body strings. Both of them use String Replacement (%s) to 
fill the parameters

* file onvif.test.Test.java contains several useful examples 
 
