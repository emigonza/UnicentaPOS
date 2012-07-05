//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (C) 2008-2009 Openbravo, S.L.
//    http://www.unicenta.net/unicentaopos
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.data.loader;

/**
 *
 * @author adrianromero
 */
public class SessionDBHSQLDB implements SessionDB {

    public String TRUE() {
        return "TRUE";
    }
    public String FALSE() {
        return "FALSE";
    }
    public String INTEGER_NULL() {
        return "CAST(NULL AS INTEGER)";
    }
    public String CHAR_NULL() {
        return "CAST(NULL AS CHAR)";
    }

    public String getName() {
        return "HSQLDB";
    }

    public SentenceFind getSequenceSentence(Session s, String sequence) {
        return new StaticSentence(s, "CALL NEXT VALUE FOR " + sequence, null, SerializerReadInteger.INSTANCE);
    }
}
