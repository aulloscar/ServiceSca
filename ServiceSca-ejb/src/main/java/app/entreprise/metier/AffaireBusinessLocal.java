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
package app.entreprise.metier;

import app.entreprise.menuiserieshared.entities.Affaire;
import app.entreprise.menuiserieshared.exceptions.AffaireExistanteException;
import app.entreprise.menuiserieshared.exceptions.AffaireInconnueException;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * Interface LOCALE exposant les méthodes métiers de la bourse, principalement les méthodes de CRUD.
 *
 *
 * @author Cédric Teyssié  <cedric.teyssie@irit.fr>, IRIT-SIERA, Université Paul Sabatier
 * @version 1.1, 11 oct. 2019
 * @since 0.1, 3 oct. 2016
 */
@Local
public interface AffaireBusinessLocal {

    /**
     * Ajoute un titre à la base de titres.
     *
     * @param t Titre Boursier complet à ajouter. Ce titre doit être complet. Tous les attributs du titre doivent être fournis. Aucune
     *          vérification de complétude n'est faite ici.
     *
     * @return Titre boursier ajouté en base
     *
     * @throws TitreExistantException  Levée si le titre existe déjà en base
     * @throws TitreIncorrectException Levée si le titre est mal formatté. Ex: Pas de mnemonique.
     */
    public Affaire ajouterAffaire(Affaire a) throws AffaireExistanteException;

    /**
     * Supprime un titre de la base
     *
     * @param t titre Boursier à supprimer
     *
     * @throws TitreInconnuException Levée si le titre est absent de la base
     */
    public void retraitAffaire(Affaire a) throws AffaireInconnueException;

    /**
     * Retourne un titre en base à partir de sa mnémonique
     *
     * @param mnemo Mnémonique du titre à rechercher
     *
     * @return Titre Boursier
     *
     * @throws TitreInconnuException Levée si le titre est absent de la base
     */
    public Affaire getAffaire(int id) throws AffaireInconnueException;

    /**
     * Retourne la liste des mnenomique des titres en base
     *
     * @return Collection des mnemoniques
     */
    public Collection<Integer> getListeAffaires();

    /**
     * Met à jour le cours d'un Titre Boursier en base à partir d'un objet Titre pré-rempli
     *
     * @param titre Titre boursier à mettre à jour avec les valeurs de cours actualisées.
     *
     * @return Titre Boursier mis à jour
     *
     * @throws TitreInconnuException Levée si le titre est absent de la base
     */
    public Affaire majAffaire(Affaire a) throws AffaireInconnueException;

}
