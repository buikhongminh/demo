package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Column(name = "publication_name", nullable = false)
    @JsonProperty("publication_name")
    private String publicationName;
    @Column(name = "publication_year", nullable = false)
    @JsonProperty("publication_year")
    private String publicationYear;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;
    @OneToOne(cascade = CascadeType.ALL)
    private Image image;
    private Integer price;
    public Book() {
    }
    public Book(Integer id, String title, String publicationName, String publicationYear, String content, Author author, Image image, Integer price) {
        this.id = id;
        this.title = title;
        this.publicationName = publicationName;
        this.publicationYear = publicationYear;
        this.content = content;
        this.author = author;
        this.image = image;
        this.price = price;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPublicationName() {
        return publicationName;
    }
    public void setPublicationName(String publicationName) {
        this.publicationName = publicationName;
    }
    public String getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Author getAuthor() {
        return author;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
}