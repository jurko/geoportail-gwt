/*
 * Copyright (c) 2011 - 2012, Juraj Polakovic
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package fr.squat51.geoportail.client;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;

public class Maps {
    public class MapsScheduledCommand implements RepeatingCommand {
        private final Runnable onLoad;
        public MapsScheduledCommand(Runnable onLoad) {
            this.onLoad = onLoad;
        }
        @Override
        public boolean execute() {
            if (Maps.checkLoaded()) {
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    @Override
                    public void execute() {
                        onLoad.run();
                    }
                });
                return false;
            } else {
                return true;
            }
        }
    }

    public static Maps newInstance() {
        return new Maps();
    }
    
    public MapsScheduledCommand checkLoad(Runnable onLoad) {
        return new MapsScheduledCommand(onLoad);
    }

    public static void loadApi(String key, String version, Runnable onLoad) {
        ScriptElement license = Document.get().createScriptElement();
        license.setSrc("http://api.ign.fr/geoportail/api?key=" + key + "&amp;");
        license.setLang("javascript");
        license.setType("text/javascript");
        Document.get().getBody().appendChild(license);

        ScriptElement script = Document.get().createScriptElement();
        script.setSrc("http://api.ign.fr/geoportail/api/js/" + version + "/Geoportal.js");
        script.setLang("javascript");
        script.setType("text/javascript");
        Document.get().getBody().appendChild(script);
        
        //TODO, enforce singleton state?
        Scheduler.get().scheduleFixedDelay(Maps.newInstance().checkLoad(onLoad), 250);
    }
    
    static final native boolean checkLoaded() /*-{
        if (typeof($wnd.OpenLayers)=='undefined'              ||
            typeof($wnd.Geoportal)=='undefined'               ||
            typeof($wnd.Geoportal.Viewer)=='undefined'        ||
            typeof($wnd.Geoportal.Viewer.Default)=='undefined')
            return false;
        else
            return true;
    }-*/;
    
//    public static final native String test() /*-{
//        if (typeof($wnd.foo)==='undefined')
//            return null;
//        return $wnd.foo;
//    }-*/;
}
