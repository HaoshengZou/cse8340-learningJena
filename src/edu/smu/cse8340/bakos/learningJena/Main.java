/*
  Main.java
  This is a simple wrapper for bootstrapping the program.
  @author Yong Joseph Bakos
    
  CSE 8340 Assignment 4: Modeling and querying with Apache Jena and SPARQL.
  TODO: Description
*/
package edu.smu.cse8340.bakos.learningJena;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*; 
import com.hp.hpl.jena.reasoner.*; 
import com.hp.hpl.jena.util.*;

public class Main {

    private final static String ONTOLOGY_URL = "http://lyle.smu.edu/~ybakos/cse8340/a4/foafonto.ttl";
    private final static String DATA_URL = "http://lyle.smu.edu/~ybakos/cse8340/a4/foafdata.ttl";
    private final static String RDF_SYNTAX = "N3";

    public static void main(String[] args) {
        Model dataModel = ModelFactory.createDefaultModel();
        dataModel.read(DATA_URL, RDF_SYNTAX);
        Model schema = ModelFactory.createDefaultModel();
        schema.read(ONTOLOGY_URL, RDF_SYNTAX);

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(schema);
        InfModel inferenceModel = ModelFactory.createInfModel(reasoner, dataModel);
        inferenceModel.write(System.out, RDF_SYNTAX);
    }

}
