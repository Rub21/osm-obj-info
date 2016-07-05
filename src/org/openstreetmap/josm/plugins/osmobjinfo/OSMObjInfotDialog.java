package org.openstreetmap.josm.plugins.osmobjinfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagLayout;
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

    protected JLabel lbNeisUser;
    protected JLabel lbChangesetMap;
    protected JLabel lbOsmDeepHistory;
    protected JLabel lbUserOsmComments;

    String typeObj = "way";

    public OSMObjInfotDialog() {
        super(tr("OpenStreetMap obj info"),
                "iconosmobjid",
                tr("Open OpenStreetMap obj info window"),
                Shortcut.registerShortcut("osmObjInfo", tr("Toggle: {0}", tr("OpenStreetMap obj info")), KeyEvent.VK_I, Shortcut.ALT_CTRL_SHIFT), 50);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        //user
        panel.add(new JLabel(tr("User")));
        panel.add(buildUser());
        //changeset
        panel.add(new JLabel(tr("Changeset")));
        panel.add(buildChangeset());
        //obj id
        panel.add(new JLabel(tr("Object Id")));
        panel.add(buildidObject());
        //version
        panel.add(new JLabel(tr("Version")));
        lbVersion = new JLabel();
        panel.add(lbVersion);
        //date
        panel.add(new JLabel(tr("Date")));
        lbTimestamp = new JLabel();
        panel.add(lbTimestamp);

        createLayout(panel, false, Arrays.asList(new SideButton[]{}));
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

    private JPanel buildUser() {
        //OSM USER
        JPanel jpUser = new JPanel(new BorderLayout());

        lbUser = new JLabel();
        lbLinkUser = new JLabel(ImageProvider.get("dialogs", "link.png"));
        lbCopyUser = new JLabel(ImageProvider.get("dialogs", "copy.png"));
        lbNeisUser = new JLabel(ImageProvider.get("dialogs", "copy.png"));
        lbUserOsmComments = new JLabel(ImageProvider.get("dialogs", "copy.png"));

        lbUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbLinkUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbCopyUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbNeisUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbUserOsmComments.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lbUser.setForeground(Color.BLUE);

        JPanel jpuseroptions = new JPanel(new GridLayout(1, 4, 5, 5));
        jpuseroptions.add(lbCopyUser);
        jpuseroptions.add(lbNeisUser);
        jpuseroptions.add(lbUserOsmComments);
        jpuseroptions.add(lbLinkUser);

        //add
        jpUser.add(lbUser, BorderLayout.LINE_START);
        jpUser.add(jpuseroptions, BorderLayout.LINE_END);
        //User Actions
        lbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoFunctions.selectbyUser(lbUser.getText());
            }
        });

        lbLinkUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.openinBrowserUser(lbUser.getText());
            }
        });

        lbCopyUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.copyUser(lbUser.getText());
            }
        });

        lbNeisUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.openinBrowserUserNeis(lbUser.getText());
            }
        });

        lbUserOsmComments.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.openinBrowserUserOsmComments(lbUser.getText());
            }
        });
        return jpUser;
    }

    private JPanel buildChangeset() {

        //CHANGESET
        JPanel jpChangeset = new JPanel(new BorderLayout());
        //add
        lbIdChangeset = new JLabel();
        lbLinkIdChangeset = new JLabel(ImageProvider.get("dialogs", "link.png"));
        lbCopyIdChangeset = new JLabel(ImageProvider.get("dialogs", "copy.png"));
        lbChangesetMap = new JLabel(ImageProvider.get("dialogs", "copy.png"));

        lbIdChangeset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbLinkIdChangeset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbCopyIdChangeset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbChangesetMap.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lbIdChangeset.setForeground(Color.BLUE);

        JPanel jpchOptions = new JPanel(new GridLayout(1, 3, 5, 5));

        jpchOptions.add(lbCopyIdChangeset);
        jpchOptions.add(lbChangesetMap);
        jpchOptions.add(lbLinkIdChangeset);

        //add
        jpChangeset.add(lbIdChangeset, BorderLayout.LINE_START);
        jpChangeset.add(jpchOptions, BorderLayout.LINE_END);

        //Changeset actions
        lbIdChangeset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idchangeset = Integer.parseInt(lbIdChangeset.getText());
                OSMObjInfoFunctions.selectbyChangesetId(idchangeset);
            }
        });
        lbLinkIdChangeset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.openinBrowserChangeset(lbIdChangeset.getText());
            }
        });
        lbCopyIdChangeset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.copyChangeset(lbIdChangeset.getText());
            }
        });
        lbChangesetMap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.openinBrowserChangesetMap(lbIdChangeset.getText());
            }
        });

        return jpChangeset;
    }

    private JPanel buildidObject() {

        //OBJ ID 
        JPanel jpIdobj = new JPanel(new BorderLayout());

        lbIdobj = new JLabel();
        lbLinnkIdobj = new JLabel(ImageProvider.get("dialogs", "link.png"));
        lbCopyIdobj = new JLabel(ImageProvider.get("dialogs", "copy.png"));
        lbOsmDeepHistory = new JLabel(ImageProvider.get("dialogs", "copy.png"));

        lbLinnkIdobj.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbCopyIdobj.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbOsmDeepHistory.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel jpIdobjOptions = new JPanel(new GridLayout(1, 3, 5, 5));
        jpIdobjOptions.add(lbCopyIdobj);
        jpIdobjOptions.add(lbOsmDeepHistory);
        jpIdobjOptions.add(lbLinnkIdobj);
        //add
        jpIdobj.add(lbIdobj, BorderLayout.LINE_START);
        jpIdobj.add(jpIdobjOptions, BorderLayout.LINE_END);

        //id obj actions 
        lbLinnkIdobj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.openinBrowserIdobj(typeObj, lbIdobj.getText());
            }
        });
        lbCopyIdobj.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.copyIdobj(typeObj, lbIdobj.getText());
            }
        });

        lbOsmDeepHistory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                OSMObjInfoActions.openinBrowserIdobjOsmDeepHistory(typeObj, lbIdobj.getText());
            }
        });
        return jpIdobj;
    }
}
