/*
 * Copyright (C) 2014 al038513
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.daw.service.generic.specific.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import net.daw.bean.generic.specific.implementation.PublicacionBeanGenSpImpl;
import net.daw.bean.generic.specific.implementation.UsuarioBeanGenSpImpl;
import net.daw.helper.ExceptionBooster;
import net.daw.service.generic.implementation.TableServiceGenImpl;

/**
 *
 * @author al038513
 */
public class PublicacionServiceGenSpImpl extends TableServiceGenImpl {

    public PublicacionServiceGenSpImpl(String strObject, Connection con) {
        super(strObject, con);
    }

    public String duplicate(Integer id) throws Exception {
        String jason = null;
        PublicacionBeanGenSpImpl oPublicacion = new PublicacionBeanGenSpImpl();
        UsuarioBeanGenSpImpl oUsuario = new UsuarioBeanGenSpImpl();

        try {
            oConnection.setAutoCommit(false);
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            jason = this.get(id);
            oPublicacion = gson.fromJson(jason, oPublicacion.getClass());
            oUsuario = oPublicacion.getObj_usuario();
            oPublicacion.setId(0);
            oPublicacion.setId_usuario(oUsuario.getId());
            jason = gson.toJson(oPublicacion);
            jason = this.set(jason);
            oConnection.commit();
        } catch (Exception ex) {
            oConnection.rollback();
            ExceptionBooster.boost(new Exception(this.getClass().getName() + ":set ERROR: " + ex.getMessage()));
        }
        return jason;
    }
}
