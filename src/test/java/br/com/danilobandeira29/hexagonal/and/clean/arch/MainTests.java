package br.com.danilobandeira29.hexagonal.and.clean.arch;

import br.com.danilobandeira29.hexagonal.and.clean.arch.infrastructure.Main;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = Main.class)
class MainTests {

	@Test
	void contextLoads() {
	}

}
