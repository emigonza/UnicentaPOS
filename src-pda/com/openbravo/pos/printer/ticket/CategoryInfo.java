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
package com.openbravo.pos.printer.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import java.awt.image.*;
import com.openbravo.data.loader.IKeyed;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.data.loader.SerializerRead;

/**
 *
 * @author  Adrian
 * @version 
 */
public class CategoryInfo implements IKeyed {

    private static final long serialVersionUID = 8612449444103L;
    private String id;
    private String name;
    private BufferedImage m_Image;

    /** Creates new CategoryInfo */
    public CategoryInfo(String id, String name, BufferedImage image) {
        this.id = id;
        this.name = name;
        m_Image = image;
    }

    public CategoryInfo() {
        super();
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public BufferedImage getM_Image() {
        return m_Image;
    }

    public void setM_Image(BufferedImage m_Image) {
        this.m_Image = m_Image;
    }

    public String getM_sID() {
        return this.id;
    }

    public void setM_sID(String m_sID) {
        this.id = m_sID;
    }

    public String getId() {
        return this.id;
    }

    public void setID(String m_sID) {
        this.id = m_sID;
    }

    public String getM_sName() {
        return name;
    }

    public void setM_sName(String m_sName) {
        this.name = m_sName;
    }

    public Object getKey() {
        return id;
    }

    
   

    public String getName() {
        return name;
    }

    public void setName(String sName) {
        name = sName;
    }

    public BufferedImage getImage() {
        return m_Image;
    }

    public void setImage(BufferedImage img) {
        m_Image = img;
    }

    @Override
    public String toString() {
        return name;
    }

    public static SerializerRead getSerializerRead() {
        return new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
            return new CategoryInfo(dr.getString(1), dr.getString(2), ImageUtils.readImage(dr.getBytes(3)));
        }};
    }
}
