package com.mwping.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class PublishLibPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.warn("-------------PublishLibPlugin applied-------------")
    }
}