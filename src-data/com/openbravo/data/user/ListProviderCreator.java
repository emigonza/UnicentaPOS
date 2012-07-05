//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2011 uniCenta
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

package com.openbravo.data.user;

import java.util.*;
import com.openbravo.data.loader.TableDefinition;
import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.SentenceList;

public class ListProviderCreator implements ListProvider {
    
    private SentenceList sent;
    private EditorCreator prov;
    private Object params;
    
    /** Creates a new instance of ListProviderEditor */
    public ListProviderCreator(SentenceList sent, EditorCreator prov) {
        this.sent = sent;
        this.prov = prov;
        params = null;
    }
    
    public ListProviderCreator(SentenceList sent) {
        this(sent, null);
    }
    
    public ListProviderCreator(TableDefinition table) {        
        this(table.getListSentence(), null);
    }
//    public ListProviderECreator(Connection c, ISQLBuilderStatic sqlbuilder, SerializerRead sr, SerializerWrite sw, EditorCreator prov) {
//        this(new StaticSentence(c, sqlbuilder), prov);
//        sent.setSerializerRead(sr);
//        sent.setSerializerWrite(sw);
//    }
//    public ListProviderECreator(Connection c, TableDefinition table, SerializerRead sr, SerializerWrite sw) {        
//        this(new PreparedSentence(c, table.getListSentence()), null);
//        sent.setSerializerRead(sr);
//        sent.setSerializerWrite(sw);
//    }    
//    public ListProviderECreator(Connection c, ISQLBuilder sqlbuilder, SerializerRead sr, SerializerWrite sw, EditorCreator prov) {
//        this(new PreparedSentence(c, sqlbuilder), prov);
//        sent.setSerializerRead(sr);
//        sent.setSerializerWrite(sw);
//    }
//    public ListProviderECreator(Connection c, TableDefinition table, String[] asFindFields, SerializerRead sr, SerializerWrite sw, EditorCreator prov) {
//        this(new PreparedSentence(c, new ListBuilder(table, asFindFields)), prov);
//        sent.setSerializerRead(sr);
//        sent.setSerializerWrite(sw);
//   } 
//    public ListProviderECreator(Connection c, String sqlsentence, SerializerRead sr, SerializerWrite sw, EditorCreator prov) {
//        this(new PreparedSentence(c, sqlsentence), prov);
//        sent.setSerializerRead(sr);
//        sent.setSerializerWrite(sw);
//    }
//    public ListProviderECreator(Connection c, String sqlsentence, SerializerRead sr, SerializerWrite sw) {
//        this(new PreparedSentence(c, sqlsentence), null);
//        sent.setSerializerRead(sr);
//        sent.setSerializerWrite(sw);
//    }

    public List loadData() throws BasicException {       
        params = (prov == null) ? null : prov.createValue();
        return refreshData();
    }
    
    public List refreshData() throws BasicException {
        return sent.list(params);
    }    
}
