package client.controller;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import common.Magasin;



public class ListenerMagasin implements MouseListener {

	private AppGestionProduit c;
	private Magasin m;
	
	public ListenerMagasin(AppGestionProduit c, Magasin m) {
	this.c = c;
	this.m=m;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel l = (JLabel) e.getSource();
		for(Component la : l.getParent().getComponents()){
			la.setForeground(Color.BLACK);
		}
		l.setForeground(Color.RED);
		c.selectedMagasin(m);
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
