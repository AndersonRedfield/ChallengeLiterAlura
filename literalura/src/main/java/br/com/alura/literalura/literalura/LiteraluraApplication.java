package br.com.alura.literalura.literalura;

import br.com.alura.literalura.literalura.repository.LivroRepository;
import br.com.alura.literalura.literalura.view.Menu;

public class
LiteraluraApplication {
	public static void main(String[] args) throws Exception {
		Menu menu = new Menu(new LivroRepository());
		menu.exibirMenu();
	}
}