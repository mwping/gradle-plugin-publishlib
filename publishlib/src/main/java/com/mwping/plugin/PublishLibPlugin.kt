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

        val isAndroidModule = project.extensions.findByName("android") != null

        if(isAndroidModule){
            project.tasks.register<Jar>("androidSourcesJar") {
                archiveClassifier.set("sources")
                from(project.android.sourceSets.getByName("main").java.srcDirs)
            }
        }

        project.tasks.register<GenPublishConfigFilesTask>("genPublishConfigFilesTask")

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
                    val libArtifactId = project.artifactId ?: project.name
                    val libVersion = project.nextReleaseVersion
                    val libGroup = project.groupId
                    println("-----------------------------------------------")
                    println("准备发布:\nimplementation(\"$libGroup:$libArtifactId:$libVersion\")")
                    println("-----------------------------------------------")

                    create("release", MavenPublication::class.java) {
                        val comp = components.getByName(if (isAndroidModule) "release" else "java")
                        from(comp)
                        if(isAndroidModule){
                            artifact(tasks.findByName("androidSourcesJar"))
                        }
                        groupId = libGroup
                        artifactId = libArtifactId
                        version = libVersion
                    }
                }
            }
        }
    }
}