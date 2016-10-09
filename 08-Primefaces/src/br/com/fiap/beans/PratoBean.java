package br.com.fiap.beans;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.fiap.dao.PratoDAO;
import br.com.fiap.dao.impl.PratoDAOImpl;
import br.com.fiap.entity.Prato;
import br.com.fiap.exceptions.DBCommitException;
import br.com.fiap.singleton.EMFactorySingleton;

@ManagedBean
@SessionScoped
public class PratoBean {
	
	private PratoDAO dao;
	
	private Prato prato;
	
	@PostConstruct
	private void init(){
		dao = new PratoDAOImpl(EMFactorySingleton.getInstance());
		prato = new Prato();
	}
	
	//Método para gravar a foto
	public void subirFoto(FileUploadEvent event){
		File arquivo = new File("C://foto//",event.getFile().getFileName());
		//Gravar informação no arquivo
		FileOutputStream output;
		try {
			output = new FileOutputStream(arquivo);
			output.write(event.getFile().getContents());
			output.close();
			//Grava o nome do arquivo de imagem na entidade
			prato.setFoto(event.getFile().getFileName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void cadastrar(){
		FacesMessage msg;
		try {
			dao.insert(prato);
			msg = new FacesMessage("Cadastrado.");
		} catch(DBCommitException e){
			e.printStackTrace();
			msg = new FacesMessage("Erro.");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public Prato getPrato() {
		return prato;
	}

	public void setPrato(Prato prato) {
		this.prato = prato;
	}
	
	public StreamedContent getFoto(){
		DefaultStreamedContent foto = new DefaultStreamedContent();
		foto.setContentType("image/jpg");
		try {
			if(prato.getFoto() != null){
				foto.setStream(new FileInputStream("c://foto//"+ prato.getFoto()));
			}else{
				foto.setStream(new FileInputStream("c://foto//semfoto.jpg"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return foto;
	}
	
	
}
