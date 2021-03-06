package uk.ac.ox.it.skossuggester.representations;

import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Objects;
import org.apache.solr.common.SolrDocument;

/**
 * Represents a skos:concept
 * @author martinfilliau
 */
public class SkosConcept {
    
    private String uri;
    private String prefLabel;           // main/preferred label
    private List<String> altLabels;
    private List<Related> related;      // related skos:concepts

    public SkosConcept() {
        this.altLabels = new ArrayList<>();
        this.related = new ArrayList<>();
    }
    
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPrefLabel() {
        return prefLabel;
    }

    public void setPrefLabel(String prefLabel) {
        this.prefLabel = prefLabel;
    }

    public List<String> getAltLabels() {
        return altLabels;
    }

    public void setAltLabels(List<String> altLabels) {
        this.altLabels = altLabels;
    }

    public List<Related> getRelated() {
        return related;
    }

    public void setRelated(List<Related> related) {
        this.related = related;
    }
    
    /**
     * Add a Related concept to the concept
     * @param related Related
     */
    public void addRelated(Related related) {
        this.related.add(related);
    }
    
    /**
     * Add an alternative label to the concept
     * @param label String
     */
    public void addAltLabel(String label) {
        this.altLabels.add(label);
    }

    /**
     * Get a SkosConcept from a SolrDocument
     * @param doc SolrDocument
     * @return SkosConcept
     */
    public static SkosConcept fromSolr(SolrDocument doc) {
        SkosConcept skos = new SkosConcept();
        skos.setUri((String) doc.getFieldValue("uri"));
        if(doc.containsKey("prefLabel")) {
            skos.setPrefLabel((String) doc.getFieldValue("prefLabel"));
        }

        if(doc.containsKey("altLabels")) {
            for(Object o : doc.getFieldValues("altLabels")) {
                skos.addAltLabel(o.toString());
            }
        }

        // related labels and URIs are stored as two multi-values properties
        // in the same document. There is no explicit ordering, but it should
        // always be in the same order than originally entered:
        // hence relatedLabel and relatedUri should be retrieved in the same order
        if(doc.containsKey("relatedLabels") && doc.containsKey("relatedUris")) {
            List<Object> relatedLabels = new ArrayList(doc.getFieldValues("relatedLabels"));
            List<Object> relatedUris = new ArrayList(doc.getFieldValues("relatedUris"));

            for (int i = 0; i < relatedLabels.size(); i++) {
                String label = (String)relatedLabels.get(i);
                String uri = (String)relatedUris.get(i);
                skos.addRelated(new Related(label, uri));
            }
        }
        return skos;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SkosConcept)) return false;
        SkosConcept c = (SkosConcept)obj;
        return this.uri.equals(c.uri)
                && this.prefLabel.equals(c.prefLabel)
                && this.altLabels.equals(c.altLabels)
                && this.related.equals(c.related);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri, prefLabel, altLabels, related);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("uri", this.uri).toString();
    }

}