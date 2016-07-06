package org.openstreetmap.josm.plugins.osmobjinfo;

import java.awt.GraphicsEnvironment;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;

/**
 *
 * @author ruben
 */
public class OSMObjInfoPlugin extends Plugin {

    protected static OSMObjInfotDialog objInfotDialog;

    public OSMObjInfoPlugin(PluginInformation info) {
        super(info);
    }

    @Override
    public void mapFrameInitialized(MapFrame oldFrame, MapFrame newFrame) {
        if (newFrame != null && !GraphicsEnvironment.isHeadless()) {
            newFrame.addToggleDialog(objInfotDialog = new OSMObjInfotDialog());
        }
    }
}
