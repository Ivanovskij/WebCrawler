package org.webcrawler.model;

import java.util.List;

public class Page {
    private List<String> links;
    private String text;

    public Page(List<String> links, String text) {
        this.links = links;
        this.text = text;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Page{" +
                "links=" + links +
                ", text='" + text + '\'' +
                '}';
    }
}