//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.pda.struts.actions;

import com.openbravo.pos.pda.bo.RestaurantManager;
import com.openbravo.pos.pda.dao.TicketDAO;
import com.openbravo.pos.pda.struts.forms.FloorForm;
import com.openbravo.pos.ticket.Place;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.ticket.UserInfo;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
/**
 *
 * @author Fernando La Chica
 */
public class PrintAction extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    private final static String FAILURE = "failure";
    private final static String PRINTING = "printing";

    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        FloorForm  inputForm = (FloorForm ) form;
        RestaurantManager manager = new RestaurantManager();

        Place place = manager.findPlaceByName(inputForm.getFloorId());

        TicketInfo ticketInfo = manager.findTicket(place.id);

        if (ticketInfo.getUser() == null){
            ticketInfo.setUser((UserInfo) request.getSession().getAttribute("user"));
        }
        TicketDAO tkdao = new TicketDAO();
        tkdao.updateTicket(place.id, ticketInfo);

        PrintPDA printer = new PrintPDA();
        printer.PrintPDATicket(place.getName(), ticketInfo, place.getName());

        // Obtenemos un Iterador y recorremos la lista.
        List<TicketLineInfo> lst = ticketInfo.getLines();
        ListIterator iter = lst.listIterator();
        int i = 0;
        while (iter.hasNext()){
          ticketInfo.getLine(i).getProperties().setProperty("sendstatus", "Yes");
          i = i + 1;
          if (i >= lst.size())
             break;
        }
        tkdao = new TicketDAO();
        tkdao.updateTicket(place.id, ticketInfo);
        
        return mapping.findForward(PRINTING);

    }
}