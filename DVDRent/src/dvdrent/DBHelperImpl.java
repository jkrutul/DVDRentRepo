package dvdrent;

import java.awt.List;

import models.ClientModel;
import models.DvdModel;

public class DBHelperImpl implements DBHelperInterface{

	@Override
	public Integer addClient(ClientModel client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeClient(Integer clientId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Integer addDVD(DvdModel dvd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeDVD() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List listClients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listDvds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listRentedDvds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List listClientWhoRentSomething() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel findClient(Integer cliendId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DvdModel findDvd(Integer dvdId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean rentDvd(Integer clientId, Integer dvdId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean returnDvd(Integer dvdId) {
		// TODO Auto-generated method stub
		return false;
	}

}
