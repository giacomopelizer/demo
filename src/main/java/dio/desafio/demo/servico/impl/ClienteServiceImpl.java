package dio.desafio.demo.servico.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dio.desafio.demo.modelo.Cliente;
import dio.desafio.demo.modelo.ClienteRepository;
import dio.desafio.demo.modelo.Endereco;
import dio.desafio.demo.modelo.EnderecoRepository;
import dio.desafio.demo.servico.ClienteService;
import dio.desafio.demo.servico.ViaCepService;


@Service
public class ClienteServiceImpl implements ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ViaCepService viaCepService;
	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}
	@Override
	public Cliente buscarPorId(Long id) {
		// Buscar Cliente por ID.
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}
	@Override
	public void adicionar(Cliente cliente) {
		salvarClienteComCep(cliente);
	}
	@Override
	public void atualizar(Long id, Cliente cliente) {
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (clienteBd.isPresent()) {
			salvarClienteComCep(cliente);
		}
	}
	@Override
	public void excluir(Long id) {
		clienteRepository.deleteById(id);
	}
	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}
}