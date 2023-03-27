package tobyspringboot.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.stream.StreamSupport;

public class MyAutoConfigurationImportSelector implements DeferredImportSelector {

    private final ClassLoader classLoader;

    public MyAutoConfigurationImportSelector(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 어떤 애플리케이션의 class path에서 리소스를 읽어올때 클래스 로더를 사용한다.
        // 스프링컨테이너가 빈 클래스를 로딩할때 사용하는 클래스로더를 집어 넣는다.
        // 클래스 로더 역시 ApplicationContextAware라는 클래스를 구현하면 그 안의 setter method를 통하여 빈을 주입할 수 있었다.
        // load라는 method는 META-INF/spring/full-qualified-annotation-name.imports 에서 경로를 읽어온다.
        Iterable<String> candidates = ImportCandidates.load(MyAutoConfiguration.class, classLoader);
        return StreamSupport.stream(candidates.spliterator(), false).toArray(String[]::new);
    }
}
