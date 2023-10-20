package dio.desafio.demo.servico;

import dio.desafio.demo.modelo.Cliente;

public interface ClienteService {
	Iterable<Cliente> buscarTodos();
	Cliente buscarPorId(Long id);
	void adicionar(Cliente cliente);
	void atualizar(Long id, Cliente cliente);
	void excluir(Long id);
}