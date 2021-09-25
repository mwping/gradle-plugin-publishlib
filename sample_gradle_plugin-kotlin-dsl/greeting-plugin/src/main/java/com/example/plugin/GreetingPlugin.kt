package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.warn("-------------GreetingPlugin applied-------------")
        project.tasks.register("greet") {
            doLast {
                project.logger.warn("Task $name: Hello from plugin 'com.example.plugin.greeting'")
            }
        }
    }
}