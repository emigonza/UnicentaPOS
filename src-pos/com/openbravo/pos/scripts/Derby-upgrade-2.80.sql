--    uniCenta oPOS - Touch Friendly Point Of Sale
--    Copyright (C) 2011 uniCenta
--    http://www.unicenta.net
--
--    This file is part of uniCenta oPOS.
--
--    uniCenta oPOS is free software: you can redistribute it and/or modify
--    it under the terms of the GNU General Public License as published by
--    the Free Software Foundation, either version 3 of the License, or
--    (at your option) any later version.
--
--    uniCenta oPOS is distributed in the hope that it will be useful,
--    but WITHOUT ANY WARRANTY; without even the implied warranty of
--    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--    GNU General Public License for more details.
--
--    You should have received a copy of the GNU General Public License
--    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.

-- Database upgrade script for DERBY
-- v2.60 - v2.80


-- **** THESE ARE THE ADDITIONAL RESOURCES REQUIRED FOR 2.80 UPGRADE **** ---
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('100', 'img.discount', 1, $FILE{/com/openbravo/pos/templates/img.discount.png});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('101', 'img.kit_print', 1, $FILE{/com/openbravo/pos/templates/img.kit_print.png});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('102', 'img.refundit', 1, $FILE{/com/openbravo/pos/templates/img.refundit.png});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('103', 'Printer.TicketKitchen', 0, $FILE{/com/openbravo/pos/templates/Printer.TicketKitchen.xml});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('104', 'script.Event.Total', 0, $FILE{/com/openbravo/pos/templates/script.Event.Total.txt});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('105', 'script.linediscount', 0, $FILE{/com/openbravo/pos/templates/script.linediscount.txt});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('106', 'script.Refundit', 0, $FILE{/com/openbravo/pos/templates/script.Refundit.txt});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('107', 'script.SendOrder', 0, $FILE{/com/openbravo/pos/templates/script.SendOrder.txt});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('108', 'script.SetPerson', 0, $FILE{/com/openbravo/pos/templates/script.SetPerson.txt});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('109', 'script.StockCurrentAdd', 0, $FILE{/com/openbravo/pos/templates/script.StockCurrentAdd.txt});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('110', 'script.StockCurrentSet', 0, $FILE{/com/openbravo/pos/templates/script.StockCurrentSet.txt});
 INSERT INTO RESOURCES(ID, NAME, RESTYPE, CONTENT) VALUES('111', 'script.totaldiscount', 0, $FILE{/com/openbravo/pos/templates/script.totaldiscount.txt});

ALTER TABLE PRODUCTS ADD ISKITCHEN SMALLINT DEFAULT 0 NOT NULL;
ALTER TABLE PRODUCTS ADD PRINTKB SMALLINT DEFAULT 0 NOT NULL;
ALTER TABLE PRODUCTS ADD SENDSTATUS SMALLINT DEFAULT 0 NOT NULL;
ALTER TABLE PRODUCTS ADD ISSERVICE SMALLINT DEFAULT 0 NOT NULL;

ALTER TABLE RECEIPTS ADD PERSON VARCHAR(256);

ALTER TABLE PAYMENTS ALTER COLUMN NOTES VARCHAR(256);

-- final script
DELETE FROM SHAREDTICKETS;

UPDATE APPLICATIONS SET NAME = $APP_NAME{}, VERSION = $APP_VERSION{} WHERE ID = $APP_ID{};