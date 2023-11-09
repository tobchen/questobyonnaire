package de.tobchen.health.questobyonnaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class QuestobyonnaireApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(QuestobyonnaireApplication.class, args);
	}
}
