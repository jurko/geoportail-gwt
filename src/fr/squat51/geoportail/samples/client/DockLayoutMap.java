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
package fr.squat51.geoportail.samples.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import fr.squat51.geoportail.client.LatLng;
import fr.squat51.geoportail.client.MapWidget;
import fr.squat51.geoportail.client.Maps;

public class DockLayoutMap implements EntryPoint {
    @Override
    public void onModuleLoad() {
        Maps.loadApi("YOUR_KEY", "1.3", new Runnable() {
            @Override
            public void run() {
                onMapLoad();
            }
        });
    }
    
    public void onMapLoad() {
        final LatLng center = new LatLng(45.192, 5.72);
        final MapWidget mapGeoportal = new MapWidget(center, 13);
        
        DockLayoutPanel panel = new DockLayoutPanel(Unit.EM);
        panel.addNorth(new Label("header"), 3);
        panel.addSouth(new Label("footer"), 1);
        panel.addWest(new Label("menu"), 10);
        panel.addEast(new HTML(), 1);
        panel.add(mapGeoportal);
        RootLayoutPanel.get().add(panel);
    }
}
