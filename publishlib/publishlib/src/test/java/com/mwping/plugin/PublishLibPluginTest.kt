package com.mwping.plugin

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Test

class PublishLibPluginTest {
    @Test
    fun pluginRegistersATask() {
        // Create a test project and apply the plugin
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.mwping.android.plugin.publishlib")
    }
}