package server.model;


public class CategorieMagasin {

	private long id;
	private String nom;
	
	
	public CategorieMagasin(){
		
	}
	
	public CategorieMagasin(long id, String nom){
		this.id = id;
		this.nom = nom;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
}
