package com.ipv.reporsitory;

public interface CustomizedRepository<E> {
	public void detach(E entity);
}
