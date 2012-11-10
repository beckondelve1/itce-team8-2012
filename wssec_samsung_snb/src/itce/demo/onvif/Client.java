/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package itce.demo.onvif;

import java.io.Closeable;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.onvif.ver20.imaging.wsdl.ImagingPort;
import org.onvif.ver20.imaging.wsdl.ImagingService;
import org.onvif.ver10.device.wsdl.Device;
import org.onvif.ver10.device.wsdl.DeviceService;
import org.onvif.ver10.schema.Capabilities;
import org.onvif.ver10.schema.CapabilityCategory;
import org.onvif.ver10.schema.ImagingSettings20;
import org.onvif.ver10.schema.Scope;




public final class Client {

    private static final String USER_NAME = System.getProperty("user.name");

    private Client() {
    }

    public static void main(String args[]) throws Exception {
    	
    	testGetCapabilities();
    	
    	//testGetScopes();
    	
    	//testGetImagingSettings();
    }
    
    //GetCapabilities()
    private static void testGetCapabilities(){
    	try {

            SpringBusFactory bf = new SpringBusFactory();
            URL busFile = Client.class.getResource("wssec.xml");
            Bus bus = bf.createBus(busFile.toString());
            BusFactory.setDefaultBus(bus);

            Map<String, Object> outProps = new HashMap<String, Object>();
            outProps.put("action", "UsernameToken Timestamp");

            outProps.put("passwordType", "PasswordDigest");
            outProps.put("user", "admin");
            outProps.put("passwordCallbackClass", "itce.demo.onvif.SNBPasswordCallback");

            bus.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
           
            /////
            DeviceService service = new DeviceService();
            Device port = service.getDevicePort();
            
            List<CapabilityCategory> list = new ArrayList<CapabilityCategory>();
            list.add(CapabilityCategory.ALL);
            Capabilities cap = port.getCapabilities(list);
            
            System.out.println("cap.Device.XAddr = " + cap.getDevice().getXAddr());
            /////
            
            if (port instanceof Closeable) {
                ((Closeable)port).close();
            }

            bus.shutdown(true);

        } catch (UndeclaredThrowableException ex) {
            ex.getUndeclaredThrowable().printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    
    //GetScopes()
    private static void testGetScopes(){
    	try {

            SpringBusFactory bf = new SpringBusFactory();
            URL busFile = Client.class.getResource("wssec.xml");
            Bus bus = bf.createBus(busFile.toString());
            BusFactory.setDefaultBus(bus);

            Map<String, Object> outProps = new HashMap<String, Object>();
            outProps.put("action", "UsernameToken Timestamp");

            outProps.put("passwordType", "PasswordDigest");
            outProps.put("user", "admin");
            outProps.put("passwordCallbackClass", "itce.demo.onvif.SNBPasswordCallback");

            bus.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
           
            /////
            DeviceService service = new DeviceService();
            Device port = service.getDevicePort();
            
            List<Scope> scopes = port.getScopes();
            
            System.out.println("cap.Device.XAddr = " + scopes.size());
            /////
            
            if (port instanceof Closeable) {
                ((Closeable)port).close();
            }

            bus.shutdown(true);

        } catch (UndeclaredThrowableException ex) {
            ex.getUndeclaredThrowable().printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    
    //GetImagingSettings()
    private static void testGetImagingSettings(){
    	try {

            SpringBusFactory bf = new SpringBusFactory();
            URL busFile = Client.class.getResource("wssec.xml");
            Bus bus = bf.createBus(busFile.toString());
            BusFactory.setDefaultBus(bus);

            Map<String, Object> outProps = new HashMap<String, Object>();
            outProps.put("action", "UsernameToken Timestamp");

            outProps.put("passwordType", "PasswordDigest");
            outProps.put("user", "admin");
            outProps.put("passwordCallbackClass", "itce.demo.onvif.SNBPasswordCallback");

            bus.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
           
            /////
            ImagingService service = new ImagingService();
            ImagingPort port = service.getImagingPort();
            
            ImagingSettings20 igmSettings = port.getImagingSettings("bd449516-4c96-44a6-a163-3ad5981c09fd");
            
            System.out.println("igmSettings.whiteBalance.cbGain = " + igmSettings.getWhiteBalance().getCbGain() );
            /////
            
            if (port instanceof Closeable) {
                ((Closeable)port).close();
            }

            bus.shutdown(true);

        } catch (UndeclaredThrowableException ex) {
            ex.getUndeclaredThrowable().printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
