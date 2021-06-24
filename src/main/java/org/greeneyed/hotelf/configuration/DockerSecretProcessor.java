package org.greeneyed.hotelf.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

public class DockerSecretProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String secretsPath = environment.getProperty("docker-secret.bind-path");
        if(!StringUtils.hasText(secretsPath)) {
            secretsPath = "/run/secrets/";
        }
        System.err.println("Trying to extract secrets from " + secretsPath);
        if (StringUtils.hasText(secretsPath)) {
            Path bindPath = Paths.get(secretsPath);
            if (bindPath.toFile().exists() && Files.isDirectory(bindPath)) {
                try {
                    MapPropertySource pptySource = new MapPropertySource("docker-secrets", Files.list(bindPath)
                            .map(Path::toFile)
                            .filter(File::isFile)
                            .collect(Collectors.toMap(File::getName, file -> {
                                try {
                                    System.err.println("Trying to extract secrets from " + file.getAbsolutePath());
                                    return new String(FileCopyUtils.copyToByteArray(file));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            })));
                    environment.getPropertySources().addLast(pptySource);
                } catch (Exception e) {
                    System.err.println("Error reading secrets");
                    e.printStackTrace();
                }
            } else if(!bindPath.toFile().exists()) {
                System.err.println("Secrets directory not found: " + bindPath.toFile().getAbsolutePath() +", nothing to do");
            }
        }
    }

}
