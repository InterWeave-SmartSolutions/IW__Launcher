/*
 * Decompiled with CFR.
 */
package com.inerweave.sdk.composites;

import com.inerweave.sdk.ConfigContext;
import com.inerweave.sdk.TransactionBase;
import com.inerweave.sdk.TransactionContext;
import com.inerweave.sdk.vews.iw_dialogs.PropertyDialog;
import com.inerweave.sdk.vews.iw_dialogs.TransactionFlowDescription;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;
import iw_sdk.Iw_sdkPlugin;
import java.sql.Timestamp;
import java.util.Hashtable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class NewTransactionFlowWizardComposite
extends Composite {
    private Text innerSycles;
    private CCombo solution;
    private Button statefulFlow;
    private MenuItem pasteMenuItem;
    private Button activeFlow;
    private Text secondaryTsUrl;
    private CLabel primaryTsLabel;
    private Text primaryTsUrl;
    private Button hostedPublic;
    private CLabel publicLabel;
    private CLabel shiftLabel;
    private CLabel dayLabel;
    private TableColumn newColumnTableColumn_4;
    private TableColumn newColumnTableColumn_3;
    private TableColumn newColumnTableColumn_2;
    private TableColumn newColumnTableColumn_1;
    private TableColumn newColumnTableColumn;
    private CCombo timeMesure_1;
    private CCombo comboDay;
    private Button calendarButton;
    private CCombo secOfTime;
    private CCombo minOfTime;
    private CCombo hoursOfTime;
    private CCombo yearOfDate;
    private CCombo monthOfDate;
    private CCombo dayOfDate;
    private CCombo timeMesure;
    private Text numberOfExecutions;
    private Button runAtStartUp;
    private Text shift;
    private Text scheduledInterval;
    private CCombo schedulingCombo;
    private Text transactionId;
    private Table propertyTable;
    private String transactionDescription = "";

    public NewTransactionFlowWizardComposite(Composite parent, int style) {
        super(parent, style);
        this.setBackground(Display.getCurrent().getSystemColor(1));
        this.setLayout((Layout)new FormLayout());
        CLabel idLabel = new CLabel((Composite)this, 0);
        FormData formData = new FormData();
        formData.top = new FormAttachment(0, 1);
        formData.left = new FormAttachment(0, 5);
        idLabel.setLayoutData((Object)formData);
        idLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        idLabel.setText("Id:");
        this.primaryTsLabel = new CLabel((Composite)this, 0);
        this.primaryTsLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_26 = new FormData();
        formData_26.top = new FormAttachment(10, 0);
        formData_26.left = new FormAttachment((Control)idLabel, 0, 16384);
        this.primaryTsLabel.setLayoutData((Object)formData_26);
        this.primaryTsLabel.setText("Primary TS URL:");
        CLabel schedulingPatternLabel = new CLabel((Composite)this, 0);
        FormData formData_1 = new FormData();
        formData_1.top = new FormAttachment(20, 0);
        formData_1.left = new FormAttachment((Control)idLabel, 0, 16384);
        schedulingPatternLabel.setLayoutData((Object)formData_1);
        schedulingPatternLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        schedulingPatternLabel.setText("Scheduling:");
        CLabel intervalLabel = new CLabel((Composite)this, 0);
        FormData formData_2 = new FormData();
        formData_2.top = new FormAttachment(30, 0);
        formData_2.left = new FormAttachment((Control)schedulingPatternLabel, 0, 16384);
        intervalLabel.setLayoutData((Object)formData_2);
        intervalLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        intervalLabel.setText("Interval:");
        this.shiftLabel = new CLabel((Composite)this, 0);
        FormData formData_3 = new FormData();
        formData_3.bottom = new FormAttachment((Control)intervalLabel, 0, 1024);
        formData_3.top = new FormAttachment((Control)intervalLabel, -19, 1024);
        formData_3.left = new FormAttachment(45, 0);
        this.shiftLabel.setLayoutData((Object)formData_3);
        this.shiftLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.shiftLabel.setText("Delay before start:");
        CLabel runAtStartupLabel = new CLabel((Composite)this, 0);
        FormData formData_4 = new FormData();
        formData_4.top = new FormAttachment(60, 0);
        formData_4.left = new FormAttachment((Control)intervalLabel, 0, 16384);
        runAtStartupLabel.setLayoutData((Object)formData_4);
        runAtStartupLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        runAtStartupLabel.setText("Run at start-up:");
        CLabel numberOfExecutionsLabel = new CLabel((Composite)this, 0);
        FormData formData_5 = new FormData();
        formData_5.bottom = new FormAttachment((Control)runAtStartupLabel, 0, 1024);
        formData_5.top = new FormAttachment((Control)runAtStartupLabel, -19, 1024);
        formData_5.right = new FormAttachment((Control)this.shiftLabel, 115, 16384);
        formData_5.left = new FormAttachment((Control)this.shiftLabel, 0, 16384);
        numberOfExecutionsLabel.setLayoutData((Object)formData_5);
        numberOfExecutionsLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        numberOfExecutionsLabel.setText("Number of executions:");
        this.propertyTable = new Table((Composite)this, 67586);
        this.propertyTable.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDoubleClick(MouseEvent e) {
                NewTransactionFlowWizardComposite.this.propertyOperation(0);
            }
        });
        this.propertyTable.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                if (e.character == '\r') {
                    NewTransactionFlowWizardComposite.this.propertyOperation(0);
                }
            }
        });
        this.propertyTable.addControlListener((ControlListener)new ControlAdapter(){

            public void controlResized(ControlEvent e) {
                NewTransactionFlowWizardComposite.this.newColumnTableColumn.setWidth(((NewTransactionFlowWizardComposite)NewTransactionFlowWizardComposite.this).propertyTable.getSize().x / 6);
                NewTransactionFlowWizardComposite.this.newColumnTableColumn_2.setWidth(((NewTransactionFlowWizardComposite)NewTransactionFlowWizardComposite.this).propertyTable.getSize().x / 12);
                NewTransactionFlowWizardComposite.this.newColumnTableColumn_3.setWidth(((NewTransactionFlowWizardComposite)NewTransactionFlowWizardComposite.this).propertyTable.getSize().x / 12);
                NewTransactionFlowWizardComposite.this.newColumnTableColumn_4.setWidth(((NewTransactionFlowWizardComposite)NewTransactionFlowWizardComposite.this).propertyTable.getSize().x / 6);
                NewTransactionFlowWizardComposite.this.newColumnTableColumn_1.setWidth(((NewTransactionFlowWizardComposite)NewTransactionFlowWizardComposite.this).propertyTable.getSize().x - NewTransactionFlowWizardComposite.this.newColumnTableColumn.getWidth() * 3 - NewTransactionFlowWizardComposite.this.propertyTable.getBorderWidth() * 4);
            }
        });
        this.propertyTable.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_6 = new FormData();
        formData_6.right = new FormAttachment(100, 0);
        formData_6.bottom = new FormAttachment(100, 0);
        formData_6.top = new FormAttachment(70, 0);
        formData_6.left = new FormAttachment(0, 0);
        this.propertyTable.setLayoutData((Object)formData_6);
        this.propertyTable.setLinesVisible(true);
        this.propertyTable.setHeaderVisible(true);
        this.newColumnTableColumn = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn.setWidth(this.propertyTable.getSize().x / 6);
        this.newColumnTableColumn.setText("Name");
        this.newColumnTableColumn_1 = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn_1.setWidth(this.propertyTable.getSize().x - this.newColumnTableColumn.getWidth() * 3 - this.propertyTable.getBorderWidth() * 4);
        this.newColumnTableColumn_1.setText("Property Value");
        this.newColumnTableColumn_2 = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn_2.setWidth(this.propertyTable.getSize().x / 12);
        this.newColumnTableColumn_2.setText("File Upload");
        this.newColumnTableColumn_3 = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn_3.setWidth(this.propertyTable.getSize().x / 12);
        this.newColumnTableColumn_3.setText("Password");
        this.newColumnTableColumn_4 = new TableColumn(this.propertyTable, 0);
        this.newColumnTableColumn_4.setWidth(this.propertyTable.getSize().x / 6);
        this.newColumnTableColumn_4.setText("Dynamic Update");
        Menu menu = new Menu((Control)this.propertyTable);
        menu.addMenuListener((MenuListener)new MenuAdapter(){

            public void menuShown(MenuEvent e) {
                NewTransactionFlowWizardComposite.this.pasteMenuItem.setEnabled(ConfigContext.getParamsClp() != null);
            }
        });
        this.propertyTable.setMenu(menu);
        MenuItem newMenuItem = new MenuItem(menu, 0);
        newMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewTransactionFlowWizardComposite.this.propertyOperation(-1);
            }
        });
        newMenuItem.setText("New");
        MenuItem insertMenuItem = new MenuItem(menu, 0);
        insertMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewTransactionFlowWizardComposite.this.propertyOperation(NewTransactionFlowWizardComposite.this.propertyTable.getSelectionIndex() + 1);
            }
        });
        insertMenuItem.setText("Insert");
        MenuItem vieweditMenuItem = new MenuItem(menu, 0);
        vieweditMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewTransactionFlowWizardComposite.this.propertyOperation(0);
            }
        });
        vieweditMenuItem.setText("Edit");
        MenuItem menuItem = new MenuItem(menu, 2);
        menuItem.setText("Menu item");
        MenuItem cutMenuItem = new MenuItem(menu, 0);
        cutMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewTransactionFlowWizardComposite.this.cutParameters();
            }
        });
        cutMenuItem.setText("Cut");
        MenuItem copyMenuItem = new MenuItem(menu, 0);
        copyMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewTransactionFlowWizardComposite.this.copyParameters();
            }
        });
        copyMenuItem.setText("Copy");
        this.pasteMenuItem = new MenuItem(menu, 0);
        this.pasteMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewTransactionFlowWizardComposite.this.pasteParameters();
            }
        });
        this.pasteMenuItem.setEnabled(false);
        this.pasteMenuItem.setText("Paste");
        new MenuItem(menu, 2);
        MenuItem deleteMenuItem = new MenuItem(menu, 0);
        deleteMenuItem.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                NewTransactionFlowWizardComposite.this.propertyTable.remove(NewTransactionFlowWizardComposite.this.propertyTable.getSelectionIndex());
            }
        });
        deleteMenuItem.setText("Delete");
        this.transactionId = new Text((Composite)this, 2048);
        FormData formData_7 = new FormData();
        formData_7.left = new FormAttachment((Control)this.primaryTsLabel, 0, 131072);
        formData_7.right = new FormAttachment((Control)this.shiftLabel, -20, 16384);
        formData_7.bottom = new FormAttachment((Control)idLabel, 0, 1024);
        formData_7.top = new FormAttachment((Control)idLabel, 0, 128);
        this.transactionId.setLayoutData((Object)formData_7);
        this.transactionId.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.schedulingCombo = new CCombo((Composite)this, 2056);
        this.schedulingCombo.setItems(new String[]{"Single Run Only", "Single Run Configurable", "Over the Interval", "At Specific Time Daily", "At Specific Time and Day of the Week", "At Specific Time and Date"});
        this.schedulingCombo.addSelectionListener((SelectionListener)new SelectionAdapter(){

            public void widgetSelected(SelectionEvent e) {
                int si = NewTransactionFlowWizardComposite.this.schedulingCombo.getSelectionIndex();
                if (si > 3) {
                    MessageDialog.openError((Shell)NewTransactionFlowWizardComposite.this.getShell(), (String)"Transaction Flow", (String)"This scheduling pattern is not supported by your version of IW IDE");
                    NewTransactionFlowWizardComposite.this.schedulingCombo.select(0);
                } else if (si == 3) {
                    NewTransactionFlowWizardComposite.this.scheduledInterval.setEnabled(false);
                    NewTransactionFlowWizardComposite.this.shift.setEnabled(false);
                    NewTransactionFlowWizardComposite.this.hoursOfTime.setEnabled(true);
                    NewTransactionFlowWizardComposite.this.minOfTime.setEnabled(true);
                    NewTransactionFlowWizardComposite.this.secOfTime.setEnabled(true);
                } else {
                    NewTransactionFlowWizardComposite.this.scheduledInterval.setEnabled(si > 0);
                    NewTransactionFlowWizardComposite.this.shift.setEnabled(true);
                    NewTransactionFlowWizardComposite.this.hoursOfTime.setEnabled(false);
                    NewTransactionFlowWizardComposite.this.minOfTime.setEnabled(false);
                    NewTransactionFlowWizardComposite.this.secOfTime.setEnabled(false);
                }
            }
        });
        FormData formData_8 = new FormData();
        formData_8.top = new FormAttachment((Control)schedulingPatternLabel, 0, 128);
        formData_8.left = new FormAttachment((Control)this.transactionId, 0, 16384);
        this.schedulingCombo.setLayoutData((Object)formData_8);
        this.schedulingCombo.select(0);
        this.schedulingCombo.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.scheduledInterval = new Text((Composite)this, 2048);
        FormData formData_9 = new FormData();
        formData_9.right = new FormAttachment((Control)this.schedulingCombo, -40, 131072);
        formData_9.top = new FormAttachment((Control)intervalLabel, 0, 128);
        formData_9.left = new FormAttachment((Control)this.schedulingCombo, 0, 16384);
        this.scheduledInterval.setLayoutData((Object)formData_9);
        this.scheduledInterval.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.scheduledInterval.setText("0");
        this.shift = new Text((Composite)this, 2048);
        FormData formData_10 = new FormData();
        formData_10.bottom = new FormAttachment((Control)this.shiftLabel, 0, 1024);
        formData_10.top = new FormAttachment((Control)this.shiftLabel, -19, 1024);
        formData_10.right = new FormAttachment((Control)numberOfExecutionsLabel, 105, 131072);
        formData_10.left = new FormAttachment((Control)numberOfExecutionsLabel, 5, -1);
        this.shift.setLayoutData((Object)formData_10);
        this.shift.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.shift.setText("0");
        this.runAtStartUp = new Button((Composite)this, 32);
        FormData formData_11 = new FormData();
        formData_11.bottom = new FormAttachment((Control)runAtStartupLabel, 16, 128);
        formData_11.top = new FormAttachment((Control)runAtStartupLabel, 0, 128);
        formData_11.left = new FormAttachment((Control)runAtStartupLabel, 12, -1);
        this.runAtStartUp.setLayoutData((Object)formData_11);
        this.runAtStartUp.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.numberOfExecutions = new Text((Composite)this, 2048);
        FormData formData_12 = new FormData();
        formData_12.right = new FormAttachment((Control)this.shift, 0, 131072);
        formData_12.top = new FormAttachment((Control)numberOfExecutionsLabel, 0, 128);
        formData_12.left = new FormAttachment((Control)numberOfExecutionsLabel, 5, -1);
        this.numberOfExecutions.setLayoutData((Object)formData_12);
        this.numberOfExecutions.setText("0");
        this.timeMesure = new CCombo((Composite)this, 2056);
        this.timeMesure.setItems(new String[]{"ms", "s", "m", "h", "d", "w"});
        this.timeMesure.setEnabled(false);
        this.timeMesure.select(0);
        FormData formData_13 = new FormData();
        formData_13.bottom = new FormAttachment((Control)this.scheduledInterval, 0, 1024);
        formData_13.top = new FormAttachment((Control)this.scheduledInterval, 0, 128);
        formData_13.left = new FormAttachment((Control)this.scheduledInterval, 5, 131072);
        this.timeMesure.setLayoutData((Object)formData_13);
        this.timeMesure.select(0);
        this.timeMesure.setBackground(SWTResourceManager.getColor(255, 255, 255));
        CLabel dateLabel = new CLabel((Composite)this, 0);
        FormData formData_14 = new FormData();
        formData_14.top = new FormAttachment(40, 0);
        formData_14.left = new FormAttachment((Control)intervalLabel, 0, 16384);
        dateLabel.setLayoutData((Object)formData_14);
        dateLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        dateLabel.setText("Date:");
        this.dayOfDate = new CCombo((Composite)this, 2056);
        this.dayOfDate.setItems(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"});
        this.dayOfDate.setEnabled(false);
        FormData formData_15 = new FormData();
        formData_15.top = new FormAttachment((Control)dateLabel, 0, 128);
        formData_15.left = new FormAttachment((Control)this.scheduledInterval, 0, 16384);
        this.dayOfDate.setLayoutData((Object)formData_15);
        this.dayOfDate.select(0);
        this.dayOfDate.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.monthOfDate = new CCombo((Composite)this, 2056);
        FormData formData_16 = new FormData();
        formData_16.top = new FormAttachment((Control)this.dayOfDate, 0, 128);
        formData_16.left = new FormAttachment((Control)this.dayOfDate, 5, 131072);
        this.monthOfDate.setLayoutData((Object)formData_16);
        this.monthOfDate.setItems(new String[]{"Jan", "Feb", "Mar", "Apr", "Mau", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        this.monthOfDate.setEnabled(false);
        this.monthOfDate.select(0);
        this.monthOfDate.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.yearOfDate = new CCombo((Composite)this, 2056);
        FormData formData_17 = new FormData();
        formData_17.top = new FormAttachment((Control)this.monthOfDate, 0, 128);
        formData_17.left = new FormAttachment((Control)this.monthOfDate, 5, 131072);
        this.yearOfDate.setLayoutData((Object)formData_17);
        this.yearOfDate.setItems(new String[]{"2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016"});
        this.yearOfDate.setEnabled(false);
        this.yearOfDate.select(0);
        this.yearOfDate.setBackground(SWTResourceManager.getColor(255, 255, 255));
        CLabel timeLabel = new CLabel((Composite)this, 0);
        FormData formData_18 = new FormData();
        formData_18.top = new FormAttachment(50, 0);
        formData_18.left = new FormAttachment((Control)dateLabel, 0, 16384);
        timeLabel.setLayoutData((Object)formData_18);
        timeLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        timeLabel.setText("Time:");
        this.hoursOfTime = new CCombo((Composite)this, 2056);
        FormData formData_19 = new FormData();
        formData_19.top = new FormAttachment((Control)timeLabel, 0, 128);
        formData_19.left = new FormAttachment((Control)this.dayOfDate, 0, 16384);
        this.hoursOfTime.setLayoutData((Object)formData_19);
        this.hoursOfTime.setItems(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"});
        this.hoursOfTime.setEnabled(false);
        this.hoursOfTime.select(0);
        this.hoursOfTime.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.minOfTime = new CCombo((Composite)this, 2056);
        FormData formData_20 = new FormData();
        formData_20.top = new FormAttachment((Control)this.hoursOfTime, 0, 128);
        formData_20.left = new FormAttachment((Control)this.hoursOfTime, 5, 131072);
        this.minOfTime.setLayoutData((Object)formData_20);
        this.minOfTime.setItems(ConfigContext.n_0_59);
        this.minOfTime.setEnabled(false);
        this.minOfTime.select(0);
        this.minOfTime.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.secOfTime = new CCombo((Composite)this, 2056);
        FormData formData_21 = new FormData();
        formData_21.top = new FormAttachment((Control)this.minOfTime, 0, 128);
        formData_21.left = new FormAttachment((Control)this.minOfTime, 5, 131072);
        this.secOfTime.setLayoutData((Object)formData_21);
        this.secOfTime.setItems(ConfigContext.n_0_59);
        this.secOfTime.setEnabled(false);
        this.secOfTime.select(0);
        this.secOfTime.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.calendarButton = new Button((Composite)this, 0);
        this.calendarButton.setEnabled(false);
        FormData formData_22 = new FormData();
        formData_22.bottom = new FormAttachment((Control)dateLabel, 0, 1024);
        formData_22.top = new FormAttachment((Control)this.yearOfDate, 0, 128);
        formData_22.left = new FormAttachment((Control)this.yearOfDate, 5, 131072);
        this.calendarButton.setLayoutData((Object)formData_22);
        this.calendarButton.setImage(ResourceManager.getPluginImage((Object)Iw_sdkPlugin.getDefault(), "icons/calendar.gif"));
        this.calendarButton.setForeground(Display.getCurrent().getSystemColor(1));
        this.calendarButton.setBackground(Display.getCurrent().getSystemColor(1));
        this.dayLabel = new CLabel((Composite)this, 0);
        FormData formData_23 = new FormData();
        formData_23.top = new FormAttachment((Control)this.calendarButton, 0, 128);
        formData_23.left = new FormAttachment((Control)this.shiftLabel, 0, 16384);
        this.dayLabel.setLayoutData((Object)formData_23);
        this.dayLabel.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.dayLabel.setText("Day of the week:");
        this.comboDay = new CCombo((Composite)this, 2056);
        this.comboDay.setItems(new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"});
        this.comboDay.setEnabled(false);
        this.comboDay.select(0);
        FormData formData_24 = new FormData();
        formData_24.top = new FormAttachment((Control)this.dayLabel, 0, 128);
        formData_24.left = new FormAttachment((Control)numberOfExecutionsLabel, 5, -1);
        this.comboDay.setLayoutData((Object)formData_24);
        this.comboDay.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.timeMesure_1 = new CCombo((Composite)this, 2056);
        this.timeMesure_1.setItems(new String[]{"ms", "s", "m", "h", "d", "w"});
        this.timeMesure_1.setEnabled(false);
        FormData formData_13_1 = new FormData();
        formData_13_1.bottom = new FormAttachment((Control)this.shift, 19, 128);
        formData_13_1.top = new FormAttachment((Control)this.shift, 0, 128);
        formData_13_1.right = new FormAttachment((Control)this.shift, 45, 131072);
        formData_13_1.left = new FormAttachment((Control)this.shift, 5, 131072);
        this.timeMesure_1.setLayoutData((Object)formData_13_1);
        this.timeMesure_1.select(0);
        this.timeMesure_1.setBackground(SWTResourceManager.getColor(255, 255, 255));
        this.publicLabel = new CLabel((Composite)this, 0);
        this.publicLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_14_1 = new FormData();
        formData_14_1.top = new FormAttachment((Control)schedulingPatternLabel, 0, 128);
        formData_14_1.left = new FormAttachment((Control)this.timeMesure_1, 0, 16384);
        this.publicLabel.setLayoutData((Object)formData_14_1);
        this.publicLabel.setText("Public:");
        this.hostedPublic = new Button((Composite)this, 32);
        this.hostedPublic.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_15_1 = new FormData();
        formData_15_1.top = new FormAttachment((Control)schedulingPatternLabel, 0, 128);
        formData_15_1.right = new FormAttachment(100, -20);
        this.hostedPublic.setLayoutData((Object)formData_15_1);
        CLabel label = new CLabel((Composite)this, 0);
        FormData formData_25 = new FormData();
        formData_25.bottom = new FormAttachment(0, 55);
        formData_25.right = new FormAttachment(0, 10);
        formData_25.top = new FormAttachment(0, 55);
        formData_25.left = new FormAttachment((Control)idLabel, 0, 16384);
        label.setLayoutData((Object)formData_25);
        label.setText("label");
        this.primaryTsUrl = new Text((Composite)this, 2048);
        FormData formData_x = new FormData();
        formData_x.right = new FormAttachment((Control)this.getTransactionId(), 0, 131072);
        formData_x.top = new FormAttachment((Control)this.primaryTsLabel, 0, 128);
        formData_x.left = new FormAttachment((Control)this.getTransactionId(), 0, 16384);
        this.primaryTsUrl.setLayoutData((Object)formData_x);
        CLabel secondaryTsUrlLabel = new CLabel((Composite)this, 0);
        secondaryTsUrlLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_1_x = new FormData();
        formData_1_x.left = new FormAttachment((Control)this.dayLabel, 0, 16384);
        formData_1_x.top = new FormAttachment((Control)this.primaryTsUrl, 0, 128);
        secondaryTsUrlLabel.setLayoutData((Object)formData_1_x);
        secondaryTsUrlLabel.setText("Secondary TS URL:");
        this.secondaryTsUrl = new Text((Composite)this, 2048);
        FormData formData_2_x = new FormData();
        formData_2_x.right = new FormAttachment(100, -20);
        formData_2_x.top = new FormAttachment((Control)secondaryTsUrlLabel, 0, 128);
        formData_2_x.left = new FormAttachment((Control)this.shift, 0, 16384);
        this.secondaryTsUrl.setLayoutData((Object)formData_2_x);
        CLabel activeLabel = new CLabel((Composite)this, 0);
        activeLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_3_x = new FormData();
        formData_3_x.top = new FormAttachment((Control)this.getTransactionId(), 0, 128);
        formData_3_x.left = new FormAttachment((Control)secondaryTsUrlLabel, 0, 16384);
        activeLabel.setLayoutData((Object)formData_3_x);
        activeLabel.setText("Active:");
        this.activeFlow = new Button((Composite)this, 32);
        this.activeFlow.setSelection(true);
        this.activeFlow.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_4_x = new FormData();
        formData_4_x.top = new FormAttachment((Control)activeLabel, 0, 128);
        formData_4_x.left = new FormAttachment((Control)this.secondaryTsUrl, 0, 16384);
        this.activeFlow.setLayoutData((Object)formData_4_x);
        Button descriptionButton = new Button((Composite)this, 0);
        descriptionButton.addMouseListener((MouseListener)new MouseAdapter(){

            public void mouseDown(MouseEvent e) {
                NewTransactionFlowWizardComposite.this.editTfDescription();
            }
        });
        descriptionButton.addKeyListener((KeyListener)new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                NewTransactionFlowWizardComposite.this.editTfDescription();
            }
        });
        descriptionButton.setToolTipText("Set Transaction Flow Description");
        descriptionButton.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_27 = new FormData();
        formData_27.right = new FormAttachment((Control)this.secondaryTsUrl, 0, 131072);
        formData_27.top = new FormAttachment((Control)this.activeFlow, 0, 128);
        descriptionButton.setLayoutData((Object)formData_27);
        descriptionButton.setText("Description");
        CLabel statefulLabel = new CLabel((Composite)this, 0);
        statefulLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_28 = new FormData();
        formData_28.top = new FormAttachment((Control)schedulingPatternLabel, 0, 128);
        formData_28.left = new FormAttachment((Control)this.shiftLabel, 0, 16384);
        statefulLabel.setLayoutData((Object)formData_28);
        statefulLabel.setText("Stateful:");
        this.statefulFlow = new Button((Composite)this, 32);
        this.statefulFlow.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_29 = new FormData();
        formData_29.top = new FormAttachment((Control)statefulLabel, 0, 128);
        formData_29.left = new FormAttachment((Control)this.shift, 0, 16384);
        this.statefulFlow.setLayoutData((Object)formData_29);
        CLabel solutionLabel = new CLabel((Composite)this, 0);
        solutionLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_30 = new FormData();
        formData_30.top = new FormAttachment((Control)this.secOfTime, 0, 128);
        formData_30.left = new FormAttachment((Control)this.dayLabel, 0, 16384);
        solutionLabel.setLayoutData((Object)formData_30);
        solutionLabel.setText("Solution:");
        this.solution = new CCombo((Composite)this, 2056);
        this.solution.setItems(new String[]{"", "SF2QB: SalesForce to QuickBooks", "SF2CMS: SalesForce to CMS", "SF2OMS: SalesForce to Nexternal", "SF2OMSQB: SalesForce to Nexternal and QB", "SF2OMSDB: SalesForce to Nexternal and DB", "SF2DBG: SalesForce to DB (Generic)", "SF2AUTH: SalesForce to Authorize.net", "OMS2QB: Nexternal to QuickBooks", "OMS2ACC: Nexternal to Accpac", "SF2OMS2: SalesForce to Nexternal 2", "SF2MAS200: Salesforce to MAS"});
        this.solution.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_31 = new FormData();
        formData_31.top = new FormAttachment((Control)solutionLabel, 0, 128);
        formData_31.left = new FormAttachment((Control)this.comboDay, 0, 16384);
        this.solution.setLayoutData((Object)formData_31);
        CLabel innerCyclesLabel = new CLabel((Composite)this, 0);
        innerCyclesLabel.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_32 = new FormData();
        formData_32.top = new FormAttachment((Control)this.numberOfExecutions, 0, 128);
        formData_32.left = new FormAttachment((Control)this.timeMesure_1, 0, 131072);
        innerCyclesLabel.setLayoutData((Object)formData_32);
        innerCyclesLabel.setText("Inner Cycles:");
        this.innerSycles = new Text((Composite)this, 2048);
        this.innerSycles.setText("0");
        this.innerSycles.setBackground(Display.getCurrent().getSystemColor(1));
        FormData formData_33 = new FormData();
        formData_33.right = new FormAttachment((Control)this.secondaryTsUrl, 0, 131072);
        formData_33.left = new FormAttachment((Control)innerCyclesLabel, 5, -1);
        formData_33.top = new FormAttachment((Control)innerCyclesLabel, 0, 128);
        this.innerSycles.setLayoutData((Object)formData_33);
    }

    private void propertyOperation(int mode) {
        int si = this.propertyTable.getSelectionIndex();
        TableItem sti = null;
        if (si >= 0) {
            if (mode == 0) {
                sti = this.propertyTable.getItem(si);
                try {
                    PropertyDialog pd = new PropertyDialog(this.getShell());
                    pd.setPName(sti.getText(0));
                    pd.setPValue(sti.getText(1));
                    pd.setPUpload(sti.getText(2).equals("Yes"));
                    pd.setPPassword(sti.getText(3).equals("Yes"));
                    pd.setPFixed(sti.getText(4).equals("Prohibited"));
                    pd.open();
                    if (pd.getReturnCode() == 0) {
                        sti.setText(0, pd.getPName());
                        sti.setText(1, pd.getPValue());
                        sti.setText(2, pd.isPUpload() ? "Yes" : "No");
                        sti.setText(3, pd.isPPassword() ? "Yes" : "No");
                        sti.setText(4, pd.isPFixed() ? "Prohibited" : "Allowed");
                    }
                }
                catch (RuntimeException e) {
                    e.printStackTrace();
                    MessageDialog.openError((Shell)this.getShell(), (String)"Internal Error", (String)("Unable to create propert dialog: " + e.toString()));
                }
            } else {
                PropertyDialog pd = new PropertyDialog(this.getShell());
                pd.setPName("");
                pd.setPValue("");
                pd.setPUpload(false);
                pd.setPPassword(false);
                pd.setPFixed(false);
                pd.open();
                if (pd.getReturnCode() == 0) {
                    sti = mode == -1 ? new TableItem(this.propertyTable, 2048) : new TableItem(this.propertyTable, 2048, mode - 1);
                    sti.setText(0, pd.getPName());
                    sti.setText(1, pd.getPValue());
                    sti.setText(2, pd.isPUpload() ? "Yes" : "No");
                    sti.setText(3, pd.isPPassword() ? "Yes" : "No");
                    sti.setText(4, pd.isPFixed() ? "Prohibited" : "Allowed");
                }
            }
        } else if (mode >= 0) {
            MessageDialog.openWarning((Shell)this.getShell(), (String)"Property operation", (String)"No Property Selected");
        } else {
            PropertyDialog pd = new PropertyDialog(this.getShell());
            pd.setPName("");
            pd.setPValue("");
            pd.setPUpload(false);
            pd.setPPassword(false);
            pd.setPFixed(false);
            pd.open();
            if (pd.getReturnCode() == 0) {
                sti = new TableItem(this.propertyTable, 2048);
                sti.setText(0, pd.getPName());
                sti.setText(1, pd.getPValue());
                sti.setText(2, pd.isPUpload() ? "Yes" : "No");
                sti.setText(3, pd.isPPassword() ? "Yes" : "No");
                sti.setText(4, pd.isPFixed() ? "Prohibited" : "Allowed");
            }
        }
    }

    public CCombo getComboDay() {
        return this.comboDay;
    }

    public CCombo getDayOfDate() {
        return this.dayOfDate;
    }

    public CCombo getHoursOfTime() {
        return this.hoursOfTime;
    }

    public CCombo getMinOfTime() {
        return this.minOfTime;
    }

    public CCombo getMonthOfDate() {
        return this.monthOfDate;
    }

    public Text getNumberOfExecutions() {
        return this.numberOfExecutions;
    }

    public Table getPropertyTable() {
        return this.propertyTable;
    }

    public Button getRunAtStartUp() {
        return this.runAtStartUp;
    }

    public Text getScheduledInterval() {
        return this.scheduledInterval;
    }

    public CCombo getSchedulingCombo() {
        return this.schedulingCombo;
    }

    public CCombo getSecOfTime() {
        return this.secOfTime;
    }

    public Text getShift() {
        return this.shift;
    }

    public CCombo getTimeMesure() {
        return this.timeMesure;
    }

    public CCombo getTimeMesure_1() {
        return this.timeMesure_1;
    }

    public Text getTransactionId() {
        return this.transactionId;
    }

    public CCombo getYearOfDate() {
        return this.yearOfDate;
    }

    public boolean saveFlow(TransactionBase currentTransactionFlow) {
        TableItem[] pkit;
        if (!this.validate()) {
            return false;
        }
        if (currentTransactionFlow == null) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Transaction Flow", (String)"No flow to save");
            return false;
        }
        String oldFlow = currentTransactionFlow.getTransactionId();
        String newFlow = this.transactionId.getText().trim();
        if (oldFlow != null && !oldFlow.equals(newFlow) && !ConfigContext.renameAllNextTransactions(oldFlow, newFlow)) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Transaction Flow", (String)"Unable to rename transactions in the flow; refresh recommended");
            return false;
        }
        currentTransactionFlow.setTransactionId(newFlow);
        currentTransactionFlow.setDescription(this.transactionDescription);
        currentTransactionFlow.setHostedPublic(this.hostedPublic.getSelection());
        currentTransactionFlow.setActive(this.activeFlow.getSelection());
        currentTransactionFlow.setInnerCycles(Integer.valueOf(this.innerSycles.getText().trim()));
        currentTransactionFlow.setPrimaryTransformationServerURL(this.primaryTsUrl.getText().trim());
        currentTransactionFlow.setSecondaryTransformationServerURL(this.secondaryTsUrl.getText().trim());
        Hashtable<String, TransactionBase.ParameterValue> partb = new Hashtable<String, TransactionBase.ParameterValue>();
        TableItem[] tableItemArray = pkit = this.propertyTable.getItems();
        int n = 0;
        int n2 = tableItemArray.length;
        while (n < n2) {
            TableItem cti = tableItemArray[n];
            String pnm = cti.getText(0);
            if (pnm.equals("QueryStartTime")) {
                currentTransactionFlow.setTransactionStartTime(Timestamp.valueOf(cti.getText(1)));
            } else {
                TransactionBase transactionBase = currentTransactionFlow;
                transactionBase.getClass();
                partb.put(pnm, transactionBase.new TransactionBase.ParameterValue(cti.getText(1), cti.getText(4).equals("Prohibited"), cti.getText(2).equals("Yes"), cti.getText(3).equals("Yes")));
            }
            ++n;
        }
        currentTransactionFlow.setParameters(partb);
        int ssi = this.solution.getSelectionIndex();
        if (ssi >= 0) {
            String sol = this.solution.getItem(ssi);
            int scm = sol.indexOf(":");
            if (scm > 0) {
                currentTransactionFlow.setSolution(sol.substring(0, scm));
            }
        } else {
            currentTransactionFlow.setSolution("");
        }
        if (currentTransactionFlow instanceof TransactionContext) {
            TransactionContext ct = (TransactionContext)currentTransactionFlow;
            long scin = Long.valueOf(this.scheduledInterval.getText());
            ct.setTransactionShiftFromHartBeat(Long.valueOf(this.shift.getText()));
            switch (this.schedulingCombo.getSelectionIndex()) {
                case 0: {
                    ct.setTransactionInterval(0L);
                    break;
                }
                case 1: {
                    ct.setTransactionInterval(-Math.abs(scin));
                    break;
                }
                case 2: {
                    ct.setTransactionInterval(scin);
                    break;
                }
                case 3: {
                    ct.setTransactionShiftFromHartBeat(-1L);
                    long hr = Long.valueOf(this.hoursOfTime.getItem(this.hoursOfTime.getSelectionIndex()));
                    long mn = Long.valueOf(this.minOfTime.getItem(this.minOfTime.getSelectionIndex()));
                    long sc = Long.valueOf(this.secOfTime.getItem(this.secOfTime.getSelectionIndex()));
                    ct.setTransactionInterval(sc + mn * 60L + hr * 60L * 60L);
                    break;
                }
            }
            ct.setRunAtStartUp(this.runAtStartUp.getSelection());
            ct.setNumberOfExecutions(Integer.valueOf(this.numberOfExecutions.getText().trim()));
            ct.setStateful(this.statefulFlow.getSelection());
        }
        return true;
    }

    private boolean validate() {
        if (this.transactionId.getText().trim().length() == 0) {
            MessageDialog.openError((Shell)this.getShell(), (String)"Transaction Flow", (String)"Transaction Flow Id is empty");
            return false;
        }
        return true;
    }

    private void editTfDescription() {
        TransactionFlowDescription tfd = new TransactionFlowDescription(this.getShell());
        tfd.create();
        tfd.getStyledText().setText(this.transactionDescription);
        tfd.open();
        if (tfd.getReturnCode() == 0) {
            this.transactionDescription = tfd.getFlowDescription();
        }
    }

    public Button getHostedPublic() {
        return this.hostedPublic;
    }

    public Button getActiveFlow() {
        return this.activeFlow;
    }

    public Text getPrimaryTsUrl() {
        return this.primaryTsUrl;
    }

    public Text getSecondaryTsUrl() {
        return this.secondaryTsUrl;
    }

    public String getTransactionDescription() {
        return this.transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    private void copyParameters() {
        TableItem[] sti = this.propertyTable.getSelection();
        String[][] op = new String[this.propertyTable.getSelectionCount()][this.propertyTable.getColumnCount()];
        int i = 0;
        while (i < op.length) {
            int j = 0;
            while (j < op[i].length) {
                op[i][j] = sti[i].getText(j);
                ++j;
            }
            ++i;
        }
        ConfigContext.setParamsClp(op);
    }

    private void cutParameters() {
        this.copyParameters();
        this.propertyTable.remove(this.propertyTable.getSelectionIndices());
    }

    private void pasteParameters() {
        String[][] pt = ConfigContext.getParamsClp();
        if (pt != null) {
            TableItem[] oldItems = this.propertyTable.getItems();
            String[][] stringArray = pt;
            int n = 0;
            int n2 = stringArray.length;
            while (n < n2) {
                int i;
                String[] ptc = stringArray[n];
                int oi = -1;
                int j = 0;
                while (j < oldItems.length) {
                    if (oldItems[j].getText(0).equals(ptc[0])) {
                        oi = j;
                        break;
                    }
                    ++j;
                }
                if (oi == -1) {
                    TableItem nti = new TableItem(this.propertyTable, 2048);
                    i = 0;
                    while (i < ptc.length) {
                        nti.setText(i, ptc[i]);
                        ++i;
                    }
                } else if (MessageDialog.openQuestion((Shell)this.getShell(), (String)"Copy Properties", (String)("Propewrty " + ptc[0] + " already exists. Overwrite?"))) {
                    TableItem oti = this.propertyTable.getItem(oi);
                    i = 1;
                    while (i < ptc.length) {
                        oti.setText(i, ptc[i]);
                        ++i;
                    }
                }
                ++n;
            }
        }
    }

    public Button getStatefulFlow() {
        return this.statefulFlow;
    }

    public CCombo getSolution() {
        return this.solution;
    }

    public Text getInnerSycles() {
        return this.innerSycles;
    }
}

