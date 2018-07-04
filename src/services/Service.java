package services;

import java.util.ArrayList;

public interface Service<E> {
	public void deleteById(String id);
	public boolean add(E obj);
	public void update(E obj);
	public E getById(String id);
	public ArrayList<E> getAll();

}
