/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mensajeriahibernate;

import java.util.Comparator;

/**
 *
 * @author Magali
 */
public class CustomComparator implements Comparator<Usuario> {
    @Override
    public int compare(Usuario o1, Usuario o2) {
        return o1.getMensajesEnviados() - (o2.getMensajesEnviados());
    }
}
