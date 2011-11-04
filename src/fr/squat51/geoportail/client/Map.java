/*
 * Copyright (c) 2011, Juraj Polakovic
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
/*
 * Copyright (c) 2008-2010, Institut Géographique National France
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *    - Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 *    - Neither the name of the Institut Géographique National nor the names of its
 *      contributors may be used to endorse or promote products derived from this
 *      software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package fr.squat51.geoportail.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

public class Map {
    JavaScriptObject map;
    public Map(Element e, LatLng center, Integer zoomLevel) {
        e.setId("geomap");
        map = Map.create(center.obj, zoomLevel);
    }
    
    private static final native JavaScriptObject create(JavaScriptObject center, int zoomLevel) /*-{
        var map = new $wnd.Geoportal.Viewer.Default(
                   "geomap",
                   $wnd.OpenLayers.Util.extend({ 
                        mode:'normal',
                        territory: 'FXX',
                        projection: 'IGNF:GEOPORTALFXX',
                        displayProjection:'IGNF:LAMB93',
                        //displayProjection: ['IGNF:RGF93G', 'IGNF:LAMB93', 'IGNF:LAMBE'],
                        nameInstance: 'map'
                        }, $wnd.gGEOPORTALRIGHTSMANAGEMENT)
                   );
        if (!map) {
            return null;
        }
        map.addGeoportalLayer('GEOGRAPHICALGRIDSYSTEMS.MAPS:WMSC', {visibility:true, opacity:1});
        map.addGeoportalLayer('ORTHOIMAGERY.ORTHOPHOTOS:WMSC', {opacity: 1, visibility: false});
        map.setInformationPanelVisibility(false);
        map.openLayersPanel(false);
        map.openToolsPanel(false);
        
        map.getMap().setCenterAtLonLat(center.lon, center.lat, zoomLevel);
        return map;
    }-*/;

    public void resize() {
        final Integer height = DOM.getElementById("geomap").getParentElement().getClientHeight();
        final Integer width =  DOM.getElementById("geomap").getParentElement().getClientWidth();
        resize(map, height, width);
    }
    
    private final native void resize(JavaScriptObject map, int height, int width) /*-{
        map.setSize(width, height);
    }-*/;
    
    public void setCenter(LatLng center) {
        setCenter(map, center.obj);
    }
    
    private static final native void setCenter(JavaScriptObject map, JavaScriptObject center) /*-{
        map.getMap().setCenterAtLonLat(center.lon, center.lat, 12);
    }-*/;
    
    public GpxLayer addGpxLayer(String name, String url) {
        JavaScriptObject obj = addGpxLayer(map, name, url);
        return new GpxLayer(obj);
    }
    
    private static final native JavaScriptObject addGpxLayer(JavaScriptObject map, String name, String url) /*-{
        return map.getMap().addLayer("GPX", name, url,
                {format: $wnd.OpenLayers.Format.GPX, visibility:true,
                 style: {strokeColor: "blue", strokeWidth: 3}});
    }-*/;
    
    public void removeLayer(Layer layer) {
        removeLayer(map, layer.obj);
    }
    
    private static final native void removeLayer(JavaScriptObject map, JavaScriptObject layer) /*-{
        map.getMap().removeLayer(layer);
    }-*/;
    
    public void render() {
        render(map);
    }
    
    //TODO, not the best way to achieve, probably renders twice, once at construct and second here
    private final native void render(JavaScriptObject map) /*-{
        map.render('geomap');
    }-*/;
}
