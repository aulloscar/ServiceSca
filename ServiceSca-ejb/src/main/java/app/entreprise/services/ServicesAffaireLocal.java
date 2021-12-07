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


import app.entreprise.menuiserieshared.exceptions.AffaireExistanteException;
import app.entreprise.menuiserieshared.exceptions.AffaireInconnueException;
import javax.ejb.Local;

/**
 *
 * Interface LOCALE regroupant le métier de la bourse, principalement les méthodes de CRUD.
 *
 *
 * @author Cédric Teyssié  <cedric.teyssie@irit.fr>, IRIT-SIERA, Université Paul Sabatier
 * @version 1.1, 11 oct. 2019
 * @since 0.1, 3 oct. 2016
 */
@Local
public interface ServicesAffaireLocal {

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
    public String ajouterAffaire(String a);


    /**
     * Supprime un titre boursier de la base
     *
     * @param mnemo mnemonique du titre à supprimer
     *
     * @throws TitreInconnuException Levée si le titre est absent de la base
     */
    public void supprimerAffaire(int id) throws AffaireInconnueException;

    /**
     * Retourne un titre boursier en base à partir de sa mnémonique
     *
     * @param mnemo Mnémonique du titre à rechercher
     *
     * @return JSON du Titre Boursier recherché
     *
     * @throws TitreInconnuException Levée si le titre est absent de la base
     */
    public String getAffaire(int id) throws AffaireInconnueException;

    /**
     * Retourne la liste des mnenomique des titres en base au format JSON.
     *
     * @return Collection des mnemoniques
     *
     * @throws BasedeTitresVideException Levée si la liste de titres est vide
     */
    public String getListeAffaires();

    /**
     * Met à jour le cours d'un Titre Boursier en base à partir d'un objet titre JSON
     *
     * @param t Titre boursier au format JSON à mettre à jour avec les valeurs de cours actualisées.
     *
     * @return JSON du Titre Boursier mis à jour
     *
     * @throws TitreInconnuException Levée si le titre est absent de la base
     */
    public String majAffaire(String a) throws AffaireInconnueException;

}
