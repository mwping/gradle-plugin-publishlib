package com.mwping.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.json.JSONObject
import java.io.File

/**
 * weiping@atlasv.com
 * 2021/9/26
 */
open class GenPublishConfigFilesTask : DefaultTask() {
    @TaskAction
    private fun doAction() {
        val libVersion = project.nextReleaseVersion
        val tag = "v$libVersion"
        File(project.rootProject.rootDir, "lib_version.txt").writeText(libVersion)
        val changelogFile = File(project.rootProject.rootDir, "CHANGELOG.md")
        changelogFile.delete()
        project.execCmd("conventional-changelog -p angular -i CHANGELOG.md -s")
        val content = changelogFile.readText()
        val index = content.indexOf("\n")
        val obj = JSONObject().put("body", content.substring(index + 1)).put("tag_name", tag)
        File(project.rootProject.rootDir, "CHANGELOG.json").writeText(obj.toString())
    }
}