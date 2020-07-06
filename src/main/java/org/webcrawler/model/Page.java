package org.webcrawler.model;

import java.util.List;

/**
 * Light page information
 */
public class Page {
    private List<String> links;
    private String body;

    public Page(List<String> links, String body) {
        this.links = links;
        this.body = body;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Page{" +
                "links=" + links +
                ", body='" + body + '\'' +
                '}';
    }
}