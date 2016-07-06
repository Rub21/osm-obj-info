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

    public static void selectbyUser(final String user) {
        OsmDataLayer layer = Main.main.getEditLayer();
        Set<OsmPrimitive> omsobj_list = new HashSet<>();
        for (OsmPrimitive obj : layer.data.allPrimitives()) {
            try {
                if (obj.getUser().getName().equals(user)) {
                    omsobj_list.add(obj);
                }
            } catch (NullPointerException e) {
            }
        }
        layer.data.setSelected(omsobj_list);
        AutoScaleAction.zoomToSelection();
    }

    public static void selectbyChangesetId(int changesetId) {
        OsmDataLayer layer = Main.main.getEditLayer();
        Set<OsmPrimitive> omsobj_list = new HashSet<>();
        for (OsmPrimitive obj : layer.data.allPrimitives()) {
            try {
                if (obj.getChangesetId() == changesetId) {
                    omsobj_list.add(obj);
                }
            } catch (NullPointerException e) {
            }
        }
        layer.data.setSelected(omsobj_list);
        AutoScaleAction.zoomToSelection();
    }
}
