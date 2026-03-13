package dev.forgepack.library.internal.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@AutoConfiguration
@EntityScan("dev.forgepack.library.internal.model")
public class ConfigurationJPAAuto {
}
