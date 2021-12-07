/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entreprise.expo.jms;

import app.entreprise.menuiserieshared.entities.Affaire;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Josselin
 */
public class EnvoiNouvelleAffaire {
    
    public static void main(String[] args) {

        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        String destName = "NouvelleAffaireFile";
        Destination dest = null;
        Session session = null;
        MessageProducer sender = null;

        /*
         * Alimentation d'une liste de titres
         */
        
        Affaire affaire = new Affaire(0,"Jean",null,null,null,null,null);
        affaire.setIdAffaire(0);
        affaire.setNomClient("Jean");
        
        try {
            // create the JNDI initial context
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);

            // look up the Destination
            dest = (Destination) context.lookup(destName);

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // create the Producer
            sender = session.createProducer(dest);

            // start the connection, to enable message sending
            connection.start();

            // envoi de l'affaire
            ObjectMessage message = session.createObjectMessage();
            message.setObject(affaire);
            sender.send(message);
            
        } catch (Exception ex) {
            System.out.println("Erreur lors de l'envoi");
        }
    }
    
    
    
}
