/*
  Main.java
  This is a simple wrapper for bootstrapping the program.
  @author Yong Joseph Bakos
    
  CSE 8340 Assignment 4: Modeling and querying with Apache Jena and SPARQL.
  TODO: Description
*/
package edu.smu.cse8340.bakos.learningJena;

import java.util.*;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*; 
import com.hp.hpl.jena.reasoner.*; 
import com.hp.hpl.jena.util.*;

public class Main {

    private final static String RDF_SYNTAX = "N3";
    private final static String ONTOLOGY_URL = "http://lyle.smu.edu/~ybakos/cse8340/a4/foafonto.ttl";
    private final static String DATA_URL = "http://lyle.smu.edu/~ybakos/cse8340/a4/foafdata.ttl";
    private final static String[] CLASS_MEMBER_URLS = {
        "http://lyle.smu.edu/~lincolns/cse8340/a4/foafdata.ttl",
        "http://lyle.smu.edu/~stekula/cse8340/foafdata.ttl",
        "http://lyle.smu.edu/~shanka/cse8340/a4/foafdata.ttl",
        "http://lyle.smu.edu/~smaradi/cse8340/a4/foafdata.ttl",
        "http://lyle.smu.edu/~mingruic/cse8340/foafdata.ttl",
        "http://lyle.smu.edu/~rmandhare/cse8340/a4/foafdata.ttl",
        "https://lyle.smu.edu/~sthathapudi/cse8340/a4/foafdata.ttl",
        "http://lyle.smu.edu/~huij/cse8340/a4/foafdata.ttl",
        "http://lyle.smu.edu/~zhaoc/cse8340/a4/foafdata.TTL",
        "http://lyle.smu.edu/~tfedorov/cse8340/a4/foafdata.ttl",
        "http://lyle.smu.edu/~stekula/cse8340/a4/foafdata.ttl",
        "http://lyle.smu.edu/~mjhaveri/cse8340/a4/foafdata.ttl"
    };

    private final static String SYMMETRY_QUERY =
        "BASE <http://lyle.smu.edu/cse8340#>\n" + 
        "PREFIX rdf:      <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
        "PREFIX rdfs:     <http://www.w3.org/2000/01/rdf-schema#>\n" +
        "PREFIX foaf:     <http://xmlns.com/foaf/spec/>\n" +
        "PREFIX frbr:     <http://purl.org/vocab/frbr/core#>\n" +
        "PREFIX dc:       <http://purl.org/dc/elements/1.1/>\n" +
        "PREFIX film:     <http://data.linkedmdb.org/page/movie/film/>\n" +
        "PREFIX actor:    <http://data.linkedmdb.org/resource/actor/>\n" +
        "PREFIX rel:      <http://purl.org/vocab/relationship/>\n" +
        "PREFIX xsd:      <http://www.w3.org/2001/XMLSchema#>\n" +
        "PREFIX owl:      <http://www.w3.org/2002/07/owl#>\n" +
        "PREFIX dbpedia:  <http://dbpedia.org/ontology#>\n" +
        "PREFIX :         <#>\n" +
        "SELECT ?x\n" +
        "WHERE {\n" +
            "?x foaf:knows ?someone\n" +
        "}";


    public static void main(String[] args) {
        Model dataModel = ModelFactory.createDefaultModel();
        dataModel.read(DATA_URL, RDF_SYNTAX);
        Model schema = ModelFactory.createDefaultModel();
        schema.read(ONTOLOGY_URL, RDF_SYNTAX);

        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(schema);
        InfModel inferenceModel = ModelFactory.createInfModel(reasoner, dataModel);
        //inferenceModel.write(System.out, RDF_SYNTAX);
        System.out.println("6. OWL Inferencing of foaf:knows symmetry.\n");
        System.out.println(SYMMETRY_QUERY);
        Query query = QueryFactory.create(SYMMETRY_QUERY);
        QueryExecution qe = QueryExecutionFactory.create(query, inferenceModel);
        ResultSet rs = qe.execSelect();
        System.out.println("Result:");
        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            System.out.println(qs);
        }
    }

}
