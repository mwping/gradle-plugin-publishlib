package com.mwping.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import java.util.regex.Pattern

/**
 * weiping@atlasv.com
 * 2021/9/26
 */
fun Project.getEnv(key: String): String? {
    return System.getenv(key) ?: project.findProperty(key)?.toString()
}

val Project.android: LibraryExtension get() = extensions.findByName("android") as LibraryExtension

val Project.nextReleaseVersion: String
    get() {
        val text =
            execCmd("standard-version --dry-run --skip.commit --skip.changelog").replace("\n", "#")
        val pattern = Pattern.compile(".* tagging release (.*)#.*")
        val matcher = pattern.matcher(text)
        return if (matcher.matches()) matcher.group(1).replace("v", "") else "1.0.0"
    }

fun Project.execCmd(cmd: String): String {
    val process = Runtime.getRuntime().exec(cmd, null, rootDir)
    val code = process.waitFor()
    if (code != 0) {
        error(process.errorStream.use { it.bufferedReader().readText() })
    }
    return process.inputStream.bufferedReader().readText().trim().also {
        println(it)
    }
}

val Project.repoUrl
    get() = project.findProperty("REPO_URL")?.toString()?.takeIf { it.isNotEmpty() }
        ?: error("REPO_URL can not be null")
val Project.groupId
    get() = project.findProperty("GROUP_ID")?.toString()?.takeIf { it.isNotEmpty() }
        ?: error("GROUP_ID can not be null")
val Project.artifactId get() = project.findProperty("ARTIFACT_ID")?.toString()

val Project.libVersion get() = project.findProperty("LIB_VERSION")?.toString()