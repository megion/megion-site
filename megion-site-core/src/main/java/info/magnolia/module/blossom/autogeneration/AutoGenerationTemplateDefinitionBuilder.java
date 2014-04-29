package info.magnolia.module.blossom.autogeneration;
 
import info.magnolia.module.blossom.dispatcher.BlossomDispatcher;
import info.magnolia.module.blossom.template.BlossomAreaDefinition;
import info.magnolia.module.blossom.template.DetectedHandlersMetaData;
import info.magnolia.module.blossom.template.HandlerMetaData;
import info.magnolia.module.blossom.template.TemplateDefinitionBuilder;
import info.magnolia.rendering.engine.RenderException;
import info.magnolia.rendering.generator.Generator;
import info.magnolia.rendering.template.AutoGenerationConfiguration;

import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class AutoGenerationTemplateDefinitionBuilder extends TemplateDefinitionBuilder {
 
    @Override
    protected BlossomAreaDefinition buildAreaDefinition(BlossomDispatcher dispatcher, DetectedHandlersMetaData detectedHandlers, HandlerMetaData area) {
        BlossomAreaDefinition definition = super.buildAreaDefinition(dispatcher, detectedHandlers, area);
        if (AutoGeneratingArea.class.isAssignableFrom(area.getHandler().getClass())) {
            definition.setAutoGeneration(new BlossomAutoGenerationConfiguration((AutoGeneratingArea) area.getHandler()));
        }
        return definition;
    }
 
    public static class BlossomAutoGenerationConfiguration implements AutoGenerationConfiguration {
 
        private final AutoGeneratingArea handler;
 
        public BlossomAutoGenerationConfiguration(AutoGeneratingArea handler) {
            this.handler = handler;
        }
 
        public AutoGeneratingArea getHandler() {
            return handler;
        }
 
        @Override
        public Map<String, Object> getContent() {
            return null;
        }
 
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
        public Class<Generator<AutoGenerationConfiguration>> getGeneratorClass() {
            return (Class) BlossomGenerator.class;
        }
    }
 
    public static class BlossomGenerator implements Generator<BlossomAutoGenerationConfiguration> {
 
        private final static Logger log = LoggerFactory.getLogger(BlossomGenerator.class);
 
        private Node areaNode;
 
        public BlossomGenerator(Node areaNode) {
            this.areaNode = areaNode;
        }
 
        @Override
        public void generate(BlossomAutoGenerationConfiguration configuration) throws RenderException {
            try {
                configuration.getHandler().generate(areaNode);
            } catch (RepositoryException e) {
                log.error("Failed to autogenerate components for [{}]", configuration.getHandler().getClass().getName(), e);
            }
        }
    }
}