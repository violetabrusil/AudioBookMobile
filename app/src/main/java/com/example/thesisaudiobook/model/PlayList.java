package com.example.thesisaudiobook.model;

import java.util.Set;

public class PlayList {

    private Integer idPlayList;
    private String namePlayList;
    private String userId;
    private Set<IdAudioBook> idAudioBooks;

    public PlayList() {
    }

    public PlayList(Integer idPlayList, String namePlayList, String userId, Set<IdAudioBook> idAudioBooks) {
        this.idPlayList = idPlayList;
        this.namePlayList = namePlayList;
        this.userId = userId;
        this.idAudioBooks = idAudioBooks;
    }

    public Integer getIdPlayList() {
        return idPlayList;
    }

    public void setIdPlayList(Integer idPlayList) {
        this.idPlayList = idPlayList;
    }

    public String getNamePlayList() {
        return namePlayList;
    }

    public void setNamePlayList(String namePlayList) {
        this.namePlayList = namePlayList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<IdAudioBook> getIdAudioBooks() {
        return idAudioBooks;
    }

    public void setIdAudioBooks(Set<IdAudioBook> idAudioBooks) {
        this.idAudioBooks = idAudioBooks;
    }

    @Override
    public String toString() {
        return "PlayList{" +
                "idPlayList=" + idPlayList +
                ", namePlayList='" + namePlayList + '\'' +
                ", userId='" + userId + '\'' +
                ", idAudioBooks=" + idAudioBooks +
                '}';
    }
}
