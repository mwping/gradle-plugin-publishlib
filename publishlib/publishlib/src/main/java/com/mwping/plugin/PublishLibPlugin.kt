package com.mwping.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class PublishLibPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.warn("-------------PublishLibPlugin applied-------------")
        initGithubActions(project)
    }

    private fun initGithubActions(project: Project) {
        val releaseYmlFile = File(File("").parentFile, "src/main/resources/release.yml")
        val outputFile =
            File(project.rootProject.rootDir, ".github/workflows/${releaseYmlFile.name}")
        outputFile.takeIf { it.length() > 0 } ?: releaseYmlFile.copyTo(outputFile).also {
            project.logger.warn("initGithubActions: $releaseYmlFile(${releaseYmlFile.length()}) -> $outputFile(${outputFile.length()})")
        }
    }
}