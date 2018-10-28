package com.netcracker.learnera.entity.media;

import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URL;

@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @Column(name = "bytes")
    private String bytes;

    @Column(name = "url")
    private String url;

    public File() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public URL getUrlAsUrl() throws MalformedURLException {
        return new URL(url);
    }

    public void setUrlAsUrl(URL url) {
        this.url = url.toString();
    }
}
