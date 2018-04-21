package Server;


import Model.Borne;
import Model.BorneDAO;
import Model.CategorieMagasin;
import Model.Magasin;
import Model.MagasinDAO;
import Model.Personne;
import Model.PersonneDAO;
import Model.Profil;
import Model.ProfilDAO;
import Model.Vente;
import Model.VenteDAO;
import Model.Zone;
import Model.ZoneDAO;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

public class HandleClient implements Runnable,AppProtocol{
	private final Socket s;
	private InputManager in;
	private OutputManager out;
	private boolean stop = false;
	private LoggerWriter logger;
	private Connection c;
	
	
	public HandleClient(Socket s, LoggerWriter logger, Connection c ) throws IOException {
		this.s = s;
		this.logger = logger;
		this.c = c;
	}
	
	public void run() {
		try (Socket s1 = s) {
			out = new OutputManager(s1.getOutputStream());
			in = new InputManager(s1.getInputStream(), this);
			in.doRun();
		} catch (IOException | JSONException ex) {
			if (!stop) {
				finish();
			}
		}
		finally
		{
			ServerCore.pool.releaseConnection(c);
		}
	}
	
	public synchronized void finish(){
		if (!stop) {
			stop = true;
			try {
				s.close();
			} catch (IOException ex) { ex.printStackTrace(); }
			
			this.logger.setMessageLog("Client exit");
		}
	}

	//-------------------------------------------------------list--------------------------------------------------------\\
	
    @Override
    public void askListMagasin() throws IOException {
        MagasinDAO magasinDAO = new MagasinDAO();
        magasinDAO.setConnection(c);
        ArrayList<Magasin> listeMag = new ArrayList<Magasin>();
        listeMag = magasinDAO.getAllMagasins();
        /**** JSON MAPPER ****/
        ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(listeMag);
		out.sendListMagasin(json);
    }
    
	@Override
	public void askListBornes() throws IOException {
		BorneDAO borneDAO = new BorneDAO();
		borneDAO.setConnection(c);
		ArrayList<Borne> listeBorne = new ArrayList<Borne>();
		listeBorne = borneDAO.getAllBornes();
		/**** JSON MAPPER ****/
        ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(listeBorne);
        out.sendListMagasin(json);
	}
	
	@Override
	public void askListZones() throws IOException {
		ZoneDAO zoneDAO = new ZoneDAO();
		zoneDAO.setConnection(c);
		ArrayList<Zone> listeZone = new ArrayList<Zone>();
		listeZone = zoneDAO.getAllZones();
		/**** JSON MAPPER ****/
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(listeZone);
        out.sendListZones(json);
		
	}
	
	@Override
	public void askListVentesClientX(long idClient) throws IOException {
		VenteDAO venteDAO = new VenteDAO();
		venteDAO.setConnection(c);
		ArrayList<Vente> listeVente = new ArrayList<Vente>();
		listeVente = venteDAO.getAllVentesClientX(idClient);
		/**** JSON MAPPER ****/
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(listeVente);
        out.sendListVentesClientX(json);
		
	}
	
	@Override
	public void askAllClient() throws IOException {
		PersonneDAO personneDAO = new PersonneDAO();
		personneDAO.setConnection(c);
		ArrayList<Personne> listePersonne = new ArrayList<Personne>();
		listePersonne = personneDAO.getAllClients();
		/**** JSON MAPPER ****/
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(listePersonne);
        out.sendAllClients(json);
		
	}

	@Override
	public void askAllProfils() throws IOException {
		ProfilDAO profilDAO = new ProfilDAO();
		profilDAO.setConnection(c);
		ArrayList<Profil> listeProfils = new ArrayList<Profil>();
		listeProfils = profilDAO.getAllProfils();
		/**** JSON MAPPER ****/
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(listeProfils);
        out.sendAllProfils(json);
	}
	
	
	
	//-------------------------------------------------------find--------------------------------------------------------\\
	
	@Override
	public void askZone(long id) throws IOException {
		ZoneDAO zoneDAO = new ZoneDAO();
		zoneDAO.setConnection(c);
		Zone zone = new Zone();
		zone = zoneDAO.find(id);
		/**** JSON MAPPER ****/
		
		
	}
	
	    @Override
	public void askBorne(long id) throws IOException {
	    	BorneDAO borneDAO = new BorneDAO();
	    	borneDAO.setConnection(c);
			Borne borne = new Borne();
			borne = borneDAO.find(id);
			/**** JSON MAPPER ****/
			
		
	}
	
	    @Override
	public void askProfil(long id) throws IOException {
	    	ProfilDAO profilDAO = new ProfilDAO();
	    	profilDAO.setConnection(c);
	    	Profil profil = new Profil();
			profil = profilDAO.find(id);
			/**** JSON MAPPER ****/
			
		
	}
	    
	    @Override
		public void askPersonne(long id) throws IOException {
	    		PersonneDAO personneDAO = new PersonneDAO();
		    	personneDAO.setConnection(c);
		    	Personne personne = new Personne();
		    	personne = personneDAO.find(id);
				/**** JSON MAPPER ****/
				
			
		}
	    
	    @Override
		public void askVente(long id) throws IOException {
	    		VenteDAO venteDAO = new VenteDAO();
	    		venteDAO.setConnection(c);
		    	Vente vente = new Vente();
		    	vente = venteDAO.find(id);
				/**** JSON MAPPER ****/
				
			
		}
	    
	    @Override
		public void askCategorieMagasinVenteX(long id) throws IOException {
			VenteDAO venteDAO = new VenteDAO();
			venteDAO.setConnection(c);
			CategorieMagasin cat = new CategorieMagasin();
			cat = venteDAO.findCategorieMagasinVenteX(id);
			/**** JSON MAPPER ****/
					
		}

	  //-------------------------------------------------------delete--------------------------------------------------------\\
	    
	@Override
		public void delZone(long id) throws IOException {
			ZoneDAO zDAO = new ZoneDAO();
			zDAO.setConnection(c);
			Zone zone = new Zone(id, "", "", 0);
			zDAO.delete(zone);
			/**** JSON MAPPER ****/
			;
			
		}

		@Override
		public void delBorne(long id) throws IOException {
			BorneDAO borneDAO = new BorneDAO();
			borneDAO.setConnection(c);
			Borne borne = new Borne(id,new Zone());
			borneDAO.delete(borne);
			/**** JSON MAPPER ****/
	        out.sendListZones("ok");
			
		}
		
		@Override
		public void delProfil(long id) throws IOException {
			ProfilDAO profilDAO = new ProfilDAO();
			profilDAO.setConnection(c);
			Profil profil = new Profil(id,"");
			profilDAO.delete(profil);
			/**** JSON MAPPER ****/
	        out.sendListZones("ok");
			
		}
		
		@Override
		public void delPersonne(long id) throws IOException {
			PersonneDAO personneDAO = new PersonneDAO();
			personneDAO.setConnection(c);
			Personne personne = new Personne(id,"", null, 0, null, 0, null, 0);
			personneDAO.delete(personne);
			/**** JSON MAPPER ****/
	        out.sendListZones("ok");
			
		}
		
		@Override
		public void delVente(long id) throws IOException {
			VenteDAO venteDAO = new VenteDAO();
			venteDAO.setConnection(c);
			Vente vente = new Vente(id, 0, 0, 0, 0, 0, null);
			venteDAO.delete(vente);
			/**** JSON MAPPER ****/
	        out.sendListZones("ok");
			
		}

		//-------------------------------------------------------insert--------------------------------------------------------\\
		
		@Override
		public void createZone(Zone zone) throws IOException {
			ZoneDAO zDAO = new ZoneDAO();
			zDAO.setConnection(c);
			zone = 	zDAO.create(zone);
			/**** JSON MAPPER ****/
			
			
		}

		@Override
		public void createBorne(Borne borne) throws IOException {
			BorneDAO borneDAO = new BorneDAO();
			borneDAO.setConnection(c);
			borne = borneDAO.create(borne);
			/**** JSON MAPPER ****/
			
			
		}
		
		@Override
		public void createProfil(Profil profil) throws IOException {
			ProfilDAO profilDAO = new ProfilDAO();
			profilDAO.setConnection(c);
			profil = profilDAO.create(profil);
			/**** JSON MAPPER ****/
			
			
		}
		
		@Override
		public void createPersonne(Personne personne) throws IOException {
			PersonneDAO personneDAO = new PersonneDAO();
			personneDAO.setConnection(c);
			personne = personneDAO.create(personne);
			/**** JSON MAPPER ****/
			
			
		}
		
		@Override
		public void createVente(Vente vente) throws IOException {
			VenteDAO venteDAO = new VenteDAO();
			venteDAO.setConnection(c);
			vente = venteDAO.create(vente);
			/**** JSON MAPPER ****/
			
			
		}

		//-------------------------------------------------------update--------------------------------------------------------\\
		
		@Override
		public void updateZone(Zone zone) throws IOException {
			ZoneDAO zDAO = new ZoneDAO();
			zDAO.setConnection(c);
			zone = zDAO.update(zone);
			/**** JSON MAPPER ****/
			
			
		}

		@Override
		public void updateBorne(Borne borne) throws IOException {
			BorneDAO borneDAO = new BorneDAO();
			borneDAO.setConnection(c);
			borneDAO.update(borne);
			/**** JSON MAPPER ****/
			
			
		}
		
		@Override
		public void updateProfil(Profil profil) throws IOException {
			ProfilDAO profilDao = new ProfilDAO();
			profilDao.setConnection(c);
			profilDao.update(profil);
			/**** JSON MAPPER ****/
			
			
		}
		
		@Override
		public void updatePersonne(Personne personne) throws IOException {
			PersonneDAO personneDao = new PersonneDAO();
			personneDao.setConnection(c);
			personneDao.update(personne);
			/**** JSON MAPPER ****/
			
			
		}
		
		@Override
		public void updatePersonneProfil(Personne personne) throws IOException {
			PersonneDAO personneDao = new PersonneDAO();
			personneDao.setConnection(c);
			personneDao.updateProfil(personne);
			/**** JSON MAPPER ****/
			
			
		}
		
		@Override
		public void updateVente(Vente vente) throws IOException {
			VenteDAO venteDao = new VenteDAO();
			venteDao.setConnection(c);
			venteDao.update(vente);
			/**** JSON MAPPER ****/
			
			
		}
		
		//-------------------------------------------------------list--------------------------------------------------------\\
		@Override
		public void sendListZones(String s) {

		}

		@Override
		public void sendListBornes(String s) {

		}

		@Override
		public void sendListMagasin(String s) throws IOException {
			
		}
		
		@Override
		public void sendListVentesClientX(String s) throws IOException {
			
		}

		@Override
		public void sendAllClients(String s) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void sendAllProfils(String s) throws IOException {
			// TODO Auto-generated method stub
			
		}
		

	




	// IMPLEMENT METHODS OF PROTOCOL BY USING 'OUT'
	
	
}
