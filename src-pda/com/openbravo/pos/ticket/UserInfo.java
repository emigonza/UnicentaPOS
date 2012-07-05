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
package com.openbravo.pos.ticket;

import java.io.Serializable;

/**
 *
 * @author adrianromero
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7537578737839L;
    private String m_sId;
    private String m_sName;
    private String psw;

    /** Creates a new instance of UserInfoBasic */
    public UserInfo(String id, String name) {
        m_sId = id;
        m_sName = name;
    }

    public UserInfo() {
        super();
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getM_sId() {
        return m_sId;
    }

    public void setM_sId(String m_sId) {
        this.m_sId = m_sId;
    }

    public String getM_sName() {
        return m_sName;
    }

    public void setM_sName(String m_sName) {
        this.m_sName = m_sName;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getId() {
        return m_sId;
    }

    public String getName() {
        return m_sName;
    }

    public void setPassword(String psw) {
        this.psw = psw;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setLogin(String name) {
        this.m_sName = name;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
