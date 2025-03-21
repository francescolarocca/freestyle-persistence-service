package it.muretti.micro.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)

public class Rapper implements Serializable {
	
    private String nome;
    private double rank;
    private List<Presenza> presenze;
    private String bio;
    private String avatarUrl;
    private String spotifyLink;
    private String soundcloudLink;
    private String instagramLink;
    
    
    public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public String getSpotifyLink() {
		return spotifyLink;
	}
	public void setSpotifyLink(String spotifyLink) {
		this.spotifyLink = spotifyLink;
	}
	public String getSoundcloudLink() {
		return soundcloudLink;
	}
	public void setSoundcloudLink(String soundcloudLink) {
		this.soundcloudLink = soundcloudLink;
	}
	public String getInstagramLink() {
		return instagramLink;
	}
	public void setInstagramLink(String instagramLink) {
		this.instagramLink = instagramLink;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}
	public List<Presenza> getPresenze() {
		return presenze;
	}
	public void setPresenze(List<Presenza> presenze) {
		this.presenze = presenze;
	}
	
    
    
}
