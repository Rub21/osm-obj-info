package org.openstreetmap.josm.plugins.osmobjinfo;

import java.awt.Color;
import static org.openstreetmap.josm.tools.I18n.tr;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collection;
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
import org.openstreetmap.josm.tools.Shortcut;

/**
 *
 * @author ruben
 */
public class OSMObjInfotDialog extends ToggleDialog implements SelectionChangedListener {

    protected JLabel lbUser;
    protected JLabel lbVersion;
    protected JLabel lbTimestamp;
    protected JLabel lbIdChangeset;
    protected JButton jbUser;
    protected JButton jbVersion;
    protected JButton jbTimestamp;
    protected JButton jbIdChangeset;

    protected boolean isclick = true;

    public OSMObjInfotDialog() {
        super(tr("OpenStreetMap obj info"),
                "iconfindchangeset",
                tr("Open OpenStreetMap obj info window"),
                Shortcut.registerShortcut("osmObjInfo", tr("Toggle: {0}", tr("OpenStreetMap obj info")), KeyEvent.VK_I, Shortcut.ALT_CTRL_SHIFT), 70);

        JPanel valuePanel = new JPanel(new GridLayout(0, 2));

        valuePanel.add(new JLabel(tr("User")));
        lbUser = new JLabel();
        valuePanel.add(lbUser);
//        lbUser.setForeground(new Color(89, 139, 214));
        lbUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                String user = lbUser.getText();
//                isclick = false;

                OSMObjInfoFunctions.selectbyUser( lbUser.getText().toString());

//                                isclick = true;

            }
        });

        valuePanel.add(new JLabel(tr("Version")));
        lbVersion = new JLabel();
        valuePanel.add(lbVersion);

        valuePanel.add(new JLabel(tr("Date")));
        lbTimestamp = new JLabel();
        valuePanel.add(lbTimestamp);

        valuePanel.add(new JLabel(tr("ChangeSet")));
        lbIdChangeset = new JLabel();
        valuePanel.add(lbIdChangeset);

        lbIdChangeset.setForeground(Color.BLUE);
        lbIdChangeset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbIdChangeset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idchangeset = Integer.parseInt(lbIdChangeset.getText().toString());
                OSMObjInfoFunctions.selectbyChangesetId(idchangeset);
//                try {
//
//            Thread.sleep(3000);
//                        isclick=true;
//
//        } catch (InterruptedException ex) {
//
//        }
            }
        });

        this.setPreferredSize(new Dimension(0, 50));

        createLayout(valuePanel, false, Arrays.asList(new SideButton[]{}));
        DataSet.addSelectionListener(this);
    }

    @Override
    public void selectionChanged(Collection<? extends OsmPrimitive> selection) {
        if (isclick) {
            String user = "";
            String version = "";
            String timestamp = "";
            String idchangeset = "";
            for (OsmPrimitive element : selection) {
                user = element.getUser().getName();
                version = element.getVersion() + "";
                timestamp = element.getTimestamp().toString();
                idchangeset = element.getChangesetId() + "";
            }

            final String txtUser = user;
            final String txtVersion = version;
            final String txtTimestamp = timestamp;
            final String txtIdChangeset = idchangeset;

            GuiHelper.runInEDT(new Runnable() {
                @Override
                public void run() {
                    lbUser.setText(txtUser);
                    lbIdChangeset.setText(txtIdChangeset);
                    lbVersion.setText(txtVersion);
                    lbTimestamp.setText(txtTimestamp);
                }
            });
        }

    }

}
