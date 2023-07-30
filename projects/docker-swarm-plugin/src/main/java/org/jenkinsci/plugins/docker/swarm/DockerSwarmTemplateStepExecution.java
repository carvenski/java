package org.jenkinsci.plugins.docker.swarm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.util.logging.Logger;

import org.apache.commons.collections.MapUtils;
import org.jenkinsci.plugins.workflow.steps.BodyExecutionCallback;
import org.jenkinsci.plugins.workflow.steps.FlowInterruptedException;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.jenkinsci.plugins.workflow.steps.SynchronousStepExecution;

import org.jenkinsci.plugins.workflow.steps.BodyInvoker;
import hudson.model.Saveable;
import java.util.stream.Collectors;


public class DockerSwarmTemplateStepExecution extends StepExecution {

    private static final Logger LOGGER = Logger.getLogger(DockerSwarmTemplateStepExecution.class.getName());

    private final transient DockerSwarmTemplateStep dockerSwarmTemplateStep;

    public DockerSwarmTemplateStepExecution(DockerSwarmTemplateStep dockerSwarmTemplateStep, StepContext context) {
        super(context);
        this.dockerSwarmTemplateStep = dockerSwarmTemplateStep;
    }

    @Override
    public boolean start() throws Exception {
        LOGGER.info("=== start DockerSwarmTemplate Step Execution");            
        final DockerSwarmCloud cloud = DockerSwarmCloud.get(); 

        // DockerSwarmAgentTemplate param
        DockerSwarmAgentTemplate dockerSwarmAgentTemplate = new DockerSwarmAgentTemplate(
            this.dockerSwarmTemplateStep.label,
            this.dockerSwarmTemplateStep.image,
            "sh\n-cx\ncurl --connect-timeout 20 --max-time 60 -o agent.jar $DOCKER_SWARM_PLUGIN_JENKINS_AGENT_JAR_URL && java -jar agent.jar -jnlpUrl $DOCKER_SWARM_PLUGIN_JENKINS_AGENT_JNLP_URL -secret $DOCKER_SWARM_PLUGIN_JENKINS_AGENT_SECRET -noReconnect -workDir /tmp",
            "/home/jenkins",
            "default"
        );
        
        LOGGER.info("=== add new label to cloud: " + dockerSwarmTemplateStep.label);        
        cloud.addAgentTemplate(dockerSwarmAgentTemplate); 
        BodyInvoker invoker = getContext()
            .newBodyInvoker()
            .withContexts(this.dockerSwarmTemplateStep)
            .withCallback(new DockerSwarmTemplateCallback(dockerSwarmAgentTemplate));
        invoker.start();
        
        List<String> labels = cloud.getAgentTemplates()
            .stream()
            .map(DockerSwarmAgentTemplate::getLabel)
            .collect(Collectors.toList());
        LOGGER.info("=== now cloud has Templates: " + labels);

        return false;
    }

    private class DockerSwarmTemplateCallback extends BodyExecutionCallback.TailCall implements Serializable, Saveable{

        private static final long serialVersionUID = 6043919968776851324L;

        private DockerSwarmAgentTemplate dockerSwarmTemplate;

        private DockerSwarmTemplateCallback(DockerSwarmAgentTemplate dockerSwarmTemplate) {
            this.dockerSwarmTemplate = dockerSwarmTemplate;
        }
        
        /**
         * Remove the template after step is done
         */
        @Override
        protected void finished(StepContext context) throws Exception {
            try {
                LOGGER.info("=== delete label from cloud: " + this.dockerSwarmTemplate.getLabel());
                
                final DockerSwarmCloud cloud = DockerSwarmCloud.get(); 
                cloud.removeAgentTemplate(this.dockerSwarmTemplate);
                
                List<String> labels = cloud.getAgentTemplates()
                    .stream()
                    .map(DockerSwarmAgentTemplate::getLabel)
                    .collect(Collectors.toList());
                LOGGER.info("=== now cloud has Templates: " + labels);
            } catch (Exception e) {
                LOGGER.info(e.toString());
            }
        }

        /**
         * Empty implementation of Saveable interface. This interface is used for DescribableList implementation
         */
        @Override
        public void save() {}

    }

}