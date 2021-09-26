package com.mwping.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project

/**
 * weiping@atlasv.com
 * 2021/9/26
 */
fun Project.getEnv(key: String): String? {
    return System.getenv(key) ?: project.findProperty(key)?.toString()
}

val Project.android: LibraryExtension get() = extensions.findByName("android") as LibraryExtension

val Project.repoUrl
    get() = project.findProperty("REPO_URL")?.toString()?.takeIf { it.isNotEmpty() }
        ?: error("REPO_URL can not be null")
val Project.groupId
    get() = project.findProperty("GROUP_ID")?.toString()?.takeIf { it.isNotEmpty() }
        ?: error("GROUP_ID can not be null")
val Project.artifactId get() = project.findProperty("ARTIFACT_ID")?.toString()