package uk.ac.ox.it.skossuggester.representations.hal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author martinfilliau
 */
public class HalLinks {
    
    private HalLink self;

    @JsonProperty("self")
    public HalLink getSelf() {
        return self;
    }

    public void setSelf(HalLink self) {
        this.self = self;
    }
    
}
