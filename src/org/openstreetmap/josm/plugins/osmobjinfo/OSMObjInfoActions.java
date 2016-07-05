/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openstreetmap.josm.plugins.osmobjinfo;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import org.openstreetmap.josm.tools.OpenBrowser;

/**
 *
 * @author ruben
 */
public class OSMObjInfoActions {

    public static void copyUser(String user) {
        String linkUser = "http://www.openstreetmap.org/user/" + user;
        StringSelection selection = new StringSelection(linkUser);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static void openinBrowserUser(String user) {
        OpenBrowser.displayUrl("http://www.openstreetmap.org/user/" + user);
    }

    public static void openinBrowserUserNeis(String user) {
        OpenBrowser.displayUrl("http://hdyc.neis-one.org/?" + user);
    }

    public static void copyChangeset(String idChangeset) {
        String linkchangeset = "https://www.openstreetmap.org/changeset/" + idChangeset;
        StringSelection selection = new StringSelection(linkchangeset);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static void openinBrowserChangeset(String idChangeset) {
        OpenBrowser.displayUrl("https://www.openstreetmap.org/changeset/" + idChangeset);
    }

    public static void openinBrowserChangesetMap(String idChangeset) {
        OpenBrowser.displayUrl("http://osmlab.github.io/changeset-map/#" + idChangeset);
    }

    public static void copyIdobj(String typeObj, String idobj) {
        String linkobjid = "https://www.openstreetmap.org/" + typeObj + "/" + idobj;
        StringSelection selection = new StringSelection(linkobjid);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static void openinBrowserIdobj(String typeObj, String idobj) {
        OpenBrowser.displayUrl("https://www.openstreetmap.org/" + typeObj + "/" + idobj);
    }

    public static void openinBrowserIdobjOsmDeepHistory(String typeObj, String idobj) {
        OpenBrowser.displayUrl("http://osmlab.github.io/osm-deep-history/#/" + typeObj + "/" + idobj);

    }
}
