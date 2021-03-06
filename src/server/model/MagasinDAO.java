package server.model;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.Article;
import common.Magasin;
import common.Profil;

public class MagasinDAO extends DAO<Magasin>{
	private DAO<CategorieArticle> DAOca = new CategorieArticleDAO();	
	//private DAO<CategorieMagasin> DAOcm = new CategorieMagasinDAO();
	
//*****************************************Extended methods of DAO*********************************************************	
	
	@Override
	public Magasin find(long id) {
		
		Magasin magasin = new Magasin();
		
		
		Statement st =null;
		ResultSet rs =null;
		
		
		try {
			st = this.connect.createStatement();
			String sql = "SELECT * FROM Magasin WHERE id="+id;
			rs = st.executeQuery(sql);
			
			if(rs.first()) {
				magasin = new Magasin(rs.getInt("id"),
										rs.getString("logo"), 
										rs.getString("nom"), 
										rs.getString("description"), 
										rs.getInt("idEmplacement"), 
										rs.getInt("idCategorieMagasin")); 
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return magasin;
		
		
		
	}

	@Override
	public Magasin create(Magasin obj) {
		Statement st =null;
		try {
			st = this.connect.createStatement();
			String sql = "INSERT INTO Magasin values(NULL,NULL,'"+obj.getNom()+"',"+obj.getIdEmplacement()+","+obj.getIdCategorieMagasin()+",'"+obj.getDescription()+"')";
			System.out.println(sql);
			st.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	@Override
	public Magasin update(Magasin obj) {
		Statement st =null;
		try {
			st = this.connect.createStatement();
			String sql = "Update Magasin set NULL,NULL,"+obj.getNom()+","+obj.getIdEmplacement()+","+obj.getIdCategorieMagasin()+","+obj.getDescription()+" where id="+obj.getId();
			st.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}

	@Override
	public void delete(Magasin obj) {
		Statement st =null;
		try {
			st = this.connect.createStatement();
			String sql = "DELETE FROM Magasin where id="+obj.getId();
			st.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
//***********************************************************************************************************************	
	public void chargerListeProduit(Magasin obj){
		
		ArrayList<Article> liste = new ArrayList<Article>();
		
		
		Statement st = null;
		ResultSet rs = null;
		
		
		try {
			st = this.connect.createStatement();
			String sql = "SELECT * FROM Article A JOIN StockMagasin SM ON A.id = SM.idArticle WHERE SM.idMagasin="+obj.getId();
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				liste.add(new Article(rs.getInt("A.id"),
										rs.getString("A.nom"), 
										rs.getString("A.description"), 
										rs.getString("A.image"), 
										rs.getInt("A.poids"), 
										rs.getString("A.provenance"), 
										DAOca.find(rs.getInt("idcategorie")),
										rs.getDouble("SM.prixUnitaire"))); 
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		obj.setListeProduits(liste);
	}
	
	public ArrayList<Magasin> getAllMagasins(){
		
		ArrayList<Magasin> liste = new ArrayList<Magasin>();
		
		
		Statement st = null;
		ResultSet rs = null;
		
		
		try {
			st = this.connect.createStatement();
			String sql = "SELECT * FROM Magasin ";
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				liste.add(new Magasin(rs.getInt("id"),
						rs.getString("logo"), 
						rs.getString("nom"), 
						rs.getString("description"), 
						rs.getInt("idEmplacement"), 
						rs.getInt("idCategorieMagasin")));  
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}

//*************************************Get list of shops according to the category selected*************************************
public ArrayList<CategorieMagasin> getCategorieMagasin(){
		
		ArrayList<CategorieMagasin> liste = new ArrayList<CategorieMagasin>();
		
		
		Statement st = null;
		ResultSet rs = null;
		
		
		try {
			st = this.connect.createStatement();
			String sql = "SELECT * FROM CategorieMagasin ";
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				liste.add(new CategorieMagasin(rs.getInt("id"),
									  rs.getString("nom"))
										); 
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}
	
	public ArrayList<Magasin> getMagasinByCategorie(long id){
		
		ArrayList<Magasin> liste = new ArrayList<Magasin>();
		
		
		Statement st = null;
		ResultSet rs = null;
		
		
		try {
			st = this.connect.createStatement();
			String sql = "SELECT * FROM Magasin where idCategorieMagasin="+id;
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				liste.add(new Magasin(rs.getInt("id"),
						rs.getString("logo"), 
						rs.getString("nom"), 
						rs.getString("description"), 
						rs.getInt("idEmplacement"), 
						rs.getInt("idCategorieMagasin")));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}

	public ArrayList<Magasin> getMagasin(Profil p){
		
		ArrayList<Magasin> liste = new ArrayList<Magasin>();
		
		
		Statement st = null;
		ResultSet rs = null;
		
		
		try {
			st = this.connect.createStatement();
			String sql = "Select M.id,M.nom,M.idEmplacement from Profil P, ProfilCat PC, Magasin M where P.id=PC.idProfil and P.id="+p.getId()+" and M.idCategorieMagasin=PC.idCategorieMagasin ";
			rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				liste.add(new Magasin(rs.getInt("id"),
									  "", rs.getString("nom"), "", rs.getInt("idEmplacement"), 0)
										); 
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}
	
	public Magasin getMagasinDAchat(long idAchat) {
		Magasin magasin = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			st = this.connect.createStatement();
			String sql = "SELECT * FROM Magasin,Employe,Achat WHERE Employe.idMagasin=Magasin.id AND Achat.idEmploye=Employe.id AND Achat.id="
					+ idAchat;
			rs = st.executeQuery(sql);

			if (rs.first()) {
				magasin = new Magasin(rs.getInt("id"), rs.getString("logo"), rs.getString("nom"),
						rs.getString("description"), rs.getInt("idEmplacement"), rs.getInt("idCategorieMagasin"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return magasin;
	}
	
}
