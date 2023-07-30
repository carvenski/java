package org.jenkinsci.plugins.docker.swarm;

import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;

import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;


public class DockerSwarmTemplateStep extends Step implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(DockerSwarmTemplateStep.class.getName());

    public String label;
    public String image;

    @DataBoundConstructor
    public DockerSwarmTemplateStep(String label, String image) {
        // params from DockerSwarmAgentTemplate
        LOGGER.info("=== new dockerSwarmTemplate: label=" + label + " image=" + image);   
        this.label = label;
        this.image = image;
    }

    @Override
    public StepExecution start(StepContext context) throws Exception {
        return new DockerSwarmTemplateStepExecution(this, context);
    }

    @Extension(optional = true)
    public static final class DescriptorImpl extends StepDescriptor {

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return ImmutableSet.of(Run.class, TaskListener.class);
        }

        @Override
        public String getFunctionName() {
            return "dockerSwarmTemplate";
        }

        @Override
        public String getDisplayName() {
            return "Define a task template for DockerSwarm plugin";
        }

        @Override
        public boolean takesImplicitBlockArgument() {
            return true;
        }

        @Override
        public boolean isAdvanced() {
            return true;
        }

        @Override
        public String toString() {
            return "DockerSwarmTemplateStep";
        }
    }
    
}
