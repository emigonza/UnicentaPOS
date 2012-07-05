/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package com.openbravo.pos.pda.struts.actions;

import com.openbravo.data.gui.ListKeyed;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.pos.printer.TicketParser;
import com.openbravo.pos.printer.DeviceTicket;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.data.loader.Session;
import com.openbravo.pos.customers.DataLogicCustomers;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.pda.bo.RestaurantManager;
import com.openbravo.pos.pda.bo.TaxesLogic;
import com.openbravo.pos.pda.dao.TicketDAO;
import com.openbravo.pos.pda.util.PropertyUtils;
import com.openbravo.pos.printer.TicketPrinterException;
import com.openbravo.pos.sales.JTicketLines;
import com.openbravo.pos.scripting.ScriptEngine;
import com.openbravo.pos.scripting.ScriptException;
import com.openbravo.pos.scripting.ScriptFactory;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 *
 * @author awolf, Fernando La Chica
 */
public class PrintPDA {

   private final String APP_ID = "openbravopos";
   private PropertyUtils properties;

    private final static int NUMBERZERO = 0;
    private final static int NUMBERVALID = 1;

    private final static int NUMBER_INPUTZERO = 0;
    private final static int NUMBER_INPUTZERODEC = 1;
    private final static int NUMBER_INPUTINT = 2;
    private final static int NUMBER_INPUTDEC = 3;
    private final static int NUMBER_PORZERO = 4;
    private final static int NUMBER_PORZERODEC = 5;
    private final static int NUMBER_PORINT = 6;
    private final static int NUMBER_PORDEC = 7;

    protected JTicketLines m_ticketlines;

    // private Template m_tempLine;
    private TicketParser m_TTP;

    protected TicketInfo m_oTicket;
    protected Object m_oTicketExt;

    // Estas tres variables forman el estado...
    private int m_iNumberStatus;
    private int m_iNumberStatusInput;
    private int m_iNumberStatusPor;
    private StringBuffer m_sBarcode;

   
    private SentenceList senttax;
    private ListKeyed taxcollection;
    // private ComboBoxValModel m_TaxModel;

    private SentenceList senttaxcategories;
    private ListKeyed taxcategoriescollection;
    
    private TaxesLogic taxeslogic;

//    private ScriptObject scriptobjinst;
   
    protected AppView m_App;
    protected DataLogicSystem dlSystem;
    protected DataLogicSales dlSales;
    protected DataLogicCustomers dlCustomers;

    public static void main(String []args)
    {
        //TestPrint tp=new TestPrint();
        //System.out.println(tp.PrintPDATicket());

    }

    private static String readFileAsString(String filePath)
    throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

    private File getDefaultConfig() {
        return new File(new File(System.getProperty("user.home")), APP_ID + ".properties");
    }

    public  boolean  PrintPDATicket(String place, TicketInfo ticket, String placename)
    {
        int numBar = 0;
        int numKit = 0;
        int numCKit = 0;
        int numCBar = 0;
        properties=new PropertyUtils();


        AppConfig ap=new AppConfig(getDefaultConfig());
        ap.load();
        DeviceTicket dt=new DeviceTicket(null, ap);

        Session s=null;
        try {

            s = new Session(properties.getUrl(),properties.getDBUser(), properties.getDBPassword());
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        DataLogicSystem dls=new DataLogicSystem();
        dls.init(s);


        TicketParser tp=new TicketParser(dt, dls);

        ticket.setName(placename);
        // Obtenemos un Iterador y recorremos la lista.
        List<TicketLineInfo> lst = ticket.getLines();
        ListIterator iter = lst.listIterator();
        int i = 0;
        while (iter.hasNext()){
          if (ticket.getLine(i).getProperties().getProperty("sendstatus").equals("No")){
              if (ticket.getLine(i).getProperties().getProperty("printkb").equals("Bar"))
                 numBar = numBar + 1;
              else
                 numKit = numKit + 1;
          }
          else if (ticket.getLine(i).getProperties().getProperty("sendstatus").equals("Cancel")){
              if (ticket.getLine(i).getProperties().getProperty("printkb").equals("Bar"))
                 numCBar = numCBar + 1;
              else
                 numCKit = numCKit + 1;
          }

          i = i + 1;
          if (i >= lst.size())
             break;
        }

        if ((numKit>0) && (numCBar<=0) && (numCKit <=0)){
            String xml=getTicketXML(dls,place,ticket, "Kitchen");
            if(xml!=null)
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                script.put("taxes", taxcollection);
                script.put("taxeslogic", taxeslogic);
                ticket.setName(placename);
                script.put("ticket", ticket);
                RestaurantManager manager = new RestaurantManager();
                script.put("place", placename);
                tp.printTicket(script.eval(xml).toString());
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (TicketPrinterException e) {
                e.printStackTrace();
            }
            else
                return false;
        }

        if (numBar>0 && (numCBar<=0) && (numCKit <=0)){
            String xml=getTicketXML(dls,place,ticket, "Bar");
            if(xml!=null)
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                script.put("taxes", taxcollection);
                script.put("taxeslogic", taxeslogic);
                script.put("ticket", ticket);
                //script.put("place", ticketext);
                if (numCBar<=0)
                   tp.printTicket(script.eval(xml).toString());
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (TicketPrinterException e) {
                e.printStackTrace();
            }
            else
                return false;
        }

        if (numCKit>0){
            String xml=getTicketXML(dls,place,ticket, "ChangeKitchen");
            if(xml!=null)
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                script.put("taxes", taxcollection);
                script.put("taxeslogic", taxeslogic);
                ticket.setName(placename);
                script.put("ticket", ticket);
                RestaurantManager manager = new RestaurantManager();
                script.put("place", placename);
                tp.printTicket(script.eval(xml).toString());

                Iterator<TicketLineInfo> tklIter = ticket.getLines().iterator();
                i = 0;
                while (tklIter.hasNext()){
                  if (ticket.getLine(i).getProperty("sendstatus").equals("cancel"))
                    ticket.removeLine(i);

                  i = i + 1;
                  if (i >= ticket.getLinesCount())
                     break;
                }
                //Revisar que no esté cancelado el penultimo o ultimo item
                if (ticket.getLinesCount()>0){
                    if (ticket.getLine(ticket.getLinesCount()-1).getProperty("sendstatus").equals("Cancel")){
                        ticket.removeLine(ticket.getLinesCount()-1);
                    }
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (TicketPrinterException e) {
                e.printStackTrace();
            }
            else
                return false;
        }

        if (numCBar>0){
            String xml=getTicketXML(dls,place,ticket, "ChangeBar");
            if(xml!=null)
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                script.put("taxes", taxcollection);
                script.put("taxeslogic", taxeslogic);
                ticket.setName(placename);
                script.put("ticket", ticket);
                RestaurantManager manager = new RestaurantManager();
                script.put("place", placename);
                tp.printTicket(script.eval(xml).toString());

                Iterator<TicketLineInfo> tklIter = ticket.getLines().iterator();
                i = 0;
                while (tklIter.hasNext()){
                  if (ticket.getLine(i).getProperty("sendstatus").equals("Cancel")){
                    ticket.removeLine(i);
                  }

                  i = i + 1;
                  if (i >= ticket.getLinesCount())
                     break;
                }
                //Revisar que no esté cancelado el penultimo o ultimo item
                if (ticket.getLinesCount()>0){
                    if (ticket.getLine(ticket.getLinesCount()-1).getProperty("sendstatus").equals("Cancel")){
                        ticket.removeLine(ticket.getLinesCount()-1);
                    }
                }
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (TicketPrinterException e) {
                e.printStackTrace();
            }
            else
                return false;
        }

        return true;

    }

     private String getTicketXML(DataLogicSystem d,String ticketext,TicketInfo ticket, String modo)
     {
         String sresource = null;
         if (modo.equals("Kitchen"))
            sresource = d.getResourceAsXML("Printer.TicketKitchen");
         else if (modo.equals("Bar"))
            sresource = d.getResourceAsXML("Printer.TicketBar");
         else if (modo.equals("ChangeKitchen"))
            sresource = d.getResourceAsXML("Printer.TicketChange_Kitchen");
         else if (modo.equals("ChangeBar"))
            sresource = d.getResourceAsXML("Printer.TicketChange_Bar");




          if (sresource == null) {

        } else {
            try {
                ScriptEngine script = ScriptFactory.getScriptEngine(ScriptFactory.VELOCITY);
                script.put("taxes", taxcollection);
                script.put("taxeslogic", taxeslogic);
                script.put("ticket", ticket);
                script.put("place", ticketext);
                return script.eval(sresource).toString();
            } catch (ScriptException e) {

            }

        }
         return null;
     }

    public void inicializa(AppView app) throws BeanFactoryException {

        m_App = app;
        dlSystem = (DataLogicSystem) m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
        dlSales = (DataLogicSales) m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        dlCustomers = (DataLogicCustomers) m_App.getBean("com.openbravo.pos.customers.DataLogicCustomers");

    
        m_ticketlines = new JTicketLines(dlSystem.getResourceAsXML("Ticket.Line"));
    
        m_TTP = new TicketParser(m_App.getDeviceTicket(), dlSystem);

        // El modelo de impuestos
        senttax = dlSales.getTaxList();
        senttaxcategories = dlSales.getTaxCategoriesList();

        // inicializamos
        m_oTicket = null;
        m_oTicketExt = null;
    }

}
