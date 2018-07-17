package org.gz.risk.auditing.util;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.internal.io.ResourceFactory;

import java.io.File;

/**
 * @since jdk1.8
 */
public class DroolsUtils {

    public static void createKieJar(KieServices ks, ReleaseId releaseId, File[] ruleFiles) {
        KieFileSystem kfs = createKieFileSystemWithKProject(ks, true);
        kfs.writePomXML(genPom(releaseId));
        if(ruleFiles != null) {
            for (File file : ruleFiles) {
                kfs.write("src/main/resources/" + file.getName(), ResourceFactory.newFileResource(file));
            }
        }
        KieBuilder kieBuilder = ks.newKieBuilder(kfs);
        if (!kieBuilder.buildAll().getResults().getMessages().isEmpty()) {
            throw new IllegalStateException("Error creating KieBuilder.");
        }
    }

    /**
     * 创建默认的kbase和stateful的kiesession
     *
     * @param ks
     * @param isDefault
     * @return
     */
    private static KieFileSystem createKieFileSystemWithKProject(KieServices ks, boolean isDefault) {
        KieModuleModel kproj = ks.newKieModuleModel();
        KieBaseModel kieBaseModel1 = kproj.newKieBaseModel("KBase").setDefault(isDefault)
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY).setEventProcessingMode(EventProcessingOption.STREAM);
        // Configure the KieSession.
        kieBaseModel1.newKieSessionModel("CreditRules").setDefault(isDefault)
                .setType(KieSessionModel.KieSessionType.STATEFUL);
        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.writeKModuleXML(kproj.toXML());
        return kfs;
    }

    /**
     * 创建kjar的pom
     *
     * @param releaseId
     * @param dependencies
     * @return
     */
    private static String genPom(ReleaseId releaseId, ReleaseId... dependencies) {
        String pom = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n"
                + "  <modelVersion>4.0.0</modelVersion>\n"
                + "  <groupId>" + releaseId.getGroupId() + "</groupId>\n"
                + "  <artifactId>" + releaseId.getArtifactId() + "</artifactId>\n"
                + "  <version>" + releaseId.getVersion() + "</version>\n";

        if (dependencies != null && dependencies.length > 0) {
            pom += "<dependencies>\n";
            for (ReleaseId dep : dependencies) {
                pom += "<dependency>\n";
                pom += "  <groupId>" + dep.getGroupId() + "</groupId>\n";
                pom += "  <artifactId>" + dep.getArtifactId() + "</artifactId>\n";
                pom += "  <version>" + dep.getVersion() + "</version>\n";
                pom += "</dependency>\n";
            }
            pom += "</dependencies>\n";
        }
        pom += "</project>";
        return pom;
    }
}
