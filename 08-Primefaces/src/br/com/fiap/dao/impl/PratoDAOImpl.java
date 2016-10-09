package br.com.fiap.dao.impl;

import javax.persistence.EntityManager;

import br.com.fiap.dao.PratoDAO;
import br.com.fiap.entity.Prato;

public class PratoDAOImpl extends DAOImpl<Prato, Integer> implements PratoDAO{

	public PratoDAOImpl(EntityManager em) {
		super(em);
	}

}
