package com.example.denis.mytvshow.data;

import java.util.ArrayList;

/**
 * Created by Denis on 27/01/2018.
 */

public class RootObject
{
    private int page;

    public int getPage() { return this.page; }

    public void setPage(int page) { this.page = page; }

    private int total_results;

    public int getTotalResults() { return this.total_results; }

    public void setTotalResults(int total_results) { this.total_results = total_results; }

    private int total_pages;

    public int getTotalPages() { return this.total_pages; }

    public void setTotalPages(int total_pages) { this.total_pages = total_pages; }

    private ArrayList<Serie> results;

    public ArrayList<Serie> getResults() { return this.results; }

    public void setResults(ArrayList<Serie> results) { this.results = results; }
}
