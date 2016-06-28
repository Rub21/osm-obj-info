package org.openstreetmap.josm.plugins.osmobjinfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import static org.openstreetmap.josm.tools.I18n.tr;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openstreetmap.josm.data.SelectionChangedListener;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.gui.JosmUserIdentityManager;
import org.openstreetmap.josm.gui.SideButton;
import org.openstreetmap.josm.gui.dialogs.ToggleDialog;
import org.openstreetmap.josm.gui.util.GuiHelper;
import org.openstreetmap.josm.tools.ImageProvider;
import org.openstreetmap.josm.tools.OpenBrowser;
import org.openstreetmap.josm.tools.Shortcut;

/**
 *
 * @author ruben
 */
public class OSMObjInfotDialog extends ToggleDialog implements SelectionChangedListener {

    protected JLabel lbUser;
    protected JLabel lbVersion;
    protected JLabel lbIdobj;
    protected JLabel lbTimestamp;
    protected JLabel lbIdChangeset;
    protected JLabel lbLinkUser;
    protected JLabel lbLinnkIdobj;
    protected JLabel lbLinkIdChangeset;
    protected JLabel lbCopyUser;
    protected JLabel lbCopyIdobj;
    protected JLabel lbCopyIdChangeset;

    String typeObj = "way";

    public OSMObjInfotDialog() {
        super(tr("OpenStreetMap obj info"),
                "iconosmobjid",
                tr("Open OpenStreetMap obj info window"),
                Shortcut.registerShortcut("osmObjInfo", tr("Toggle: {0}", tr("OpenStreetMap obj info")), KeyEvent.VK_I, Shortcut.ALT_CTRL_SHIFT), 50);

        JPanel valuePanel = new JPanel(new GridLayout(0, 2));
        valuePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        //User
        valuePanel.add(new JLabel(tr("User")));
        lbUser = new JLabel();
        lbLinkUser = new JLabel(ImageProvider.get("dialogs", "link.png"));
        lbCopyUser = new JLabel(ImageProvider.get("dialogs", "copy.png"));
        lbUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbLinkUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbCopyUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbUser.setForeground(Color.BLUE);
        lbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoFunctions.selectbyUser(lbUser.getText());
            }
        });
        lbLinkUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OpenBrowser.displayUrl("http://www.openstreetmap.org/user/" + lbUser.getText());
            }
        });
        lbCopyUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String linkUser = "http://www.openstreetmap.org/user/" + lbUser.getText();
                StringSelection selection = new StringSelection(linkUser);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });
        JPanel jpuser = new JPanel(new BorderLayout());
        jpuser.add(lbUser, BorderLayout.LINE_START);
        JPanel jpu = new JPanel(new BorderLayout(5, 5));
        jpu.add(lbCopyUser, BorderLayout.LINE_START);
        jpu.add(lbLinkUser, BorderLayout.LINE_END);
        jpuser.add(jpu, BorderLayout.LINE_END);
        valuePanel.add(jpuser);

        //Changeset
        valuePanel.add(new JLabel(tr("Changeset")));
        lbIdChangeset = new JLabel();
        lbIdChangeset.setForeground(Color.BLUE);
        lbIdChangeset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbIdChangeset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idchangeset = Integer.parseInt(lbIdChangeset.getText());
                OSMObjInfoFunctions.selectbyChangesetId(idchangeset);
            }
        });

        lbLinkIdChangeset = new JLabel(ImageProvider.get("dialogs", "link.png"));
        lbCopyIdChangeset = new JLabel(ImageProvider.get("dialogs", "copy.png"));
        lbLinkIdChangeset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbCopyIdChangeset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbLinkIdChangeset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OpenBrowser.displayUrl("https://www.openstreetmap.org/changeset/" + lbIdChangeset.getText());
            }
        });
        lbCopyIdChangeset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String linkchangeset = "https://www.openstreetmap.org/changeset/" + lbIdChangeset.getText();
                StringSelection selection = new StringSelection(linkchangeset);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });

        JPanel jpIdchange = new JPanel(new BorderLayout());
        jpIdchange.add(lbIdChangeset, BorderLayout.LINE_START);
        JPanel jpch = new JPanel(new BorderLayout(5, 5));
        jpch.add(lbCopyIdChangeset, BorderLayout.LINE_START);
        jpch.add(lbLinkIdChangeset, BorderLayout.LINE_END);
        jpIdchange.add(jpch, BorderLayout.LINE_END);
        valuePanel.add(jpIdchange);

        //obj id
        valuePanel.add(new JLabel(tr("Object Id")));
        lbIdobj = new JLabel();
        lbLinnkIdobj = new JLabel(ImageProvider.get("dialogs", "link.png"));
        lbCopyIdobj = new JLabel(ImageProvider.get("dialogs", "copy.png"));
        lbLinnkIdobj.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbCopyIdobj.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbLinnkIdobj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OpenBrowser.displayUrl("https://www.openstreetmap.org/" + typeObj + "/" + lbIdobj.getText());
            }
        });
        lbCopyIdobj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String linkobjid = "https://www.openstreetmap.org/" + typeObj + "/" + lbIdobj.getText();
                StringSelection selection = new StringSelection(linkobjid);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });
        JPanel jpIdobj = new JPanel(new BorderLayout());
        jpIdobj.add(lbIdobj, BorderLayout.LINE_START);
        JPanel jpob = new JPanel(new BorderLayout(5, 5));
        jpob.add(lbCopyIdobj, BorderLayout.LINE_START);
        jpob.add(lbLinnkIdobj, BorderLayout.LINE_END);
        jpIdobj.add(jpob, BorderLayout.LINE_END);
        valuePanel.add(jpIdobj);

        //version
        valuePanel.add(new JLabel(tr("Version")));
        lbVersion = new JLabel();
        valuePanel.add(lbVersion);
        valuePanel.add(new JLabel(tr("Date")));
        lbTimestamp = new JLabel();
        valuePanel.add(lbTimestamp);
        createLayout(valuePanel, false, Arrays.asList(new SideButton[]{}));
        DataSet.addSelectionListener(this);
    }

    @Override
    public void selectionChanged(Collection<? extends OsmPrimitive> selection) {
        if (selection.size() < 2) {
            String user = "";
            String version = "";
            String idObject = "";
            String timestamp = "";
            String idchangeset = "";
            for (OsmPrimitive element : selection) {
                if (!element.isNew()) {
                    typeObj = element.getType().toString();
                    try {
                        user = element.getUser().getName();
                        timestamp = new SimpleDateFormat("yyyy/MM/dd hh:mm a").format(element.getTimestamp().getTime());
                    } catch (NullPointerException e) {
                        user = JosmUserIdentityManager.getInstance().getUserName();
                    }
                    idObject = String.valueOf(element.getId());
                    version = String.valueOf(element.getVersion());
                    idchangeset = String.valueOf(element.getChangesetId());
                }
            }

            final String txtUser = user;
            final String txtVersion = version;
            final String txtIdobject = idObject;
            final String txtTimestamp = timestamp;
            final String txtIdChangeset = idchangeset;

            GuiHelper.runInEDT(new Runnable() {
                @Override
                public void run() {
                    lbUser.setText(txtUser);
                    lbIdChangeset.setText(txtIdChangeset);
                    lbIdobj.setText(txtIdobject);
                    lbVersion.setText(txtVersion);
                    lbTimestamp.setText(txtTimestamp);
                }
            });
        }
    }
}
