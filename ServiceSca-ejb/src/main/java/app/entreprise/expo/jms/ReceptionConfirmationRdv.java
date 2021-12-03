/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entreprise.expo.jms;

import app.entreprise.menuiserieshared.entities.Affaire;
import app.entreprise.menuiserieshared.entities.RdvCommercial;
import app.entreprise.menuiserieshared.expo.jms.EnvoiAffaire;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author Josselin
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "ConfirmationRdvFile")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ReceptionConfirmationRdv implements MessageListener {
    
    public ReceptionConfirmationRdv() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage om = (ObjectMessage) message;
                Affaire affaire = (Affaire) om.getObject();
                System.out.println("Reçu dans le service chargés d'affaires: " + affaire.toString());
                
                // Enregistrement BD
                
            } else if (message != null) {
                System.out.println("Received non object message");
            } else {
                System.out.println("???");
            }
        } catch(Exception ex){
            System.out.println("Erreur lors de la réception : ");
        }
    }
    
}
