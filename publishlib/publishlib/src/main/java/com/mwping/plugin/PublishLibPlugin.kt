package com.mwping.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register

class PublishLibPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.warn("-------------PublishLibPlugin applied-------------")
        project.plugins.apply("maven-publish")

        project.tasks.register<Jar>("androidSourcesJar") {
            archiveClassifier.set("sources")
            from(project.android.sourceSets.getByName("main").java.srcDirs)
        }

        project.afterEvaluate {
            configure<PublishingExtension> {
                repositories {
                    maven {
                        name = "GitHubPackages"
                        setUrl(project.repoUrl)
                        credentials {
                            username = project.getEnv("GPR_USR")
                            password = project.getEnv("GPR_KEY")
                        }
                    }
                }
                publications {
                    create("release", MavenPublication::class.java) {
                        from(components.getByName("release"))
                        artifact(tasks.findByName("androidSourcesJar"))
                        groupId = project.groupId
                        artifactId = project.artifactId ?: project.name
                        version = "0.0.1"
                    }
                }
            }
        }
    }
}