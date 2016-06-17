/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openstreetmap.josm.plugins.osmobjinfo;

import java.util.HashSet;
import java.util.Set;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.AutoScaleAction;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.gui.layer.OsmDataLayer;

/**
 *
 * @author ruben
 */
public class OSMObjInfoFunctions {
    public static void selectbyUser(String user) {
        OsmDataLayer layer = Main.main.getEditLayer();
        Set<OsmPrimitive> omsobj_list = new HashSet<>();
        for (OsmPrimitive obj : layer.data.allPrimitives()) {
            if (obj.getUser().getName().equals(user)) {
                omsobj_list.add(obj);
            }
        }
        layer.data.setSelected(omsobj_list);
        AutoScaleAction.zoomToSelection();
    }
}
