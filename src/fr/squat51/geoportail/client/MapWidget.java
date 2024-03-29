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
package fr.squat51.geoportail.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class MapWidget extends ResizeComposite {
    private static class MapPanel extends FlowPanel implements RequiresResize {
        /*
        public void addVirtual(Widget w) {
          w.removeFromParent();
          getChildren().add(w);
          adopt(w);
        }
         */
        public void onResize() {
            for (Widget child : getChildren()) {
                if (child instanceof RequiresResize) {
                    ((RequiresResize) child).onResize();
                }
            }
        }
    }
    
    private final MapPanel mapContainer = new MapPanel();
    private Map map;
    //these are temporary saves, to pass values between MapWidget() and onLoad()
    private final LatLng center;
    private final Integer zoomLevel;

    public MapWidget(LatLng center, Integer zoomLevel) {
        this.center = center;
        this.zoomLevel = zoomLevel;
        initWidget(mapContainer);
    }
    
    public MapWidget() {
        this(new LatLng(5.72, 45.192), 13);
    }
    
    @Override
    public void onLoad() {
        //TODO, Geoportal needs the DOM element to be inserted, otherwise wont' work..
        if (map == null) {
            map = new Map(mapContainer.getElement(), center, zoomLevel);

            //defer recalculation of intial size, so it works for fixed sizes and full-element sizes 
            Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                @Override
                public void execute() {
                    map.resize();
                }
            });
        }
    }

    @Override
    public void onResize() {
        map.resize();
    }
    
    @Override
    public void setSize(String height, String width) {
        mapContainer.setSize(height, width);
    }

    @Override
    public void setHeight(String height) {
        mapContainer.setHeight(height);
    }

    @Override
    public void setWidth(String width) {
        mapContainer.setHeight(width);
    }

    public Map getMap() {
        //if (map == null)
        return map;
    }
}
