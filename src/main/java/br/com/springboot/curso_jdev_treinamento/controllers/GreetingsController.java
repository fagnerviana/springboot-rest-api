package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
    //IC , CD ou CDI - Injeção de dependencia 
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/**
     *
     * @param name the name to greet
     * @return greeting text
     */
		
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso JDev SpringBoot" + name + "!";
    }
    
    //Criação de mapeamento utilizando o methodo GET
    @RequestMapping(value="/olamundo/{nome}",method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario); //grava no banco de dados
    	
    	return "Ola mundo "+nome;
    }
    
    @GetMapping(value ="listatodos") //nosso primeiro metodos de API
    @ResponseBody //Retorna os dados para ao corpo da resposta
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    	List<Usuario> usuarios = usuarioRepository.findAll();
		
    	return new ResponseEntity<List<Usuario>>(usuarios,HttpStatus.OK);
    	
    	
    }
}
