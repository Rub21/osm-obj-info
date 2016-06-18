package org.openstreetmap.josm.plugins.osmobjinfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openstreetmap.josm.actions.AutoScaleAction;
import org.openstreetmap.josm.data.SelectionChangedListener;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
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
    protected JLabel lnLinkUser;
    protected JLabel lbLinnkIdobj;
    protected JLabel jbLinkIdChangeset;

    String typeObj = "way";

    public OSMObjInfotDialog() {
        super(tr("OpenStreetMap obj info"),
                "iconosmobjid",
                tr("Open OpenStreetMap obj info window"),
                Shortcut.registerShortcut("osmObjInfo", tr("Toggle: {0}", tr("OpenStreetMap obj info")), KeyEvent.VK_I, Shortcut.ALT_CTRL_SHIFT), 70);

        JPanel valuePanel = new JPanel(new GridLayout(0, 2));
        valuePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        //User
        valuePanel.add(new JLabel(tr("User")));
        lbUser = new JLabel();
        lnLinkUser = new JLabel(ImageProvider.get("dialogs", "link.png"));
        lbUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lnLinkUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbUser.setForeground(Color.BLUE);
        lbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoFunctions.selectbyUser(lbUser.getText().toString());
            }
        });
        lnLinkUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OpenBrowser.displayUrl("http://www.openstreetmap.org/user/" + lbUser.getText());
            }
        });

        JPanel jpuser = new JPanel(new BorderLayout());
        jpuser.add(lbUser, BorderLayout.LINE_START);
        jpuser.add(lnLinkUser, BorderLayout.LINE_END);
        valuePanel.add(jpuser);

        //obj id
        valuePanel.add(new JLabel(tr("Object Id")));
        lbIdobj = new JLabel();

        lbLinnkIdobj = new JLabel(ImageProvider.get("dialogs", "link.png"));
        lbLinnkIdobj.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbLinnkIdobj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OpenBrowser.displayUrl("https://www.openstreetmap.org/" + typeObj + "/" + lbIdobj.getText());
            }
        });

        JPanel jpIdobj = new JPanel(new BorderLayout());
        jpIdobj.add(lbIdobj, BorderLayout.LINE_START);
        jpIdobj.add(lbLinnkIdobj, BorderLayout.LINE_END);
        valuePanel.add(jpIdobj);

        //version
        valuePanel.add(new JLabel(tr("Version")));
        lbVersion = new JLabel();
        valuePanel.add(lbVersion);

        valuePanel.add(new JLabel(tr("Date")));
        lbTimestamp = new JLabel();
        valuePanel.add(lbTimestamp);

        //Changeset
        valuePanel.add(new JLabel(tr("ChangeSet")));
        lbIdChangeset = new JLabel();
        lbIdChangeset.setForeground(Color.BLUE);
        lbIdChangeset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbIdChangeset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idchangeset = Integer.parseInt(lbIdChangeset.getText().toString());
                OSMObjInfoFunctions.selectbyChangesetId(idchangeset);
            }
        });

        jbLinkIdChangeset = new JLabel(ImageProvider.get("dialogs", "link.png"));
        jbLinkIdChangeset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jbLinkIdChangeset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OpenBrowser.displayUrl("https://www.openstreetmap.org/changeset/" + lbIdChangeset.getText());
            }
        });

        JPanel jpIdchange = new JPanel(new BorderLayout());
        jpIdchange.add(lbIdChangeset, BorderLayout.LINE_START);
        jpIdchange.add(jbLinkIdChangeset, BorderLayout.LINE_END);
        valuePanel.add(jpIdchange);

        this.setPreferredSize(new Dimension(0, 50));

        createLayout(valuePanel, false, Arrays.asList(new SideButton[]{}));
        DataSet.addSelectionListener(this);
    }

    @Override
    public void selectionChanged(Collection<? extends OsmPrimitive> selection) {
        String user = "";
        String version = "";
        String idObject = "";
        String timestamp = "";
        String idchangeset = "";
        for (OsmPrimitive element : selection) {
            typeObj = element.getType().toString();
            user = element.getUser().getName();
            idObject = String.valueOf(element.getId());
            version = element.getVersion() + "";
            timestamp = new SimpleDateFormat("yyyy/MM/dd hh:mm a").format(element.getTimestamp().getTime());
            idchangeset = String.valueOf(element.getChangesetId());
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
