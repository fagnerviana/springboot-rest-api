package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    	//usuario.setIdade(idade);
    	
    	usuarioRepository.save(usuario); //grava no banco de dados
    	
    	return "Ola mundo "+nome;
    }
    
    @GetMapping(value ="listatodos") //nosso primeiro metodos de API
    @ResponseBody //Retorna os dados para ao corpo da resposta
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	List<Usuario> usuarios = usuarioRepository.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios,HttpStatus.OK);
    	   	
    }
    
    //Salvar o objeto Ususario no BD
    
    @PostMapping(value="salvar",consumes=MediaType.APPLICATION_JSON_VALUE)//mapear URL
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){//recebe os dados para salvar
    	
    	Usuario user = usuarioRepository.save(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    	
    }
    //Atualizar o objeto Ususario no BD
    @PutMapping(value="atualizar")//Mapear URL
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){//recebe os dados para salvar
    	
    	if(usuario.getId()==null) {
    		return new ResponseEntity<String>("Id não foi informado para atualização.",HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);    		
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @DeleteMapping(value="delete") //mapeia a urdl
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<String> delete(@RequestParam Long iduser){
    	
    	usuarioRepository.deleteById(iduser);
		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

    }
    
    @GetMapping(value="buscaruserid") //mapeia a urdl
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name="iduser") Long iduser){//Recebe os dados para consultar
    	
    	Usuario usuario = usuarioRepository.findById(iduser).get();
		return new ResponseEntity<Usuario>(usuario,HttpStatus.OK);

    }
    
    @GetMapping(value="buscarusernome") //mapeia a urdl
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<List<Usuario>> buscarUserNome(@RequestParam(name="name") String name){//Recebe os dados para consultar
    	
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuario,HttpStatus.OK);
    	 }
  
    
}
