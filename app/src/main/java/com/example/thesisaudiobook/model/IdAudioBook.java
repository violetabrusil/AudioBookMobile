package com.example.thesisaudiobook.model;

public class IdAudioBook {

    private Integer idAudioBookPlayList;
    private String idAudioBook;

    public IdAudioBook() {
    }

    public IdAudioBook(Integer idAudioBookPlayList, String idAudioBook) {
        this.idAudioBookPlayList = idAudioBookPlayList;
        this.idAudioBook = idAudioBook;
    }

    public Integer getIdAudioBookPlayList() {
        return idAudioBookPlayList;
    }

    public void setIdAudioBookPlayList(Integer idAudioBookPlayList) {
        this.idAudioBookPlayList = idAudioBookPlayList;
    }

    public String getIdAudioBook() {
        return idAudioBook;
    }

    public void setIdAudioBook(String idAudioBook) {
        this.idAudioBook = idAudioBook;
    }

    @Override
    public String toString() {
        return "IdAudioBook{" +
                "idAudioBookPlayList=" + idAudioBookPlayList +
                ", idAudioBook='" + idAudioBook + '\'' +
                '}';
    }
}
