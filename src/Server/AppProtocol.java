package Server;

import java.io.IOException;

import Model.Borne;
import Model.Profil;
import Model.Zone;

public interface AppProtocol {


    // QUERIES
	//-------------------------------------------------------list--------------------------------------------------------\\
    public void askListMagasin() throws IOException;
    public void askListBornes() throws IOException;
    public void askListZones() throws IOException;
    
    //-------------------------------------------------------find--------------------------------------------------------\\
    public void askZone(long id) throws IOException;
    public void askBorne(long id) throws IOException;
    public void askProfil(long id) throws IOException;
    
    //-------------------------------------------------------delete--------------------------------------------------------\\
    public void delZone(long id) throws IOException;
    public void delBorne(long id) throws IOException;
    public void delProfil(long id) throws IOException;
    
    //-------------------------------------------------------insert--------------------------------------------------------\\
    public void createZone(Zone zone) throws IOException;
    public void createBorne(Borne borne)throws IOException;
    public void createProfil(Profil profil)throws IOException;
    
    //-------------------------------------------------------update--------------------------------------------------------\\
    public void updateZone(Zone zone) throws IOException;
    public void updateBorne(Borne borne) throws IOException;
    public void updateProfil(Profil profil) throws IOException;
    
    
    

    //-------------------------------------------------------Answers List--------------------------------------------------------\\
    public void sendListZones(String s);
    public void sendListBornes(String s);
    public void sendListMagasin(String s) throws IOException;
}
