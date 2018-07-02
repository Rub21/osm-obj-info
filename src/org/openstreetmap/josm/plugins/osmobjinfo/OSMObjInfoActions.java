package org.openstreetmap.josm.plugins.osmobjinfo;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.JOptionPane;
import org.openstreetmap.josm.gui.Notification;
import static org.openstreetmap.josm.tools.I18n.tr;
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
        new Notification(tr("Copy: " + linkUser)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
    }

    public static void openinBrowserUser(String user) {
        String url = "http://www.openstreetmap.org/user/" + user;
        new Notification(tr("Open in browser " + url)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
        OpenBrowser.displayUrl(url);

    }

    public static void openinBrowserUserNeis(String user) {
        String url = "http://hdyc.neis-one.org/?" + user;
        new Notification(tr("Open in browser " + url)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
        OpenBrowser.displayUrl(url);

    }

    static void openinBrowserUserOsmComments(String user) {
        String url = "https://www.mapbox.com/osm-comments/#/changesets/?q=users:" + user;
        new Notification(tr("Open in browser " + url)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
        OpenBrowser.displayUrl(url);

    }

    public static void copyChangeset(String idChangeset) {
        String linkchangeset = "https://www.openstreetmap.org/changeset/" + idChangeset;
        StringSelection selection = new StringSelection(linkchangeset);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        new Notification(tr("Copy: " + linkchangeset)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
    }

    public static void openinBrowserChangeset(String idChangeset) {
        String url = "https://www.openstreetmap.org/changeset/" + idChangeset;
        new Notification(tr("Open in browser " + url)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
        OpenBrowser.displayUrl(url);
    }

    public static void openinBrowserChangesetMap(String idChangeset) {
        String url = "https://osmlab.github.io/changeset-map/#" + idChangeset;
        new Notification(tr("Open in browser " + url)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
        OpenBrowser.displayUrl(url);

    }

    public static void copyIdobj(String typeObj, String idobj) {
        String linkobjid = "https://www.openstreetmap.org/" + typeObj + "/" + idobj;
        StringSelection selection = new StringSelection(linkobjid);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        new Notification(tr("Copy: " + linkobjid)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
    }

    public static void openinBrowserIdobj(String typeObj, String idobj) {
        String url = "https://www.openstreetmap.org/" + typeObj + "/" + idobj;
        new Notification(tr("Open in browser " + url)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
        OpenBrowser.displayUrl(url);

    }

    public static void openinBrowserIdobjOsmDeepHistory(String typeObj, String idobj) {
        String url = "https://osmlab.github.io/osm-deep-history/#/" + typeObj + "/" + idobj;
        new Notification(tr("Open in browser " + url)).setIcon(JOptionPane.INFORMATION_MESSAGE).setDuration(Notification.TIME_SHORT).show();
        OpenBrowser.displayUrl(url);

    }

}
