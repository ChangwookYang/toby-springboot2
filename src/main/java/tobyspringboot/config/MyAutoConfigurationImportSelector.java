package tobyspringboot.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyAutoConfigurationImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                "tobyspringboot.config.autoconfig.DispatcherServletConfig",
                "tobyspringboot.config.autoconfig.TomcatWebServerConfig"
        };
    }
}
