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

package com.openbravo.pos.sales;

/**
 *
 * @author adrianromero
 */
public class TaxesException extends Exception {

    /**
     * Creates a new instance of <code>TaxesException</code> without detail message.
     */
    public TaxesException(Throwable t) {
        super(t);
    }
    
    public TaxesException(String msg, Throwable t) {
        super(msg, t);
    }
    /**
     * Constructs an instance of <code>TaxesException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public TaxesException(String msg) {
        super(msg);
    }
}
