/**
 * Copyright © ${project.inceptionYear} MIAGE de Toulouse (cedric.teyssie@miage.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.entreprise.services;

import app.entreprise.expo.jms.EnvoiAffaire;
import app.entreprise.menuiserieshared.entities.Affaire;
import app.entreprise.menuiserieshared.exceptions.AffaireExistanteException;
import app.entreprise.menuiserieshared.exceptions.AffaireInconnueException;
import app.entreprise.metier.AffaireBusinessLocal;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * Classe regroupant les services pouvant être exposés à l'extérieur du coeur de l'application de la bourse.
 *
 *
 * @author Cédric Teyssié  <cedric.teyssie@irit.fr>, IRIT-SIERA, Université Paul Sabatier
 * @version 1.1, 11 oct. 2019
 * @since 0.1, 3 oct. 2016
 */
@Stateless
public class ServicesAffaire implements ServicesAffaireLocal {

    /**
     * EJB de Log dans JMS. Pour l'exemple...
     */
    @EJB
    private EnvoiAffaire envoiAffaire;

    /**
     * EJB métier de la bourse
     */
    @EJB
    private AffaireBusinessLocal affaireBusiness;
    /**
     * Convertisseur Objet JSON et inversement)
     */
    private Gson gson;

    /**
     * Constructeur par défaut de l'exposition
     */
    public ServicesAffaire() {
        this.gson = new Gson();
    }

    /**
     * Ajoute un titre boursier à la base de titres à partie d'un JSON.
     *
     * @param t Titre Boursier au format JSON complet à ajouter. Ce titre n'a pas besoin d'être complet. A minima, mnemonique, nom et cours.
     *
     * @return JSON du Titre boursier ajouté en base
     *
     * @throws TitreExistantException  Levée si le titre existe déjà en base
     * @throws TitreIncorrectException Levée si le titre est mal formatté. Ex: Pas de mnemonique.
     */
    @Override
    public String ajouterAffaire(String a) throws AffaireExistanteException {
        try {
            System.out.println(a);
            Affaire affaire = this.gson.fromJson(a, Affaire.class);
            // titrevalide permet de positionner correctement la date de prise en compte de la cotation
            Affaire affairevalide = new Affaire(affaire.getIdAffaire(), affaire.getNomClient(), affaire.getPrenomClient(), affaire.getAdressePostale(), affaire.getMail(), affaire.getTelephone(), affaire.getAdresseLivraison());

            // Envoi vers le service commercial
            this.envoiAffaire.send(affairevalide, "NouvelleAffaireFile");

            return this.gson.toJson(this.affaireBusiness.ajouterAffaire(affairevalide));
        } catch (JsonSyntaxException e) {
            System.out.println("Erreur - Ajout de l'affaire à échoué");
            return("Erreur - Ajout de l'affaire à échoué");
        }
    }

    /**
     * Supprime un titre boursier de la base
     *
     * @param mnemo mnemonique du titre à supprimer
     *
     * @throws TitreInconnuException Levée si le titre est absent de la base
     */
    @Override
    public void supprimerAffaire(int id) throws AffaireInconnueException {
        Affaire a = this.affaireBusiness.getAffaire(id);
        this.affaireBusiness.retraitAffaire(a);
    }

    /**
     * Retourne un titre boursier en base à partir de sa mnémonique
     *
     * @param mnemo Mnémonique du titre à rechercher
     *
     * @return JSON du Titre Boursier recherché
     *
     * @throws TitreInconnuException Levée si le titre est absent de la base
     */
    @Override
    public String getAffaire(int id) throws AffaireInconnueException {
        return this.gson.toJson(this.affaireBusiness.getAffaire(id));
    }

    /**
     * Retourne la liste des mnenomique des titres en base au format JSON.
     *
     * @return Collection des mnemoniques
     *
     * @throws BasedeTitresVideException Levée si la liste de titres est vide
     */
    @Override
    public String getListeAffaires() {
        Collection<Integer> listeaffaires = this.affaireBusiness.getListeAffaires();
        if (listeaffaires.isEmpty()) {
            return("Liste vide");
        }
        return this.gson.toJson(listeaffaires);
    }

    /**
     * Met à jour le cours d'un Titre Boursier en base à partir d'un objet titre JSON
     *
     * @param t Titre boursier au format JSON à mettre à jour avec les valeurs de cours actualisées.
     *
     * @return JSON du Titre Boursier mis à jour
     *
     * @throws TitreInconnuException Levée si le titre est absent de la base
     */
    @Override
    public String majAffaire(String a) throws AffaireInconnueException {
        try {
            Affaire affaire = this.gson.fromJson(a, Affaire.class);
            this.affaireBusiness.majAffaire(affaire);
            return this.gson.toJson(this.affaireBusiness.getAffaire(affaire.getIdAffaire()));
        } catch (AffaireInconnueException ex) {
            throw new AffaireInconnueException();
        }
    }
}
