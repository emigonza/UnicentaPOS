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

package com.openbravo.data.model;

import com.openbravo.data.loader.Datas;
import com.openbravo.format.Formats;

/**
 *
 * @author adrian
 */
public class Field {
    
    private String label;
    private Datas data;
    private Formats format;
    
    private boolean searchable;
    private boolean comparable;
    private boolean title;
    
    public Field(String label, Datas data, Formats format, boolean title, boolean searchable, boolean comparable) {
        this.label = label;
        this.data = data;
        this.format = format;
        this.title = title;
        this.searchable = searchable;
        this.comparable = comparable;             
    }
    
    public Field(String label, Datas data, Formats format) {
        this(label, data, format, false, false, false);
    }
    
    public String getLabel() {
        return label;
    }
    
    public Formats getFormat() {
        return format;
    }
    
    public Datas getData() {
        return data;
    }
    
    public boolean isSearchable() {
        return searchable;
    }
    
    public boolean isComparable() {
        return comparable;
    }
    
    public boolean isTitle() {
        return title;
    }    
}
